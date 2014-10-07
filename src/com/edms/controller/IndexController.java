package com.edms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edms.dwr.ScriptSessList;
import com.edms.dwr.XmppChatClass;
import com.edms.model.LoginModel;


@Controller
public class IndexController {
	
	@Autowired private ScriptSessList scriptSessList;
	
	@Value ("${https}") private Boolean https;
	@Value ("${host}") private String host;
	@Value ("${port}") private int port;
	@Value ("${filePath}") private String filePath;
	@Value ("${xmppDomain}") private String xmppDomain;
	@Value ("${packetReplyTimeout}") private int packetReplyTimeout; // millis
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String getIndex(ModelMap map){
		System.out.println("in login..........");
		map.addAttribute("loginUser", new LoginModel());
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String performLogin(@ModelAttribute(value="loginUser")LoginModel loginUser, ModelMap map, HttpServletRequest request){
		System.out.println("in login..........");
		System.out.println("userid="+loginUser.getUserid());
		System.out.println("password="+loginUser.getPassword());
		XmppChatClass xmppChatClass=new XmppChatClass();
		xmppChatClass.createConnection(https, host, port, filePath, xmppDomain, packetReplyTimeout, request);
		xmppChatClass.getRoster();
		xmppChatClass.performLogin(loginUser.getUserid(), loginUser.getPassword());
		scriptSessList.listenScriptSession();
		request.getSession().setAttribute("xmppChatClass", xmppChatClass);
		map.addAttribute("loggedUser", loginUser.getUserid());
		return "redirect:/userChat";
	}
	
	@RequestMapping(value="/userChat", method=RequestMethod.GET)
	public String getChat(ModelMap map){
		System.out.println("in userChat..........");
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
		return "Friend Request Accepted Successfully";
    }

}
