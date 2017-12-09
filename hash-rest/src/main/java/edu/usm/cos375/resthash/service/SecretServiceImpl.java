package edu.usm.cos375.resthash.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.usm.cos375.resthash.datasource.SecretRepository;
import edu.usm.cos375.resthash.exception.HashCrackException;
import edu.usm.cos375.resthash.exception.LmPlaintextException;
import edu.usm.cos375.resthash.model.LmHash;
import edu.usm.cos375.resthash.model.Secret;

/*
 * Implementation of SecretService
 */

@Service
public class SecretServiceImpl implements SecretService {

	@Autowired
	private SecretRepository secretRepository;

	@Autowired
	private LMGenerator lmGenerator;
	
	@Autowired 
	private LMCracker lmCracker;


	/*
	 * Returns all Secrets already in the database
	 */
	@Override
	public List<Secret> getAll() {

		Iterable<Secret> secrets = secretRepository.findAll();
		if(secrets != null) {
			for(Secret s : secrets) {
				s.incrementTimesRequested();
			}
			secretRepository.save(secrets);
		}
		return (List<Secret>) secrets;
	}

	/*
	 * Returns a Secret whose hash matches the supplied htext
	 * First checks the database for that Secret
	 * If Secret is not found there it will use the LMCracker service to crack the password
	 */
	@Override
	public Secret getByHash(String htext) throws HashCrackException {
		Secret secret = secretRepository.findByHashedPlaintext(htext);
		if(secret != null) {
			secret.incrementTimesRequested();
			secretRepository.save(secret);
		}
		else {
			long startFind = Instant.now().getEpochSecond();
			String ptext = lmCracker.crackHash(htext);
			long endFind = Instant.now().getEpochSecond();
			secret = new Secret();
			secret.setPlaintext(ptext);
			LmHash hash = new LmHash();
			hash.setHashedPlaintext(htext);
			hash.getMetadata().updateInstantFound();
			hash.getMetadata().setSecondsToFind(endFind - startFind);
			secret.setLmHash(hash);
			secret.incrementTimesRequested();
			
			secretRepository.save(secret);
			
		}
		return secret;
	}

	/*
	 * Return a Secret whose plaintext matches the supplied ptext
	 * If the Secret does not already exist in the database it is created
	 */
	@Override
	public Secret getByPlaintext(String ptext) throws LmPlaintextException {
		Secret s = secretRepository.findByPlaintext(ptext);
		if(s != null) {
			s.incrementTimesRequested();
			secretRepository.save(s);
			return s;
		}
		else {
			return create(ptext);
		}
	}

	/*
	 * Remove the Secret with the given plaintext ptext from the database
	 */
	@Transactional
	@Override
	public void delete(String ptext) {
		secretRepository.deleteByPlaintext(ptext);
	}

	/*
	 * Creates the Secret with the given ptext and adds it to the database
	 * Returns the Secret created
	 */
	@Transactional
	@Override
	public Secret create(String ptext) throws LmPlaintextException {
		Secret sec = new Secret();
		sec.setPlaintext(ptext);
		String hashtext;
		long startTime = Instant.now().getEpochSecond();
		hashtext = lmGenerator.findHash(ptext);
		long endTime = Instant.now().getEpochSecond();
		
		LmHash lmHash = new LmHash();
		lmHash.setHashedPlaintext(hashtext);
		sec.setLmHash(lmHash);
		lmHash.getMetadata().updateInstantFound();
		lmHash.getMetadata().setSecondsToFind(endTime - startTime);
		return secretRepository.save(sec);
	}

	/*
	 * Returns true if a Secret exists in the database with the plaintext supplied by ptext,
	 * false otherwise
	 */
	@Override
	public boolean exists(String ptext) {
		return secretRepository.existsByPlaintext(ptext);
	}

}
