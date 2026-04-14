public class Card {

    private String color;
    private int number;
    private String tipo; // NUMERO, +2, +4, REVERSA, SALTO, WILD

    public Card(String color, int number, String tipo) {
        this.color = color;
        this.number = number;
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isPlayable(Card cartaMesa) {

        // comodines siempre se pueden jugar
        if (this.tipo.equals("+4") || this.tipo.equals("WILD")) {
            return true;
        }

        // mismo color
        if (this.color.equals(cartaMesa.color)) {
            return true;
        }

        // números iguales
        if (this.tipo.equals("NUMERO") && cartaMesa.tipo.equals("NUMERO")) {
            return this.number == cartaMesa.number;
        }

        // cartas especiales iguales
        if (!this.tipo.equals("NUMERO") && this.tipo.equals(cartaMesa.tipo)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        if (tipo.equals("NUMERO")) {
            return color + " " + number;
        } else {
            return color + " " + tipo;
        }
    }
}

