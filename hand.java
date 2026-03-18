import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> mano;

    public Hand(){
        mano = new ArrayList<>();
    }

    public void addCard(Card c){

        if(c == null){
            System.out.println("No se pudo agregar carta (carta nula)");
            return;
        }

        mano.add(c);
    }

    public Card playCard(int index){

        if(index < 0 || index >= mano.size()){
            System.out.println("Índice inválido");
            return null;
        }

        return mano.remove(index);
    }

    public void showHand(){

        System.out.println("Tus cartas:");

        for(int i = 0; i < mano.size(); i++){
            System.out.println("[" + i + "] " + mano.get(i));
        }
    }

    public int size(){
        return mano.size();
    }

    public Card getCard(int index){

        if(index < 0 || index >= mano.size()){
            return null;
        }

        return mano.get(index);
    }
}
