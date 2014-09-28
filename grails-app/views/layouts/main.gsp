<!DOCTYPE html>
<html lang="en">
	<head>
		<title><g:layoutTitle default="2Go Copenhagen Home" /></title>
		<link rel="stylesheet"  href="${resource(dir: 'css/css_bootstrap', file: 'bootstrap.min.css')}" type="text/css">
		<link rel="stylesheet"  href="${resource(dir: 'css/css_bootstrap', file: 'bootstrap.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css/css_custom', file: 'main.css')}" />
		<link rel="stylesheet"  href="${resource(dir: 'css/css_custom', file: 'categoryButtons.css')}" />	
		<link rel="stylesheet"  href="${resource(dir: 'css/css_custom', file: 'mobile.css')}" />	
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<script src="http://code.jquery.com/jquery.min.js"></script>
		<script src="${resource(dir: 'js', file:'bootstrap.min.js')}" type="text/javascript"></script>	
		
		<g:layoutHead />
		<r:layoutResources />
	</head>
	
	<body>
		
		<div id="mainwrapper" >
			<g:render template="/layouts/header" />
			<g:layoutBody />
			<g:render template="/layouts/footer" />
			<r:layoutResources />
		</div>
		
		
	</body>
	
	</html>
