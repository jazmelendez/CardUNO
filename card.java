/**
 * Representa una carta individual del juego CardUNO.
 * Almacena las propiedades basicas como color, numero y tipo.
 * @author Alejandro Hernandez
 * @author Abigail Martinez
 * @author Jazmin Melendez
 */
public class Card {

    private String color;
    private int number;
    private String tipo; // NUMERO, +2, +4, REVERSA, SALTO, WILD

    /**
     * Constructor para crear una nueva carta.
     * @param color Color de la carta (Rojo, Azul, etc.)
     * @param number Valor numerico de la carta (-1 para especiales)
     * @param tipo Categoria de la carta (+2, SALTO, etc.)
     */
    public Card(String color, int number, String tipo) {
        this.color = color;
        this.number = number;
        this.tipo = tipo;
    }

    public String getColor() { return color; }
    public int getNumber() { return number; }
    public String getTipo() { return tipo; }

    /**
     * Genera la ruta exacta del archivo .png asociado a esta carta.
     * Convierte internamente los nombres a los archivos en ingles para la interfaz.
     */
    public String getRutaImagen() {
        String colorIngles = this.color.toLowerCase();
        
        // Sincronizacion de nombres de color con los archivos del mazo
        switch (this.color.toUpperCase()) {
            case "ROJO": colorIngles = "red"; break;
            case "AZUL": colorIngles = "blue"; break;
            case "VERDE": colorIngles = "green"; break;
            case "AMARILLO": colorIngles = "yellow"; break;
            case "NEGRO": colorIngles = "black"; break;
        }

        String tipoIngles = this.tipo.toLowerCase();
        
        // Traducimos los tipos especiales a la nomenclatura del archivo .png
        switch (this.tipo.toUpperCase()) {
            case "SALTO": tipoIngles = "skip"; break;
            case "REVERSA": tipoIngles = "reverse"; break;
            case "+2": tipoIngles = "draw2"; break;
            case "WILD": tipoIngles = "wild"; break;
            case "+4": tipoIngles = "draw4"; break;
        }

        // Armamos la ruta omitiendo el prefijo src/ para evitar conflictos desde la terminal
        if (this.tipo.equalsIgnoreCase("NUMERO")) {
            return "img/" + colorIngles + "_" + this.number + ".png";
        } else {
            return "img/" + colorIngles + "_" + tipoIngles + ".png";
        }
    }

    /**
     * Determina si esta carta puede ser jugada sobre otra.
     * @param cartaMesa La carta que esta actualmente en el mazo de descarte.
     * @return true si la jugada es valida segun las reglas del UNO.
     */
    public boolean isPlayable(Card cartaMesa) {
        if (this.color.equalsIgnoreCase("NEGRO") || this.tipo.equalsIgnoreCase("+4") || this.tipo.equalsIgnoreCase("WILD")) {
            return true;
        }
        if (cartaMesa.getColor().equalsIgnoreCase("COLOR_CAMBIADO")) {
            return true; 
        }
        if (this.color.equalsIgnoreCase(cartaMesa.getColor())) {
            return true;
        }
        if (this.tipo.equalsIgnoreCase("NUMERO") && cartaMesa.getTipo().equalsIgnoreCase("NUMERO")) {
            return this.number == cartaMesa.getNumber();
        }
        if (!this.tipo.equalsIgnoreCase("NUMERO") && this.tipo.equalsIgnoreCase(cartaMesa.getTipo())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (tipo.equalsIgnoreCase("NUMERO")) return color + " " + number;
        else return color + " " + tipo;
    }
}
