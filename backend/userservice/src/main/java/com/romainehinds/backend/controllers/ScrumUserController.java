package com.romainehinds.backend.controllers;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.romainehinds.backend.models.ScrumUser;

@RestController
@RequestMapping("/scrumuser")
public class ScrumUserController {

	private static final Logger log = Logger.getLogger(ScrumUserController.class);
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ScrumUser> saveUser(Map<String, Object> usrMap){
		log.info(usrMap);
		
		return new ResponseEntity<ScrumUser>(new ScrumUser(), HttpStatus.CREATED);
	}
}
