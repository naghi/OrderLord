
<%@ page import="orderlord.website.Activeorder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activeorder.label', default: 'Activeorder')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        
        <script type="text/javascript">
        	function ajaxrefresh()
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
        			//alert("aaaaaaa") 			  
        		    document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
        		    }
        		  else
        			  {
        			  //alert("state: "+xmlhttp.readyState)
        			  //alert("status: "+xmlhttp.status)
        			  }
        		  }
        		
        		xmlhttp.open("GET","http://${localHostAddress}:12080/Orderlord/refresh/refreshactiveorders",true);
        		//xmlhttp.open("GET","http://107.20.135.212:12080/Orderlord/refresh/refreshactiveorders",true);
        		xmlhttp.send();

        		var t=setTimeout(ajaxrefresh,4000); //***
            }

        	window.load = ajaxrefresh();
        </script>
        
    </head>
    
    <!-- body onLoad="ajaxrefresh();"-->
    <body>
    
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
           <g:if test="${session?.store?.admin}">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
           </g:if>
        </div>
        
        <div class="body" id="myDiv">
            <!-- h1><g:message code="default.list.label (ordered by ascending pickTime)" args="[entityName]" /></h1-->
            <h1><g:message code="Activeorder List (ordered by ascending pickupTime)" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="refreshTable">
                <table>
                    <thead>
                        <tr>
                        
		                   <g:if test="${session?.store?.admin}">
		                    <!-- g:sortableColumn property="id" title="${message(code: 'activeorder.id.label', default: 'Id')}" /-->
		                    <th><g:message code="activeorder.id.label" default="Id" /></th>
		                   </g:if>
		                    <th><g:message code="activeorder.pickupTime.label" default="Pickup Time" /></th>
		                
		                    <th><g:message code="activeorder.orderEtp.label" default="Order ETP" /></th>
		                
		                    <th><g:message code="activeorder.totalCost.label" default="Total Cost" /></th>
		                
		                    <th><g:message code="activeorder.customer.label" default="Customer" /></th>
		                
		                    <th><g:message code="activeorder.store.label" default="Store" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${activeorderInstanceList}" status="i" var="activeorderInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                           <g:if test="${session?.store?.admin}">
                            <td><g:link action="show" id="${activeorderInstance.id}">${fieldValue(bean: activeorderInstance, field: "id")}</g:link></td>
                           </g:if>
                        
                            <!-- td><g:formatDate date="${activeorderInstance.pickupTime}" /></td-->
                            
                            <td><g:link action="show" id="${activeorderInstance.id}">${activeorderInstance.toDisplayDateTime()}</g:link></td>
                        
                            <td>${activeorderInstance.orderEtp} min</td>
                        
                            <td>$${activeorderInstance.totalCost}</td>
                        
                            <td>${fieldValue(bean: activeorderInstance, field: "customer")}</td>
                        
                            <td>${fieldValue(bean: activeorderInstance, field: "store")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${activeorderInstanceTotal}" />
            </div>
        </div>
        
    </body>
</html>
