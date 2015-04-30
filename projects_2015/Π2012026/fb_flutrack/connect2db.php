<?php

function db_connect()
{
/*
    $con = new mysqli(
    null, // host
    'root', // username
    '',     // password
    '', // database name
    null,
    '/cloudsql/cedar-defender-93011:flutrack'
    );
*/
  $con = new mysqli('localhost', 'root', '');
    if (!$con){
      return false;
    }
   return $con;
}

?>
