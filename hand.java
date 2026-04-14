import java.util.*;

public class Hand {
    private List<Card> mano;

    public Hand() {
        mano = new ArrayList<>();
    }

    public void addCard(Card c) {
        if (c != null) mano.add(c);
    }

    public Card playCard(int index) {
        return mano.remove(index);
    }

    public Card getCard(int index) {
        if (index >= 0 && index < mano.size()) {
            return mano.get(index);
        }
        return null;
    }

    public int size() {
        return mano.size();
    }

    public void showHand() {
        for (int i = 0; i < mano.size(); i++) {
            System.out.println(i + ": " + mano.get(i));
        }
    }
}
