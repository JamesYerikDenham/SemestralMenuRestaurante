<?php
header("Content-Type: application/json");
$conn = new mysqli("localhost", "root", "", "miapp");

if ($conn->connect_error) {
    echo json_encode(["error" => "Conexión fallida"]);
    exit;
}

if (isset($_GET["usuario_id"])) {
    $usuario_id = $_GET["usuario_id"];
    $result = $conn->query("SELECT pedidos.id, platos.nombre, platos.precio, pedidos.fecha, pedidos.estado
                            FROM pedidos
                            JOIN platos ON pedidos.plato_id = platos.id
                            WHERE pedidos.usuario_id = $usuario_id");

    $pedidos = [];
    while ($row = $result->fetch_assoc()) {
        $pedidos[] = $row;
    }

    echo json_encode($pedidos);
} else {
    echo json_encode(["error" => "usuario_id no proporcionado"]);
}

$conn->close();
?>