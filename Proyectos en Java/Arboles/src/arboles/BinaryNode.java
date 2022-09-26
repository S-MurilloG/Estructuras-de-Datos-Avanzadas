/**
 * Clase Nodo Binario.
 * Cuando hablamos de la instancia de este nodo, le vamos
 * a llamar TH por su referencia a 'this'.
 */
package arboles;

/**
 * 15 febrero, 2021.
 * @author Murillo
 */
public class BinaryNode <T extends Comparable<T>> {
    
    // Atributos.
    private T elem;
    private BinaryNode <T> izq, der, pa;
    private int fe;
    
    // Constructor.
    public BinaryNode(T e){
        elem = e;
        izq = null;
        der = null;
        pa = null;
        fe = 0;
    }
    
    // Getters.
    public T getElem(){
        return elem;
    }
    public BinaryNode getIzq(){
        return izq;
    }
    public BinaryNode getDer(){
        return der;
    }
    public BinaryNode getPa(){
        return pa;
    }
    public int getFactor(){
        return fe;
    }
    
    // Setters.
    public void setElem(T e){
        elem = e;
    }
    public void setIzq(BinaryNode e){
        izq = e;
    }
    public void setDer(BinaryNode e){
        der = e;
    }
    public void setPa(BinaryNode e){
        pa = e;
    }
    public void setFactor(int f){
        fe = f;
    }
    public void actFactor(int f){
        fe += f;
    }
    
    /**
     * Método número de hijos.
     * Si el nodo tiene hijo izquierdo, aumenta el contador.
     * Si el nodo tiene hijo derecho, aumenta contador.
     * @return número de hijos.
     */
    public int numHijos (){
        int numDesc = 0;
        
        if(izq != null)
            numDesc += 1;
        if(der != null)
            numDesc += 1;
        return numDesc;
    }
    
    /**
     * Método número de descendientes.
     * Calcula el número total de descendientes que tiene un nodo.
     * Si el nodo tiene un hijo izquierdo, aumenta el contador y llama nuevamente
     * al método {numDesc} con su hijo izquierdo.
     * Si tiene un hijo derecho, aumenta el contado y llama al método con su hijo 
     * derecho.
     * Parece ser un método recursivo, pero no lo es. Para cada nodo se hace un
     * llamado a otro diferente nodo. Es así como los descendientes se cuentan.
     * @return número de descendientes totales.
     */
    public int numDesc (){
        int numDesc = 0;
        
        if(izq != null)
            numDesc = 1 + izq.numDesc();
        if(der != null)
            numDesc = 1 + der.numDesc();
        return numDesc;
    }
    
    /**
     * Método elimina sub-árbol.
     * Se hace una comparación del elemento que nos dan como parámetro [que es 
     * la raíz del sub-árbol que se desea eliminar] con el elemento del nodo
     * TH para saber si es su hijo izquierdo o si es su hijo derecho (si es más 
     * chico, es izquierdo; si es más grande, es derecho). En cualquiera de los 
     * dos casos nuestro nodo apunta a nulo como su nuevo hijo. 
     * De esta manera se termina eliminando el subárbol completo.
     * @param elem : elemento que se quiere borrar.
     */
    public void eliminaSubArb(T elem){
        
        if(elem == null)
            return;
        if(elem.compareTo(this.elem) <= 0)
            izq = null;
        else
            der = null;
    }
    
    /**
     * Método cuelga.
     * Solamente se determina si el nodo va a ser el hijo izquierdo o el derecho
     * de TH. Si el elemento del nodo a colgar es menor al elemento TH, entonces
     * va a ser su hijo izquierdo; si no, va a ser el derecho.
     * Al final, como establecimos que el nodo sea uno de los hijos, se 'setea'
     * que el papá del nodo sea el nodo TH.
     * @param nodo : nodo que se desea colgar.
     */
    public void cuelga(BinaryNode<T> nodo){
        
        if(nodo.getElem().compareTo(elem) <= 0)
            izq = nodo;
        else
            der = nodo;
        nodo.setPa(this);
    }
}
