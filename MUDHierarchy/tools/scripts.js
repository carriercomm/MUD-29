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
	document.getElementById("id01").innerHTML += '<p>' + "Var initialization done..." + '</p>';
	xmlhttp.open("GET", url, true);
	xmlhttp.overrideMimeType("application/json");
}

function compatabilityCheck()
{
	if (window.File && window.FileReader && window.FileList && window.Blob)
	{
		document.getElementById("id01").innerHTML += "<p>Browser compatibility established...</p>";
	}
	else
	{
		alert('The File APIs are not fully supported in this browser.');
	}
}
				 
xmlhttp.onreadystatechange = function()
{
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		document.getElementById("id01").innerHTML += "<p>XMLHttp ready...</p>";
		for(var url in urlArr)
		{
			var jarr = JSON.parse(xmlhttp.responseText);
			myFunction(jarr);
		}
	}
}

function myFunction(arr)
{
	document.getElementById("id01").innerHTML += '<p>' + "XMLHttp sent..." + '</p>';
	document.getElementById("id01").innerHTML += '<h1>' + arr[i] + '</h1>';
}

xmlhttp.send();
// xmlhttprequest goes
// 1: construct
// 2: open
// 3: set handler
// 4: send