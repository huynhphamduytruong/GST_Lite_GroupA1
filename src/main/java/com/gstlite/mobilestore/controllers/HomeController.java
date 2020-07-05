package com.gstlite.mobilestore.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
	@GetMapping("/")
	String hello() {
		return "It's work!";
	}
}
