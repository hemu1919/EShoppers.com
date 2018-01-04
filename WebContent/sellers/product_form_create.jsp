<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Form</title>
<link rel="stylesheet" type="text/css" href="../../css/main.css" />
</head>
<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<c:import url="../sellers/header.jsp" />
	<div class="form">
		<h2 id="adds">Product Creation Form</h2>
		<div>
			<label class="control-label" for="name">Name</label>
			<input type="text" placeholder="Enter product name" name="name" id="name" required/>
		</div>
		<div>
			<label class="control-label" for="type">Product Type</label>
			<select id="type">
				<option value="">Select a type</option>
				<option value="books">Books</option>
				<option value="electronics">Electronics</option>
				<option value="movies">Movies</option>
			</select>
		</div>
		<div>
			<label class="control-label" for="count">Production Count</label>
			<input type="text" placeholder="Availble Stock count" name="count" id="count" number required/>
		</div>
		<div>
			<label class="control-label" for="price">Price</label>
			<input type="text" placeholder="Enter product price in $" id="price" name="price" number required/>
		</div>
		<div>
			<label class="control-label" for="desc">Description</label>
			<textarea rows="2" cols="22" placeholder="Describe product" id="desc" name="desc" required></textarea>
		</div>
		<form enctype="multipart/form-data" action="./store" method="post" id="product_form">
			<div>
				<label class="control-label" for="photos">Select Image</label>
				<input type="file" id="photos" name="photos" accept=".jpg, .png" required/>
			</div>
		</form>
		<div id="submit">
			<input type="button" onclick="validateProduct()" id="create" value="Create Product"/>
		</div>
	</div>
	<c:import url="../footer.jsp" />
	<script type="text/javascript" src="../../js/xhrRequests.js"></script>
	<script type="text/javascript" src="../../js/utils.js"></script>
	<script type="text/javascript" src="../../js/handle_product.js"></script>
</body>
</html>