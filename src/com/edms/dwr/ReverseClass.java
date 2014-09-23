package com.edms.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ServerContext;
import org.directwebremoting.proxy.dwr.Util;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class ReverseClass {
	
	public void updatePresence(ServerContext serverContext, Presence presence){
		String[] divid = presence.getFrom().split("/");	  	
  	    System.out.println("page=========="+serverContext.getContextPath());
  	    String currentPage=serverContext.getContextPath()+"/userChat";
  		Collection sessions = serverContext.getScriptSessionsByPage(currentPage);
		Util utilAll = new Util(sessions);
  		if(presence.isAvailable()){
  			System.out.println(presence.getFrom()+" is available===========================================");
  			utilAll.setValue(divid[0], "<img src='images/online_file.png'>");
  			presence.getStatus();
  			
  			}
  		else {
			utilAll.setValue(divid[0], "<img src='images/off_line.png'>");
		}
	}
	
	public void listeningForMessages(ServerContext serverContext, Message message) {
		String from=message.getFrom();
		String newmsg=message.getBody();
		String [] name=from.split("@");
		String currentPage=serverContext.getContextPath()+"/userChat";
		Collection sessions = serverContext.getScriptSessionsByPage(currentPage);
		Util utilAll = new Util(sessions);
		if(newmsg!=null){
			String msgarrived="<p><b>"+name[0]+" : </b>"+newmsg+"</p>";
			String id=name[0]+"typing";
			utilAll.addFunctionCall("removeLastAppended", id);
			ArrayList<String> msglist=new ArrayList<String>();
			msglist.add(name[0]);
			msglist.add(msgarrived);
		    utilAll.addFunctionCall("updateChatBox", msglist);
		}
		else{
			String typeid=name[0]+"typing";
			String typing="<p id='"+typeid+"' style='bottom: 65px;position: fixed;'><b>Typing..</b></p>";
			ArrayList<String> msglist=new ArrayList<String>();
			msglist.add(name[0]);
			msglist.add(typing);
			utilAll.addFunctionCall("updateChatBox", msglist);
		}
	}

/*	public void getReverseRoster() {

		HttpSession session = WebContextFactory.get().getSession();
		XMPPBOSHConnection xmppConnection = (XMPPBOSHConnection) session.getAttribute("xmppConnection");

		WebContext webContext = WebContextFactory.get();
		final String currentPage = webContext.getCurrentPage();
		ServletContext servletContext = webContext.getServletContext();
		final ServerContext serverContext = ServerContextFactory.get(servletContext);

		System.out.println("session for connected=" + session);
		System.out.println("is connectes ??=" + xmppConnection.isConnected());
		Roster roster = xmppConnection.getRoster();
		roster.addRosterListener(new RosterListener() {
			@Override
			public void presenceChanged(Presence presence) {
				System.out.println("Presence changed: " + presence.getFrom()
						+ " " + presence);
				String[] divid = presence.getFrom().split("/");
				try {
					Collection sessions = serverContext.getScriptSessionsByPage(currentPage);
					Util utilAll = new Util(sessions);
					if (presence.isAvailable()) {
						System.out.println(presence.getFrom()+ " is available===========================================");
						utilAll.setValue(divid[0], "<img src='images/online_file.png'>");
					} else {
						utilAll.setValue(divid[0], "<img src='images/off_line.png'>");
					}
				} catch (Exception e) {
					System.out.println("Error in Update");
					e.printStackTrace();
				}
			}

			@Override
			public void entriesUpdated(Collection<String> arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void entriesDeleted(Collection<String> arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void entriesAdded(Collection<String> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}*/

   /* public void updateMessages(){
    	HttpSession session = WebContextFactory.get().getSession();
		XMPPBOSHConnection xmppConnection = (XMPPBOSHConnection) session.getAttribute("xmppConnection");

		WebContext webContext = WebContextFactory.get();
		final String currentPage = webContext.getCurrentPage();
		ServletContext servletContext = webContext.getServletContext();
		final ServerContext serverContext = ServerContextFactory.get(servletContext);
	    ChatManager.getInstanceFor(xmppConnection).addChatListener(new ChatManagerListener() {
		
		@Override
		public void chatCreated(Chat chat, boolean createdLocally) {
			chat.addMessageListener(new MessageListener() {
				
				@Override
				public void processMessage(Chat chat, Message message) {
					String from=message.getFrom(); 
					String newmsg=message.getBody();
					String[] name=from.split("@");
					System.out.println("Received new msg="+newmsg+" from "+from);
					Collection sessions=serverContext.getScriptSessionsByPage(currentPage); Util
					utilAll=new Util(sessions); 
					String msgarrived="<br><p><b>"+name[0]+" : </b>"+newmsg+"</p>";
					utilAll.addFunctionCall("updateChatBox", msgarrived);
				}
			} );
		}
	});
    }*/
	 
	  
		 
		 
		 
		 
		 
		 
		 
		 
		 
		/* @Override public void chatCreated(Chat chat, boolean createdLocally) {
	  //chat = XmppChatClass.chat; chat.addMessageListener(new
	MessageListener() {
	  
	 @Override public void processMessage(Chat chats, Message messages) {
	  String from=messages.getFrom(); String newmsg=messages.getBody(); String
	  [] name=from.split("@");
	  System.out.println("Received new msg="+newmsg+" from "+from);
	  //update(newmsg); Collection
	  sessions=serverContext.getScriptSessionsByPage(currentPage); Util
	  utilAll=new Util(sessions); //utilAll.setValue("chat_div",
	  "<p><b>"+name[0]+" : </b>"+newmsg+"</p>"); String
	  msgarrived="<br><p><b>"+name[0]+" : </b>"+newmsg+"</p>";
	  utilAll.addFunctionCall("updateChatBox", msgarrived);
	 
	  //ScriptBuffer scriptBuffer=new ScriptBuffer();
	  //scriptBuffer.appendData(str) } }); } });
	  
	  }
*/
	
	 /* public void listeningForMessages(){ 
		  HttpSession session = WebContextFactory.get().getSession();
			XMPPBOSHConnection xmppConnection = (XMPPBOSHConnection) session.getAttribute("xmppConnection");
		  PacketFilter filter=new AndFilter(new PacketTypeFilter(Message.class)); 
		  PacketCollector collector=xmppConnection.createPacketCollector(filter); 
		  while(true){
	           Packet packet=collector.nextResult();
	           if (packet instanceof Message){
	           Message message=(Message)packet; 
	           if (message != null && message.getBody()!= null) 
	        	   System.out.println("Received message from " + packet.getFrom() +
	  " : " + (message != null ? message.getBody() : "NULL")); } } }*/
	 

	/**
	 * This method continually calls the update method utill the for loop
	 * completes
	 */
	/*
	 * public void callReverseDWR() {
	 * System.out.println(" Ur in callReverseDWR "); try { for (int i = 0; i <
	 * 10; i++) { update(); try { Thread.sleep(1000); } catch
	 * (InterruptedException e) { e.printStackTrace(); } } } catch (Exception e)
	 * { System.out.println("Error in callReverseDWR"); e.printStackTrace(); } }
	 */

	/**
	 * This method updates ReversePage.jsp
	 * <ul id="updates">
	 * using dwr reverse ajax
	 */
	/*
	 * public void update(String newmsg) { try {
	 * System.out.println("in update !!!!!!!!!!!!!  "+currentPage); List<Data>
	 * messages = new ArrayList<Data>(); messages.add(new Data(newmsg));
	 * Collection sessions=serverContext.getScriptSessionsByPage(currentPage);
	 * Util utilAll=new Util(sessions); utilAll.addOptions("chat_div", messages,
	 * "value"); System.out.println("afetr add option");
	 * 
	 * } catch (Exception e) { System.out.println("Error in Update");
	 * e.printStackTrace(); } }
	 */

}
