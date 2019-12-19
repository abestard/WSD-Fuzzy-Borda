package utils;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public String name;

    //Lista de Sentidos
    public List<Sense> senses;

    public Entity(String n){
        name = n;
        senses = new ArrayList<Sense>();
    }
}
