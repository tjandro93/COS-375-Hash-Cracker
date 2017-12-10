package edu.usm.cos375.resthash.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * Entity to represent some metadata for a specific Hash
 */

@Entity
public class Metadata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="META_ID")
	private long id;
	

	private String instantFound;
	
	private int timesRequested;
	
	
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
	public String getInstantFound() {
		return instantFound;
	}
	public void updateInstantFound() {
		if(!hasBeenFound) {
			this.instantFound = formatDate();
			hasBeenFound = true;
		}
	}

	public long getSecondsToFind() {
		return secondsToFind;
	}

	public void setSecondsToFind(long timeToFind) {
		this.secondsToFind = timeToFind;
	}
	
	private String formatDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy - HH:mm:ss z");
		return df.format(new Date());
	}
	
}
