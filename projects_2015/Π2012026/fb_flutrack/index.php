<?php
session_start();
//needs autoload from facebook php sdk!
include ('util.php');

use Facebook\FacebookSession;
use Facebook\FacebookRedirectLoginHelper;
use Facebook\FacebookRequest;
use Facebook\FacebookResponse;
use Facebook\FacebookSDKException;
use Facebook\FacebookRequestException;
use Facebook\FacebookAuthorizationException;
use Facebook\GraphObject;
use Facebook\Entities\AccessToken;
use Facebook\HttpClients\FacebookCurlHttpClient;
use Facebook\HttpClients\FacebookHttpable;


$conn=db_connect(); //establish connection

if (!$conn->select_db('fb_flutrack')){ //this way we create our database only when the select_db returns false
  setUpDB(); //creates and initializes our db
            //see init_db.php for more info
}


FacebookSession::setDefaultApplication('1573571739571921','c9741dd7b31d948c8d78f3876037f3a7');
$helper = new FacebookRedirectLoginHelper('http://localhost/fb_flutrack/index.php');



if ( !isset( $session ) || $session === null ) {
  // no session exists

  try {
    $session = $helper->getSessionFromRedirect();
  } catch( FacebookRequestException $ex ) {

  } catch( Exception $ex ) {

  }

}

// see if we have a session
if ( isset( $session ) ) {

  // save the session
  $_SESSION['fb_token'] = $session->getToken();
  // create a session using saved token or the new one we generated at login
  $session = new FacebookSession( $session->getToken() );

  // graph api request for user data
  // /me/statuses?fields=place,message,id,from,comments{from,id,created_time,message}
  $request = new FacebookRequest( $session, 'GET', '/me/statuses?fields=place,message,id,from' );
  $response = $request->execute();
  // get response
  $graphObject = $response->getGraphObject()->asArray();
  //preg_match in the place field
  $pattern = '/(hell|heaven|paradise|home|road|mother|fairytail|universe|mars|jupiter|moon|planet|galaxy|house|mom|mercury|venus|saturn|uranus|neptune|pluto|sun|solar|asteroid|comet)/i';
  $flu_pattern='/(flu|chills|sore|throat|headache|runny|nose|vomiting|sneazing|fever|diarrhea|dry|cough)/i';
/*
  no need for this yet
  $aggrav_pattern = '/(getting worse|get worse|weaker|deterioration|deteriorate|worsening|degenerate|regress|exacerbate|relapse|intensify|compound|Become aggravated|get into a decline|go to pot)/i';
*/
$counter=0;
  foreach($graphObject['data'] as $key) {
  //TODO check in comments for similar tweets but not from the original poster!
   if (isset($key->message) and isset($key->place->location->longitude) and isset($key->place->location->latitude)){
    if (!preg_match($pattern, $key->place->name) and preg_match($flu_pattern, $key->message)){//everything is ok!
    $valid_post = preg_replace('/(#|@)\S+/', '', $key->message); //remove hashtags
    $valid_post = preg_replace('#\b(([\w-]+://?|www[.])[^\s()<>]+(?:\([\w\d]+\)|([^[:punct:]\s]|/)))#', '', $valid_post);
    $counter++;
  //  echo   $key->from->name.' '.$valid_post.' '.$key->updated_time.'<br />';
submitPost($key->from->name,$valid_post,$key->id,$key->updated_time,$key->place->location->latitude,$key->place->location->longitude);

  }
}}
if ($counter===0){
//  echo "Nothing interesting to see here<br/>";
}
// echo '<pre>' . print_r( $graphObject, 1 ) . '</pre>';

  // print logout url using session and redirect_uri (logout.php page should destroy the session)
  echo '<META HTTP-EQUIV="Refresh" Content="0; URL='.$helper->getLogoutUrl( $session, 'http://localhost/fb_flutrack/logout.php' ).'">';

} else {
  echo '<p align="center"><a href="' . $helper->getLoginUrl( array( 'email', 'user_friends','user_posts') ) . '"><img src="fb_icon.gif"width="60" height="60"/></a></p><br/>';
  echo '<p align="center">Help us improve flutrack by proving us your Facebook status history.</p>';
}

function submitPost($user_name,$fb_text,$post_id,$post_date,$latitude, $longitude){
  $conn = db_connect();
  $conn->select_db('fb_flutrack');

  $msg = trim($fb_text);
  $msg =mysqli_real_escape_string($conn, $fb_text);
  $lat=(string)$latitude;
  $lon=(string)$longitude;





  $query="INSERT INTO flu_posts(user_name,fb_text,post_id,post_date,latitude,longitude) VALUES('$user_name','$fb_text','$post_id','$post_date','$latitude','$longitude')";

  $result=$conn->query($query);
  if (!$result) {
  //  die('Invalid query from submitPost');
  }
  $conn->close();
}

?>
