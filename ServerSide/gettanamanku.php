<?php

header("Content-type:application/json");

require_once('connect.php');

if($_SERVER['REQUEST_METHOD']=='POST'){
    $user_id = $_POST['user_id'];

    $query = mysqli_query($conn, "SELECT * FROM `new_banyan_tanamanku` WHERE user_id='$user_id'");
    
    $response = array();
    $jumlah=0;
    while($row = mysqli_fetch_assoc($query)){
        $jumlah++;
        $tanaman_id = $row['tanaman_id'];
        $sqll = "SELECT * FROM `new_banyan_tanaman` where id='$tanaman_id'";
        $queryy = mysqli_query($conn, $sqll);
        
        if($roww = mysqli_fetch_assoc($queryy)){
            $nama = $roww['nama'];
            $nama_latin = $roww['nama_latin'];
            $deskripsi = $roww['deskripsi'];
            $jenis = $roww['jenis'];
            $ketinggian = $roww['ketinggian'];
            $struktur_tanah = $roww['struktur_tanah'];
            $suhu = $roww['suhu'];
            $ph = $roww['ph'];
            $kelembapan = $roww['kelembapan'];
            $tekanan = $roww['tekanan'];
            $lahan = $roww['lahan'];
            $air = $roww['air'];
            $gambar = $roww['gambar'];
        }
        array_push($response,
        array(
            'id'                => $row['id'],
            'user_id'           => $row['user_id'],
            'tanaman_id'        => $row['tanaman_id'],
            'nama'              => $nama,
            'nama_latin'        => $nama_latin,
            'deskripsi'         => $deskripsi,
            'jenis'             => $jenis,
            'ketinggian'        => $ketinggian,
            'struktur_tanah'    => $struktur_tanah,
            'suhu'              => $suhu,
            'ph'                => $ph,
            'kelembapan'        => $kelembapan,
            'tekanan'           => $tekanan,
            'lahan'             => $lahan,
            'air'               => $air,
            'gambar'            => $gambar,
            'date'              => $row['date'],
            'jumlah'            => $jumlah,
            'success'           => true,
            'message'           => "success",
            )
            );
    }

    echo json_encode($response);
}

?>