package org.lasers;

import java.util.ArrayList;
import java.util.List;

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
        List<Laser> lasersParaRemover = new ArrayList<>();
        for (Laser laser : lasersActivos) {
            if (!laser.estaActivo()) {
                lasersParaRemover.add(laser);
                continue;
            }
            try {
                laser.avanzar();
                Celda celda = grilla.obtenerCeldaEnPosicion(laser.getPosicionActual());
                if (celda != null) {
                    celda.interactuarConLaser(laser);
                } else {
                    laser.detener();
                }

                for (Objetivo objetivo : objetivos) {
                    objetivo.verificarImpacto(laser);
                }
            } catch (Exception e) {
                laser.detener();
                lasersParaRemover.add(laser);

            }
        }
        lasersActivos.removeAll(lasersParaRemover);
    }

    public List<Laser> getLasersActivos() {
        return new ArrayList<>(lasersActivos);
    }
}

