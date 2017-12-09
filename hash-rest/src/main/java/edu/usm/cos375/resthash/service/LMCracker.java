package edu.usm.cos375.resthash.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import edu.usm.cos375.resthash.exception.HashCrackException;

/*
 * A Service to invoke Ophcracker to crack an LM Hash
 * Using this service requires the following resources (-) and directories (+) exist within /src
 * 
 * 	+src
 * 		+ophcrack-3.7.0-bin
 * 			+x64
 * 				+tables_xp_free_fast
 * 					- //The relevant rainbow tables
 * 				+tempFiles
 * 					-in.txt
 * 					-out.txt
 * 				- ophcrack_nogui.exe
 * 			
 */

@Service
public class LMCracker {

	private static final String OPHCRACK_PATH = "src/ophcrack-3.7.0-bin/x64";
	private static final String TABLE_PATH = "tables_xp_free_fast";
	private static final String OUTPUT_PATH = "tempFiles/out.txt";
	private static final String INPUT_PATH = "tempFiles/in.txt";
	
	
	private static final String FILE_OUTPUT_PATH = "src/ophcrack-3.7.0-bin/x64/tempFiles/out.txt";
	private static final String FILE_INPUT_PATH = "src/ophcrack-3.7.0-bin/x64/tempFiles/in.txt";
	
	private static final String[] PROCESS_CALL = {"cmd", "/c", "ophcrack_nogui", "-g", "-d", TABLE_PATH, "-t", TABLE_PATH, 
													"-f", INPUT_PATH, "-o", OUTPUT_PATH};
	
	
	public String crackHash(String hashText) throws HashCrackException {
		
		if(hashText.length() != 32  && hashText.length() != 16) {
			throw new HashCrackException(hashText, "provided hash was incorrect length");
		}
		
		
		String workingDir = System.getProperty("user.dir");
		
		File targetDir = new File(workingDir + "/" + OPHCRACK_PATH);

		File inFile = new File(FILE_INPUT_PATH);
		File outFile = new File(FILE_OUTPUT_PATH);
		
		try {
			outFile.delete();
			outFile.createNewFile();
			inFile.delete();
			inFile.createNewFile();
			
			FileWriter writer = new FileWriter(inFile);
			writer.write(hashText.toUpperCase());
			writer.flush();
			writer.close();
			
		} catch (IOException e1) {
			throw new HashCrackException(hashText, "could not access " + FILE_INPUT_PATH);
		}
		
		Process pr;
		try {
			pr = Runtime.getRuntime().exec(PROCESS_CALL, null, targetDir);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new HashCrackException(hashText, "could not create ophcrack process");
		}
		
		try {
			pr.waitFor();
		} catch (InterruptedException e) {
			throw new HashCrackException(hashText, "RunOphcrack was interrupted while waiting");
		}
		
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(outFile));
		} catch (FileNotFoundException e) {
			throw new HashCrackException(hashText, FILE_OUTPUT_PATH + " could not be located");
		}
		
		String result;
		try {
			result = reader.readLine();
		} catch (IOException e) {
			try {
				reader.close();
			} catch (IOException e1) {
				// reader already closed so do nothin
			}
			throw new HashCrackException(hashText, "Could not read from " + FILE_OUTPUT_PATH);
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// reader already closed, so do nothing
		}
		
		if(result == null) {
			throw new HashCrackException(hashText, "the file output by Ophcrack was empty");
		}
		Scanner sc = new Scanner(result);
		
		sc.useDelimiter(":+");
		String hashIn = "";
		String ptext1 = "";
		String ptext2 = "";
		if(!sc.hasNext()) {
			unexpectedFormat(hashText);
		}
		hashIn = sc.next();
		if(!hashIn.equals(hashText)) {
			throw new HashCrackException(hashText, "the hash from Ophcrack does not match the requested hash");
		}
		if(!sc.hasNext()) {
			unexpectedFormat(hashText);
		}
		ptext1 = sc.next();
		if(sc.hasNext()) {
			ptext2 = sc.next();
		}
		

		String finalText = ptext1 + ptext2;
		if(finalText.equals("") || finalText == null) {
			throw new HashCrackException(hashText, "The empty password was returned");
		}
		
		return finalText;
	}
	
	private void unexpectedFormat(String hashText) throws HashCrackException {
		throw new HashCrackException(hashText, "the file created by Ophcrack is in unexpected format");
	}
	
	
	
}
