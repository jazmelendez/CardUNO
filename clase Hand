import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> mano;

    public Hand() {
        mano = new ArrayList<>();
    }

    public void addCard(Card c) {
        mano.add(c);
    }

    public Card playCard(int index) {
        if (index >= 0 && index < mano.size()) {
            return mano.remove(index);
        }
        return null;
    }

    public boolean hasPlayableCard(Card cartaMesa) {
        for (Card c : mano) {
            if (c.isPlayable(cartaMesa)) {
                return true;
            }
        }
        return false;
    }

    public void showHand() {
        for (int i = 0; i < mano.size(); i++) {
            System.out.println(i + ": " + mano.get(i));
        }
    }

    public int size() {
        return mano.size();
    }

    public Card getCard(int index) {
        return mano.get(index);
    }
}
