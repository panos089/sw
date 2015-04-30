<?php
include ('util.php');

function setUpDB(){

$conn=db_connect();

$query= "CREATE DATABASE fb_flutrack";
$result=$conn->query($query);
if (!$result) {
    die('Invalid query');
}

$conn->select_db('fb_flutrack');

$query = "CREATE TABLE flu_posts (
    id INT auto_increment,
    user_name VARCHAR(20),
    fb_text VARCHAR(140),
    post_id VARCHAR(20),
    post_date VARCHAR(20),
    latitude DECIMAL(10,6) NOT NULL,
    longitude DECIMAL(10,6) NOT NULL,
    primary key(id))";


$result=$conn->query($query);
if (!$result) {
    die('Invalid query');
}
$query="ALTER IGNORE TABLE flu_posts ADD UNIQUE INDEX tmp (user_name,fb_text,post_id,post_date,latitude,longitude)";


$result=$conn->query($query);
if (!$result) {
    die('Invalid query');
}

$conn->close();
}

?>
