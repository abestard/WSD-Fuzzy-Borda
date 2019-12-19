import experts.*;
import utils.Entity;
import utils.Sense;

import java.util.List;
import java.util.ArrayList;


public class Main {
    public static String text = "";
    public static  List<Entity> Example(){
        //Santiago
        Entity EntStg = new Entity("Santiago");
        EntStg.senses.add(new Sense("bn:00069201n", 20.019833, -75.813919));
        EntStg.senses.add(new Sense("bn:00069202n", 19.466667, -70.699997));
        EntStg.senses.add(new Sense("bn:00015528n", -33.450001, -70.666664));

        //Chile
        Entity EntChile = new Entity("Chile");
        EntChile.senses.add(new Sense("bn:00018369n", -31.716667, -70.833332));

        List<Entity> LEnt = new ArrayList<Entity>();
        LEnt.add( EntStg );
        LEnt.add( EntChile );

        return LEnt;
    }

    public static  List<Entity> Example2(){
        //Havana
        Entity EntHavana= new Entity("Havana");
        EntHavana.senses.add(new Sense("bn:00015532n", 23.133333, -82.383331)); //Havana
        EntHavana.senses.add(new Sense("bn:00239041n", 40.296944, -90.059998)); //Havana,_Illinois
        EntHavana.senses.add(new Sense("bn:02281091n", 37.989445, -81.855278)); //Havana,_West_Virginia
        EntHavana.senses.add(new Sense("bn:00211321n", 30.624166, -84.415001)); //Havana,_Florida
        EntHavana.senses.add(new Sense("bn:00191913n", 35.111111, -93.529167)); //Havana,_Arkansas
        EntHavana.senses.add(new Sense("bn:00276330n", 37.091110, -95.941948)); //Havana,_Kansas
        EntHavana.senses.add(new Sense("bn:00562898n", 26.250278, -98.506111)); //Havana,_Texas
        EntHavana.senses.add(new Sense("bn:03006363n", 32.895000, -87.620277)); //Havana,_Alabama
        EntHavana.senses.add(new Sense("bn:00469973n", 45.950832, -97.618889)); //Havana,_North_Dakota

        //Venezuela
        Entity EntVenezuela = new Entity("Venezuela");
        EntVenezuela.senses.add(new Sense("bn:03664437n", 21.751112, -78.779167)); //Venezuela,_Cuba
        EntVenezuela.senses.add(new Sense("bn:00067239n", 4.2916665, -73.033333)); //Venezuela

        //United_States
        Entity EntUnited_States = new Entity("United States");
        EntUnited_States.senses.add(new Sense("bn:00003341n", 39.4416675, -88.508335));//United_States

        //Cuba
        Entity EntCuba = new Entity("Cuba");
        EntCuba.senses.add(new Sense("bn:16902188n", 42.217777, -78.275276)); //Cuba,_New_York
        EntCuba.senses.add(new Sense("bn:01631103n", 37.357777, -120.568336)); //Cuba,_Merced_County,_California
        EntCuba.senses.add(new Sense("bn:00277486n", 39.802223, -97.457222)); //Cuba,_Kansas
        EntCuba.senses.add(new Sense("bn:00410279n", 38.063057, -91.403336)); //Cuba,_Missouri
        EntCuba.senses.add(new Sense("bn:00234020n", 40.493332, -90.193336)); //Cuba,_Illinois
        EntCuba.senses.add(new Sense("bn:00170427n", 32.43375, -88.371666)); //Cuba,_Alabama
        EntCuba.senses.add(new Sense("bn:02587199n", 46.8213885, -97.862503)); //Cuba,_North_Dakota
        EntCuba.senses.add(new Sense("bn:02530428n", 36.584999, -88.629166)); //Cuba,_Kentucky
        EntCuba.senses.add(new Sense("bn:00024247n", 25.5666665, -81.1916655)); //Cuba
        EntCuba.senses.add(new Sense("bn:00428302n", 36.018333, -106.959724)); //Cuba,_New_Mexico

        List<Entity> LEnt = new ArrayList<Entity>();
        LEnt.add( EntHavana );
        LEnt.add( EntVenezuela );
        LEnt.add( EntUnited_States );
        LEnt.add( EntCuba );

        return LEnt;
    }

    public static void printResult( List<Entity> Entitys ){
        for( Entity ent : Entitys ){
            System.out.println("Name: "+ ent.name);

            for( Sense se : ent.senses )
                System.out.println("        "+se.name +": " + se.score.toString());
        }
    }

    public static void lesk(String text, List<Entity> Entitys){
        LeskExpert lesk = new LeskExpert();

        List<Entity> resLesk = lesk.predict( text, Entitys );

        printResult(resLesk);
    }

    public static void fuzzy(String text, List<Entity> Entitys) {
        ShorterDistanceExpert sd = new ShorterDistanceExpert();
        LeskExpert lesk = new LeskExpert();
        BabelfyExpert ba = new BabelfyExpert();

        List<Experts> experts = new ArrayList<Experts>();
        experts.add(sd);
        experts.add(lesk);
        experts.add(ba);

        FuzzyBordaExpert fuzz = new FuzzyBordaExpert( experts );

        List<Entity> resF = fuzz.predict( text, Entitys );

        printResult(resF);
    }

    public static void babelfy(String text, List<Entity> Entitys){
        BabelfyExpert ba = new BabelfyExpert();

        List<Entity> resB = ba.predict( text, Entitys );

        printResult(resB);
    }

    public static void distance(String text, List<Entity> Entitys){
        ShorterDistanceExpert sd = new ShorterDistanceExpert();

        List<Entity> resS = sd.predict( text, Entitys );

        printResult(resS);
    }

    public static void main(String[] args) throws Exception {
//        text = "This Friday evening, 1.2 million people took to the streets of Santiago, Chile capital, demanding the Government led by right-wing president Sebastián Piñera radical changes regarding the economic system in one of the OECDs most unequal countries in terms of income.";
//
//        System.out.println("Lesk");
//        lesk(text, Example());
//
//        System.out.println("Babelfy");
//        babelfy(text, Example());
//
//        System.out.println("Shorter Distance");
//        distance(text, Example());
//
//        System.out.println("Fuzzy Borda");
//        fuzzy(text, Example());

        text = "President Diaz Canel gave a speech in Havana on his return from Venezuela, on the conduct ingerencista United States to Cuba";

        System.out.println("Lesk2");
        lesk(text, Example2());

        System.out.println("Babelfy2");
        babelfy(text, Example2());

        System.out.println("Shorter Distance2");
        distance(text, Example2());

        System.out.println("Fuzzy Borda2");
        fuzzy(text, Example2());

        System.out.println("Ok");
    }
}
