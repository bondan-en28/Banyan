<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    
    $id = $_POST['id'];

    $sql = "DELETE FROM banyan_post WHERE id='$id' ";
	$query = mysqli_query($conn, $sql);

	if ($query) {
        $result["success"] = true;
        $result["message"] = "success";

    } else{
        $result["success"] = false;
        $result["message"] = "error query!";
        
    }
} else{
    $result["success"] = false;
    $result["message"] = "error connection!";
}

echo json_encode($result);
mysqli_close($conn);

?>
