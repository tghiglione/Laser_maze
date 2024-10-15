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
    //Post: Asigna piso a una celda
    public void asignarPisoEnCelda(boolean tienePiso) {
        this.tienePiso = tienePiso;
    }

    //Pre: debe haber una celda valida
    //Post: devuelve la coordenada x
    public int getCoordenadaX() {
        return coordenadaX;
    }
    //Pre: debe haber una celda valida
    //Post: devuelve la coordenada y
    public int getCoordenadaY() {
        return coordenadaY;
    }
    //Pre: -
    //Post: devuelve el bloque o null si no lo hay
    public Bloque obtenerBloque() {
        return bloque;
    }
    //Pre: -
    //Post: devuelve true si tiene piso osea que puede haber un bloque, false si no lo tiene
    public boolean celdaConPiso(){
        return tienePiso;
    }
}