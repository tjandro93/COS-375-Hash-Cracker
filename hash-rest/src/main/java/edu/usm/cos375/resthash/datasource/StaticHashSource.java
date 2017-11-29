package edu.usm.cos375.resthash.datasource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.usm.cos375.resthash.model.Hash;
import edu.usm.cos375.resthash.model.Secret;

@Service
public class StaticHashSource {
	private static List<Secret> secrets = new ArrayList<Secret>();
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
	
	public List<Secret> getSecrets(){
		return StaticHashSource.secrets;
	}
}
