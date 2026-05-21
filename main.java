/**
 * Clase principal que sirve como punto de entrada para la aplicación CardUNO.
 * En esta versión, se encarga de iniciar la interfaz gráfica (GameView).
 * @author Alejandro Hernandez
 * @author Abigail Martinez
 * @author Jazmin Melendez
 */
public class Main {
    /**
     * Método principal que arranca la aplicación.
     * Utiliza InvokeLater para asegurar que la interfaz se inicie correctamente 
     * en el hilo de eventos de Java Swing.
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Esto le dice a Java que prepare la ventana de forma segura
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Creamos y mostramos la ventana que configuramos con los 5 paneles
                new GameView();
            }
        });
    }
}
