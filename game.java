import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Cerebro de CardUNO con el flujo oficial en sentido de las agujas del reloj.
 * Secuencia estricta de turnos: 0 = Tú (Sur) -> 1 = Piki (Este) -> 2 = Jaz (Norte) -> 3 = Abby (Oeste).
 */
public class Game {
    private List<Hand> jugadores;
    private Deck deck;
    private Card cartaMesa;
    private int turnoActual;
    private int direccion; // 1 = Sentido horario, -1 = Sentido antihorario
    private GameView vista;
    private boolean humanoDeclaroUno;
    private boolean juegoTerminado;
    private Timer timerIA; 

    public Game(GameView vista) {
        this.vista = vista;
        this.deck = new Deck();
        this.jugadores = new ArrayList<>();
        this.direccion = 1; 
        this.turnoActual = 0; 
        this.humanoDeclaroUno = false;
        this.juegoTerminado = false;

        for (int i = 0; i < 4; i++) {
            jugadores.add(new Hand());
        }
    }

    /**
     * FIJADO: Mapeo de índices matemático para respetar los turnos en orden.
     */
    private String obtenerNombreJugador(int id) {
        switch (id) {
            case 0: return "Tu";
            case 1: return "Piki"; // ESTE (Juega justo después de ti)
            case 2: return "Jaz";  // NORTE
            case 3: return "Abby"; // OESTE
            default: return "IA";
        }
    }

    public void startGame() {
        for (int i = 0; i < 7; i++) {
            for (Hand h : jugadores) {
                h.addCard(deck.drawCard());
            }
        }
        do {
            cartaMesa = deck.drawCard();
        } while (cartaMesa.getColor().equalsIgnoreCase("NEGRO"));

        vista.registrarMensaje("¡Partida Iniciada!");
        vista.registrarMensaje("La carta de inicio es: " + cartaMesa);
        
        vista.actualizarTodo(jugadores.get(0), cartaMesa);
    }

    public void jugarCartaHumano(Card cartaSeleccionada, int indice) {
        if (juegoTerminado || (timerIA != null && timerIA.isRunning())) return;

        if (validarJugada(cartaSeleccionada)) {
            if (jugadores.get(0).size() == 2 && !humanoDeclaroUno) {
                vista.registrarMensaje("¡No cantaste UNO! Recibes +2 cartas de castigo.");
                jugadores.get(0).addCard(deck.drawCard());
                jugadores.get(0).addCard(deck.drawCard());
            }

            jugadores.get(0).getCards().remove(indice);
            cartaMesa = cartaSeleccionada;
            vista.registrarMensaje("Jugaste: " + cartaSeleccionada);

            if (jugadores.get(0).size() != 1) {
                humanoDeclaroUno = false;
            }

            if (verificarFinJuego()) return;

            procesarAccionEspecial(cartaSeleccionada);
            
            vista.actualizarTodo(jugadores.get(0), cartaMesa);

            if (!cartaSeleccionada.getColor().equalsIgnoreCase("NEGRO")) {
                ejecutarTurnosIA();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Carta invalida. Debe coincidir el color o el numero.");
        }
    }

    private boolean validarJugada(Card c) {
        return c.isPlayable(cartaMesa);
    }

    public void declararUnoHumano() {
        if (jugadores.get(0).size() == 1 || jugadores.get(0).size() == 2) {
            humanoDeclaroUno = true;
            vista.registrarMensaje("📣 ¡Gritaste UNO con exito!");
        }
    }

    private boolean verificarFinJuego() {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).size() == 0) {
                juegoTerminado = true;
                if (timerIA != null) timerIA.stop(); 
                
                String ganador = obtenerNombreJugador(i);
                vista.registrarMensaje("\n=================================");
                vista.registrarMensaje("¡PARTIDA CONCLUIDA CON EXITO!");
                vista.registrarMensaje("Ganador definitivo: " + (i == 0 ? "¡Haz ganado tú! 🎉" : ganador));
                vista.registrarMensaje("=================================\n");
                
                vista.actualizarTodo(jugadores.get(0), cartaMesa);
                JOptionPane.showMessageDialog(null, "¡Juego Terminado! El ganador es: " + ganador);
                return true;
            }
        }
        return false;
    }

    private void procesarAccionEspecial(Card c) {
        String tipo = c.getTipo().toUpperCase();

        switch (tipo) {
            case "SALTO":
                avanzarTurno(); 
                vista.registrarMensaje("--> ¡SALTO! Se ha saltado el turno de " + obtenerNombreJugador(turnoActual));
                break;
                
            case "REVERSA":
                direccion *= -1; 
                vista.registrarMensaje("--> ¡REVERSA! El sentido de la ronda ahora es: " + (direccion == 1 ? "Horario (Piki)" : "Antihorario (Abby)"));
                break;
                
            case "+2":
                int castigado2 = (turnoActual + direccion + 4) % 4;
                vista.registrarMensaje("--> ¡+2! " + obtenerNombreJugador(castigado2) + " recibe 2 cartas y pierde su turno.");
                darCartasA(castigado2, 2);
                avanzarTurno(); 
                break;
                
            case "WILD":
            case "+4":
                if (turnoActual == 0) {
                    vista.activarSeleccionColor(true);
                    vista.registrarMensaje("--> Selecciona un color en los botones superiores...");
                } else {
                    String colorElegido = elegirColorAutomaticoIA(turnoActual);
                    cartaMesa = new Card(colorElegido, -1, "COLOR_CAMBIADO");
                    vista.registrarMensaje("  " + obtenerNombreJugador(turnoActual) + " cambio el color a: " + colorElegido);
                }
                
                if (tipo.equals("+4")) {
                    int castigado4 = (turnoActual + direccion + 4) % 4;
                    vista.registrarMensaje("--> ¡+4! " + obtenerNombreJugador(castigado4) + " recibe 4 cartas y pierde su turno.");
                    darCartasA(castigado4, 4);
                    avanzarTurno();
                }
                break;
            default: 
                break;
        }
        avanzarTurno(); 
    }

    private String elegirColorAutomaticoIA(int jugadorIA) {
        Hand mano = jugadores.get(jugadorIA);
        int r = 0, az = 0, v = 0, am = 0;
        for (int i = 0; i < mano.size(); i++) {
            String col = mano.getCard(i).getColor().toUpperCase();
            if (col.equals("ROJO")) r++;
            else if (col.equals("AZUL")) az++;
            else if (col.equals("VERDE")) v++;
            else if (col.equals("AMARILLO")) am++;
        }
        if (r >= az && r >= v && r >= am) return "ROJO";
        if (az >= r && az >= v && az >= am) return "AZUL";
        if (v >= r && v >= az && v >= am) return "VERDE";
        return "AMARILLO";
    }

    private void avanzarTurno() {
        turnoActual = (turnoActual + direccion + 4) % 4;
    }

    private void darCartasA(int destino, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            jugadores.get(destino).addCard(deck.drawCard());
        }
    }

    public void robarCartaHumano() {
        if (juegoTerminado || (timerIA != null && timerIA.isRunning())) return;
        
        Card robada = deck.drawCard();
        jugadores.get(0).addCard(robada);
        vista.registrarMensaje("Robaste una carta de la baraja.");
        humanoDeclaroUno = false;
        
        avanzarTurno();
        vista.actualizarTodo(jugadores.get(0), cartaMesa);
        ejecutarTurnosIA();
    }

    public void cambiarColorMesa(String nuevoColor) {
        cartaMesa = new Card(nuevoColor, -1, "COLOR_CAMBIADO");
        vista.registrarMensaje("Estableciste el color de la mesa en: " + nuevoColor);
        vista.activarSeleccionColor(false);
        
        ejecutarTurnosIA();
        vista.actualizarTodo(jugadores.get(0), cartaMesa);
    }

    private void ejecutarTurnosIA() {
        if (juegoTerminado) return;

        if (timerIA != null && timerIA.isRunning()) {
            timerIA.stop();
        }

        timerIA = new Timer(1500, e -> {
            if (turnoActual == 0 || juegoTerminado) {
                ((Timer)e.getSource()).stop();
                if (!juegoTerminado) {
                    vista.registrarMensaje("Es tu turno.");
                }
                vista.actualizarTodo(jugadores.get(0), cartaMesa); 
                return;
            }

            Hand manoIA = jugadores.get(turnoActual);
            String nombreIA = obtenerNombreJugador(turnoActual);
            boolean jugo = false;
            vista.registrarMensaje("Turno de " + nombreIA + "...");

            for (int i = 0; i < manoIA.size(); i++) {
                if (validarJugada(manoIA.getCard(i))) {
                    Card c = manoIA.getCards().remove(i);
                    cartaMesa = c;
                    vista.registrarMensaje("  " + nombreIA + " descarto: " + c);
                    
                    if (manoIA.size() == 1) {
                        vista.registrarMensaje("  📣 " + nombreIA + " grita: ¡UNO!");
                    }

                    if (verificarFinJuego()) {
                        ((Timer)e.getSource()).stop();
                        return;
                    }

                    procesarAccionEspecial(c);
                    jugo = true;
                    break;
                }
            }

            if (!jugo) {
                manoIA.addCard(deck.drawCard());
                vista.registrarMensaje("  " + nombreIA + " no tenia cartas utiles y robo.");
                avanzarTurno();
            }
            
            vista.actualizarTodo(jugadores.get(0), cartaMesa);
        });

        timerIA.setInitialDelay(800); 
        timerIA.start();
    }

    public List<Hand> getJugadores() { return jugadores; }
    public Card getCartaMesa() { return cartaMesa; }
}
