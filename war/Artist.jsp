<!doctype html>
<!-- The DOCTYPE declaration above will set the -->
<!-- browser's rendering engine into -->
<!-- "Standards Mode". Replacing this declaration -->
<!-- with a "Quirks Mode" doctype is not supported. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<link rel="stylesheet" type="text/css" media="all" href="fonts.css" />
<!--[if IE]>
	<link rel="stylesheet" type="text/css" media="all" href="ie-fonts.css" />
<![endif]-->

<!-- FONTS -->
<link href='http://fonts.googleapis.com/css?family=Jolly+Lodger'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Share+Tech|PT+Sans+Narrow:400,700'
	rel='stylesheet' type='text/css'>

<!-- jQuery-->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<!-- jQuery UI-->
<script src="jQ/js/jquery-ui-1.9.0.custom.min.js"></script>
<link rel="stylesheet"
	href="jQ/css/custom-theme/jquery-ui-1.9.0.custom.css" type="text/css"
	media="screen" />

<!-- FANCY BOX -->
<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript"
	src="fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<!-- Add fancyBox -->
<link rel="stylesheet"
	href="fancybox/source/jquery.fancybox.css?v=2.0.6" type="text/css"
	media="screen" />
<script type="text/javascript"
	src="fancybox/source/jquery.fancybox.pack.js?v=2.0.6"></script>
<!-- Optionally add helpers - button, thumbnail and/or media
            -->
<link rel="stylesheet"
	href="fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.2"
	type="text/css" media="screen" />
<script type="text/javascript"
	src="fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.2"></script>
<script type="text/javascript"
	src="fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.0"></script>
<link rel="stylesheet"
	href="fancybox/source/helpers/jquery.fancybox-thumbs.css?v=2.0.6"
	type="text/css" media="screen" />
<script type="text/javascript"
	src="fancybox/source/helpers/jquery.fancybox-thumbs.js?v=2.0.6"></script>

<!--  MOSAIC -->
<link rel="stylesheet" href="mosaic/css/mosaic.css" type="text/css"
	media="screen" />
<script type="text/javascript" src="mosaic/js/mosaic.1.0.1.min.js"></script>

<!--  TWITTER BOOTSTRAP DONT GET FROM CDN USING FOR DIFFERING TOOLTIP LOCATIONS-->
<script src="twbootstrap/js/bootstrap.js"></script>
<link href="twbootstrap/css/bootstrap.css" rel="stylesheet">

<!--  TO TOP -->
<script src="jqTop/js/jquery.ui.totop.js"></script>
<link href="jqTop/css/ui.totop.css" rel="stylesheet">

<!--  jQ cookie -->
<script src="jQcookie/jquery.cookie.js"></script>

<!-- BFI CSS -->
<!-- <link rel="stylesheet" type="text/css" href="Artist.css" media="all"> -->
<link rel="stylesheet" type="text/css" href="Artist_screen.css"
	media="screen" />


<!-- ==================================================================================================================================== -->
<script type="text/javascript">


	function installStuff() {
		makeFancyBoxey();
		$('.thumbnail').css('cursor', 'pointer');
		$('.bar2').mosaic({
			animation : 'slide',
			// 									 speed : 150,
			opacity : 0,
// 			preload : 1,
			anchor_x : 'bottom',
			// 									 anchor_y : 'left',
			// 									 hover_x : '0px',
			hover_y : '1px'
		});

	}
	function makeFancyBoxey() {
		$(".fancybox").fancybox({
			
			
			'transitionIn'        :        'elastic', 
            'transitionOut'        :        'elastic', 
            'cyclic'                :        true, 
            'easingIn'                :        'easeInOutQuad', 
            'easingOut'                :        'easeInOutQuad', 
            'speedIn'                :        300, 
            'speedOut'                :        300, 
            
			openEffect : 'elastic',
			closeEffect : 'elastic',
			padding : 0,
			width : '640',
			height : '385',
			hideOnOverlayClick : 'false',
			hideOnContentClick : 'false',
			// 								overlayShow: false,
			// 								'showNavArrows'   : false,  
			youtube : {
				'autoplay' : 1, // 1 = will enable autoplay
				wmode : 'transparent'
			},

			helpers : {
				media : {}
			}
		});
	}
	
	var contentHeight = 0;
	var firstTime = true;
	var divExpanded = false;
	
	function logMixpanelVideo(evt){
		mixpanel.track('Video', {
		    'videoTitle': evt.innerText
		});
	}
	
	function makeAccordion() {
		
		$("#accordion").accordion(
				{
					autoHeight : true,
					animated: 'bounceslide', 
					collapsible : true,
					active : false,
					changestart : function(e, ui) {	
						var videoCount = $(ui.newHeader[0]).children('a').children('span.videoCount').text().replace(/[^0-9]/g, '');
						var content = $(ui.newHeader[0]).next();
						if(videoCount >=9){//3 rows
							content.height('555px');								
						}else if(videoCount >=5){//2 rows
							content.height('420px');								
						}else if(videoCount >=1){//1 row
							content.height('235px');								
						}else{//no videos
							content.height('145px');		
						}
						var moreButton = $(ui.newHeader[0]).next().children('.eventFooter').children('.moreButton');
						var populated = ui.newContent.hasClass('populated');
						if (!populated && ui.newContent.length > 0) {
							$.get('/videos', {
								action : "open",
								artistTerms : $('#artistTerms').val(),
								tag : ui.newHeader.children('a').children(
										"input[name=tag]").val(),
								feedURL : ui.newHeader.children('a').children(
										"input[name=feedURL]").val(),
								eventID : ui.newHeader.children('a').children(
										"input[name=eventID]").val(),
								date : ui.newHeader.children('a').children(
										"input[name=date]").val(),
								screenWidth : screen.width
							},

							function(data) {
								if(data.indexOf('noMoreVideos') == -1){
									var thisVidDiv = $(ui.newHeader[0]).next().children(':first-child');
									thisVidDiv.hide();
									thisVidDiv.children('.vidzContainer').html(data), 'html';
									formatVideos(thisVidDiv);
									thisVidDiv.fadeIn('slow');
									thisVidDiv.parents('.videoContent').height(thisVidDiv.height()+thisVidDiv.next());
									installStuff();
									moreButton.button();
									moreButton.click(function() {
										if(!$(this).hasClass('disabled')){
											moreButton.hide();
											getMoreVideos(this);
										}
									});
									moreButton.delay(1000).fadeIn("slow");
									$(this).popover('show');
								}else{
									//no videos at all for event
// 									$(ui.newHeader[0]).next().animate({'height':'100%'});
									var noVidsDiv = $('#noVideosAlert');
									$(ui.newHeader[0]).next().children(
										':first-child').html(noVidsDiv), 'html';
									$('#noVideosAlert').show();
								}
								ui.newContent.addClass('populated');
							});
						}
					}
				});

	}
	
	function formatVideos(thisVidDiv){
		var dimensions = getDimensions(screen.width);
		thisVidDiv.find('.mosaic-backdrop, .mosaic-block').css({
		    'height': dimensions[0],
		    'width': dimensions[1]
		});
		thisVidDiv.find('img').css({
		    'height': '100%',
		    'width': '100%'
		});
	}
	
	function getDimensions(screenWidth){
			var width = 0;
			var height = 0;
			if(screenWidth <= 500){
				height=70;
				width = 140;
			}
			if(screenWidth <= 1500){
				height=140;
				width = 260;
			}
			else{
				height=175;
				width = 325;
			}
			return new Array(height,width);
		}
	
	function getMoreVideos(button) {
		$.get('/videos', {
			action : "more",
			artistTerms : $('#artistTerms').val(),
			eventID : $(button).parent().parent().prev().children('a').children(
					"input[name=eventID]").val(),
			videoCount : $(button).parent().parent().children('.vidz').children(
					".vidzContainer").children('.video').length,
			feedURL : $(button).parent().parent().prev().children('a')
					.children("input[name=feedURL]").val(),
			tag : $(button).parent().parent().prev().children('a').children(
					"input[name=tag]").val(),
			date : $(button).parent().parent().prev().children('a').children(
					"input[name=date]").val(),
		}, function(data) {
			if (data.indexOf('noMoreVideos') == -1) {
				var thisVidDiv = $(button).parent().prev();
				thisVidDiv.children('.vidzContainer').append(data), 'html';
				formatVideos(thisVidDiv);
				installStuff();
				//reposition more button
				$('.eventFooter').css('width', '0px');
				$('.eventFooter').css('margin', '0 auto');
			} else {
				//no more videos message
				$(button).show();
				$(button).addClass('disabled');
				$(button).popover('show');
			}
		}).done(function() { 
			$(button).fadeIn("slow");
		});;
	}
	
	$(document).click(function(event){
		$('.moreButton').popover('destroy');
	});

	var searchTerms = ${searchJson};
	var termsArray = new Array();
	var termsArrayStrings = new Array();

	for ( var i = 0; i < searchTerms.length; i++) {
		termsArray.push(
		{
			label : searchTerms[i].val0,
			value : searchTerms[i].val1,
			category : searchTerms[i].val2
		}
		);
		termsArrayStrings.push(searchTerms[i].val0);
	}

	var i = 0;

	$(function() {

		$('#upcomingAlert').show();
		mixpanel.track('Visit', {
		});
		
		$('.evt').click(function(){
			mixpanel.track('Event', {
			});
		});
		
		var searchBox = $('#search');
		searchBox.typeahead({
			source: termsArrayStrings,
			items: 8,
		  });

		searchBox.change(function(){
		    var searchTerm = $(this).val();
		    var searchTermObject = $(termsArray).filter(function(){
		        return this.label == searchTerm;
		    });
		    //nothing was selected
		    if(searchTermObject.length==0){
		    	var noEventsDiv = $('#nothingSelectedAlert');
				noEventsDiv.css({'position':'fixed','top':'0', 'margin-top':'50px','width':'100%','z-index':'100'});
				noEventsDiv.show().delay(8000).fadeOut();
		    }
		    //fixes a bug in FF where was re-searching after leaving search box
		    if(arguments[0].eventPhase == 2){
				return;
			}
		    
		    $.get('/search', {
					value : searchTermObject[0].value,
					category : searchTermObject[0].category,
					label : searchTermObject[0].label
				}, function(data) {
					if(data.indexOf('eventsEmpty') == -1){
						$('#accordion').hide();
						$('#accordion')
// 							.animate({ width: ['toggle', 'swing'],height: ['toggle', 'swing'] })
// 							.fadeIn('4000')
						    .html(data)
						    , 'html';
						dimZeroCountEvents();
						$('#accordion').accordion('destroy');
						makeAccordion();
						$('#accordion').animate({
						    width: ['toggle', 'swing'],
						    height: ['toggle', 'swing'],
						    opacity: 'toggle'
						  }, 300, 'linear', function() {
// 						      animation complete
						  });
						
						$('.moreButton').hide();
					}else{
						var noEventsDiv = $('#noEventsAlert');
						noEventsDiv.css({'position':'fixed','top':'0', 'margin-top':'50px','width':'100%','z-index':'100'});
						noEventsDiv.show().delay(4000).fadeOut();
					}
				});
		});

		makeAccordion();
		$('.moreButton').hide();
		$('.alert').not($('.upcomingAlert')).hide();
		$('#search').click(function () {
			$('#search').tooltip('hide');
	    });
	
		$('#search').tooltip({
			'trigger':'hover', 
			'title': 'Search for concerts by Date, Year, Tour, Venue, or even relative date terms.  Example: Today, Yesterday, July 7, 2012, Riverbend, Summer Tour 2002, Last Month, 11/20/2010', 
			'placement':'bottom'
			});
		
		$('div#artistTT.icon-question-sign').tooltip({
			'trigger':'hover', 
			'title': 'This is the artist that will be the default when you go to breakforit.com', 
			'placement':'top',
			});
		
		$('div#timeframeTT.icon-question-sign').tooltip({
			'trigger':'hover', 
			'title': 'This is the default timeframe for events that will appear upon entering the site', 
			'placement':'top'
			});
		
		$('div#accordion h3:first-child .eventTitle').tooltip({
			'title': 'Click here to see videos for this event.  This pulls videos in real-time from YouTube', 
			'placement':'top tab',
			});
		
		$('div#accordion > h3:nth-child(11) .videoCount').tooltip({
			'title': 'Number of video for this event', 
			'placement':'top'
			});
		
		dimZeroCountEvents();
		
		$(window).scroll(function() {
			if($(this).scrollTop() != 0) {
				$('#toTop').fadeIn();	
			} else {
				$('#toTop').fadeOut();
			}
		});
	 
		$('#toTop').click(function() {
			$('body,html').animate({scrollTop:0},800);
			mixpanel.track('To Top', {
			});
		});	

		$('#saveDefaults').click(function() {
			var tf = $('#timeframeDD').val();
			var artist = $('#artistDD').val();
			$.cookie("artist", artist, {expires: 9999, path: '/'});
			$.cookie("timeframe", tf, {expires: 9999, path: '/'});
		});	
		$('#resetDefaults').click(function() {
			$.cookie("artist",null);
			$.cookie("timeframe",null);
			mixpanel.track('Reset Defaults', {
			});
		});
		$('#dontShowFirstTime').click(function() {
			$.cookie("firstTime", "true", {expires: 9999, path: '/'});
			$('#search').tooltip('hide');
		});	
		$('#okButton').click(function() {
			$('#search').tooltip('hide');
		});
		//set defaults from cookies
		 var artistCookie = $.cookie("artist");
		 var tfCookie = $.cookie("timeframe");
		 var firstTimeCookie = $.cookie("firstTime");
		 if(tfCookie != null){
		 	$('#timeframeDD').val(tfCookie);
		 }if(artistCookie != null){
		 	$('#artistDD').val(artistCookie);
		 }if(firstTimeCookie == null){
			 showFirstTimeStuff();
		 }
		 
		 $('img#tw').click(function() {
			 window.open('https://twitter.com/BreakForIt');
			 mixpanel.track('Twitter', {
				});
		 });
		 $('img#fb').click(function() {
		 	window.open('https://www.facebook.com/BreakForIt');
		 	mixpanel.track('Facebook', {
			});
		 });
		
		 //color background differently
		 var colorArray = [
		                              'Red',
		                              'BlueViolet',
		                              'Orange',
		                              'Chocolate',
		                              'SteelBlue', 
		                              'OliveDrab', 
		                              'Green'
		                          ];
		 
		 jQuery.each($('.tooltip-inner'), function(i, val) {
		      $(this).css('background-color',getRandomColor());
	     });
		 
		 function getRandomColor(){
			 
              var randomColorIx = Math.floor(Math.random()*colorArray.length);
              var color = colorArray[randomColorIx];
              colorArray.splice(randomColorIx,1);
              
              return color;
		 };
		 
		 $('#chg').click(function(){
			 changeBackground();
		 });
		 
	});
	
	function changeBackground(){
		var bgCount = ${bgCount};
		 var randomnumber=Math.floor(Math.random()*bgCount);
		 var bg = 'url(images/bglogo.png), url(images/bg/bg'+randomnumber+'.png)';
		 $('html').css('background',bg);
		 $('html').css('background-position','center top, left top');
		 $('html').css('background-repeat','no-repeat, repeat');
		 $('#chg').html('change background '+ randomnumber);
		 
		 /*
		 	background: url(images/bglogo.png), url(images/bg/bg21.png);
			background-position: center top, left top;;
			background-repeat: no-repeat, repeat;
		 */
	}
	
	function showFirstTimeStuff(){
		hideOrShowAllTooltips('show');
		$('#firstTimeModal').modal('show');
		$('#firstTimeModal').on('hidden', function () {
			hideOrShowAllTooltips('hide');
		});
		
		function hideOrShowAllTooltips(hideOrShow){
			$('#search').tooltip(hideOrShow);
			if(hideOrShow=='hide'){
				hideOrShow = 'destroy';
			}
			$('div#accordion h3:first-child .eventTitle').tooltip(hideOrShow);
			$('div#accordion > h3:nth-child(11) .videoCount').tooltip(hideOrShow);
			
		};
	}
	
	function dimZeroCountEvents(){
        $('span.videoCount').each(function(){
        if($(this).html()=='0'){
                        $(this).parents('h3').css('opacity','.5');
        }

        //bug that was causing last row to expand over footer
        jQuery('h3').last().css('visibility','hidden');

    }); 
}
	
</script>

<!-- start Mixpanel -->
<script type="text/javascript">(function(e,b){if(!b.__SV){var a,f,i,g;window.mixpanel=b;a=e.createElement("script");a.type="text/javascript";a.async=!0;a.src=("https:"===e.location.protocol?"https:":"http:")+'//cdn.mxpnl.com/libs/mixpanel-2.2.min.js';f=e.getElementsByTagName("script")[0];f.parentNode.insertBefore(a,f);b._i=[];b.init=function(a,e,d){function f(b,h){var a=h.split(".");2==a.length&&(b=b[a[0]],h=a[1]);b[h]=function(){b.push([h].concat(Array.prototype.slice.call(arguments,0)))}}var c=b;"undefined"!==
typeof d?c=b[d]=[]:d="mixpanel";c.people=c.people||[];c.toString=function(b){var a="mixpanel";"mixpanel"!==d&&(a+="."+d);b||(a+=" (stub)");return a};c.people.toString=function(){return c.toString(1)+".people (stub)"};i="disable track track_pageview track_links track_forms register register_once alias unregister identify name_tag set_config people.set people.increment people.append people.track_charge".split(" ");for(g=0;g<i.length;g++)f(c,i[g]);b._i.push([a,e,d])};b.__SV=1.2}})(document,window.mixpanel||
[]);
mixpanel.init("aa7b0128e1925baed3a3618c08dc6c3c");
</script>
<!-- end Mixpanel -->

<title>BreakForIt</title>
</head>
<body>
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">Your
			web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>

	<!-- ==================================================================================================================================== -->
	<!-- ==================================================================================================================================== -->
	<!-- ==================================================================================================================================== -->
	<!-- ==================================================================================================================================== -->
	<div id="container">
		<div id="headerbar">
			<div id="logo">
				<a href="../"><img src="images/logotext.png"
					alt="Break For It Logo"></a>
			</div>
			<div id="social">
				<img id="fb" class="soc" src="images/facebook.png" alt="facebook"
					style="height: 50px" /> <img id="tw" class="soc"
					src="images/twitter.png" alt="twitter" style="height: 50px" />
			</div>
			<!-- 		    <div class="searchBox"> -->
			<!-- 				<button class="btn btn-inverse" id="chg">change background 0</button> -->
			<input type="text" autocomplete="off" id="search" class="searchInput"
				data-provide="typeahead" placeholder="Search" data-items="4"
				tabindex="0"> <input type="hidden" id="searchValue" /> <input
				type="hidden" id="searchCategory" /> <input type="hidden"
				id="searchLabel" /> <input type="hidden" id="currentTime"
				value="${currentTime}" /> <input type="hidden" id="artistTerms"
				value="${artistTerms}" />
			<!-- 	        </div> -->
			<div class="upcoming upcomingAlert alert alert-info">
				<div>
					<strong>${daysUntil}</strong> days until opening night
				</div>
				<div>
					<i>Coming up:</i>
					<ul>
					<c:forEach var="thisEvent" items="${nextEvents}">
						<li>
						&nbsp&nbsp${thisEvent.dateNum}&nbsp${thisEvent.venue.name} ${thisEvent.venue.location} 
						</li>
					</c:forEach>
					</ul>
				</div>
			</div>

		</div>
	</div>

	<div class="modal hide" id="accountModal" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">Set Defaults</h3>
		</div>
		<div class="modal-body">
			<div>
				Default Artist:
				<div class="icon-question-sign" id="artistTT"></div>
				<select name="test" id="artistDD">
					<option value="dmb">DMB</option>
				</select>
			</div>
			<div>
				Default Timeframe:
				<div class="icon-question-sign" id="timeframeTT"></div>
				<select name="test" id="timeframeDD">
					<option value="This Year">This Year</option>
					<option value="This Month">This Month</option>
					<option value="This Week">This Week</option>
					<option value="Recent Tour">Recent Tour</option>
				</select>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-warning" data-dismiss="modal"
				id="resetDefaults">Reset Defaults</button>
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary" data-dismiss="modal"
				id="saveDefaults">Save changes</button>
		</div>
	</div>

	<div class="modal hide" id="firstTimeModal" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">Welcome to Break For It!</h3>
		</div>
		<div class="modal-body">
			<p>Here you will find a collection of live DMB footage grouped
				together by concert date. More artists will be coming in the future
				as our site expands.</p>
			<br>
			<p>The home page will always display the most recent events, but
				use the search box at the top right to find and relive concerts from
				any year.</p>
			<br>
			<p>Please feel free to leave comments and suggestions on our
				feedback widget(left side), Facebook, or Twitter!
		</div>
		<div class="modal-footer">
			<button id="okButton" class="btn" data-dismiss="modal"
				aria-hidden="true">OK</button>
			<button id="dontShowFirstTime" class="btn btn-primary"
				data-dismiss="modal" aria-hidden="true">Don't Show Again</button>
		</div>
	</div>



	<div class="modal hide" id="aboutModal" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">About This Site</h3>
		</div>
		<div class="modal-body">
			<p>
				Break For It was designed and developed by <a
					href="http://www.timmygcentral.com" target="_blank">Tim Giblin</a>.
				It allows users to view organized concert footage in order to easily
				relive concert experiences again and again. Videos are currently
				streamed solely from YouTube, but other sites are being looked into.
			</p>
			<br>
			<p>If there is any concerts or additional artists that you would
				like to see, please contact us via the feedback widget, Facebook, or
				Twitter.</p>
			<br>
			<p>If you have YouTube videos that are being displayed on this
				site and would not like them to be, go to your video settings >
				advanced settings > uncheck 'Allow Embedding'. Remember, this site
				makes no money and has no plans to. All views will be recorded just
				like if watching through YouTube.com.</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		</div>
	</div>

	<section id="content">
		<div class="basic" id="accordion">
			<%@ include file="FilteredAccordian.jsp"%>
		</div>
	</section>
	<!-- 		<div id="paddingDiv" style="height:400px;display:none;"> -->
	<!-- 		</div> -->
	<footer>
		<div id="footer">
			<a href="#aboutModal" class="footerButtons" data-toggle="modal"><img
				id="about" class="footer" src="images/about.png" alt="about"
				style="height: 50px" /></a> <a href="#accountModal"
				class="footerButtons" data-toggle="modal"><img id="defaults"
				class="footer" src="images/defaults.png" alt="defaults"
				style="height: 50px" /></a>
		</div>
		©2012 Break For It
	</footer>

	<div class="alert alert-block" id="noVideosAlert">
		<!-- 		  <button type="button" class="close" data-dismiss="alert">×</button> -->
		<h4>Sorry!</h4>
		No videos for this event yet!
	</div>
	<div class="alert alert-danger" id="noEventsAlert">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<h4>Sorry!</h4>
		No events for this search term
	</div>
	<div class="alert alert-danger" id="nothingSelectedAlert">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<h4>Sorry!</h4>
		You must selected a term from the search list!
	</div>

	<button type="button" class="btn btn-primary" id="toTop"></button>

	<div id="browserFooter"></div>
	<!-- crowd -->
	<script type='text/javascript'>
	
	var _ues = {
	host:'breakforit.userecho.com',
	forum:'14750',
	lang:'en',
	tab_corner_radius:15,
	tab_font_size:20,
	tab_image_hash:'ZmVlZGJhY2s%3D',
	tab_chat_hash:'Y2hhdA%3D%3D',
	tab_alignment:'left',
	tab_text_color:'black',
	tab_text_shadow_color:'#00000055',
	tab_bg_color:'white',
	tab_hover_color:'aqua'
	};
	
	(function() {
	    var _ue = document.createElement('script'); _ue.type = 'text/javascript'; _ue.async = true;
	    _ue.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'cdn.userecho.com/js/widget-1.4.gz.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(_ue, s);
	  })();

</script>
</body>

</html>