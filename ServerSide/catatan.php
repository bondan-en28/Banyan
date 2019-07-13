<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    $tanaman_id = $_POST['tanaman_id'];
    $user_id = $_POST['user_id'];
    $catatan = $_POST['catatan'];

    date_default_timezone_set('Asia/Jakarta');
    $date= date("Y-m-d H:i:s");

    $sql = "INSERT INTO new_banyan_catatan (tanaman_id, user_id, catatan, date) VALUES ('$tanaman_id', '$user_id', '$catatan', '$date')";

	$query = mysqli_query($conn, $sql);

	if ($query) {
        $result["success"] = true;
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
            
        }
    } else{
        $result["success"] = false;
        $result["message"] = "error query!";

        echo json_encode($result);
        mysqli_close($conn);

  }
?>
