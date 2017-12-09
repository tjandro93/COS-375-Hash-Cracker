package edu.usm.cos375.resthash.exception;

public class HashCrackException extends Exception {
	private String message;
	
	public HashCrackException(){
		this.message = "An exception occured trying to crack a hash";
	}
	
	public HashCrackException(String hashtext) {
		this.message = "An exception occured trying to crack the hash "+ hashtext;
	}
	
	public HashCrackException(String hashtext, String reason) {
		this.message = "An exception occured trying to crack the hash "+ hashtext + ": " + reason;
	}
	
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
