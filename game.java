import java.util.*;

public class Game {

    private List<Player> jugadores;
    private Deck deck;
    private Card cartaMesa;

    private TurnManager turnManager;
    private RuleEngine ruleEngine;

    private Scanner scanner;

    public Game() {

        scanner = new Scanner(System.in);
        deck = new Deck();
        jugadores = new ArrayList<>();

        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();

        jugadores.add(new Player(nombre, true));

        for (int i = 1; i < 4; i++) {
            jugadores.add(new Player("IA " + i, false));
        }

        turnManager = new TurnManager(jugadores.size());
        ruleEngine = new RuleEngine();
    }

    public void startGame() {

        for (int i = 0; i < 7; i++) {
            for (Player p : jugadores) {
                p.getHand().addCard(deck.drawCard());
            }
        }

        cartaMesa = deck.drawCard();

        gameLoop();
    }

    private void gameLoop() {

        while (true) {

            mostrarEstado();

            int turno = turnManager.getTurnoActual();

            if (jugadores.get(turno).esHumano()) {
                turnoJugador();
            } else {
                turnoIA(turno);
            }

            if (isGameOver()) break;

            turnManager.siguienteTurno();
        }
    }

    private void turnoJugador() {

        Player jugador = jugadores.get(0);

        jugador.getHand().showHand();

        System.out.print("Elige carta o -1 para robar: ");
        int op = scanner.nextInt();

        if (op == -1) {
            jugador.getHand().addCard(deck.drawCard());
            return;
        }

        Card c = jugador.getHand().getCard(op);

        if (c != null && c.isPlayable(cartaMesa)) {

            cartaMesa = jugador.getHand().playCard(op);

            verificarUNO(0);

            ruleEngine.aplicarEfecto(cartaMesa, this, 0);

        } else {
            System.out.println("Movimiento inválido");
            jugador.getHand().addCard(deck.drawCard());
        }
    }

    private void turnoIA(int i) {

        Player cpu = jugadores.get(i);

        for (int j = 0; j < cpu.getHand().size(); j++) {

            Card c = cpu.getHand().getCard(j);

            if (c.isPlayable(cartaMesa)) {

                cartaMesa = cpu.getHand().playCard(j);

                System.out.println(cpu.getNombre() + " jugó: " + cartaMesa);

                verificarUNO(i);

                ruleEngine.aplicarEfecto(cartaMesa, this, i);
                return;
            }
        }

        cpu.getHand().addCard(deck.drawCard());
        System.out.println(cpu.getNombre() + " roba carta");
    }

    public void robarASiguiente(int cantidad) {

        int siguiente = (turnManager.getTurnoActual() + 1) % jugadores.size();

        for (int i = 0; i < cantidad; i++) {
            jugadores.get(siguiente).getHand().addCard(deck.drawCard());
        }

        System.out.println(jugadores.get(siguiente).getNombre() + " roba " + cantidad);
    }

    public void saltarTurno() {
        turnManager.saltarTurno();
    }

    public void reversa() {
        turnManager.reversa();
    }

    public void setCartaMesa(Card c) {
        cartaMesa = c;
    }

    private void verificarUNO(int i) {

        if (jugadores.get(i).getHand().size() == 1) {
            System.out.println("¡UNO! (" + jugadores.get(i).getNombre() + ")");
        }
    }

    private boolean isGameOver() {

        for (Player p : jugadores) {
            if (p.getHand().size() == 0) {
                System.out.println("Ganador: " + p.getNombre());
                return true;
            }
        }
        return false;
    }

    private void mostrarEstado() {

        System.out.println("\nCarta en mesa: " + cartaMesa);

        int turno = turnManager.getTurnoActual();

        System.out.println("Turno: " + jugadores.get(turno).getNombre());
    }
}
