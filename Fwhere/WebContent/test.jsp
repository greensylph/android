<html>
<head>

<script src="./scripts/jquery.min.js"></script>
<script src="./scripts/jquery.Jcrop.js"></script>
<link rel="stylesheet" href="./css/jquery.Jcrop.css" type="text/css" />
<link rel="stylesheet" href="./css/demos.css" type="text/css" />

<script language="Javascript">

			// Remember to invoke within jQuery(window).load(...)
			// If you don't, Jcrop may not initialize properly
			jQuery(window).load(function(){

				jQuery('#cropbox').Jcrop({
					onChange: showPreview,
					onSelect: showPreview,
					aspectRatio: 1
				});

			});

			// Our simple event handler, called from onChange and onSelect
			// event handlers, as per the Jcrop invocation above
			function showPreview(coords)
			{
				if (parseInt(coords.w) > 0)
				{
					var rx = 100 / coords.w;
					var ry = 100 / coords.h;

					jQuery('#preview').css({
						width: Math.round(rx * 500) + 'px',
						height: Math.round(ry * 370) + 'px',
						marginLeft: '-' + Math.round(rx * coords.x) + 'px',
						marginTop: '-' + Math.round(ry * coords.y) + 'px'
					});
				}
			}

		</script>

</head>

<body>

<div id="outer">
<div class="jcExample">
<div class="article"><!-- This is the image we're attaching Jcrop to -->
	<table>
	<tr>
		<td><img src="images/flowers.jpg" id="cropbox" /></td>
		<td>
		<div style="width:100px;height:100px;overflow:hidden;">
			<img src="images/flowers.jpg" id="preview" />
		</div>
		
		</td>
		</tr>
		</table>

	</div>
	</div>
	</div>
	</body>

</html>
