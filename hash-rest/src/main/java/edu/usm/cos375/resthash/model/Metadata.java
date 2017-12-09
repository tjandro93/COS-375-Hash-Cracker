package edu.usm.cos375.resthash.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Metadata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="META_ID")
	private long id;
	
	private int timesRequested;
	
	private long instantFound;
	
	private long secondsToFind;
	
	private boolean hasBeenFound = false;

	public Metadata() {
		this.timesRequested = 0;
	}

	public int getTimesRequested() {
		return timesRequested;
	}
	public void incrementTimesRequested() {
		this.timesRequested++;
	}
	public long getInstantFound() {
		return instantFound;
	}
	public void updateInstantFound() {
		if(!hasBeenFound) {
			this.instantFound = Instant.now().getEpochSecond();
			hasBeenFound = true;
		}
	}

	public long getSecondsToFind() {
		return secondsToFind;
	}

	public void setSecondsToFind(long timeToFind) {
		this.secondsToFind = timeToFind;
	}
}
