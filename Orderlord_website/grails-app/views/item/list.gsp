
<%@ page import="orderlord.website.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
           <!-- g:if test="${session?.store?.admin}"-->
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
           <!-- /g:if-->
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                           <g:if test="${session?.store?.admin}">
                            <g:sortableColumn property="id" title="${message(code: 'item.id.label', default: 'Id')}" />
                           </g:if>
                        
                            <g:sortableColumn property="itemName" title="${message(code: 'item.itemName.label', default: 'Item Name')}" />
                        
                            <g:sortableColumn property="price" title="${message(code: 'item.price.label', default: 'Price')}" />
                        
                            <g:sortableColumn property="itemEtp" title="${message(code: 'item.itemEtp.label', default: 'Item Etp')}" />
                            
                            <g:sortableColumn property="description" title="${message(code: 'item.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="linkToPic" title="${message(code: 'item.linkToPic.label', default: 'Link To Pic')}" />
                        
                            <th><g:message code="item.store.label" default="Store" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemInstanceList}" status="i" var="itemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                           <g:if test="${session?.store?.admin}">
                            <td><g:link action="show" id="${itemInstance.id}">${fieldValue(bean: itemInstance, field: "id")}</g:link></td>
                           </g:if>
                        
                            <td><g:link action="show" id="${itemInstance.id}">${fieldValue(bean: itemInstance, field: "itemName")}</g:link></td>
                        
                            <td>$${fieldValue(bean: itemInstance, field: "price")}</td>
                        
                            <td>${fieldValue(bean: itemInstance, field: "itemEtp")} min</td>
                            
                            <td>${fieldValue(bean: itemInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: itemInstance, field: "linkToPic")}</td>
                        
                            <td>${fieldValue(bean: itemInstance, field: "store")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${itemInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
