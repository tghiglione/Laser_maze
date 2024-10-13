package org.lasers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorLasers {
    private static GestorLasers instancia;
    private List<Laser> lasersActivos;

    private GestorLasers() {
        lasersActivos = new ArrayList<>();
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

    public boolean chequearGanador(Grilla grilla) {
        for (Laser laser : lasersActivos) {
            laser.reiniciarTrayectoria();
        }
        for (Objetivo objetivo : grilla.getObjetivos()) {
            objetivo.setAlcanzado(false);
        }

        Iterator<Laser> iterador = lasersActivos.iterator();
        while (iterador.hasNext()) {
            Laser laser = iterador.next();
            try {
                while (laser.estaActivo()) {
                    laser.avanzar();
                    Posicion posActual = laser.getPosicionActual();

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
                        if (objetivo.getPosicionObjetivo().equals(posActual)) {
                            objetivo.setAlcanzado(true);
                        }
                    }
                    if(grilla.todosObjetivosAlcanzados()){
                        laser.detener();
                    }
                    if(!grilla.estaDentroDeLimites(posActual)){
                        laser.detener();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                iterador.remove();
            }
        }
        return grilla.todosObjetivosAlcanzados();
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

