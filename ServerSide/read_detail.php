<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){

    $id = $_POST['id'];
    
    require_once 'connect.php';

    $sql = "SELECT * FROM banyan_pengguna WHERE id='$id'";
    $query = mysqli_query($conn, $sql);
    
    $result = array();
    $result['read'] = array();

    if (mysqli_num_rows($query) === 1 ) {


        if($row = mysqli_fetch_assoc($query)){
        
            $index['name'] = $row['nama'];
            $index['email'] = $row['email'];
            $index['photo'] = $row['photo'];
            $index['ttl'] = $row['ttl'];
            $index['alamat'] = $row['alamat'];
            $index['notelp'] = $row['notelp'];

            array_push($result['read'], $index);
            
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

}
?>