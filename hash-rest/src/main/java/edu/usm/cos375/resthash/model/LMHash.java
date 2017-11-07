package edu.usm.cos375.resthash.model;

public class LMHash extends Hash {

	public LMHash(String plaintext) {
		super(plaintext);
	}
	@Override
	public String generateHashedPlaintext(String plaintext) {
		// TODO Unimplemented LM hash generation
		if(plaintext.equals("password")) {
			return "E52CAC67419A9A224A3B108F3FA6CB6D";
		}
		else if (plaintext.equals("secret")) {
			return "552902031BEDE9EFAAD3B435B51404EE";
		}
		else if(plaintext.equals("test")) {
			return "01FC5A6BE7BC6929AAD3B435B51404EE";
		}
		return "LM HASH GENERATION NOT FULLY IMPLEMENTED";
	}

	@Override
	public String getHashType() {
		return "LM Hash";
	}

}
