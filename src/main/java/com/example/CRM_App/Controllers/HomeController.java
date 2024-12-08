package com.example.CRM_App.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
@GetMapping("/")
public String home() {
	return "index";
}

}
