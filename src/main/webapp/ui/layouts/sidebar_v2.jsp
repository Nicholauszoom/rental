<%@ include file="/ui/layouts/tag_lib.jsp"%>
<!-- Sidebar Start-->
<div class="sidebar" data-background-color="white" data-color="rose" data-image="../../ui/static/img/money.jpg">
    <div class="logo align-items-center" style="text-align: center;height: 135px;">
        <img src="<c:url value="/ui/static/img/bank.png"/>" style="height: 100%;position: relative;z-index: 10;">
    </div>
    <div class="logo" style="text-align: center;">
        <h4 style="color: #034404;">Bank X</h4>
    </div>
    <div class="sidebar-wrapper">
        <div class="user">
            <div class="photo">
                <img src="/ui/static/img/faces/avatar_v2.png"/>
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
                            <a class="nav-link" href="/pf/logout">
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
                <a class="nav-link" href="/pf/home">
                    <i class="material-icons">dashboard</i>
                    <p> Dashboard </p>
                </a>
            </li>
            <li class="nav-item " id="paymentnav">
                <a class="nav-link" href="/pf/payment/create">
                    <i class="material-icons">payment</i>
                    <p> Payment </p>
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- Sidebar End-->

