<!doctype html>
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
  <head>
  <link type="text/css" rel="stylesheet" href="Welcome.css">

<!-- jQuery-->
<link rel="stylesheet" href="jQ/js/jquery-1.8.0.min.js" />
<script src="jQ/js/jquery-1.8.0.min.js"></script>

<!-- jQuery UI-->
<script src="jQ/js/jquery-ui-1.8.23.custom.min.js"></script>
<script	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />

<!--  TWITTER BOOTSTRAP -->
<script src="twbootstrap/js/bootstrap.js"></script>
<link href="twbootstrap/css/bootstrap.css" rel="stylesheet">

<!--  jQ cookie -->
<script src="jQcookie/jquery.cookie.js"></script>

<script type="text/javascript">
	$(function() {
		$('#dmb').button();
		
	});
</script>
  
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
  </head>

  <body>
    <h1>Welcome!</h1>
	
    <table>
      <tr>
        <td colspan="2" style="font-weight:bold;">Available Artists:</td>        
      </tr>
      <tr>
        <td><a href="dmb"><button class="btn btn-info" id="dmb">DMB</button></a></td>
      </tr>
    </table>
  </body>
</html>
