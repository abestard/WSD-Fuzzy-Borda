package utils;

public class Sense {
    public String name;

    //Coordenadas
    public Double lat;
    public Double lon;

    public Double score;

    public Sense(String n){
        score = 0.0;
        lat = 0.0;
        lon = 0.0;
        name = n;
    }

    public Sense(String n, Double la, Double lo){
        score = 0.0;
        lat = la;
        lon = lo;
        name = n;
    }
}
