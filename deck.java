import java.util.*;

/**
 * Gestiona el mazo de cartas del juego.
 * Se encarga de la creación, mezcla y reparto de cartas.
 */
public class Deck {
    private List<Card> cartas;

    /**
     * Inicializa un nuevo mazo, crea las cartas y las mezcla.
     */
    public Deck() {
        cartas = new ArrayList<>();
        createDeck();
        Collections.shuffle(cartas);
    }

    /**
     * Genera todas las cartas estándar y especiales del juego UNO.
     */
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
        for (int i = 0; i < 4; i++) {
            cartas.add(new Card("Negro", -1, "+4"));
        }
    }

    /**
     * Retira la carta superior del mazo para entregarla a un jugador.
     * @return El objeto Card retirado, o null si el mazo está vacío.
     */
    public Card drawCard() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }
}
