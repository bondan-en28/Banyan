<?php
include ("connect.php");

if($_SERVER['REQUEST_METHOD']=='POST'){

    $email = $_POST['email'];
    $password = $_POST['password'];

    require_once 'connect.php';

    $sql = "SELECT * FROM banyan_pengguna WHERE email='$email'";
    $query = mysqli_query($conn, $sql);
    
    $result = array();
    $result['login'] = array();

	if (mysqli_num_rows($query) === 1 ) {

        $row = mysqli_fetch_assoc($query);

        if(password_verify($password, $row['password'])){

            $index['name'] = $row['nama'];
            $index['email'] = $row['email'];
            $index['ttl'] = $row['ttl'];
            $index['alamat'] = $row['alamat'];
            $index['notelp'] = $row['notelp'];
            
            $index['id'] = $row['id'];
            

            array_push($result['login'], $index);
            
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
    } else{
        $result["success"] = "0";
        $result["message"] = "User tidak ditemukan!";
            
        echo json_encode($result);
        mysqli_close($conn);
    }
}
