package org.lasers;

public class Celda {
    private final int coordenadaX;
    private  final int coordenadaY;
    private boolean tienePiso;
    private Bloque bloque;

    public Celda(int x, int y){
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.tienePiso = false;
        this.bloque = null;
    }

    //Pre: -
    //Post: devuelve true si no tiene un bloque y tiene piso, false de lo contrario
    public boolean estaVacia(){
        return bloque == null && tienePiso;
    }

    //Pre: Debe ser un bloque valido
    //Post: coloca un bloque en la celda con piso y vacia
    public void colocarBloqueEnCelda(Bloque bloque) {
        if(estaVacia()){
            this.bloque = bloque;
        }
    }

    //Pre: Debe haber un bloque en la celda
    //Post: Quita el bloque de la celda y lo setea en null
    public void removerBloque(){
        if(this.bloque != null){
            this.bloque = null;
        }
    }

    //Pre:-
    //Post: Define la interaccion del bloque con el laser
    public void interactuarConLaser(Laser laser) throws Exception {
        if (obtenerBloque() != null) {
            System.out.println("ingresa porque hay bloque: "+ obtenerBloque());
            bloque.interactuarConLaser(laser);
        }
    }

    //Pre:-
    //Post: Asigna piso a una celda
    public void asignarPisoEnCelda(boolean tienePiso) {
        this.tienePiso = tienePiso;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public Bloque obtenerBloque() {
        return bloque;
    }

    public boolean celdaConPiso(){
        return tienePiso;
    }
}