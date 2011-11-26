
<%@ page import="orderlord.website.Store" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'store.label', default: 'Store')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'store.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="storeName" title="${message(code: 'store.storeName.label', default: 'Store Name')}" />
                        
                            <g:sortableColumn property="login" title="${message(code: 'store.login.label', default: 'Login')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'store.password.label', default: 'Password')}" />
                        
                            <g:sortableColumn property="role" title="${message(code: 'store.role.label', default: 'Role')}" />
                        
                            <g:sortableColumn property="storeAddress" title="${message(code: 'store.storeAddress.label', default: 'Store Address')}" />
                            
                            <g:sortableColumn property="latitude" title="${message(code: 'store.latitude.label', default: 'Latitude')}" />
                            
                            <g:sortableColumn property="longitude" title="${message(code: 'store.longitude.label', default: 'Longitude')}" />
                            
                            <g:sortableColumn property="phoneNumber" title="${message(code: 'store.phoneNumber.label', default: 'Phone Number')}" />
                        
                            <g:sortableColumn property="linkToPic" title="${message(code: 'store.linkToPic.label', default: 'Link To Pic')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${storeInstanceList}" status="i" var="storeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${storeInstance.id}">${fieldValue(bean: storeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: storeInstance, field: "storeName")}</td>
                        
                            <td>${fieldValue(bean: storeInstance, field: "login")}</td>
                        
                            <td>${fieldValue(bean: storeInstance, field: "password")}</td>
                        
                            <td>${fieldValue(bean: storeInstance, field: "role")}</td>
                        
                            <td>${fieldValue(bean: storeInstance, field: "storeAddress")}</td>
                            
                            <td>${fieldValue(bean: storeInstance, field: "latitude")}</td>
                            
                            <td>${fieldValue(bean: storeInstance, field: "longitude")}</td>
                            
                            <td>${fieldValue(bean: storeInstance, field: "phoneNumber")}</td>
                        
                            <td>${fieldValue(bean: storeInstance, field: "linkToPic")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${storeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
