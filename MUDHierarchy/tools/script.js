var xmlhttp = new XMLHttpRequest();
var fileIndex = null;

window.onload = function()
{
	jsonIndex = loadJson("index.json", "indexJson");
}

function loadJson(path, processAs)
{
	switch(processAs)
	{
		case "indexJson":
			xmlhttp.onreadystatechange = indexJsonCallback;
		break;
		case "pageJson":
			xmlhttp.onreadystatechange = pageJsonCallback;
		break;
		default:
			console.log("Error: undefined processing flag");
		break;
	}

	xmlhttp.open("GET", path, true);
	xmlhttp.overrideMimeType("application/json");
	xmlhttp.send();
	console.log(path + " XMLHttp requested...");
}

function indexJsonCallback()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		console.log("File Index XMLHttp content received...");
		fileIndex = JSON.parse(xmlhttp.responseText);
		loadJson(fileIndex.Path, "pageJson");	// load page contents
		
		document.getElementById("sidebar").innerHTML = '';
		for(var member in fileIndex)
		{
			document.getElementById("sidebar").innerHTML += 
			'<input id="index-button" type="button" value=' + member.Name + ' onclick="buttonCallback(' + member.Name + ');" />';
		}
	}
}

function pageJsonCallback()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		console.log("XMLHttp content received...");
		var jsonObj = JSON.parse(xmlhttp.responseText);
		document.getElementById("content").innerHTML = '';
		for(var member in jsonObj)
		{
			document.getElementById("content").innerHTML += '<p>' + member + ': ' + jsonObj[member] + '</p>';
		}
	}
}

function buttonCallback(name)
{
	for(var member in fileIndex)
	{
		if(member.Name == name)
		{
			loadJson(member.Path, "pageJson");
		}
	}
}