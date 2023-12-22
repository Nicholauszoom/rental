<%@ include file="/ui/layouts/tag_lib.jsp"%>

<!-- HEADER START-->
<header class="app-header navbar leseni_np">
  <button class="navbar-toggler mobile-sidebar-toggler d-lg-none mr-auto" type="button">
    <span class="navbar-toggler-icon"></span>
  </button>


  <a class="navbar-brand leseni_np" href="#"></a>
  <button class="navbar-toggler sidebar-toggler d-md-down-none" type="button">
    <span class="navbar-toggler-icon icon-menu"></span>
  </button>
  <span class="erb-mis pl-4 leseni_np">GLICA</span>
  
  <ul class="nav navbar-nav ml-auto mr-5 url_nav_bar">
  	
    <li class="nav-item dropdown" id="user_alert_menu">
	  	<div class="drop-sub">      
			<a class="nav-link  nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon-bell"></i><span class="badge badge-pill badge-danger" id="user_total_alert"></span></a>
		    <ul class="dropdown-menu multi-level drop-menu" role="menu" aria-labelledby="dropdownMenu">
		      	<li class="drop-header">Notifications</li>
		        <li class="dropdown-submenu">
		          	<a tabindex="-1" href="#"><i class="icon-layers"></i> Personnel Licence  <span class="badge badge-pill badge-danger alertParent" id="personnel_licence_alert">0</span></a>
			        <ul class="dropdown-menu">
			        	<li><a href="${pageContext.request.contextPath}/personnel/licence/new/applications"><i class="icon-layers"></i>  New Applications  <span class="badge badge-pill badge-danger" id="personnel_new_application_alert"></span></a></li>
			            <li><a href="${pageContext.request.contextPath}/personnel/renew/applications"><i class="icon-layers"></i>  Renewals  <span class="badge badge-pill badge-danger" id="personnel_renewal_application_alert"></span></a></li>
			            <li><a href="${pageContext.request.contextPath}/personnelLicences/controlNumberRequests/summary"><i class="icon-layers"></i>  Bill Generation  <span class="badge badge-pill badge-danger" id="personnel_bill_generation_alert"></span></a></li>
			        </ul>
		        </li>
		        <li class="dropdown-submenu">
		          	<a tabindex="-1" href="#"><i class="icon-layers"></i> Gaming Licence<span class="badge badge-pill badge-danger alertParent" id="gaming_licence_alert">0</span></a>
			        <ul class="dropdown-menu">
			        	<li><a href="${pageContext.request.contextPath}/licence/applications"><i class="icon-layers"></i>  New Applications  <span class="badge badge-pill badge-danger" id="gaming_new_licence_alert"></span></a></li>
			            <li><a href="${pageContext.request.contextPath}/licence/renew/applications"><i class="icon-layers"></i>  Renewals  <span class="badge badge-pill badge-danger" id="gaming_renewal_application_alert"></span></a></li>
			            <li><a href="${pageContext.request.contextPath}/duediligence"><i class="icon-layers"></i>  Due Diligence  <span class="badge badge-pill badge-danger" id="gaming_due_diligence_alert"></span></a></li>
			            <li><a href="${pageContext.request.contextPath}/suitability"><i class="icon-layers"></i>  Suitability Inspection  <span class="badge badge-pill badge-danger" id="gaming_suitability_inspection_alert"></span></a></li>
			            <li><a href="${pageContext.request.contextPath}/licence/transfer/applications"><i class="icon-layers"></i>  Premise Transfer  <span class="badge badge-pill badge-danger" id="gaming_premise_transfer_alert"></span></a></li>
			        </ul>
		        </li>
		        <li class="dropdown-submenu">
		          	<a tabindex="-1" href="#"><i class="icon-layers"></i> Amusements  <span class="badge badge-pill badge-danger alertParent" id="amusement_licence_alert">0</span></a>
			        <ul class="dropdown-menu">
			        	<li><a href="#"><i class="icon-layers"></i>  New Applications  <span class="badge badge-pill badge-danger" id="amusement_new_application_alert"></span></a></li>
			            <li><a href="#"><i class="icon-layers"></i>  Renewals  <span class="badge badge-pill badge-danger" id="amusement_renewal_application_alert"></span></a></li>
			        </ul>
		        </li>
		        <li class="dropdown-submenu">
		          	<a tabindex="-1" href="#"><i class="icon-layers"></i> Devices <span class="badge badge-pill badge-danger alertParent" id="device_management_alert">0</span></a>
			        <ul class="dropdown-menu">
			        	<li><a href="#"><i class="icon-layers"></i>  Purchase Requests  <span class="badge badge-pill badge-danger" id="device_purchase_request_alert"></span></a></li>
			            <li><a href="#"><i class="icon-layers"></i>  Software Registration  <span class="badge badge-pill badge-danger" id="device_software_registration_alert"></span></a></li>
			            <li><a href="#"><i class="icon-layers"></i>  Device Registration  <span class="badge badge-pill badge-danger" id="device_registration_alert"></span></a></li>
			            <li><a href="#"><i class="icon-layers"></i>  Device Levy  <span class="badge badge-pill badge-danger" id="device_levy_alert"></span></a></li>
			        </ul>
		        </li>
		        <li><a href="#"><i class="icon-layers"></i> Responsible Gaming  <span class="badge badge-pill badge-danger alertParent" id="responsible_gaming_complaint_alert">0</span></a></li>
		        <li><a href="#"><i class="icon-layers"></i> Gaming Returns  <span class="badge badge-pill badge-danger alertParent" id="gaming_return_return_alert">0</span></a></li>
			</ul>
		</div>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon-user"></i></a>
      <div class="dropdown-menu dropdown-menu-right">
      	<div class="dropdown-header text-center"><strong>Account</strong></div>
      	<sec:authorize access="hasAnyAuthority('VIEW_MY_PROFILE','EDIT_MY_PROFILE')">
      		<a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile"><i class="fa fa-user"></i>My Profile</a>
      	</sec:authorize> 
      	<sec:authorize access="hasAuthority('HANDOVER')">
      		<a class="dropdown-item" id="userHandoverLink" href="#"  data-toggle="modal" data-target="#userHandoverDialog"><i class="fa fa-repeat" ></i> Handover</a>
      	</sec:authorize>
      	<sec:authorize access="hasAuthority('TAKEOVER')">
      		<a class="dropdown-item" id="userTakeoverLink" href="#"  data-toggle="modal" data-target="#userTakeoverDialog"><i class="fa fa-umbrella" ></i> Takeover</a>
      	</sec:authorize>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="fa fa-lock"></i> Logout</a>
      </div>
    </li>
</ul>
 
  
  <!-- <!-- Added by Nyambile -->
   <script language="javascript" type="text/javascript">
      function printDiv1(divID) {
          //Get the HTML of div
          var divElements = document.getElementById(divID).innerHTML;
          //Get the HTML of whole page
          var oldPage = document.body.innerHTML;
          //Reset the page's HTML with div's HTML only
          //document.body.innerHTML ="<html><head><title></title></head><body>"+divElements+"</body>";
          //alert(document.body.innerHTML);
          //Print Page
          
           setTimeout(function() {
        	   document.body.innerHTML ="<html><head><title></title></head><body>"+divElements+"</body>";
        	   window.print();
        	   window.close();
        	   //Restore orignal HTML
               document.body.innerHTML = oldPage;
              
    		 }, 250); 
          
    	  
      }      		 
  </script>
  
    <!-- <!-- Added by Ima Emanueli -->
   <script language="javascript" type="text/javascript">
      function printDiv(divID) {
    	  alert("jejejejejejej");
          //Get the HTML of div
          var divElements = document.getElementById(divID).innerHTML;
          //Get the HTML of whole page
          var oldPage = document.body.innerHTML;
          //Reset the page's HTML with div's HTML only
          //document.body.innerHTML ="<html><head><title></title></head><body>"+divElements+"</body>";
          //alert(document.body.innerHTML);
          //Print Page
          
           setTimeout(function() {
        	   document.body.innerHTML ="<html><head><title></title></head><body>"+divElements+"</body>";
        	   window.print();
        	   window.close();
        	   //Restore orignal HTML
               document.body.innerHTML = oldPage;
              
    		 }, 250); 
          
    	  
      }      		 
  </script>
  
<script>
  function goBack() {
  window.history.back();
}
</script>

<!-- Added by Nyambile,updates amusement permits is_printed column -->
<script language="javascript" type="text/javascript">
	function printAndUpdateAmusementPermit(divID, permitId) {

		//Get the HTML of div
		var divElements = document.getElementById(divID).innerHTML;
		//Get the HTML of whole page
		var oldPage = document.body.innerHTML;
		$.getJSON('${pageContext.request.contextPath}/approved/amusement/permit/print',
			{
				permitId : permitId,
				ajax : 'true'
			}, function(data) {
				//alert("Updates done"+data);
			});
		setTimeout(
				function() {
					document.body.innerHTML = "<html><head><title></title></head><body>"
							+ divElements + "</body>";
					window.print();
					window.close();
					//Restore orignal HTML
					document.body.innerHTML = oldPage;

				}, 250);
	}
</script>

</header>

<!-- Hand Over Model -->
<div class="modal fade" id="userHandoverDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <form id="handoverForm" action="#">
        <div class="modal-content">
            <div class="modal-header">
                <h6 class="modal-title"><i class="fa fa-users"></i> &nbsp; Handover Note </h6>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
            	<div id="handoverAlert" ></div>
            	<c:if test="${userDetails.handoverStatusId == 0 or empty userDetails.handoverStatusId}">
	                <div class="form-group col-md-12">
	                    <p>Select a Person to Handover your Tasks </p>
	                    <div class="data-box">
	                      <div class="input-group col-md-12">
	                          <span class="input-group-addon">Position Title</span>
	                          <select name="handoverPositionTitleId" id="handoverPositionTitleId" class="form-control col-md-6">
	                            <option value="" selected>--- Select ---</option> 
	                          </select>
	                      </div><br/>
	                      <div class="input-group col-md-12">
	                          <span class="input-group-addon">Officer Name</span>
	                          <select name="handedoverUserId" id="handedoverUserId" class="form-control col-md-6" data-validation="required">
	                            <option value="" selected>--- Select ---</option>
	                          </select>
	                      </div>                    
	                    </div>
	                    <div class="data-box">
	                      <div class="form-group">
	                          <label>Handover Reason</label>
	                          <textarea name="handoverReason" id="handoverReason" rows="3" class="form-control" maxlength="250" data-validation="required"></textarea>
	                          <p></p>
	                      </div>
	                    </div>
	                </div>
                </c:if>
                <c:if test="${userDetails.handoverStatusId == 1}">
	                <div class="form-group col-md-12">
	                    <p>My Handover(s) </p>
	                    <div class="data-box">
	                    	<div class="col-lg-12 col-sm-12">
	                            <div class="personal-info-1">
	                                <table class="table table-striped" id="handedoverTable">
		                                <thead>
		                                    <tr>
		                                        <th>Officer Name</th>
		                                        <th>Title</th>
		                                        <th>Start Date</th>
		                                        <th>Reason</th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                </tbody>
		                            </table>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </c:if>
                <c:if test="${userDetails.handoverStatusId == 2}">
	                <div class="form-group col-md-12">
	                    <p>Handover Details</p>
	                    <input type="hidden" id="userHandoverId"/>
	                    <div class="data-box">
	                      <div class="input-group col-md-8">
	                          <span class="input-group-addon">Position Title</span>
	                          <input type="text" id="handedoverPositionTitleName" readonly="readonly" name="handedoverPositionTitleName" class="form-control"/>
	                      </div>
	                      <div class="input-group col-md-8">
	                          <span class="input-group-addon">Name</span>
	                          <input type="text" id="handedoverUserName" readonly="readonly" name="handedoverUserName" class="form-control"/>
	                      </div>  
	                      <br/> 
	                      <div class="input-group col-md-6">
	                          <span class="input-group-addon">Start Date</span>
	                          <input type="text" id="handoverStartDate" readonly="readonly" name="handoverStartDate" class="form-control"/>
	                      </div>  
	                      <br/>                      
	                    </div>
	                    <div class="data-box">
	                      <div class="form-group">
	                          <label>Reason for Handover</label>
	                          <textarea name="handoverReason" readonly="readonly" id="handoverReason" rows="3" class="form-control" maxlength="250"></textarea>
	                          <p></p>
	                      </div>
	                    </div>
	                </div>
                </c:if>
            </div>
            <div class="modal-footer">
            	<c:if test="${userDetails.handoverStatusId == 0 or empty userDetails.handoverStatusId}">
                	<input id="submitHandover" type="button" value="Handover" class="btn btn-success  btn-raised mr-2">
                </c:if>
                <c:if test="${userDetails.handoverStatusId == 1 }">
                	<input  data-dismiss="modal" type="button" value="Close" class="btn btn-danger btn-raised mr-2">
                </c:if>
                <c:if test="${userDetails.handoverStatusId == 2 }">
                	<input id="submitTakeBack" type="button" value="Take Back" class="btn btn-success btn-raised mr-2">
                </c:if>
            </div>
        </div>
        </form>
    </div>
</div>
<!-- Hand Over Model -->

<!-- Take Over Model -->
<div class="modal fade" id="userTakeoverDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <form id="takeoverForm" action="#">
        <div class="modal-content">
            <div class="modal-header">
                <h6 class="modal-title"><i class="fa fa-users"></i> &nbsp; Handover On Behalf</h6>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
            	<div id="takeoverAlert" ></div>
                <div class="form-group col-md-12">
                    <p>Handover Task(s)</p>
                    <div class="data-box">
                     <p>From: </p>
                      <div class="input-group col-md-12">
                          <span class="input-group-addon">Position Title</span>
                          <select name="handoverFromPositionTitleId" id="handoverFromPositionTitleId" class="form-control col-md-6">
                            <option value="" selected>--- Select ---</option> 
                            <c:forEach items="${positionTitles}" var="entry">
                            	<option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                          </select>
                      </div><br/>
                      <div class="input-group col-md-12">
                          <span class="input-group-addon">Officer Name</span>
                          <select name="handingoverUserId" id="handoverFromUserId" class="form-control col-md-6" data-validation="required">
                            <option value="" selected>--- Select ---</option>
                          </select>
                      </div> 
                    <p/>
                    <p>To:</p>
                      <div class="input-group col-md-12">
                          <span class="input-group-addon">Position Title</span>
                          <select name="handoverToPositionTitleId" id="handoverToPositionTitleId" class="form-control col-md-6">
                            <option value="" selected>--- Select ---</option> 
                          </select>
                      </div><br/>
                      <div class="input-group col-md-12">
                          <span class="input-group-addon">Officer Name</span>
                          <select name="handedoverUserId" id="handoverToUserId" class="form-control col-md-6" data-validation="required">
                            <option value="" selected>--- Select ---</option>
                          </select>
                      </div>                      
                    </div>
                    <div class="data-box">
                      <div class="form-group">
                          <label>Handover Reason</label>
                          <textarea name="handoverReason" id="handoverReason" rows="3" class="form-control" maxlength="250" data-validation="required"></textarea>
                          <p></p>
                      </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input id="submitTakeover" type="button" value="Takeover" class="btn btn-success btn-raised mr-2">
            </div>
        </div>
        </form>
    </div>
</div>
<!-- Take Over Model -->

