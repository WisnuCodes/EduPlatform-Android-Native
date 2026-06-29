<?php
include 'config.php';
header('Content-Type: application/json');

$json = file_get_contents('php://input');
$data = json_decode($json, true);

if (isset($data['title'])) {
    $title = mysqli_real_escape_string($conn, $data['title']);
    $desc = mysqli_real_escape_string($conn, $data['description']);
    $content = mysqli_real_escape_string($conn, $data['content']);

    $query = "INSERT INTO courses (title, description, content) VALUES ('$title', '$desc', '$content')";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => "success"]);
    } else {
        http_response_code(500);
        echo json_encode(["error" => mysqli_error($conn)]);
    }
}
