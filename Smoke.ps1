$ErrorActionPreference = 'Stop'
$repoRoot = $PSScriptRoot
$tempBase = [IO.Path]::GetFullPath([IO.Path]::GetTempPath()).TrimEnd('\', '/')
$workDir = Join-Path $tempBase ('2006sp-smoke-' + [guid]::NewGuid().ToString('N'))

Push-Location $repoRoot
try {
    & (Join-Path $repoRoot 'Build.bat') --no-pause
    if ($LASTEXITCODE -ne 0) { throw "Build failed with exit code $LASTEXITCODE" }

    New-Item -ItemType Directory -Path $workDir | Out-Null
    New-Item -ItemType Directory -Path (Join-Path $workDir 'data') | Out-Null
    foreach ($folder in @('content', 'npcs', 'world', 'areas')) {
        $source = Join-Path $repoRoot "data/$folder"
        if (Test-Path -LiteralPath $source) {
            Copy-Item -LiteralPath $source -Destination (Join-Path $workDir 'data') -Recurse
        }
    }
    New-Item -ItemType Directory -Path (Join-Path $workDir 'data/launcher') | Out-Null
    Copy-Item -LiteralPath (Join-Path $repoRoot 'data/launcher/map_index.dat') `
        -Destination (Join-Path $workDir 'data/launcher')
    foreach ($file in @('data.dat', 'geOfferData.dat', 'partyChest.dat', 'Songs.dat',
            'watched items.dat', 'messageOfTheWeek.dat', 'minutes.log')) {
        $source = Join-Path $repoRoot "data/$file"
        if (Test-Path -LiteralPath $source) {
            Copy-Item -LiteralPath $source -Destination (Join-Path $workDir 'data')
        }
    }
    Copy-Item -LiteralPath (Join-Path $repoRoot 'cache') -Destination $workDir -Recurse

    $testClasses = Join-Path $workDir 'test-classes'
    New-Item -ItemType Directory -Path $testClasses | Out-Null
    $classPath = (Join-Path $repoRoot 'dist/server.jar') + ';' +
        (Join-Path $repoRoot 'lib/*') + ';' + $testClasses
    & javac -classpath $classPath -d $testClasses `
        (Join-Path $repoRoot 'src/test/java/SmokeChecks.java')
    if ($LASTEXITCODE -ne 0) { throw "Smoke check compilation failed with exit code $LASTEXITCODE" }

    Push-Location $workDir
    try {
        foreach ($check in @('assets', 'startup')) {
            & java -classpath $classPath SmokeChecks $check
            if ($LASTEXITCODE -ne 0) { throw "$check smoke check failed with exit code $LASTEXITCODE" }
        }
    } finally {
        Pop-Location
    }
    Write-Host 'All smoke checks passed.'
} finally {
    Pop-Location
    $resolvedWorkDir = [IO.Path]::GetFullPath($workDir)
    if ($resolvedWorkDir.StartsWith($tempBase + [IO.Path]::DirectorySeparatorChar,
            [StringComparison]::OrdinalIgnoreCase) -and
        [IO.Path]::GetFileName($resolvedWorkDir).StartsWith('2006sp-smoke-') -and
        (Test-Path -LiteralPath $resolvedWorkDir)) {
        Remove-Item -LiteralPath $resolvedWorkDir -Recurse -Force
    }
}
