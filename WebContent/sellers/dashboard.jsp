<!DOCTYPE html>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../css/main.css" />
		<link rel="stylesheet" type="text/css" href="../css/dashboard.css" />
		<title>Dashboard</title>
	</head>
	<body>
	
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
		<c:import url="../sellers/header.jsp" />
		<div>
			<!-- Filter Space -->
		</div>
		
		<div class="section">
			<h3 id="header">Products List</h3>
			<div id="option" onclick="window.open(getURL('/seller/dashboard/create'), '_self');"><button>Create Product</button></div>
			<table class="list">
				<tr>
					<th>Sl. No</th>
					<th>Product Name</th>
					<th>Product Type</th>
					<th>Stock</th>
					<th>Price</th>
					<th>Actions</th>
				</tr>
				<c:choose>
					<c:when test="${products.size() != 0}">
						<c:forEach var="product" items="${products}" varStatus="stat">
							<tr>
								<td align="right"><c:out value="${stat.count}" /></td>
								<td align="center"><c:out value="${product.name}" /></td>
								<td align="center"><c:out value="${product.type}" /></td>
								<td align="right"><c:out value="${product.count}" /></td>
								<td align="right"><c:out value="${product.price}" /></td>
								<td align="center">
									<c:choose>
										<c:when test="${product.isPublished()}">
											<button class="options" onclick="publish('<c:out value="${product.id}" />', false)">Stop</button>
										</c:when>
										<c:otherwise>
											<button class="options" onclick="publish('<c:out value="${product.id}" />', true)">Publish</button>
										</c:otherwise>
									</c:choose>
									<button class="options" onclick="window.open('./dashboard/manage/<c:out value="${product.id}" />', '_self');">Manage</button>
									<button class="options" onclick="remove('<c:out value="${product.id}" />')">Remove</button>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6">
								No Products yet created.
							</td>
						</tr>
					</c:otherwise>
				</c:choose>	 
			</table>
		</div>
		<c:import url="../footer.jsp" />
		<script type="text/javascript" src="../js/xhrRequests.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
		<script type="text/javascript" src="../js/dashboard.js"></script>
	</body>
</html>