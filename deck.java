import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cartas;

    public Deck() {
        cartas = new ArrayList<>();
        createDeck();
        shuffle();
    }

    private void createDeck() {

        String[] colors = {"Rojo","Azul","Verde","Amarillo"};

        for(String color : colors){

            for(int number=0; number<=9; number++){

                cartas.add(new Card(color,number,"NUMBER"));

                if(number!=0){
                    cartas.add(new Card(color,number,"NUMBER"));
                }
            }

            cartas.add(new Card(color,-1,"SKIP"));
            cartas.add(new Card(color,-1,"REVERSE"));
            cartas.add(new Card(color,-1,"DRAW2"));
        }

        for(int i=0;i<4;i++){
            cartas.add(new Card("Negro",-1,"WILD"));
            cartas.add(new Card("Negro",-1,"DRAW4"));
        }
    }

    public void shuffle() {
        Collections.shuffle(cartas);
    }

    public Card drawCard() {

        if(cartas.isEmpty()){
            throw new RuntimeException("La baraja se quedó sin cartas");
        }

        return cartas.remove(0);
    }

    public int size(){
        return cartas.size();
    }
}
