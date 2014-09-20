// JavaScript Document
// COMMON CLASS Only LEFT  PORTION



//COMMON FOR RIGHT PORTION
//$(document).ready(function() {
	
    
//});


// COMMON FOR MIDDLE PORTION
//$(document).ready(function() {$('.middle_tab').click(function(){
//var opend = true;
//if($(this).find('.icon_folder').attr('class').indexOf('folderminus')>-1);
//{opend=false;
//}
//$('.row_content').css('display','block');
//$('.icon_folder').removeClass('folderminus');
//$('.icon_folder').removeClass('folderplus');
//if(opend){
//$(this).next().next().css('display','block');
//$(this).find('.icon_folder').addClass('folderminus');}
// else
//  {$(this).next().next().css('display','none');
//$(this).find('.icon_folder').addClass('folderplus');} }
//	);
//	
//});


// COMMON FOR MIDDLE PORTION
//$(document).ready(function() {

    

//});

// TILE VIEW 
function tile_view(){
	
	$('.thum_view_middle').css('display','block');
	$('.thum_view').addClass('color_thum_icon');
	$('.list_view').removeClass('color_list_icon');
	$('.list_view_middle').css('display','none');
	$('.thum_view').removeClass('color_tile_icon');
	}
	function list_view(){
	
	$('.list_view_middle').css('display','block');
	$('.thum_view_middle').css('display','none');
	$('.list_view').addClass('color_list_icon');
	$('.thum_view').addClass('color_tile_icon');
	$('.thum_view').removeClass('color_thum_icon');
	
	}


//LEFT PART STARTED HERE

	
	function icon_1(){
				
				if($('.content_left_1').css('display')=='none')
				
						{
							$('.content_left').css('display','none');
							$('.content_left_1').css('display','block');
							$('.icon_1').addClass('icon_minus');
							$('.icon_1').removeClass('icon_plus');
							
						}
						
				else 
						{
							
							$('.content_left_1').css('display','none');
							$('.icon_1').removeClass('icon_minus');
							$('.icon_1').addClass('icon_plus');
							$('.content_left').css('display','none');
							
						}		
	
	}






// RIGHT PART MENU ONLY STARTED HERE
function icon_right(){
	
	if($('.content_right').css('display')=='none')
	{
		$('.content_right').css('display','block');
		$('.icon_right').addClass('icon_right_minus');
		$('.icon_right').removeClass('icon_right_plus');
		
		
		}
	else {
		
		$('.content_right').css('display','none');
		$('.icon_right').removeClass('icon_right_minus');
		$('.icon_right').addClass('icon_right_plus');
		
		
		}
	
	
	}
	
	/// MID FOLDER EVENT STARTED HERE 
	function folder_icon(){
		
		if($('.row_content').css('display')=='block')
		
		     {
			     $('.row_content').css('display','none');
				 $('.icon_folder').removeClass('folderminus');
				  $('.icon_folder').addClass('folderplus');
			
			    }
				
				else {
					
			     $('.row_content').css('display','block');
				 $('.icon_folder').removeClass('folderplus');
				 $('.icon_folder').addClass('folderminus');
					
					
					}
		
		
		
		}
		
		/// Bottom here 
function bottom_view(){
	
	if($('.bottom').css('display')=='none')
	{
		$('.bottom_view').addClass('bottom_view_color');
		$('.right_view').removeClass('right_view_color');
		$('.middle-pane').css('display','none');
		$('.right-pane').css('display','none');
		$('.bottom').css('display','block')
		$('.right_left_part').removeClass('left_border_star');
		$('.right-pane').css('display','none');
	    $('.only_left_icon').addClass('hide_left');
	     $('.thum_view_middle').removeClass('new_width');
		
		}
		else{
		$('.middle-pane').css('display','none');
		$('.right-pane').css('display','none');
		$('.bottom').css('display','block')
		$('.right_left_part').removeClass('left_border_star');
		$('.right-pane').css('display','none');

	
		
		}
	
	
	}
	
	/// LEFT VIEW 
	function left_view(){
	if($('.bottom').css('display')=='block')
	{
		    $('.bottom').css('display','none')
			$('.right-pane').css('display','block');
		    $('.middle-pane').css('display','block');
			$('.right_left_part').addClass('left_border_star');
           $('.only_left_icon').removeClass('hide_left');
		    $('.right_view').addClass('right_view_color');
			$('.bottom_view').removeClass('bottom_view_color');
			$('.thum_view_middle').addClass('new_width')
		}
	
	
	}
	
	/// FOR CHEAT OPTION 
	function tab_left(){
		
	/*	if($('#test').css('display')=="block")
		{
			    $('#test').addClass('cheat_height_big');
				$('#test').removeClass('cheat_height_small');
			}
			else{
				
				
					$('#test').addClass('cheat_height_small');
			        $('#test').removeClass('cheat_height_big');
				
				
				} */
				$('#test').toggleClass('cheat_height_big');
				$('#test').toggleClass('cheat_height_small');

		}
		
		
		function tab_arrow(){
			
			if($('.tab_container').css('display')=='block')
			    {
					$('.arrow_tab').addClass('icon_change_close');
					$('.arrow_tab').removeClass('icon_change_open');
					$('.tab_container').addClass('close');
					$('.tab_container').removeClass('open');
				    $('.active').css('border-bottom','1px solid #ccc')
					$('.bottom_icon').css('height','546');
					$('.bottom').css('height','546');
					
					
				}
				
				else 
				{
					$('.arrow_tab').removeClass('icon_change_close');
                    $('.arrow_tab').addClass('icon_change_open');
                    $('.tab_container').removeClass('close');
					
					$('.tab_container').animate({'height':'500px'},2000);
					//alert("hjuj");
					$('.active').css('border-bottom','1px solid #fff');
					$('.bottom_icon').css('height','300')
					$('.bottom').css('height','300');
				
			    }
			
			
			
			}
			
			
			// LEFT ICON ONLY STARTED HERE 
			
			function left_icon()
			{
	
				if($('.right-pane').css('display')=='block')
				{
					
					$('.right_icon_main').addClass('only_icon');
						$('.right_icon_main').removeClass('icon_descri');
						$('.thum_view_middle').css('margin-right','91')
						$('.thum_view_middle').addClass('for_margin');
						$('.middle-pane').addClass('cover_space');
                        $('.only_left_icon').removeClass('hide_left');
						    $('.thum_view_middle').removeClass('new_width');
					}
					
					else {
					$('.right_icon_main').removeClass('only_icon');
					$('.right_icon_main').addClass('icon_descri');
					$('.thum_view_middle').removeClass('for_margin');
					$('.middle-pane').removeClass('cover_space');
					 $('.only_left_icon').removeClass('hide_left');
					 $('.thum_view_middle').addClass('new_width');
						}
				
				
				}
				
				function setting(){
				if($('.list_menu').css('display')=='block')
				   {
					  $('.list_menu').css('display','none');
					
				   }
				   else {
					   
					   $('.list_menu').css('display','block');
					   
					   }
					
					}
					
					
					// Search Here 
					function search_1(){
						
						
						
						}
						
						
			// Basic Tab 
			
			function basic_tab(){
				
				if($('.basic_content').css('display')=='block')
				{
					$('.basic_content').css('display','block');
					$('.basic').addClass('bg_color');
                    $('.basic_content').css('display','block')
					}
					else
					{
						$('.basic').removeClass('bg_color');
						}
				
				}		
				
				function basic_tab_close(){
				
				if($('.basic_content').css('display')=='none')
				{
					$('.basic_content').css('display','block');
					$('.basic').addClass('bg_color');
					}
					else
					{
						$('.basic').removeClass('bg_color');
						}
				
				}
				///Advanced Here 
				function advanced_tab(){
				
				if($('.advanced_content').css('display')=='none')
				{
					$('.advanced_content').css('display','block');
					$('.advanced').addClass('bg_color');
					$('.basic_content').css('display','none')

					}
					else
					{
						$('.advanced').removeClass('bg_color');
						}
				
				}	
				
			function tab_search(){
				if($('.search_box_details').css('display')=='none')
				{
					
					$('.search_box_details').css('display','block')
					
					}
				else
				{
					
					$('.search_box_details').css('display','none')
					
					}
				}
				
				

				
				/// For Chat Option Only Started Here 
$(document).ready(function() {
	//Top
	$('.right_tab').click( function(){
	
		var opend=false;
		if($(this).find('.icon_right').attr('class').indexOf('icon_right_minus')>-1)
		{
		opend=true;
		}
			  $('.content_right').css('display','none');
			  $('.icon_right').removeClass('icon_right_minus');
			  $('.icon_right').removeClass('icon_right_plus');
			  if(!opend)
			  {
				  $(this).find('.icon_right').addClass('icon_right_minus');
				  $(this).next('.content_right').css('display','block');
				  
				  }
			  else {
				  
				  $(this).find('.icon_right').addClass('icon_right_plus');
				  $(this).next('.content_right').css('display','none')
				  
				  }
			  
		  
	}
	);
	
	//Middle
	$('.middle_tab').click(
            function() {

                var opend = true;
                if ($(this).find('.icon_folder').attr('class').indexOf('folderminus') > -1)
                {
                    opend = false;
                }
                $('.row_content').css('display', 'block');
                $('.icon_folder').removeClass('folderminus');
                $('.icon_folder').removeClass('folderplus');
                if (opend) {
                    $(this).next().next().css('display', 'block');
                    $(this).find('.icon_folder').addClass('folderminus');
                } else {
                    $(this).next().next().css('display', 'none');
                    $(this).find('.icon_folder').addClass('folderplus');
                }

            }



    );
	
	//Bottom
    $('.tab_header').click(function() { 
        var opend = false;
        if ($(this).find('.icon').attr('class').indexOf('icon_minus') > -1) {
            opend = true;
        }
        $('.content_left').css('display', 'none');
        $('.icon').removeClass('icon_minus');
        $('.icon').removeClass('icon_plus');
        if (!opend) {
            $(this).find('.icon').addClass('icon_minus');
            $(this).next('.content_left').css('display', 'block');
        } else {
            $(this).find('.icon').addClass('icon_plus');
            $(this).next('.content_left').css('display', 'none');
        }		
    });
	$('.document_library').click(function() {
			$('#test').toggleClass('cheat_height_small');
			$('#test').toggleClass('cheat_height_big');			
	});
	
	// Folder View,List View,Left View,Bottom View
	$('.user_images').click(function(){
		
		if($('.uparrowdiv').css('display')=='none')
		{
			$('.uparrowdiv').css('display','block');
			}
			else{
				$('.uparrowdiv').css('display','none');
				
				}
		
		});
		
		
		///Appoment Tab Started Here 
		
			$('.tab_menu_1').click(function() {
			if($('.appoment_tab_1').css('display')=='none')
			{
				$('.appoment_tab_2').css('display','none');
				$('.tab_menu_1').addClass('tab_menu_1_bg')
				$('.appoment_tab_1').css('display','block');
				$('.tab_menu_2').removeClass('tab_menu_2_bg');
				
				}		
	});
	
			$('.tab_menu_2').click(function() {
			if($('.appoment_tab_2').css('display')=='none')
			{
				$('.appoment_tab_2').css('display','block');
				$('.appoment_tab_1').css('display','none');
				$('.tab_menu_1').removeClass('tab_menu_1_bg');
				$('.tab_menu_2').addClass('tab_menu_2_bg');
				}		
	});
	
	
	
	
	
	/// Add New User 
	
	  $('.add_user_js').click(function(){
		       	
				   if($('.add_user_content').css('display')=='block')
				   {
				       $('.add_user_content').css('display','block');
					   $('.add_user_js').addClass('add_user_bg');
					   $('.loged_user_content').css('display','none');
					   $('.logged_user_js').removeClass('log_user_bg');
					   $('.User_List_js').removeClass('user_list_bg');
					   $('.User_List_content').css('display','none');
					   $('.user_profile_content').css('display','none');
					   $('.user_profile_js').removeClass('user_profile_bg');
					   $('.mini_type_content').css('display','none');
					   $('.mini_type_js').removeClass('mini_type_bg');
				

				   }
				   else{
					    $('.add_user_content').css('display','block');
						 $('.add_user_js').addClass('add_user_bg');
					    $('.loged_user_content').css('display','none');
						$('.logged_user_js').removeClass('log_user_bg');
						$('.user_profile_js').removeClass('user_profile_bg');
						$('.user_profile_content').css('display','none');
						$('.User_List_js').removeClass('user_list_bg');
						$('.User_List_content').css('display','none');
						$('.logged_page_current').css('display','block');
						$('.logged_page_profile').css('display','none');
				        $('.logged_page_list').css('display','none');
						
					   
					   
					   }
	
		      });
	
	//Loged User Tab Stared Here 
	   //Loged User 
	   
			   
            $('.logged_user_js').click(function(){
		       	
				   if($('.loged_user_content').css('display')=='none')
				   {
				       $('.add_user_js').removeClass('add_user_bg');
					   $('.loged_user_content').css('display','block');
					   $('.logged_user_js').addClass('log_user_bg');
					   $('.User_List_js').removeClass('user_list_bg');
					   $('.User_List_content').css('display','none');
					   $('.user_profile_content').css('display','none');
					   $('.user_profile_js').removeClass('user_profile_bg');
					   $('.mini_type_content').css('display','none');
					   $('.mini_type_js').removeClass('mini_type_bg');
				       $('.add_user_content').css('display','none');

				   }
	
		      });
	   
	   //User List
	   
	   	   	$('.User_List_js').click(function(){
		
		       if($('.User_List_content').css('display')=='none')
			   {
				   $('.add_user_js').removeClass('add_user_bg');
				   $('.User_List_content').css('display','block');
				   $('.User_List_js').addClass('user_list_bg');
				   $('.loged_user_content').css('display','none');
				   $('.logged_user_js').removeClass('log_user_bg');
				   $('.user_profile_content').css('display','none');
				   $('.user_profile_js').removeClass('user_profile_bg');
				   $('.mini_type_content').css('display','none');
				   $('.mini_type_js').removeClass('mini_type_bg');
				   $('.logged_page_profile').css('display','none');
				   $('.logged_page_list').css('display','block');
				   $('.logged_page_current').css('display','none');
				   $('.add_user_content').css('display','none');

				   }
	
		});
	   
	   
	   
	   //User Profile
	          
			     	   	$('.user_profile_js').click(function(){
		
		       if($('.user_profile_content').css('display')=='none')
			   {
				   $('.add_user_js').removeClass('add_user_bg');
				   $('.user_profile_content').css('display','block');
				   $('.user_profile_js').addClass('user_profile_bg')
				   $('.User_List_content').css('display','none');
				   $('.User_List_js').removeClass('user_list_bg');
				   $('.loged_user_content').css('display','none');
				   $('.logged_user_js').removeClass('log_user_bg');
				   $('.mini_type_content').css('display','none');
				   $('.mini_type_js').removeClass('mini_type_bg');
				   $('.logged_page_profile').css('display','block');
				   $('.logged_page_list').css('display','none');
				   $('.logged_page_current').css('display','none');
				   $('.add_user_content').css('display','none');

				   }
	
		       });
	   
	    
	   // Mini Type 
	
	         
			   $('.mini_type_js').click(function(){
		
		       if($('.mini_type_content').css('display')=='none')
			   {
				   $('.add_user_js').removeClass('add_user_bg');
				   $('.mini_type_content').css('display','block');
				   $('.mini_type_js').addClass('mini_type_bg');
				   $('.user_profile_content').css('display','none');
				   $('.user_profile_js').removeClass('user_profile_bg')
				   $('.User_List_content').css('display','none');
				   $('.User_List_js').removeClass('user_list_bg');
				   $('.loged_user_content').css('display','none');
				   $('.logged_user_js').removeClass('log_user_bg');
				   $('.add_user_content').css('display','none');

				   }
	
		       });
			   
	
		
	

});