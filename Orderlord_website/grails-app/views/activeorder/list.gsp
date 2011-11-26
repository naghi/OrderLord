
<%@ page import="orderlord.website.Activeorder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activeorder.label', default: 'Activeorder')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <script type="text/javascript">
        	function checkDB()
        	{
        		var xmlhttp;
        		var xmlDoc;
        		var x;
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
        			xmlDoc=xmlhttp.responseText;
        			//alert("aaaaaaa")
        			//alert(xmlDoc)
        			x=xmlDoc.getElementsByTagName("table");
        			//alert(x)
        			//alert("bbbbbb")
        			for (i=0;i<x.length;i++)
        		      {
        		      txt = x[i].childNodes[0].nodeValue;
        		      }
        			//alert(txt)    			  
        		    document.getElementById("refreshTable").innerHTML=txt;
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
        	window.load = checkDB();
        </script>
    </head>
    
    <body>
    
      <!-- div id="myDiv"></div-->
      <!-- button type="button" onclick="checkDB()">Inject Content</button-->
      
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
           <g:if test="${session?.store?.admin}">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
           </g:if>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="refreshTable">
                <table>
                    <thead>
                        <tr>
                           <g:if test="${session?.store?.admin}">
                            <g:sortableColumn property="id" title="${message(code: 'activeorder.id.label', default: 'Id')}" />
                           </g:if>
                            <g:sortableColumn property="pickupTime" title="${message(code: 'activeorder.pickupTime.label', default: 'Pickup Time')}" />
                        
                            <g:sortableColumn property="orderEtp" title="${message(code: 'activeorder.orderEtp.label', default: 'Order Etp')}" />
                        
                            <g:sortableColumn property="totalCost" title="${message(code: 'activeorder.totalCost.label', default: 'Total Cost')}" />
                        
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
