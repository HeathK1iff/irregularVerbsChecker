package utils.irregularverbs.verbs;

import utils.irregularverbs.readers.IIrregularVerbsReader;
import java.io.IOException;
import java.util.ArrayList;

public class IrregularVerbs extends ArrayList<IIrregularVerb>{
    private static IrregularVerbs instance;

    private IrregularVerbs(){
        ;
    }

    public int load(IIrregularVerbsReader reader) throws IOException {
        IIrregularVerb verb;
        while ((verb = reader.readVerb()) != null){
            add(new IrregularVerb(verb));
        }

        return this.size();
    }

    public static IrregularVerbs getInstance(){
        if (instance == null)
            instance = new IrregularVerbs();
        return instance;
    }

    public IrregularVerbRandom getRandom(int count){
        return new IrregularVerbRandom(this, count);
    }
}
