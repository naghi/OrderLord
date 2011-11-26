

<%@ page import="orderlord.website.Activeorder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activeorder.label', default: 'Activeorder')}" />
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
            <g:hasErrors bean="${activeorderInstance}">
            <div class="errors">
                <g:renderErrors bean="${activeorderInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table style="width: 800px;">
                        <tbody>
                        
                        <h3 style="color:rgb(160,50,110); margin-bottom:2px; margin-top:8px;">
                        	Placing active orders through the website as a 'store admin':
                        </h3>
                        <h4 style="margin-left:12px; margin-bottom:3px;">
                        	1. Create an order with no items, specifying the customer and store only.</br>
                        	2. On the next screen click 'Edit' to edit the order.</br>
                        	3. Add items to the order and click 'Update'.</br>
                        </h4>
                        <h4 style="margin-left:8px; margin-bottom:6px;">
                        	NOTE: Clicking 'Create' will set the new order to be temporarly due in 6 days, to give you enough time to edit it.
                        </h4>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pickupTime"><g:message code="activeorder.pickupTime.label" default="Pickup Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'pickupTime', 'errors')}">
                                    <!-- g:datePicker name="pickupTime" precision="minute" value="${activeorderInstance?.pickupTime}"  /-->
                                    Creating active orders will auto-generate the pickupTime for you.
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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="store"><g:message code="activeorder.store.label" default="Store" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activeorderInstance, field: 'store', 'errors')}">
                                    <g:select name="store.id" from="${orderlord.website.Store.list()}" optionKey="id" value="${activeorderInstance?.store?.id}"  />
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
