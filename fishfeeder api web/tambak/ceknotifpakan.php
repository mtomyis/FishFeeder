<?php

if ($_SERVER['REQUEST_METHOD']=='GET') {

    require_once 'koneksi.php';

    $response = $conn->prepare("SELECT kode, jampakan FROM notifpakan;");
    $response -> execute();

    $response->bind_result($kode, $status);

    $result = array();

    while ($response->fetch()) {

        $result['kode']    = $kode;
        $result['pakan']    = $status;

        // array_push($result, $h);
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
    