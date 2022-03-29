<?php

if ($_SERVER['REQUEST_METHOD']=='GET') {

    require_once 'koneksi.php';

    $response = $conn->prepare("SELECT kode, status FROM motor ORDER BY id DESC LIMIT 1;");
    $response -> execute();

    $response->bind_result($kode, $status);

    $result = array();

    while ($response->fetch()) {

        $result['kode']    = $kode;
        $result['status']    = $status;

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
    