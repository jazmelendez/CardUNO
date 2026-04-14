import java.util.*;

public class Game {

    private List<Hand> jugadores;
    private Deck deck;
    private Card cartaMesa;

    private int turnoActual;
    private int direccion;

    private Scanner scanner;
    private String nombreJugador;

    public Game() {
        deck = new Deck();
        jugadores = new ArrayList<>();
        scanner = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        nombreJugador = scanner.nextLine();

        for (int i = 0; i < 4; i++) {
            jugadores.add(new Hand());
        }

        turnoActual = 0;
        direccion = 1;
    }

    public void startGame() {

        for (int i = 0; i < 7; i++) {
            for (Hand h : jugadores) {
                h.addCard(deck.drawCard());
            }
        }

        cartaMesa = deck.drawCard();

        gameLoop();
    }

    private void gameLoop() {

        while (true) {

            mostrarEstado();

            if (turnoActual == 0) {
                turnoJugador();
            } else {
                turnoIA(turnoActual);
            }

            if (isGameOver()) break;
        }
    }

    private void turnoJugador() {

        System.out.println("\nTu turno");
        jugadores.get(0).showHand();

        System.out.print("Elige carta o -1 para robar: ");
        int opcion = scanner.nextInt();

        if (opcion == -1) {
            jugadores.get(0).addCard(deck.drawCard());
            avanzarTurno();
            return;
        }

        Hand jugador = jugadores.get(0);

        if (opcion < 0 || opcion >= jugador.size()) {
            System.out.println("Entrada inválida");
            return;
        }

        Card c = jugador.getCard(opcion);

        if (c.isPlayable(cartaMesa)) {

            cartaMesa = jugador.playCard(opcion);

            verificarUNO(0);

            aplicarEfecto(cartaMesa, 0);

        } else {

            System.out.println("No válida, robas carta");
            jugador.addCard(deck.drawCard());
            avanzarTurno();
        }
    }

    private void turnoIA(int i) {

        Hand cpu = jugadores.get(i);
        System.out.println("\nTurno IA " + i);

        for (int j = 0; j < cpu.size(); j++) {

            Card c = cpu.getCard(j);

            if (c.isPlayable(cartaMesa)) {

                cartaMesa = cpu.playCard(j);

                System.out.println("IA jugó: " + cartaMesa);

                verificarUNO(i);

                aplicarEfecto(cartaMesa, i);
                return;
            }
        }

        Card robada = deck.drawCard();

        if (robada != null && robada.isPlayable(cartaMesa)) {

            cartaMesa = robada;

            System.out.println("IA robó y jugó: " + cartaMesa);

            verificarUNO(i);

            aplicarEfecto(cartaMesa, i);

        } else {

            cpu.addCard(robada);
            System.out.println("IA roba y pasa");
            avanzarTurno();
        }
    }

    private void aplicarEfecto(Card carta, int jugadorQueJugo) {

        int siguiente = (turnoActual + direccion + jugadores.size()) % jugadores.size();

        switch (carta.getTipo()) {

            case "+2":

                jugadores.get(siguiente).addCard(deck.drawCard());
                jugadores.get(siguiente).addCard(deck.drawCard());

                System.out.println("Jugador " + siguiente + " roba 2");

                turnoActual = (siguiente + direccion + jugadores.size()) % jugadores.size();
                break;

            case "+4":

                for (int i = 0; i < 4; i++) {
                    jugadores.get(siguiente).addCard(deck.drawCard());
                }

                System.out.println("Jugador " + siguiente + " roba 4");

                String color;

                if (jugadorQueJugo == 0) {

                    System.out.println("Elige color: 1-Rojo 2-Azul 3-Verde 4-Amarillo");
                    int op = scanner.nextInt();

                    color = "Rojo";

                    switch (op) {
                        case 2: color = "Azul"; break;
                        case 3: color = "Verde"; break;
                        case 4: color = "Amarillo"; break;
                    }

                } else {

                    String[] colores = {"Rojo","Azul","Verde","Amarillo"};
                    color = colores[new Random().nextInt(4)];

                    System.out.println("IA eligió color: " + color);
                }

                cartaMesa = new Card(color, -1, "+4");

                turnoActual = (siguiente + direccion + jugadores.size()) % jugadores.size();
                break;

            case "SALTO":

                turnoActual = (siguiente + direccion + jugadores.size()) % jugadores.size();
                System.out.println("Jugador saltado");
                break;

            case "REVERSA":

                direccion *= -1;
                turnoActual = siguiente;
                System.out.println("Dirección invertida");
                break;

            case "WILD":

                String nuevoColor;

                if (jugadorQueJugo == 0) {

                    System.out.println("Elige color: 1-Rojo 2-Azul 3-Verde 4-Amarillo");
                    int op = scanner.nextInt();

                    nuevoColor = "Rojo";

                    switch (op) {
                        case 2: nuevoColor = "Azul"; break;
                        case 3: nuevoColor = "Verde"; break;
                        case 4: nuevoColor = "Amarillo"; break;
                    }

                } else {

                    String[] colores = {"Rojo","Azul","Verde","Amarillo"};
                    nuevoColor = colores[new Random().nextInt(4)];

                    System.out.println("IA eligió color: " + nuevoColor);
                }

                cartaMesa = new Card(nuevoColor, -1, "WILD");

                turnoActual = siguiente;
                break;

            default:

                turnoActual = siguiente;
        }
    }

    private void avanzarTurno() {
        turnoActual = (turnoActual + direccion + jugadores.size()) % jugadores.size();
    }

    private boolean isGameOver() {

        for (int i = 0; i < jugadores.size(); i++) {

            if (jugadores.get(i).size() == 0) {

                if (i == 0) {
                    System.out.println("¡" + nombreJugador + " ha ganado!");
                } else {
                    System.out.println("¡IA " + i + " ha ganado!");
                }

                return true;
            }
        }

        return false;
    }

    private void mostrarEstado() {

        System.out.println("\n===== ESTADO =====");
        System.out.println("Carta en mesa: " + cartaMesa);

        if (turnoActual == 0) {
            System.out.println("Turno: " + nombreJugador);
        } else {
            System.out.println("Turno: IA " + turnoActual);
        }

        System.out.println(nombreJugador + ": " + jugadores.get(0).size() + " cartas");
    }

    private void verificarUNO(int jugadorIndex) {

        if (jugadores.get(jugadorIndex).size() == 1) {

            if (jugadorIndex == 0) {
                System.out.println("¡UNO! (" + nombreJugador + ")");
            } else {
                System.out.println("¡UNO! (IA " + jugadorIndex + ")");
            }
        }
    }
}
