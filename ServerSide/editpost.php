<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    $id = $_POST['id'];
    $judul = $_POST['judul'];
    $deskripsi = $_POST['deskripsi'];
    $gambar = $_POST['gambar'];
    
    $sqll = "SELECT user_id, date FROM `new_banyan_post` where id='$id'";
    $queryy = mysqli_query($conn, $sqll);
    
    $resultt = array();
    $resultt['read'] = array();

    if($roww = mysqli_fetch_assoc($queryy)){
        $user_id = $roww['user_id'];
        $date = $roww['date'];
    }
    
    $directory = "foto_post/";
    $hapus = unlink($directory.$_GET["$user_id.$date.jpeg"]);

    $date= date("Y-m-d H:i:s");
    date_default_timezone_set('Asia/Jakarta');

    $path = "foto_post/$user_id.$date.jpeg";
    $finalpath = "http://bonbon28.000webhostapp.com/banyan/".$path;

    $sql = "UPDATE new_banyan_post SET judul='$judul', deskripsi='$deskripsi', gambar='$finalpath' WHERE id='$id' ";

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
