package dk.tandhjulet.elements.expressions.koner;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Kone {
    String name;

    public Kone() {
        List<String> givenList = Arrays.asList("Margerit", "Stefan (trans)", "Sofie", "Sofie nr. 2",
                "Amalie", "Catrine (fed)", "Isabella", "Josefine", "Mie");
        Random rand = new Random();
        name = givenList.get(rand.nextInt(givenList.size()));
    };

    public String getName() {
        return name;
    }
}
