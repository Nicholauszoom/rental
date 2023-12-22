<%@ include file="/ui/layouts/tag_lib.jsp"%>
<spring:hasBindErrors name="${commandName}">
	<div class="alert alert-danger alert-dismissible fade show" style="border-radius: 5px;line-height: 22px;">
		<p><ul>
			<c:forEach var="error" items="${errors.allErrors}">
				<li><spring:message message="${error}" /></li>
			</c:forEach>
			</ul>
		</p>
		<button class="close" type="button" data-dismiss="alert" aria-label="Close">
		  <span aria-hidden="true">×</span>
		</button>
	</div>
</spring:hasBindErrors>

<c:if test="${not empty errorMsg}" >
	<div class="alert alert-danger alert-dismissible fade show" role="alert" style="border-radius: 5px;line-height: 22px;box-shadow:0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)">
		<c:out value="${errorMsg}"/>
		<button class="close" type="button" data-dismiss="alert" aria-label="Close">
		  <span aria-hidden="true">×</span>
		</button>
	</div>  
</c:if>
<c:if test="${not empty successMsg}" >
	<div class="alert alert-success alert-dismissible fade show" role="alert" style="border-radius: 5px;line-height: 22px;box-shadow:0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)">
		<c:out value="${successMsg}"/>
		<button class="close" type="button" data-dismiss="alert" aria-label="Close">
		  <span aria-hidden="true">×</span>
		</button>
	</div>  
</c:if>

<!-- Removing messages box -->
<%
	request.getSession().removeAttribute("successMsg");
	request.getSession().removeAttribute("errorMsg");
%>