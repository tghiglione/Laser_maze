package org.lasers;

public class BloqueOpacoFijo extends Bloque{
    @Override
    public void interactuarConLaser(Laser laser) {
        laser.detener();
    }
    @Override
    public boolean puedeMoverse() {
        return false;
    }
}
