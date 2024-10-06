package org.lasers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Main extends Application {
    private static final int TAMANO_CELDA = 50;
    private static final int RADIO_EMISOR = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InputStream nivelStream = getClass().getResourceAsStream("/level2.dat");
        if (nivelStream == null) {
            throw new FileNotFoundException("El archivo level1.dat no se encontró en el classpath.");
        }
        Nivel nivel = new Nivel(nivelStream);
        Grilla grilla = nivel.getGrilla();

        int numFilas = obtenerNumeroDeFilas(grilla);
        int numColumnas = obtenerNumeroDeColumnas(grilla);

        Canvas canvas = new Canvas((numColumnas + 1) * TAMANO_CELDA, (numFilas + 1) * TAMANO_CELDA);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el juego
        dibujarJuego(gc, grilla, numFilas);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Laser Maze");
        primaryStage.show();
    }

    private void dibujarJuego(GraphicsContext gc, Grilla grilla, int numFilas) {
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
                gc.fillRect(x + TAMANO_CELDA * 0.25, y + TAMANO_CELDA * 0.25, TAMANO_CELDA * 0.5, TAMANO_CELDA * 0.5);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x + TAMANO_CELDA * 0.25, y + TAMANO_CELDA * 0.25, TAMANO_CELDA * 0.5, TAMANO_CELDA * 0.5);
            }
        }

        for (Emisor emisor : grilla.getEmisores()) {
            Posicion posicion = emisor.getPosicionEmisor();

            double x = posicion.getX() * ((double) TAMANO_CELDA /2);
            double y = posicion.getY() * ((double) TAMANO_CELDA /2);

            gc.setFill(Color.RED);
            gc.fillOval(x - RADIO_EMISOR, y - RADIO_EMISOR, RADIO_EMISOR * 2, RADIO_EMISOR * 2);
        }

        for (Objetivo objetivo : grilla.getObjetivos()) {
            Posicion posicion = objetivo.getPosicionObjetivo();

            double x = posicion.getX() * ((double) TAMANO_CELDA /2);
            double y = posicion.getY() * ((double) TAMANO_CELDA /2);

            gc.setFill(Color.VIOLET);
            gc.fillOval(x - RADIO_EMISOR, y - RADIO_EMISOR, RADIO_EMISOR * 2, RADIO_EMISOR * 2);
        }
    }

    private Color obtenerColorDeBloque(Bloque bloque) {
        if (bloque instanceof BloqueOpacoFijo) {
            return Color.BLACK;
        } else if (bloque instanceof BloqueOpacoMovil) {
            return Color.DARKGRAY;
        } else if (bloque instanceof BloqueEspejo) {
            return Color.SILVER;
        } else if (bloque instanceof BloqueVidrio) {
            return Color.LIGHTBLUE;
        } else if (bloque instanceof BloqueCristal) {
            return Color.CYAN;
        } else {
            return Color.PINK;
        }
    }

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
}

