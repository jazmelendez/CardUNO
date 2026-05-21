import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Interfaz gráfica estilizada para CardUNO.
 * Sincronizada con el orden de índices: 1 = Piki (Este), 2 = Jaz (Norte), 3 = Abby (Oeste).
 */
public class GameView extends JFrame {

    private JPanel panelNorte, panelSur, panelEste, panelOeste, panelCentro;
    private JPanel panelColores;
    private JButton btnRojo, btnAzul, btnVerde, btnAmarillo, btnRobar, btnUno;
    private JLabel lblCartaMesa;
    private JTextArea txtHistorial;
    private Game game;

    private final Color COLOR_FONDO_TABLERO = new Color(110, 44, 153); 
    private final Color COLOR_ZONAS_JUGADOR = new Color(142, 68, 173); 
    private final Color COLOR_CONSOLA = new Color(44, 12, 68); 
    private final Font FUENTE_INTERFAZ = new Font("Segoe UI", Font.BOLD, 14);

    public GameView() {
        setTitle("CardUNO - Entrega Final");
        setSize(1250, 920); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO_TABLERO);

        configurarPanelesJugadores();
        construirCentro();
        
        game = new Game(this);
        game.startGame();

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void configurarPanelesJugadores() {
        panelNorte = new JPanel();
        panelNorte.setBackground(COLOR_FONDO_TABLERO);
        panelNorte.setPreferredSize(new Dimension(100, 160)); 
        panelNorte.setBorder(crearBordeEstilizado(" JAZ "));
        
        panelSur = new JPanel();
        panelSur.setBackground(COLOR_ZONAS_JUGADOR);
        panelSur.setPreferredSize(new Dimension(100, 220));
        panelSur.setBorder(crearBordeEstilizado(" TU MANO "));
        
        panelEste = new JPanel();
        panelEste.setBackground(COLOR_FONDO_TABLERO);
        panelEste.setPreferredSize(new Dimension(130, 100)); 
        panelEste.setBorder(crearBordeEstilizado(" PIKI "));
        
        panelOeste = new JPanel();
        panelOeste.setBackground(COLOR_FONDO_TABLERO);
        panelOeste.setPreferredSize(new Dimension(130, 100));
        panelOeste.setBorder(crearBordeEstilizado(" ABBY "));
        
        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(COLOR_FONDO_TABLERO);

        add(panelNorte, BorderLayout.NORTH);
        add(panelSur, BorderLayout.SOUTH);
        add(panelEste, BorderLayout.EAST);
        add(panelOeste, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
    

        add(panelNorte, BorderLayout.NORTH);
        add(panelSur, BorderLayout.SOUTH);
        add(panelEste, BorderLayout.EAST);
        add(panelOeste, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
    }

    private TitledBorder crearBordeEstilizado(String titulo) {
        TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), titulo);
        borde.setTitleFont(FUENTE_INTERFAZ);
        borde.setTitleColor(Color.WHITE);
        borde.setTitleJustification(TitledBorder.CENTER);
        return borde;
    }

    private void construirCentro() {
        panelColores = new JPanel();
        panelColores.setBackground(COLOR_FONDO_TABLERO);
        
        btnRojo = crearBotonColor("Rojo", new Color(231, 76, 60));
        btnAzul = crearBotonColor("Azul", new Color(52, 152, 219));
        btnVerde = crearBotonColor("Verde", new Color(46, 204, 113));
        btnAmarillo = crearBotonColor("Amarillo", new Color(241, 196, 15));

        panelColores.add(btnRojo);
        panelColores.add(btnAzul);
        panelColores.add(btnVerde);
        panelColores.add(btnAmarillo);
        
        activarSeleccionColor(false);

        btnUno = new JButton("¡UNO!");
        btnUno.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnUno.setBackground(new Color(230, 126, 34)); 
        btnUno.setForeground(Color.WHITE);
        btnUno.setFocusPainted(false);
        btnUno.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnUno.addActionListener(e -> game.declararUnoHumano());

        btnRobar = new JButton();
        btnRobar.setContentAreaFilled(false);
        btnRobar.setBorderPainted(false);
        btnRobar.setFocusPainted(false);
        
        ImageIcon iconoMazo = new ImageIcon("img/back.png");
        Image imgMazo = iconoMazo.getImage().getScaledInstance(150, 175, Image.SCALE_SMOOTH);
        btnRobar.setIcon(new ImageIcon(imgMazo));
        btnRobar.addActionListener(e -> game.robarCartaHumano());

        lblCartaMesa = new JLabel("", SwingConstants.CENTER);
        lblCartaMesa.setPreferredSize(new Dimension(160, 180));

        txtHistorial = new JTextArea(5, 30);
        txtHistorial.setEditable(false);
        txtHistorial.setBackground(COLOR_CONSOLA);
        txtHistorial.setForeground(new Color(235, 220, 245)); 
        txtHistorial.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollHistorial = new JScrollPane(txtHistorial);
        scrollHistorial.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelAccionesAbajo = new JPanel(new BorderLayout());
        panelAccionesAbajo.setBackground(COLOR_FONDO_TABLERO);
        panelAccionesAbajo.add(btnUno, BorderLayout.WEST);
        panelAccionesAbajo.add(scrollHistorial, BorderLayout.CENTER);

        JPanel panelTableroCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        panelTableroCentral.setBackground(COLOR_FONDO_TABLERO);
        panelTableroCentral.add(lblCartaMesa);
        panelTableroCentral.add(btnRobar);

        panelCentro.add(panelColores, BorderLayout.NORTH);
        panelCentro.add(panelAccionesAbajo, BorderLayout.SOUTH);
        panelCentro.add(panelTableroCentral, BorderLayout.CENTER);
    }

    private JButton crearBotonColor(String nombre, Color color) {
        JButton btn = new JButton(nombre);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(FUENTE_INTERFAZ);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        btn.addActionListener(e -> game.cambiarColorMesa(nombre.toUpperCase()));
        return btn;
    }

    public void activarSeleccionColor(boolean estado) {
        btnRojo.setVisible(estado);
        btnAzul.setVisible(estado);
        btnVerde.setVisible(estado);
        btnAmarillo.setVisible(estado);
        if (panelColores != null) {
            panelColores.revalidate();
            panelColores.repaint();
        }
    }

    public void registrarMensaje(String texto) {
        txtHistorial.append(texto + "\n");
        txtHistorial.setCaretPosition(txtHistorial.getDocument().getLength());
    }

    private ImageIcon obtenerIconoRotado(String ruta, int anchoDestino, int altoDestino) {
        ImageIcon iconoOriginal = new ImageIcon(ruta);
        Image imgOriginal = iconoOriginal.getImage();
        
        BufferedImage bi = new BufferedImage(imgOriginal.getWidth(null), imgOriginal.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(imgOriginal, 0, 0, null);
        g2.dispose();
        
        BufferedImage rotada = new BufferedImage(bi.getHeight(), bi.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = rotada.createGraphics();
        g.translate((bi.getHeight() - bi.getWidth()) / 2.0, (bi.getHeight() - bi.getWidth()) / 2.0);
        g.rotate(Math.toRadians(90), bi.getWidth() / 2.0, bi.getHeight() / 2.0);
        g.drawImage(bi, 0, 0, null);
        g.dispose();
        
        Image imgEscalada = rotada.getScaledInstance(anchoDestino, altoDestino, Image.SCALE_SMOOTH);
        return new ImageIcon(imgEscalada);
    }

    public void actualizarTodo(Hand manoHumana, Card cartaMesa) {
        panelSur.removeAll();
        panelNorte.removeAll();
        panelEste.removeAll();
        panelOeste.removeAll();

        // 1. Carta en la mesa
        ImageIcon iconoMesa = new ImageIcon(cartaMesa.getRutaImagen());
        Image imgMesa = iconoMesa.getImage().getScaledInstance(150, 175, Image.SCALE_SMOOTH);
        lblCartaMesa.setIcon(new ImageIcon(imgMesa));
        
        // 2. Tu mano Sur (Humano - Índice 0)
        for (int i = 0; i < manoHumana.size(); i++) {
            Card c = manoHumana.getCard(i);
            ImageIcon iconoBtn = new ImageIcon(c.getRutaImagen());
            Image imgBtn = iconoBtn.getImage().getScaledInstance(105, 120, Image.SCALE_SMOOTH);
            
            JButton btnCarta = new JButton(new ImageIcon(imgBtn));
            btnCarta.setPreferredSize(new Dimension(105, 120));
            btnCarta.setContentAreaFilled(false);
            btnCarta.setBorderPainted(false);

            final int indice = i;
            btnCarta.addActionListener(e -> game.jugarCartaHumano(c, indice));
            panelSur.add(btnCarta);
        }

        // Preparación de los reversos de las cartas
        ImageIcon iconoBack = new ImageIcon("img/back.png");
        Image imgBackNorte = iconoBack.getImage().getScaledInstance(75, 85, Image.SCALE_SMOOTH);
        ImageIcon backNorteFinal = new ImageIcon(imgBackNorte);
        
        // Genera el icono horizontal de las cartas laterales de forma segura
        ImageIcon backHorizontalFinal = obtenerIconoRotado("img/back.png", 115, 85);

        // 3. Jaz (Norte - Índice 2)
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, -25, 15)); 
        for (int i = 0; i < game.getJugadores().get(2).size(); i++) {
            panelNorte.add(new JLabel(backNorteFinal));
        }

        // 4. Piki (Este - Índice 1)
        panelEste.setLayout(new BoxLayout(panelEste, BoxLayout.Y_AXIS));
        panelEste.setBorder(BorderFactory.createCompoundBorder(
            crearBordeEstilizado(" PIKI "),
            BorderFactory.createEmptyBorder(35, 10, 0, 10)
        ));
        
        for (int i = 0; i < game.getJugadores().get(1).size(); i++) {
            JLabel labelCarta = new JLabel(backHorizontalFinal);
            labelCarta.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelEste.add(labelCarta);
            
            if (i < game.getJugadores().get(1).size() - 1) {
                panelEste.add(Box.createVerticalStrut(-55)); 
            }
        }

        // 5. Abby (Oeste - Índice 3)
        panelOeste.setLayout(new BoxLayout(panelOeste, BoxLayout.Y_AXIS));
        panelOeste.setBorder(BorderFactory.createCompoundBorder(
            crearBordeEstilizado(" ABBY "),
            BorderFactory.createEmptyBorder(35, 10, 0, 10)
        ));
        
        for (int i = 0; i < game.getJugadores().get(3).size(); i++) {
            JLabel labelCarta = new JLabel(backHorizontalFinal);
            labelCarta.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelOeste.add(labelCarta);
            
            if (i < game.getJugadores().get(3).size() - 1) {
                panelOeste.add(Box.createVerticalStrut(-55));
            }
        }

        panelSur.revalidate(); panelSur.repaint();
        panelNorte.revalidate(); panelNorte.repaint();
        panelEste.revalidate(); panelEste.repaint();
        panelOeste.revalidate(); panelOeste.repaint();
        panelCentro.revalidate(); panelCentro.repaint();
    }
}
