

<%@ page import="orderlord.website.Store" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'store.label', default: 'Store')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${storeInstance}">
            <div class="errors">
                <g:renderErrors bean="${storeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table style="width: 800px;">
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="storeName"><g:message code="store.storeName.label" default="Store Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'storeName', 'errors')}">
                                    <g:textField name="storeName" value="${storeInstance?.storeName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="login"><g:message code="store.login.label" default="Login" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'login', 'errors')}">
                                    <g:textField name="login" maxlength="15" value="${storeInstance?.login}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="store.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" maxlength="15" value="${storeInstance?.password}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="role"><g:message code="store.role.label" default="Role" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'role', 'errors')}">
                                    <g:select name="role" from="${storeInstance.constraints.role.inList}" value="${storeInstance?.role}" valueMessagePrefix="store.role"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="storeAddress"><g:message code="store.storeAddress.label" default="Store Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'storeAddress', 'errors')}">
                                    <g:textField name="storeAddress" value="${storeInstance?.storeAddress}" />
                                </td>
                            </tr>
                            
                            <!-- tr class="prop">
                                <td valign="top" class="name">
                                    <label for="latitude"><g:message code="store.latitude.label" default="Latitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'latitude', 'errors')}">
                                    <g:textField name="latitude" value="${storeInstance?.latitude}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="longitude"><g:message code="store.longitude.label" default="Longitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'longitude', 'errors')}">
                                    <g:textField name="longitude" value="${storeInstance?.longitude}" />
                                </td>
                            </tr -->
                        	
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="phoneNumber"><g:message code="store.phoneNumber.label" default="Phone Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'phoneNumber', 'errors')}">
                                    <g:textField name="phoneNumber" value="${storeInstance?.phoneNumber}" />
                                </td>
                            </tr>
                        	
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="linkToPic"><g:message code="store.linkToPic.label" default="Link To Pic" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: storeInstance, field: 'linkToPic', 'errors')}">
                                    <g:textField name="linkToPic" value="${storeInstance?.linkToPic}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                   <g:if test="${session?.store?.admin}">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                   </g:if>
                </div>
            </g:form>
        </div>
    </body>
</html>
