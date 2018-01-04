function remove(id) {
	let params = "id=" + id;
	let url = getURL("/seller/dashboard/remove");
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		if(status == 200) {
			location.reload();
		} else {
			alert(data);
		}
	});
}

function publish(id, flag) {
	let params = "id=" + id + "&flag=" + flag;
	let url = getURL("/seller/dashboard/publish");
	let method = "post";
	let headers = [{name: "Content-Type", value: "application/x-www-form-urlencoded; charset=UTF-8"}];
	serviceXHR(url, method, params, headers, (data, status, message) => {
		if(status == 200) {
			location.reload();
		} else {
			alert(data);
		}
	});
}