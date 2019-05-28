package utils.irregularverbs.verbs;

import java.util.ArrayList;
import java.util.Random;

public class IrregularVerbRandom extends ArrayList<IIrregularVerb> {
    private IrregularVerbs verbs;

    public IrregularVerbRandom(IrregularVerbs verbs, int count){
        super(count);
        this.verbs = verbs;

        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int randomVar = -1;
            while (randomVar == -1) {
                randomVar =	rand.nextInt(this.verbs.size());
                if (this.contains(randomVar))
                    randomVar = -1;
            }
            this.add(verbs.get(randomVar));
        }
    }
}
