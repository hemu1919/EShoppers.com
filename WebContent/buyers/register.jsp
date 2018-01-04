<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register | Buyers</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<link rel="stylesheet" type="text/css" href="../css/login.css" />
<link rel="stylesheet" type="text/css" href="../css/register.css" />
</head>
<body>
	<div class="form">
		<h3 id="header">Welcome, EShoppers Buyers</h3>
		<div>
			<label class="control-label" for="fname">First Name</label>
			<input type="text" id="fname" name="fname" placeholder="XYZ" required/> 
		</div>
		<div>
			<label class="control-label" for="lname">Last Name</label>
			<input type="text" id="lname" name="laname" placeholder="ABC" required/> 
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
		<div>
			<label class="control-label" for="addr-l1">Address Line1</label>
			<input type="text" id="addr-l1" name="addr-l1" placeholder="ABC" required/> 
		</div>
		<div>
			<label class="control-label" for="addr-l2">Address Line2</label>
			<input type="text" id="addr-l2" name="addr-l2" placeholder="ABC" />
		</div>
		<div>
			<label class="control-label" for="city">City</label>
			<input type="text" id="city" name="city" placeholder="ABC" required/> 
		</div>
		<div>
			<label class="control-label" for="state">State</label>
			<input type="text" id="state" name="state" placeholder="ABC" required/> 
		</div>
		<div>
			<label class="control-label" for="pincode">ZIP code</label>
			<input type="text" id="pincode" name="pincode" placeholder="ABC" pincode required/> 
		</div>
		<div>
			<label class="control-label" for="mobile">Mobile</label>
			<input type="text" id="mobile" name="mobile" onkeypress="if(this.value.length === 10) event.preventDefault();" placeholder="ABC" mobile required/> 
		</div>
		<div id="submit">
			<input type="button" id="submit" name="submit" onclick="validate_buyer('register');" value="Register" /> 
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