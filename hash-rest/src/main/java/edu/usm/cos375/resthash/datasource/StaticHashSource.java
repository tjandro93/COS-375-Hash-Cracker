package edu.usm.cos375.resthash.datasource;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.usm.cos375.resthash.model.Secret;

@Service
public class StaticHashSource {
	private static Set<Secret> secrets = new HashSet<Secret>();
	static {
		Secret passwordSec = new Secret("secret");
		Secret passwordPass = new Secret("password");
		Secret passwordTest = new Secret("test");
		passwordSec.addLMHash();
		passwordPass.addLMHash();
		passwordTest.addLMHash();
		secrets.add(passwordSec);
		secrets.add(passwordPass);
		secrets.add(passwordTest);
	}
	
	public Set<Secret> getAll(){
		for(Secret s: secrets) {
			s.incrementAllTimesRequested();
		}
		return this.secrets;
	}
	
	public Secret getByPlainText(String plainText) {
		for(Secret s : secrets) {
			if(s.getPlaintext().equals(plainText)) {
				s.incrementAllTimesRequested();
				return s;
			}
		}
		return null;
	}
}
