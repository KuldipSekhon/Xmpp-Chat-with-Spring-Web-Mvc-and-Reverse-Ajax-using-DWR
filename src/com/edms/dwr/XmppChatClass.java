package com.edms.dwr;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XmppChatClass {
	
	@Autowired private HttpSession httpSession;
	@Autowired private ServletContext servletContext;
	
	private static final int packetReplyTimeout = 20000; // millis
	private static XMPPBOSHConnection xmppConnection;
	private static ChatManager chatManager;
	private static MessageListener messageListener;
	private static XMPPConnection conn;
	public static Chat chat;
	/*private Boolean https;
	private String host;
	private int port;
	private String filePath;
	private String xmppDomain;
	private String username;
	private String password;*/
	
	private int count=0;
	
	
	
	/*public XmppChatClass(Boolean https, String host, int port, String filePath,
			String xmppDomain, String username, String password){
		this.https=https;
		this.host=host;
		this.port=port;
		this.filePath=filePath;
		this.xmppDomain=xmppDomain;
		this.username=username;
		this.password=password;		
	}*/
	
	public void createConnection(Boolean https, String host, int port, String filePath, String xmppDomain){
		System.out.println("initializing connection to server"+host);
		SmackConfiguration.setDefaultPacketReplyTimeout(packetReplyTimeout);
		/**
		 * Create a HTTP Binding configuration.
		 * 
		 * @param https
		 *            true if you want to use SSL (e.g. false for
		 *            http://domain.lt:7070/http-bind).
		 * @param host
		 *            the hostname or IP address of the connection manager (e.g.
		 *            domain.lt for http://domain.lt:7070/http-bind).
		 * @param port
		 *            the port of the connection manager (e.g. 7070 for
		 *            http://domain.lt:7070/http-bind).
		 * @param filePath
		 *            the file which is described by the URL (e.g. /http-bind
		 *            for http://domain.lt:7070/http-bind).
		 * @param xmppDomain
		 *            the XMPP service name (e.g. domain.lt for the user
		 *            alice@domain.lt)
		 */
		
		BOSHConfiguration config=new BOSHConfiguration(https, host, port, filePath, xmppDomain);
		// Create a configuration for the connection
		config.setSecurityMode(SecurityMode.required);
		config.setRosterLoadedAtLogin(true);
		config.setCompressionEnabled(true); 
		System.out.println("service name="+config.getServiceName());
		System.out.println("host="+config.getHostAddresses());
		System.out.println("security mode="+config.getSecurityMode());
		try {
			System.out.println("uri="+config.getURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		// Create a new XMPP connection object
	    xmppConnection=new XMPPBOSHConnection(config);
		try {
			// Connect to the server
			xmppConnection.connect();
			httpSession.setAttribute("xmppConnection", xmppConnection);
			System.out.println("Connected to server............"+xmppConnection.isConnected());
			System.out.println("connection id="+xmppConnection.getConnectionID());
			
			//chatManager=chatManager.getInstanceFor(xmppConnection);
			
		} catch (NotConnectedException nce) {
			nce.printStackTrace();
		} catch (SmackException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (XMPPException xme) {
			xme.printStackTrace();
		}
	}
	
	public void getRoster(){
		Roster roster=xmppConnection.getRoster();
		roster.addRosterListener(new RosterListener() {
			  	@Override
			  	public void presenceChanged(Presence presence) {
			  		System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
			  		if(presence.isAvailable()){
			  			System.out.println(presence.getFrom()+" is available===========================================");
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
	}
	
	public void performLogin(String username, String password){
		try{
		//login to the connected server
		xmppConnection.login(username, password);
			
		Presence presence=new Presence(Presence.Type.available);
		presence.setStatus("i am online");
		xmppConnection.sendPacket(presence);
			
		AccountManager am = AccountManager.getInstance(xmppConnection);
		am.getAccountAttributes();
			
		am.getAccountAttribute("FN");
		am.getAccountAttribute("NICKNAME");
		am.getAccountAttribute("EMAIL");
		am.getAccountAttribute("FAMILY");
		} catch (NotConnectedException nce) {
			nce.printStackTrace();
		} catch (SmackException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (XMPPException xme) {
			xme.printStackTrace();
		}
	}
	
	/*public void sendMessage(String message, String buddyJID){
		System.out.println("Sending Message "+message+" to buddy "+buddyJID);
		chat=ChatManager.getInstanceFor(xmppConnection).createChat(buddyJID, messageListener);
		try {
			chat.sendMessage(message);
			System.out.println("Message sent successfully");
		} catch (NotConnectedException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}*/
	
	/*public void sendMessage(String message, String buddyJID){
		System.out.println("Sending Message "+message+" to buddy "+buddyJID);
		Message msg=new Message(buddyJID, Message.Type.chat);
		msg.setBody(message);
		try {
			xmppConnection.sendPacket(msg);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println("Message sent successfully");
	}*/
	
	public void sendMessage(String message, String buddyJID){
		System.out.println("Sending Message "+message+" to buddy "+buddyJID);
		Chat chat=chatManager.getInstanceFor(xmppConnection).createChat(buddyJID, new MessageListener() {
			
			@Override
			public void processMessage(Chat chats, Message messages) {
				String from=messages.getFrom();
				String newmsg=messages.getBody();
				
				ServerContext serverContext = ServerContextFactory.get(servletContext);
				
				(new ReverseClass()).testing(serverContext, newmsg);
				System.out.println("SERVICE Received new msg="+newmsg+" from "+from);
			}
		});
		try {
			chat.sendMessage(message);
			System.out.println("Message sent successfully");
		} catch (NotConnectedException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	/*public void sendAndReceiveMessages(String message, String buddyJID){
		System.out.println("Sending msg "+message+" to "+buddyJID);
		Chat chat=chatManager.getInstanceFor(xmppConnection).createChat(buddyJID, new MessageListener() {
			
			@Override
			public void processMessage(Chat arg0, Message arg1) {
				// TODO Auto-generated method stub
				
			}
		})
	}*/

	
	 /**
	   * This method continually calls the update method utill the
	   * for loop completes
	   */
	/* public void callReverseDWR() {
	      System.out.println(" Ur in callReverseDWR ");
	      try {
	      for (int i = 0; i < 10; i++) {
	         update();
	         try {
	            Thread.sleep(1000);
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	         }
	      }
	      } catch (Exception e) {
	         System.out.println("Error in callReverseDWR");
	         e.printStackTrace();
	      }
	   }*/
	 
	 /**
	   * This method updates ReversePage.jsp <ul id="updates">
	   * using dwr reverse ajax
	   */
	 /* public void update() {
		  WebContext webContext = WebContextFactory.get();
			String currentPage=webContext.getCurrentPage();
			ServletContext servletContext = webContext.getServletContext();
			ServerContext serverContext = ServerContextFactory.get(servletContext);
	      try {
	         List<Data> messages = new ArrayList<Data>();
	         messages.add(new Data("testing" + count++));
	         Collection sessions =  serverContext.getScriptSessionsByPage(currentPage);
	         Util utilAll = new Util(sessions);
	         utilAll.addOptions("updates", messages, "value");
	      } catch (Exception e) {
	         System.out.println("Error in Update");
	         e.printStackTrace();
	      }
	   }*/
}
