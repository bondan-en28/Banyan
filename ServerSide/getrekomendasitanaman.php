<?php

header("Content-type:application/json");

require_once('connect.php');

if($_SERVER['REQUEST_METHOD']=='POST'){

    $suhu = $_POST['suhu'];
    $ketinggian = $_POST['ketinggian'];
    $struktur_tanah = $_POST['struktur_tanah'];
    $kelembapan= $_POST['kelembapan'];
    $tekanan = $_POST['tekanan'];
    $lahan = $_POST['lahan'];
    $air = $_POST['air'];
    
    $query = mysqli_query($conn, "SELECT * FROM `new_banyan_tanaman` ORDER BY nama");

    $response = array();

    while($row = mysqli_fetch_assoc($query)){
        
        if(
            ($row['ketinggian']>=$ketinggian-600 && $row['ketinggian']<=$ketinggian+600) &&
            ($row['suhu']>=$suhu-10 && $row['suhu']<=$suhu+10) &&
            ($row['kelembapan']>=$kelembapan-40 && $row['kelembapan']<=$kelembapan+40) &&
            ($row['tekanan']>=$tekanan-50 && $row['tekanan']<=$tekanan+50) &&
            $struktur_tanah>=$row['struktur_tanah'] &&
            $lahan>=$row['lahan'] &&
            $air>= $row['air']
            ){
                array_push($response,
                array(
                    'id'                => $row['id'],
                    'nama'              => $row['nama'],
                    'nama_latin'        => $row['nama_latin'],
                    'deskripsi'         => $row['deskripsi'],
                    'jenis'             => $row['jenis'],
                    'ketinggian'        => $row['ketinggian'],
                    'struktur_tanah'    => $row['struktur_tanah'],
                    'suhu'              => $row['suhu'],
                    'ph'                => $row['ph'],
                    'kelembapan'        => $row['kelembapan'],
                    'tekanan'           => $row['tekanan'],
                    'lahan'             => $row['lahan'],
                    'air'               => $row['air'],
                    'gambar'            => $row['gambar'])
                    );
                  
            }
        
            
        }

    echo json_encode($response);
}
?>