package org.lasers;

public enum Direccion {
    NE, NW, SE, SW;

    //Pre: -
    //Post: devuelve la direccion reflejada segun la direccion con la que impacta el laser y la direccion que este tiene
    public Direccion direccionReflejada(Posicion posicionActual, Direccion direccionActual){
        int posicionX = posicionActual.getX();
        int posicionY = posicionActual.getY();

        boolean esXPar = (posicionX % 2 == 0);
        boolean esYPar = (posicionY % 2 == 0);

        Direccion direccionFinal = null;
        
        if (direccionActual == SE) {
            if (esXPar && !esYPar) {
                direccionFinal = SW;
            } else if (!esXPar && esYPar) {
                direccionFinal = NE;
            }
        } else if (direccionActual == NE) {
            if (!esXPar && esYPar) {
                direccionFinal = SE;
            } else if (esXPar && !esYPar) {
                direccionFinal = NW;
            }
        } else if (direccionActual == SW) {
            if (!esXPar && esYPar) {
                direccionFinal = NW;
            } else if (esXPar && !esYPar) {
                direccionFinal = SE;
            }
        } else if (direccionActual == NW) {
            if (esXPar && !esYPar) {
                direccionFinal = NE;
            } else if (!esXPar && esYPar) {
                direccionFinal = SW;
            }
        }
        return direccionFinal;
    }

    //Pre: tiene que tener una direccion valida en el archivo
    //Post: devuelve la direccion en el formato esperado
    public static Direccion desdeArchivo(String direccionStr) {
        switch (direccionStr.toUpperCase()) {
            case "NE":
                return NE;
            case "NW":
                return NW;
            case "SE":
                return SE;
            case "SW":
                return SW;
            default:
                throw new IllegalArgumentException("Dirección desconocida: " + direccionStr);
        }
    }
}
