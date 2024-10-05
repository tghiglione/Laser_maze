package org.lasers;

public class BloqueOpacoMovil implements Bloque{
    @Override
    public void interactuarConLaser(Laser laser) {
        laser.detener();
    }
    @Override
    public boolean puedeMoverse() {
        return true;
    }
}
