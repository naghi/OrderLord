<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Store Login</title>
</head>
<body>
	<div class="body">
		<h1>Store Login Credentials</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:form controller= "authentication" action="authenticate" method="post">
			<div class="dialog">
				<table>
					<tbody>
						<tr class="prop">
							<td valign="top" class="name"><label for="storeLogin">Store Login:</label>
							</td>
							<td valign="top"><input type="text" id="storeLogin" name="login" />
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="password">Password:</label>
							</td>
							<td valign="top"><input type="password" id="storePassword" name="password" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"> 
					<input type="submit" value="Login" />
				</span>
			</div>
		</g:form>
	</div>
</body>
</html>