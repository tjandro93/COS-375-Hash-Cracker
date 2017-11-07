package edu.usm.cos375.resthash.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.usm.cos375.resthash.datasource.StaticHashSource;
import edu.usm.cos375.resthash.model.Secret;

@RestController
public class HashController {
	
	@Autowired
	private StaticHashSource hashSource;
	
	/*
	 * Return all Secrets
	 */
	@RequestMapping(value= {"", "secrets"}, method=RequestMethod.GET)
	public ResponseEntity<Set<Secret>> getAllSecret() {
		Set<Secret> secrets = hashSource.getAll();
		
		if(secrets == null || secrets.isEmpty()) {
			return new ResponseEntity<Set<Secret>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Set<Secret>>(secrets, HttpStatus.OK);
	}
	
	/*
	 * Return a specific secret
	 */
	@RequestMapping(value="secrets/{ptext}", method=RequestMethod.GET)
	public ResponseEntity<Secret> getSecret(@PathVariable("ptext") String ptext) {
		Secret s = hashSource.getByPlainText(ptext);
		if( s == null) {
			return new ResponseEntity<Secret>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Secret>(s, HttpStatus.OK);
	}
	
	/*
	 * Return a secret based on the requested 
	 */
}
