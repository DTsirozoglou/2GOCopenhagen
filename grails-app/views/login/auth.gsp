<html>

<head>
<title><g:message code='spring.security.ui.login.title'/></title>
<meta name='layout' content='main'/>
</head>
<body>

<div class="login s2ui_center ui-corner-all" style='text-align:center;'>
	<div class="login-inner">
		<h1><g:message code='spring.security.ui.login.signin'/></h1>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<p>
				<label for='username'>Email:</label>
				<input type='text' class='text_' name='j_username' id='username'/>
			</p>

			<p>
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</p>

			<p id="remember_me_holder">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
			</p>
			
			<p>
				<span class="forgot-link">
					<g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
				</span>
			</p>

			<p>
				<s2ui:linkButton elementId='register' controller='register' messageCode='spring.security.ui.login.register'/>
			</p>
			
			<p>
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
			</p>
			
			<oauth:connect provider="facebook" id="facebook-connect-link"><img src="${resource(dir: 'images/header_images', file: 'facebook.png')}" /></oauth:connect>
			<oauth:connect provider="twitter" id="twitter-connect-link"><img  src="${resource(dir: 'images/header_images', file: 'twitter.png')}"  /></oauth:connect>
			<oauth:connect provider="google" id="google-connect-link"><img  src="${resource(dir: 'images/header_images', file: 'google_plus.png')}"  /></oauth:connect>	
	
		</form>
	</div>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>

</body>

</html>
