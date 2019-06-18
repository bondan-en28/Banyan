<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){

    $name = $_POST['name'];
    $email = $_POST['email'];
    $id = $_POST['id'];
    $ttl = $_POST['ttl'];
    $alamat = $_POST['alamat'];
    $notelp = $_POST['notelp'];
    
    require_once 'connect.php';
    $tgl = date('Y-m-d H:i:s', strtotime($ttl));
    
    $sql = "UPDATE banyan_pengguna SET nama='$name', email='$email', ttl='$tgl', alamat='$alamat', notelp='$notelp' WHERE id='$id'";

    $query = mysqli_query($conn, $sql);
    
    if ($query) {
        $result["success"] = "1";
        $result["message"] = "success";
        echo json_encode($result);
        mysqli_close($conn);    
    } else{
        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
?>