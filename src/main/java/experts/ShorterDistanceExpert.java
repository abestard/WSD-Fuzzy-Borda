package experts;

import utils.Entity;
import utils.Sense;

import java.util.List;
import org.gavaghan.geodesy.*;

public class ShorterDistanceExpert extends Experts {
    private List<Entity> Ent;
    private double score = 1.0;
    private int[] arr;
    private int[] tmp;
    private int[] res;
    private double val;
    private int L;

    private double distance( Sense s1, Sense s2 ){
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;

        GlobalPosition point1 = new GlobalPosition(s1.lat, s1.lon, 0.0); // Point 1
        GlobalPosition point2 = new GlobalPosition(s2.lat, s2.lon, 0.0);// Point 2

        double dist = geoCalc.calculateGeodeticCurve(reference, point1, point2).getEllipsoidalDistance(); // Distance between Point 1 and Point 2

        return dist;
    }

    private double calcCost( int pos ){
        Sense s = Ent.get(pos).senses.get( tmp[pos] );
        double cost = 0;
        for( int i = 0; i < pos; i ++ ){
            Sense s1 = Ent.get(i).senses.get( tmp[i] );
            cost += distance( s1, s );
        }
        return cost;
    }

    private void comb( int pos, double cost ){
        if( pos == L ){
            if( cost < val ){
                val = cost;
                for( int i = 0; i < L; i ++ ) res[i] = tmp[i];
            }
            return;
        }
        for( int i = 0; i < arr[pos]; i ++ ){
            tmp[pos] = i;
            comb( pos + 1, cost + calcCost( pos ) );
        }
    }

    public List<Entity> predict(String text, List<Entity> Entitys) {
        Ent = Entitys;
        L = Entitys.size();
        arr = new int[L];
        tmp = new int[L];
        res = new int[L];
        val = Double.MAX_VALUE;
        for( int i = 0; i < L; i ++ )
            arr[i] = Entitys.get(i).senses.size();


        comb( 0, 0.0 );

        int id = 0;
        for( Entity ent : Entitys ){
            int ids = 0;
            for(Sense s : ent.senses ){
                s.score = 0.0;
                if( res[id] == ids ) s.score = score;
                ids += 1;
            }
            id += 1;
        }

        return Entitys;
    }
}


//    /*
//     * Calculate distance between two points in latitude and longitude taking
//     * into account height difference. If you are not interested in height
//     * difference pass 0.0. Uses Haversine method as its base.
//     *
//     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
//     * el2 End altitude in meters
//     * @returns Distance in Meters
//     */
//    public static double distance(double lat1, double lat2, double lon1,
//                                  double lon2, double el1, double el2) {
//
//        final int R = 6371; // Radius of the earth
//
//        Double latDistance = Math.toRadians(lat2 - lat1);
//        Double lonDistance = Math.toRadians(lon2 - lon1);
//        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c * 1000; // convert to meters
//
//        double height = el1 - el2;
//
//        distance = Math.pow(distance, 2) + Math.pow(height, 2);
//
//        return Math.sqrt(distance);
//    }
