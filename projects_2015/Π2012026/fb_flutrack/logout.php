<?php

session_start();
session_destroy();


header( 'Location: http://localhost/fb_flutrack/index.php' );
die();
?>
