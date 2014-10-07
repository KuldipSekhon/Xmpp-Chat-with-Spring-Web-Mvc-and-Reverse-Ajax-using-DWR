package com.edms.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.proxy.dwr.Util;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
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
				for(RosterEntry re:entries){
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
			     else if(re.getStatus()==null && type.equals("none")){
			    	 addDivs+="<div class='cheat_row' ><div class='small_images'>Friend Request</div>"+
			    				"<div class='contact_information'><a href='#' id='"+user+"name' ><p><strong>"+user+"</strong><br/></p></a></div>"+
			    				"<div class='online_file' id="+user+" >"
			    						+ "<input type='button' value='Accept' onclick='friendRequest(this.id)' id='"+user+"acceptbtn' style='margin-left: -56px;'/></div></div>";
			     }
			     else {
			    	 addDivs+="<div class='cheat_row' ><div class='small_images'>Pending Request</div>"+
			"<div class='contact_information'><a href='#' id='"+user+"name' ><p><strong>"+user+"</strong><br/></p></a></div>"+
			"<div class='online_file' id="+user+" ></div></div>";
				  	}}
				ScriptSessions.addFunctionCall("createChatRow", addDivs);
			}
		});	
	}
	
	public void updatePresence(ServerContext serverContext, Presence presence){		
		String[] divid = presence.getFrom().split("/");	
		String [] name=presence.getFrom().split("@");
		String avlblid=name[0]+"avlblimg";
  	    System.out.println("page=========="+serverContext.getContextPath());
  	    String currentPage=serverContext.getContextPath()+"/userChat";
  		Collection sessions = serverContext.getScriptSessionsByPage(currentPage);
		Util utilAll = new Util(sessions);
  		if(presence.isAvailable()){
  			System.out.println(presence.getFrom()+" is available===========================================");
			utilAll.setValue(divid[0], "<img src='images/online_file.png'>");
		  	utilAll.setValue(avlblid, "<img src='images/online_file.png' style='margin-left: 2px;margin-right: 4px;' />");
  			}
  		else {
			utilAll.setValue(divid[0], "<img src='images/off_line.png'>");
			utilAll.setValue(avlblid, "<img src='images/off_line.png' style='margin-left: 2px;margin-right: 4px;' />");
		}
	}
	
	public void listeningForMessages(Message message) {
		String from=message.getFrom();
		final String newmsg=message.getBody();
		final String [] name=from.split("@");
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
					ScriptSessions.addFunctionCall("updateChatBox", msglist);
				}
				else{
					String typeid=name[0]+"typing";
					String typing="<div id='"+typeid+"' class='ui-chatbox-msg'><p style='bottom: 69px;position: fixed;'><b>Typing..</b></p></div>";
					ArrayList<String> msglist=new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(typing);
					ScriptSessions.addFunctionCall("updateChatBox", msglist);
				}
			}
		});
	}
	
	public void frndRequest(String from, String to){
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
		}
}
