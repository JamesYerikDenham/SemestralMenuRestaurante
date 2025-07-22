<?php
$host = "localhost";
$user = "root";
$pass = "";
$db = "miapp";

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

error_log("🟢 Conectado a la base de datos '$db'");
?>