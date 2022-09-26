/**
 * Implementación de la clase de Nodo Hash.
 */
package mainhash;

/**
 * 21 abril, 2021.
 * @author Murillo
 */
public class HashNode <T> {
    
    // Atributos
    private T elem;
    private HashNode<T> next;
    
    // Constructor
    public HashNode(T e){
        elem = e;
        next = null;
    }
    
    // Función de hash
    public int funcHash(){
        return elem.hashCode();
    }
    
    // Getters
    public T getElem(){
        return elem;
    }
    public HashNode<T> getNext(){
        return next;
    }
    
    // Setters
    public void setElem(T e){
        this.elem = e;
    }
    public void setNext(HashNode<T> n){
        this.next = n;
    }
}
