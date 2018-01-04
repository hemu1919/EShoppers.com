var base_url = "";

function getURL(path) {
	return base_url + path;
}
	
function validateEmail(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validatePassword(passwd) {
	if(passwd.length > 0 && passwd.length < 6) {
		return {status: false, message: "Password should be atleast 6 characters"};
	}
	return {status: true, message: ''};
}

function validateNumber(number) {
	return !isNaN(number);
}