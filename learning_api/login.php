<?php
include 'config.php';
header('Content-Type: application/json');

$username = $_GET['username'] ?? '';
$password = $_GET['password'] ?? '';

$query = "SELECT id, username, role FROM users WHERE username='$username' AND password='$password'";
$result = mysqli_query($conn, $query);

if ($row = mysqli_fetch_assoc($result)) {
    echo json_encode($row);
} else {
    http_response_code(401);
    echo json_encode(["message" => "Invalid credentials"]);
}