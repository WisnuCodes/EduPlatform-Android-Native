<?php
$host = "localhost";
$user = "root";
$pass = ""; // Laragon default password kosong
$db   = "learning_platform";

$conn = mysqli_connect($host, $user, $pass, $db);

if (!$conn) {
    die(json_encode(["error" => "Connection failed: " . mysqli_connect_error()]));
}
