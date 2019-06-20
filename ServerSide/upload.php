<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){

    $id = $_POST['id'];
    $photo = $_POST['foto'];

    $path = "foto_profil/$id.jpeg";
    $finalPath = "http://bonbon28.000webhostapp.com/banyan/".$path;

    $sql = "UPDATE banyan_pengguna SET photo='$finalPath' WHERE id='$id'";
    $query = mysqli_query($conn, $sql);

    if ($query) {

        if(file_put_contents($path, base64_decode($photo))){
            $result["success"] = "1";
            $result["message"] = "success";

            echo json_encode($result);
            mysqli_close($conn);    
        }


    } else{
        echo "ERRORR";
        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
?>