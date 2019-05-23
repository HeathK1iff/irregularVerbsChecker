package utils.irregularverbs;

import java.util.List;

public interface IIrregularVerb {
	public String getPresentWord();
	public List<String> getPastWord();
	public List<String> getPastParticipantWord();
	public List<String> getTranslatedWord();
	
	public boolean checkPresentWord(String word);
	public boolean checkPastWord(String word);
	public boolean checkPastParticipantWord(String word);
	public boolean checkTranslatedWord(String word);
}
