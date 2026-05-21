public class Player {

    private Hand hand;
    private String nombre;
    private boolean esHumano;

    public Player(String nombre, boolean esHumano) {
        this.nombre = nombre;
        this.esHumano = esHumano;
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean esHumano() {
        return esHumano;
    }
}
