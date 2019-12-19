package experts;

import utils.Entity;
import utils.Sense;

import java.util.ArrayList;
import java.util.List;

public class FuzzyBordaExpert extends Experts{
    private List<Experts> ListExperts;
    private Double limit = 0.5;

    public FuzzyBordaExpert( List<Experts> e ){
        ListExperts = e;
    }

    private Double calcCost( Double w1, Double w2 ){
        if( w2+w1 == 0.0 ) return 0.0;
        return w1/(w1+w2);
    }

    private Entity update( Entity ent ){
        int L = ent.senses.size();
        Double arr[] = new Double[L];
        for( int i = 0; i < L; i ++ ) arr[i] = 0.0;

        int id = 0;
        for( Sense s1 : ent.senses ){
            for( Sense s2 : ent.senses ){
                if( s1.name.equals(s2.name) ) continue;

                Double cost = calcCost( s1.score, s2.score );
                if( cost >= limit )
                    arr[id] += cost;
            }

            id += 1;
        }

        id = 0;
        for( Sense s1 : ent.senses ) {
            s1.score = arr[id];
            id += 1;
        }

        return  ent;
    }

    private List<Entity> resExpert( Experts exp, String text, List<Entity> Entitys ){
        List<Entity> res = exp.predict( text, Entitys );

        for( Entity ent : res )
            ent = update( ent );

        return res;
    }

    private Entity mergeEntity(Entity E1, Entity E2){
        int id = 0;
        for ( Sense e1 : E1.senses ){
            Sense e2 = E2.senses.get(id);
            e1.score += e2.score;
            id += 1;
        }

        return E1;
    }

    private List<Entity> merge(List<Entity> E1, List<Entity> E2){
        List<Entity> res = new ArrayList<Entity>();

        int L = E1.size();
        for( int i = 0; i < L; i ++ ){
            Entity ent1 = E1.get(i);
            Entity ent2 = E2.get(i);

            res.add( mergeEntity( ent1, ent2 ) );
        }

        return res;
    }

    public List<Entity> predict(String text, List<Entity> Entitys){
        List<Entity> result = new ArrayList<Entity>();

        for( Entity ent : Entitys ){
            Entity e1 = new Entity( ent.name );
            for ( Sense sen : ent.senses ){
                e1.senses.add( new Sense(sen.name, sen.lat, sen.lon) );
            }
            result.add( e1 );
        }

        for ( Experts exp : ListExperts ){
            List<Entity> res1 = resExpert( exp, text, Entitys );
            result = merge( result, res1 );
        }

        return result;
    }
}
