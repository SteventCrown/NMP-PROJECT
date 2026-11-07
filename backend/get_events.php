<?php
require_once("connection.php");

try {
    $search = isset($_REQUEST['search']) ? trim($_REQUEST['search']) : '';
    $category = isset($_REQUEST['category']) ? trim($_REQUEST['category']) : '';

    $sql = "SELECT * FROM events WHERE 1=1";
    $params = array();
    $types = "";

    if (!empty($search)) {
        $sql .= " AND (title LIKE ? OR description LIKE ? OR organizer LIKE ?)";
        $searchParam = "%" . $search . "%";
        $params[] = $searchParam;
        $params[] = $searchParam;
        $params[] = $searchParam;
        $types .= "sss";
    }

    if (!empty($category) && $category !== "Semua") {
        $sql .= " AND category = ?";
        $params[] = $category;
        $types .= "s";
    }

    $sql .= " ORDER BY id DESC";

    $stmt = $c->prepare($sql);
    if (!empty($params)) {
        $stmt->bind_param($types, ...$params);
    }
    $stmt->execute();
    $result = $stmt->get_result();

    $events = array();
    while ($row = $result->fetch_assoc()) {
        // cast numeric fields to int for clean JSON
        $row['id'] = (int)$row['id'];
        $row['quota'] = (int)$row['quota'];
        $row['registered_count'] = (int)$row['registered_count'];
        $row['likes'] = (int)$row['likes'];
        $row['skk_points'] = (int)$row['skk_points'];
        $events[] = $row;
    }

    echo json_encode(array(
        "result" => "SUCCESS",
        "data" => $events
    ));
} catch (Exception $e) {
    echo json_encode(array(
        "result" => "ERROR",
        "message" => $e->getMessage()
    ));
}
?>
