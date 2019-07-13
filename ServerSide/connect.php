<?php
$server = "localhost";
$user = "id9485374_bondan_en28";
$password = "b4ny4N";
$nama_database = "id9485374_userdb";

$conn = mysqli_connect($server, $user, $password, $nama_database);

session_start();

mysqli_select_db($conn, 'id9485374_userdb');

if (!$conn) {
	die("Gagal terhubung dengan database". mysqli_connect_error());
}
?>