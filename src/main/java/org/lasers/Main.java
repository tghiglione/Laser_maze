package org.lasers;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;

import static org.lasers.ControladorNiveles.CANTIDAD_NIVELES;

public class Main extends Application {
    private static final int TAMANO_CELDA = 50;
    private static final int RADIO_EMISOR = 5;

    private GraphicsContext gc;
    private Grilla grilla;

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

        ControladorNiveles controladorNiveles = new ControladorNiveles();
        List<StackPane> interfacesNiveles = obtenerInterfacesNiveles(controladorNiveles);
        StackPane interfazNivelDefault = obtenerInterfazNivel(controladorNiveles,1);
        List<Button> botones = new ArrayList<>();

        var root = new HBox();
        var vBoxBotones = new VBox();

        for (int i = 1; i <= CANTIDAD_NIVELES ;i++) {
            botones.add(new Button("Nivel " + i));
            vBoxBotones.getChildren().add(botones.get(i - 1));
        }
        root.getChildren().addAll(interfazNivelDefault, vBoxBotones);

        botones.get(0).setOnAction(e -> {
            if (root.getChildren().size() > 1) {
                root.getChildren().set(0,obtenerInterfazNivel(controladorNiveles, 1));
            }
        });

        botones.get(1).setOnAction(e -> {
            if (root.getChildren().size() > 1) {
                root.getChildren().set(0,obtenerInterfazNivel(controladorNiveles, 2));
            }
        });

        botones.get(2).setOnAction(e -> {
            if (root.getChildren().size() > 1) {
                root.getChildren().set(0,obtenerInterfazNivel(controladorNiveles, 3));
            }
        });

        botones.get(3).setOnAction(e -> {
            if (root.getChildren().size() > 1) {
                root.getChildren().set(0,obtenerInterfazNivel(controladorNiveles, 4));
            }
        });
        botones.get(5).setOnAction(e -> {
            if (root.getChildren().size() > 1) {
                root.getChildren().set(0,obtenerInterfazNivel(controladorNiveles, 6));
            }
        });
        botones.get(4).setOnAction(e -> {
            if (root.getChildren().size() > 1) {
                root.getChildren().set(0,obtenerInterfazNivel(controladorNiveles, 5));
            }
        });

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego Lasers");
        primaryStage.show();
    }

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

                double bloqueX = x + TAMANO_CELDA * 0.25;
                double bloqueY = y + TAMANO_CELDA * 0.25;
                double bloqueAncho = TAMANO_CELDA * 0.5;
                double bloqueAlto = TAMANO_CELDA * 0.5;

                gc.setFill(colorBloque);
                gc.fillRect(bloqueX, bloqueY, bloqueAncho, bloqueAlto);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(bloqueX, bloqueY, bloqueAncho, bloqueAlto);

                if (bloque.puedeMoverse()) {
                    Rectangle2D areaBloque = new Rectangle2D(bloqueX, bloqueY, bloqueAncho, bloqueAlto);
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
    }

    private List<StackPane> obtenerInterfacesNiveles(ControladorNiveles controladorNiveles){
        List<StackPane> interfaces = new ArrayList<>();
        List<Grilla> grillas = controladorNiveles.obtenerGrillas();

        for (Grilla grilla : grillas) {
            int numFilas = obtenerNumeroDeFilas(grilla);
            int numColumnas = obtenerNumeroDeColumnas(grilla);

            Canvas canvas = new Canvas((numColumnas + 1) * TAMANO_CELDA, (numFilas + 1) * TAMANO_CELDA);
            gc = canvas.getGraphicsContext2D();

            dibujarJuego(gc, grilla);

            canvas.setOnMousePressed(event -> manejarMousePressed(event, grilla));
            canvas.setOnMouseDragged(event -> manejarMouseDragged(event));
            canvas.setOnMouseReleased(event -> manejarMouseReleased(event, grilla, gc));

            StackPane ventanaGrilla = new StackPane(canvas);
            interfaces.add(ventanaGrilla);
        }
        return interfaces;
    }

    private StackPane obtenerInterfazNivel(ControladorNiveles controladorNiveles, final int numeroNivel){
        return obtenerInterfacesNiveles(controladorNiveles).get(numeroNivel - 1);
    }

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

    private void manejarMouseDragged(MouseEvent event) {
        if (bloqueArrastrado != null) {
            double x = event.getX() - offsetX;
            double y = event.getY() - offsetY;

            dibujarJuego(gc, grilla);

            gc.setFill(obtenerColorDeBloque(bloqueArrastrado));
            gc.fillRect(x, y, TAMANO_CELDA * 0.5, TAMANO_CELDA * 0.5);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, TAMANO_CELDA * 0.5, TAMANO_CELDA * 0.5);
        }
    }

    private void manejarMouseReleased(MouseEvent event, Grilla grilla, GraphicsContext gc) {
        if (bloqueArrastrado != null) {
            double x = event.getX() - offsetX + TAMANO_CELDA * 0.25;
            double y = event.getY() - offsetY + TAMANO_CELDA * 0.25;

            int columna = (int) ((x + TAMANO_CELDA * 0.25) / TAMANO_CELDA);
            int fila = (int) ((y + TAMANO_CELDA * 0.25) / TAMANO_CELDA);
            Posicion posicionDestino = new Posicion(columna, fila);

            System.out.println("Mouse Released at x: " + x + ", y: " + y);
            System.out.println("Calculated column: " + columna + ", row: " + fila);

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

    private static class BloqueMovil {
        Bloque bloque;
        Posicion posicion;
        Rectangle2D area;

        public BloqueMovil(Bloque bloque, Posicion posicion, Rectangle2D area) {
            this.bloque = bloque;
            this.posicion = posicion;
            this.area = area;
        }
    }
}



