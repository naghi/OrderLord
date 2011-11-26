

<%@ page import="orderlord.website.Customer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
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
            <g:hasErrors bean="${customerInstance}">
            <div class="errors">
                <g:renderErrors bean="${customerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${customerInstance?.id}" />
                <g:hiddenField name="version" value="${customerInstance?.version}" />
                <div class="dialog">
                    <table style="width: 800px;">
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userName"><g:message code="customer.userName.label" default="User Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerInstance, field: 'userName', 'errors')}">
                                    <g:textField name="userName" maxlength="15" value="${customerInstance?.userName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="password"><g:message code="customer.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" maxlength="15" value="${customerInstance?.password}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="customer.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${customerInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="firstName"><g:message code="customer.firstName.label" default="First Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerInstance, field: 'firstName', 'errors')}">
                                    <g:textField name="firstName" value="${customerInstance?.firstName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastName"><g:message code="customer.lastName.label" default="Last Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerInstance, field: 'lastName', 'errors')}">
                                    <g:textField name="lastName" value="${customerInstance?.lastName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="balance"><g:message code="customer.balance.label" default="Balance" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerInstance, field: 'balance', 'errors')}">
                                    <g:textField name="balance" value="${fieldValue(bean: customerInstance, field: 'balance')}" />
                                </td>
                            </tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="activeorders"><g:message
										code="customer.activeorders.label" default="Activeorders" /></label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: customerInstance, field: 'activeorders', 'errors')}">

								<ul>
									<g:each in="${customerInstance?.activeorders?}" var="a">
										<li><g:link controller="activeorder" action="show"
												id="${a.id}">
												${a?.encodeAsHTML()}
											</g:link></li>
									</g:each>
								</ul> <g:link controller="activeorder" action="create"
									params="['customer.id': customerInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'activeorder.label', default: 'Activeorder')])}
								</g:link>

							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="pendingorders"><g:message
										code="customer.pendingorders.label" default="Pendingorders" /></label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: customerInstance, field: 'pendingorders', 'errors')}">

								<ul>
									<g:each in="${customerInstance?.pendingorders?}" var="p">
										<li><g:link controller="pendingorder" action="show"
												id="${p.id}">
												${p?.encodeAsHTML()}
											</g:link></li>
									</g:each>
								</ul> <g:link controller="pendingorder" action="create"
									params="['customer.id': customerInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'pendingorder.label', default: 'Pendingorder')])}
								</g:link>

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
