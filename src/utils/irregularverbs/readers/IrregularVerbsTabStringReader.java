package utils.irregularverbs.readers;

import utils.irregularverbs.verbs.IIrregularVerb;
import utils.irregularverbs.verbs.IrregularVerb;

import java.io.IOException;
import java.io.Reader;

public class IrregularVerbsTabStringReader extends IIrregularVerbsReader {
    IIrregularVerb constVerb;

    public IrregularVerbsTabStringReader(Reader in) {
        super(in);
    }

    @Override
    public IIrregularVerb readVerb() throws IOException {
        String str;

        if ((str = this.readLine()) != null){
            str = str.replaceAll(" / ", "/");
            String[] arr = str.trim().split("\t");

            if (constVerb == null) {
                constVerb = new IrregularVerb();
            } else
                ((IrregularVerb) constVerb).clear();

            ((IrregularVerb) constVerb).setPresentWord(arr[0].trim());

            for (String item : arr[1].split("/")) {
                ((IrregularVerb) constVerb).appendPastWord(item.trim());
            }

            for (String item : arr[2].split("/")) {
                ((IrregularVerb) constVerb).appendPastParticipantWord(item.trim());
            }

            for (String item : arr[3].split(",")) {
                ((IrregularVerb) constVerb).appendTranslatedWord(item.trim());
            }
        } else {
            constVerb = null;
        }

        return constVerb;
    }
}
