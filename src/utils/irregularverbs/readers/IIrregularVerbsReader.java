package utils.irregularverbs.readers;


import utils.irregularverbs.verbs.IIrregularVerb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class IIrregularVerbsReader extends BufferedReader {
    public IIrregularVerbsReader(Reader in) {
        super(in);
    }

    public abstract IIrregularVerb readVerb() throws IOException;
}
