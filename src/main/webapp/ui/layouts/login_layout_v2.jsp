<%@ include file="tag_lib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
		
		<title><tiles:insertAttribute name="pagetitle"/> </title>
		
		<link rel="shortcut icon" href="<c:url value="/ui/static/img/logo_bg.png"/>"/>
		<!--     Fonts and icons     -->
	    <link href="/ui/static/fonts/awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	    <link href="/ui/static/fonts/roboto/css/font-roboto.css" rel="stylesheet" type="text/css"/>
	    <!-- CSS Files -->
	    <link rel="stylesheet" href="<c:url value="/ui/static/css/material-dashboard.css"/>"/>
		
	</head>
	<body class="app flex-row align-items-center">
		<!-- Content -->
		 <div class="container">
			<tiles:insertAttribute name="content" />
		</div>
		<!-- Content -->
		<!-- Footer -->
		<tiles:insertAttribute name="footer" />
		<!-- Footer -->
	</body>
</html>

