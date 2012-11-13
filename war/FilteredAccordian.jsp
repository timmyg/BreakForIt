<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${eventsPopulated}">
		
			<c:forEach var="thisEvent" items="${events}">
				<h3 class="evt">
					<a class="header"> <span class = "eventTitle"></span><c:out value="${thisEvent.venue.name}" />&nbsp;&nbsp;(<c:out
							value="${thisEvent.venue.location}" />)&nbsp;&nbsp;&nbsp;&nbsp;
							(<span class="videoCount"><c:out value="${thisEvent.videoCount}" /></span>)
								<span class="eventDate"><c:out value="${thisEvent.dateFormatted}" /></span>
								<input type="hidden" name="tag" value="${thisEvent.tag}" /> 
								<input type="hidden" name="feedURL" value="${thisEvent.feedURL}" />
								<input type="hidden" name="eventID" value="${thisEvent.id.id}" /> 
								<input type="hidden" name="date" value="${thisEvent.dateNumString}" />
					</a>
				</h3>
				<div class="videoContent" style="height: 535px;">
					<div class="image">
						<img id="loading-image" class="centered" src="images/ajax-loader.gif" alt="Loading..."/>
					</div>
					<div class="bs-docs-example eventFooter more"
						style="padding-bottom: 15px;">
						<a class="btn moreButton" rel="popover" title="Sorry"
							data-content="No more videos!">More</a>
					</div>
				</div>
			</c:forEach>
	</c:when>
	<c:otherwise>
		<c:out value="eventsEmpty!" />
	</c:otherwise>
</c:choose>
