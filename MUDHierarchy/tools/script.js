var xmlhttp = new XMLHttpRequest();
var fileIndex = null;

window.onload = function()
{
	jsonIndex = loadJson("index.json", null, "indexJson");	// load the index
}

function loadJson(path, name, processAs)
{
	switch(processAs)	// change the processing callback for the json based on what is being loaded
	{
		case "indexJson":
			xmlhttp.onreadystatechange = indexJsonCallback;
		break;
		case "pageJson":
			xmlhttp.onreadystatechange = pageJsonCallback(name);
		break;
		default:
			console.log("Error: undefined processing flag");
		break;
	}

	xmlhttp.open("GET", path, true);	// send a xmlhttp request to retrieve the .json file from the server
	xmlhttp.overrideMimeType("application/json");	// why isn't this php? 
	xmlhttp.send();
	console.log(path + " XMLHttp requested...");
}

function indexJsonCallback()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		console.log("File Index XMLHttp content received");
		document.getElementById("sidebar").innerHTML = '';	// clear the sidebar contents
		fileIndex = JSON.parse(xmlhttp.responseText);		// parse the index file
		loadJson(fileIndex.FileTypes[0].Path, fileIndex.FileTypes[0].Name, "pageJson");	// start the starting page load
		
		for(var member in fileIndex.FileTypes)	//  for each index file
		{
			var name = fileIndex.FileTypes[member].Name;	// get the name and a make a button out of it
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

function pageJsonCallback(name)
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		console.log("XMLHttp content received");
		var jsonObj = JSON.parse(xmlhttp.responseText);	// parse page file
		document.getElementById("content").innerHTML = '';	// clear current page
		
		document.getElementById("content").innerHTML += '<h2>' + name + '</h2>';	// set page title
		for(var member in jsonObj)	// display page contents
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
			loadJson(fileIndex.FileTypes[member].Path, null, "pageJson");
			matched = true;
		}
	}
	if(!matched)
	{
		console.log("No valid page to load");
	}
}