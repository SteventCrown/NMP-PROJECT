<?php
require_once('connection.php');

try {
    $nrp = isset($_REQUEST['student_nrp']) ? trim($_REQUEST['student_nrp']) : '';

    $sql = "SELECT r.id as registration_id, r.student_name, r.student_nrp, r.student_email, r.student_phone, r.participant_type, r.attendance_method, r.claim_skk, r.registration_date, e.id as event_id, e.title, e.category, e.organizer, e.date_time, e.location, e.poster_url, e.skk_points 
            FROM registrations r 
            JOIN events e ON r.event_id = e.id";

    if (!empty($nrp)) {
        $sql .= " WHERE r.student_nrp = ? ORDER BY r.id DESC";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("s", $nrp);
    } else {
        $sql .= " ORDER BY r.id DESC";
        $stmt = $c->prepare($sql);
    }

    $stmt->execute();
    $result = $stmt->get_result();

    $registrations = array();
    while ($row = $result->fetch_assoc()) {
        $row['registration_id'] = (int)$row['registration_id'];
        $row['event_id'] = (int)$row['event_id'];
        $row['claim_skk'] = (int)$row['claim_skk'];
        $row['skk_points'] = (int)$row['skk_points'];
        $registrations[] = $row;
    }

    echo json_encode(array(
        "result" => "SUCCESS",
        "data" => $registrations
    ));
} catch (Exception $e) {
    echo json_encode(array("result" => "ERROR", "message" => $e->getMessage()));
}
?>