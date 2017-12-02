package edu.usm.cos375.resthash.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/*
 * Represents a single plaintext password which may have many known hashes from different algorithms associated with it.
 */
@Entity
public class Secret {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="SEC_ID")
	private Long id;
	
	private String plaintext;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn()
	private LmHash lmHash;
	
	public Secret() {}

	public Secret(LmHash lmHash) {
		this.lmHash = lmHash;
	}
	
	public void incrementTimesRequested() {
		lmHash.getMetadata().incrementTimesRequested();	
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaintext() {
		return plaintext;
	}

	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}

	public LmHash getLmHash() {
		return lmHash;
	}

	public void setLmHash(LmHash lmHash) {
		this.lmHash = lmHash;
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
