
<%@ page import="orderlord.website.Store" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'store.label', default: 'Store')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
           <g:if test="${session?.store?.admin}">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
           </g:if>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table style="width: 800px;">
                    <tbody>
                    
                       <g:if test="${session?.store?.admin}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "id")}</td>
                        </tr>
                       </g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.storeName.label" default="Store Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "storeName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.login.label" default="Login" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "login")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.password.label" default="Password" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "password")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.role.label" default="Role" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "role")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.storeAddress.label" default="Store Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "storeAddress")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.latitude.label" default="Latitude" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "latitude")}</td>
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.longitude.label" default="Longitude" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "longitude")}</td>
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.phoneNumber.label" default="Phone Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "phoneNumber")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.linkToPic.label" default="Link To Pic" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: storeInstance, field: "linkToPic")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.activeorders.label" default="Activeorders" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${storeInstance.activeorders}" var="a">
                                    <li><g:link controller="activeorder" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.items.label" default="Items" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${storeInstance.items}" var="i">
                                    <li><g:link controller="item" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    <g:if test="${session?.store?.admin}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="store.pendingorders.label" default="Pendingorders" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${storeInstance.pendingorders}" var="p">
                                    <li><g:link controller="pendingorder" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                     </g:if>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${storeInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                   <g:if test="${session?.store?.admin}">
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                   </g:if>
                </g:form>
            </div>
        </div>
    </body>
</html>
