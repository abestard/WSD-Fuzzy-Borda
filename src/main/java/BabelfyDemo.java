import it.uniroma1.lcl.babelfy.core.*;
import it.uniroma1.lcl.babelfy.commons.*;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.*;
import it.uniroma1.lcl.babelfy.commons.annotation.*;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation.*;
import it.uniroma1.lcl.jlt.util.Language;
import sun.reflect.annotation.AnnotationType;

import java.util.*;

public class BabelfyDemo {

    public static void main(String[] args) {
//"I a from Santiago de Cuba, Cuba";//;

        String inputText = "President Diaz Canel gave a speech in Havana on his return from Venezuela, on the conduct ingerencista United States to Cuba";
//        BabelfyConstraints constraints = new BabelfyConstraints();
//        SemanticAnnotation a = new SemanticAnnotation(new TokenOffsetFragment(0, 0), "bn:03083790n",
//                "http://dbpedia.org/resource/BabelNet", Source.OTHER);
//        constraints.addAnnotatedFragments(a);
        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(SemanticAnnotationResource.BN);
        bp.setMCS(MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(ScoredCandidates.ALL);
        bp.setAnnotationType(SemanticAnnotationType.NAMED_ENTITIES);
        Babelfy bfy = new Babelfy(bp);

        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.EN);

        for (SemanticAnnotation annotation : bfyAnnotations) {
            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1);
            System.out.println(frag + "\t" + annotation.getBabelSynsetID());
            System.out.println("\t" + annotation.getBabelNetURL());
            System.out.println("\t" + annotation.getDBpediaURL());
            System.out.println("\t" + annotation.getScore());
            System.out.println("\t" + annotation.getBabelSynsetID());

        }

        System.out.println("Ok");
    }


}
