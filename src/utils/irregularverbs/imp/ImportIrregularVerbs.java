package utils.irregularverbs.imp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import utils.irregularverbs.IIrregularVerb;
import utils.irregularverbs.IrregularVerb;

public class ImportIrregularVerbs implements IImportIrregularVerbs{
	Iterator<String> iterator;
	BufferedReader bufferedReader;
	IIrregularVerb constVerb;
	
	public ImportIrregularVerbs(InputStream stream) throws UnsupportedEncodingException {
		bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		iterator = bufferedReader.lines().iterator();
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public IIrregularVerb next() {			
		String str = iterator.next();
		
		str = str.replaceAll(" / ", "/");
		String[] arr = str.trim().split("\t");
		
		if (constVerb == null) {
			constVerb = new IrregularVerb();
		} else 
			((IrregularVerb) constVerb).clear();

		((IrregularVerb)constVerb).setPresentWord(arr[0].trim());
		
		for (String item: arr[1].split("/")){
			((IrregularVerb)constVerb).appendPastWord(item.trim());	
		}
		
		for (String item: arr[2].split("/")){
			((IrregularVerb)constVerb).appendPastParticipantWord(item.trim());	
		}
		
		for (String item: arr[3].split(",")){
			((IrregularVerb)constVerb).appendTranslatedWord(item.trim());	
		}
		
		return constVerb;
	}
	
	
};
