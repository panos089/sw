<?php

function db_connect()
{

    $con = new mysqli(
    null, // host
    'root', // username
    '',     // password
    '', // database name
    null,
    ''
    );

    if (!$con){
      return false;
    }
   return $con;
}

?>
