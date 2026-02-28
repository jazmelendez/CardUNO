import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cartas;

    public Deck() {
        cartas = new ArrayList<>();
        createDeck();
        shuffle();
        validateCardCount();
    }

    private void createDeck() {
        String[] colors = {"Rojo", "Azul", "Verde", "Amarillo"};

        for (String color : colors) {
            for (int number = 0; number <= 9; number++) {
                cartas.add(new Card(color, number));
                if (number != 0) {
                    cartas.add(new Card(color, number));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cartas);
    }

    public Card drawCard() {
        if (!cartas.isEmpty()) {
            return cartas.remove(0);
        }
        System.out.println("Ya no hay cartas en la baraja.");
        return null;
    }

    public int size() {
        return cartas.size();
    }

    private void validateCardCount() {
        if (cartas.size() == 76) {
            System.out.println("La baraja tiene 76 cartas.");
        } else {
            System.out.println("Advertencia: la baraja tiene " + cartas.size() + " cartas.");
        }
    }
}
