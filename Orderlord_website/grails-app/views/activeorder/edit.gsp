

<%@ page import="orderlord.website.Activeorder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activeorder.label', default: 'Activeorder')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${activeorderInstance}">
            <div class="errors">
                <g:renderErrors bean="${activeorderInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${activeorderInstance?.id}" />
                <g:hiddenField name="version" value="${activeorderInstance?.version}" />
                <div class="dialog">
                    <table style="width: 800px;">
                        <tbody>
                        
                        <h4 style="margin-left:8px; margin-bottom:6px;">
                        	NOTE: The order will be set due in 6 days if you do not select any items.
                        </h4>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pickupTime"><g:message code="activeorder.pickupTime.label" default="Pickup Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'pickupTime', 'errors')}">
                                    <!-- g:datePicker name="pickupTime" precision="minute" value="${activeorderInstance?.pickupTime}"  /-->
                                    Editing active orders will auto-modify the pickupTime according to 'currentTime + orderETP'
                                </td>
                            </tr>
                        
                            <!-- tr class="prop">
                                <td valign="top" class="name">
                                  <label for="orderEtp"><g:message code="activeorder.orderEtp.label" default="Order Etp" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'orderEtp', 'errors')}">
                                    <g:textField name="orderEtp" value="${fieldValue(bean: activeorderInstance, field: 'orderEtp')}" />
                                </td>
                            </tr-->
                        
                            <!-- tr class="prop">
                                <td valign="top" class="name">
                                  <label for="totalCost"><g:message code="activeorder.totalCost.label" default="Total Cost" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'totalCost', 'errors')}">
                                    <g:textField name="totalCost" value="${fieldValue(bean: activeorderInstance, field: 'totalCost')}" />
                                </td>
                            </tr-->
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="customer"><g:message code="activeorder.customer.label" default="Customer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'customer', 'errors')}">
                                    <g:select name="customer.id" from="${orderlord.website.Customer.list()}" optionKey="id" value="${activeorderInstance?.customer?.id}"  />
                                </td>
                            </tr>
                        
                        	<!-- tr class="prop">
                                <td valign="top" class="name">
                                  <label for="store"><g:message code="activeorder.store.label" default="Store" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'store', 'errors')}">
                                    <g:select name="store.id" from="${orderlord.website.Store.list()}" optionKey="id" value="${activeorderInstance?.store?.id}"  />
                                </td>
                            </tr-->
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="items"><g:message code="activeorder.items.label" default="Items" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'items', 'errors')}">
                                    <g:select style="width: 650px;" name="items" from="${activeorderInstance.store ? orderlord.website.Item.findAllByStore(activeorderInstance.store) : []}" multiple="yes" optionKey="id" size="10" value="${activeorderInstance?.items*.id}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                   <g:if test="${session?.store?.admin}">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                   </g:if>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
