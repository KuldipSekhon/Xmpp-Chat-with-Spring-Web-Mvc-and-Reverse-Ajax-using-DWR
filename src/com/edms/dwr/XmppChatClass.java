package com.edms.dwr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class XmppChatClass {

	private static ServletContext servletContext;
	private XMPPBOSHConnection xmppConnection;
	private static MessageListener messageListener;
	public static Chat chat;

	public void createConnection(Boolean https, String host, int port,
			String filePath, String xmppDomain, int packetReplyTimeout,
			HttpServletRequest request) {
		System.out.println("initializing connection to server" + host);
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

		BOSHConfiguration config = new BOSHConfiguration(https, host, port,
				filePath, xmppDomain);
		// Create a configuration for the connection
		config.setSecurityMode(SecurityMode.required);
		config.setRosterLoadedAtLogin(true);
		config.setCompressionEnabled(true);
		System.out.println("service name=" + config.getServiceName());
		System.out.println("host=" + config.getHostAddresses());
		System.out.println("security mode=" + config.getSecurityMode());
		try {
			System.out.println("uri=" + config.getURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		// Create a new XMPP connection object
		xmppConnection = new XMPPBOSHConnection(config);
		try {
			// Connect to the server
			xmppConnection.connect();
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("xmppConnection", xmppConnection);
			System.out.println("Connected to server............"
					+ xmppConnection.isConnected());
			System.out.println("connection id="
					+ xmppConnection.getConnectionID());

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
	
	public void getRoster() {
		System.out.println("in function with connection id="+ xmppConnection.getConnectionID());
		xmppConnection.getRoster().setSubscriptionMode(Roster.SubscriptionMode.manual);

		xmppConnection.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(Packet paramPacket)throws NotConnectedException {

				System.out.println("2 coonection id is %%%%%%%%%%% "+ xmppConnection.getConnectionID());

				if (paramPacket instanceof Presence) {
					Presence presence = (Presence) paramPacket;
					final RosterEntry newEntry = xmppConnection.getRoster().getEntry(presence.getFrom());

					System.out.println(presence.getFrom() + " new entry in roster ?? "+ newEntry + " type= " + presence.getType());

					if (presence.getType().equals(Presence.Type.subscribe)) {
						if (newEntry == null) {
							System.out.println("in if.........");
							try {
								xmppConnection.getRoster().createEntry(presence.getFrom(), null, null);
							} catch (NotLoggedInException e) {
								e.printStackTrace();
							} catch (NoResponseException e) {
								e.printStackTrace();
							} catch (XMPPErrorException e) {
								e.printStackTrace();
							}
							System.out.println("@@@@@@@@@@@@@@@ Friend request roster entry created for "+presence.getFrom());
							//(new ReverseClass()).frndRequest(presence.getFrom(), presence.getTo());
						} else {
							System.out.println("in else........");
							Presence againpresence = new Presence(Presence.Type.subscribed);
							againpresence.setMode(Presence.Mode.available);
							againpresence.setTo(presence.getFrom());
							xmppConnection.sendPacket(againpresence);
							System.out.println("subscribed sent to %%%%%%%%%%%%% "+ presence.getFrom());
						}
					}
				}
			}
		}, new PacketFilter() {

			@Override
			public boolean accept(Packet packet) {
				System.out.println("1 coonection id is %%%%%%%%%%% "
						+ xmppConnection.getConnectionID());
				if (packet instanceof Presence) {
					Presence presence = (Presence) packet;
					if (presence.getType().equals(Presence.Type.subscribed)
							|| presence.getType().equals(
									Presence.Type.subscribe)) {
						System.out.println("return true %%%%%%%%%%%%%% "
								+ presence.getType() + " from "
								+ presence.getFrom());
						return true;
					}
				}
				return false;
			}
		});

		Roster roster = xmppConnection.getRoster();
		roster.addRosterListener(new RosterListener() {
			@Override
			public void presenceChanged(Presence presence) {
				System.out.println("Presence changed: " + presence.getFrom()
						+ " " + presence);
				ServerContext serverContext = ServerContextFactory
						.get(servletContext);
				(new ReverseClass()).updatePresence(serverContext, presence);
			}

			@Override
			public void entriesUpdated(Collection<String> coll) {
				System.out.println("Entries Updated %%%%%%%%%%%%%%%%%%% "
						+ coll);
				(new ReverseClass()).createRoster(xmppConnection,
						xmppConnection.getUser().split("/")[0]);
			}

			@Override
			public void entriesDeleted(Collection<String> arg0) {
				System.out.println("Entries Deleted %%%%%%%%%%%%%%%%%%%");
			}

			@Override
			public void entriesAdded(Collection<String> coll) {
				System.out.println("Entries Added %%%%%%%%%%%%%%% " + coll);
				(new ReverseClass()).createRoster(xmppConnection,
						xmppConnection.getUser().split("/")[0]);
			}

		});

		ChatManager.getInstanceFor(xmppConnection).addChatListener(
				new ChatManagerListener() {

					@Override
					public void chatCreated(Chat chat, boolean createdLocally) {
						System.out.println("created locally ******** "
								+ createdLocally);
						// if(!createdLocally){
						chat.addMessageListener(new MessageListener() {

							@Override
							public void processMessage(Chat chat,
									Message message) {
								System.out.println("Listener Received new msg="
										+ message.getBody() + " from "
										+ message.getFrom() + " to "
										+ message.getTo() + " participant "
										+ chat.getParticipant());
								System.out.println("Listener message connection user="
												+ xmppConnection.getUser());
								(new ReverseClass()).listeningForMessages(message);
							}
						});
						// }
					}
				});

	}

	public void performLogin(String username, String password) {
		try {
			// login to the connected server
			xmppConnection.login(username, password);

			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus("i am online");
			xmppConnection.sendPacket(presence);

			AccountManager am = AccountManager.getInstance(xmppConnection);
			am.getAccountAttributes();

			am.getAccountAttribute("FN"); // CN for name and jpegPhoto
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

	public void sendChatMessages(String message, String buddyJID) {
		System.out.println(xmppConnection.getConnectionID()
				+ " : Sending Message " + message + "from "
				+ xmppConnection.getUser() + " to buddy " + buddyJID);
		chat = ChatManager.getInstanceFor(xmppConnection).createChat(buddyJID,
				messageListener);
		try {
			chat.sendMessage(message);
			System.out.println("Message sent successfully");
		} catch (NotConnectedException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public void sendInvite(String address) {
		Presence invite = new Presence(Presence.Type.subscribe);
		invite.setTo(address);
		try {
			xmppConnection.sendPacket(invite);
			System.out.println("INVITE SENT SUCCESSFULLY to " + address+ "............");
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public void acceptFrndReq(String from) {
		System.out.println("in accept friend request");
		Presence newpresence = new Presence(Presence.Type.subscribed);
		// newpresence.setMode(Presence.Mode.available);
		newpresence.setTo(from);
		try {
			xmppConnection.sendPacket(newpresence);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		System.out.println("subscribed sent to %%%%%%%%%%%%% " + from);
		
		Presence subscription = new Presence(Presence.Type.subscribe);
		subscription.setTo(from);
		try {
			xmppConnection.sendPacket(subscription);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		System.out.println("subscribe sent to %%%%%%%%%% " + from);
	}
}
