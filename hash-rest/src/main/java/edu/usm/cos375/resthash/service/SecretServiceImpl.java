package edu.usm.cos375.resthash.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.usm.cos375.resthash.datasource.StaticHashSource;
import edu.usm.cos375.resthash.model.Hash;
import edu.usm.cos375.resthash.model.Secret;

@Service
public class SecretServiceImpl implements SecretService {

	@Autowired
	private StaticHashSource data;

	@Override
	public List<Secret> getAll() {
		return data.getSecrets();
	}

	@Override
	public List<Secret> getByHash(String htext) {
		List<Secret> foundMatches = new ArrayList<Secret>();
		for(Secret s : data.getSecrets()) {
			for(Hash h : s.getKnownHashes()) {
				if(h.getHashedPlaintext().equalsIgnoreCase(htext)) {
					foundMatches.add(s);
				}
			}
		}
		return foundMatches;
	}

	@Override
	public Secret getByPlaintext(String ptext) {
		for(Secret s : data.getSecrets()) {
			if(s.getPlaintext().equalsIgnoreCase(ptext)) {
				return s;
			}
		}
		return null;
	}

	@Override
	public void delete(String ptext) {
		Secret secret = getByPlaintext(ptext);
		data.getSecrets().remove(secret);
	}

	@Override
	public void create(String ptext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String ptext) {
		// TODO Auto-generated method stub
		return false;
	}

}
