package edu.usm.cos375.resthash.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import edu.usm.cos375.resthash.service.LMGenerator;

@Entity
public class LmHash {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "HASH_ID")
	private Long id;
	
	@Column
	private String hashValue;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="META_ID")
	private Metadata metadata;
	
	public LmHash() {
		this.metadata = new Metadata();
		this.hashValue = "";
	}

	public String getHashType() {
		return "LM Hash";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHashedPlaintext() {
		return hashValue;
	}

	public void setHashedPlaintext(String hashedPlaintext) {
		this.hashValue = hashedPlaintext;
	}

	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

}
