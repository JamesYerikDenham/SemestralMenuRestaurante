<?php
// 0. Registrar que el script se ejecutó
file_put_contents("debug.txt", "registro.php ejecutado\n", FILE_APPEND);

header("Content-Type: application/json");

// 1. Leer el JSON del cuerpo
$raw = file_get_contents("php://input");
file_put_contents("debug.txt", "RAW: " . $raw . "\n", FILE_APPEND);

$data = json_decode($raw, true);
file_put_contents("debug.txt", print_r($data, true), FILE_APPEND);


// 2. Guardar para depuración
file_put_contents("debug.txt", print_r($data, true), FILE_APPEND);

// 3. Verificar que se reciban todos los campos requeridos
if (isset($data['nombre'], $data['correo']) && (isset($data['contrasena']) || isset($data['password']))) {
    $nombre = $data['nombre'];
    $correo = $data['correo'];
    $contrasenaPlano = $data['contrasena'] ?? $data['password']; // toma el campo disponible
    $contrasena = password_hash($contrasenaPlano, PASSWORD_DEFAULT);

    // 4. Conectar a la base de datos
    $conn = new mysqli("localhost", "root", "", "miapp");

    if ($conn->connect_error) {
        echo json_encode(["mensaje" => "Error de conexión: " . $conn->connect_error]);
        exit;
    }

    // 5. Verificar si el correo ya existe
    $checkQuery = $conn->prepare("SELECT id FROM usuarios WHERE correo = ?");
    $checkQuery->bind_param("s", $correo);
    $checkQuery->execute();
    $checkQuery->store_result();

    if ($checkQuery->num_rows > 0) {
        echo json_encode(["mensaje" => "Correo ya registrado"]);
        $checkQuery->close();
        $conn->close();
        exit;
    }
    $checkQuery->close();

    // 6. Insertar nuevo usuario
    $stmt = $conn->prepare("INSERT INTO usuarios (nombre, correo, contrasena) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $nombre, $correo, $contrasena);

    if ($stmt->execute()) {
        echo json_encode(["mensaje" => "Registro exitoso"]);
    } else {
        echo json_encode(["mensaje" => "Error al registrar: " . $stmt->error]);
    }

    $stmt->close();
    $conn->close();
} else {
    echo json_encode(["mensaje" => "Datos incompletos"]);
}
?>
