var xmlhttp = new XMLHttpRequest();

var urlArray = 	[];
urlArray =		["res/PortalOutline.json",
				 "res/AiOutline.json",
				 "res/ClassOutline.json",
				 "res/ConsumableItemOutline.json",
				 "res/ContainerItemOutline.json",
				 "res/EquipableItemOutline.json",
				 "res/LocationOutline.json",
				 "res/NpcOutline.json",
				 "res/PcOutline.json",
				 "res/PortalOutline.json",
				 "res/SliceOutline.json"];

function getJsonContents(jsonObj)
{
	Console.log("XMLHttp sent...");
	for(var member in jsonObj)
	{
		document.getElementById("id01").innerHTML += '<p>' + member + ': ' + jsonObj[member] + '</p>';
	}
}

xmlhttp.onreadystatechange = function()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		var jsonObj = JSON.parse(xmlhttp.responseText);
		getJsonContents(jsonObj);
	}
}

window.onload = function()
{
	recursiveLoad(0);
}

function recursiveLoad(currentFile)
{
	if(currentFile < urlArr.length)
	{
		xmlhttp.open("GET", urlArr[currentFile], true);
		xmlhttp.overrideMimeType("application/json");
		xmlhttp.send();
	}
}