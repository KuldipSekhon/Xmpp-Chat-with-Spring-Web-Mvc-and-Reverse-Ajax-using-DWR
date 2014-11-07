package com.edms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edms.activiti.Runner;
import com.edms.dwr.ScriptSessList;
import com.edms.dwr.XmppChatClass;
import com.edms.model.LoginModel;


@Controller
public class IndexController {
	
	@Autowired private ScriptSessList scriptSessList;
	@Autowired private Runner runner;
	
	@Value ("${xmppDomain}") private String xmppDomain;
	@Value ("${packetReplyTimeout}") private int packetReplyTimeout; // millis
	@Value ("${chatImageFolder}") private String chatImageFolder;
	@Value ("${onlineStatus}") private String onlineStatus;
	@Value ("${awayStatus}") private String awayStatus;
	@Value ("${dndStatus}") private String dndStatus;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String getIndex(ModelMap map){
		System.out.println("in index..........");
		map.addAttribute("loginUser", new LoginModel());
		runner.start();
		runner.fetchGroupTasks();
		runner.continueTask();
		runner.fetchUserTask();
		runner.resendVacationRequest();
		runner.fetchGroupTasks();
		runner.continueTask1();
		runner.retrieveHistory();
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String performLogin(@ModelAttribute(value="loginUser")LoginModel loginUser, ModelMap map, HttpServletRequest request){
		System.out.println("in login ..........");
		System.out.println("userid="+loginUser.getUserid());
		System.out.println("password="+loginUser.getPassword());
		XmppChatClass xmppChatClass=new XmppChatClass();
		//TODO: SEPERATE THE CONFIGURATION
		xmppChatClass.createConnection(xmppDomain, packetReplyTimeout, request);
		xmppChatClass.registerListeners(chatImageFolder);
		xmppChatClass.performLogin(loginUser.getUserid(), loginUser.getPassword(), onlineStatus);
		scriptSessList.listenScriptSession();
		request.getSession().setAttribute("xmppChatClass", xmppChatClass);
		return "redirect:/userChat";
	}
	
	@RequestMapping(value="/userChat", method=RequestMethod.GET)
	public String getChat(ModelMap map){
		System.out.println("in userChat..........");
		map.addAttribute("imageurl", chatImageFolder);
		return "userChat";
	}
	
	@RequestMapping(value = "/sendChatMessage", method = RequestMethod.GET)
    public @ResponseBody String sendChat(@RequestParam Map<String,String> requestParams, HttpServletRequest request)
    {   
		String message=requestParams.get("message");
		String buddyJID=requestParams.get("buddyJID");
		XmppChatClass xmppChatClass=(XmppChatClass)request.getSession().getAttribute("xmppChatClass");
		xmppChatClass.sendChatMessages(message, buddyJID); 
		return "success";
    }
	
	@RequestMapping(value = "/inviteBuddy", method = RequestMethod.GET)
    public @ResponseBody String sendInvite(@RequestParam(value="buddyJID")String buddyJID, HttpServletRequest request){  
		XmppChatClass xmppChatClass=(XmppChatClass)request.getSession().getAttribute("xmppChatClass");
		xmppChatClass.sendInvite(buddyJID);
		return "Invited Successfully";
    }
	
	@RequestMapping(value = "/respondFrndReq", method = RequestMethod.GET)
    public @ResponseBody String respondToReq(@RequestParam(value="fromJID")String fromJID, HttpServletRequest request){ 
		XmppChatClass xmppChatClass=(XmppChatClass)request.getSession().getAttribute("xmppChatClass");
		xmppChatClass.acceptFrndReq(fromJID);
		return "Friend Request Accepted Successfully!";
    }
	
	@RequestMapping(value = "/denyFrndReq", method = RequestMethod.GET)
    public @ResponseBody String denyToReq(@RequestParam(value="fromJID")String fromJID, HttpServletRequest request){ 
		XmppChatClass xmppChatClass=(XmppChatClass)request.getSession().getAttribute("xmppChatClass");
		xmppChatClass.denyFrndReq(fromJID);
		return "Friend Request Refused!";
    }
	
	@RequestMapping(value = "/logoutChat", method = RequestMethod.GET)
    public @ResponseBody String closeChat(HttpServletRequest request){  
		System.out.println("in logout chat");
		XmppChatClass xmppChatClass=(XmppChatClass)request.getSession().getAttribute("xmppChatClass");
		xmppChatClass.closeConnection();
		return "successful log out";
    }
	
	@RequestMapping(value = "/changedPresence", method = RequestMethod.GET)
    public @ResponseBody void changePresenceInfo(@RequestParam(value="presmode")String presmode, HttpServletRequest request){  
		XmppChatClass xmppChatClass=(XmppChatClass)request.getSession().getAttribute("xmppChatClass");
		xmppChatClass.sendChangePresence(presmode, onlineStatus, awayStatus, dndStatus);
    }

}
