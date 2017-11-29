package edu.usm.cos375.resthash.service;

import java.util.List;

import edu.usm.cos375.resthash.model.Secret;

public interface SecretService {
	
	public List<Secret> getAll();
	
	public List<Secret> getByHash(String htext);
	
	public Secret getByPlaintext(String ptext);

	public void delete(String ptext);

	public void create(String ptext);

	public boolean exists(String ptext);
}
