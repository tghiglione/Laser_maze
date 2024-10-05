package org.lasers;

public enum Direccion {
    NE, NW, SE, SW;

    public Direccion direccionReflejada() throws Exception{
        switch (this) {
            case NE:
                return SW;
            case NW:
                return SE;
            case SE:
                return NW;
            case SW:
                return NE;
            default:
                throw new Exception("Dirección desconocida");
        }
    }
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
