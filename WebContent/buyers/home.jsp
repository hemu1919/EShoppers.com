<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home | EShoppers</title>
<link rel="stylesheet" type="text/css" href="./css/main.css" />
<link rel="stylesheet" type="text/css" href="./css/home.css" />
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:import url="../buyers/header.jsp" />
	<table id= "content">
		<tr>
			<td width="12%" align="left">
				<div id="filters">
					<form method="get" action="./buyer">
						<c:if test="${types.size() != 0}">
							<div>
								<label for="type">Product</label>
								<div id="type">
									<c:forEach var="type" items="${types}">
										<input type="checkbox" class="type" name="type" value="<c:out value='${type}' />" <c:if test="${type_filter.contains(type)}">checked</c:if> /><c:out value='${type}' /><br/>
									</c:forEach>
								</div>
							</div>
						</c:if>
						<div>
							<label for="sellers">Seller</label>
							<div id="seller">
								<c:forEach var="seller" items="${sellers}">
									<input type="checkbox" class="sellers" name="seller" value="<c:out value='${seller.id}' />" <c:if test="${seller_filter.contains(String.valueOf(seller.id))}">checked</c:if> /><c:out value='${seller.brand_name}' /><br/>
								</c:forEach>
							</div>
						</div>
						<div>
							<input type="submit" value="Update" />
						</div>
					</form>
				</div>
			</td>
			<td align="right">
				<div id="items">
					<c:forEach var="product" items="${products}">
						<div class="item">
							<img src='<c:out value="${product.imagePath}" />' alt='<c:out value="${product.name}" />.jpg' />
							<div class="title"><a href='./buyer/product/<c:out value="${product.id}"/>'><c:out value="${product.name}" /></a></div>
							<div></div>
							<div></div>
						</div>
					</c:forEach>
				</div>
			</td>
		</tr>
	</table>
	<c:import url="../footer.jsp" />
</body>
</html>