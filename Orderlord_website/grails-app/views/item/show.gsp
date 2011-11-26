
<%@ page import="orderlord.website.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
           <!-- g:if test="${session?.store?.admin}"-->
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
           <!-- /g:if-->
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
                            <td valign="top" class="name"><g:message code="item.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: itemInstance, field: "id")}</td>
                        </tr>
                       </g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="item.itemName.label" default="Item Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: itemInstance, field: "itemName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="item.price.label" default="Price" /></td>
                            
                            <td valign="top" class="value">$${fieldValue(bean: itemInstance, field: "price")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="item.itemEtp.label" default="Item Etp" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: itemInstance, field: "itemEtp")} min</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="item.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: itemInstance, field: "description")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="item.linkToPic.label" default="Link To Pic" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: itemInstance, field: "linkToPic")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="item.store.label" default="Store" /></td>
                            
                            <td valign="top" class="value"><g:link controller="store" action="show" id="${itemInstance?.store?.id}">${itemInstance?.store?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${itemInstance?.id}" />
                   <!-- g:if test="${session?.store?.admin}"-->
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                   <!-- /g:if-->
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
