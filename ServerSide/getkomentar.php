<?php
header("Content-type:application/json");

include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    $post_id = $_POST['post_id'];

    $sql = "SELECT * FROM `new_banyan_komentar` where post_id='$post_id' ORDER BY id";

	$query = mysqli_query($conn, $sql);

	if ($query) {

        $response = array();

        while($row = mysqli_fetch_assoc($query)){
        
            $user_id = $row['user_id'];
        
            $sqll = "SELECT nama FROM `new_banyan_pengguna` where id='$user_id'";
            $queryy = mysqli_query($conn, $sqll);
            
            if($roww = mysqli_fetch_assoc($queryy)){
                $user_name = $roww['nama'];
            }
            
            array_push($response,
            array(
                'id'        => $row['id'],
                'post_id'      => $row['post_id'],
                'user_id'     => $user_name,
                'komentar'     => $row['komentar'],
                'date'      => $row['date'])
                );
        }
        
        echo json_encode($response);
        }
    }
?>
