<?php

require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD']=='POST') {
    
    $tglpersemaian = $_POST['tanggalpersemaian'];
    // $id = $_POST['id'];
    // $tglpersemaian="2019-5-1";


    $tgl1 = date('Y-m-d', strtotime('+1 days', strtotime($tglpersemaian))); $tgl1_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl1)));
    $tgl2 = date('Y-m-d', strtotime('+1 days', strtotime($tgl1_2))); $tgl2_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl2)));
    $tgl3 = date('Y-m-d', strtotime('+1 days', strtotime($tgl2_2))); $tgl3_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl3)));
    $tgl4 = date('Y-m-d', strtotime('+1 days', strtotime($tgl3_2))); $tgl4_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl4)));
    $tgl5 = date('Y-m-d', strtotime('+1 days', strtotime($tgl4_2))); $tgl5_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl5)));
    $tgl6 = date('Y-m-d', strtotime('+1 days', strtotime($tgl5_2))); $tgl6_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl6)));
    $tgl7 = date('Y-m-d', strtotime('+1 days', strtotime($tgl6_2))); $tgl7_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl7)));
    $tgl8 = date('Y-m-d', strtotime('+1 days', strtotime($tgl7_2))); $tgl8_2 = date('Y-m-d', strtotime('+14 days', strtotime($tgl8)));

    $result = array();
    $result['read'] = array();

    $l['tgl1'] = $tgl1; $l['tgl1_2'] = $tgl1_2;
    $l['tgl2'] = $tgl2; $l['tgl2_2'] = $tgl2_2;
    $l['tgl3'] = $tgl3; $l['tgl3_2'] = $tgl3_2;
    $l['tgl4'] = $tgl4; $l['tgl4_2'] = $tgl4_2;
    $l['tgl5'] = $tgl5; $l['tgl5_2'] = $tgl5_2;
    $l['tgl6'] = $tgl6; $l['tgl6_2'] = $tgl6_2;
    $l['tgl7'] = $tgl7; $l['tgl7_2'] = $tgl7_2;
    $l['tgl8'] = $tgl8; $l['tgl8_2'] = $tgl8_2;
    
    array_push($result['read'], $l);

    echo json_encode($result);

    }else {
 
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 }


?>
