<?php
include ('util.php');




//$string='[{"user_name":"Korakakis Korakas","user_id":"429310450566551","msg":"sore throat","post_id":"429310450566551_447622125402050","date":"2015-04-25T14:59:29+0000","latitude":"39.62","longitude":"19.9197"},{"user_name":"Korakakis Korakas","user_id":"429310450566551","msg":"very sick with the flu","post_id":"429310450566551_447621905402072","date":"2015-04-25T14:46:13+0000","latitude":"37.9529805621","longitude":"23.7463655808"}]';

$con = db_connect();
if (!$con->select_db('fb_flutrack')){ 
  setUpDB();
}

$con->select_db('fb_flutrack');
if (isset($_POST['json'])){
$json=json_decode($_POST['json']);
//$json=json_decode($string);
foreach($json as $p)
{

  $query ="INSERT INTO flu_posts(user_name,fb_text,post_id,post_date,latitude,longitude) VALUES('$p->user_name','$p->msg','$p->post_id','$p->date','$p->latitude','$p->longitude')";
  $result = $con->query($query);
  if ($result) {
      $response["success"] = 1;
      $response["message"] = "success";
      echo json_encode($response);
  } else {
      $response["success"] = 0;
      $response["message"] = "failure";
      echo json_encode($response);
  }
}
}
else {
  $response["success"] = 0;
  $response["message"] = "something is missing";
  echo json_encode($response);
}



?>
