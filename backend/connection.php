<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
header("Content-Type: application/json; charset=utf-8");

mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

try {
    $c = new mysqli("localhost", "root", "", "event_ubaya");
    $c->set_charset("utf8mb4");
} catch (Exception $e) {
    echo json_encode(array(
        "result" => "ERROR",
        "message" => "Database connection failed: " . $e->getMessage()
    ));
    exit();
}
?>
