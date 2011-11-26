

<%@ page import="orderlord.website.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
           <!-- g:if test="${session?.store?.admin}"-->
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
           <!--  /g:if-->
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${itemInstance}">
            <div class="errors">
                <g:renderErrors bean="${itemInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${itemInstance?.id}" />
                <g:hiddenField name="version" value="${itemInstance?.version}" />
                <div class="dialog">
                    <table style="width: 800px;">
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="itemName"><g:message code="item.itemName.label" default="Item Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'itemName', 'errors')}">
                                    <g:textField name="itemName" value="${itemInstance?.itemName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="price"><g:message code="item.price.label" default="Price" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'price', 'errors')}">
                                    $<g:textField name="price" value="${fieldValue(bean: itemInstance, field: 'price')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="itemEtp"><g:message code="item.itemEtp.label" default="Item Etp" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'itemEtp', 'errors')}">
                                    <g:textField name="itemEtp" value="${fieldValue(bean: itemInstance, field: 'itemEtp')}" />min
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="item.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${fieldValue(bean: itemInstance, field: 'description')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="linkToPic"><g:message code="item.linkToPic.label" default="Link To Pic" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'linkToPic', 'errors')}">
                                    <g:textField name="linkToPic" value="${itemInstance?.linkToPic}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="store"><g:message code="item.store.label" default="Store" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'store', 'errors')}">
                                   <g:if test="${session?.store?.admin}">
                                	<g:select name="store.id" from="${orderlord.website.Store.list()}" optionKey="id" value="${itemInstance?.store?.id}"  />
                                   </g:if>
                                   <g:if test="${!session?.store?.admin}">
                                    <g:select name="store.id" from="${orderlord.website.Store.findById(session.store.id)}" optionKey="id" value="${itemInstance?.store?.id}"  />
                                   </g:if>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                   <!-- g:if test="${session?.store?.admin}"-->
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                   <!-- /g:if-->
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
