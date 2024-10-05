package org.lasers;

public interface Bloque {
    //Pre:-
    //Post: Devuelve true si el bloque es movil o false si es fijo
    boolean puedeMoverse();

    //Pre:-
    //Post: Define como interactua el bloque con el laser al ser impactado
    void interactuarConLaser(Laser laser) throws Exception;
}
