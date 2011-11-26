
<%@ page import="orderlord.website.Customer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'customer.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="userName" title="${message(code: 'customer.userName.label', default: 'User Name')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'customer.password.label', default: 'Password')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'customer.email.label', default: 'Email')}" />
                        
                            <g:sortableColumn property="firstName" title="${message(code: 'customer.firstName.label', default: 'First Name')}" />
                        
                            <g:sortableColumn property="lastName" title="${message(code: 'customer.lastName.label', default: 'Last Name')}" />
                        
                            <g:sortableColumn property="balance" title="${message(code: 'customer.balance.label', default: 'Balance')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${customerInstanceList}" status="i" var="customerInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${customerInstance.id}">${fieldValue(bean: customerInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: customerInstance, field: "userName")}</td>
                        
                            <td>${fieldValue(bean: customerInstance, field: "password")}</td>
                        
                            <td>${fieldValue(bean: customerInstance, field: "email")}</td>
                        
                            <td>${fieldValue(bean: customerInstance, field: "firstName")}</td>
                        
                            <td>${fieldValue(bean: customerInstance, field: "lastName")}</td>
                        
                            <td>${fieldValue(bean: customerInstance, field: "balance")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${customerInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
