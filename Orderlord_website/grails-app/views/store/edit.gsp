

<%@ page import="orderlord.website.Store"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'store.label', default: 'Store')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" /></a></span>
	   <g:if test="${session?.store?.admin}">
		<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
		<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
	   </g:if>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.edit.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${storeInstance}">
			<div class="errors">
				<g:renderErrors bean="${storeInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${storeInstance?.id}" />
			<g:hiddenField name="version" value="${storeInstance?.version}" />
			<div class="dialog">
				<table style="width: 800px;">
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="storeName"><g:message
										code="store.storeName.label" default="Store Name" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'storeName', 'errors')}">
								<g:textField name="storeName"
									value="${storeInstance?.storeName}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="login"><g:message
										code="store.login.label" default="Login" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'login', 'errors')}">
								<g:textField name="login" maxlength="15"
									value="${storeInstance?.login}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="password"><g:message
										code="store.password.label" default="Password" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'password', 'errors')}">
								<g:passwordField name="password" maxlength="15"
									value="${storeInstance?.password}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="storeAddress"><g:message
										code="store.storeAddress.label" default="Store Address" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'storeAddress', 'errors')}">
								<g:textField name="storeAddress"
									value="${storeInstance?.storeAddress}" />
							</td>
						</tr>
						
						<tr class="prop">
							<td valign="top" class="name"><label for="phoneNumber"><g:message
										code="store.phoneNumber.label" default="Phone Number" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'phoneNumber', 'errors')}">
								<g:textField name="phoneNumber"
									value="${storeInstance?.phoneNumber}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="linkToPic"><g:message
										code="store.linkToPic.label" default="Link To Pic" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'linkToPic', 'errors')}">
								<g:textField name="linkToPic"
									value="${storeInstance?.linkToPic}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="items"><g:message
										code="store.items.label" default="Items" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'items', 'errors')}">

								<ul>
									<g:each in="${storeInstance?.items?}" var="i">
										<li><g:link controller="item" action="show" id="${i.id}">
												${i?.encodeAsHTML()}
											</g:link></li>
									</g:each>
								</ul> <g:link controller="item" action="create"
									params="['store.id': storeInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'item.label', default: 'Item')])}
								</g:link>

							</td>
						</tr>

					<g:if test="${session?.store?.admin}">
						<tr class="prop">
							<td valign="top" class="name"><label for="role"><g:message
										code="store.role.label" default="Role" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'role', 'errors')}">
								<g:select name="role"
									from="${storeInstance.constraints.role.inList}"
									value="${storeInstance?.role}" valueMessagePrefix="store.role" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="activeorders"><g:message
										code="store.activeorders.label" default="Activeorders" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'activeorders', 'errors')}">

								<ul>
									<g:each in="${storeInstance?.activeorders?}" var="a">
										<li><g:link controller="activeorder" action="show"
												id="${a.id}">
												${a?.encodeAsHTML()}
											</g:link></li>
									</g:each>
								</ul> <g:link controller="activeorder" action="create"
									params="['store.id': storeInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'activeorder.label', default: 'Activeorder')])}
								</g:link>

							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="pendingorders"><g:message
										code="store.pendingorders.label" default="Pendingorders" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: storeInstance, field: 'pendingorders', 'errors')}">

								<ul>
									<g:each in="${storeInstance?.pendingorders?}" var="p">
										<li><g:link controller="pendingorder" action="show"
												id="${p.id}">
												${p?.encodeAsHTML()}
											</g:link></li>
									</g:each>
								</ul> <g:link controller="pendingorder" action="create"
									params="['store.id': storeInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'pendingorder.label', default: 'Pendingorder')])}
								</g:link>

							</td>
						</tr>

					</g:if>

					</tbody>
				</table>
			</div>
			<div class="buttons">
					<span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
				<g:if test="${session?.store?.admin}">
					<span class="button"><g:actionSubmit class="delete" action="delete"	value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
				</g:if>
			</div>
		</g:form>
	</div>
</body>
</html>
