import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import utils.irregularverbs.IIrregularVerb;
import utils.irregularverbs.IrregularVerb;
import utils.irregularverbs.imp.IImportIrregularVerbs;
import utils.irregularverbs.imp.ImportIrregularVerbs;


/**
 * The main class of program, present main logic of validation inputed verbs, import list of verbs
 * @author heathk1iff
 *
 */

public class IrregularVerbsChecker {
	
	public enum WordInputMode {
		wimRussianTranslation("RussianTranslation"), 
		wimEnglishVerbs("EnglishVerbs");
		
	    private String caption;
	    
	    WordInputMode(String caption){
	    	this.caption = caption;
	    }
	    
	    public String getCaption() {
	    	return caption;
	    }
	
	};
	
	public enum IrregularVerbType {
		ivtPresent("Present"),
		ivtPast("Past"),
		ivtPastParticipant("Past Participant");
		
		private String caption;
	    
		IrregularVerbType(String caption){
	    	this.caption = caption;
	    }
	    
	    public String getCaption() {
	    	return caption;
	    }
	};
	
	
	private WordInputMode mode = WordInputMode.wimEnglishVerbs;
	private int verbsCount = 10;
	public static IrregularVerbsChecker checker;
	public ArrayList<IIrregularVerb> irregularVerbs = new ArrayList<IIrregularVerb>();
	
	
	/**
	 * The method provide possibility to import list of verbs from external source.  
	 * 
	 * @param iterator The external source for import.
	 * @param clearBefore If this parameter is true then collection will clear of self list before import, otherwise append items.
	 * @return The method return count of imported items
	 */
	public int importVerbs(IImportIrregularVerbs source, boolean clearBefore){
		if (clearBefore)
			irregularVerbs.clear();

		while (source.hasNext()) { 
			irregularVerbs.add(new IrregularVerb(source.next()));
		}
		return irregularVerbs.size();		
	}
	
	
	/**
	 * This method provide possibility to set VerbsCount variable
	 * @param count
	 */
	void setVerbsCount(int count) {
		this.verbsCount = count;
	}
	
	
	/**
	 * This method returns verbs count
	 * @return
	 */
	
	int getVerbsCount() {
		return this.getVerbsCount();
	}
	
	/**
	 * This method returns word input mode for session
	 * @return
	 */
	WordInputMode setWordInputMode() {
		return this.mode;
	}
	

	/**
	 * this method povide possibility to set word input mode for session
	 * @param mode
	 */
	void setWordInputMode(WordInputMode mode) {
		this.mode = mode;
	}
	

	/**
	 * The method is main method for executing of program
	 */
	
	public void execute() {
		
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			do {
				
				ArrayList<Integer> randomVerbs = new ArrayList<Integer>(verbsCount);
				Random rand = new Random();
				
				for (int i = 0; i < verbsCount; i++) {
					int randomVar = -1;
					
					while (randomVar == -1) {
						randomVar =	rand.nextInt(irregularVerbs.size());
						if (randomVerbs.contains(randomVar))
							randomVar = -1;				
					}
					
					randomVerbs.add(randomVar);
				}
					
				int indexVerb = 0;
				do {					
					
					IIrregularVerb verb = irregularVerbs.get(randomVerbs.get(indexVerb));
	
					if (mode == WordInputMode.wimEnglishVerbs) {
						System.out.println(String.format("Please write irregular verbs for russion word: %s", verb.getTranslatedWord()));					
	
						int successWords = 0;
						for (IrregularVerbType verbType: IrregularVerbType.values()) {
							
							System.out.print(String.format("[%s]:", verbType.getCaption()));	
							String inputtedWord = bufReader.readLine();
							switch (verbType) {
								case ivtPresent: 
									successWords += verb.checkPresentWord(inputtedWord) ? 1 : 0;
								break;
								case ivtPast: 
									successWords += verb.checkPastWord(inputtedWord) ? 1 : 0;
								break;
								case ivtPastParticipant: 
									successWords += verb.checkPastParticipantWord(inputtedWord) ? 1 : 0;
								break;
							}
							
						}
						
						if (successWords != IrregularVerbType.values().length) {
							
							System.out.println(String.format("Incorrect: %s, %s, %s", verb.getPresentWord(), verb.getPastWord(), verb.getPastParticipantWord()));
							indexVerb++;							
						} else {
							randomVerbs.remove(randomVerbs.get(indexVerb));
						}
						
						if (indexVerb >= randomVerbs.size())
							indexVerb = 0;
					}
	
				} while (randomVerbs.size() > 0);
			
				System.out.println("Please press any key for repeat exercise, otherwise write 'quit' for exit.");			
				
			} while (!bufReader.readLine().toLowerCase().contains("quit"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
		
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		CommandLineParser commandParser = new DefaultParser();
		Options commandOptions = new Options();
		commandOptions.addOption("i", "import", true, "This option give possibility to specify text file for import into program");
		commandOptions.addOption("m", "mode", true, "This option specify user input mode.");
		commandOptions.addOption("v", "verbs", true, "This option specify count of verbs for current session.");
		
		checker = new IrregularVerbsChecker();
		
		try {
			CommandLine commandLine = commandParser.parse(commandOptions, args);
			if (commandLine.hasOption("i")){
				try {
					checker.importVerbs(new ImportIrregularVerbs(new FileInputStream(commandLine.getOptionValue("i"))), true);
				} catch (UnsupportedEncodingException e) {
					System.out.println(String.format("Err: Encoding of import file (%s) is not UTF-8.", commandLine.getOptionValue("i")));
				} catch (FileNotFoundException e) {
					System.out.println(String.format("Err:Import file (%s) is not found.", commandLine.getOptionValue("i")));
				}
			} else if (commandLine.hasOption("m")) {
				if (commandLine.getOptionValue("m") == "ru") {
					checker.setWordInputMode(WordInputMode.wimRussianTranslation);
				} else {
					checker.setWordInputMode(WordInputMode.wimEnglishVerbs);
				}
			} else if (commandLine.hasOption("v")) {
				checker.setVerbsCount(Integer.parseInt(commandLine.getOptionValue("v")));
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
		checker.execute();
		checker = null;
	}

}
