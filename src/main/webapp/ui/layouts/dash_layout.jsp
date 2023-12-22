<%@ include file="/ui/layouts/tag_lib.jsp"%>  
<!DOCTYPE html>   
<html class="loading" lang="en" data-textdirection="ltr">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="GBT Executive Dashboard">
    <title>GBT | Executive Dashboard</title>
    <link rel="apple-touch-icon" href="img/apple-icon-120.html">
    <link href="https://fonts.googleapis.com/css?family=Rubik:300,400,500,600%7CIBM+Plex+Sans:300,400,500,600,700" rel="stylesheet">

    <!-- BEGIN: Vendor CSS-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/ui/dashboard/css/vendors.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/ui/dashboard/css/charts/apexcharts.css"/>" >
    <link rel="stylesheet" type="text/css" href="<c:url value="/ui/dashboard/css/extensions/swiper.min.css"/>" >
    <!-- END: Vendor CSS-->

    <!-- BEGIN: Theme CSS-->
    <link rel="stylesheet" type="text/css"  href="<c:url value="/ui/dashboard/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css"  href="<c:url value="/ui/dashboard/css/bootstrap-extended.min.css"/>">
    <link rel="stylesheet" type="text/css"  href="<c:url value="/ui/dashboard/css/colors.min.css"/>" >
    <link rel="stylesheet" type="text/css"  href="<c:url value="/ui/dashboard/css/components.min.css"/>" >
    <link rel="stylesheet" type="text/css"  href="<c:url value="/ui/dashboard/css/dark-layout.min.css"/>" >
    <link rel="stylesheet" type="text/css"  href="<c:url value="/ui/dashboard/css/semi-dark-layout.min.css"/>">
    <!-- END: Theme CSS-->

    <!-- BEGIN: Page CSS-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/ui/dashboard/css/horizontal-menu.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/ui/dashboard/css/dashboard-ecommerce.min.css"/>">
    <!-- END: Page CSS-->

    <!-- BEGIN: Custom CSS-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/ui/dashboard/css/style.css"/>" >
    <!-- END: Custom CSS-->


</head>




<body class="horizontal-layout horizontal-menu navbar-static 2-columns   footer-static  " data-open="hover" data-menu="horizontal-menu" data-col="2-columns">
    <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

<!-- INCLUDE HEADER -->
<tiles:insertAttribute name="header" />
    
<!-- INCLUDE CONTENT -->
		<tiles:insertAttribute name="content" />

<!-- INCLUDE FOOTER -->
<tiles:insertAttribute name="footer"/>
		
