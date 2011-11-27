<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'orderlord.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div style="width: 700px; margin-top:15px; margin-left:15px;" id="orderlord_logo">
           <!-- a href="http://107.20.135.212:12080/Orderlord_website/"> -->
        	<img style="width: 200px; height: 200px;" src="${resource(dir:'images',file:'orderlord.jpg')}" alt="Grails" border="0" />
           <!--  /a>-->
        	<div style="float: right; width: 500px; margin-top:80px;"><text style="font-weight:bold; font-size:24px; color:rgb(160,50,110)">The Orderlord welcomes you!</text></div>
        </div>
        <div style="margin-left: 50px; width: 100px;">ver. 1.0</div>
        
        <!-- g:render template="/layouts/header" /-->

		<div id="loginHeader">
			<g:loginControl />
		</div>

	<!-- a href="http://grails.org" -->
        <g:layoutBody />
        
        <g:render template="/layouts/footer" />
    </body>
    
</html>