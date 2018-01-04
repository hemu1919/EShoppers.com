function validateProduct(id) {
	let elements = document.getElementsByTagName("input");
	let flag = true;
	let name = document.getElementById("name");
	let price = document.getElementById("price");
	let count = document.getElementById("count");
	let desc = document.getElementById("desc");
	let type = document.getElementById("type");
	let photos = document.getElementById("photos");
	for(let i = 0; i < elements.length-1; i++) {
		if(elements[i].getAttribute('number') === "") {
			elements[i].setCustomValidity(validateNumber(elements[i].value) ? '' : "Provide a valid number");
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
	if(desc.value !== "" && desc.value.length < 40) {
		desc.title = "Description should be atleast 40 charcters long.";
		desc.className = "has-error";
		flag = flag && false;
	} else if(desc.value === '') {
		desc.title = "Please fill out this field";
		desc.className = "has-error";
		flag && false;
	} else {
		desc.className = "";
		desc.title = "";
	}
	if(type.value === "") {
		flag = flag && false;
		type.title = "Select a product type";
		type.className = "has-error";
	} else {
		type.title = "";
		type.className = "";
	}
	if(!flag) {
		return;
	}
	let params = "name=" + name.value + "&price=" + price.value + "&desc=" + desc.value +
		"&count=" + count.value + "&type=" + type.value;
	let url = "";
	if(id === undefined) {
		url = getURL("/seller/dashboard/create");
	} else {
		params += "&id=" + id;
		url = getURL("/seller/dashboard/edit");
	}
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		document.getElementById("product_form").submit();
	});
}