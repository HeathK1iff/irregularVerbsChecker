package utils.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Iterable;

/**
 * The class that give possibility to convert input array parameter string like:
 * --key1 [val1] [..valN] --key2 ...
 * to collection of keys with multiply list of parameters. 
 * @author heathk1iff
 *  
 */

public class Parametrs implements Iterable<Parametr> {
	private ArrayList<Parametr> params  = new ArrayList<Parametr>();
	private static final String PARAM_PREFIX = "--";
	
	public Parametrs(String[] args){
		Parametr last = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i].matches("^"+PARAM_PREFIX+"\\S+")) { //Is it key?
				last = new Parametr(args[i].substring(PARAM_PREFIX.length()).toLowerCase());
				params.add(last);
			} else {
				if (last != null) {
					last.addParam(args[i]);
				}
			}
		}
	}
	
	/**
	 * The method returns instance of Parametr class by key text
	 * @param key is string key text
	 * @return return instance of Parametr class
	 */
	public Parametr getParam(String key) {
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).getKey().equals(key)) {
				return params.get(i);
			}
		}
		return null;
	}

	/**
	 * The method returns iterator for parameters
	 */
	@Override
	public Iterator<Parametr> iterator() {
		return new ParametrsIterator(params);
	}
	
}
