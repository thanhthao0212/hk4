package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Role;
import com.demo.helpers.SecurityCodeHelper;
import com.demo.service.AccountService;
import com.demo.service.MailService;
import com.demo.service.RoleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "account", "account/"})
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailService mailService;

	@Autowired
	private Environment environment;
	
	
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
	
	// Change PassWord
	// Update Password
		@GetMapping({"updatePassword/{email}"})
		public String updatePassword(@PathVariable("email") String email, 
				RedirectAttributes redirectAttributes, ModelMap modelMap) {
			Account account = accountService.findByEmail(email);
			if (account == null) {
				redirectAttributes.addFlashAttribute("msg", "Email not found");
			} else {
				modelMap.put("account", account);
				return "account/updatePassword";
			}
			return "redirect:/account/login";
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
		
	
	//
	@RequestMapping(value = "verify", method = RequestMethod.GET)
	public String verify(@RequestParam("email") String email, @RequestParam("code") String code,
			RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "email not found");
		} else {
			if (code.equals(account.getSecurityCode())) {
				account.setStatus(code);
				if (accountService.save(account)) {
					redirectAttributes.addFlashAttribute("msg", "Actived");
				} else {
					redirectAttributes.addFlashAttribute("msg", "Failed");
				}
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed");
			}
		}
		return "redirect:/account/login";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return "redirect:/account/login";
	}

	@RequestMapping(value = "forgetpassword", method = RequestMethod.GET)
	public String forgetpassword() {
		return "account/forgetpassword";
	}

	@RequestMapping(value = "forgetpassword", method = RequestMethod.POST)
	public String forgetpassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
			return "redirect:/account/forgetpassword";
		} else {
			String securityCode = SecurityCodeHelper.generate();
			account.setSecurityCode(securityCode);
			if (accountService.save(account)) {
				String content = "Nhan vao <a href='http://localhost:8085/account/updatepassword?code=" + securityCode
						+ "&email=" + account.getEmail() + "'>day</a> de cap nhat password";
				mailService.send(environment.getProperty("spring.mail.email"), account.getEmail(), "Update Password",
						content);
			}
			return "redirect:/account/login";
		}
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
