
<?php

include ('util.php');


$response = array();
$con = db_connect();
$con->select_db('fb_flutrack');


$query="SELECT * FROM flu_posts";
$res=$con->query($query);


if ($res->num_rows> 0) {
       $rows= array();
       $rows= array();
    while ($r=  $res->fetch_assoc()) {
      $rows[] = $r;
    }
    echo json_encode($rows);
    }
     else {
  echo "Empty table";
}

?>
