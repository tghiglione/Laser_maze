package org.lasers;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class Main extends Application {
    private static final int TAMANO_CELDA = 50;
    private static final int RADIO_EMISOR = 5;

    private GraphicsContext gc;
    private Grilla grilla;
    private Label labelEstado;

    private Bloque bloqueArrastrado = null;
    private Posicion posicionBloqueArrastrado = null;
    private double offsetX;
    private double offsetY;

    private final List<BloqueMovil> bloquesMoviles = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InputStream nivelStream = getClass().getResourceAsStream("/level1.dat");
        if (nivelStream == null) {
            throw new FileNotFoundException("El archivo level1.dat no se encontró.");
        }
        Nivel nivel = new Nivel(nivelStream);
        grilla = nivel.getGrilla();
        GestorLasers gestorLasers = GestorLasers.obtenerInstancia();
        gestorLasers.inicializarLasersDesdeEmisores(grilla.getEmisores());
        int numFilas = obtenerNumeroDeFilas(grilla);
        int numColumnas = obtenerNumeroDeColumnas(grilla);

        Canvas canvas = new Canvas((numColumnas + 1) * TAMANO_CELDA, (numFilas + 1) * TAMANO_CELDA);
        gc = canvas.getGraphicsContext2D();

        labelEstado = new Label("");
        labelEstado.setStyle("-fx-font-size: 30px; -fx-text-fill: green; -fx-font-weight: bold;");

        dibujarJuego(gc, grilla);

        canvas.setOnMousePressed(event -> manejarMousePressed(event, grilla));
        canvas.setOnMouseReleased(event -> manejarMouseReleased(event, grilla, gc));

        VBox buttonBox = new VBox(10);

        for (int i = 1; i <= 6; i++) {
            Button button = new Button("Nivel " + i);
            button.setMinWidth(100);
            int nivelNumero = i;
            button.setOnAction(event -> cargarNivel(nivelNumero));
            buttonBox.getChildren().add(button);
        }

        HBox nivelBox = new HBox(10);
        nivelBox.getChildren().addAll(canvas, buttonBox);

        VBox root = new VBox(10);
        root.getChildren().addAll(nivelBox, labelEstado);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego Lasers");
        primaryStage.show();
    }

    //Pre: debe ser un numero de nivel valido
    //Post: crea el nivel en un estado inicial
    private void cargarNivel(int nivelNumero) {
        GestorLasers gestorLasersActual = GestorLasers.obtenerInstancia();
        gestorLasersActual.eliminarTodosLosLasers();
        gestorLasersActual.finalizarGestor();
        labelEstado.setText("");

        Nivel nuevoNivel = ControladorNiveles.obtenerInstancia().cargarNivel(nivelNumero);
        grilla = nuevoNivel.getGrilla();

        GestorLasers nuevoGestorLasers = GestorLasers.obtenerInstancia();
        nuevoGestorLasers.inicializarLasersDesdeEmisores(grilla.getEmisores());

        dibujarJuego(gc, grilla);
    }

    //Pre: -
    //Post: renderiza el juego con los bloques, emisores, objetivos y laser
    private void dibujarJuego(GraphicsContext gc, Grilla grilla) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        bloquesMoviles.clear();

        for (Map.Entry<Posicion, Celda> entry : grilla.getCeldas().entrySet()) {
            Posicion posicion = entry.getKey();
            Celda celda = entry.getValue();

            int x = posicion.getX() * TAMANO_CELDA;
            int y = posicion.getY() * TAMANO_CELDA;

            gc.setFill(celda.celdaConPiso() ? Color.BEIGE : Color.DARKGRAY);
            gc.fillRect(x, y, TAMANO_CELDA, TAMANO_CELDA);

            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, TAMANO_CELDA, TAMANO_CELDA);

            if (celda.obtenerBloque() != null) {
                Bloque bloque = celda.obtenerBloque();
                Color colorBloque = obtenerColorDeBloque(bloque);

                gc.setFill(colorBloque);
                gc.fillRect(x, y, TAMANO_CELDA, TAMANO_CELDA);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, TAMANO_CELDA, TAMANO_CELDA);

                if (bloque.puedeMoverse()) {
                    Rectangle2D areaBloque = new Rectangle2D(x, y, TAMANO_CELDA, TAMANO_CELDA);
                    bloquesMoviles.add(new BloqueMovil(bloque, posicion, areaBloque));
                }
            }
        }
        // Dibujar emisores
        for (Emisor emisor : grilla.getEmisores()) {
            Posicion posicion = emisor.getPosicionEmisor();

            double x = posicion.getX() * ((double) TAMANO_CELDA / 2);
            double y = posicion.getY() * ((double) TAMANO_CELDA / 2);

            gc.setFill(Color.RED);
            gc.fillOval(x - RADIO_EMISOR, y - RADIO_EMISOR, RADIO_EMISOR * 2, RADIO_EMISOR * 2);
        }
        // Dibujar objetivos
        for (Objetivo objetivo : grilla.getObjetivos()) {
            Posicion posicion = objetivo.getPosicionObjetivo();

            double x = posicion.getX() * ((double) TAMANO_CELDA / 2);
            double y = posicion.getY() * ((double) TAMANO_CELDA / 2);

            gc.setFill(Color.VIOLET);
            gc.fillOval(x - RADIO_EMISOR, y - RADIO_EMISOR, RADIO_EMISOR * 2, RADIO_EMISOR * 2);
        }
        GestorLasers gestorLasers = GestorLasers.obtenerInstancia();
        boolean ganoJuego = gestorLasers.chequearGanador(grilla);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);

        for (Laser laser : gestorLasers.getLasersActivos()) {
            List<Posicion> trayectoria = laser.getTrayectoria();

            for (int i = 0; i < trayectoria.size() - 1; i++) {
                Posicion origen = trayectoria.get(i);
                Posicion destino = trayectoria.get(i + 1);

                double xOrigen = origen.getX() * ((double) TAMANO_CELDA / 2);
                double yOrigen = origen.getY() *  ((double) TAMANO_CELDA / 2);
                double xDestino = destino.getX() * ((double) TAMANO_CELDA / 2);
                double yDestino = destino.getY() *  ((double) TAMANO_CELDA / 2);

                gc.strokeLine(xOrigen, yOrigen, xDestino, yDestino);
            }
        }
        if (ganoJuego){
            labelEstado.setText("¡Ganaste!");
        }
    }

    //Pre: -
    //Post: Maneja el drag de los bloques moviles
    private void manejarMousePressed(MouseEvent event, Grilla grilla) {
        double x = event.getX();
        double y = event.getY();

        for (BloqueMovil bloqueMovil : bloquesMoviles) {
            if (bloqueMovil.area.contains(x, y)) {
                bloqueArrastrado = bloqueMovil.bloque;
                posicionBloqueArrastrado = bloqueMovil.posicion;
                offsetX = x - bloqueMovil.area.getMinX();
                offsetY = y - bloqueMovil.area.getMinY();
                break;
            }
        }
    }

    //Pre: -
    //Post: Maneja el drop de los bloques moviles
    private void manejarMouseReleased(MouseEvent event, Grilla grilla, GraphicsContext gc) {
        if (bloqueArrastrado != null) {

            double x = event.getX() - offsetX + TAMANO_CELDA * 0.25;
            double y = event.getY() - offsetY + TAMANO_CELDA * 0.25;

            int columna = (int) ((x + TAMANO_CELDA * 0.25) / TAMANO_CELDA);
            int fila = (int) ((y + TAMANO_CELDA * 0.25) / TAMANO_CELDA);
            grilla.moverBloque(bloqueArrastrado,columna,fila);
            Posicion posicionDestino = new Posicion(columna, fila);

            Celda celdaDestino = grilla.obtenerCeldaEnPosicion(posicionDestino);

            if (celdaDestino != null && celdaDestino.estaVacia()) {
                grilla.obtenerCeldaEnPosicion(posicionBloqueArrastrado).removerBloque();
                celdaDestino.colocarBloqueEnCelda(bloqueArrastrado);

            }
            bloqueArrastrado = null;
            posicionBloqueArrastrado = null;

            dibujarJuego(gc, grilla);
        }
    }

    //Pre: tiene que ser un bloque valido
    //Post: Indica el color de los bloques
    private Color obtenerColorDeBloque(Bloque bloque) {
        if (bloque instanceof BloqueOpacoFijo) {
            return Color.BLACK;
        } else if (bloque instanceof BloqueOpacoMovil) {
            return Color.DARKGRAY;
        } else if (bloque instanceof BloqueEspejo) {
            return Color.STEELBLUE;
        } else if (bloque instanceof BloqueVidrio) {
            return Color.WHITESMOKE;
        } else if (bloque instanceof BloqueCristal) {
            return Color.LIGHTBLUE;
        } else {
            return Color.PINK;
        }
    }

    //Pre: -
    //Post: devuelve las filas de la grilla
    private int obtenerNumeroDeFilas(Grilla grilla) {
        int maxFila = 0;
        for (Posicion posicion : grilla.getCeldas().keySet()) {
            if (posicion.getY() > maxFila) {
                maxFila = posicion.getY();
            }
        }
        for (Emisor emisor : grilla.getEmisores()) {
            if (emisor.getPosicionEmisor().getY() > maxFila) {
                maxFila = emisor.getPosicionEmisor().getY();
            }
        }
        for (Objetivo objetivo : grilla.getObjetivos()) {
            if (objetivo.getPosicionObjetivo().getY() > maxFila) {
                maxFila = objetivo.getPosicionObjetivo().getY();
            }
        }
        return maxFila;
    }

    //Pre: -
    //Post: devuelve las columnas de la grilla
    private int obtenerNumeroDeColumnas(Grilla grilla) {
        int maxColumna = 0;
        for (Posicion posicion : grilla.getCeldas().keySet()) {
            if (posicion.getX() > maxColumna) {
                maxColumna = posicion.getX();
            }
        }
        for (Emisor emisor : grilla.getEmisores()) {
            if (emisor.getPosicionEmisor().getX() > maxColumna) {
                maxColumna = emisor.getPosicionEmisor().getX();
            }
        }
        for (Objetivo objetivo : grilla.getObjetivos()) {
            if (objetivo.getPosicionObjetivo().getX() > maxColumna) {
                maxColumna = objetivo.getPosicionObjetivo().getX();
            }
        }
        return maxColumna;
    }

    private static class BloqueMovil {
        Bloque bloque;
        Posicion posicion;
        Rectangle2D area;

        //Constructor
        public BloqueMovil(Bloque bloque, Posicion posicion, Rectangle2D area) {
            this.bloque = bloque;
            this.posicion = posicion;
            this.area = area;
        }
    }
}

