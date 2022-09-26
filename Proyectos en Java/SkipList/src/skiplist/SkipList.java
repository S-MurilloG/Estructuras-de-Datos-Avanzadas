/**
 * Clase SkipList.
 * Se implementa la estructura de datos SkipList utilizando listas ligadas.
 */
package skiplist;

import java.util.ArrayList;

/**
 * 12 abril, 2021.
 * @author Murillo
 */
public class SkipList <T extends Comparable<T>> {
    
    // Atributos
    private SkipNode<T> cabeza, cola;
    private int numListas;
    private int cont;
    
    // Constructor
    public SkipList(){
        cabeza = new SkipNode<T>(null);
        cola = new SkipNode<T>(null);
        ligaIzqDer(cabeza,cola);
        numListas = 1;
        cont = 0;
    }
    
    // Getters necesarios
    public int getNumListas(){
        return numListas;
    }
    public int getCont(){
        return cont;
    }
    
    /**
     * Método de ligamiento izquierda - derecha.
     * Dados dos nodos izquierda y derecha, se settea a 'der' como nodo 
     * derecho de 'izq' y a 'izq' como nodo izquierdo de 'der'.
     * @param izq,der : nodos que se quieren ligar.
     */
    private void ligaIzqDer(SkipNode<T> izq, SkipNode<T> der){
        izq.setDer(der);
        der.setIzq(izq);
    }
    /**
     * Método de ligamiento arriba - abajo.
     * Dados dos nodos arriba y abajo, se settea a 'ab' como nodo 
     * inferior de 'arr' y a 'arr' como nodo superior de 'ab'.
     * @param arr,ab : nodos que se quieren ligar.
     */
    private void ligaArrAb(SkipNode<T> arr, SkipNode<T> ab){
        arr.setAb(ab);
        ab.setArr(arr);
    }
    
    
    /**
     * Método de búsqueda auxiliar.
     * Es empieza por la cabeza de la lista más superficial de la estructura.
     * Mientras que todavía existan listas para abajo que recorrer, se guarda
     * en 'der' al siguiente nodo de 'actual'. Mientras que 'der' no llegue al
     * centinela de cola y que su elemento sea más chico que 'elem', actual se
     * va a mover a su siguiente nodo. Si 'der' es más grande que 'actual', este
     * nodo va a bajar una lista para continuar su recorrido.
     * R : Regresa el nodo si el elemento se encuentra en la estructura o
     *     regresa el nodo anterior de donde deberá de estar si no se encuentra.
     * @param elem : elemento que se desea buscar.
     */
    private SkipNode<T> buscaAux(T elem){
        SkipNode<T> actual = cabeza;
        T der;
        int i;
        
        i = 0;
        while(i < numListas){
            der = actual.getDer().getElem();
            while(der != null && der.compareTo(elem) < 0){
                actual = actual.getDer();
                der = actual.getDer().getElem();
            }
            if(actual.getAb() != null)
                actual = actual.getAb();
            i++;
        }
        return actual;
    }
    /**
     * Método de búsqueda.
     * En la variable 'actual' se lLama al método {buscaAux}. Si el elemnto del
     * nodo que regresa el método es igual al elemento dado como parámetro, 
     * entonces quiere decir que sí se encuentra en la estructura. De lo
     * contrario, no se encuentra.
     * @param elem : elemento que se desea buscar.
     * @return true si el elemento está en la estructura o false si no es así.
     */
    public boolean busca(T elem){
        SkipNode<T> actual;
        
        actual = buscaAux(elem);
        return actual.getElem().equals(elem);
    }
    
    
    /**
     * Método de borrado.
     * En 'actual' se llama al método {buscaAux} para iniciar con el proceso
     * de borrado. Si se encuentra al elemento, se hace lo siguiente:
     * Se borra al elemento de la lista más inferior de la estructura y se va 
     * subiendo (si es posible) a las otras listas para borrar al elemento de 
     * toda la estructura.
     * Si el número de listas que tiene la estructura es mayor al número de 
     * ellas que idealmente debería de tener, se colapsa la lista que se 
     * encuentra más en la superficie de la estructura: los dos centinelas 
     * bajan un nivel y 'actual' empieza por esta nueva cabeza. Mientras que
     * 'actual' no llegue al centinela de cola, se elimina el apuntador al nodo
     * de arriba de 'actual'. Se decrementa el contador de listas.
     * @param elem : elemento que se desea borrar.
     */
    public void borra(T elem){
        SkipNode<T> actual = buscaAux(elem);
        SkipNode<T> izq,der;
        int listas;
        
        actual = actual.getDer();
        // Si el elemento no se encuentra en la estructura.
        if(!actual.getElem().equals(elem))
            return;
        
        // Sube las listas necesarias para borrar al elemento.
        while(actual != null){
            izq = actual.getIzq();
            der = actual.getDer();
            // Desliga
            ligaIzqDer(izq,der);
            actual = actual.getArr();
        }
        cont--;
        
        // ¿Es necesario colapsar?
        listas = (int)(Math.log(cont)/Math.log(2));
        if(numListas > listas && numListas > 1){
            cabeza = cabeza.getAb();
            cola = cola.getAb();
            actual = cabeza;
            while(actual != null){
                actual.setArr(null);
                actual = actual.getDer();
            }
            numListas--;
        }
    }
    
    
    /**
     * Método de inserción.
     * En 'actual' se llama al método {buscaAux}. Recordemos que este método
     * regresa el nodo del elemento si se encuentra en la estructura o el nodo
     * anterior a donde debería de estar si es que no se encuentra. Debido a 
     * esto podemos mantener la lógica de que siempre se va a insertar al
     * elemento después del nodo que nos devuelva el método de búsqueda.
     * 'sig' es el nodo que se encuentra después de 'actual'. Para insertar, 
     * simplemente se liga a actual con 'nuevo' y a 'nuevo' con 'sig' con el
     * método {ligaIzqDer} y se aumenta el contador de elementos.
     * Si el número ideal de listas es mayor al número de listas existentes, 
     * se crean dos nuevos centinelas, se ligan entre ellos, se ligan con los
     * centinelas anteriores.
     * Se tiran volados para ver si el elemento insertado sube a las otras 
     * listas.
     * @param elem : elemento que se desea insertar.
     */
    public void inserta(T elem){
        SkipNode<T> actual = buscaAux(elem);
        SkipNode<T> nuevo = new SkipNode(elem);
        SkipNode<T> sig = actual.getDer();
        int i;
        
        // Expande listas si es necesario
        cont++;
        if((Math.log(cont)/Math.log(2)) > numListas){
            SkipNode<T> nCabeza = new SkipNode(null);
            SkipNode<T> nCola = new SkipNode(null);
            // Liga los elementos de la nueva lista
            ligaIzqDer(nCabeza,nCola);
            // Liga las listas.
            ligaArrAb(nCabeza,cabeza);
            ligaArrAb(nCola,cola);
            // Establece la cabeza y la cola de la estructura
            cabeza = nCabeza;
            cola = nCola;
            numListas++;
        }
        
        // Inserta en la lista al elemento
        ligaIzqDer(actual,nuevo);
        ligaIzqDer(nuevo,sig);
        
        /**
         * Un volado para insertarlo arriba. [ Águila menor a 0.5 ]
         * Si cae águila y siguen existiendo listas superiores para insertar, 
         * se crea el nuevo nodo. Mientras que 'actual' no tenga un nodo arriba,
         * se mueve a la izquierda para encontrar a un nodo que sí tenga su 
         * copia en la lista de arriba. Ahora 'actual' se convierte en el nodo
         * que se encontraba arriba y en 'sig' se guarda al siguiente nodo de
         * esta lista para poder doblemente ligar a 'nuevo'  con 'actual' y 
         * 'sig' como sus vecinos. Además se liga con su copia de abajo.
         */ 
        i = 1;
        double val = Math.random();
        while(val < 0.5 && i < numListas){
            while(actual.getArr() == null)
                actual = actual.getIzq();
            SkipNode<T> n = new SkipNode<T>(elem);
            actual = actual.getArr();
            sig = actual.getDer();
            ligaIzqDer(actual,n);
            ligaIzqDer(n,sig);
            ligaArrAb(n,nuevo);
            nuevo = n;
            val = Math.random();
            i++;
        }
    }
    
    
    /**
     * Método que cuenta el número de elementos que existen por lista.
     * @return 
     */
    public ArrayList<Integer> numElemXList(){
        ArrayList<Integer> res = new ArrayList();
        SkipNode<T> c, actual;
        int cont;
        
        // Se llega a la lista inferior
        c = cabeza;
        while(c.getAb() != null){
            c = c.getAb();
        }
        
        // Se hace el conteo lista por lista.
        while(c != null){
            actual = c.getDer();
            cont = 0;
            while(actual.getElem() != null){
                cont++;
                actual = actual.getDer();
            }
            res.add(cont);
            c = c.getArr();
        }
        return res;
    }
    
    
    /**
     * Método que regresa el pilar más cercano de la estructura.
     */
    public T pilar(){
        SkipNode<T> actual, c, res; 
        boolean var = false;
        int i;
        
        res = new SkipNode(null);
        actual = cabeza;
        c = actual;
        i = 0;
        while(!var && i < numListas){
            actual = actual.getDer();
            while(!var && actual != null){
                if(actual.getElem() != null){
                    res = actual;
                    var = true;
                }
                actual = actual.getDer();
            }
            actual = c.getAb();
            c = c.getAb();
            i++;
        }
        return res.getElem();
    }
    
    /**
     * Método de toString.
     */
    public void toStrRes(){
        StringBuilder cad = new StringBuilder();
        SkipNode<T> c, actual;
        
        // Se llega hasta la lista más interior.
        c = cabeza;
        while(c.getAb() != null)
            c = c.getAb();
        
        cabeza = c;
        cabeza.setArr(null);
        actual = cabeza.getDer();
        while(actual.getElem() != null){
            cad.append(actual.getElem()+"  ");
            actual.setArr(null);
            actual = actual.getDer();
        }
        actual.setArr(null);
        cola = actual;
        numListas = 1;
        
        System.out.println("\t"+cad.toString());
        
        reestructura();
    }
    
    
    /**
     * Método toString por niveles.
     */
    public void toStrComp(){
        SkipNode<T> c, actual;
        
        // Se llega hasta la lista más interior.
        c = cabeza;
        while(c.getAb() != null)
            c = c.getAb();
        
        // toString de la lista
        while(c != null){
            StringBuilder cad = new StringBuilder();
            actual = c.getDer();
            while(actual.getElem() != null){
                cad.append(actual.getElem()+"  ");
                actual = actual.getDer();
            }
            System.out.println("\t"+cad.toString());
            c = c.getArr();
        }
    }
    
    
    /**
     * Método para reestructurar el SkipList.
     */
    public void reestructura(){
        SkipNode<T> actual,cab,c,r, tail;
        double n;
        int i;
        
        n = (Math.log(cont)/Math.log(2))+1;
        while(numListas < n){
            cab = cabeza;
            c = new SkipNode(cab.getElem());
            ligaArrAb(c,cab);
            cabeza = c;
            r = c;
            actual = cab.getDer();
            i = 1;
            while(actual.getElem() != null){
                if(i%2 == 0){
                    SkipNode<T> a = new SkipNode(actual.getElem());
                    ligaArrAb(a,actual);
                    ligaIzqDer(r,a);
                    r = a;
                }
                actual = actual.getDer();
                i++;
            }
            tail = cola;
            SkipNode<T> t = new SkipNode(tail.getElem());
            ligaArrAb(t,tail);
            ligaIzqDer(r,t);
            cola = t;
            numListas++;
        }
    }
}
