package edu.usm.cos375.resthash.model;

import java.time.Instant;

public class Metadata {
	private int timesRequested;
	private long instantFound;
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
		}
	}
}
