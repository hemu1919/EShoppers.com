<%@page import="db.Cart"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="db.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><c:out value="${product.name}"/> Description | EShoppers</title>
	<link rel="stylesheet" type="text/css" href="../../css/main.css" />
	<link rel="stylesheet" type="text/css" href="../../css/description.css" />
</head>
<body>
	<c:import url="../buyers/header.jsp" />
	<table id="item">
		<tr>
			<td align="center" width="150vw">
				<img height="200vh" src='<c:out value="${product.imagePath}" />' alt='<c:out value="${product.name}" />.jpg' />
			</td>
			<td align="left">
				<div class="title"><c:out value="${product.name}"/></div>
				<form id="form" method="post" action='../addCart/<c:out value="${product.id}"/>'></form>
				<b>Price: </b><c:out value="${product.getPriceFormatted()}"/>
				<button onclick="document.getElementById('form').submit();" <c:if test="${cart.search(product) || product.count <= 0}">disabled</c:if>>Buy</button><br/>
				<b>Seller: </b><c:out value="${product.seller.company_name}"/>
				<p align="justify"><span class="title">Description</span><br/>
				<c:out value="${product.desc}"/></p>
			</td>
			<td align="right">
				<c:choose>
					<c:when test="${product.count <= 0}"><span class="failed">Out of Stock</span></c:when>
					<c:otherwise><span class="success">Stock Available</span></c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
	<c:import url="../footer.jsp" />
</body>
</html>