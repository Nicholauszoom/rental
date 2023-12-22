<%@ include file="/ui/layouts/tag_lib.jsp"%>
<!-- Sidebar Start-->
<div class="sidebar" data-background-color="white" data-color="rose" data-image="../../ui/static/img/sidebar-1.jpg">
    <div class="logo" style="text-align: center;height:150px;">
        <img src="<c:url value="/ui/static/img/epg.png"/>" style="height: 100%;position: relative;z-index: 10;">
    </div>
    <div class="logo" style="text-align: center;">
        <h4 style="color: #03275d;">Ministry of Lands and Human Settlements</h4>
    </div>
    <div class="sidebar-wrapper">
        <div class="user">
            <div class="photo">
                <img src="/ui/static/img/faces/avatar.jpg"/>
            </div>
            <div class="user-info">
                <a class="username" data-toggle="collapse" href="#collapseExample">
        <span>
        	 A.Mwageni
          <b class="caret"></b>
        </span>

                </a>
                <div class="collapse" id="collapseExample">
                    <ul class="nav">
                        <!-- <li class="nav-item" id="profilenav">
                            <a class="nav-link" th:href="@{/users/profile}">
                                <span class="sidebar-mini"> MP </span>
                                <span class="sidebar-normal"> My Profile </span>
                            </a>
                        </li> -->
                        <li class="nav-item">
                            <a class="nav-link" href="/sp/logout">
                                <span class="sidebar-mini"> LO </span>
                                <span class="sidebar-normal"> Logout </span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <ul class="nav">
           <li class="nav-item " id="dashboardnav">
                <a class="nav-link" href="/sp/home">
                    <i class="material-icons">dashboard</i>
                    <p> Dashboard </p>
                </a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" data-toggle="collapse" href="#billing" id="billinglink">
                    <i class="material-icons">content_paste</i>
                    <p> Billing
                        <b class="caret"></b>
                    </p>
                </a>
                <div class="collapse" id="billing">
                    <ul class="nav">
                        <li class="nav-item " id="searchbillnav">
                            <a class="nav-link" href="/sp/bill/search">
                                <span class="sidebar-mini"> SE </span>
                                <span class="sidebar-normal"> Search </span>
                            </a>
                        </li>
                        <li class="nav-item " id="createbillnav">
                            <a class="nav-link" href="/sp/bill/create">
                                <span class="sidebar-mini"> CR </span>
                                <span class="sidebar-normal">Create</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </li>
            <li class="nav-item " id="paymentnav">
                <a class="nav-link" href="/sp/payment/search">
                    <i class="material-icons">payment</i>
                    <p> Payment </p>
                </a>
            </li>
            <!-- <li class="nav-item " id="reportsnav">
                <a class="nav-link" href="#">
                    <i class="material-icons">report</i>
                    <p> Reports </p>
                </a>
            </li> -->
        </ul>
    </div>
</div>
<!-- Sidebar End-->

