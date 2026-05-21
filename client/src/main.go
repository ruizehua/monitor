package main

import (
	"bytes"
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"os/exec"
	"runtime"
	"strconv"
	"strings"
	"time"
)

type RegisterResponse struct {
	Code    int    `json:"code"`
	Message string `json:"message"`
	Data    struct {
		ClientId        int      `json:"clientId"`
		ClientName      string   `json:"clientName"`
		HostIp          string   `json:"hostIp"`
		ReportInterval  int      `json:"reportInterval"`
		MonitorItems    []string `json:"monitorItems"`
	} `json:"data"`
}

type MonitorData struct {
	ClientId              int     `json:"clientId"`
	CpuUsage              float64 `json:"cpuUsage"`
	PhysicalMemoryUsed    int64   `json:"physicalMemoryUsed"`
	PhysicalMemoryTotal   int64   `json:"physicalMemoryTotal"`
	VirtualMemoryUsed     int64   `json:"virtualMemoryUsed"`
	VirtualMemoryTotal    int64   `json:"virtualMemoryTotal"`
	DiskUsage             float64 `json:"diskUsage"`
	DiskTotal             int64   `json:"diskTotal"`
	DiskUsed              int64   `json:"diskUsed"`
	ProcessCount          int     `json:"processCount"`
	ProcessInfo           string  `json:"processInfo"`
	ReportTime            string  `json:"reportTime"`
}

var (
	clientId         int
	serverAddr       string
	reportInterval   int
	monitorItems     []string
)

func main() {
	name := flag.String("name", "", "Client name (required)")
	ip := flag.String("ip", "", "Host IP address (required)")
	server := flag.String("server", "http://localhost:8080", "Server address")
	interval := flag.Int("interval", 30, "Report interval in seconds")
	flag.Parse()

	if *name == "" || *ip == "" {
		log.Fatal("Please provide -name and -ip parameters")
	}

	serverAddr = *server

	err := registerClient(*name, *ip)
	if err != nil {
		log.Fatalf("Failed to register client: %v", err)
	}

	log.Printf("Client registered successfully, ID: %d", clientId)
	log.Printf("Report interval: %d seconds", reportInterval)
	log.Printf("Monitor items: %v", monitorItems)

	ticker := time.NewTicker(time.Duration(reportInterval) * time.Second)
	defer ticker.Stop()

	for range ticker.C {
		reportMonitorData()
	}
}

func registerClient(name, ip string) error {
	hostname, _ := os.Hostname()
	
	osType := runtime.GOOS
	if osType == "windows" {
		osType = "WINDOWS"
	} else {
		osType = "LINUX"
	}

	data := map[string]string{
		"clientName": name,
		"hostIp":     ip,
		"hostName":   hostname,
		"osType":     osType,
	}

	jsonData, err := json.Marshal(data)
	if err != nil {
		return err
	}

	resp, err := http.Post(serverAddr+"/api/client/register", "application/json", bytes.NewBuffer(jsonData))
	if err != nil {
		return err
	}
	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return err
	}

	var result RegisterResponse
	err = json.Unmarshal(body, &result)
	if err != nil {
		return err
	}

	if result.Code != 200 {
		return fmt.Errorf("registration failed: %s", result.Message)
	}

	clientId = result.Data.ClientId
	reportInterval = result.Data.ReportInterval
	monitorItems = result.Data.MonitorItems

	return nil
}

func reportMonitorData() {
	data := MonitorData{
		ClientId: clientId,
		ReportTime: time.Now().Format("2006-01-02T15:04:05"),
	}

	for _, item := range monitorItems {
		switch item {
		case "cpu_usage":
			data.CpuUsage = getCPUUsage()
		case "physical_memory":
			data.PhysicalMemoryUsed, data.PhysicalMemoryTotal = getMemoryInfo()
		case "virtual_memory":
			data.VirtualMemoryUsed, data.VirtualMemoryTotal = getVirtualMemoryInfo()
		case "disk_usage":
			data.DiskUsage, data.DiskTotal, data.DiskUsed = getDiskUsage()
		case "process_info":
			data.ProcessCount = getProcessCount()
		}
	}

	jsonData, err := json.Marshal(data)
	if err != nil {
		log.Printf("Failed to marshal monitor data: %v", err)
		return
	}

	resp, err := http.Post(serverAddr+"/api/monitor/report", "application/json", bytes.NewBuffer(jsonData))
	if err != nil {
		log.Printf("Failed to report data: %v", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != 200 {
		body, _ := ioutil.ReadAll(resp.Body)
		log.Printf("Report failed, status: %d, message: %s", resp.StatusCode, string(body))
	} else {
		log.Printf("Data reported successfully for client %d", clientId)
	}
}

func getCPUUsage() float64 {
	if runtime.GOOS == "windows" {
		return getWindowsCPUUsage()
	}
	return getLinuxCPUUsage()
}

func getWindowsCPUUsage() float64 {
	cmd := exec.Command("wmic", "cpu", "get", "loadpercentage")
	output, err := cmd.Output()
	if err != nil {
		return 0
	}

	lines := strings.Split(string(output), "\n")
	for _, line := range lines {
		line = strings.TrimSpace(line)
		if line != "" && line != "LoadPercentage" {
			usage, err := strconv.ParseFloat(line, 64)
			if err == nil {
				return usage
			}
		}
	}
	return 0
}

func getLinuxCPUUsage() float64 {
	content, err := ioutil.ReadFile("/proc/stat")
	if err != nil {
		return 0
	}

	lines := strings.Split(string(content), "\n")
	for _, line := range lines {
		if strings.HasPrefix(line, "cpu ") {
			fields := strings.Fields(line)
			if len(fields) >= 5 {
				user, _ := strconv.ParseFloat(fields[1], 64)
				nice, _ := strconv.ParseFloat(fields[2], 64)
				system, _ := strconv.ParseFloat(fields[3], 64)
				idle, _ := strconv.ParseFloat(fields[4], 64)
				
				total := user + nice + system + idle
				if total == 0 {
					return 0
				}
				return ((user + nice + system) / total) * 100
			}
		}
	}
	return 0
}

func getMemoryInfo() (used, total int64) {
	if runtime.GOOS == "windows" {
		return getWindowsMemoryInfo()
	}
	return getLinuxMemoryInfo()
}

func getWindowsMemoryInfo() (used, total int64) {
	cmd := exec.Command("wmic", "computersystem", "get", "totalphysicalmemory")
	output, err := cmd.Output()
	if err != nil {
		return 0, 0
	}

	lines := strings.Split(string(output), "\n")
	for _, line := range lines {
		line = strings.TrimSpace(line)
		if line != "" && line != "TotalPhysicalMemory" {
			total, _ = strconv.ParseInt(line, 10, 64)
			break
		}
	}

	cmd = exec.Command("wmic", "os", "get", "freephysicalmemory")
	output, err = cmd.Output()
	if err != nil {
		return 0, total
	}

	lines = strings.Split(string(output), "\n")
	for _, line := range lines {
		line = strings.TrimSpace(line)
		if line != "" && line != "FreePhysicalMemory" {
			free, _ := strconv.ParseInt(line, 10, 64)
			return total - free, total
		}
	}

	return 0, total
}

func getLinuxMemoryInfo() (used, total int64) {
	content, err := ioutil.ReadFile("/proc/meminfo")
	if err != nil {
		return 0, 0
	}

	var memTotal, memFree, buffers, cached int64

	lines := strings.Split(string(content), "\n")
	for _, line := range lines {
		if strings.HasPrefix(line, "MemTotal:") {
			fmt.Sscanf(line, "MemTotal: %d kB", &memTotal)
		} else if strings.HasPrefix(line, "MemFree:") {
			fmt.Sscanf(line, "MemFree: %d kB", &memFree)
		} else if strings.HasPrefix(line, "Buffers:") {
			fmt.Sscanf(line, "Buffers: %d kB", &buffers)
		} else if strings.HasPrefix(line, "Cached:") {
			fmt.Sscanf(line, "Cached: %d kB", &cached)
		}
	}

	total = memTotal * 1024
	used = (memTotal - memFree - buffers - cached) * 1024

	return used, total
}

func getVirtualMemoryInfo() (used, total int64) {
	if runtime.GOOS == "windows" {
		return getWindowsVirtualMemoryInfo()
	}
	return getLinuxVirtualMemoryInfo()
}

func getWindowsVirtualMemoryInfo() (used, total int64) {
	cmd := exec.Command("wmic", "pagefile", "get", "allocatedbasesize")
	output, err := cmd.Output()
	if err != nil {
		return 0, 0
	}

	lines := strings.Split(string(output), "\n")
	for _, line := range lines {
		line = strings.TrimSpace(line)
		if line != "" && line != "AllocatedBaseSize" {
			sizeMB, _ := strconv.ParseInt(line, 10, 64)
			total = sizeMB * 1024 * 1024
			break
		}
	}

	return 0, total
}

func getLinuxVirtualMemoryInfo() (used, total int64) {
	content, err := ioutil.ReadFile("/proc/meminfo")
	if err != nil {
		return 0, 0
	}

	var swapTotal, swapFree int64

	lines := strings.Split(string(content), "\n")
	for _, line := range lines {
		if strings.HasPrefix(line, "SwapTotal:") {
			fmt.Sscanf(line, "SwapTotal: %d kB", &swapTotal)
		} else if strings.HasPrefix(line, "SwapFree:") {
			fmt.Sscanf(line, "SwapFree: %d kB", &swapFree)
		}
	}

	total = swapTotal * 1024
	used = (swapTotal - swapFree) * 1024

	return used, total
}

func getDiskUsage() (usage float64, total, used int64) {
	if runtime.GOOS == "windows" {
		return getWindowsDiskUsage()
	}
	return getLinuxDiskUsage()
}

func getWindowsDiskUsage() (usage float64, total, used int64) {
	cmd := exec.Command("wmic", "logicaldisk", "get", "size,freespace")
	output, err := cmd.Output()
	if err != nil {
		return 0, 0, 0
	}

	lines := strings.Split(string(output), "\n")
	for i, line := range lines {
		if i == 0 {
			continue
		}
		fields := strings.Fields(line)
		if len(fields) >= 2 {
			available, _ := strconv.ParseInt(fields[0], 10, 64)
			size, _ := strconv.ParseInt(fields[1], 10, 64)
			if size > 0 {
				total = size
				used = size - available
				usage = float64(used) / float64(size) * 100
				return
			}
		}
	}
	return 0, 0, 0
}

func getLinuxDiskUsage() (usage float64, total, used int64) {
	content, err := ioutil.ReadFile("/proc/partitions")
	if err != nil {
		return 0, 0, 0
	}

	lines := strings.Split(string(content), "\n")
	for _, line := range lines {
		if strings.Contains(line, "sd") && !strings.Contains(line, "loop") {
			fields := strings.Fields(line)
			if len(fields) >= 5 {
				name := fields[3]
				if strings.HasPrefix(name, "sd") && len(name) == 3 {
					statPath := fmt.Sprintf("/sys/block/%s/size", name)
					sizeContent, err := ioutil.ReadFile(statPath)
					if err != nil {
						continue
					}
					sectors, _ := strconv.ParseInt(strings.TrimSpace(string(sizeContent)), 10, 64)
					total = sectors * 512
					break
				}
			}
		}
	}

	return 0, total, 0
}

func getProcessCount() int {
	if runtime.GOOS == "windows" {
		return getWindowsProcessCount()
	}
	return getLinuxProcessCount()
}

func getWindowsProcessCount() int {
	cmd := exec.Command("tasklist", "/v", "/fo", "csv")
	output, err := cmd.Output()
	if err != nil {
		return 0
	}

	lines := strings.Split(string(output), "\n")
	return len(lines) - 2
}

func getLinuxProcessCount() int {
	files, err := ioutil.ReadDir("/proc")
	if err != nil {
		return 0
	}

	count := 0
	for _, file := range files {
		if file.IsDir() {
			if _, err := strconv.Atoi(file.Name()); err == nil {
				count++
			}
		}
	}
	return count
}
