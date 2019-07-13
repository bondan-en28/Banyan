<?php
header("Content-type:application/json");

include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){
    $tanaman_id = $_POST['tanaman_id'];

    $sql = "SELECT * FROM `new_banyan_catatan` where tanaman_id='$tanaman_id' ORDER BY id";

	$query = mysqli_query($conn, $sql);

	if ($query) {

        $response = array();

        while($row = mysqli_fetch_assoc($query)){
        
            $user_id = $row['user_id'];
        
            $sqll = "SELECT nama FROM `banyan_pengguna` where id='$user_id'";
            $queryy = mysqli_query($conn, $sqll);
            
            $resultt = array();
            $resultt['read'] = array();
        
            if($roww = mysqli_fetch_assoc($queryy)){
                $user_name = $roww['nama'];
            }
            
            array_push($response,
            array(
                'id'        => $row['id'],
                'tanaman_id'      => $row['tanaman_id'],
                'user_id'     => $user_name,
                'catatan'     => $row['catatan'],
                'date'      => $row['date'])
                );
        }
        
        echo json_encode($response);
        }
    }
?>
