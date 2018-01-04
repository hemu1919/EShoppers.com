<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Purchase | EShoppers</title>
<link rel="stylesheet" lang="text/css" href="../css/main.css"/>
<link rel="stylesheet" lang="text/css" href="../css/checkout.css"/>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:import url="../buyers/header.jsp" />
	<form id="form" method="post" action="./checkout"></form>
	<h3>Select any option below to continue</h3>
	<button onclick="document.getElementById('form').submit()" <c:if test="${cart.items.size() == 0}">disabled</c:if>>Confirm Purchase</button>
	<button onclick="window.location='/buyer/cart'">Edit Cart</button>
	<button onclick="window.location='/buyer'">Continue Shopping</button>
	<c:import url="../footer.jsp" />
</body>
</html>