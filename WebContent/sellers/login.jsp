<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login | Sellers</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<link rel="stylesheet" type="text/css" href="../css/login.css" />
</head>
<body>
	
	<div class="form">
		<h3 id="header">Welcome, EShoppers Sellers</h3>
		<div>
			<label class="control-label" for="email">Email ID</label>
			<input class="control" type="email" id="email" name="email" placeholder="abc@xyz.com" required/> 
		</div>
		<div>
			<label class="control-label" for="passwd">Password</label>
			<input class="control" type="password" id="passwd" name="passwd" required/> 
		</div>
		<div id="submit">
			<input type="button" id="submit" name="submit" onclick="validate_seller('login')" value="Log In" /> 
		</div>
		<div id="adds">
			New Account? <a href="./register">Register</a>
		</div>
	</div>
	
	<script type="text/javascript" src="../js/xhrRequests.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../js/handle_user.js"></script>
</body>
</html>