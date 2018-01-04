
// Function that handles all ajax based requests in the application.

function serviceXHR(url, method, params, headers, callback) {
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (this.readyState == 4) {
            callback(this.responseText, this.status, this.statusText);
        }
    };

    if(method.toLowerCase() === "get") {
        url += "?" + params;
    }

    xhr.open(method.toUpperCase(), url, true);
    if(headers !== "") {
        xhr = setHeaders(xhr, headers);
    }

    if(method.toLowerCase() === "get") {
        xhr.send();
    }
    else {
        xhr.send(params);
    }
}

// Function to load the header information into the request object.

function setHeaders(xhr, headers) {
    for(let i=0;i < headers.length;i++) {
        xhr.setRequestHeader(headers[i].name, headers[i].value);
    }
    return xhr;
}