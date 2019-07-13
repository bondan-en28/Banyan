<?php

header("Content-type:application/json");

require_once('connect.php');

if($_SERVER['REQUEST_METHOD']=='POST'){

    $user_id = $_POST['user_id'];

    $query = mysqli_query($conn, "SELECT * FROM `new_banyan_post` WHERE user_id='$user_id' ORDER BY id DESC");

    $response = array();

    while($row = mysqli_fetch_assoc($query)){

        $user_id = $row['user_id'];

        $sqll = "SELECT nama, photo FROM `new_banyan_pengguna` where id='$user_id'";
        $queryy = mysqli_query($conn, $sqll);
        
        $resultt = array();
        $resultt['read'] = array();

        if($roww = mysqli_fetch_assoc($queryy)){
            $user_name = $roww['nama'];
            $user_image = $roww['photo'];
        }
        
        array_push($response,
        array(
            'id'        => $row['id'],
            'user_id'     => $user_name,
            'judul'      => $row['judul'],
            'deskripsi'     => $row['deskripsi'],
            'gambar'      => $row['gambar'],
            'date'      => $row['date'],
            'user_image'     => $user_image)
            );
    }

    echo json_encode($response);
}
?>