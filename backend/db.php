<?php
    $conn = new mysqli("localhost", "root", "", "connect4");

    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
?>
