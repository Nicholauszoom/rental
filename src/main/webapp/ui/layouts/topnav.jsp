<%@ include file="/ui/layouts/tag_lib.jsp"%>
<!-- Top Nav Start-->
<nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top " id="navigation-example"
     th:fragment="nav">
    <div class="container-fluid">
        <div class="navbar-wrapper">
            <div class="navbar-minimize">
                <button class="btn btn-just-icon btn-white btn-fab btn-round" id="minimizeSidebar">
                    <i class="material-icons text_align-center visible-on-sidebar-regular">more_vert</i>
                    <i class="material-icons design_bullet-list-67 visible-on-sidebar-mini">view_list</i>
                </button>
            </div>
            <!-- <a class="navbar-brand" href="/"><h2>Vital Records Documentation Registry</h2></a> -->
        </div>
        <button aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation"
                class="navbar-toggler" data-target="#navigation-example" data-toggle="collapse" type="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end">
            <!-- <form class="navbar-form">
              <div class="input-group no-border">
                <input type="text" value="" class="form-control" placeholder="Search...">
                <button type="submit" class="btn btn-white btn-round btn-just-icon">
                  <i class="material-icons">search</i>
                  <div class="ripple-container"></div>
                </button>
              </div>
            </form> -->
            <ul class="navbar-nav">
                <!-- <li class="nav-item">
                  <a class="nav-link" href="#pablo">
                    <i class="material-icons">dashboard</i>
                    <p class="d-lg-none d-md-block">
                      Stats
                    </p>
                  </a>
                </li> -->
                <li class="nav-item dropdown">
                    <a aria-expanded="false" aria-haspopup="true" class="nav-link" data-toggle="dropdown"
                       href="http://example.com" id="navbarDropdownMenuLink">
                        <i class="material-icons">notifications</i>
                        <span class="notification">5</span>
                        <p class="d-lg-none d-md-block">
                            Notifications
                        </p>
                    </a>
                    <div aria-labelledby="navbarDropdownMenuLink" class="dropdown-menu dropdown-menu-right">
                        <tr th:each="notification : ${notifications}">
                            <td>
                                <a class="dropdown-item"
                                   th:href="@{/conversations/show/__${notification.ConversationId}__}"
                                   th:text="${notification.ConversationSubject}"></a>
                            </td>
                        </tr>

                    </div>
                </li>
                <li class="nav-item">
                  <a class="nav-link" th:href="@{/users/profile}">
                    <i class="material-icons">person</i>
                    <p class="d-lg-none d-md-block">
                      Account
                    </p>
                  </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- Top Nav End-->
