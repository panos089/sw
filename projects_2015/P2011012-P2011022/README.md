
<h2>Final Report</h2>


<h3>Twitter and Google News based D3.js visualization on political events</h3>

[Online](http://83.212.97.224:8080/nodenews/frontend/)


[My repository](https://github.com/skid648/Twitter-and-google-news-based-visualization)

<h4>Summary</h4>

A force layout visualization on current "Hot" events. Data source will be twitter search content based on Google news headlines.
The nodes will represent each entity's political origin ,the entity itself and the tweets posted for it respectively. The entire 
visualization is in realtime and it is also storing it's snapshots periodically. That said the user will be able to scroll through days and hours and construct the news diagram through time.


<h4>Introduction</h4>

In the last few years a necessity has been shown up in internet's world. That is the need to represent and organize all this information in any way possible. A visualization of a dataset is not purposed only to explore new patterns but it is also usefull for organizing raw data in a more structured way. That is the main but not the only reason this application was developed for.

<h4>Tools</h4>
<ul>
<li>Server & database</li>
  <ul>
  <li>phpMyAdmin</li>
  <li>XAMPP</li> 
  <li>MAMPP</li>
  <li>Apache server</li>
  <li>MySQL database</li>
  </ul>
<li>Text Editors</li>
<ul>
  <li>Sublime Text 2 </li>
  <li>Notepad++</li>
  </ul>
<li>Programming languages & libraries</li>
<ul>
  <li>PHP</li>
  <li>Javascript</li>
  <li>HTML5</li>
  <li>SQL  </li>
  <li>CSS</li>
  <li>[D3.js Javascript Library](http://d3js.org/)</li>
  </ul>
<li>Browser</li>
<ul>
  <li>Google Chrome</li>
  </ul>
<li>Other</li>
<ul>
<li>[Google News RSS for Political News](https://news.google.gr/news?pz=1&cf=all&ned=el_gr&hl=el&topic=p&output=rss/)</li> 
<li>[Twitter Search API](https://dev.twitter.com/rest/public/search)</li>
<li>[CodePen](http://codepen.io/)</li>
  </ul>
</ul>

<h4>Development Process </h4>
<ul>
<li>The first stage of develepment was application's backbone. Backend had split up to an rss reader and a twitter search collector script. Google news rss had been parsed and we filtred the current elected deputies with the "hottest" news this hour based on Google. For every Deputy that we found the twitter script was called with his name in the search query. All the data was being stored in json format and orgnized in a directory based on hours and the date that it was pulled.
That is the basic structure of app's backend. In Addition to this a cronjob was set to run the backend script every hour.</li>

<li>The second stage was the actual visualization. We developed a client-side(Javascript) graph node based on d3's force layout. The graph node uses .json files to create the nodes and the links that connect them. A basic node is in the center connected to political parties ending up in Deputies nodes. These leaf nodes are clickable, constructing a new graph with the deputy for the center and the all the tweets connected to them. At this point we had to find a way for the graph to constuct through time. We use a two dimension area with a draggable circle to move through days an hours simultaneously.</li>

<li>In the third stage of development we tried to eliminate any bug that came up either from the raw data, the backend , or the visualization itself. We came to realize that the interaction with the draggable circle was contradictory to application desired simplicity so we developed a [drop-down calendar](http://codepen.io/skid648/pen/QbGYKX) to chose a timestamp.</li>

<li>All these came to a final design with a free template and a responsive mobile-friendly version of the website.</li>
</ul>
<h4>Application Diagram</h4>

![Diagram](https://raw.githubusercontent.com/courses-ionio/sw/master/projects_2015/P2011012-P2011022/imgs/diagram.png)

<h4>Screenshots</h4>

![Main](https://raw.githubusercontent.com/courses-ionio/sw/master/projects_2015/P2011012-P2011022/imgs/screenshot1.png)

![Secondary](https://raw.githubusercontent.com/courses-ionio/sw/master/projects_2015/P2011012-P2011022/imgs/Screenshot2.png)

<h4>Conlusions</h4>

After a few days of using and trying to improve the application in the same time, we finally achieved to collect some data about the last days.
We have to emphasise that a lot of old collected data were deleted while we were trying to find the best form of storage in order to use them considering our expectations. Any further research or additional feature is prior to first observe the data that we collect in a long period.

