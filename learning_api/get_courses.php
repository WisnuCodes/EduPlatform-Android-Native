<?php

include 'config.php';
header('Content-Type: application/json');

$query = "SELECT * FROM courses ORDER BY id DESC";
$result = mysqli_query($conn, $query);
$courses = [];

while($row = mysqli_fetch_assoc($result)) {
    $courses[] = $row;
}

echo json_encode($courses);
?>