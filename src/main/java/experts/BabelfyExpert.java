package experts;

import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.jlt.util.Language;
import utils.Entity;
import utils.Sense;

import java.util.List;

public class BabelfyExpert extends Experts {
    public List<Entity> predict(String inputText, List<Entity> Entitys){

        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(BabelfyParameters.SemanticAnnotationResource.BN);
        bp.setMCS(BabelfyParameters.MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(BabelfyParameters.ScoredCandidates.ALL);
        bp.setAnnotationType(BabelfyParameters.SemanticAnnotationType.NAMED_ENTITIES);
        Babelfy bfy = new Babelfy(bp);

        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.EN);


        for( Entity ent : Entitys ) {
            for (SemanticAnnotation annotation : bfyAnnotations) {
                String name_ent = inputText.substring(annotation.getCharOffsetFragment().getStart(),
                        annotation.getCharOffsetFragment().getEnd() + 1);

                if( name_ent.equals(ent.name) ) {
                    for (Sense s : ent.senses) {
                        if ( !s.name.equals( annotation.getBabelSynsetID()) ) continue;
                        s.score = annotation.getScore();
                        break;
                    }
                }
            }
        }

        return Entitys;
    }
}
