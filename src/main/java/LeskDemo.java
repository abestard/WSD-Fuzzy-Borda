import de.tudarmstadt.ukp.dkpro.wsd.lesk.algorithm.Lesk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import de.tudarmstadt.ukp.dkpro.wsd.lesk.algorithm.SimplifiedLesk;
import de.tudarmstadt.ukp.dkpro.wsd.lesk.util.normalization.NoNormalization;
import de.tudarmstadt.ukp.dkpro.wsd.lesk.util.overlap.SetOverlap;
import de.tudarmstadt.ukp.dkpro.wsd.lesk.util.tokenization.StringSplit;
import de.tudarmstadt.ukp.dkpro.wsd.si.POS;
import de.tudarmstadt.ukp.dkpro.wsd.si.SenseInventory;
import de.tudarmstadt.ukp.dkpro.wsd.si.SenseInventoryException;
import de.tudarmstadt.ukp.dkpro.wsd.si.TestSenseInventory;
import de.tudarmstadt.ukp.dkpro.wsd.si.TestSenseInventory;
import java.net.URL;

import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.jlt.util.Language;
import net.sf.extjwnl.JWNLException;

import  de.tudarmstadt.ukp.dkpro.wsd.si.wordnet.WordNetSynsetSenseInventory;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.WordNetSynsetID;

public class LeskDemo {

    public static void main(String[] args) throws Exception {
        WordNetSynsetSenseInventory inventory = new WordNetSynsetSenseInventory(
                new URL("file:///D:/DKPRO_HOME/LexSemResources/WordNet_3/extjwnl_properties.xml")); //wordnet_properties.xml") );


        SimplifiedLesk l = new SimplifiedLesk(inventory,
                new SetOverlap(),
                new NoNormalization(),
                new StringSplit(),
                new StringSplit()
        );
        String[] contextWords1 = {"bat", "bank", "test"};
        POS[] contextPos1 = {POS.NOUN, POS.NOUN, POS.NOUN};
        String[] sodSenses2 = {"foo foo foo", "bar", "baz"};
        String[][] contextSenses2 = {{"foo foo foo"}, {"bar"}, {"bar baz"}};

//{01293832n=4.0, 08750822n=3.0}
        l.setSenseInventory(inventory);
        Map<String, Double> senseMap;

        ///API para buscar el significado: http://wordnet.pt/api/synset/08752671-n
        // Find the sense of "bank" which best matches the context string "financial institution"
        senseMap = l.getDisambiguation("United States", POS.NOUN, "President Diaz Canel gave a speech in Havana on his return from Venezuela, on the conduct ingerencista United States to Cuba");
        System.out.println(  senseMap.toString() );
        System.out.println( senseMap.keySet() );
        System.out.println( "wn:"+senseMap.keySet().toArray()[0] );

//        BabelNet bn = BabelNet.getInstance();
        for( String s_wn :senseMap.keySet()  ) {
            System.out.println(s_wn);
//            BabelSynset synset = bn.getSynset( new WordNetSynsetID("wn:"+senseMap.keySet().toArray()[0]));
//            System.out.println( synset.getID().toString() );
        }



//        BabelSynset synset = bn.getSynset( new WordNetSynsetID("wn:"+senseMap.keySet().toArray()[0]));
//        System.out.println( synset.getID().toString() );

        BabelNet bn = BabelNet.getInstance();

        for (BabelSense s : bn.getSensesFrom("Havana", Language.EN))
            if( s.getSource().toString().matches("GEONM") ){
                System.out.println(s);
                System.out.println(s.getSynset().getID());
            }


        System.out.println("Ok");
    }
}
