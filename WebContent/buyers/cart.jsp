<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cart | EShoppers</title>
<link rel="stylesheet" lang="text/css" href="../css/main.css"/>
<link rel="stylesheet" lang="text/css" href="../css/cart.css"/> 
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:import url="../buyers/header.jsp" />
	<table id="temp">
		<tr>
			<td align="left" width="81%">
				<div id="items">
					<div id="title">Your Cart</div>
					<c:if test="${cart.items.size() == 0}">Oops! Your Cart is Empty. Shop and get something.</c:if>
					<c:forEach var="item" items="${cart.items}" varStatus="status">
						<div class="item">
							<table>
								<tr>
									<td align="center" rowspan="2" width="50vw">
										<img height="100vh" src='<c:out value="${item.product.imagePath}" />' alt='<c:out value="${product.name}" />.jpg' />
									</td>
									<td align="left" colspan="3">
										<div id="title"><c:out value="${item.product.name}"/></div>
									</td>
								</tr>
								<tr>
									<td align="left">
										<b>Unit Price: </b><c:out value="${item.product.getPriceFormatted()}"/>
									</td>
									<td align="center">
										<b>Total: </b><c:out value="${item.getPriceFormatted()}"/>
									</td>
									<td align="right">
										<form method="post" action="./cart/edit/${item.id}">
											<b>Count: </b><input type="text" name="count" onkeypress="if(isNaN(event.key)) event.preventDefault();" disabled value='<c:out value="${item.count}"/>' size="5" />
											<button type="button" onclick="change(this, <c:out value='${item.count}' />,  <c:out value='${item.product.count}'/>);">Edit</button>
										</form>
									</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</td>
			<td align="right">
				<div id="checkout">
					<div><span>Items: </span><c:out value="${cart.items.size()}"/></div>
					<div><span>Total: </span><c:out value="${cart.getPriceFormatted()}"/></div>
					<button onclick="window.location='/buyer/checkout'" <c:if test="${cart.items.size() == 0}">disabled</c:if>>Checkout</button><br/>
					<button onclick="window.location='/buyer'" >Continue Shopping</button>
				</div>
			</td>
		</tr>
	</table>
	<c:import url="../footer.jsp" />
	<script lang="text/javascript" src="../js/cart.js"></script>
</body>
</html>