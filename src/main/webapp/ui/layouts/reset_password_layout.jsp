<%@ include file="/ui/layouts/tag_lib.jsp"%>     
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Gaming Board Core System">
  <meta name="author" content="e-Government Agency"> 
  <meta name="keyword" content="Gaming Board of Tanzania">
  <meta name="description" content="some page description here">
  <meta name="keywords" content="page keywords here">

  <title> <tiles:insertAttribute name="pagetitle" /> </title>

  <link rel="shortcut icon" href="<c:url value="/ui/static/img/logo.png"/>">
  <link rel="stylesheet" href="<c:url value="/ui/static/css/fonts.min.css"/>">
  <link rel="stylesheet" href="<c:url value="/ui/static/css/master.min.css"/>">  
  <link rel="stylesheet" href="<c:url value="/ui/static/css/select2.min.css"/>"> 
  <script type="text/javascript" src="<c:url value="/ui/static/js/qrcode.min.js"/>"></script>
  <!--[if lt IE 9]>
    <script src="<c:url value="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"/>"></script>
    <script src="<c:url value="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"/>"></script>
  <![endif]-->
  
</head>

<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
<!-- HEADER START-->
	<header class="app-header navbar leseni_np">
		<div>
		  <div style="float:left;margin-right: 66px;"><a class="navbar-brand leseni_np" href="#"></a></div>
		  <div style="float:left;margin-top: 5px;"><span class="erb-mis pl-4 leseni_np">GLICA</span></div>
		</div>
	</header>
     
<!--START MAIN CONTENT-->
  <div class="app-body">
<!--INCLUDED CONTENT BLOCK HERE -->
		 <tiles:insertAttribute name="content" />
  </div>

<!-- FOOTER START-->
	 <footer class="mdl-mega-footer">
	     <div class="container">
              <span class="text-center">
                  Copyright &copy; 2019, <a href="http://gbt.go.tz" target="_blank"> GBT - Gaming Licensing, Inspection and Compliance Application  </a> All Rights Reserved.
              </span>       
	     </div>
	 </footer>
</body>
</html>