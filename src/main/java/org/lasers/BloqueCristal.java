package org.lasers;

public class BloqueCristal extends Bloque {

    @Override
    public boolean puedeMoverse() {
        return true;
    }
    @Override
    public void interactuarConLaser(Laser laser) {
        Posicion puntoEntrada = laser.getPosicionActual();
        Direccion direccion = laser.getDireccion();
        Posicion puntoSalida = calcularPuntoSalida(puntoEntrada, direccion);
        laser.setPosicionActual(puntoSalida);
    }

    private Posicion calcularPuntoSalida(Posicion puntoEntrada, Direccion direccionActual){

        int xEntrada = puntoEntrada.getX();
        int yEntrada = puntoEntrada.getY();

        boolean esXPar = (xEntrada % 2 == 0);
        boolean esYPar = (yEntrada % 2 == 0);

        Posicion posicionFinal = null;

        if (direccionActual == Direccion.SE) {
            if (esXPar && !esYPar) {
                posicionFinal = new Posicion(xEntrada + 2, yEntrada);
            } else if (!esXPar && esYPar) {
                posicionFinal = new Posicion(xEntrada, yEntrada + 2);
            }
        } else if (direccionActual == Direccion.NE) {
            if (!esXPar && esYPar) {
                posicionFinal = new Posicion(xEntrada, yEntrada -2);
            } else if (esXPar && !esYPar) {
                posicionFinal = new Posicion(xEntrada + 2, yEntrada);
            }
        } else if (direccionActual == Direccion.SW) {
            if (!esXPar && esYPar) {
                posicionFinal = new Posicion(xEntrada , yEntrada + 2);
            } else if (esXPar && !esYPar) {
                posicionFinal = new Posicion(xEntrada - 2, yEntrada);
            }
        } else if (direccionActual == Direccion.NW) {
            if (esXPar && !esYPar) {
                posicionFinal = new Posicion(xEntrada - 2, yEntrada);
            } else if (!esXPar && esYPar) {
                posicionFinal = new Posicion(xEntrada, yEntrada -2);
            }
        }

        return posicionFinal;
    }
}

