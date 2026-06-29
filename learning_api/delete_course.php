<?php
include 'config.php';
header('Content-Type: application/json');

$id = $_GET['id'] ?? null;

if ($id) {
    $query = "DELETE FROM courses WHERE id=$id";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => "success"]);
    } else {
        http_response_code(500);
    }
}