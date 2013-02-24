<%@page import="java.text.DecimalFormat"%>
<%@page import="com.google.gdata.data.youtube.YouTubeMediaGroup"%>
<%@page import="com.google.gdata.data.youtube.VideoEntry"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				title = title.replace("Dave Matthews Band", "DMB");
				title = title.replace("Dave Matthews", "DMB");
				title = title + " (" + author + ")";
				//get duration
				Long totalDuration = v.getMediaGroup().getDuration();
				String dur = minFormat.format((int)Math.floor(totalDuration / 60)) + ":" + secFormat.format(totalDuration % 60);
						
				title = title + " " + dur;
				
				String viewCount = "";
							if (v.getStatistics() != null) {
								viewCount = String.valueOf(v.getStatistics()
										.getViewCount());
							}
							
			%>
			
			
			<a href="http://www.youtube.com/v/<%=id%>?wmode=opaque" data-role="button">
				<div class="ui-body ui-body-e"><h4><%=title%>&nbsp;View Count:<%=viewCount%></h4></div>
			</a>
			

		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:out value="noMoreVideos" />
	</c:otherwise>
</c:choose>

