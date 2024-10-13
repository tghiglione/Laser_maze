package org.lasers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GestorLasers {
    private static GestorLasers instancia;
    private List<Laser> lasersActivos;
    private List<Objetivo> objetivos;

    private GestorLasers() {
        lasersActivos = new ArrayList<>();
    }

    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos;
    }

    public static GestorLasers obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorLasers();
        }
        return instancia;
    }

    public void inicializarLasersDesdeEmisores(List<Emisor> emisores) {
        for (Emisor emisor : emisores) {
            Laser laser = emisor.emitirLaser();
            agregarLaser(laser);
        }
    }

    public void agregarLaser(Laser laser) {
        lasersActivos.add(laser);
    }

    public void actualizarLasers(Grilla grilla) {
        for (Laser laser : lasersActivos) {
            laser.reiniciarTrayectoria();
        }
        Iterator<Laser> iterador = lasersActivos.iterator();
        while (iterador.hasNext()) {
            Laser laser = iterador.next();
            try {
                int contador = 0;
                while (contador < 8) {
                    Posicion posIni = laser.getPosicionActual();
                    System.out.println("Pos inicial: " + posIni);
                    laser.avanzar();
                    Posicion posActual = laser.getPosicionActual();
                    System.out.println("Pos actual: " + posActual);

                    for (Celda celda : grilla.obtenerTodasLasCeldas()) {
                        Bloque bloque = celda.obtenerBloque();
                        if (bloque != null) {
                            List<Posicion> posicionesBloque = bloque.getPosicionesLogicas();
                            if (posicionesBloque.contains(posActual)) {
                                bloque.interactuarConLaser(laser);
                                break;
                            }
                        }
                    }

                    for (Objetivo objetivo : grilla.getObjetivos()) {
                        objetivo.verificarImpacto(laser);
                    }

                    contador++;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                iterador.remove();
            }
        }
    }

    public List<Laser> getLasersActivos() {
        return new ArrayList<>(lasersActivos);
    }
    public void eliminarTodosLosLasers() {
        lasersActivos.clear();
    }
    public void finalizarGestor() {
        instancia = null;
    }
}

