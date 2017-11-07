package edu.usm.cos375.resthash.model;

import java.util.HashSet;
import java.util.Set;

/*
 * Represents a single plaintext password which may have many known hashes from different algorithms associated with it.
 */
public class Secret {
	private String plaintext;
	private Set<Hash> knownHashes;
	
	public Secret() {}
	
	public Secret(String plaintext) {
		this.knownHashes = new HashSet<Hash>();
		this.plaintext = plaintext;
	}
	
	public String getPlaintext() {
		return plaintext;
	}
	
	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}
	
	public void addLMHash() {
		this.knownHashes.add(new LMHash(this.plaintext));
	}
	
	public Set<Hash> getKnownHashes() {
		return knownHashes;
	}
	
	public void setKnownHashes(Set<Hash> knownHashes) {
		this.knownHashes = knownHashes;
	}
	
	public void incrementAllTimesRequested() {
		for(Hash h : knownHashes) {
			h.getMetadata().incrementTimesRequested();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		Secret s = (Secret) o;
		return this.plaintext.equals(s.plaintext);
	}
}
