package edu.usm.cos375.resthash.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.usm.cos375.resthash.exception.HashCrackException;
import edu.usm.cos375.resthash.exception.LmPlaintextException;
import edu.usm.cos375.resthash.exception.RestRequestException;
import edu.usm.cos375.resthash.model.Secret;
import edu.usm.cos375.resthash.service.SecretService;


/*
 * The RestController for the application.
 * Handles REST requests as described below
 * 
 * GET /secrets
 * 		Returns all secrets in the database
 * GET /secrets/{plaintext}
 * 		Returns the secret with given plaintext
 * GET /hashes/{hash}
 * 		Returns the secret with the given hash
 * DELETE /secrets/{plaintext}
 * 		Deletes the secret with given plaintext from the database
 * POST /secrets/{plaintext}
 * 		Adds the secret with given plaintext to the database
 */

@CrossOrigin
@RestController
public class HashController {

	@Autowired
	private SecretService secretService;

	/*
	 * Return all Secrets
	 */
	@RequestMapping(value= {"", "secrets"}, method=RequestMethod.GET)
	public ResponseEntity<List<Secret>> getAllSecret() throws RestRequestException {
		List<Secret> secrets = secretService.getAll();

		if(secrets == null || secrets.isEmpty()) {
			throw new RestRequestException("No secrets exist in database", HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Secret>>(secrets, HttpStatus.OK);
	}

	/*
	 * Return a specific secret based on the supplied plaintext
	 */
	@RequestMapping(value="secrets/{ptext}", method=RequestMethod.GET)
	public ResponseEntity<Secret> getSecretByPlaintext(@PathVariable("ptext") String ptext) 
			throws RestRequestException, LmPlaintextException {
		Secret s = secretService.getByPlaintext(ptext);
		if( s == null) {
			throw new RestRequestException("The secret " + ptext + " could not be found in the database", 
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Secret>(s, HttpStatus.OK);
	}

	/*
	 * Return a secret based on the supplied hash
	 */
	@RequestMapping(value="hashes/{htext}", method=RequestMethod.GET)
	public ResponseEntity<Secret> getSecretByHash(@PathVariable("htext") String htext) throws RestRequestException, HashCrackException {
		Secret secret = secretService.getByHash(htext);
		if(secret != null){
			return new ResponseEntity<Secret>(secret, HttpStatus.OK);
		}
		else{
			throw new RestRequestException("No secret with the hash " + htext + " could be found in the database", 
					HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Delete a secret based on supplied plaintext
	 */
	@RequestMapping(value="secrets/{ptext}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteByPlaintext(@PathVariable("ptext") String ptext) throws RestRequestException, LmPlaintextException{
		Secret secret = secretService.getByPlaintext(ptext);
		if(secret == null) {
			throw new RestRequestException("The secret " + ptext + " could not be found in the database", 
					HttpStatus.NOT_FOUND);
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
	public ResponseEntity<Secret> create(@PathVariable("ptext") String ptext, UriComponentsBuilder ucBuilder) 
			throws LmPlaintextException, RestRequestException{
		if(secretService.exists(ptext)) {
			throw new RestRequestException("The secret " + ptext + " is already present in the database ", HttpStatus.CONFLICT);
		}
		else {
			Secret secret = secretService.create(ptext);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("").buildAndExpand(ptext).toUri());
			return new ResponseEntity<Secret>(secret, headers, HttpStatus.CREATED);
		}
	}
}
