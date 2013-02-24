<!doctype html>
<!-- The DOCTYPE declaration above will set the -->
<!-- browser's rendering engine into -->
<!-- "Standards Mode". Replacing this declaration -->
<!-- with a "Quirks Mode" doctype is not supported. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<meta name="viewport" content="width=device-width"/> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>

<!-- FONTS -->
<link href='http://fonts.googleapis.com/css?family=Jolly+Lodger' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Share+Tech|PT+Sans+Narrow:400,700' rel='stylesheet' type='text/css'>

<!--  TWITTER BOOTSTRAP DONT GET FROM CDN USING FOR DIFFERING TOOLTIP LOCATIONS--> 
<script src="twbootstrap/js/bootstrap.js"></script>
<link href="twbootstrap/css/bootstrap.css" rel="stylesheet">

<!--  jQ cookie -->
<script src="jQcookie/jquery.cookie.js"></script>

<!-- shortcut keys -->
<script type="text/javascript" src="js/shortcut.js"></script>

<!-- BFI CSS -->
<!-- <link rel="stylesheet" type="text/css" href="Artist.css" media="all"> -->
<!-- <link rel="stylesheet" type="text/css" href="Artist_screen.css" media="screen"/> -->
<link rel="stylesheet" type="text/css" href="Artist_mobile.css" media="all"/>


<!-- ==================================================================================================================================== -->
<script type="text/javascript">

	
	var contentHeight = 0;
	var firstTime = true;
	var divExpanded = false;
	

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
			eventID : $(button).parent().parent().children(
					"input[name=eventID]").val(),
			videoCount : $(button).parent().parent().children('p').children('a').length,
			feedURL : $(button).parent().parent()
					.children("input[name=feedURL]").val(),
			tag : $(button).parent().parent().children(
					"input[name=tag]").val(),
			date : $(button).parent().parent().children(
					"input[name=date]").val(),
		}, function(data) {
			if (data.indexOf('noMoreVideos') == -1) {

				$(button).parent().parent().children('p').append(data), 'html';
			} else {
				//no more videos message
				var html = $(button)[0].innerHTML.replace('More','No More Videos');
				$(button)[0].innerHTML = html;
				$(button).show();
				$(button).addClass('disabled');
// 				$(button).popover('show');
			}
		}).done(function() { 
			$(button).css('visibility','visible');
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
	var recentButtonClicked;

	$(function() {
		var eventButton = $('.event'); //ui.newHeader
		eventButton.bind('expand', function () {
			var eventData = $(this).children('.ui-collapsible-content');
			recentButtonClicked = eventData;
			if (!eventData.hasClass('populated')) {
				$.get('/videos', {
					action : "open",
					artistTerms : $('#artistTerms').val(),
					tag : eventData.children(
							"input[name=tag]").val(),
					feedURL : eventData.children(
							"input[name=feedURL]").val(),
					eventID : eventData.children(
							"input[name=eventID]").val(),
					date : eventData.children(
							"input[name=date]").val(),
					screenWidth : screen.width
				},function(data) {
					if(data.indexOf('noMoreVideos') == -1){
						var thisVidDiv = recentButtonClicked;
// 						recentButtonClicked.children('p');
// 						thisVidDiv.hide();
						thisVidDiv.children('p').html(data), 'html';
// 						formatVideos(thisVidDiv);
// 						thisVidDiv.fadeIn('slow');
// 						thisVidDiv.parents('.videoContent').height(thisVidDiv.height()+thisVidDiv.next());
// 						installStuff();
						var moreButton = thisVidDiv.children('.eventFooter').children('.moreButton');
						moreButton.button();
						moreButton.click(function() {
							if(!$(this).hasClass('disabled')){
								moreButton.css('visibility','hidden');
								getMoreVideos(this);
							}
						});
						moreButton.show();
						$(this).popover('show');
					}else{
						//no videos at all for event
//							$(ui.newHeader[0]).next().animate({'height':'100%'});
						var noVidsDiv = $('#noVideosAlert');
						$(ui.newHeader[0]).next().children(
							':first-child').html(noVidsDiv), 'html';
// 						$('#noVideosAlert').show();
						
					}
// 					moreButton.css('visibility','visible');
					eventData.addClass('populated');
				});
			};	
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
						    .html(data)
						    , 'html';
						dimZeroCountEvents();
						$('#accordion').show();
						$('.moreButton').hide();
					}else{
						var noEventsDiv = $('#noEventsAlert');
						noEventsDiv.css({'position':'fixed','top':'0', 'margin-top':'50px','width':'100%','z-index':'100'});
						noEventsDiv.show().delay(4000).fadeOut();
					}
				});
		});

// 		makeAccordion();
		$('.moreButton').hide();
		$('.alert').hide();
	
		
		

		
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
			 showFirstTimeStuff();
		 }
		 
		 $('img#tw').click(function() {
			 window.open('https://twitter.com/BreakForIt');
		 });
		 $('img#fb').click(function() {
		 	window.open('https://www.facebook.com/BreakForIt');
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
<!-- 				<a href="../" ><img src="images/logotext.png" alt="Break For It Logo"></a> -->
			</div> 
			<div id="social">
				<img id="fb" class="soc" src="images/facebook.png" alt="facebook" style="height:50px"/>
				<img id="tw" class="soc" src="images/twitter.png" alt="twitter" style="height:50px"/>
				<a href="#aboutModal" class="footerButtons" data-toggle="modal"><img id="about" class="footer" src="images/about.png" alt="about" style="height:50px"/></a>
		    </div>
<!-- 	             <input type="text" autocomplete="off" id="search" class="searchInput"  data-provide="typeahead" placeholder="Search" data-items="4" tabindex="0"> -->
	             <input type="hidden" id="searchValue" />
	             <input type="hidden" id="searchCategory" /> 
	             <input type="hidden" id="searchLabel" /> 
	             <input type="hidden" id="currentTime" value="${currentTime}" /> 
	             <input type="hidden" id="artistTerms" value="${artistTerms}" /> 
	        
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
				<option value="This Year">This Year</option>
				<option value="This Month">This Month</option>
				<option value="This Week">This Week</option>			
				<option value="Recent Tour">Recent Tour</option>
			</select>	
			</div>			    
		  </div>
		  <div class="modal-footer">
		    <button class="btn btn-warning" data-dismiss="modal" id="resetDefaults">Reset Defaults</button>
		    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		    <button class="btn btn-primary" data-dismiss="modal" id="saveDefaults">Save changes</button>
		  </div>
		</div>
		
		<div class="modal hide" id="firstTimeModal" tabindex="-1" >
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="myModalLabel">Welcome to Break For It!</h3>
		  </div>
		  <div class="modal-body">
		  	<p>Here you will find a collection of live DMB footage grouped together by concert date. More artists will be coming in the future as our site expands.</p>
		  	<br>
		  	<p>The home page will always display the most recent events, but use the search box at the top right to find and relive concerts from any year.</p>
		  	<br>
		  	<p>Please feel free to leave comments and suggestions on our feedback widget(left side), Facebook, or Twitter!				    
		  </div>
		  <div class="modal-footer">
		    <button id="okButton" class="btn"  data-dismiss="modal" aria-hidden="true">OK</button>
		    <button id="dontShowFirstTime"class="btn btn-primary"  data-dismiss="modal" aria-hidden="true">Don't Show Again</button>
		  </div>
		</div>
		
		
		
<div class="modal hide" id="aboutModal" tabindex="-1" >
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="myModalLabel">About This Site</h3>
		  </div>
		  <div class="modal-body">
		   <p>Break For It was designed and developed by <a href="http://www.timmygcentral.com" target="_blank">Tim Giblin</a>. It allows users to view organized concert footage in order to easily relive concert experiences again and again. Videos are currently streamed solely from YouTube, but other sites are being looked into.</p>
		   <br>
		   <p>If there is any concerts or additional artists that you would like to see, please contact us via the feedback widget, Facebook, or Twitter.</p>
		   <br>
		   <p>If you have YouTube videos that are being displayed on this site and would not like them to be, go to your video settings > advanced settings > uncheck 'Allow Embedding'. Remember, this site makes no money and has no plans to. All views will be recorded just like if watching through YouTube.com.</p>		    
		  </div>
		  <div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		  </div>
		</div>

		<section id="content">
			<div class="basic" id="accordion">
				<%@ include file="FilteredAccordianMobile.jsp" %>
			</div>
		</section>
<!-- 		<div id="paddingDiv" style="height:400px;display:none;"> -->
<!-- 		</div> -->
		<footer>
			<div id="footer">
			    
<!-- 			    <a href="#accountModal" class="footerButtons" data-toggle="modal"><img id="defaults" class="footer" src="images/defaults.png" alt="defaults" style="height:50px"/></a> -->
			</div>
<!-- 			Break For It -->
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
		
<!-- 		<button type="button" class="btn btn-primary" id="toTop" ></button> -->

<div id="browserFooter"></div> <!-- crowd -->
<!-- <script type='text/javascript'> -->
	
<!-- // 	var _ues = { -->
<!-- // 	host:'breakforit.userecho.com', -->
<!-- // 	forum:'14750', -->
<!-- // 	lang:'en', -->
<!-- // 	tab_corner_radius:15, -->
<!-- // 	tab_font_size:20, -->
<!-- // 	tab_image_hash:'ZmVlZGJhY2s%3D', -->
<!-- // 	tab_chat_hash:'Y2hhdA%3D%3D', -->
<!-- // 	tab_alignment:'left', -->
<!-- // 	tab_text_color:'black', -->
<!-- // 	tab_text_shadow_color:'#00000055', -->
<!-- // 	tab_bg_color:'white', -->
<!-- // 	tab_hover_color:'aqua' -->
<!-- // 	}; -->
	
<!-- // 	(function() { -->
<!-- // 	    var _ue = document.createElement('script'); _ue.type = 'text/javascript'; _ue.async = true; -->
<!-- // 	    _ue.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'cdn.userecho.com/js/widget-1.4.gz.js'; -->
<!-- // 	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(_ue, s); -->
<!-- // 	  })(); -->

<!-- </script> -->
</body>

</html>