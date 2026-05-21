param(
    [switch]$AutoPush,
    [switch]$Watch,
    [switch]$InstallHook,
    [int]$WatchInterval = 30
)

function Show-Title {
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Spec Document Auto Commit" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
}

function Test-GitStatus {
    $status = git status --porcelain
    $hasChanges = ($status -ne $null -and $status.Length -gt 0)
    return $hasChanges
}

function Commit-GitChanges {
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $commitMessage = "docs: update spec - $timestamp"
    git add -A
    git commit -m $commitMessage
    return ($LASTEXITCODE -eq 0)
}

function Push-GitChanges {
    param([switch]$AutoPush)
    if ($AutoPush) {
        git push
    } else {
        $push = Read-Host "Push to remote? (Y/N)"
        if ($push -eq "Y" -or $push -eq "y") {
            git push
        }
    }
}

function Install-GitHook {
    $hookPath = ".git\hooks\pre-commit"
    $hookContent = @"
#!/bin/sh
powershell -ExecutionPolicy Bypass -File update-spec.ps1 -AutoPush
exit 0
"@
    Set-Content -Path $hookPath -Value $hookContent -Encoding UTF8
    Write-Host "Hook installed: $hookPath" -ForegroundColor Green
}

function Invoke-WatchMode {
    Write-Host "Entering watch mode..." -ForegroundColor Yellow
    Write-Host "Interval: $WatchInterval seconds" -ForegroundColor Yellow
    Write-Host "Press Ctrl+C to exit" -ForegroundColor Yellow
    Write-Host ""
    
    while ($true) {
        $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
        Write-Host "[$timestamp] Checking..." -ForegroundColor Cyan
        
        $hasChanges = Test-GitStatus
        if ($hasChanges) {
            Write-Host "Changes found, committing..." -ForegroundColor Green
            $commitSuccess = Commit-GitChanges
            if ($commitSuccess) {
                git push
            }
        }
        Start-Sleep -Seconds $WatchInterval
    }
}

Show-Title

if ($InstallHook) {
    Install-GitHook
    exit
}

if ($Watch) {
    Invoke-WatchMode
    exit
}

$hasChanges = Test-GitStatus

if ($hasChanges) {
    Write-Host "Changes detected" -ForegroundColor Yellow
    $commitSuccess = Commit-GitChanges
    if ($commitSuccess) {
        Write-Host "Committed successfully" -ForegroundColor Green
        Push-GitChanges -AutoPush:$AutoPush
    }
} else {
    Write-Host "No changes to commit" -ForegroundColor Green
}

Write-Host ""
Write-Host "Done" -ForegroundColor Cyan
