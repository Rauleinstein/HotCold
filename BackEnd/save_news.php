<?php 

$params = $_POST;

$mysqli = new mysqli('mysql.hostinger.es', 'u887840871_root', 'JEe3dIvLVO', 'u887840871_news');
if ($mysqli->connect_errno) {
    echo "Falló la conexión con MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}


$sql = sprintf("INSERT INTO `Noticias` (`id`, `title`, `description`, `link`, `guid`, `pubDate`) VALUES (NULL, '%s', '%s', '%s', '%s', '%s')", 
				$mysqli->real_escape_string($params['title']), 
				$mysqli->real_escape_string($params['description']), 
				$mysqli->real_escape_string($params['link']), 
				$mysqli->real_escape_string($params['guid']), 
				$mysqli->real_escape_string($params['pubDate'])
			);


if ($result = $mysqli->query($sql)) {
	echo "Éxito <br>";
} else {
	printf("Error: %s\n", $mysqli->error);
}
/*
$result = $mysqli->query("SELECT * FROM Noticias"); 
var_dump($result);

$json = $result->fetch_array();
echo json_encode($json); */