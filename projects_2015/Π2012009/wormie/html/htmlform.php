<?php
	function html_top( $settings, $pagetitle, $active = 0 ) { ?>
		<!DOCTYPE HTML PUBLIC "-//W3C//DfgfhTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html lang="en" >
			<head>
				<meta charset="utf-8" />
				<title><?php
					echo $settings['title']; 
					if ( strlen( $settings['subtitle'] ) > 0 ) { echo " | " . $settings['subtitle']; } ?>
				</title>
				<link href="css/style.css" rel="stylesheet" type="text/css" />
				<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
				<script src="js/script.js"></script>
			</head>
			<body>
				<header>
					<h2>The Wormie Game</h2>
				</header>
			   <div class="container">
					<div id='cssmenu'>
					<ul> 
					   <li <?php if ( $active == 0 ) echo 'class="active"'; ?>><a href='index.php'><span>Home</span></a></li>
					</ul>
					</div>
					<div class="main">
						<div class="text">
							<?php //echo '<div class="pagetitle">' . $pagetitle . '</div>';
							echo '<div class="pagetitle">' . $pagetitle . '</div><hr class="pagetitle"></hr>';
	}						
	function html_bot( $settings  ) { ?>
							
						</div>
					</div>
					
				</div>
			</body>
			<footer>
				<div class="table">
					<div class="tr">
						<div class="d1">&nbsp;&nbsp;&copy; www.HarryGSn.eu</div>
						<div class="d3">Developed by <?php echo $settings['developers']; ?>&nbsp;&nbsp;</div>
					</div>
				</div>
			</footer>
		</html><?php
	}
?>