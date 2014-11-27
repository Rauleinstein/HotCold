<?php 

$params = $_POST;

$mysqli = new mysqli('mysql.hostinger.es', 'u887840871_root', 'JEe3dIvLVO', 'u887840871_news');
if ($mysqli->connect_errno) {
    echo "Falló la conexión con MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}

$sql = sprintf("SELECT * FROM %s", $_POST['tabla']);

$result = $mysqli->query($sql); 
if (!$result) {
	 printf("Error: %s\n", $mysqli->error);
	 die;
}

while($row = $result->fetch_array(MYSQLI_ASSOC))
{
	$rows[] = $row;
}

echo json_encode($rows);