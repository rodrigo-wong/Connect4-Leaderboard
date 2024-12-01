<?php

    include 'db.php';

    function replaceCharacterAtIndex($inputString, $index, $newCharacter) {
        $characters = str_split($inputString);
        $characters[$index] = $newCharacter;
        $outputString = implode('', $characters);
    
        return $outputString;
    }

    $headers = getallheaders();

    if(empty($headers["API_KEY"])) {
        echo "Missing API KEY";
        die();
    }
    
    if ($_SERVER['REQUEST_METHOD'] === 'PUT') {

        $hashedPassword = env('PASSWORD'); // Store this in an environment variable ?
        if(!password_verify($headers["API_KEY"], $hashedPassword)) {
            echo "Invalid API KEY";
            die();
        }

        $data = file_get_contents("php://input");
        $putData = json_decode($data, true);

        $student = [];

        if (empty($putData['publicName']) || empty($putData['difficulty']) || 
            empty($putData['result']) || empty($putData["firstPlayer"])) {
            echo "Missing information";
            die();
        } else {
            $publicName = filter_var($putData['publicName'], FILTER_SANITIZE_STRING);
            $difficulty = strtoupper(filter_var($putData['difficulty'],FILTER_SANITIZE_STRING));
            $outcome = filter_var($putData['result'],FILTER_SANITIZE_STRING);
            $firstPlayer = strtolower(filter_var($putData['firstPlayer'],FILTER_SANITIZE_STRING));

            $query = "SELECT * FROM students WHERE publicName = ?";
            $stmt = $conn->prepare($query);

            $stmt->bind_param('s', $publicName);
            $stmt->execute();
            $result = $stmt->get_result();
            $student = $result->fetch_assoc();

            if ($student) {
                $indexToChange;
                switch($difficulty){
                    case "E":
                        $indexToChange = 0;
                        break;
                    case "M":
                        $indexToChange = 1;
                        break;
                    case "H":
                        $indexToChange = 2;
                        break;
                }
                if ($firstPlayer == "bot") {
                    $currentResult = $student['botFirstEMH'];

                    $newResult = replaceCharacterAtIndex($currentResult, $indexToChange, $outcome);

                    $updateQuery = "UPDATE students SET botFirstEMH = ?, lastDate = CURRENT_TIMESTAMP() WHERE publicName = ?";
                    $updateStmt = $conn->prepare($updateQuery);
                    $updateStmt->bind_param('ss', $newResult, $publicName);
                    if ($updateStmt->execute()) {
                        
                        echo "Results updated\n";
                        echo json_encode($student);
                    } else {
                        echo "Error updating results: " . $updateStmt->error;
                    }
                } elseif ($firstPlayer == "player"){
                    $currentResult = $student['playerFirstEMH'];
                    
                    $newResult = replaceCharacterAtIndex($currentResult, $indexToChange, $outcome);
                    $updateQuery = "UPDATE students SET playerFirstEMH = ?, lastDate = CURRENT_TIMESTAMP() WHERE publicName = ?";
                    $updateStmt = $conn->prepare($updateQuery);
                    $updateStmt->bind_param('ss', $newResult, $publicName);
                    
                    if ($updateStmt->execute()) {
                        
                        echo "Results updated\n";
                        echo json_encode($student);
                    } else {
                        echo "Error updating results: " . $updateStmt->error;
                    }
                }

                    $updateStmt->close();
            } else {
                echo "Student not on record\n";
            }

            $stmt->close();
        }
    }

    $conn->close();

?>
