package com.ircs.portal.home.controller;

import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
/**import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler; **/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ircs.portal.setup.dto.UserDetail;
//import tz.go.mof.archive.dto.users.UserDetailData;

@Controller
public class HomeController {

	@GetMapping(value = {"","/","/home"})
	public String home(Model model, @RequestParam(value = "name", required = false,defaultValue = "Guest") String name,
			@ModelAttribute("userDetails") UserDetail userDetail) {
		System.out.println("userDetail****************"+userDetail);
		
		model.addAttribute("name",name);
		/*
		 * if(userDetailData.getRoleId().equals(role_custodian) ||
		 * userDetailData.getRoleId().equals(role_manager)) { return
		 * "redirect:documents/dashboard"; } else
		 * if(userDetailData.getRoleId().equals(role_admin)){ return
		 * "redirect:users/create"; }
		 */
		return "home/si-dashboard";
	}
	
	
	@GetMapping(value = "login")
	public String login(Model model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		
		if (error != null) {
            model.addAttribute("message", "Your username and password is invalid.");
            model.addAttribute("alertType", "danger");
		}

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            model.addAttribute("alertType", "info");
        }

		return "login/login";
	}
	/**
	@RequestMapping(value="/logout", method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
		
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
	
	@GetMapping(value="/denied")
    public String deniedPage (Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
		model.addAttribute("message", "We are sorry that the access is denied.");
		model.addAttribute("alertType", "warning");
		return "login";
    }
    **/
}
