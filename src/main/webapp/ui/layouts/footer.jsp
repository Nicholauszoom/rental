<%@ include file="/ui/layouts/tag_lib.jsp"%> 
<footer class="footer">
    <div class="container">
        <div class="copyright float-center">
            &copy;
            <script>
                document.write(new Date().getFullYear())
            </script>
            , All Rights Reserved
        </div>
    </div>
</footer>
<script type="text/javascript">

	$('.sidebar-wrapper').resize(function(){
		
	});
	
	$('#billBilledAmount').change(function(){
		$('#billedAmount').val($(this).val().replace(/\,/g, ''));
	});
	
	var defaultDate = new Date();
	if('${bill.expiryDateHdn}'){
		defaultDate = "${bill.expiryDateHdn}";
    }
	$('#expiryDate').datetimepicker({
        date: defaultDate
	});
		
	$('#submitBillBtn').click(function(){
		$('#billedAmount').val($('#billedAmount').val().replace(/\,/g, ''));
		$('#expiryDateHdn').val($('#expiryDate').val());
	});
	
</script>

 <script type="text/javascript">
  $(document).ready(function () {
    $("#searchnav").addClass('active');
    $(".datetimepicker").datetimepicker({
        icons: {
        time: "fa fa-clock-o",
        date: "fa fa-calendar", up: "fa fa-chevron-up", down: "fa fa-chevron-down",
        previous: "fa fa-chevron-left", next: "fa fa-chevron-right", today: "fa fa-screenshot",
        clear: "fa fa-trash", close: "fa fa-remove"
      }
    });
    
    $('#documentsDatatable').DataTable({
      "pagingType": "full_numbers",
      "pageLength": 10,
      "lengthMenu": [
        [5, 10, 25, -1],
        [5, 10, 25, 50, "All"]
      ],
      columnDefs: [
        {"width": "30%", "targets": [0]}
      ],
      responsive: true,
      language: {
        search: "_INPUT_",
        searchPlaceholder: "Search records",
      }
    });

  });
  
  
  function formatToCommaString(x){
	  x=x.replace(/\,/g, '');
	   if(isNaN(x)){
		   x="0.00";
	   }
	   x= Number(x); 
	   var parts = x.toString().split(".");
	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    if(parts[1] == null){
	    	parts.push("00");
	    }
	    if(parts[1] == 0){
	    	parts.splice(1,1);
	    	parts.push("00");
	    }
	    x=parts.join(".");
	    
	    if(x == 0){
	    	x="0.00"
	    } 
	    return x;
  }
  
  $(".amount").mouseout(function() {
	  //alert($(this).val());
	  $(this).val(formatToCommaString($(this).val()));
  });
  
   function showAlertNotification(message, alertType) {
       type = ['', 'info', 'danger', 'success', 'warning', 'rose', 'primary'];

       color = Math.floor((Math.random() * 6) + 1);

       $.notify({
           icon: "add_alert",
           message: message

       }, {
           type: alertType,
           timer: 5000,
           placement: {
               from: 'top',
               align: 'right'
           }
       });
   }

   $('.nav-item').click(function () {
      $(this).addClass('active');
   });

    $('#searchnav').click(function () {
        $(this).addClass('active');
    });

    $(document).ready(function () {
    	
        var message = '${message}';
        var alertType = '${alertType}';
        
        if (message !== '' && message !== null) {
            showAlertNotification(message, alertType);
        }
    });
 </script>

    <script>
        $(document).ready(function () {
            $().ready(function () {
                $sidebar = $('.sidebar');

                $sidebar_img_container = $sidebar.find('.sidebar-background');

                $full_page = $('.full-page');

                $sidebar_responsive = $('body > .navbar-collapse');

                window_width = $(window).width();

                fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();

                if (window_width > 767 && fixed_plugin_open == 'Dashboard') {
                    if ($('.fixed-plugin .dropdown').hasClass('show-dropdown')) {
                        $('.fixed-plugin .dropdown').addClass('open');
                    }

                }

                $('.fixed-plugin a').click(function (event) {
                    // Alex if we click on switch, stop propagation of the event, so the dropdown will not be hide, otherwise we set the  section active
                    if ($(this).hasClass('switch-trigger')) {
                        if (event.stopPropagation) {
                            event.stopPropagation();
                        } else if (window.event) {
                            window.event.cancelBubble = true;
                        }
                    }
                });

                $('.fixed-plugin .active-color span').click(function () {
                    $full_page_background = $('.full-page-background');

                    $(this).siblings().removeClass('active');
                    $(this).addClass('active');

                    var new_color = $(this).data('color');

                    if ($sidebar.length != 0) {
                        $sidebar.attr('data-color', new_color);
                    }

                    if ($full_page.length != 0) {
                        $full_page.attr('filter-color', new_color);
                    }

                    if ($sidebar_responsive.length != 0) {
                        $sidebar_responsive.attr('data-color', new_color);
                    }
                });

                $('.fixed-plugin .background-color .badge').click(function () {
                    $(this).siblings().removeClass('active');
                    $(this).addClass('active');

                    var new_color = $(this).data('background-color');

                    if ($sidebar.length != 0) {
                        $sidebar.attr('data-background-color', new_color);
                    }
                });

                $('.fixed-plugin .img-holder').click(function () {
                    $full_page_background = $('.full-page-background');

                    $(this).parent('li').siblings().removeClass('active');
                    $(this).parent('li').addClass('active');


                    var new_image = $(this).find("img").attr('src');

                    if ($sidebar_img_container.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
                        $sidebar_img_container.fadeOut('fast', function () {
                            $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
                            $sidebar_img_container.fadeIn('fast');
                        });
                    }

                    if ($full_page_background.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
                        var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

                        $full_page_background.fadeOut('fast', function () {
                            $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
                            $full_page_background.fadeIn('fast');
                        });
                    }

                    if ($('.switch-sidebar-image input:checked').length == 0) {
                        var new_image = $('.fixed-plugin li.active .img-holder').find("img").attr('src');
                        var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

                        $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
                        $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
                    }

                    if ($sidebar_responsive.length != 0) {
                        $sidebar_responsive.css('background-image', 'url("' + new_image + '")');
                    }
                });

                $('.switch-sidebar-image input').change(function () {
                    $full_page_background = $('.full-page-background');

                    $input = $(this);

                    if ($input.is(':checked')) {
                        if ($sidebar_img_container.length != 0) {
                            $sidebar_img_container.fadeIn('fast');
                            $sidebar.attr('data-image', '#');
                        }

                        if ($full_page_background.length != 0) {
                            $full_page_background.fadeIn('fast');
                            $full_page.attr('data-image', '#');
                        }

                        background_image = true;
                    } else {
                        if ($sidebar_img_container.length != 0) {
                            $sidebar.removeAttr('data-image');
                            $sidebar_img_container.fadeOut('fast');
                        }

                        if ($full_page_background.length != 0) {
                            $full_page.removeAttr('data-image', '#');
                            $full_page_background.fadeOut('fast');
                        }

                        background_image = false;
                    }
                });

                $('.switch-sidebar-mini input').change(function () {
                    $body = $('body');

                    $input = $(this);

                    if (md.misc.sidebar_mini_active == true) {
                        $('body').removeClass('sidebar-mini');
                        md.misc.sidebar_mini_active = false;

                        $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar();

                    } else {

                        $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar('destroy');

                        setTimeout(function () {
                            $('body').addClass('sidebar-mini');

                            md.misc.sidebar_mini_active = true;
                        }, 300);
                    }

                    // we simulate the window Resize so the charts will get updated in realtime.
                    var simulateWindowResize = setInterval(function () {
                        window.dispatchEvent(new Event('resize'));
                    }, 180);

                    // we stop the simulation of Window Resize after the animations are completed
                    setTimeout(function () {
                        clearInterval(simulateWindowResize);
                    }, 1000);

                });
            });
        });
    </script>
    <script>
        $(document).ready(function () {
            demo.checkFullPageBackgroundImage();
            setTimeout(function () {
                // after 1000 ms we add the class animated to the login/register card
                $('.card').removeClass('card-hidden');
            }, 700);
        });
    </script>
    <script>
        $(document).ready(function () {
            $('#datatables').DataTable({
                "columns": [
                    null,
                    {"width": "20%"},
                    null,
                    null,
                    null,
                    null
                ],
                "pagingType": "full_numbers",
                "lengthMenu": [
                    [10, 25, 50, -1],
                    [10, 25, 50, "All"]
                ],
                responsive: true,
                language: {
                    search: "_INPUT_",
                    searchPlaceholder: "Search records",
                }
            });

            var table = $('#datatable').DataTable();

            // Edit record
            table.on('click', '.edit', function () {
                $tr = $(this).closest('tr');
                var data = table.row($tr).data();
                alert('You press on Row: ' + data[0] + ' ' + data[1] + ' ' + data[2] + '\'s row.');
            });

            // Delete a record
            table.on('click', '.remove', function (e) {
                $tr = $(this).closest('tr');
                table.row($tr).remove().draw();
                e.preventDefault();
            });

            //Like record
            table.on('click', '.like', function () {
                alert('You clicked on Like button');
            });
        });
    </script>
    <script>
        $(document).ready(function () {
            // initialise Datetimepicker and Sliders
            md.initFormExtendedDatetimepickers();
            if ($('.slider').length != 0) {
                md.initSliders();
            }
        });
    </script>