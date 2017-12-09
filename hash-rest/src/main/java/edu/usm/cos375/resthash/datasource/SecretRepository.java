package edu.usm.cos375.resthash.datasource;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import edu.usm.cos375.resthash.model.Secret;

/*
 * Repository to handle persistence with the MySQL database
 * Because it extends CrudRepository<Secret, Long> it will provide default implementations for
 * 		delete(Secret)
 * 		save(Secret)
 * 		findAll()
 * 		among others
 */

public interface SecretRepository extends CrudRepository<Secret, Long>{

	@Query("SELECT sec FROM Secret sec WHERE sec.plaintext = ?1")
	public Secret findByPlaintext(String ptext);

	@Modifying
	@Query("DELETE FROM Secret sec WHERE plaintext = ?1")
	public void deleteByPlaintext(String ptext);

	public boolean existsByPlaintext(String ptext);	
	
	@Query("SELECT sec, hash FROM Secret sec JOIN sec.lmHash hash WHERE hash.hashValue = ?1")
	public Secret findByHashedPlaintext(String htext);

}
