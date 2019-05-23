package utils.irregularverbs;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provide implementation of shared IrregularVerb interface
 * 
 * @author heathk1iff
 * 
 */
public class IrregularVerb implements IIrregularVerb{
	private String PresentWord;
	private List<String> PastWord = new ArrayList<String>();
	private List<String> PastParticipantWord = new ArrayList<String>();
	private List<String> TranslatedWord = new ArrayList<String>();
	
	/**
	 * Default constructor of class
	 */
	public IrregularVerb() {
		
	}
	
	/**
	 * The constructor provide possibility to initialize of properties from IIrregularVerb class 
	 * @param verb The parameter.
	 */
	public IrregularVerb(IIrregularVerb verb){
		this();
		
		this.PresentWord = verb.getPresentWord();
		
		for (String word: verb.getPastWord())
		  this.PastWord.add(word);
		
		for (String word: verb.getPastParticipantWord())
			  this.PastParticipantWord.add(word);
			
		for (String word: verb.getTranslatedWord())
			  this.TranslatedWord.add(word);
	}


	/**
	 * This method provide of clear internal structures of object 
	 */
	public void clear() {
		PresentWord = "";
		PastWord.clear();
		PastParticipantWord.clear();
		TranslatedWord.clear();
	}
	
		
	@Override
	public String toString() {
		return String.format("%s; %s; %s; %s", PresentWord, PastWord, PastParticipantWord, TranslatedWord);
	}

	/**
	 * The method returns Present form of irregular verb
	 */
	@Override
	public String getPresentWord() {
		return PresentWord;
	}

	/**
	 * The method returns list of Past form of irregular verbs.
	 */
	@Override
	public List<String> getPastWord() {
		return PastWord;
	}

	/**
	 * The method returns list of Past Participant of irregular verbs.
	 */
	@Override
	public List<String> getPastParticipantWord() {
		return PastParticipantWord;
	}
	
	/**
	 * The method returns list of translated words of irregular verb
	 */
	@Override
	public List<String> getTranslatedWord() {
		return TranslatedWord;
	}
	
	/**
	 * This method provide possibility to assign value for class property, and contains present form of irregular verb
	 * @param value It is present form of irregular verb
	 */
	public void setPresentWord(String value) {
		this.PresentWord = value.toLowerCase();
	}

	/**
	 * This method provide possibility to append value for list, it contains past form of irregular verb
	 * @param value It is past form of irregular verb
	 */
	public void appendPastWord(String value) {
		this.PastWord.add(value.toLowerCase());
	}

	/**
	 *  This method provide possibility to append value for list, it contains past participant form of irregular verb
	 * @param value It is past participant form of irregular verb
	 */
	public void appendPastParticipantWord(String value) {
		this.PastParticipantWord.add(value.toLowerCase());
	}

	/**
	 *  This method provide possibility to append value for list, it contains translated words of irregular verb
	 * @param value It is translated words form of irregular verb
	 */
	public void appendTranslatedWord(String value) {
		this.TranslatedWord.add(value.toLowerCase());
	}

	/**
	 *  This method validate of existing of inputed parameter for list of Past irregular verb
	 * @param value It is past form of irregular verb
	 */
	
	@Override
	public boolean checkPastWord(String word) {
		return PastWord.contains(word.toLowerCase());
	}

	/**
	 *  This method validate of existing of inputed parameter for list of Past Participant irregular verb
	 * @param value It is past participant form of irregular verb
	 */
	
	@Override
	public boolean checkPastParticipantWord(String word) {
		return PastParticipantWord.contains(word.toLowerCase());
	}

	/**
	 *  This method validate of existing of inputed parameter for list of translated words of irregular verb
	 * @param value It is translated words of irregular verb
	 */
	
	@Override
	public boolean checkTranslatedWord(String word) {
		return TranslatedWord.contains(word.toLowerCase());
	}


	/**
	 *  This method validate of existing of inputed parameter for list of Present irregular verb
	 * @param value It is present form of irregular verb
	 */
	
	@Override
	public boolean checkPresentWord(String word) {
		return PresentWord.contains(word.toLowerCase());
	}
}
