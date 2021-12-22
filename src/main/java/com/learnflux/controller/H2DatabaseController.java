package com.learnflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/h2database")
public class H2DatabaseController {

	public String welcome() {
		return "hello h2database example";
	}
	
}
