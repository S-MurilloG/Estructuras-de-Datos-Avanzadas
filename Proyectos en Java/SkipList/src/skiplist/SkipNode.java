/**
 * Implementación de la clase Skip Node.
 */
package skiplist;

/**
 * 12 abril, 2021.
 * @author Murillo
 */
public class SkipNode <T extends Comparable<T>> {
    
    // Atributos
    private SkipNode<T> izq,der,arr,ab;
    private T elem;
    
    // Constructor
    public SkipNode(T elem){
        this.elem = elem;
        izq = null;
        der = null;
        arr = null;
        ab = null;
    }
    
    // Getters
    public T getElem(){
        return elem;
    }
    public SkipNode<T> getIzq(){
        return izq;
    }
    public SkipNode<T> getDer(){
        return der;
    }
    public SkipNode<T> getArr(){
        return arr;
    }
    public SkipNode<T> getAb(){
        return ab;
    }
    
    // Setters
    public void setElem(T elem){
        this.elem = elem;
    }
    public void setIzq(SkipNode<T> izq){
        this.izq = izq;
    }
    public void setDer(SkipNode<T> der){
        this.der = der;
    }
    public void setArr(SkipNode<T> arr){
        this.arr = arr;
    }
    public void setAb(SkipNode<T> ab){
        this.ab = ab;
    }
}
