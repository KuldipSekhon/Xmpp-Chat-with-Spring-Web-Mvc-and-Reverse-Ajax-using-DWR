package com.edms.dwr;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class MyMessageListener{
	
	private static ChatManager chatManager;
	
	WebContext webContext = WebContextFactory.get();
	String currentPage=webContext.getCurrentPage();
	ServletContext servletContext = webContext.getServletContext();
	ServerContext serverContext = ServerContextFactory.get(servletContext);
	
	HttpSession session = WebContextFactory.get().getSession();
	XMPPBOSHConnection xmppConnection=(XMPPBOSHConnection)session.getAttribute("xmppConnection");
	
	public void listeningForMessages(){
		chatManager.getInstanceFor(xmppConnection).addChatListener(new ChatManagerListener() {
			
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				chat.addMessageListener(new MessageListener() {
					
					@Override
					public void processMessage(Chat chats, Message messages) {
						String from=messages.getFrom();
						String newmsg=messages.getBody();
						System.out.println("Received new msg="+newmsg+" from "+from);
						Collection sessions=serverContext.getScriptSessionsByPage(currentPage);
						Util utilAll=new Util(sessions);
						utilAll.setValue("updates", newmsg+from);
					}
				});
			}
		});
		
	}		

}
