import java.util.*;

public class Deck {
    private List<Card> cartas;

    public Deck() {
        cartas = new ArrayList<>();
        createDeck();
        Collections.shuffle(cartas);
    }

    private void createDeck() {
        String[] colors = {"Rojo", "Azul", "Verde", "Amarillo"};

        for (String color : colors) {
            for (int i = 0; i <= 9; i++) {
                cartas.add(new Card(color, i, "NUMERO"));
                if (i != 0) cartas.add(new Card(color, i, "NUMERO"));
            }

            for (int i = 0; i < 2; i++) {
                cartas.add(new Card(color, -1, "+2"));
                cartas.add(new Card(color, -1, "REVERSA"));
                cartas.add(new Card(color, -1, "SALTO"));
            }
        }

        // +4
        for (int i = 0; i < 4; i++) {
            cartas.add(new Card("Negro", -1, "+4"));
        }
    }

    public Card drawCard() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }
}
