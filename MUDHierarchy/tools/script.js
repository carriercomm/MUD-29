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
		document.getElementById("sidebar").innerHTML = '';	// clear the sidebar contents
		document.getElementById("content").innerHTML = '';	// clear the page contents
		fileIndex = JSON.parse(xmlhttp.responseText);		// set the file Index vale for the page to use
		loadJson(fileIndex.FileTypes[0].Path, "pageJson");	// load page contents .json
		
		var title = fileIndex.FileTypes[0].Name	// get page title
		document.getElementById("content").innerHTML += '<h2>' + title + '</h2>';	// set page title
		
		for(var member in fileIndex.FileTypes)	//  for each index file
		{
			var name = fileIndex.FileTypes[member].Name;	// get its name and a make a button out of it
			console.log("adding: " + name);
			
			var button = document.createElement("input");
			button.setAttribute("type", "button");
			button.setAttribute("id", "index-button");
			button.setAttribute("onclick", "buttonCallback(\"" + name + "\")");
			button.setAttribute("value", name);
			document.getElementById("sidebar").appendChild(button);	// add the button to the sidebar
			
			var br = document.createElement("br");
			document.getElementById("sidebar").appendChild(br);	// add a break after the button
		}
	}
}

function pageJsonCallback()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		console.log("XMLHttp content received");
		var jsonObj = JSON.parse(xmlhttp.responseText);
		
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
		if(fileIndex.FileTypes[member].Name == name)
		{
			loadJson(fileIndex.FileTypes[member].Path, "pageJson");
			matched = true;
		}
	}
	if(!matched)
	{
		console.log("No valid page to load");
	}
}