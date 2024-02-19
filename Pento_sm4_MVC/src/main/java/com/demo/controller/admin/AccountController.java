package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Role;
import com.demo.service.AccountService;
import com.demo.service.RoleService;

@Controller
@RequestMapping({ "account", "account/"})
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAll());
		return "account/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		return "account/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			account.setStatus("0");
			account.setRole(new Role(3,"ROLE_MEMBER"));
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(accountService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/account/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", accountService.find(id));	
		return "account/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			account.setStatus("0");
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}
	
	// Update Password
	@GetMapping({"updatePassword"})
	public String updatePassword(@PathVariable("email") String email, RedirectAttributes redirectAttributes, ModelMap modelMap) {
		Account account = accountService.findByEmail(email);
		if(account == null) {
			redirectAttributes.addFlashAttribute("msg", "Username Not Found");
		} else {
			return "account/updatePassword";			
		}
		return "redirect:/account/index";
	}
	
	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			account.setStatus("0");
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}
	
	
	
	@GetMapping({"changePass/{id}"})
	public String changePassword(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", accountService.find(id));	
		return "account/changePassword";
	}
	
	@PostMapping({ "changePass" })
	public String changePassword(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Password changed");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Change Failed");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}

}
