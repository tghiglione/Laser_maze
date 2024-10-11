package org.lasers;

public class BloqueCristal extends Bloque {

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
    }

    private Posicion calcularPuntoSalida(Posicion puntoEntrada, Direccion direccion) throws Exception{

        int xEntrada = puntoEntrada.getX();
        int yEntrada = puntoEntrada.getY();

        int xCentroCelda = xEntrada;
        int yCentroCelda = yEntrada;

        switch (direccion) {
            case NE:
                xCentroCelda += 1;
                yCentroCelda -= 1;
                break;
            case NW:
                xCentroCelda -= 1;
                yCentroCelda -= 1;
                break;
            case SE:
                xCentroCelda += 1;
                yCentroCelda += 1;
                break;
            case SW:
                xCentroCelda -= 1;
                yCentroCelda += 1;
                break;
            default:
                throw new Exception("Dirección desconocida");
        }

        return new Posicion(xCentroCelda, yCentroCelda);
    }
}

