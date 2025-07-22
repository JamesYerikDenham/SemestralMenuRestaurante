<?php
// Mostrar errores durante desarrollo (¡desactiva esto en producción!)
ini_set('display_errors', 1);
ini_set('log_errors', 1);
error_reporting(E_ALL);
error_log("==== login.php fue accedido ====");

// Encabezado JSON
header("Content-Type: application/json");

// Conexión a la base de datos
include 'conexion.php';

// Leer y decodificar el cuerpo JSON
$raw_input = file_get_contents("php://input");
$data = json_decode($raw_input, true);
error_log("📥 Datos recibidos: " . $raw_input);

// Validar campos
if (!isset($data['correo']) || !isset($data['contrasena'])) {
    error_log("❌ Datos incompletos");
    echo json_encode(["error" => "Datos incompletos"]);
    exit;
}

$correo = $data['correo'];
$contrasena = $data['contrasena'];

// Buscar usuario por correo
$stmt = $conn->prepare("SELECT id, nombre, correo, contrasena FROM usuarios WHERE correo = ?");
if (!$stmt) {
    error_log("❌ Error al preparar consulta: " . $conn->error);
    echo json_encode(["error" => "Error en el servidor"]);
    exit;
}

$stmt->bind_param("s", $correo);
$stmt->execute();
$result = $stmt->get_result();

// Verificar usuario y contraseña
if ($result->num_rows > 0) {
    $usuario = $result->fetch_assoc();

    if (password_verify($contrasena, $usuario['contrasena'])) {
        // Éxito: eliminar contraseña antes de enviar
        unset($usuario['contrasena']);
        echo json_encode([$usuario]);  // Envolvemos en array para Retrofit
        error_log("✅ Login exitoso: $correo");
    } else {
        echo json_encode([]);  // Contraseña incorrecta
        error_log("❌ Contraseña incorrecta para $correo");
    }
} else {
    echo json_encode([]);  // Usuario no encontrado
    error_log("❌ Usuario no encontrado: $correo");
}

$stmt->close();
$conn->close();
?>
