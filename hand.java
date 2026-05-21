import java.util.*;

/**
 * Representa el conjunto de cartas que posee un jugador (humano o IA).
 */
public class Hand {
    private List<Card> mano;

    public Hand() {
        mano = new ArrayList<>();
    }

    /**
     * Agrega una carta a la mano del jugador.
     * @param c La carta a añadir.
     */
    public void addCard(Card c) {
        if (c != null) {
            mano.add(c);
        }
    }

    /**
     * Juega una carta de la mano según su posición.
     * @param index Índice de la carta en la lista.
     * @return La carta seleccionada para jugar.
     */
    public Card playCard(int index) {
        if (index >= 0 && index < mano.size()) {
            return mano.remove(index);
        }
        return null;
    }

    /**
     * Obtiene una carta específica sin quitarla de la mano.
     */
    public Card getCard(int index) {
        if (index >= 0 && index < mano.size()) {
            return mano.get(index);
        }
        return null;
    }

    // --- MÉTODOS DE CONEXIÓN (IMPORTANTES PARA LA INTERFAZ) ---

    /**
     * Devuelve la lista completa de cartas. 
     * Este es el "getCards" que pide el error en Game.java.
     */
    public List<Card> getCards() {
        return this.mano;
    }

    /**
     * Devuelve cuántas cartas tiene el jugador actualmente.
     */
    public int size() {
        return this.mano.size();
    }
}
