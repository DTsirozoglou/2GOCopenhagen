<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="layout" content="main" />
<title>Guides</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/blitzer/jquery-ui.css"/>
<link rel="stylesheet" href="${resource(dir: 'css/css_custom', file: 'Guide.css')}" />
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="${resource(dir: 'js', file:'guides.js')}" type="text/javascript"></script>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp" type="text/javascript"></script>
<script>
$(document).ready(function(e) {
	$('img[usemap]').rwdImageMaps();
	
	$('area').on('click', function() {
		alert($(this).attr('alt') + ' clicked');
	});
});
</script>
</head>

<body>

	<!--  MAIN PAGE - MAIN SECTION -->
	<div id="mainsection">
		<!--  LEFT BAR CONTENT -->
		<div id="leftbar">

			<div id="SideBarTop">
				<g:render template="categoryButtons" />
			</div>
			
			<div id="districtMap_mobile" class="hidden-lg">

			</div>

			<div id="SideBarBottomLeft">
				<g:if test="${guideInstance == null}" >
					<g:render template="leftSideBarContent" />
				</g:if>		
				<g:else>
					<g:render template="leftSideBarContentForSingleGuide" />
				</g:else>	
			</div>

		</div>

		<!--  EVENT CONTENT ON DESKTOP -->
		<div id="container">
			<g:if test="${guideInstance == null}" >
				<g:render template="listGuides" />
			</g:if>		
			<g:else >
				<g:render template="singleGuide" />
			</g:else>
		</div>

		<!--  RIGHT BAR CONTENT -->
		<div id="rightbar" >

			<div id="districtMap">
				<img id="ImageMap1" src="${resource(dir: 'images/districtMap', file: 'CphMap.png')}" width="100%" height="250" border="0" alt="" usemap="#Map_Vect_v2_Circle_ImageMapping_Test_Map">
				<map id="Map_Vect_v2_Circle_ImageMapping_Test_Map" name="Map_Vect_v2_Circle_ImageMapping_Test_Map">
					<area shape="poly" alt="" coords="139,130, 142,126, 149,118, 156,109, 162,102, 165,93, 167,89, 170,89, 172,89, 173,83, 172,76, 174,73, 176,66, 176,59, 176,54, 179,51, 188,49, 200,49, 209,51, 210,56, 206,63, 203,68, 197,73, 190,75, 186,76, 186,80, 191,80, 191,84, 189,95, 187,104, 182,114, 170,126, 153,133, 143,132, 139,130"
					data-maphilight='{"strokeColor":"0000ff","strokeWidth":5,"fillColor":"00ff00","fillOpacity":0.6}' href="http://www.facebook.com">
					<area shape="poly" alt="" coords="162,244, 162,234, 162,226, 161,220, 162,217, 169,216, 179,213, 191,209, 199,208, 182,161, 162,130, 159,132, 150,133, 139,130, 119,155, 117,162, 112,173, 110,181, 108,185, 104,193, 101,204, 96,215, 72,238, 97,247, 124,250, 162,244" href="http://www.facebook.com">
					<area shape="poly" alt="" coords="229,139, 227,140, 225,142, 220,134, 218,124, 224,126, 225,133, 231,133, 236,128, 239,120, 238,118, 240,114, 242,110, 240,103, 235,98, 229,99, 227,104, 223,104, 221,100, 227,97, 225,90, 222,85, 218,84, 211,89, 204,96, 207,104, 213,112, 217,118, 216,121, 207,111, 200,97, 201,91, 208,86, 216,81,
					220,77, 219,71, 216,68, 211,71, 201,76, 191,80, 191,84, 189,95, 187,104, 182,114, 170,126, 162,130, 182,161, 199,208, 202,209, 204,209, 209,206, 215,204, 214,201, 213,197, 228,193, 243,161, 229,139" href="http://www.google.com">
					<area shape="poly" alt="" coords="106,178, 105,184, 102,188, 100,192, 91,211, 80,206, 80,213, 86,213, 88,216, 72,234, 67,234, 66,230, 63,225, 56,221, 48,221, 46,217, 45,206, 47,195, 44,190, 40,184, 38,182, 42,183, 50,184, 58,184, 61,187, 64,187, 72,187, 78,184, 83,181, 89,179, 92,178, 106,178" href="http://www.google.com">
					<area shape="poly" alt="" coords="92,178, 89,179, 83,181, 78,184, 72,187, 64,187, 61,187, 58,184, 50,184, 42,183, 38,182, 52,152, 56,150, 61,150, 57,142, 65,139, 72,134, 83,126, 90,123, 90,122, 91,120, 99,121, 108,117, 110,119, 112,118, 123,132, 123,139, 116,147, 117,149, 113,157, 110,168, 106,178, 92,178" href="http://www.9gag.com">
					<area shape="poly" alt="" coords="123,132, 123,135, 124,138, 126,136, 158,102, 161,96, 161,93, 160,92, 162,85, 166,77, 165,69, 164,67, 164,65, 166,58, 159,62, 154,58, 150,49, 132,58, 130,54, 118,70, 114,77, 107,89, 102,98, 99,104, 99,121, 108,117, 110,119, 112,118, 123,132" href="http://www.viedemerde.com">
					<area shape="poly" alt="" coords="101,2, 90,18, 94,21, 96,28, 98,36, 100,40, 104,33, 105,31, 106,35, 105,44, 104,54, 104,58, 107,60, 112,66, 118,70, 130,54, 132,58, 150,49, 154,58, 159,62, 166,58, 165,42, 164,35, 163,34, 163,32, 162,29, 164,28, 167,28, 166,25, 166,23, 169,23, 171,23, 168,19, 167,17, 168,17, 170,16, 175,18,
					179,20, 184,16, 149,3, 147,6, 145,6, 142,6, 138,1, 124,0, 101,2" href="http://www.dropbox.com">
					<area shape="poly" alt="" coords="102,98, 104,94, 109,85, 115,75, 118,70, 112,66, 107,60, 104,58, 104,54, 105,44, 106,35, 105,31, 104,33, 100,40, 98,36, 96,28, 94,21, 90,18, 77,27, 70,38, 68,48, 68,55, 68,58, 66,65, 68,76, 77,86, 84,91, 92,92, 96,92, 96,98, 102,98" href="http://www.google.com">
					<area shape="poly" alt="" coords="0,114, 2,121, 4,130, 7,137, 14,139, 23,138, 30,137, 43,141, 55,142, 64,139, 72,134, 83,126, 90,123, 90,122, 91,120, 95,121, 99,121, 99,110, 100,98, 100,98, 96,98, 96,92, 92,92, 84,91, 77,86, 68,76, 66,65, 67,61, 68,59, 65,59, 60,60, 58,61, 47,67, 38,76, 27,82, 14,88, 4,98, 0,114, 0,114"
					href="http://www.boligportal.dk">

					<area shape="poly" alt="" coords="38,76, 47,67, 58,61, 60,60, 65,59, 68,59, 68,58, 68,55, 68,48, 70,38, 77,27, 90,18, 101,3, 66,15, 37,36, 15,65, 2,100, 13,89, 27,82, 38,76" href="http://www.facebook.com">
				</map>
				<!-- <a href="amaOOO"><img style="" src="${resource(dir: 'images/districtMap', file: 'AmagerO.png')}"/></a>
				<a href="amaV"><img style="" src="${resource(dir: 'images/districtMap', file: 'AmagerV.png')}"/></a>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Christianshavn.png')}"/>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Frederikberg.png')}"/>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Inner City.png')}"/>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Norrebro.png')}"/>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Osterbro.png')}"/>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Sydhavnen.png')}"/>
				<img style=""src="${resource(dir: 'images/districtMap', file: 'Vesterbro.png')}"/> -->
				

			</div>
				
			<div id="SideBarBottomRight">
				<g:if test="${guideInstance == null}" >
					<g:render template="rightSideBarContent" />
				</g:if>		
				<g:else>
					<g:render template="rightSideBarContentForSingleGuide" />
				</g:else>
			</div>

		</div>
	</div>

</body>
</html>


