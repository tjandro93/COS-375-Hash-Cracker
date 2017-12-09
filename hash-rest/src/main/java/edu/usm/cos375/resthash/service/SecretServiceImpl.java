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

@Service
public class SecretServiceImpl implements SecretService {

	@Autowired
	private SecretRepository secretRepository;

	@Autowired
	private LMGenerator lmGenerator;
	
	@Autowired LMCracker lmCracker;


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

	@Transactional
	@Override
	public void delete(String ptext) {
		secretRepository.deleteByPlaintext(ptext);
	}

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

	@Override
	public boolean exists(String ptext) {
		return secretRepository.existsByPlaintext(ptext);
	}

}
