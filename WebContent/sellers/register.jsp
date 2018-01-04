<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register | Sellers</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<link rel="stylesheet" type="text/css" href="../css/login.css" />
</head>
<body>
	<div class="form">
		<h3 id="header">Welcome, EShoppers Sellers</h3>
		<div>
			<label class="control-label" for="company">Company Name</label>
			<input type="text" id="company" name="company" placeholder="ABC Inc." required/> 
		</div>
		<div>
			<label class="control-label" for="brand">Brand Name</label>
			<input type="text" id="brand" name="brand" placeholder="ABC" required/> 
		</div>	
		<div>
			<label class="control-label" for="email">Email ID</label>
			<input type="email" id="email" name="email" placeholder="abc@xyz.com" required/> 
		</div>
		<div>
			<label class="control-label" for="passwd">Password</label>
			<input type="password" id="passwd" name="passwd" password required/> 
		</div>
		<div>
			<label class="control-label" for="re-passwd">Retype Password</label>
			<input type="password" id="re-passwd" name="re-passwd" required/>
		</div>
		<div id="submit">
			<input type="button" id="submit" name="submit" onclick="validate_seller('register');" value="Register" /> 
		</div>
		<div id="adds">
			Already Registered? <a href="./login">Log In</a>
		</div>
	</div>
	
	<script type="text/javascript" src="../js/xhrRequests.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../js/handle_user.js"></script>
</body>
</html>