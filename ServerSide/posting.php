<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    $user_id = $_POST['user_id'];
    $judul = $_POST['judul'];
    $deskripsi = $_POST['deskripsi'];
    $gambar = $_POST['gambar'];

    date_default_timezone_set('Asia/Jakarta');
    $date= date("Y-m-d H:i:s");

    $path = "foto_post/$user_id.$date.jpeg";
    $finalPath = "http://bonbon28.000webhostapp.com/banyan/".$path;

    $sql = "INSERT INTO new_banyan_post (user_id, judul, deskripsi, gambar, date) VALUES ('$user_id', '$judul', '$deskripsi', '$finalPath', '$date')";

	$query = mysqli_query($conn, $sql);

	if ($query) {
        if(file_put_contents($path, base64_decode($gambar))){
            $result["success"] = true;
            $result["message"] = "success";

            echo json_encode($result);
            mysqli_close($conn);
        } else{
        $result["success"] = false;
        $result["message"] = "error decoding image!";

        echo json_encode($result);
        mysqli_close($conn);
            
        }
    } else{
        $result["success"] = false;
        $result["message"] = "error query!";

        echo json_encode($result);
        mysqli_close($conn);

  }
}
?>
