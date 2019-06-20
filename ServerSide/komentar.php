<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    $post_id = $_POST['post_id'];
    $user_id = $_POST['user_id'];
    $komentar = $_POST['komentar'];

    date_default_timezone_set('Asia/Jakarta');
    $date= date("Y-m-d H:i:s");

    $sql = "INSERT INTO banyan_komentar (post_id, user_id, komentar, date) VALUES ('$post_id', '$user_id', '$komentar', '$date')";

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
