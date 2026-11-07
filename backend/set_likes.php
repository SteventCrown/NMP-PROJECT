<?php
require_once('connection.php');

if (isset($_POST['id'])) {
    $id = (int)$_POST['id'];
    try {
        $sql = "UPDATE events SET likes = likes + 1 WHERE id = ?";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("i", $id);
        $stmt->execute();

        $stmt2 = $c->prepare("SELECT likes FROM events WHERE id = ?");
        $stmt2->bind_param("i", $id);
        $stmt2->execute();
        $res = $stmt2->get_result()->fetch_assoc();

        echo json_encode(array(
            "result" => "SUCCESS",
            "message" => "Berhasil menyukai event",
            "likes" => (int)$res['likes']
        ));
    } catch (Exception $e) {
        echo json_encode(array("result" => "ERROR", "message" => $e->getMessage()));
    }
} else {
    echo json_encode(array("result" => "ERROR", "message" => "Parameter id diperlukan"));
}
?>