
<%@ page import="orderlord.website.Pendingorder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pendingorder.label', default: 'Pendingorder')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
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
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'pendingorder.id.label', default: 'Id')}" />
                            
                            <th><g:message code="pendingorder.orderType.label" default="Order Type" /></th>
                            
                            <g:sortableColumn property="scheduleDay" title="${message(code: 'pendingorder.scheduleDay.label', default: 'Schedule Day')}" />
                        
                            <g:sortableColumn property="pickupTime" title="${message(code: 'pendingorder.pickupTime.label', default: 'Pickup Time')}" />
                        
                            <g:sortableColumn property="orderEtp" title="${message(code: 'pendingorder.orderEtp.label', default: 'Order Etp')}" />
                        
                            <g:sortableColumn property="totalCost" title="${message(code: 'pendingorder.totalCost.label', default: 'Total Cost')}" />
                        
                            <th><g:message code="pendingorder.customer.label" default="Customer" /></th>
                            
                            <th><g:message code="pendingorder.store.label" default="Store" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pendingorderInstanceList}" status="i" var="pendingorderInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pendingorderInstance.id}">${fieldValue(bean: pendingorderInstance, field: "id")}</g:link></td>
                            
                            <td>${pendingorderInstance.orderType}</td>
                            
                            <td>${pendingorderInstance.toDisplayDay()}</td>
                        
                            <td>${pendingorderInstance.toDisplayTime()}</td>
                        
                            <td>${pendingorderInstance.orderEtp} min</td>
                        
                            <td>$${pendingorderInstance.totalCost}</td>
                        
                            <td>${fieldValue(bean: pendingorderInstance, field: "customer")}</td>
                            
                            <td>${fieldValue(bean: pendingorderInstance, field: "store")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pendingorderInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
