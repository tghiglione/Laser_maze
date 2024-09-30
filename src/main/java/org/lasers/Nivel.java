package org.lasers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Nivel {
    private Grilla grilla;
    private List<EmisorLaser> emisores;
    private List<Objetivo> objetivos;
    private String archivoNivel;

    public Nivel(String archivoNivel) throws Exception {
        this.archivoNivel = archivoNivel;
        this.emisores = new ArrayList<EmisorLaser>();
        this.objetivos = new ArrayList<Objetivo>();
        inicializarNivel();
    }

    private void inicializarNivel() throws Exception {
        List<String> lineas = leerArchivo(archivoNivel);

        int filas = 0;
        int columnas = 0;
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) break;
            filas++;
            columnas = Math.max(columnas, linea.length());
        }

        this.grilla = new Grilla(filas, columnas);

        for (int i = 0; i < filas; i++) {                   //armo la grilla con piso o bloque
            String linea = lineas.get(i);
            for (int j = 0; j < linea.length(); j++) {
                char simbolo = linea.charAt(j);
                if (simbolo == '.') {
                    grilla.colocarPiso(i, j);
                } else if (simbolo != ' ') {
                    Bloque tipoDeBloque = crearBloque(simbolo);
                    grilla.colocarBloque(i, j, tipoDeBloque);
                }
            }
        }

        for (int i = filas + 1; i < lineas.size(); i++) {       //coloco emisor y objetivo en la grilla
            String[] partes = lineas.get(i).split(" "); //armo array con las partes [E,0,1,SE] o [G,8.0] para crear emisor u objetivo
            if (partes[0].equals("E")) {
                emisores.add(new EmisorLaser(
                        Integer.parseInt(partes[1]),
                        Integer.parseInt(partes[2]),
                        partes[3])
                );
            } else if (partes[0].equals("G")) {
                objetivos.add(new Objetivo(
                        Integer.parseInt(partes[1]),
                        Integer.parseInt(partes[2]))
                );
            }
        }
    }

    private Bloque crearBloque(char tipo) throws Exception {
        switch (tipo) {
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
                throw new Exception("Tipo de bloque no válido");
        }
    }

    private List<String> leerArchivo(String archivoNivel) throws Exception {
        try {
            return Files.readAllLines(Paths.get(archivoNivel));
        } catch (Exception e) {
            throw new Exception("Error al leer el archivo: " + e);
        }
    }

    public Grilla getGrilla() {
        return grilla;
    }

    public List<EmisorLaser> getEmisores() {
        return emisores;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }
}







