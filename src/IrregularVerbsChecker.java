import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import utils.irregularverbs.verbs.IIrregularVerb;
import utils.irregularverbs.verbs.IrregularVerbRandom;
import utils.irregularverbs.verbs.IrregularVerbs;
import utils.irregularverbs.readers.IrregularVerbsTabStringReader;


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

	IrregularVerbs verbs = IrregularVerbs.getInstance();
	private WordInputMode mode = WordInputMode.wimEnglishVerbs;
	private int verbsCount = 10;
	public static IrregularVerbsChecker checker;
	public ArrayList<IIrregularVerb> irregularVerbs = new ArrayList<IIrregularVerb>();
	
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
				int indexVerb = 0;
				IrregularVerbRandom rmVerbs = verbs.getRandom(verbsCount);
				while (rmVerbs.size() > 0){
					IIrregularVerb verb = rmVerbs.get(indexVerb);
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
							indexVerb++;
							System.out.println(String.format("Incorrect: %s, %s, %s", verb.getPresentWord(), verb.getPastWord(), verb.getPastParticipantWord()));
						} else {
							rmVerbs.remove(verb);
						}
						
						if (indexVerb >= rmVerbs.size())
							indexVerb = 0;
					}
	
				};
			
				System.out.println("Please press any key for repeat exercise, otherwise write 'quit' for exit.");			
				
			} while (!bufReader.readLine().toLowerCase().contains("quit"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
		
	private static final String OPTION_IN_FILE = "load-file";
	private static final String OPTION_INPUT_MODE = "input-mode";
	private static final String OPTION_VERB_COUNT = "verb-count";

	private static final String OPTION_IN_FILE_SHORT = "lf";
	private static final String OPTION_INPUT_MODE_SHORT = "im";
	private static final String OPTION_VERB_COUNT_SHORT = "vc";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		CommandLineParser commandParser = new DefaultParser();
		Options commandOptions = new Options();
		commandOptions.addRequiredOption(OPTION_IN_FILE_SHORT, OPTION_IN_FILE, true, "This option give possibility to specify text file for import into program");
		commandOptions.addOption(OPTION_INPUT_MODE_SHORT, OPTION_INPUT_MODE, true, "This option specify user input mode.");
		commandOptions.addOption(OPTION_VERB_COUNT_SHORT, OPTION_VERB_COUNT, true, "This option specify count of verbs for current session.");
		
		checker = new IrregularVerbsChecker();

		try {
			CommandLine commandLine = commandParser.parse(commandOptions, args);
			if (commandLine.hasOption(OPTION_IN_FILE)){
				try {
					checker.verbs.load(new IrregularVerbsTabStringReader(new InputStreamReader(new FileInputStream(commandLine.getOptionValue(OPTION_IN_FILE)), "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.println(String.format("Err: Encoding of import file (%s) is not UTF-8.", commandLine.getOptionValue(OPTION_IN_FILE)));
				} catch (FileNotFoundException e) {
					System.out.println(String.format("Err:Import file (%s) is not found.", commandLine.getOptionValue(OPTION_IN_FILE)));
				}
			}

			if (commandLine.hasOption(OPTION_INPUT_MODE)) {
				if (commandLine.getOptionValue(OPTION_INPUT_MODE) == "ru") {
					checker.setWordInputMode(WordInputMode.wimRussianTranslation);
				} else {
					checker.setWordInputMode(WordInputMode.wimEnglishVerbs);
				}
			}

			if (commandLine.hasOption(OPTION_VERB_COUNT)) {
				checker.setVerbsCount(Integer.parseInt(commandLine.getOptionValue(OPTION_VERB_COUNT)));
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}

		checker.execute();
		checker = null;
	}

}
