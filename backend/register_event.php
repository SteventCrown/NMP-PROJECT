<?php
require_once('connection.php');

if (
    isset($_POST['event_id']) &&
    isset($_POST['student_name']) &&
    isset($_POST['student_nrp']) &&
    isset($_POST['student_email']) &&
    isset($_POST['student_phone']) &&
    isset($_POST['participant_type']) &&
    isset($_POST['attendance_method'])
) {
    $event_id = (int)$_POST['event_id'];
    $student_name = trim($_POST['student_name']);
    $student_nrp = trim($_POST['student_nrp']);
    $student_email = trim($_POST['student_email']);
    $student_phone = trim($_POST['student_phone']);
    $participant_type = trim($_POST['participant_type']);
    $attendance_method = trim($_POST['attendance_method']);
    $claim_skk = isset($_POST['claim_skk']) ? (int)$_POST['claim_skk'] : 1;

    try {
        // Cek kuota event
        $stmtCheck = $c->prepare("SELECT quota, registered_count FROM events WHERE id = ?");
        $stmtCheck->bind_param("i", $event_id);
        $stmtCheck->execute();
        $resCheck = $stmtCheck->get_result();

        if ($resCheck->num_rows == 0) {
            echo json_encode(array("result" => "ERROR", "message" => "Event tidak ditemukan"));
            exit();
        }

        $eventRow = $resCheck->fetch_assoc();
        if ($eventRow['registered_count'] >= $eventRow['quota']) {
            echo json_encode(array("result" => "ERROR", "message" => "Kuota pendaftaran event ini sudah penuh"));
            exit();
        }

        // Insert pendaftaran
        $sql = "INSERT INTO registrations (event_id, student_name, student_nrp, student_email, student_phone, participant_type, attendance_method, claim_skk) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("issssssi", $event_id, $student_name, $student_nrp, $student_email, $student_phone, $participant_type, $attendance_method, $claim_skk);
        $stmt->execute();

        // Increment registered_count di tabel events
        $updateSql = "UPDATE events SET registered_count = registered_count + 1 WHERE id = ?";
        $stmtUpdate = $c->prepare($updateSql);
        $stmtUpdate->bind_param("i", $event_id);
        $stmtUpdate->execute();

        echo json_encode(array(
            "result" => "SUCCESS",
            "message" => "Pendaftaran berhasil dikonfirmasi! Tiket Anda telah tersimpan."
        ));
    } catch (Exception $e) {
        echo json_encode(array("result" => "ERROR", "message" => $e->getMessage()));
    }
} else {
    echo json_encode(array("result" => "ERROR", "message" => "Semua kolom formulir wajib diisi"));
}
?>