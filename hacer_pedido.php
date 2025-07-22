<?php
header("Content-Type: application/json");
$raw = file_get_contents("php://input");
$data = json_decode($raw, true);

$conn = new mysqli("localhost", "root", "", "miapp");

if ($conn->connect_error) {
    echo json_encode(["mensaje" => "Error de conexión"]);
    exit;
}

if (isset($data["usuario_id"], $data["plato_id"])) {
    $stmt = $conn->prepare("INSERT INTO pedidos (usuario_id, plato_id) VALUES (?, ?)");
    $stmt->bind_param("ii", $data["usuario_id"], $data["plato_id"]);

    if ($stmt->execute()) {
        echo json_encode(["mensaje" => "Pedido registrado"]);
    } else {
        echo json_encode(["mensaje" => "Error al registrar pedido"]);
    }

    $stmt->close();
} else {
    echo json_encode(["mensaje" => "Datos incompletos"]);
}

$conn->close();
?>