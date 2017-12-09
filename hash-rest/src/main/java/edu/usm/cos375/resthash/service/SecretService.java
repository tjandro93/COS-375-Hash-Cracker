package edu.usm.cos375.resthash.service;

import java.util.List;

import edu.usm.cos375.resthash.exception.HashCrackException;
import edu.usm.cos375.resthash.exception.LmPlaintextException;
import edu.usm.cos375.resthash.model.Secret;

public interface SecretService {
	
	public List<Secret> getAll();
	
	public Secret getByHash(String htext) throws HashCrackException;
	
	public Secret getByPlaintext(String ptext) throws LmPlaintextException;

	public void delete(String ptext);

	public Secret create(String ptext) throws LmPlaintextException;

	public boolean exists(String ptext);
}
