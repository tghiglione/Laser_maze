package org.lasers;

public class BloqueOpacoFijo implements Bloque{
    @Override
    public void interactuarConLaser(Laser laser) {
        laser.detener();
    }
    @Override
    public boolean puedeMoverse() {
        return false;
    }
}
