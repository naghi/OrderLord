<html>
<head>
<script type="text/javascript">
function loadXMLDoc()
{
var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
    }
  else
	  {
	  //alert("state: "+xmlhttp.readyState)
	  //alert("status: "+xmlhttp.status)
	  }
  }
xmlhttp.open("GET","http://localhost:8080/Orderlord/checkdb/checkdb",true);
xmlhttp.send();
}
</script>
</head>
<body>

	<div id="myDiv">
		<h2>Let AJAX change this text</h2>
	</div>
	<button type="button" onclick="loadXMLDoc()">Change Content</button>

</body>
</html>