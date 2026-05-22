@echo off
chcp 65001 >nul

echo ==============================================
echo          Monitor System Docker Deploy Script
echo ==============================================
echo.

setlocal

echo [1/3] Stopping and removing existing containers...
docker-compose down
if %errorlevel% neq 0 (
    echo Warning: Container may not be running, continuing...
)

echo.
echo [2/3] Rebuilding Docker image...
docker-compose build

if %errorlevel% neq 0 (
    echo Error: Docker image build failed!
    pause
    exit /b 1
)

echo.
echo [3/3] Starting Docker container...
docker-compose up -d

if %errorlevel% neq 0 (
    echo Error: Container start failed!
    pause
    exit /b 1
)

echo.
echo ==============================================
echo              Deployment Completed!
echo ==============================================
echo.
echo Service Address: http://localhost:8080
echo Frontend Page: http://localhost:8080/html/index.html
echo API Endpoint:   http://localhost:8080/api
echo.
echo Check container status: docker-compose ps
echo Check container logs:  docker-compose logs -f
echo.

endlocal
pause