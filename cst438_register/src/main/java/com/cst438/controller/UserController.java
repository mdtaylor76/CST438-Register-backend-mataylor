package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.Administrator;
import com.cst438.domain.AdministratorRepository;

@RestController
public class UserController {

	@Autowired
	AdministratorRepository adminRepository;
	
	@GetMapping("/userType")
	public Boolean user (@AuthenticationPrincipal OAuth2User principal){

		String student_email = principal.getAttribute("email");
		System.out.println("/userType - Get User Type - " + student_email );
		
		Administrator admin = adminRepository.findByEmail(student_email);
		if (admin != null) {
			System.out.println("User is an Administrator");
			return true;
		} else {
			System.out.println("User is NOT an Administrator");
			return false;
		}
	}
	
}
