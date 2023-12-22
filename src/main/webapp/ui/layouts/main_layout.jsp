<%@ include file="tag_lib.jsp"%>    
<!DOCTYPE html>
<html lang="en">
<head>
  	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="ePayment Gateway">
  	<meta name="keywords" content="electronic Payment Gateway">
	<link href="/assets/img/favicon.ico" rel="icon" type="image/png">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport'/>

  	<title><tiles:insertAttribute name="pagetitle" /></title>
  	
  	<!--     Fonts and icons     -->
    <link href="/ui/static/fonts/awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/ui/static/fonts/roboto/css/font-roboto.css" rel="stylesheet" type="text/css">
    <!-- CSS Files -->
    <link rel="stylesheet" href="<c:url value="/ui/static/css/material-dashboard.css"/>">
    <link rel="stylesheet" href="<c:url value="/ui/static/css/jquery-ui.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/ui/static/css/jquery-confirm.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/ui/static/css/custom.css"/>">
    <link rel="stylesheet" href="<c:url value="/ui/static/css/demo.css"/>">
    
</head>
<body class="">
	<div class="wrapper ">
		<!-- Sidebar -->
		<tiles:insertAttribute name="sidebar"/>
		<!-- Sidebar -->
  		<div class="main-panel">
    		<!-- Topnav -->
    		<tiles:insertAttribute name="topnav"/>
    		<!-- Topnav -->
    		<!-- Content -->
    		<tiles:insertAttribute name="content"/>
			<!-- Content -->
 			<!-- Footer -->
			<tiles:insertAttribute name="footer" />
			<!-- Footer -->
		</div>
	</div>		
</body>
</html>