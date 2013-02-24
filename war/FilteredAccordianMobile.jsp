<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:choose>
	<c:when test="${eventsPopulated}">

		<div data-role="collapsible-set" data-theme="a"
			data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d">
			<c:forEach var="thisEvent" items="${events}">
				<div data-role="collapsible" class="event">
					<h3 class="header">
						<c:out value="${thisEvent.venue.name}" />
						&nbsp;&nbsp;(
						<c:out value="${thisEvent.venue.location}" />
						)&nbsp; (
						<c:out value="${thisEvent.videoCount}" />
						)<br>
						<c:out value="${thisEvent.dateFormatted}" />
					</h3>
					<input type="hidden" name="tag" value="${thisEvent.tag}" /> <input
						type="hidden" name="feedURL" value="${thisEvent.feedURL}" /> <input
						type="hidden" name="eventID" value="${thisEvent.id.id}" /> <input
						type="hidden" name="date" value="${thisEvent.dateNumString}" />
					<p>
						<img id="loading-image" class="centered"
							src="images/ajax-loader_small.gif" alt="Loading..." />
					</p>
					<div class="bs-docs-example eventFooter more ">
						<a data-role="button" class="btn moreButton ui-body-e"
							rel="popover">
							<div>
								<h1>More</h1>
							</div>
						</a>
					</div>
				</div>

			</c:forEach>
		</div>



		<!-- dummy event because jquery UI accordion was overlapping for last event-->
		<!-- 			<h3 class="evt"> -->
		<!-- 			<a class="header"> <span class = "eventTitle"></span>Test -->
		<!-- 								<span class="eventDate">June 2, 2012</span> -->
		<!-- 					</a> -->
		<!-- 			</h3> -->
	</c:when>
	<c:otherwise>
		<c:out value="eventsEmpty!" />
	</c:otherwise>
</c:choose>
