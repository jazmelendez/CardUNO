public class Card {

    private String color;
    private int number;
    private String type;

    public Card(String color, int number, String type) {
        this.color = color;
        this.number = number;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public boolean isPlayable(Card cartaMesa) {

        if (this.color.equals(cartaMesa.getColor()))
            return true;

        if (this.type.equals("NUMBER") && this.number == cartaMesa.getNumber())
            return true;

        if (this.type.equals("WILD") || this.type.equals("DRAW4"))
            return true;

        return false;
    }

    @Override
    public String toString() {

        if(type.equals("NUMBER")){
            return color + " " + number;
        } else {
            return color + " " + type;
        }
    }
}
