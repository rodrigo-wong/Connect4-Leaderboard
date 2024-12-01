Set-Location -Path "test" # Change the working directory to "test"

$publicName = ($args[0] -split "-")[2] # Extract publicName from submission folder (connectFour-publicName)

# Delete all .class
Remove-Item -Path "*.class"

# Copy student's AI solution from the parent directory
Copy-Item -Path "../$($args[0])/$($publicName)Bot.java" -Destination .

# Echo the directory name in PowerShell
Write-Host "Testing $($publicName)"

# Compile playConnectFour.java 
javac playConnectFour.java
javac ($publicName+"Bot.java")
javac RandomAI.java # We need to pass this as a parameter too, case sensitive!!

# Check the exit code ($LASTEXITCODE) for the compile result
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compile failed, skipping bot."
}
else {
    Write-Host "Compile successful, scoring bot."

    #[Net.ServicePointManager]::SecurityProtocol = "Tls13"
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls13
    $url = "https://csunix.mohawkcollege.ca:443/~adams/10185/connectFour/backend/updateData.php"
    #$url = "http://localhost:1080/csep-work/ConnectFour/backend/updateData.php"
    $headers = @{
        'Content-Type' = 'application/json'
        'API_KEY' = 'BotSteve@2023' # Store this in an environment variable ?
   }
    $levels = @("E", "M", "H")

    # Student as Player 1 playing every level against AI
    foreach($level in $levels){
        $lastLine = ""
        $result = ""
        java playConnectFour -d $level -p1 ($publicName + "Bot") -p1name $publicName -p2 "RandomAI" -p2name "RandomAI" -p1auto -p2auto | ForEach-Object {
            $_
            $lastLine = $_
        }
        $lastLine = $lastLine -split " "
        
        if($lastLine[0] -eq $publicName){
            $result = "W"
        } else {
            $result = "L"
        }

        # Set body for cURL command to update student's new results
        $body = @{
            "publicName" = $publicName
            "difficulty" = $level
            "result" = $result
            "firstPlayer" = "player"
        } | ConvertTo-Json

        $response = Invoke-RestMethod -Uri $url -Method Put -Body $body -Headers $headers

        $response # ??
    }

    # Student as Player 2 playing every level against AI
    foreach($level in $levels){
        $lastLine = ""
        $result = ""
        java playConnectFour -d $level -p2 $($publicName + "Bot") -p2name $publicName -p1 "RandomAI" -p1name "RandomAI" -p1auto -p2auto | ForEach-Object {
            $_
            $lastLine = $_
        }
        $lastLine = $lastLine -split " "
        
        if($lastLine[0] -eq $publicName){
            $result = "W"
        } else {
            $result = "L"
        }

        # Set body for cURL command to update student's new results
        $body = @{
            "publicName" = $publicName
            "difficulty" = $level
            "result" = $result
            "firstPlayer" = "bot"
        } | ConvertTo-Json

        $response = Invoke-RestMethod -Uri $url -Method Put -Body $body -Headers $headers

        $response # ??
    }

    Remove-Item "$($publicName)Bot.java" # Delete student's bot solution 

}