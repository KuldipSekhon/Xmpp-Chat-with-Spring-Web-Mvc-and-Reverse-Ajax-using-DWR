<%@page import="org.jivesoftware.smack.packet.RosterPacket"%>
<%@page import="org.jivesoftware.smack.XMPPException.XMPPErrorException"%>
<%@page import="org.jivesoftware.smack.SmackException.NotConnectedException"%>
<%@page import="org.jivesoftware.smack.SmackException.NoResponseException"%>
<%@page import="org.jivesoftware.smackx.pubsub.PresenceState"%>
<%@page import="org.jivesoftware.smackx.vcardtemp.provider.VCardProvider"%>
<%@page import="org.jivesoftware.smack.provider.ProviderManager"%>
<%@page import="org.jivesoftware.smackx.vcardtemp.packet.VCard"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.jivesoftware.smack.RosterListener"%>
<%@page import="org.jivesoftware.smack.Roster"%>
<%@page import="org.jivesoftware.smack.packet.Presence"%>
<%@page import="org.jivesoftware.smack.RosterEntry"%>
<%@page import="org.jivesoftware.smack.XMPPConnection"%>
<%@page import="org.jivesoftware.smack.tcp.XMPPTCPConnection"%>
<%@page import="java.util.Collection"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ReverseClass.js'></script>
<!-- <script type='text/javascript' src='dwr/interface/XmppChatClass.js'></script> -->
<script type='text/javascript' src='dwr/util.js'></script>

<!-----------------// FROM MAIN INDEX PAGES -------------->
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
<script src="js/jquery-1.7.2.min.js" type="application/javascript" ></script>
<script src="js/left_event.js" type="application/javascript"></script>

<!--jQuery UI CSS-->
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" type="text/css" media="screen" />
	<!--jQuery and jQuery UI with jQuery Chat-->
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <link type="text/css" href="css/jquery.ui.chatbox.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery.ui.chatbox.js"></script>
	
    <script type="text/javascript">
				function getChatBox(username){
				var box = null;
				var chatname=username.split("name");
				var userchatid=username.split("@");
				
				var imagesrc=document.getElementById(chatname[0]+"presence").src;
				var imagesp=imagesrc.split("images/");
				var image=imagesp[1];
				
				var id="#"+userchatid[0]+"open_chat_box";
				$("#appendchatdiv").append("<div id='"+userchatid[0]+"open_chat_box'></div>");
				
				$(id).append("<div id='"+userchatid[0]+"chat_div'></div>");
				var chatdivid="#"+userchatid[0]+"chat_div";
			
					box = $(chatdivid).chatbox(
					{
						/*
							unique id for chat box
						*/
						id:"me",
                        user:
						{
							key : "value"
						},
						/*
							Title for the chat box
						*/
						title : chatname[0],
						imagenm : image,
						/*
							messageSend as name suggest,
							this will called when message sent.
						*/
						messageSent : function(id, user, msg)
						{
                            $(chatdivid).chatbox("option", "boxManager").addMsg(id, msg);
                        }
					});
		}
    </script>



<!-------------------/// FOR TAB ONLY --------------------->
<script type="text/javascript">
/* <![CDATA[ */
$(document).ready(function(){
	$("#tabs li").click(function() {
		//	First remove class "active" from currently active tab
		$("#tabs li").removeClass('active');

		//	Now add class "active" to the selected/clicked tab
		$(this).addClass("active");

		//	Hide all tab content
		$(".tab_content").hide();

		//	Here we get the href value of the selected tab
		var selected_tab = $(this).find("a").attr("href");

		//	Show the selected tab content
		$(selected_tab).fadeIn();

		//	At the end, we add return false so that the click on the link is not executed
		return false;
	});
});
/* ]]> */
</script>


<link rel="stylesheet" type="text/css" href="tabs.css" />
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">

$(document).ready(function() {

	$(".tab_content_1").hide();
	$(".tab_content_1:first").show(); 

	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content_1").hide();
		var activeTab = $(this).attr("rel"); 
		$("#"+activeTab).fadeIn(); 
	});
});

</script> 

<!----------------------// FOR TAB END HERE ---------------->
<!-----------------// FROM MAIN INDEX PAGES END HERE ------>

<script language="javascript">
   function onloadmethod() {
      dwr.engine.setActiveReverseAjax(true);
   }
</script>

<script type="text/javascript">
function updateChatBox(msglist){
	var id="#"+msglist[0]+"chat_div";
	var boxid=msglist[0]+"chatboxcreated";
	var elementExists=document.getElementById(boxid);
	var isvis= $("#"+boxid).is(":visible");
	if(elementExists==null){
		document.getElementById(msglist[2]+"name").click();
	}
	else if(!isvis){
		document.getElementById(boxid).style.display="block";
	}
	$(id).append(msglist[1]);
	var divid=msglist[0]+"chat_div";
    var divv=document.getElementById(divid);
	if(divv.scrollHeight > divv.clientHeight) {
    	divv.scrollTop=divv.scrollHeight; 
    } 
}
</script>

<script type="text/javascript">
function removeLastAppended(anyid){
	var id="#"+anyid;
	$(id).remove();
}
</script>

<script type="text/javascript">
function sendBuddyInvite(){
	var buddyId=document.getElementById("buddyInvite").value;
	$.ajax ({
    	url:"${pageContext.request.contextPath}/inviteBuddy",
    	data: {
    		buddyJID:buddyId,
    	},
    	success: function(data){
    		$("#buddyInvite").val("");
    		alert(data);
    	}
    });
}
</script>

<script type="text/javascript">
function friendRequest(from){
	var acceptfrom=from.split("acceptbtn");
		$.ajax ({
	    	url:"${pageContext.request.contextPath}/respondFrndReq",
	    	data: {
	    		fromJID:acceptfrom[0],
	    	},
	    	success: function(data){
	    		alert(data);
	    	}
	    });
}
</script>

<script type="text/javascript">
function friendDeny(from){
	var acceptfrom=from.split("denybtn");
		$.ajax ({
	    	url:"${pageContext.request.contextPath}/denyFrndReq",
	    	data: {
	    		fromJID:acceptfrom[0],
	    	},
	    	success: function(data){
	    		alert(data);
	    	}
	    });
}
</script>

<script type="text/javascript">
function createChatRow(addDivs){
	document.getElementById("test").innerHTML=addDivs;
}
</script>

<script type="text/javascript">
function closeConnection(){
	$.ajax ({
    	url:"${pageContext.request.contextPath}/logoutChat",
    	success: function(data){
    		window.location.href="${pageContext.request.contextPath}/index";
    	}
    });
	
}
</script>

<script type="text/javascript">
function changePresence(){
	var pres=document.getElementById("statusChangeSelect").value;
	$.ajax ({
    	url:"${pageContext.request.contextPath}/changedPresence",
    	data: {
    		presmode:pres,
    	},
    	success: function(data){
    	}
    });
}
</script>

<script type="text/javascript">
function getAltImage(imgid){
	var pic=document.getElementById(imgid);
	pic.src="images/chatuser.png"
}
</script>

</head>
<body onload="onloadmethod()">
<%HttpSession hsession = request.getSession();
XMPPConnection connection=(XMPPTCPConnection)hsession.getAttribute("xmppConnection");
String loggedUser=connection.getUser().split("/")[0];
String url=(String)request.getAttribute("imageurl");
String imgsrc=url+loggedUser+".jpg";
%>

<div class="header_main">
<!--------------// HEADER STARED HERE -------------->
    <div class="header">
    
                  <div class="logo"></div>
                     <!----------------// SEARC HERE ----------------------->
                      <div class="serach_top" onclick="tab_search()">
                      
                         
                      </div>
                      <div class="search_icon">
                         <img src="images/search.png" />
                      </div>
                    <!------------------/// SEARCH END HERE ------------------>
                  
                  <div class="user_deatils">
                  
                                   <div class="user_details_left">
                                   <a href="#" onclick="closeConnection()" >Logout</a>
                                   
                                                Welcome : <strong><%=loggedUser %></strong>
                                          
                                                <div class="claer"></div>
                                                
                                                <!--<span>My Account</span><div class="drop_down"></div> <span>| &nbsp;&nbsp;Logout</span>
                                                --->
                                                
                                   </div>
                                   
                                   <div class="user_images">
                                   </div>
                                          <!---------------// TOP ARROW ------------->
                  <div class="uparrowdiv">
                       <div class="login_photo">
                             <div class="change_photo">Change photo</div>
                       </div>
                       <div class="right_box">
                          <strong>Hariom Srivastava</strong>
                          <span>hariom15791@gmail.com</span>
                          <div class="full_profile">View profile</div>
                         
                          <div class="sign_out">Sign out</div>
                          <a href="#">Profile Setting</a>
                       </div>
                  </div>
                  <!--------------// TOP ARROW END ------------>
                  </div>
                  <div class="claer"></div>
    </div>
    
 
<!-------------// HEADER END HERE ------------------------->
<!-------------/// Header Menu Section Stared Here --------------->
    <div class="header_menu">
                   
                   <div class="menu_left">
                   
                                  <span>5 GB Available</span>
                                  <span class="font_weight"> Upgrade! </span>
                                  <div class="prgress_bar"></div>
                          
                   </div>
                   
                   <div class="menu_right">
                   
                                 <ul>
                                      
                                      <li><a href="#"><div class="home"></div> Home<div class="claer"></div></a>     
                                       </li>
                                      <li> <a href="#"> <div class="digtory"></div>DIRECTORY</a> </li>
                                      <li> <a href="#"> <div class="find"></div>Find</a> </li>
                                      <li> <a href="#"> <div class="download"></div>Download</a> </li>
                                      <li> <a href="#"> <div class="printer"></div>PRINT</a> </li>
                                      <li> <a href="#"> <div class="lock"></div>LOCK</a> </li>
                                      <li> <a href="#"> <div class="unlock"></div>UNLOCK</a> </li>
                                      <li> <a href="#"> <div class="create"></div>CREATE</a> </li>
                                      <li> <a href="#"> <div class="upload"></div>UPLOAD</a> </li>
                                      <li> <a href="#"> <div class="create_doc"></div>CREATE</a> </li>
                                      <li> <a href="#"> <div class="edit"></div>Edit</a> </li>
                                      <li> <a href="#"> <div class="update"></div>UPDATE</a> </li>
                                      <li> <a href="#"> <div class="cancel_edit"></div>CANCEL EDIT</a> </li>
                                      <li> <a href="#"> <div class="delet"></div>DELETE</a> </li>
                                      <li> <a href="#"> <div class="add_in"></div>ADD GROUP</a> </li>
                                      <li> <a href="#"> <div class="remove_group"></div>REMOVE GROUP</a> </li>
                                      <li> <a href="#"> <div class="history"></div>HISTORY</a> </li>
                                 </ul>
                   </div>
                   
                   <!-----------------/// SEARCH BOX PANNEL STARTED HERE ----------------->
            <div class="search_box_details">
            
                        <!-----------------Tab Content Here -=-------------------->
                           <div id="tabs_wrapper">
                                    <div id="tabs_container">
                                        <ul id="tabs">
                                            <li class="active"><a href="#tab1">BASIC</a></li>
                                            <li><a class="icon_accept" href="#tab2">ADVANCED</a></li>
                                            <li><a href="#tab3">RECENT SEARCH</a></li>
                                        </ul>
                                    </div>
                                    <div id="tabs_content_container">
                                        <div id="tab1" class="tab_content" style="display: block;">
                                        <!-------------// Tab Content Started Here ----------------->
                                        
                                            <ul> 
                                                <li>GROUP</li>
                                                <li>
                                                   <select>
                                                        <option> Group 1</option>
                                                        <option> Group 2</option>
                                                        <option> Group 3</option>
                                                   </select>
                                                </li>
                                                <li>USER</li>
                                                <li>
                                                   <input  type="text" />
                                                </li>
                                                <li>TITLE</li>
                                                <li>
                                                   <input  type="text" />
                                                </li>
                                                <li>KEYWORDS</li>
                                                <li>
                                                  <input  type="text" />
                                                </li>
                                                <li>DATE</li>
                                                <li></li>
                                                <li>LAST MONTH</li>
                                                <li>  
                                                  <select>
                                                        <option> Group 1</option>
                                                        <option> Group 2</option>
                                                        <option> Group 3</option>
                                                   </select>
                                                </li>
                                                <li>
                                                  <div class="search_icon_tab">
                                                     <img src="images/search.png" />
                                                  </div>
                                                
                                                </li>
                                            </ul>
                                         <!--------------/// Tab End Here --------------------------->   
                                        </div>
                                        <div id="tab2" class="tab_content">
                                            <!-----------TABN CONTENT STARED HERE ---------------->
                                              <ul>
                                                <li>FOLDER</li>
                                                <li><input  type="text" /></li>
                                                <li>TYPE</li>
                                                <li><input type="checkbox" />DOCUMENT <input type="checkbox" />FOLDER</li>
                                                <li>MIME TYPE</li>
                                                <li><input  type="text" /></li>
                                                <li>NOTES</li>
                                                <li><input  type="text" /></li>
                                                <li>
                                                  <div class="search_icon_tab">
                                                     <img src="images/search.png" />
                                                  </div>
                                                
                                                </li>
                                                 
                                              </ul>
                                            <!------------TAB CONTENT END HERE ---------------------->
                                        </div>
                                        <div id="tab3" class="tab_content">
                                            <p>Suspendisse blandit velit eget erat suscipit in malesuada odio venenatis.</p>
                                        </div>
                                    </div>
                          </div>
                        <!------------------Tab Content End Here ------------------>
                  
              </div>
<!-------------/// HEADER MENU SECTON END HERE ----------------->
</div>
<div class="claer"></div>
</div>
<!-----------/// Header Stared Here ------------>
<div id="head">

<!----------------/// Header End Here ---------->
<!------------// Testing CONTENT STARTED HERE ------------------>
<div id="content">




<!-----------------/// LEFT PANNEL ------------------>
 <div class="left-pane">
                  
                                 <div class="left_heading">
                                          
                                          <h1>Document Library</h1>
                                 
                                 </div>
                                 <div class="left_sub_menu">
                                 
                                         <ul>
                                             
                                             <li class="file_system tab_header" onClick="tab_left()"> 
                                             <a href="#" >FILE SYSTEM</a><div class="icon"></div>
                                             </li>
                                             
                                                           <div class="content_left">
                                                               
                                                               <ul>
                                                                   
                                                                   <li><div class="folder"></div></li>
                                                                   <li class="sub_imag">
                                                                         
                                                                             <ul>
                                                                                    
                                                                                     <li><div class="folder"></div>
                                                                                     
                                                                                            <ul>
                                                                                    
                                                                                                   <li><div class="folder"></div></li>
                                                                                                   <li><div class="folder"></div></li>
                                                                                                   <li><div class="folder"></div></li>
                                                                             
                                                                                          </ul>
                                                                                     
                                                                                     
                                                                                     </li>
                                                                                     <li><div class="folder"></div>
                                                                                          
                                                                                           <ul>
                                                                                    
                                                                                                   <li><div class="folder"></div></li>
                                                                                                   <li><div class="folder"></div></li>
                                                                                                   <li><div class="folder"></div></li>
                                                                             
                                                                                          </ul>
                                                                                     
                                                                                     
                                                                                     </li>
                                                                                     <li><div class="folder"></div></li>
                                                                             
                                                                             </ul>
                                                                   
                                                                   
                                                                   
                                                                   </li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   <li><div class="folder"></div></li>
                                                                   
                                                               </ul>
                                                                 
                                                           </div>
                                                           
                                             <li class="group_view tab_header document_library"> <a href="#" >GROUP VIEW</a> <div class="icon"></div></li>
                                                                  <div class="content_left">
                                                         
                                                                  </div>
                                             <li class="create_view tab_header document_library"> <a href="#" >CATEGORY VIEW</a><div class="icon"></div> </li>
                                                                 <div class="content_left">
                                                         
                                                                 </div>
                                             <li class="recent_open tab_header document_library"> <a href="#" >RECENTLY OPENED</a><div class="icon"></div> </li>
                                                                <div class="content_left">
                                                                           
                                                                </div>
                                             <li class="my_doc tab_header document_library" > <a href="#" >MY DOCUMENT</a><div class="icon"></div> </li>
                                                               <div class="content_left">
                                                                         
                                                               </div>
                                              
                                         </ul>
                                         
                                         
                                 </div>
              <div id="updates"></div>                   
                                 <div class="chaet_option" id="chat_options">
                                 
      <div>
      <input type="text" id="buddyInvite" />
      <input type="button" value="Invite" onclick="sendBuddyInvite()" />
      </div>
                                 
                                      <div class="cheat_heading" style="height: 32px;">
                                      <%VCard card = new VCard(); //To load VCard 
                                      try {
                                        card.load(connection); //load own vcard 
                                      } catch (NoResponseException e) {
                              			e.printStackTrace();
                              		} catch (XMPPErrorException e) {
                              			e.printStackTrace();
                              		} catch (NotConnectedException e) {
                              			e.printStackTrace();
                              		}  %>
                                        <div class="small_images" style="margin-left: 0px; margin-right: 12px;">
                                           <img src="<%=imgsrc %>" onerror="getAltImage(this.id)" id="noimage"/>
                                        </div>
                                      <%=card.getFirstName()%>
        <select id="statusChangeSelect" onchange="changePresence()" style="margin-left: 41px;">
        <option value="online">Online</option>
        <option value="away">Idle</option>
        <option value="dnd">Busy</option>
        <option value="offline">Offline</option>
        </select>
                                      </div>
                                      
                                      <div class="chaet_scroll cheat_height_big" id="test">
    <!-------- // CHEAT ROW STARTED HERE ------------->
                                                                                 
  <%Roster roster=connection.getRoster();
  Collection<RosterEntry> entries=roster.getEntries();
  System.out.println("ROSTER SIZE="+entries.size());
  Presence presence;
  ArrayList<String> pendingRequests=new ArrayList<String>();
  ArrayList<String> frndRequests=new ArrayList<String>();
  for(RosterEntry re:entries){
  	System.out.println("Buddy=="+re.getName()+" & Status="+re.getStatus()+" User="+re.getUser()+" type="+re.getType());
  	String user=re.getUser();	
  	String imagesrc=url+user+".jpg";
 	
  	presence=roster.getPresence(user);
  	System.out.println("before if "+user+" is offline type="+presence.getType()+"Mode="+presence.getMode());
  	RosterPacket.ItemType type=re.getType();
	if( re.getStatus()==null && type==RosterPacket.ItemType.both){
		 try {
	  		 card.load(connection, re.getUser()); //load Buddy's VCard
	  	 } catch (NoResponseException e) {
				e.printStackTrace();
			} catch (XMPPErrorException e) {
				e.printStackTrace();
			} catch (NotConnectedException e) {
				e.printStackTrace();
			} 
	   
	    System.out.println("vcard fname----vvvvvvvvvvvvvvvv "+card.getFirstName());
	 	System.out.println("vcard lname----vvvvvvvvvvvvvvvv "+card.getLastName());
	 	System.out.println("vcard avatar----vvvvvvvvvvvvv "+card.getAvatar());
	 	System.out.println("vcard nickname----vvvvvvvvvvvvvvvv "+card.getNickName());
	 	System.out.println("vcard email home----vvvvvvvvvvvvvvvv "+card.getEmailHome());
	 	System.out.println("vcard email work----vvvvvvvvvvvvv "+card.getEmailWork());
	 	System.out.println("vcard avatar mime type----vvvvvvvvvvvvv "+card.getAvatarMimeType());
	 	System.out.println("vcard jabberid----vvvvvvvvvvvvv "+card.getJabberId()); 
  	if(presence.getType()==Presence.Type.available){
  		Presence.Mode mode=presence.getMode();
  		System.out.println(user+" is online");
  		%>
  		      <div class="cheat_row" >
                 <div class="small_images">
                    <img src="<%=imagesrc %>" onerror="getAltImage(this.id)" id="<%=user+"noimage" %>" />
                 </div>
   <%if(mode==Presence.Mode.available || mode==null){ %>
                 <div class="contact_information">
                    <a href="#" id="<%=user+"name"%>" onclick="getChatBox(this.id)"><p><strong><%=card.getFirstName()+" "+card.getLastName() %></strong></p>
                <%if(presence.getStatus()!=null){ %>   
                     <p id="<%=user+"status"%>" style="margin-left: -94px; margin-top: 14px;" ><%=presence.getStatus() %></p>
                  <%} else {%>   
                  <p id="<%=user+"status"%>" style="margin-left: -94px; margin-top: 14px;" ></p>
                  <%} %>
                     </a>  
                                                    </div>
                                                    <div class="online_file" id="<%=user%>" >
                                                      <img src="images/online_file.png" id="<%=user+"presence"%>" />
           </div>
            <%} if(mode==Presence.Mode.away) {%>    
        <div class="contact_information">
                    <a href="#" id="<%=user+"name"%>" onclick="getChatBox(this.id)"><p><strong><%=card.getFirstName()+" "+card.getLastName() %></strong></p>
                     <p id="<%=user+"status"%>" style="margin-left: -94px; margin-top: 14px;" ><%=presence.getStatus() %></p></a>  
                                                    </div>
                                                    <div class="online_file" id="<%=user%>" >
                                                      <img src="images/bullet_orange.png" id="<%=user+"presence"%>"  />
           </div>
           <%} if(mode==Presence.Mode.dnd){ %>  
             <div class="contact_information">
                    <a href="#" id="<%=user+"name"%>" onclick="getChatBox(this.id)"><p><strong><%=card.getFirstName()+" "+card.getLastName() %></strong></p>
                     <p id="<%=user+"status"%>" style="margin-left: -94px; margin-top: 14px;" ><%=presence.getStatus() %></p></a>  
                                                    </div>
                                                    <div class="online_file" id="<%=user%>" >
                                                      <img src="images/bullet_red.png" id="<%=user+"presence"%>"  />
           </div>
           <%} %>                                     
                                              </div>
<% 	}
  	else{
  		System.out.println(user+" is offline type="+presence.getType()); %>
  		 <div class="cheat_row" >
         <div class="small_images" >
           <img src="<%=imagesrc %>" onerror="getAltImage(this.id)" id="<%=user+"noimage" %>" />
         </div>
         <div class="contact_information">
            <a href="#" id="<%=user+"name"%>" onclick="getChatBox(this.id)"><p><strong><%=card.getFirstName()+" "+card.getLastName() %></strong></p>
            <%if(presence.getStatus()!=null){ %>   
            <p id="<%=user+"status"%>" style="margin-left: -94px; margin-top: 14px;"><%=presence.getStatus() %></p>
            <%} else {%>   
            <p id="<%=user+"status"%>" style="margin-left: -94px; margin-top: 14px;" ></p>
            <%} %>
            </a>
         </div>
         <div class="online_file" id="<%=user%>" >
           <img src="images/off_line.png" id="<%=user+"presence"%>" />
         </div>
   
   </div>
 <% 	} } 
	 else if(re.getStatus()==null && (type==RosterPacket.ItemType.none || type==RosterPacket.ItemType.to)){
		 frndRequests.add(user); 
		 }
	else {
		 pendingRequests.add(user);
		 }
  }	
	for(String frnds:frndRequests){ %>
	 <div class="cheat_row" style="margin-bottom: 10px;">
     <div class="contact_information">
        <a href="#" id="<%=frnds+"name"%>"><p><strong><%=frnds %></strong><br/></p></a>
     </div>
     <div class="online_file" >
     <input type="button" value="Accept" onclick="friendRequest(this.id)" id="<%=frnds+"acceptbtn"%>" style="margin-left: -56px;"/>
     </div>
      <div class="online_file" >
     <input type="button" value="Deny" onclick="friendDeny(this.id)" id="<%=frnds+"denybtn"%>" style="margin-left: -56px; margin-top: 25px;"/>
     </div>
</div>
<%} for(String pending:pendingRequests){ %>
 <div class="cheat_row" style="margin-bottom: -16px;" >
         <div class="contact_information">
            <a href="#" id="<%=pending+"name"%>"><p><strong><%=pending %></strong><br/></p></a>
         </div>
   </div> 
<%} %>
                                     </div>
                                 
                                 </div>


                  </div>
<!----------------------/// left PANNEL ------------------>

<!-----------------------/// RIGHT PANNEL ------------------->
  <div class="strip_details">
                  
     
                  
                  
                               <div class="home_heading"></div>
                               <span>HOME</span>
                               <div class="path">Path:Home/Folder/New Folder/Silvereye.html</div>
                               <div class="right_icon">
                                    
                                    <div class="thum_view" onClick="tile_view();" title="Folder View"></div>
                                    <div class="list_view" onClick="list_view();" title="List View"></div>
                                    <div class="right_view right_view_color" onClick="left_view();" title="Right View"></div>
                                    <div class="bottom_view" onClick="bottom_view();" title="Bottom View"></div> 
                               </div>
                  </div>
                  
                        <div class="bottom">
                   
                            <!----------// SETTING MENU ------------->
                                <div class="list_menu">
                                     <ul>
                                        <li>
                                        <a href="#" onClick="bottom_view()">Bottom View</a>
                                        </li>
                                        <li><a href="#" onClick="left_view()">Left View</a></li>
                                     </ul>
                                
                                </div>
                            <!-------------// SETTING MENU ------------>
                   
                   
                      <div class="bottom_icon">
                   
                         <div class="bottom_top_view">
                             <div class="bottom_strip">
                              <!----------------------// LEFT VIEW ARE HERE ------------------------------>
                             </div>
                                    <!-------------/// ROW FIRST CONTENT STARTED HERE ---------------------->
                              <div class="thum_view_middle left_folder">
                                            <div class="row_text middle_tab " >
                                            
                                                     <div class="white">
                                                               
                                                               <div class="icon_folder" ></div>
                                                               <div class="folder_text">Folder View</div>
                                                     
                                                     </div>
                                            
                                               
                                            </div>
                                            <div class="claer"></div>
                                            <div class="row_content">
                                            
                                                    <ul>
                                                       
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                            
                                                   </ul>   
                                            </div>
                                            
                                  </div>          
                         <!-------------------/// ROW FIRST CONTENT END HERE ----------------------->    
                         
                         <!------------------/// LIST VIEW STARTED HERE ------------------------->
                         
                                  <div class="list_view_middle left_list">
                                  
                                            <div class="list_view_information">
                                                  
                                                  <ul>
                                                         <li>Name</li>
                                                         <li>Name</li>
                                                         <li>Title</li>
                                                         <li>Size</li>
                                                         <li>Date Modified </li>
                                                         <li>Author</li>
                                                         
                                                   </ul>      
                                            
                                            
                                            
                                            </div>
                                            <div class="list_view_content">
                                                
                                                  <ul>
                                                         <li><div class="icon_list"></div></li>
                                                         <li>Main_Folder</li>
                                                         <li>Main</li>
                                                         <li>20kb</li>
                                                         <li>26/07/2020</li>
                                                         <li>Admin</li>
                                                         
                                                   </ul> 
                                                   
                                                   
                                                   <ul class="gray">
                                                   
                                                         <li><div class="icon_list"></div></li>
                                                         <li>Name</li>
                                                         <li>Title</li>
                                                         <li>Size</li>
                                                         <li>Date Modified </li>
                                                         <li>Author</li>
                                                         
                                                   </ul> 
                                                   
                                                   <ul>
                                                         <li><div class="icon_list"></div></li>
                                                         <li>Name</li>
                                                         <li>Title</li>
                                                         <li>Size</li>
                                                         <li>Date Modified </li>
                                                         <li>Author</li>
                                                         
                                                   </ul> 
                                                   
                                            </div>
                                  
                                  
                                  
                                  
                                  </div>
                         
                         <!---------------/// LIST VIEW END HERE ----------------------------------->  
                             <table style="display:none;">
                                <tr >
                                   <td>Name<div class="border_left"></div></td>
                                   <td>Date<div class="border_left"></div></td>
                                   <td>Type<div class="border_left"></div></td>
                                   <td>Size<div class="border_left"></div></td>
                                   <td>Tags<div class="border_left"></div></td>
                                   <td>File version</td>
                                </tr>
                                <tr class="space">
                                   <td class="first_text left_potion"><div class="ms_office"></div>Main Folder</td>
                                   <td>21-09-2014</td>
                                   <td>Folder Type</td>
                                   <td>20 Kb</td>
                                   <td></td>
                                   <td>.doc</td>
                                </tr>
                                <tr >
                                   <td class="first_text left_potion"><div class="ms_office"></div>Main Folder</td>
                                   <td>21-09-2014</td>
                                   <td>Folder Type</td>
                                   <td>20 Kb</td>
                                   <td></td>
                                   <td>.doc</td>
                                </tr>
                             </table>
                           
                         
                         </div>
                      </div>   
                         <div class="claer"></div>
                         <div class="bottom_tab">
                         
                                            <!-------------------/// TAB STARTED HERE -------------------->

		<div id="container">

  <ul class="tabs"> 
        <li class="active" rel="tab4"><div class="peroperty_1"></div> PROPERTIES</li>
        <li rel="tab5"><div class="note_1"></div>NOTE</li>
        <li rel="tab6"><div class="keyword_1"></div>KEYWORDS</li>
        <li rel="tab7"><div class="perim_1"></div>PERMISSION</li>
        <li rel="tab8"><div class="history_right_1"></div>HISTORY</li>
        <li rel="tab9"><div class="prew_1"></div>PREVIEW</li>
        <a href="#"><div class="arrow_tab" onclick="tab_arrow();"></div></a>
    </ul>

<div class="tab_container"> 

     <div id="tab4" class="tab_content_1"> 
 
          <table>
            <tr>
              <td class="table_heading">UUID</td>
              <td>a0da7cc-017</td>
            </tr>
            <tr>
              <td class="table_heading"> Name</td>
              <td>Main Document folder</td>
            </tr>
            <tr>
                <td class="table_heading">Style</td>
                <td>folder icon</td>
            </tr>
            <tr>
                <td class="table_heading">Created</td>
                <td>15/07/2014 14:05 by Administrator</td> 
            </tr>
            <tr>
               <td class="table_heading">Subscribed</td>
               <td>No</td>
            
            </tr>
            
          </table>

     </div><!-- #tab1 -->
     <div id="tab5" class="tab_content_1"> 

       <p><img src="images/mortal combat.jpg"> <br />
      	<strong>
      	Mortal Kombat returns after a lengthy hiatus and puts players
      	back into the Tournament for 2D fighting with gruesome combat.
      	</strong>
       </p>

     </div><!-- #tab2 -->
     <div id="tab6" class="tab_content_1"> 

       <p><img src="images/halo.jpg"> <br />
      	<strong>
      	Halo: Reach is the culmination of the superlative combat, sensational
      	multiplayer, and seamless online integration that are the hallmarks
      	of this superb series.
      	</strong>
       </p>

     </div><!-- #tab3 -->
     <div id="tab7" class="tab_content_1"> 

       <p><img src="images/portal.jpg"> <br />
      	<strong>
      	Portal 2 is an action shooter game from Valve Software that draws 
      	from the original formula of innovative gameplay, story, and music,
      	which earned the original Portal more than 70 industry accolades.
      	</strong>
       </p>

     </div><!-- #tab4 --> 
     
          <div id="tab8" class="tab_content_1"> 

       <p><img src="images/portal.jpg"> <br />
      	<strong>
      	Portal 2 is an action shooter game from Valve Software that draws 
      	from the original formula of innovative gameplay, story, and music,
      	which earned the original Portal more than 70 industry accolades.
      	</strong>
       </p>

     </div><!-- #tab4 --> 
     
          <div id="tab9" class="tab_content_1"> 

       <p><img src="images/portal.jpg"> <br />
      	<strong>
      	Portal 2 is an action shooter game from Valve Software that draws 
      	from the original formula of innovative gameplay, story, and music,
      	which earned the original Portal more than 70 industry accolades.
      	</strong>
       </p>

     </div><!-- #tab4 --> 
     
 </div> <!-- .tab_container --> 

</div> <!-- #container -->
                         <!-------------------// TAB END HERE -------------------------->
                         
                           <div class="claer"></div>
                          </div>
                         
                   
                   </div><!---------BottOm_End -------------->
                  <div class="right_left_part left_border_star" id="appendchatdiv">
              
                         <div class="middle-pane left_border">
                           
                         
                         <!-------------/// ROW FIRST CONTENT STARTED HERE ---------------------->
                              <div class="thum_view_middle new_width">
                                            <div class="row_text middle_tab " >
                                            
                                                     <div class="white">
                                                               
                                                               <div class="icon_folder" ></div>
                                                               <div class="folder_text">Folder View</div>
                                                     
                                                     </div>
                                            
                                               
                                            </div>
                                            <div class="claer"></div>
                                            <div class="row_content">
                                            
                                                    <ul>
                                                       
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                                       <li>
                                                            <div class="folder_icon"></div>
                                                            <span>admin_user_<br/>list_perimission_5</span>
                                                       </li>
                                            
                                                   </ul>   
                                            </div>
                                            
                                  </div>          
                         <!-------------------/// ROW FIRST CONTENT END HERE ----------------------->    
                         
                         <!------------------/// LIST VIEW STARTED HERE ------------------------->
                         
                                  <div class="list_view_middle">
                                  
                                            <div class="list_view_information">
                                                  
                                                  <ul>
                                                         <li>Name</li>
                                                         <li>Name</li>
                                                         <li>Title</li>
                                                         <li>Size</li>
                                                         <li>Date Modified </li>
                                                         <li>Author</li>
                                                         
                                                   </ul>      
                                            
                                            
                                            
                                            </div>
                                            <div class="list_view_content">
                                                
                                                  <ul>
                                                         <li><div class="icon_list"></div></li>
                                                         <li>Main_Folder</li>
                                                         <li>Main</li>
                                                         <li>20kb</li>
                                                         <li>26/07/2020</li>
                                                         <li>Admin</li>
                                                         
                                                   </ul> 
                                                   
                                                   
                                                   <ul class="gray">
                                                   
                                                         <li><div class="icon_list"></div></li>
                                                         <li>Name</li>
                                                         <li>Title</li>
                                                         <li>Size</li>
                                                         <li>Date Modified </li>
                                                         <li>Author</li>
                                                         
                                                   </ul> 
                                                   
                                                   <ul>
                                                         <li><div class="icon_list"></div></li>
                                                         <li>Name</li>
                                                         <li>Title</li>
                                                         <li>Size</li>
                                                         <li>Date Modified </li>
                                                         <li>Author</li>
                                                         
                                                   </ul> 
                                                   
                                            </div>
                                  
                                  
                                  
                                  
                                  </div>
                         
                         <!---------------/// LIST VIEW END HERE ----------------------------------->            
                                                
                <!----------------/// RIGHT PART STARTED HERE -------------------> 
                                                <div class="right_icon_main option_left">
                                    <div class="right-pane">
                                       <ul class="icon_left_descri">
                                                          
                                                          <li class="padding_less">
                                                          <a href="#" onclick="left_icon()"><div class="left_icon"></div></a>
                                                          </li>
                                                          <li class="peroperty right_tab" >
                                                          <a href="#">PROPERTIES</a><div class="icon_right"></div></li>
                                                                   <div class="content_right"></div>
                                                          <li class="note right_tab"><a href="#">NOTES</a><div class="icon_right"></div>
                                                          </li>
                                                                   <div class="content_right"></div>
                                                          <li class="keyword right_tab"><a href="#">KEYWORDS</a><div class="icon_right">
                                                          </div></li>
                                                                   <div class="content_right"></div>
                                                          <li class="perim right_tab"><a href="#">PERMISSIONS</a><div class="icon_right">
                                                          </div></li>
                                                                   <div class="content_right"></div>
                                                          <li class="history_right right_tab"><a href="#">HISTORY</a>
                                                          <div class="icon_right"></div></li>
                                                                   <div class="content_right"></div>
                                                          <li class="prew right_tab"><a href="#">PREVIEW</a><div class="icon_right">
                                                          </div></li>
                                                                  
                                                                   
                                                                    
                                       </ul>
                  </div>
                  
                  <!------------------------------/// LEFT ICON ONLY --------------->
                                       <div class="left_icon_only">
                 
                            
                  
                             <div class="only_left_icon">
                                 <a href="#" onclick="left_icon()"><div class="left_icon back_pos"></div></a>
                                 <div class="claer"></div>
                                          <ul class="icon_left_descri new_icon_view">
                                                          <li class="peroperty right_tab" ></li>
                                                          <li class="note right_tab"> </li>
                                                          <li class="keyword right_tab"></li>
                                                          <li class="perim right_tab"></li> 
                                                          <li class="history_right right_tab"></li>
                                                          <li class="prew right_tab"></li>
                                                                  
                                                                   
                                                                    
                                       </ul>
                             </div>
                  </div>
                  
                   <!------------------------------/// LEFT ICON ONLY END  --------------->
                  
                    
                  </div>

                   <!----------------/// RIGHT PART END HERE ------------------->     
                             
                          
                         </div>
                   <!-------------/// MIDDLE END HERE ---------------->
                      

                   <div class="claer"></div> 
                   
                   
    <!--Chat box will be generated in this container-->
		<!-- <div id="open_chat_box"><div id="chat_div"></div></div>  -->        
                   
                   </div>
                   
                     <!-----------------//// BOTTOM VIEW STARTED HERE ------------------>
             
                  
<!-------------------------/// RIGHT PANNEL END ----------------->

</div>
<!-----------------/// Testing Content End Here ------------------>
<!-----------------/// TESTING FOOTER STARTED HERE ------------>
<div id="foot">

                     <!-------------------/// Footer Started Here ----------------------->
      <div class="footer">
           <ul class="left_footer">
             <li><a href="#">TERMS AND CONDITION</a></li>
             <li><a href="#">DISCLAIMER</a></li>
             <li><a href="#">CONTACT US</a></li>
             <li><a href="#">HELP</a></li>
           </ul>
           <div class="right_footer">
               <ul class="right_footer_1">
                 <li><a href="#"><img src="images/facebook.png" /></a></li>
                 <li><a href="#"><img src="images/twitter.png" /></a></li>
               </ul>
           
           </div>
      </div>
     <!-------------------/// Footer End Here ----------------------------> 

</div>
<!---------------/// TESTING FOOTER END HERE ----------------->
</body>
</html>