<?php
include 'config.php';
header('Content-Type: application/json');

$json = file_get_contents('php://input');
$data = json_decode($json, true);

if (isset($data['id'])) {
    $id = $data['id'];
    $title = mysqli_real_escape_string($conn, $data['title']);
    $desc = mysqli_real_escape_string($conn, $data['description']);
    $content = mysqli_real_escape_string($conn, $data['content']);

    $query = "UPDATE courses SET title='$title', description='$desc', content='$content' WHERE id=$id";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => "success"]);
    } else {
        http_response_code(500);
    }
}
