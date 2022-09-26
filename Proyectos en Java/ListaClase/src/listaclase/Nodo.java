/**
 * Clase Nodo.
 */
package listaclase;

/**
 * 13 enero, 2021.
 * @author Murillo
 */
public class Nodo <T> {
    private T elemento;
    private Nodo<T> siguiente;
    
    //Constructor
    public Nodo (T elem){
        elemento = elem;
        siguiente = null;
    }
    
    //Getters
    public T getElem(){
        return elemento;
    }
    public Nodo<T> getSig(){
        return siguiente;
    }
    
    //Setters
    public void setSig (Nodo<T> nodo){
        siguiente = nodo;
    }
    public void setElem (T elem){
        elemento = elem;
    }
}
