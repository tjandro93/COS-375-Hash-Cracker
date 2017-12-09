package edu.usm.cos375.resthash.exception;

/*
 * An Exception to represent an error based on the constraints of a valid plaintext to be hashed by the LM Algorithm
 */

public class LmPlaintextException extends Exception {

	private static final long serialVersionUID = 2640389570262328197L;
	private String message;
	
	public LmPlaintextException(){
		this.message = "The provided plaintext is not valid for LM Hash generation";
	}
	
	public LmPlaintextException(String plaintext) {
		this.message = "\"" + plaintext + "\" is not valid for LM Hash generation";
	}
	
	public LmPlaintextException(String plaintext, String reason) {
		this.message = "\"" + plaintext + "\" is not valid for LM Hash generation: " + reason;
	}
	
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
}
