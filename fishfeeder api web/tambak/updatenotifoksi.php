<?php

require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $kode = $_POST['kode'];
    $id = 1;

    $sql = "UPDATE notifoksigen SET kode='$kode' WHERE id='$id' ";

    if(mysqli_query($conn, $sql)) {

          $result["success"] = "1";
          $result["message"] = "success";

          mysqli_close($conn);
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
    