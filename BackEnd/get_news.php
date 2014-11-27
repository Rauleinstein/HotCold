<?php 

$params = $_POST;

$mysqli = new mysqli('mysql.hostinger.es', 'u887840871_root', 'JEe3dIvLVO', 'u887840871_news');
if ($mysqli->connect_errno) {
    echo "Falló la conexión con MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}

$sql = sprintf("SELECT * FROM %s", $_POST['tabla']);

$result = $mysqli->query("SELECT * FROM "); 
var_dump($result);

$json = $result->fetch_array();
echo json_encode($json); 