<?php

if($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    // $photo = $_REQUEST['photo'];
    $idp = $_POST['id'];
    $nama = date('H:i:s');
    $id = 1;
    
    $path = "image/$nama.jpeg";

    $finalPath = "http://wiridhub.id/tambak/".$path;

    require_once 'connect.php';

    //hapus dulu
    // $response = $conn->prepare("SELECT gambar FROM camera WHERE id=1;");

    // $response -> execute();

    // $response->bind_result($gambar);

    // $result = array();

    // unlink("image/.$gambar");
    //hapus dulu

    //simpan baru
    $sql = "UPDATE camera SET namagambar ='$finalPath', gambar = '$nama' WHERE id='$id' ";

    if (mysqli_query($conn, $sql)) {
        
        // if ( file_put_contents($path, base64_decode($photo) ) ) {
            
            $result['success'] = "1";
            $result['message'] = "success";

            echo json_encode($result);
            mysqli_close($conn);
        // }

    }
    //simpan baru

}
    else{
        $result['success'] = "0";
        $result['message'] = "not success";
        echo json_encode($result);
        mysqli_close($conn);
    }

?>