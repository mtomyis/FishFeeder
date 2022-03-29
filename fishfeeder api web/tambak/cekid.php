<?php

if ($_SERVER['REQUEST_METHOD']=='GET') {

    require_once 'koneksi.php';

    $response = $conn->prepare("SELECT id_sensor FROM hasil_sensor ORDER BY id_sensor DESC LIMIT 1;");
    $response -> execute();

    $response->bind_result($id_sensor);

    $result = array();

    while ($response->fetch()) {

        $h['id_sensor']    = $id_sensor;

        array_push($result, $h);
    }
    echo json_encode($result);
}
else {
 
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 
     mysqli_close($conn);
 }

?>
    