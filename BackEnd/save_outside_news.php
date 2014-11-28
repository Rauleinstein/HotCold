<?php


$path[] = "http://www.elmundo.es/rss/hackathon/ciencia.xml";
$path[] = "http://www.elmundo.es/rss/hackathon/espana.xml";
$path[] = "http://www.elmundo.es/rss/hackathon/economia.xml";
$path[] = "http://www.elmundo.es/rss/hackathon/internacional.xml";
foreach ($path as $rss) {
	$archivo = fopen($rss, 'r');
	stream_set_blocking($archivo,true);
	$datos = stream_get_contents($archivo);
	fclose($archivo);


	$noticias = simplexml_load_string($datos);

	$noticias = $noticias->channel;

	// Conectando, seleccionando la base de datos
	$mysqli = new mysqli('mysql.hostinger.es', 'u887840871_root', 'JEe3dIvLVO', 'u887840871_news');
	if ($mysqli->connect_errno) {
	    echo "Falló la conexión con MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
	}

	foreach ($noticias->item as $noticia) 
	{
		$i++;
		$title = $noticia->title;
		$description = $noticia->description;
		$link = $noticia->link;
		$guid = $noticia->guid;
		$pubDate = $noticia->pubDate;

		$query = "INSERT INTO Noticias (id, title, description, link, guid, pubDate) VALUES(NULL, ?, ?, ?, ?, ?)";
		$statement = $mysqli->prepare($query);
		if ( false===$statement ) {
			die('prepare() failed: ' . htmlspecialchars($mysqli->error));
		}
		$ret = $statement->bind_param('sssss', $title, $description, $link, $guid, $pubDate);

		if($statement->execute()){
		    print 'Success! ID of last inserted record is : ' .$statement->insert_id .'<br />'; 
		}else{
		    die('Error : ('. $mysqli->errno .') '. $mysqli->error);
		}

	}

	$result = $mysqli->query("SELECT * FROM  `Noticias`");

	$row = $result->fetch_array(MYSQLI_ASSOC);
	printf ("%s (%s)\n", $row["title"], $row["pubDate"]);
}