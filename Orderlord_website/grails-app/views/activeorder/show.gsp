
<%@ page import="orderlord.website.Activeorder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activeorder.label', default: 'Activeorder')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
           <g:if test="${session?.store?.admin}">
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
                            <td valign="top" class="name"><g:message code="activeorder.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: activeorderInstance, field: "id")}</td>
                        </tr>
                       </g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="activeorder.pickupTime.label" default="Pickup Time" /></td>
                            
                            <td valign="top" class="value">${activeorderInstance.toDisplayDateTime()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="activeorder.orderEtp.label" default="Order Etp" /></td>
                            
                            <td valign="top" class="value">${activeorderInstance.orderEtp} min</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="activeorder.totalCost.label" default="Total Cost" /></td>
                            
                            <td valign="top" class="value">$${activeorderInstance.totalCost}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="activeorder.customer.label" default="Customer" /></td>
                            
                            <td valign="top" class="value"><g:link controller="customer" action="show" id="${activeorderInstance?.customer?.id}">${activeorderInstance?.customer?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="activeorder.store.label" default="Store" /></td>
                            
                            <td valign="top" class="value"><g:link controller="store" action="show" id="${activeorderInstance?.store?.id}">${activeorderInstance?.store?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="activeorder.items.label" default="Items" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${activeorderInstance.items}" var="i">
                                    <li><g:link controller="item" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${activeorderInstance?.id}" />
                   <g:if test="${session?.store?.admin}">
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                   </g:if>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
