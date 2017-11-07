package edu.usm.cos375.resthash.model;

/*
 * Algorithm independent representation of a hashed password
 * Has metadata as well as 
 */
public abstract class Hash {
	private String hashedPlaintext;
	private Metadata metadata;
	
	public Hash() {
		this.metadata = new Metadata();
		this.hashedPlaintext = "";
	}
	
	public Hash(String plaintext) {
		this.metadata = new Metadata();
		metadata.incrementTimesRequested();
		metadata.updateInstantFound();
		this.hashedPlaintext = generateHashedPlaintext(plaintext);
	}
	
	
	public Metadata getMetadata() {
		return metadata;
	}
	public String getHashedPlaintext() {
		return hashedPlaintext;
	}
	
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	public void setHashedPlaintext(String hashedPlaintext) {
		this.hashedPlaintext = hashedPlaintext;
	}
	
	public abstract String  generateHashedPlaintext(String plaintext);

	public abstract String getHashType();
	
}
