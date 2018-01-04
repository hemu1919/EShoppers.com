<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Form</title>
<link rel="stylesheet" type="text/css" href="../../../css/main.css" />
</head>
<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<c:import url="../sellers/header.jsp" />
	
	<div class="form">
		<h2 id="adds">Prodcut Updation Form</h2>
		<div>
			<label class="control-label" for="name">Name</label>
			<input type="text" placeholder="Enter product name" value="<c:out value='${product.name}' />" name="name" id="name" required/>
		</div>
		<div>
			<label class="control-label" for="type">Product Type</label>
			<select id="type">
				<option value="">Select a type</option>
				<c:out value='${product.type.equals("electronics")}' />
				<c:choose>
					<c:when test="${product.type.equals('books')}">
						<option value="books" selected>Books</option>
					</c:when>
					<c:otherwise>
						<option value="books">Books</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${product.type.equals('electronics')}">
						<option value="electronics" selected>Electronics</option>
					</c:when>
					<c:otherwise>
						<option value="electronics">Electronics</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${product.type.equals('movies')}">
						<option value="movies" selected>Movies</option>
					</c:when>
					<c:otherwise>
						<option value="movies">Movies</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<div>
			<label class="control-label" for="count">Production Count</label>
			<input type="text" placeholder="Availble Stock count" value="<c:out value='${product.count}' />" name="count" id="count" number required/>
		</div>
		<div>
			<label class="control-label" for="price">Price</label>
			<input type="text" placeholder="Enter product price in $" value="<c:out value='${product.price}' />" id="price" name="price" number required/>
		</div>
		<div>
			<label class="control-label" for="desc">Description</label>
			<textarea rows="2" cols="20" placeholder="Describe product" id="desc" name="desc" required><c:out value='${product.desc}' /></textarea>
		</div>
		<form enctype="multipart/form-data" action="../store" method="post" id="product_form">
			<div>
				<label class="control-label" for="photos">Update Image</label>
				<input type="file" id="photos" name="photos" accept=".jpg, .png" />
			</div>
		</form>
		<div id="submit">
			<input type="button" onclick="validateProduct('<c:out value="${product.id}" />')" id="manage" value="Update Product"/>
		</div>
	</div>
	<c:import url="../footer.jsp" />
	<script type="text/javascript" src="../../../js/xhrRequests.js"></script>
	<script type="text/javascript" src="../../../js/utils.js"></script>
	<script type="text/javascript" src="../../../js/handle_product.js"></script>
</body>
</html>