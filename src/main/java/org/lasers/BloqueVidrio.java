package org.lasers;

public class BloqueVidrio extends Bloque {
    @Override
    public boolean puedeMoverse() {
        return true;
    }

    @Override
    public void interactuarConLaser(Laser laser) throws Exception {
        Posicion puntoEntrada = laser.getPosicionActual();
        Direccion direccion = laser.getDireccion();
        Posicion puntoSalida = calcularPuntoSalida(puntoEntrada, direccion);
        laser.setPosicionActual(puntoSalida);

        Direccion direccionReflejada = direccion.direccionReflejada(puntoEntrada, direccion);
        Laser laserReflejado = new Laser(puntoEntrada, direccionReflejada);
        GestorLasers.obtenerInstancia().agregarLaser(laserReflejado);
    }

    private Posicion calcularPuntoSalida(Posicion puntoEntrada, Direccion direccionActual){

        int xEntrada = puntoEntrada.getX();
        int yEntrada = puntoEntrada.getY();

        Posicion posicionFinal = null;

        if (direccionActual == Direccion.SE) {
            posicionFinal = new Posicion(xEntrada + 1, yEntrada + 1);
        } else if (direccionActual == Direccion.NE) {
            posicionFinal = new Posicion(xEntrada + 1, yEntrada - 1);
        } else if (direccionActual == Direccion.SW) {
            posicionFinal = new Posicion(xEntrada -1, yEntrada + 1);
        } else if (direccionActual == Direccion.NW) {
            posicionFinal = new Posicion(xEntrada - 1, yEntrada - 1);
        }

        return posicionFinal;
    }
}