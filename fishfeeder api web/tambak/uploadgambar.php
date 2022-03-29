<?php

require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $kode = $_POST['id'];
    $photo = $_REQUEST['photo'];
    $nama = date('H:i:s');
    $id = 1;
    
    $path = "image/$nama.jpeg";
    
    $namagambar = "$nama.jpeg";

    $finalPath = "http://wiridhub.id/tambak/".$path;
    
    //hapus dulu
        // $sql=mysql_query("select * from camera where id='$id'");
        // $row=mysql_fetch_array($sql);
        // unlink("image/$row[gambar]");
    // $response = $conn->prepare("SELECT gambar FROM camera WHERE id=1;");

    // $response -> execute();

    // $response->bind_result($gambar);

    $result = array();
    
    // while ($response->fetch()) {

    //      $h['gambary']  = $gambar;

    //     array_push($result, $h);
        
    //     $ini = json_encode($h['gambary']);
    //     echo "image/"+$ini;
    //     unlink("image/.$ini");
    // }
    //hapus dulu
    
    $sql = "UPDATE camera SET namagambar ='$finalPath', gambar = '$namagambar' WHERE id='$id' ";

    // $sql = "UPDATE camera SET gambar='$nama' WHERE id='$id' ";

    if(mysqli_query($conn, $sql)) {
        if ( file_put_contents($path, base64_decode($photo) ) ) {

          $result["success"] = "1";
          $result["message"] = "success";

          mysqli_close($conn);
        }
      }
      echo json_encode($result);
}
else {
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
     
    // $result = mysql_query($conn, "SELECT * FROM camera");
    // while ($a_row = mysql_fetch_assoc ($result) )
    // {
    //     echo "kaka";
    // //     chmod("image/$a_row[gambar]", 0777);
    // // unlink("image/$a_row[gambar]");
    // }
    
 
     mysqli_close($conn);
 }

?>
    