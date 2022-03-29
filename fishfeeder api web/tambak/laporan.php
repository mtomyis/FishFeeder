<?php

if ($_SERVER['REQUEST_METHOD']=='GET') {

    require_once 'koneksi.php';
    $response = $conn->prepare("SELECT waktu, suhu, ph, oksigen FROM hasil_sensor;");

    $response -> execute();

    $response->bind_result($wak, $su, $p, $oks);

    $result = array();

    while ($response->fetch()) {

        $h['waktu']    = $wak;
        $h['suhu']     = $su;
        $h['ph']       = $p;
        $h['oksigen']  = $oks;

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
    