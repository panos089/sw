<?php 
	include_once( 'html/htmlform.php' );
	include_once( 'settings.php' );
	
	html_top( $settings, "The Wormie Game!", 0 );
	echo '<center>' .  file_get_contents( "wormie.html" ). '</center>';
	html_bot( $settings );
	
	
?>