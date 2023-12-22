<%@ include file="/ui/layouts/tag_lib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title> <tiles:insertAttribute name="pagetitle" /> </title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Gaming Board Core System">
    <link rel="icon" href="img/favicon.ico" type="image/x-icon">

    <link rel="shortcut icon" href="<c:url value="/ui/static/img/logo_bg.png"/>">
  <!-- Icons -->
  	<link rel="stylesheet" href="<c:url value="/ui/static/css/fonts.min.css"/>">
  	<link rel="stylesheet" href="<c:url value="/ui/static/css/master.min.css"/>">
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">

    <!-- CONTENT BLOCK HERE -->  
		<tiles:insertAttribute name="content" />
		
</body>

</html>