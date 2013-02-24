<%@page import="java.text.DecimalFormat"%>
<%@page import="com.google.gdata.data.youtube.YouTubeMediaGroup"%>
<%@page import="com.google.gdata.data.youtube.VideoEntry"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.mosaic-block {
	overflow: hidden;
	width: 240px;
	height: 126px;
	margin: 10px;
	border: 1px solid #fff;
	-webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5);
	-moz-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5);
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5);
}

.mosaic-backdrop {
	display: none;
	position: absolute;
	top: 0;
	height: 100%;
	width: 100%;
	background: #111;
	width: 260px;
	height: 140px;
}

.mosaic-overlay {
	display: none;
	z-index: 5;
	position: absolute;
	width: 100%;
	height: 100%;
	background: #111;
}

.mosaic-overlay .details {
	font-family: sans-serif, Arial, Helvetica;
	color: #fff;
	font-size: 13px;
	padding: 3px
}

<!--
FIX FANCYBOX CHROME BUG -->.fancybox-prev,.fancybox-next {
	width: 30px;
	height: 30px;
	top: 45%
}

.fancybox-prev:hover span,.fancybox-prev-ico {
	left: -10px;
}

.fancybox-next:hover span,.fancybox-next-ico {
	right: -10px;
	left: auto;
}
</style>

<c:choose>
	<c:when test="${videosPopulated}">
		<c:forEach var="thisVideo" items="${videos}">
			<c:set var="v" value="${thisVideo}" />
			<%
				DecimalFormat minFormat = new DecimalFormat("#0");
							DecimalFormat secFormat = new DecimalFormat("00");
							VideoEntry v = (VideoEntry) pageContext
									.getAttribute("v");
							String id = v.getId().substring(
									v.getId().indexOf("video:") + 6,
									v.getId().length());
							String author = v.getAuthors().get(0).getName();
							String title = v.getTitle().getPlainText();
							title = title + " (" + author + ")";
							//get duration
							Long totalDuration = v.getMediaGroup().getDuration();
							String dur = minFormat.format((int) Math
									.floor(totalDuration / 60))
									+ ":"
									+ secFormat.format(totalDuration % 60);

							title = title + " " + dur;

							String viewCount = "";
							if (v.getStatistics() != null) {
								viewCount = String.valueOf(v.getStatistics()
										.getViewCount());
							}
			%>



			<div class="mosaic-block bar2 video">
				<a class="mosaic-overlay fancybox" onClick="logMixpanelVideo(this)"
					href="http://www.youtube.com/v/<%=id%>?wmode=opaque" rel="group">
					<div class="details">
						<h4><%=title%></h4>
						<br />
						<p>
							View Count:
							<%=viewCount%></p>
					</div>
				</a>
				<div class="mosaic-backdrop">
					<span class="image"> <img
						src="http://img.youtube.com/vi/<%=id%>/0.jpg" alt="" />
					</span>
				</div>
			</div>

		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:out value="noMoreVideos" />
	</c:otherwise>
</c:choose>

