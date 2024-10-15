package org.lasers;

import java.util.*;

public class GestorLasers {
    private static GestorLasers instancia;
    private List<Laser> lasersActivos;

    private GestorLasers() {
        lasersActivos = new ArrayList<>();
    }

    //Pre: -
    //Post: devuelve una instancia de la clase GestorLasers. Si es null la crea, sino devuelve la existente
    public static GestorLasers obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorLasers();
        }
        return instancia;
    }

    //Pre: -
    //Post: agrega los laser a los emisores
    public void inicializarLasersDesdeEmisores(List<Emisor> emisores) {
        for (Emisor emisor : emisores) {
            Laser laser = emisor.emitirLaser();
            agregarLaser(laser);
        }
    }

    //Pre: -
    //Post: agrega un laser a los laseres que se encuentran en el nivel
    public void agregarLaser(Laser laser) {
        lasersActivos.add(laser);
    }

    //Pre: -
    //Post: mueve los laseres del nivel y chequea si tocan todos los objetivos. Devuelve true si los alcanza o false si no los alcanza
    public boolean chequearGanador(Grilla grilla) {
        lasersActivos.clear();

        for (Objetivo objetivo : grilla.getObjetivos()) {
            objetivo.setAlcanzado(false);
        }

        List<Laser> colaDeProcesamiento = new ArrayList<>();
        for (Emisor emisor : grilla.getEmisores()) {
            Laser laser = emisor.emitirLaser();
            colaDeProcesamiento.add(laser);
        }
        while (!colaDeProcesamiento.isEmpty()) {
            Laser laser = colaDeProcesamiento.remove(0);
            try {
                while (laser.estaActivo()) {
                    laser.avanzar();
                    Posicion posActual = laser.getPosicionActual();

                    for (Celda celda : grilla.obtenerTodasLasCeldas()) {
                        Bloque bloque = celda.obtenerBloque();
                        if (bloque != null) {
                            List<Posicion> posicionesBloque = bloque.getPosicionesLogicas();
                            if (posicionesBloque.contains(posActual)) {
                                if (bloque instanceof BloqueVidrio) {
                                    Posicion puntoEntrada = laser.getPosicionActual();
                                    Direccion direccion = laser.getDireccion();
                                    Direccion direccionReflejada = laser.getDireccion().direccionReflejada(puntoEntrada,direccion);
                                    Laser laserReflejado = new Laser(posActual, direccionReflejada);
                                    colaDeProcesamiento.add(laserReflejado);
                                }
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
                    if (grilla.todosObjetivosAlcanzados() || !grilla.estaDentroDeLimites(posActual)) {
                        laser.detener();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                laser.detener();
            }
            lasersActivos.add(laser);
        }
        return grilla.todosObjetivosAlcanzados();
    }

    //Pre: -
    //Post: devuelve los laseres que se encuentran activos en el nivel
    public List<Laser> getLasersActivos() {
        return new ArrayList<>(lasersActivos);
    }

    //Pre: -
    //Post: elimina los laseres activos del nivel
    public void eliminarTodosLosLasers() {
        lasersActivos.clear();
    }

    //Pre: -
    //Post: reinicia la instancia del gestor
    public void finalizarGestor() {
        instancia = null;
    }

}
