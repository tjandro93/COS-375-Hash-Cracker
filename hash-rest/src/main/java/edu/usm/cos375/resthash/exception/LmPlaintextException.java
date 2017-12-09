package edu.usm.cos375.resthash.exception;

public class LmPlaintextException extends Exception {
	/**
	 * 
	 */
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
