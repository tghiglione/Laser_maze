package org.lasers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Nivel {
    private Grilla grilla;

    public Nivel(InputStream rutaArchivo){
        this.grilla = new Grilla();
        cargarNivel(rutaArchivo);
    }

    private void cargarNivel(InputStream rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(rutaArchivo))){
            List<String> seccionBloques = new ArrayList<>();
            List<String> seccionEmisoresObjetivos = new ArrayList<>();
            List<String> seccionActual = seccionBloques;

            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isEmpty()) {
                    seccionActual = seccionEmisoresObjetivos;
                    continue;
                }
                seccionActual.add(linea);
            }

            procesarSeccionBloques(seccionBloques);
            procesarSeccionEmisoresObjetivos(seccionEmisoresObjetivos);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void procesarSeccionBloques(List<String> seccionBloques) throws Exception {
        int fila = 0;

        for (String linea : seccionBloques) {
            for (int columna = 0; columna < linea.length(); columna++) {
                char simbolo = linea.charAt(columna);

                if(simbolo == '.'){
                    Celda celdaVacia = new Celda(columna, fila);
                    celdaVacia.asignarPisoEnCelda(true);
                    grilla.agregarCelda(celdaVacia);
                } else if (simbolo != ' ') {
                    Celda celdaConBloque = new Celda(columna, fila);
                    celdaConBloque.asignarPisoEnCelda(true);
                    Bloque bloque = crearBloquePorSimbolo(simbolo);
                    List<Posicion> posicionesLogicas = new ArrayList<>();
                    Posicion centro = new Posicion(columna * 2 + 1, fila * 2 + 1);
                    Posicion arriba = new Posicion(columna * 2 + 1, fila * 2);
                    Posicion abajo = new Posicion(columna * 2 + 1, fila * 2 + 2);
                    Posicion izquierda = new Posicion(columna * 2, fila * 2 + 1);
                    Posicion derecha = new Posicion(columna * 2 + 2, fila * 2 + 1);

                    posicionesLogicas.add(centro);
                    posicionesLogicas.add(arriba);
                    posicionesLogicas.add(abajo);
                    posicionesLogicas.add(izquierda);
                    posicionesLogicas.add(derecha);
                    bloque.setPosicionesLogicas(posicionesLogicas);

                    celdaConBloque.colocarBloqueEnCelda(bloque);
                    grilla.agregarCelda(celdaConBloque);
                }
            }
            fila++;
        }
    }

    private Bloque crearBloquePorSimbolo(char simbolo) {
        switch (simbolo) {
            case 'F':
                return new BloqueOpacoFijo();
            case 'B':
                return new BloqueOpacoMovil();
            case 'R':
                return new BloqueEspejo();
            case 'G':
                return new BloqueVidrio();
            case 'C':
                return new BloqueCristal();
            default:
                throw new IllegalArgumentException("Símbolo de bloque desconocido: " + simbolo);
        }
    }

    private void procesarSeccionEmisoresObjetivos(List<String> seccionEmisoresObjetivos) {
        for (String linea : seccionEmisoresObjetivos) {
            if (linea.isEmpty()) {
                continue; // saltearse líneas vacías
            }
            String[] partes = linea.split(" ");
            switch (partes[0]) {
                case "E":
                    int xEmisor = Integer.parseInt(partes[1]);
                    int yEmisor = Integer.parseInt(partes[2]);
                    Direccion direccionEmisor = Direccion.desdeArchivo(partes[3]);
                    Emisor emisor = new Emisor(new Posicion(xEmisor, yEmisor), direccionEmisor);
                    grilla.agregarEmisor(emisor);
                    break;
                case "G":
                    int xObjetivo = Integer.parseInt(partes[1]);
                    int yObjetivo = Integer.parseInt(partes[2]);
                    Objetivo objetivo = new Objetivo(new Posicion(xObjetivo, yObjetivo));
                    grilla.agregarObjetivo(objetivo);
                    break;
                default:
                    break;
            }
        }
    }

    public Grilla getGrilla() {
        return grilla;
    }


}







