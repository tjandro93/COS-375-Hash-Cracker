package edu.usm.cos375.resthash.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.usm.cos375.resthash.model.Secret;
import edu.usm.cos375.resthash.service.SecretService;

@RestController
public class HashController {
	
	@Autowired
	private SecretService secretService;
	
	/*
	 * Return all Secrets
	 */
	@RequestMapping(value= {"", "secrets"}, method=RequestMethod.GET)
	public ResponseEntity<List<Secret>> getAllSecret() {
		List<Secret> secrets = secretService.getAll();
		
		if(secrets == null || secrets.isEmpty()) {
			return new ResponseEntity<List<Secret>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Secret>>(secrets, HttpStatus.OK);
	}
	
	/*
	 * Return a specific secret based on the supplied plaintext
	 */
	@RequestMapping(value="secrets/{ptext}", method=RequestMethod.GET)
	public ResponseEntity<Secret> getSecretByPlaintext(@PathVariable("ptext") String ptext) {
		Secret s = secretService.getByPlaintext(ptext);
		if( s == null) {
			return new ResponseEntity<Secret>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Secret>(s, HttpStatus.OK);
	}
	
	/*
	 * Return a secret based on the supplied hash
	 */
	@RequestMapping(value="hashes/{htext}", method=RequestMethod.GET)
	public ResponseEntity<List<Secret>> getSecretByHash(@PathVariable("htext") String htext) {
		List<Secret> secrets = secretService.getByHash(htext);
		if(!secrets.isEmpty()){
			return new ResponseEntity<List<Secret>>(secrets, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<List<Secret>>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Delete a secret based on supplied plaintext
	 */
	@RequestMapping(value="secrets/{ptext}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteByPlaintext(@PathVariable("ptext") String ptext){
		Secret secret = secretService.getByPlaintext(ptext);
		if(secret == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		else {
			secretService.delete(ptext);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	/*
	 * Create a new Secret based on the supplied plaintext
	 */
	@RequestMapping(value="secrets/{ptext}", method=RequestMethod.POST)
	public ResponseEntity<Void> create(@PathVariable("ptext") String ptext, UriComponentsBuilder ucBuilder){
		if(secretService.exists(ptext)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		else {
			secretService.create(ptext);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("").buildAndExpand(ptext).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
	}
}
