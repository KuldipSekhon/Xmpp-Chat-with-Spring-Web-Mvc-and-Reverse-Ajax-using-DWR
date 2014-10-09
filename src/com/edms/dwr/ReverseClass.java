package com.edms.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.proxy.dwr.Util;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.pubsub.PresenceState;

public class ReverseClass {
	
	public void createRoster(XMPPBOSHConnection connection, String loggedUser){
		String page=null;
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> allSessions= manager.getAllScriptSessions();
		for(ScriptSession ss:allSessions){
			System.out.println("in for ^^^^^^^^^^^^ "+ss.getAttribute("scriptAttribute"));
			if(ss.getAttribute("scriptAttribute").equals(loggedUser)){
				page=ss.getPage();
				System.out.println("page ^^^^^^^^^^^ "+ss.getPage());
				break;
			}
		  }
		ScriptSessionFilter filter=new TestScriptSessionFilter("scriptAttribute", loggedUser);
		final XMPPBOSHConnection xmppConnection = connection;
		Browser.withPageFiltered(page, filter, new Runnable() {
			
			@Override
			public void run() {
				Roster roster=xmppConnection.getRoster();
				Collection<RosterEntry> entries=roster.getEntries();
				System.out.println("reverse ROSTER SIZE="+entries.size());
				Presence presence;
				String addDivs="";
				String pendDivs="";
				String frndreqDivs="";
				for(RosterEntry re:entries){
					System.out.println("REVERSE Buddy="+re.getName()+" user="+re.getUser()+" status="+re.getStatus()+" type="+re.getType());
				 String user=re.getUser();
				 presence=roster.getPresence(user);
				 String type=re.getType().toString();
			     if( re.getStatus()==null && type.equals("both")){
				 if(presence.getType()==Presence.Type.available){
				  		String image="online_file.png";
				  		addDivs+="<div class='cheat_row'><div class='small_images'><img src='images/photo.jpg' /></div>"+
			"<div class='contact_information'><a href='#' id='"+user+"name' onclick='getChatBox(this.id,"+image+")'>"+
			"<p><strong>"+user+"</strong><br/>Work for fun</p></a></div>"+
			"<div class='online_file' id='"+user+"' ><img src='images/"+image+"' /></div></div>";
				 }
				  	else{ 
				  		String image="off_line.png";
				  		addDivs+="<div class='cheat_row'><div class='small_images'><img src='images/photo.jpg' /></div>"+
				  				"<div class='contact_information'><a href='#' id='"+user+"name' onclick='getChatBox(this.id,"+image+")'>"+
				  				"<p><strong>"+user+"</strong><br/>Work for fun</p></a></div>"+
				  				"<div class='online_file' id='"+user+"' ><img src='images/"+image+"' /></div></div>";
				  	} } 
			     else if(re.getStatus()==null && (type.equals("none") || type.equals("to"))){
			    	 frndreqDivs+="<div class='cheat_row' >"+
			    				"<div class='contact_information'><a href='#' id='"+user+"name' ><p><strong>"+user+"</strong><br/></p></a></div>"+
			    				"<div class='online_file' >"
			    						+ "<input type='button' value='Accept' onclick='friendRequest(this.id)' id='"+user+"acceptbtn' style='margin-left: -56px;'/></div></div>";
			     }
			     else {
			    	 pendDivs+="<div class='cheat_row' style='margin-bottom: -16px;'>"+
			"<div class='contact_information'><a href='#' id='"+user+"name' ><p><strong>"+user+"</strong><br/></p></a></div>"+
			"</div>";
				  	}}
				addDivs+=frndreqDivs+pendDivs;
				ScriptSessions.addFunctionCall("createChatRow", addDivs);
			}
		});	
	}
	
	public void updatePresence(ServerContext serverContext, Presence presence){		
		String[] divid = presence.getFrom().split("/");	
		String [] name=presence.getFrom().split("@");
		String avlblid=name[0]+"avlblimg";
		String statusid=divid[0]+"status";
  	    System.out.println("page=========="+serverContext.getContextPath());
  	    String currentPage=serverContext.getContextPath()+"/userChat";
  		Collection sessions = serverContext.getScriptSessionsByPage(currentPage);
		Util utilAll = new Util(sessions);
  		if(presence.isAvailable()){
  			Presence.Mode mode=presence.getMode();
  			System.out.println(presence.getFrom()+" is available=========================================== mode="+mode);
  			if(mode==Presence.Mode.available || mode==null){
			utilAll.setValue(divid[0], "<img src='images/online_file.png'>");
		  	utilAll.setValue(avlblid, "<img src='images/online_file.png' style='margin-left: 2px;margin-right: 4px;' />");
		  	utilAll.setValue(statusid, presence.getStatus());
  			}
  			else if(mode==Presence.Mode.away){
  				utilAll.setValue(divid[0], "<img src='images/bullet_orange.png'>");
  			  	utilAll.setValue(avlblid, "<img src='images/bullet_orange.png' style='margin-left: 2px;margin-right: 4px;' />");
  			  	utilAll.setValue(statusid, presence.getStatus());
  			}
            else if(mode==Presence.Mode.dnd){
            	utilAll.setValue(divid[0], "<img src='images/bullet_red.png'>");
    		  	utilAll.setValue(avlblid, "<img src='images/bullet_red.png' style='margin-left: 2px;margin-right: 4px;' />");
    		  	utilAll.setValue(statusid, presence.getStatus());
  			}
  			}
  		else {
			utilAll.setValue(divid[0], "<img src='images/off_line.png'>");
			utilAll.setValue(avlblid, "<img src='images/off_line.png' style='margin-left: 2px;margin-right: 4px;' />");
			utilAll.setValue(statusid, presence.getStatus());
		}
	}
	
	public void listeningForMessages(Message message) {
		String from=message.getFrom();
		final String newmsg=message.getBody();
		final String [] name=from.split("@");
		final String [] nameid=from.split("/");
		String page=null;
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> allSessions= manager.getAllScriptSessions();
		System.out.println("script session size="+allSessions.size());
		for(ScriptSession ss:allSessions){
			System.out.println("in for ^^^^^^^^^^^^ "+ss.getAttribute("scriptAttribute"));
			if(ss.getAttribute("scriptAttribute").equals(message.getTo())){
				page=ss.getPage();
				System.out.println("page ^^^^^^^^^^^ "+ss.getPage());
				break;
			}
		  }
		ScriptSessionFilter filter=new TestScriptSessionFilter("scriptAttribute", message.getTo());
		Browser.withPageFiltered(page, filter, new Runnable() {
			
			@Override
			public void run() {
				System.out.println("in run message ^^^^^^^^^^^^^^^^^");
				if(newmsg!=null){
					String msgarrived="<div class='ui-chatbox-msg' style='float:right'><p><b>"+name[0]+" : </b>"+newmsg+"</p></div>";
					String id=name[0]+"typing";
					ScriptSessions.addFunctionCall("removeLastAppended", id);
					ArrayList<String> msglist=new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(msgarrived);
					msglist.add(nameid[0]);
					ScriptSessions.addFunctionCall("updateChatBox", msglist);
				}
				else{
					String typeid=name[0]+"typing";
					String typing="<div id='"+typeid+"' class='ui-chatbox-msg'><p style='bottom: 69px;position: fixed;'><b>Typing..</b></p></div>";
					ArrayList<String> msglist=new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(typing);
					msglist.add(nameid[0]);
					ScriptSessions.addFunctionCall("updateChatBox", msglist);
				}
			}
		});
	}
	
	/*public void frndRequest(String from, String to){
		final String fromUser=from;
		String page=null;
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> allSessions= manager.getAllScriptSessions();
		for(ScriptSession ss:allSessions){
			System.out.println("in for ^^^^^^^^^^^^ "+ss.getAttribute("scriptAttribute"));
			if(ss.getAttribute("scriptAttribute").equals(to)){
				page=ss.getPage();
				System.out.println("page ^^^^^^^^^^^ "+ss.getPage());
				break;
			}
		  }
		ScriptSessionFilter filter=new TestScriptSessionFilter("scriptAttribute", to);
		Browser.withPageFiltered(page, filter, new Runnable() {
			
			@Override
			public void run() {
				System.out.println("in run ^^^^^^^^^^^^^^^^^");
				ScriptSessions.addFunctionCall("friendRequest", fromUser);
			}
		});
		}*/
}
