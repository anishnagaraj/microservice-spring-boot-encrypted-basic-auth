package com.anishnagaraj.poc.microservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postmessage")
public class MessageBrokerController {
	
	@RequestMapping(value = "/pushnotification/{param}", method = RequestMethod.GET)
	public String print(@PathVariable(value = "param") final String param) {
		return "You said: " + param;
	}
	
	@RequestMapping(value = "/toKafka", method = RequestMethod.POST)
	public String postMessage(@RequestBody Object message, final String param) {
		return "You message is: " + message;
	}
	
	
}
