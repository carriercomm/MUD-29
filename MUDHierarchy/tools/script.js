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
		console.log("File Index XMLHttp content received");
		fileIndex = JSON.parse(xmlhttp.responseText);
		loadJson(fileIndex.FileTypes[0].Path, "pageJson");	// load page contents
		
		document.getElementById("sidebar").innerHTML = '';
		for(var member in fileIndex.FileTypes)
		{
			var name = fileIndex.FileTypes[member].Name;
			console.log("adding: " + name);
			
			var button = document.createElement("input");
			button.setAttribute("type", "button");
			element.setAttribute("name", name);
			element.setAttribute("onclick", "buttonCallback()");
			document.getElementById("sidebar").appendChild(button);
		}
	}
}

function pageJsonCallback()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		console.log("XMLHttp content received");
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
	var matched = false;
	for(var member in fileIndex.FileTypes)
	{
		if(member.Name == name)
		{
			loadJson(member.Path, "pageJson");
			matched = true;
		}
	}
	if(!matched)
	{
		console.log("No valid page to load");
	}
}