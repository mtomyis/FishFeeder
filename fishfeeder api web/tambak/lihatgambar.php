<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    require_once 'koneksi.php';
    $id = $_POST['id'];

    $response = $conn->prepare("SELECT namagambar FROM camera ORDER BY id DESC LIMIT 1;");

    $response -> execute();

    $response->bind_result($nama);

    $result = array();

    while ($response->fetch()) {
        
        $h['namagambar']  = $nama;

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