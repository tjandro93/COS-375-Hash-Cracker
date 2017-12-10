package edu.usm.cos375.resthash.service;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import edu.usm.cos375.resthash.exception.LmPlaintextException;

/*
 * Service to generate the LM Hash of a given plaintext
 * 
 * I used the following tutorials for help in implementing this
 *		https://blog.skullsecurity.org/2008/lanman-and-ntlm-not-as-complex-as-you-think
 * 		https://www.mkyong.com/java/jce-encryption-data-encryption-standard-des-tutorial/
 * and a bunch of reading through the JCE documentation
 * 		https://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html
 */

@Service
public class LMGenerator {

	private static final byte[] DES_CONSTANT = "KGS!@#$%".getBytes();

	public  String findHash(String ptext) throws LmPlaintextException {
		if(ptext.length() > 14) {
			throw new LmPlaintextException(ptext, "plaintext must be less than 14 characters");
		}
		if(!Charset.forName("US-ASCII").newEncoder().canEncode(ptext)) {
			throw new LmPlaintextException(ptext, "plaintext must only contain printable ASCII characters");
		}
		ptext = ptext.toUpperCase();
		byte[] ptextCharArray = ptext.getBytes(Charset.forName("US-ASCII"));
		byte[] allByteArray = new byte[14];
		int i = 0;
		while (i < ptext.length()) {
			if(!isPrintableASCII(ptextCharArray[i])) {
				throw new LmPlaintextException(ptext, "plaintext must only contain printable ASCII characters");
			}
			allByteArray[i] = ptextCharArray[i];
			i++;
		}
		while( i < 14) {
			allByteArray[i] = 0;
			i++;
		}

		byte[] half1 = new byte[7];
		byte[] half2 = new byte[7];
		System.arraycopy(allByteArray, 0, half1, 0, 7);
		System.arraycopy(allByteArray, 7, half2, 0, 7);

		byte[] key1 = passwordBytesToKeyBytes(half1);
		byte[] key2 = passwordBytesToKeyBytes(half2);

		DESKeySpec desKeySpc1, desKeySpc2;

		try {
			desKeySpc1 = new DESKeySpec(key1);
			desKeySpc2 = new DESKeySpec(key2);
		} catch (InvalidKeyException e) {
			throw new LmPlaintextException(ptext, "error occured generating the DES key spec");
		}
		SecretKeyFactory keyFactory;
		try {
			keyFactory = SecretKeyFactory.getInstance("DES");
		} catch (NoSuchAlgorithmException e1) {
			throw new LmPlaintextException(ptext, "error occured getting DES key factory: " + e1.getMessage());
		}

		SecretKey desKey1, desKey2;
		try {
			desKey1 = keyFactory.generateSecret(desKeySpc1);
			desKey2 = keyFactory.generateSecret(desKeySpc2);
		} catch (InvalidKeySpecException e1) {
			throw new LmPlaintextException(ptext, "error occured generating secret key: " + e1.getMessage());
		}
		Cipher desCipher;

		try {
			desCipher = Cipher.getInstance("DES/ECB/NoPadding");
		} catch (Exception e) {
			throw new LmPlaintextException(ptext, "error occured obtaining DES Cipher");
		}

		byte[] hash1;
		byte[] hash2;

		try {
			desCipher.init(Cipher.ENCRYPT_MODE, desKey1);
			hash1 = desCipher.doFinal(DES_CONSTANT);
			desCipher.init(Cipher.ENCRYPT_MODE, desKey2);
			hash2 = desCipher.doFinal(DES_CONSTANT);
		} catch (Exception e) {
			throw new LmPlaintextException(ptext, "error occured encrypting LM constant: " + e.getMessage());
		}

		String hashText1 = DatatypeConverter.printHexBinary(hash1);
		String hashText2 = DatatypeConverter.printHexBinary(hash2);

		return hashText1 + hashText2;
	}

	private byte[] passwordBytesToKeyBytes(byte[] password) {
		byte[] key = new byte[8];
		key[0] = (byte) password[0];
		key[1] = (byte) (((password[0]) << 7) | (password[1] >> 1));
		key[2] = (byte) (((password[1]) << 6) | (password[2] >> 2));
		key[3] = (byte) (((password[2]) << 5) | (password[3] >> 3));
		key[4] = (byte) (((password[3]) << 4) | (password[4] >> 4));
		key[5] = (byte) (((password[4]) << 3) | (password[5] >> 5));
		key[6] = (byte) (((password[5]) << 2) | (password[6] >> 6));
		key[7] = (byte) ((password[6]) << 1);

		return key;
	}
	
	private boolean isPrintableASCII(int c) {
		return (c >= 32 && c <=126);
	}
}
