public class Card {
    private String color;
    private int number;

    public Card(String color, int number) {
        this.color = color;
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public boolean isPlayable(Card cartaMesa) {
        return this.color.equals(cartaMesa.color) ||
               this.number == cartaMesa.number;
    }

    @Override
    public String toString() {
        return color + " " + number;
    }
}
