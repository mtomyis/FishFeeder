<?php

if ($_SERVER['REQUEST_METHOD']=='GET') {

    require_once 'koneksi.php';

    $tanggal = date('Y-m-d');
    $tglkemarin = date('Y-m-d', strtotime('-1 days', strtotime($tanggal)));

    $response = $conn->prepare("SELECT waktu, suhu, ph, oksigen FROM hasil_sensor WHERE date (waktu) = '$tglkemarin' ;");

    $response -> execute();

    $response->bind_result($wak, $su, $p, $oks);

    $result = array();

    while ($response->fetch()) {

        $result['waktu']    = $wak;
        $result['suhu']     = $su;
        $result['ph']       = $p;
        $result['oksigen']  = $oks;

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
    