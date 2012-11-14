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
<link href='http://fonts.googleapis.com/css?family=Jolly+Lodger' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Share+Tech|PT+Sans+Narrow:400,700' rel='stylesheet' type='text/css'>

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

<!--  TWITTER BOOTSTRAP -->
<script src="twbootstrap/js/bootstrap.js"></script>
<link href="twbootstrap/css/bootstrap.css" rel="stylesheet">

<!--  TO TOP -->
<script src="jqTop/js/jquery.ui.totop.js"></script>
<link href="jqTop/css/ui.totop.css" rel="stylesheet">

<!--  jQ cookie -->
<script src="jQcookie/jquery.cookie.js"></script>

<link type="text/css" rel="stylesheet" href="Artist.css">

<!-- shortcut keys -->
<script type="text/javascript" src="js/shortcut.js"></script>



<!-- ==================================================================================================================================== -->
<script type="text/javascript">

	shortcut.add("alt+s", function() {
	    $('#search').focus();
	});  

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
							content.height('385px');								
						}else if(videoCount >=1){//1 row
							content.height('215px');								
						}else{//no videos
							content.height('145px');		
						}
						//accordion expanding
						if(ui.newContent.length > 0){
							contentHeight = content.height();
							$('#accordion').height($('#accordion').height() + contentHeight);
							firstTime = false;
						//accordion collapsing
						}else{
							$('#accordion').height($('#accordion').height() - contentHeight);
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
										"input[name=date]").val()
							},

							function(data) {
								if(data.indexOf('noMoreVideos') == -1){
									var thisVidDiv = $(ui.newHeader[0]).next().children(':first-child');
									thisVidDiv.hide();
									thisVidDiv.html(data), 'html';
									thisVidDiv.fadeIn('slow');
									thisVidDiv.parents('.videoContent').height(thisVidDiv.height()+thisVidDiv.next());
									installStuff();
									moreButton.button();
									moreButton.click(function() {
										if(!$(this).hasClass('disabled')){
											moreButton.hide();
											getMoreVideos(this);
											moreButton.delay(1000).fadeIn("slow");
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

	function getMoreVideos(button) {
		$.get('/videos', {
			action : "more",
			artistTerms : $('#artistTerms').val(),
			eventID : $(button).parent().parent().prev().children('a').children(
					"input[name=eventID]").val(),
			videoCount : $(button).parent().parent().children('.image')
					.children('.video').length,
			feedURL : $(button).parent().parent().prev().children('a')
					.children("input[name=feedURL]").val(),
			tag : $(button).parent().parent().prev().children('a').children(
					"input[name=tag]").val(),
			date : $(button).parent().parent().prev().children('a').children(
					"input[name=date]").val(),
		}, function(data) {
			if (data.indexOf('noMoreVideos') == -1) {
				$(button).parent().prev().append(data), 'html';
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
		});
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
	});
	
	$(function() {
		makeAccordion();
		$('.moreButton').hide();
		$('.alert').hide();
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
			'title': 'This is the artist that will be the default when you go to tgcgrapes3.com/grapes', 
			'placement':'top',
			});
		
		$('div#timeframeTT.icon-question-sign').tooltip({
			'trigger':'hover', 
			'title': 'This is the default timeframe for events that will appear upon entering the site', 
			'placement':'top'
			});
		
		dimZeroCountEvents();
		
	});
	
	function dimZeroCountEvents(){
		$('span.videoCount').each(function(){
	        if($(this).html()=='0'){
				$(this).parents('h3').css('opacity','.5');
	        }
	    });	
	}
	
	
	$(function() {
		$(window).scroll(function() {
			if($(this).scrollTop() != 0) {
				$('#toTop').fadeIn();	
			} else {
				$('#toTop').fadeOut();
			}
		});
	 
		$('#toTop').click(function() {
			$('body,html').animate({scrollTop:0},800);
		});	
// 		$('.dropdown-toggle').dropdown();
		$('#saveDefaults').click(function() {
			var tf = $('#timeframeDD').val();
			var artist = $('#artistDD').val();
			$.cookie("artist", artist, {expires: 9999, path: '/'});
			$.cookie("timeframe", tf, {expires: 9999, path: '/'});
		});	
		$('#dontShowFirstTime').click(function() {
			$.cookie("firstTime", "true", {expires: 9999, path: '/'});
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
			 $('#firstTimeModal').modal('show');
		 }
		 
		 $('img#tw').click(function() {
			 window.open('https://twitter.com/BreakForIt');
		 });
		 $('img#fb').click(function() {
		 	window.open('https://www.facebook.com/BreakForIt');
		 });
	});
	
</script>

<title>Break For It</title>
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
		<div class="navbar ui-widget-header">
			<div id="logo">
				<a href="http://localhost:55940/dmb" >BreakForIt</a>
			</div> 
			<div id="social">
				<img id="fb" class="soc" src="images/facebook.png" alt="facebook" style="height:50px"/>
				<img id="tw" class="soc" src="images/twitter.png" alt="twitter" style="height:50px"/>
		    </div>
<!-- 		    <ul class="nav"> -->
<!-- <!-- 		      <li><a href="" class="topButtons">Home</a></li> --> 
<!-- 		    </ul> -->
		    <div class="searchBox">
	             <input type="text" autocomplete="off" class="searchInput" id="search"  data-provide="typeahead" placeholder="Search" data-items="4" tabindex="0">
	             <input type="hidden" id="searchValue" />
	             <input type="hidden" id="searchCategory" /> 
	             <input type="hidden" id="searchLabel" /> 
	             <input type="hidden" id="currentTime" value="${currentTime}" /> 
	             <input type="hidden" id="artistTerms" value="${artistTerms}" /> 
	        </div>
	        
		  </div>
		</div>
		
        <div class="modal hide" id="accountModal" tabindex="-1" >
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="myModalLabel">Set Defaults</h3>
		  </div>
		  <div class="modal-body">
		  <div>
		  	Default Artist:
		  	<div class="icon-question-sign" id="artistTT">
			</div>
		  	<select name="test" id="artistDD">
				<option value="dmb">DMB</option>
			</select>
			</div>
			<div>
			Default Timeframe:
			<div class="icon-question-sign" id="timeframeTT">
			</div>
			<select name="test" id="timeframeDD" >
				<option value="This Week">This Week</option>
				<option value="This Month">This Month</option>
				<option value="This Year">This Year</option>
				<option value="Recent Tour">Recent Tour</option>
			</select>	
			</div>			    
		  </div>
		  <div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		    <button class="btn btn-primary" data-dismiss="modal" id="saveDefaults">Save changes</button>
		  </div>
		</div>
		
		<div class="modal hide" id="firstTimeModal" tabindex="-1" >
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="myModalLabel">Welcome!</h3>
		  </div>
		  <div class="modal-body">
		   This is what you see the first time				    
		  </div>
		  <div class="modal-footer">
		    <button class="btn"  data-dismiss="modal" aria-hidden="true">OK</button>
		    <button id="dontShowFirstTime"class="btn btn-primary"  data-dismiss="modal" aria-hidden="true">Don't Show Again</button>
		  </div>
		</div>
		
		
		
<div class="modal hide" id="aboutModal" tabindex="-1" >
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="myModalLabel">About This Site</h3>
		  </div>
		  <div class="modal-body">
		   About This Site			    
		  </div>
		  <div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		  </div>
		</div>
		
		<div id="content">
			<div class="basic" id="accordion">
				<%@ include file="FilteredAccordian.jsp" %>
			</div>
		</div>
		<div id="paddingDiv" style="height:400px;display:none;">
		</div>
		
		<div class="alert alert-block" id="noVideosAlert">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  <h4>Sorry!</h4>
		  No videos for this event yet!
		</div>
		<div class="alert alert-danger" id="noEventsAlert">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  <h4>Sorry!</h4>
		  No events for this search term
		</div>
		
		
		
		
		
		
<!-- 		<div id="toTop" class="navbar-inner"> -->
		<button type="button" class="btn btn-primary" id="toTop" ></button>
<!-- 		</div> -->
	</div>
	<!-- UserEcho Feedback Widget -->
	<script type='text/javascript'>
		
		var _ues = {
		host:'breakforit.userecho.com',
		forum:'14750',
		lang:'en',
		tab_corner_radius:10,
		tab_font_size:20,
		tab_image_hash:'RmVlZGJhY2s%3D',
		tab_alignment:'left',
		tab_text_color:'#FFFFFF',
		tab_bg_color:'#707AFF',
		tab_hover_color:'#73EBF4'
		};
		
		(function() {
		    var _ue = document.createElement('script'); _ue.type = 'text/javascript'; _ue.async = true;
		    _ue.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'cdn.userecho.com/js/widget-1.4.gz.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(_ue, s);
		  })();

</script>
<footer>
	<div id="footer">
	    <a href="#aboutModal" class="footerButtons" data-toggle="modal">About</a>
	    <a href="#accountModal" class="footerButtons" data-toggle="modal">Defaults</a>
	</div>
	©2012 Break For It
</footer>

<div id="browserFooter"></div> <!-- crowd -->
</body>

</html>