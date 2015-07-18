var xmlhttp = new XMLHttpRequest();
var urlArr = 	[];
urlArr =		["http://localhost/PortalOutline.json"];
//urlArray =		["res/AiOutline.json",
//				 "res/ClassOutline.json",
//				 "res/ConsumableItemOutline.json",
//				 "res/ContainerItemOutline.json",
//				 "res/EquipableItemOutline.json",
//				 "res/LocationOutline.json",
//				 "res/NpcOutline.json",
//				 "res/PcOutline.json",
//				 "res/PortalOutline.json",
//				 "res/SliceOutline.json"];

window.onload = function()
{
	compatabilityCheck();
	
	document.getElementById("id01").innerHTML += '<p>' + "Var initialization done..." + '</p></br>';
	
	for(var url in urlArr)
	{
		xmlhttp.open("GET", url, true);
		xmlhttp.overrideMimeType("application/json");
		xmlhttp.send();
	}
}
				 
xmlhttp.onreadystatechange = function()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		document.getElementById("id01").innerHTML += '<p>' + "XMLHttp ready..." + '</p></br>';
		var jarr = JSON.parse(xmlhttp.responseText);
		myFunction(jarr);
	}
}

function myFunction(arr)
{
	document.getElementById("id01").innerHTML += '<p>' + "XMLHttp sent..." + '</p></br>';
	document.getElementById("id01").innerHTML += '<h1>' + arr[i] + '</h1></br>';
}

function compatabilityCheck()
{
	if (window.File && window.FileReader && window.FileList && window.Blob)
	{
		//document.getElementById("body").innerHTML += "<h1>Browser is compatable</h1>";
	}
	else
	{
		alert('The File APIs are not fully supported in this browser.');
	}
}