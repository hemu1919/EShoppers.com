function validate_seller(action) {
	switch(action) {
		case 'register':validate_seller_register();
						break;
		case 'login': validate_seller_login();
					break;
	}
}

function validate_buyer(action) {
	switch(action) {
	case 'register':validate_buyer_register();
					break;
	case 'login': validate_buyer_login();
				break;
	}
}

function validate_seller_register() {
	let company_name = document.getElementById("company");
	let brand_name = document.getElementById("brand");
	let email = document.getElementById("email");
	let passwd = document.getElementById("passwd");
	let re_passwd = document.getElementById("re-passwd");
	re_passwd.setCustomValidity(re_passwd.value === '' || re_passwd.value === passwd.value ? '' : "Password did not match");
	let flag = validate_forms();
	flag = flag && (re_passwd.value === passwd.value);
	if(!flag) {
		return;
	}
	let params = "company_name=" + company_name.value + "&brand_name=" + brand_name.value + "&email=" + email.value +
		"&passwd=" + passwd.value;
	let url = getURL("/seller/register");
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		if(status == 200) {
			let redirectURL = getURL("/seller/login");
			let message = data + "<br/>Redirecting to Log In page. If automatically not redirected click <a href='./login'>LogIn</a>";
			document.write(message);
			setTimeout(() => {window.open(redirectURL, '_self');}, 2000);
		} else {
			alert(data);
		}
	});
}

function validate_buyer_register() {
	let fname = document.getElementById("fname");
	let lname = document.getElementById("lname");
	let email = document.getElementById("email");
	let passwd = document.getElementById("passwd");
	let re_passwd = document.getElementById("re-passwd");
	let addr_l1 = document.getElementById("addr-l1");
	let addr_l2 = document.getElementById("addr-l2");
	let city = document.getElementById("city");
	let state = document.getElementById("state");
	let pincode = document.getElementById("pincode");
	let mobile = document.getElementById("mobile");
	re_passwd.setCustomValidity(re_passwd.value === '' || re_passwd.value === passwd.value ? '' : "Password did not match");
	let flag = validate_forms();
	flag = flag && (re_passwd.value === passwd.value);
	
	if(!flag) {
		return;
	}
	
	let addr = addr_l1.value + "<br/>";
	if(addr_l2.value.length != 0) addr += addr_l2.value + "<br/>";
	addr += city.value + "<br/>" + state.value;
	
	let params = "fname=" + fname.value + "&lname=" + lname.value + "&email=" + email.value + "&passwd=" + passwd.value + 
		"&addr=" + addr + "&pincode=" + pincode.value + "&mobile=" + mobile.value;
	let url = getURL("/buyer/register");
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		if(status == 200) {
			let redirectURL = getURL("/buyer/login");
			let message = data + "<br/>Redirecting to Log In page. If automatically not redirected click <a href='./login'>LogIn</a>";
			document.write(message);
			setTimeout(() => {window.open(redirectURL, '_self');}, 2000);
		} else {
			alert(data);
		}
	});
}

function validate_seller_login() {
	let email = document.getElementById("email");
	let passwd = document.getElementById("passwd");
	let flag = validate_forms();
	if(!flag) {
		return;
	}
	let params = "email=" + email.value + "&passwd=" + passwd.value;
	let url = getURL("/seller/login");
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		if(status == 200) {
			window.open(getURL(data), '_self');
		} else {
			alert(data);
		}
	});
}

function validate_buyer_login() {
	let email = document.getElementById("email");
	let passwd = document.getElementById("passwd");
	let flag = validate_forms();
	if(!flag) {
		return;
	}
	let params = "email=" + email.value + "&passwd=" + passwd.value;
	let url = getURL("/buyer/login");
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		if(status == 200) {
			window.open(getURL(data), '_self');
		} else {
			alert(data);
		}
	});
}

function validate_forms() {
	let elements = document.getElementsByTagName("input");
	let flag = true;
	for(let i = 0; i < elements.length-1; i++) {
		if(elements[i].type === "email") {
			elements[i].setCustomValidity(validateEmail(elements[i].value) ? '' : "Provide a valid email");
		} else if(elements[i].getAttribute('password') === "") {
			let temp = validatePassword(elements[i].value);
			elements[i].setCustomValidity(temp.status ? '' : temp.message);
		} else if(elements[i].getAttribute('pincode') === "") {
			elements[i].setCustomValidity(validateNumber(elements[i].value) ? '' : "Provide a valid pincode");
		} else if(elements[i].getAttribute('mobile') === "") {
			elements[i].setCustomValidity(validateNumber(elements[i].value)
					&& elements[i].value.length === 10 ? '' : "Provide a valid mobile number");
		}
		flag = flag && elements[i].checkValidity();
		if(!elements[i].checkValidity()) {
			elements[i].title = elements[i].validationMessage;
			elements[i].className = "has-error";
		} else {
			elements[i].className = "";
			elements[i].title = "";
		}
	}
	return flag;
}