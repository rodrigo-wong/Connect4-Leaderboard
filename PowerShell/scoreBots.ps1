Set-Location -Path 'PowerShell'
Remove-Item -Path 'connect-four-submissions' -Recurse -Force -ErrorAction SilentlyContinue
New-Item -Path 'connect-four-submissions' -ItemType Directory

# Clone every repository in Github classroom here
gh classroom clone student-repos -a 521811

Set-Location -Path 'connect-four-submissions'
New-Item -Name 'test' -ItemType Directory
Copy-Item -Path '../mog-connectFour/*.java' -Destination './test'

Get-ChildItem -Directory -Filter "connect-four-*" | ForEach-Object {
    & "../test.ps1" $_.Name
}

Set-Location -Path "../../"