package experts;

import utils.Entity;
import java.util.List;

public abstract class Experts {

    abstract List<Entity> predict(String text, List<Entity> Entitys);
}









