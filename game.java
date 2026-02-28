import java.util.Scanner;

public class Game {
    private Deck deck;
    private Hand jugador;
    private Hand computadora;
    private Card cartaMesa;
    private boolean turnoJugador;
    private Scanner scanner;

    public Game() {
        deck = new Deck();
        jugador = new Hand();
        computadora = new Hand();
        turnoJugador = true;
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        for (int i = 0; i < 7; i++) {
            jugador.addCard(deck.drawCard());
            computadora.addCard(deck.drawCard());
        }

        cartaMesa = deck.drawCard();
        System.out.println("Carta inicial en mesa: " + cartaMesa);
        gameLoop();
    }

    private void gameLoop() {
        while (true) {
            if (turnoJugador) {
                playerTurn();
            } else {
                computerTurn();
            }

            if (isGameOver()) {
                break;
            }

            turnoJugador = !turnoJugador;
        }
    }

    private void playerTurn() {
        System.out.println("\nTu turno");
        System.out.println("Carta en mesa: " + cartaMesa);
        jugador.showHand();

        System.out.print("Elige índice de carta o -1 para robar: ");
        int opcion = scanner.nextInt();

        if (opcion == -1) {
            jugador.addCard(deck.drawCard());
            return;
        }

        Card seleccionada = jugador.getCard(opcion);

        if (seleccionada.isPlayable(cartaMesa)) {
            cartaMesa = jugador.playCard(opcion);
            System.out.println("Jugaste: " + cartaMesa);
        } else {
            System.out.println("Carta no válida, robas una.");
            jugador.addCard(deck.drawCard());
        }

        if (jugador.size() == 1) {
            System.out.print("Te queda una carta, escribe UNO: ");
            scanner.nextLine(); // limpiar buffer
            String uno = scanner.nextLine();
            if (!uno.equalsIgnoreCase("UNO")) {
                System.out.println("No dijiste UNO, robas una carta.");
                jugador.addCard(deck.drawCard());
            }
        }
    }

    private void computerTurn() {
        System.out.println("\nTurno de la computadora");

        for (int i = 0; i < computadora.size(); i++) {
            Card c = computadora.getCard(i);
            if (c.isPlayable(cartaMesa)) {
                cartaMesa = computadora.playCard(i);
                System.out.println("La computadora jugó: " + cartaMesa);
                return;
            }
        }

        computadora.addCard(deck.drawCard());
        System.out.println("La computadora robó carta.");
    }

    private boolean isGameOver() {
        if (jugador.size() == 0) {
            System.out.println("¡Jugador gana!");
            return true;
        }
        if (computadora.size() == 0) {
            System.out.println("¡Computadora gana!");
            return true;
        }
        return false;
    }
}
