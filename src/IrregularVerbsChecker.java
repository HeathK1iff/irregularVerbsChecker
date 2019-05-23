import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import utils.app.Parametr;
import utils.app.Parametrs;
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
	
	
	private WordInputMode mode;
	private int verbsCount = 10;
	public static IrregularVerbsChecker checker;
	public ArrayList<IIrregularVerb> irregularVerbs = new ArrayList<IIrregularVerb>();
	private Parametrs params = null;

	
	public IrregularVerbsChecker(Parametrs params) {
		this.mode = WordInputMode.wimRussianTranslation;
		this.params = params;
	}
	
	
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
	 * The method provide prepare of program parameters by inputed keys
	 * @return The method returns true if all is success otherwise false
	 */
	public boolean prepare() {
				
		Parametr param = null;
		if ((param = params.getParam("import")) != null) {
			try {
				this.importVerbs(new ImportIrregularVerbs(new FileInputStream(param.getParam(0))), true);
			} catch (UnsupportedEncodingException e) {
				System.out.println(String.format("Err: Encoding of import file (%s) is not UTF-8.", param.getParam(0)));
				return false;
			} catch (FileNotFoundException e) {
				System.out.println(String.format("Err:Import file (%s) is not found.", param.getParam(0)));
				return false;
			}
		}
		
		if ((param = params.getParam("mode")) != null) {
			
			if (param.getParam(0) == "ru") {
				this.mode = WordInputMode.wimRussianTranslation;
			} else {
				this.mode = WordInputMode.wimEnglishVerbs;
			}
		
		}
		
		if ((param = params.getParam("verbs")) != null) {		
			verbsCount = Integer.parseInt(param.getParam(0));
		}
		
		return true;
	}
	
	
	/**
	 * The method is main method for executing of program
	 */
	
	public void execute() {
		
		if (!this.prepare())
		  return;
		
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
		checker = new IrregularVerbsChecker(new Parametrs(args));
		checker.execute();
		checker = null;
	}

}
