function change(element, initial, max) {
	if(element.innerHTML === "Edit") {
		element.innerHTML = "Update";
		element.parentNode.children[1].disabled = false;
	} else {
		if(element.parentNode.children[1].value.length === 0) {
			element.parentNode.children[1].className="has-error";
			element.parentNode.children[1].title="Please Provide a valid number";
			element.parentNode.children[1].value = initial;
			return;
		}else if(element.parentNode.children[1].value > max)  {
			element.parentNode.children[1].className="has-error";
			element.parentNode.children[1].title="Only " + max + " stock is available";
			element.parentNode.children[1].value = max;
			return;
		}
		element.parentNode.submit();
	}
}