package edu.usm.cos375.resthash.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.usm.cos375.resthash.datasource.SecretRepository;
import edu.usm.cos375.resthash.exception.LmPlaintextException;
import edu.usm.cos375.resthash.model.LmHash;
import edu.usm.cos375.resthash.model.Secret;

@Service
public class SecretServiceImpl implements SecretService {

	@Autowired
	private SecretRepository secretRepository;

	@Autowired
	private LMGenerator lmGenerator;


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
	public Secret getByHash(String htext) {
		Secret secret = secretRepository.findByHashedPlaintext(htext);
		if(secret != null) {
			secret.incrementTimesRequested();
			secretRepository.save(secret);
		}
		return secret;
	}

	@Override
	public Secret getByPlaintext(String ptext) {
		Secret s = secretRepository.findByPlaintext(ptext);
		if(s != null) {
			s.incrementTimesRequested();
			secretRepository.save(s);
		}
		return s;
	}

	@Override
	public void delete(String ptext) {
		secretRepository.deleteByPlaintext(ptext);
	}

	@Override
	public void create(String ptext) throws LmPlaintextException {
		Secret sec = new Secret();
		sec.setPlaintext(ptext);
		String hashtext;
		hashtext = lmGenerator.findHash(ptext);

		LmHash lmHash = new LmHash();
		lmHash.setHashedPlaintext(hashtext);
		sec.setLmHash(lmHash);
		lmHash.getMetadata().updateInstantFound();
		secretRepository.save(sec);

	}

	@Override
	public boolean exists(String ptext) {
		return secretRepository.existsByPlaintext(ptext);
	}

}
