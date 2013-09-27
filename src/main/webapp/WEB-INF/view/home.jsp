<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/view/fragments/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Hello world</title>
<%@include file='fragments/headerIncludes.jsp'%>
	</head>
	<body>
		<div class="container">

			<div class="row">
				<div class="col-sm-12">
					<h2 class="heading-page">Hello world!</h2>

					<c:forEach var="value" items='${values}'>
						<p>${value.name} ${value.world}</p>
					</c:forEach>

				</div>
			</div>

		</div>

<%@include file='fragments/footerIncludes.jsp'%>
	</body>
</html>
