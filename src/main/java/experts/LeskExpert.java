package experts;

import de.tudarmstadt.ukp.dkpro.wsd.lesk.algorithm.SimplifiedLesk;
import de.tudarmstadt.ukp.dkpro.wsd.lesk.util.normalization.NoNormalization;
import de.tudarmstadt.ukp.dkpro.wsd.lesk.util.overlap.SetOverlap;
import de.tudarmstadt.ukp.dkpro.wsd.lesk.util.tokenization.StringSplit;
import de.tudarmstadt.ukp.dkpro.wsd.si.POS;
import de.tudarmstadt.ukp.dkpro.wsd.si.wordnet.WordNetSynsetSenseInventory;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.WordNetSynsetID;
import utils.Entity;
import utils.Sense;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class LeskExpert extends Experts{
    public List<Entity> predict(String text, List<Entity> Entitys) {
        try {
            BabelNet bn = BabelNet.getInstance();

            WordNetSynsetSenseInventory inventory = new WordNetSynsetSenseInventory(
                    new URL("file:///D:/DKPRO_HOME/LexSemResources/WordNet_3/extjwnl_properties.xml"));

            SimplifiedLesk l = new SimplifiedLesk(inventory,
                    new SetOverlap(),
                    new NoNormalization(),
                    new StringSplit(),
                    new StringSplit()
            );
            l.setSenseInventory(inventory);
            Map<String, Double> senseMap;

            ///API para buscar el significado: http://wordnet.pt/api/synset/08752671-n
            for( Entity ent : Entitys ){
                senseMap = l.getDisambiguation(ent.name, POS.NOUN, text);
                for( String s_wn :senseMap.keySet()  ){
                    BabelSynset synset = bn.getSynset( new WordNetSynsetID("wn:"+s_wn));
                    String babelnetId = synset.getID().toString();
                    for(Sense s : ent.senses )
                        if( babelnetId.equals( s.name ) )
                            s.score = senseMap.get(s_wn);
                }

//                for(Sense s : ent.senses )
//                    if( senseMap.keySet().contains(s.name) )
//                        s.score = senseMap.get(s.name);
            }

        }catch( Exception e){}

        return Entitys;
    }
}
