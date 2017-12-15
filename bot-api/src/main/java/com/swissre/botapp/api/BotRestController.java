package com.swissre.botapp.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swissre.botapp.app.BotController;
import com.swissre.botapp.dto.activity.Activity;

@RestController
public class BotRestController {

	BotController controller;
	
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public void message(@RequestBody Activity activity) {
		
	}
}
