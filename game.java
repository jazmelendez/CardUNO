import java.util.Scanner;

public class Game {

    private Deck deck;
    private Hand jugador;
    private Hand computadora;
    private Card cartaMesa;
    private boolean turnoJugador;
    private Scanner scanner;

    public Game(){
        deck = new Deck();
        jugador = new Hand();
        computadora = new Hand();
        turnoJugador = true;
        scanner = new Scanner(System.in);
    }

    public void startGame(){

        for(int i=0;i<7;i++){
            jugador.addCard(deck.drawCard());
            computadora.addCard(deck.drawCard());
        }

        cartaMesa = deck.drawCard();

        System.out.println("Carta inicial: " + cartaMesa);

        gameLoop();
    }

    private void gameLoop(){

        while(true){

            if(turnoJugador){
                playerTurn();
            }else{
                computerTurn();
            }

            if(isGameOver()){
                break;
            }
        }
    }

    private void playerTurn(){

        System.out.println("\nTu turno");
        System.out.println("Carta en mesa: " + cartaMesa);

        jugador.showHand();

        try{

            System.out.print("Elige carta o -1 para robar: ");
            int opcion = scanner.nextInt();

            if(opcion==-1){
                jugador.addCard(deck.drawCard());
                turnoJugador = false;
                return;
            }

            Card seleccionada = jugador.getCard(opcion);

            if(seleccionada==null){
                System.out.println("Índice inválido");
                return;
            }

            if(seleccionada.isPlayable(cartaMesa)){

                cartaMesa = jugador.playCard(opcion);

                System.out.println("Jugaste: " + cartaMesa);

                if(jugador.size()==1){
                    System.out.print("¡Te queda una carta! Escribe UNO: ");
                    String uno = scanner.next();
                    if(!uno.equalsIgnoreCase("UNO")){
                        System.out.println("No dijiste UNO, robas 2 cartas");
                        jugador.addCard(deck.drawCard());
                        jugador.addCard(deck.drawCard());
                    }
                }

                applyCardEffect(cartaMesa);

            }else{

                System.out.println("Carta no válida, robas una");
                jugador.addCard(deck.drawCard());
                turnoJugador = false;
            }

        }catch(Exception e){
            System.out.println("Entrada inválida");
            scanner.nextLine();
        }
    }

    private void computerTurn(){

        System.out.println("\nTurno computadora");

        for(int i=0;i<computadora.size();i++){

            Card c = computadora.getCard(i);

            if(c.isPlayable(cartaMesa)){

                cartaMesa = computadora.playCard(i);

                System.out.println("Computadora jugó: " + cartaMesa);

                if(computadora.size()==1){
                    System.out.println("Computadora dice UNO");
                }

                applyCardEffect(cartaMesa);

                return;
            }
        }

        computadora.addCard(deck.drawCard());

        System.out.println("Computadora roba carta");

        turnoJugador = true;
    }

    private void applyCardEffect(Card card){

        switch(card.getType()){

            case "SKIP":

                System.out.println("Turno saltado");

                // mismo jugador vuelve a jugar
                break;


            case "DRAW2":

                System.out.println("+2 activado");

                if(turnoJugador){

                    computadora.addCard(deck.drawCard());
                    computadora.addCard(deck.drawCard());

                }else{

                    jugador.addCard(deck.drawCard());
                    jugador.addCard(deck.drawCard());
                }

                break;


            case "DRAW4":

                System.out.println("+4 activado");

                String color4;

                if(turnoJugador){

                    for(int i=0;i<4;i++)
                        computadora.addCard(deck.drawCard());

                    System.out.println("Elige color: 1-Rojo 2-Azul 3-Verde 4-Amarillo");

                    int opcion = scanner.nextInt();

                    color4="Rojo";

                    switch(opcion){
                        case 1: color4="Rojo"; break;
                        case 2: color4="Azul"; break;
                        case 3: color4="Verde"; break;
                        case 4: color4="Amarillo"; break;
                    }

                }else{

                    for(int i=0;i<4;i++)
                        jugador.addCard(deck.drawCard());

                    String[] colores={"Rojo","Azul","Verde","Amarillo"};

                    int random=(int)(Math.random()*4);

                    color4=colores[random];

                    System.out.println("Computadora eligió color: "+color4);
                }

                cartaMesa = new Card(color4,-1,"DRAW4");

                break;


            case "REVERSE":

                System.out.println("Reversa: juegas nuevamente");

                break;


            case "WILD":

                String nuevoColor;

                if(turnoJugador){

                    System.out.println("Elige color: 1-Rojo 2-Azul 3-Verde 4-Amarillo");

                    int opcion = scanner.nextInt();

                    nuevoColor="Rojo";

                    switch(opcion){
                        case 1: nuevoColor="Rojo"; break;
                        case 2: nuevoColor="Azul"; break;
                        case 3: nuevoColor="Verde"; break;
                        case 4: nuevoColor="Amarillo"; break;
                    }

                }else{

                    String[] colores={"Rojo","Azul","Verde","Amarillo"};

                    int random=(int)(Math.random()*4);

                    nuevoColor=colores[random];

                    System.out.println("Computadora eligió color: "+nuevoColor);
                }

                cartaMesa = new Card(nuevoColor,-1,"WILD");

                break;


            default:

                turnoJugador = !turnoJugador;
        }
    }

    private boolean isGameOver(){

        if(jugador.size()==0){
            System.out.println("¡Ganaste!");
            return true;
        }

        if(computadora.size()==0){
            System.out.println("Computadora gana");
            return true;
        }

        return false;
    }
}
