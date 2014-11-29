<?php 

$params = $_GET;


$mysqli = new mysqli('mysql.hostinger.es', 'u887840871_root', 'JEe3dIvLVO', 'u887840871_news');
if ($mysqli->connect_errno) {
    echo "Falló la conexión con MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}


$sql = sprintf("INSERT INTO `u887840871_news`.`%s` (`id`, `title`, `description`, `link`, `guid`, `pubDate`, `temperatura`, `latitud`, `longitud`) VALUES ( NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
				$mysqli->real_escape_string($params['tabla']), 
				$mysqli->real_escape_string($params['title']), 
				$mysqli->real_escape_string($params['description']), 
				$mysqli->real_escape_string($params['link']), 
				$mysqli->real_escape_string($params['guid']), 
				$mysqli->real_escape_string($params['pubDate'])
			);

if ($result = $mysqli->query($sql)) {
	echo "Éxito";
} else {
	printf("Error: %s\n", $mysqli->error);
}

