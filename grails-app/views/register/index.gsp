<html>

<head>
	<meta name='layout' content='main'/>
	<title><g:message code='spring.security.ui.register.title'/></title>
	<link rel="stylesheet" href="${resource(dir: 'css/css_custom', file: 'Event.css')}" />
</head>

<body>

<div id="mainsection">
		<!--  LEFT BAR CONTENT -->
		<div id="leftbar"></div>

		<div id="container">
		
			<g:form action='register' name='registerForm'>

			<g:if test='${emailSent}'>
			<br/>
			<g:message code='spring.security.ui.register.sent'/>
			</g:if>
			<g:else>
		
			<br/>
		
			<table>
			<tbody>
				
				<s2ui:textFieldRow name='username' bean="${command}" value="${command.username}"
				                   size='40' labelCode='user.username.label' labelCodeDefault='E-mail'/>
				                   
				                   <s2ui:textFieldRow name='firstName' bean="${command}" value="${command.firstName}"
				                   size='40' labelCode='first Name' labelCodeDefault='First Name'/>
				                   
				                   <s2ui:textFieldRow name='lastName' bean="${command}" value="${command.lastName}"
				                   size='40' labelCode='Last Name' labelCodeDefault='last Name'/> 
				                   <s2ui:textFieldRow name='country' bean="${command}" value="${command.country}"
				                   size='40' labelCode='user.counrty.label' labelCodeDefault='Country'/>
				                   
				<s2ui:textFieldRow name='phoneNumber' bean="${command}" value="${command.phoneNumber}"
				                   size='40' labelCode='user.phoneNumber.label' labelCodeDefault='phoneNumber'/>
		
				<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
		                             size='40' labelCodeDefault='Password' value="${command.password}"/>
		
				<s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
		                             size='40' labelCodeDefault='Password (again)' value="${command.password2}"/>
		
			</tbody>
			</table>
		
			<s2ui:submitButton elementId='create' form='registerForm' messageCode='spring.security.ui.register.submit'/>
		
			</g:else>
		
		</g:form>
		
		</div>

		<!--  RIGHT BAR CONTENT -->
		<div id="rightbar" class="visible-lg"></div>
</div>

<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>

</body>
</html>
