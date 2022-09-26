/**
 * Clase Árbol.
 */
package arboles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import pilas.*;

/**
 * 15 febrero, 2021.
 * @author Murillo
 */
public class LinkedBinaryTree <T extends Comparable<T>> implements LinkedBinaryTreeADT <T> {
    
    // Atributos
    private BinaryNode raiz;
    private int cont = 0;
    
    // Constructores
    public LinkedBinaryTree (){
        raiz = null;
    }
    public LinkedBinaryTree(T elem){
        raiz = new BinaryNode(elem);
        cont++;
    }
    
    // Método is empty.
    public boolean isEmpty(){
        boolean res = false;
        
        if(cont == 0){
            res = true;
        }
        return res;
    }
    
    // Método cantidad de elementos en el árbol.
    public int size(){
        return cont;
    }
    
    /**
     * Método Pre-Order.
     *  - Visita nodo.
     *  - Recorre hijo izquierdo.
     *  - Recorre hijo derecho.
     * @param actual : nodo en el que nos encontramos.
     */
    private void preOrder(BinaryNode<T> actual, ArrayList<T> lista){
        
        if (actual == null)
		return;
        lista.add(actual.getElem());
	preOrder(actual.getIzq(),lista);
	preOrder(actual.getDer(),lista);    
    }
    public Iterator<T> preOrder(){
        ArrayList<T> lista = new ArrayList<T>();
        
        preOrder(raiz,lista);
        return lista.iterator();
    }
    // De manera iterativa.
    public void preOrderIt(){
        ArrayStack<BinaryNode<T>> pila = new ArrayStack<BinaryNode<T>>();
        ArrayList<T> lista = new ArrayList<T>();
        BinaryNode<T> actual;
        
        pila.push(raiz);
        while(!pila.isEmpty()){
            actual = pila.pop();
            lista.add(actual.getElem());
            if(actual.getIzq() != null)
                pila.push(actual.getIzq());
            if(actual.getDer() != null)
                pila.push(actual.getDer());
        }
    }
    
    
    /**
     * Método In-Order. 
     *  - Recorre hijo izquierdo.
     *  - Visita nodo.
     *  - Recorre hijo derecho.
     * @param actual : nodo en el que nos encontramos.
     */
    private void inOrder(BinaryNode<T> actual, ArrayList<T> lista){
        
        if (actual == null)
		return;
	inOrder(actual.getIzq(), lista);
        lista.add(actual.getElem());
	inOrder(actual.getDer(), lista);    
    }
    public Iterator<T> inOrder(){
        ArrayList<T> lista = new ArrayList<T>();
        
        inOrder(raiz,lista);
        return lista.iterator();
    }
    
    
    /**
     * Método Post-Order.
     *  - Recorre hijo izquierdo.
     *  - Recorre hijo derecho.
     *  - Visita nodo.
     * @param actual : nodo en el que nos encontramos.
     */
    private void postOrder(BinaryNode<T> actual, ArrayList<T> lista){
        
        if (actual == null)
		return;
	postOrder(actual.getIzq(),lista);
	postOrder(actual.getDer(),lista);    
        lista.add(actual.getElem());
    }
    public Iterator<T> postOrder(){
        ArrayList<T> lista = new ArrayList<T>();
        
        postOrder(raiz,lista);
        return lista.iterator();
    }
    
    
    /**
     * Método busca.
     * Empieza a buscar por la raíz. Como es un método recursivo, se presentan
     * los siguientes casos para cada uno de los nodos que se van recorriendo.
     *  - Caso nulo : no se encuentra al elemento en el árbol. También marca
     *                la pauta para ir avanzando entre los hijos de los nodos.
     *  - Caso equals : si se encuentra al elemento simplemente regresa al nodo
     *                  en el cual se encuentra.
     *  - Caso izquierdo: en la variable 'aux' se busca al elemento en el hijo
     *                    izquierdo del nodo. Si 'aux' no es null, significa que
     *                    lo encontró y lo regresa (nodo hijo izquierdo).
     *  - Caso derecho: Análogo al caso izquierdo. 
     * Desde un punto de vista, podemos notar que se hace un recorrido Pre-Order
     * @param elem : elemento que estamos buscando.
     * @param actual : sirve para recorrer el árbol de manera recursiva.
     * @return El nodo si es que existe. Si no, regresa null.
     */
    private BinaryNode<T> busca(T elem, BinaryNode<T> actual){
        BinaryNode<T> aux;
        
        if (actual == null)                 // Caso nulo
          return null;

        if(actual.getElem().equals(elem))   // Caso equals
          return actual;
        
        aux = busca(elem,actual.getIzq());  // Caso izquierdo
        if(aux != null)
          return aux;
        
        aux = busca(elem,actual.getDer());  // Caso derecho
        return aux;

    }
    public boolean find(T elem){
        return busca(elem,raiz) != null;
    }
    
    
    /**
     * Método cantidad de niveles.
     * Como es un método recursivo, vamos a analizarlo desde el punto de vista 
     * de un solo nodo.
     *  - Si el nodo en el que nos encontramos es nulo, se regresa un 0 porque no
     *    hay un nivel que contar.
     *  - Como un nodo puede tener dos hijos, se hace un llamado recursivo con
     *    el hijo izquierdo y otro con el hijo derecho. Estos llamados se hacen
     *    para llamár a la función {max}. Esto es porque se hace un recuento de
     *    la cantidad de niveles que tiene el su-árbol de los hijos del nodo. La
     *    función matemática se hace para saber el número de niveles del "camino
     *    más largo" entre los dos sub-árboles hijos.
     * Por último, se regresa 1 (por la raíz) + la cantidad máxima entre ambos 
     * subárboles.
     * @param actual : sirve para recorrer el árbol de manera recursiva.
     * @return cantidad de niveles que tiene el árbol.
     */
    private int lvlsAux(BinaryNode<T> actual){
        int val, izq, der;
        
        if(actual == null)
            return 0;
        izq = lvlsAux(actual.getIzq());
        der = lvlsAux(actual.getDer());
        val = Math.max(izq, der);
        return 1 + val;
    }
    // Empieza desde la raíz.
    public int niveles(){
        return lvlsAux(raiz);
    }
    // Empeiza desde un nodo dado.
    public int niveles(BinaryNode<T> nodo){
        return lvlsAux(nodo);
    }
    
    
    /**
     * Método antecesor común.
     * El método empieza a recorrer el árbol por medio de la raíz y la lógica
     * es sencilla. Una vez que tratamos con un árbol binario de búsqueda, 
     * podemos jugar con la propiedad de que los elementos más chicos de un nodo
     * estarán depositados en su subárbol izquierdo y los más grandes en el 
     * derecho. Ese es el criterio que seguimos
     *  - Si el nodo es nulo, nos encontramos con el hijo de una hoja
     *  - Si el elemento del nodo en el que nos encontramos es mayor que ambos
     *    elementos, entonces llamamos recursivamente al método con el hijo izq.
     *  - Si el elemento del nodo es más chico que ambos elementos, llamamos
     *    recursivamente al método con el hijo derecho. 
     *  - Si no es ninguno de los casos anteriores, entonces uno de sus los 
     *    elementos se encuentra en el subárbol izquierdo y el otro en el
     *    deecho; por lo tanto, lo regresamos.
     */
    private BinaryNode<T> ancestorAux(BinaryNode<T> actual, T e1, T e2){
        T act;
        
        if(actual == null)
            return null;
        
        act = actual.getElem();
        if(act.compareTo(e1) > 0 && act.compareTo(e2) > 0)
            return ancestorAux(actual.getIzq(), e1, e2);
        
        if(act.compareTo(e1) < 0 && act.compareTo(e2) < 0)
            return ancestorAux(actual.getDer(), e1, e2);
        
        return actual;
    }
    public BinaryNode<T> ancestor(T e1, T e2){
        BinaryNode<T> ancestor;
        T anc;
        
        ancestor = ancestorAux(raiz, e1, e2);
        anc = ancestor.getElem();
        // Si el antecesor común es alguno de los elementos dados como param.
        if(anc.equals(e1) || anc.equals(e2))
            // Si es la raíz, la regresamos.
            if(ancestor.equals(raiz))
                return ancestor;
            // Si no es la raíz, regresamos al papá.
            else
                return ancestor.getPa();
        return ancestor;
    }
    
    
    /**
     * Método inserción.
     * La variable 'actual' va a permitir el recorrido por las ramas del árbol, 
     * es por ello que empieza con la raíz. La variable 'pa' va a recordar al 
     * nodo 'actual', que al final del método va a ser el papá del nodo que
     * insertamos. 
     * Mientras que 'actual' no sea nulo, se compara el valor del elemento a 
     * insertar con el valor de actual. Si el elemento es más chico, 'actual' va
     * a pasar a ser el hijo izquierdo; si no, va a ser el derecho. En cada vuelta
     * de la estructura algorítmica se guarda primeramente al papá.
     * Cuando se llegue a que 'actual' es nulo significa que llegó a la posición
     * donde se debe insertar, pero recordemos que la inserción es la creación de
     * un apuntador del papá al nuevo nodo. Es por ello que se recuerda a 'actual'
     * como 'pa'. Finalmente colgamos al nuevo nodo en 'pa' y aumentamos el
     * contador de los elementos del árbol.
     * @param elem : elemento a insertar.
     */
    public void inserta(T elem){
        BinaryNode<T> actual = raiz;
        BinaryNode<T> pa = null;
        BinaryNode<T> nuevo = new BinaryNode<T>(elem);
        
        if(raiz == null){
            raiz = nuevo;
            cont++;
            return;
        }
        
        while(actual != null){
            pa = actual;
            if(elem.compareTo(actual.getElem()) <= 0)
                actual = actual.getIzq();
            else
                actual = actual.getDer();
        }
        pa.cuelga(nuevo);
        cont++;
    }
    
    
    /**
     * Método de borrado de elemento.
     * En la variable 'actual' se va a buscar al nodo en el que se encuentra el
     * elemento que queremos borrar del árbol. En la variable 'pa' se va a guardar
     * al papá del nodo encontrado, si es que se encuentra.
     * Para saber qué se hace en cada caso, ver los apuntes pertinentes.
     * @param elem : elemento que se desea borrar.
     */
    public BinaryNode<T> borra(T elem){
        BinaryNode<T> actual = busca(elem,raiz);
        BinaryNode<T> temp;
        
        /** 
         * Caso : no existe.
         */
        if(actual == null)
            return null;    // O se puede lanzar una excepción.
        
        BinaryNode<T> pa = actual.getPa();
        
        /**
         * Caso : es una hoja.
         */
        if(actual.getIzq() == null && actual.getDer() == null){  
            if(actual.getPa() == null){     // Si es la raíz
                raiz = null;
            }
            else                            // No es la raíz
                pa.eliminaSubArb(elem);
            cont--;
            return pa;
        }
        
        /**
         * Caso : tiene un hijo.
         */
        if(actual.getIzq() == null || actual.getDer() == null){
            if(actual == raiz){             // Si es la raíz
                if(actual.getIzq() == null) // ¿Qué pasa con la raíz anterior?
                    raiz = actual.getDer(); // ¿Se queda como padre de la nueva raíz?
                else
                    raiz = actual.getIzq();
                raiz.setPa(null);
            }
            
            else                            // No es la raíz
                if(actual.getIzq() != null)
                    pa.cuelga(actual.getIzq());
                else 
                    pa.cuelga(actual.getDer());
            
            cont--;
            return pa;    
        }
        
        /**
         * Caso : tiene dos hijos.
         */
        BinaryNode<T> sucesor = actual.getDer();
        BinaryNode<T> paSuc = sucesor.getPa();
        while(sucesor.getIzq() != null){
            sucesor = sucesor.getIzq();
            paSuc = sucesor.getPa();
        }
        actual.setElem(sucesor.getElem());
        if(sucesor != actual.getDer())
            sucesor.getPa().setIzq(sucesor.getDer());
        else {
            sucesor.getPa().setDer(sucesor.getDer());
            actual.actFactor(-2);
        }
        if(sucesor.getDer() != null)
                sucesor.getDer().setPa(sucesor.getPa());
        cont--;
        return paSuc;
    }
    
    
    /**
     * Método que muestra factores de equilibrio.
     * En un recorrido in-order imprime al elemento y su factor de equilibrio
     * empezando por la raíz.
     */
    private void factores(BinaryNode<T> actual){
        
        if (actual == null)
		return;
	factores(actual.getIzq());
        System.out.println(actual.getElem());
        System.out.println("\t"+actual.getFactor());
	factores(actual.getDer());    
    }
    public void factores(){
        
        factores(raiz);
        return;
    }
    
    
    /**
     * Método inserción en Árbol AVL.
     * La primera parte del método es un copy-paste del método de inserción
     * arriba programado. Lo diferente tiene que ver con los factores de equilibrio.
     * Los factores de equilibrio del padre se actualizan de manera ascendente por 
     * la rama del padre del elemento que se inserta. Si en el ascenso el nodo 
     * 'actual' es el hijo izquierdo, se le resta 1 al factor de equilibrio del 
     * padre; si no, se suma 1. El recorrido se hace por medio de los padres.
     * En cada vuelta se verifica si se tiene que hacer una rotación de algún 
     * subárbol. El recorrido termina en tres casos: si se hace una rotación, si
     * se llega a la raíz o si el factor de equilibrio del padre se hace 0.
     * @param elem : elemento que se desea insertar. 
     */
    public void insertaAVL(T elem){
        BinaryNode<T> actual = raiz;
        BinaryNode<T> pa = null;
        BinaryNode<T> nuevo = new BinaryNode<T>(elem);
        boolean var = false;
        
        if(raiz == null){
            raiz = nuevo;
            cont++;
            return;
        }
        
        // Inicio inserción normal.
        while(actual != null){
            pa = actual;
            if(elem.compareTo(actual.getElem()) <= 0)
                actual = actual.getIzq();
            else
                actual = actual.getDer();
        }
        pa.cuelga(nuevo);
        cont++;
        // Fin inserción normal.
        
        // Actualización de factores de equilibrio.
        actual = nuevo;
        pa = actual.getPa();
        while(pa != null && !var){
            if(actual == pa.getIzq())
                pa.actFactor(-1);
            else 
                pa.actFactor(1);
            
            if(pa.getFactor() == -2 || pa.getFactor() == 2){
                pa = rotacion(pa);
                var = true;
            }
            else
                if(pa.getFactor() == 0)
                    var = true;
                else {
                    actual = pa;
                    pa = actual.getPa();
                }
        }
    }
    
    
    /**
     * Método de borrado en árbol AVL.
     * Para empezar, se hace un llamado al método {borra} en la variable 'actual'.
     * Este llamado se hace para no tener que repetir todos los casos del borrado.
     * El llamado regresa al papá del elemento borrado o del sucesor in-order, 
     * dependiendo del caso que se haya ejecutado. Debido a que actual es el padre,
     * se hace una primera actualización de su factor de equilibrio, para ya después
     * continuar con el recorrido ascendente usual. 
     * Si en el ascenso el nodo 'actual' es el hijo izquierdo del nodo 'pa', se le
     * suma 1 al factor de equilibrio de este último; si no, se resta 1. El recorrido
     * se hace por medio de los padres. en cada vuelta se verifica si se tiene que 
     * hacer una rotación. El recorrido termina en tres casos: cuando se llega a la
     * raíz, cuando se hace una rotación o cuando el factor de equilibrio del padre
     * se hace -1 o 1. 
     * @param elem : elemento que se desea borrar
     */
    public void borraAVL(T elem){
        BinaryNode<T> actual = borra(elem);
        boolean var = false;
        
        //Caso: no existe o no es necesario actualizar fe's.
        if(actual == null)
            return;
        
        // Primera actualización, pues el llamado al método {borra} regresa
        // al padre del elemento que borramos.
        if(elem.compareTo(actual.getElem()) < 0)
            actual.actFactor(1);
        else
            actual.actFactor(-1);
        if(actual.getFactor() == -2 || actual.getFactor() == 2){
                actual = rotacion(actual);
            }
        if(actual.getFactor() == -1 || actual.getFactor() == 1)
                    var = true;
        
        // Actualización de factores de equilibrio.
        BinaryNode<T> pa = actual.getPa();
        while(pa != null && !var){
            if(actual == pa.getIzq())
                pa.actFactor(1);
            else
                pa.actFactor(-1);
            
            // Si se necesita hacer una rotación.
            if(pa.getFactor() == -2 || pa.getFactor() == 2){
                pa = rotacion(pa);
                var = true;
            }
            else
                if(pa.getFactor() == -1 || pa.getFactor() == 1)
                    var = true;
                else {
                    actual = pa;
                    pa = actual.getPa();
                }
        }
        
    }
    
    /**
     * Método de rotación.
     * @param actual : el nodo que tiene un factor de equilibrio de -2 o +2.
     * @return : la raíz del nuevo subárbol.
     */
    private BinaryNode<T> rotacion(BinaryNode<T> actual){
        BinaryNode<T> alpha,beta,gamma,A,B,C,D;
        BinaryNode<T> pa = actual.getPa();
        
        int factor = actual.getFactor();
        
        // Caso: izquierda-izquierda.
        if(factor == -2 && (actual.getIzq().getFactor() == -1 || actual.getIzq().getFactor() == 0)){
            alpha = actual;
            beta = alpha.getIzq();
            gamma = beta.getIzq();
            A = gamma.getIzq();
            B = gamma.getDer();
            C = beta.getDer();
            D = alpha.getDer();
            
            if(A == null)
                gamma.setIzq(null);
            else
                gamma.cuelga(A);
            if(B == null)
                gamma.setDer(null);
            else
                gamma.cuelga(B);
            if(C == null)
                alpha.setIzq(null);
            else
                alpha.cuelga(C);
            if(D == null)
                alpha.setDer(null);
            else
                alpha.cuelga(D);
            beta.cuelga(gamma);
            beta.cuelga(alpha);
            if(pa == null){
                raiz = beta;
                beta.setPa(null);
            }
            else
                pa.cuelga(beta);
            
            if(beta.getFactor() == -1){
                alpha.setFactor(0);
                beta.setFactor(0);
            }
            else {
                alpha.setFactor(-1);
                beta.setFactor(1);
            }
            return beta;
        }
        
        // Caso: izquierda-derecha.
        if(factor == -2 && actual.getIzq().getFactor() == 1){
            alpha = actual;
            beta = alpha.getIzq();
            gamma = beta.getDer();
            A = beta.getIzq();
            B = gamma.getIzq();
            C = gamma.getDer();
            D = alpha.getDer();
            
            if(A == null)
                beta.setIzq(null);
            else
                beta.cuelga(A);
            if(B == null)
                beta.setDer(null);
            else
                beta.cuelga(B);
            if(C == null)
                alpha.setIzq(null);
            else
                alpha.cuelga(C);
            if(D == null)
                alpha.setDer(null);
            else
                alpha.cuelga(D);
            gamma.cuelga(beta);
            gamma.cuelga(alpha);
            if(pa == null){
                raiz = gamma;
                gamma.setPa(null);
            }
            else 
                pa.cuelga(gamma);
            
            switch (gamma.getFactor()) {
                case 0:
                    alpha.setFactor(0);
                    beta.setFactor(0);
                    break;
                case -1:
                    alpha.setFactor(1);
                    beta.setFactor(0);
                    gamma.setFactor(0);
                    break;
                default:
                    alpha.setFactor(0);
                    beta.setFactor(-1);
                    gamma.setFactor(0);
                    break;
            }
            return gamma;
        }
        
        // Caso: derecha-derecha.
        if(factor == 2 && (actual.getDer().getFactor() == 1) || actual.getDer().getFactor() == 0){
            alpha = actual;
            beta = alpha.getDer();
            gamma = beta.getDer();
            A = alpha.getIzq();
            B = beta.getIzq();
            C = gamma.getIzq();
            D = gamma.getDer();
            
            if(A == null)
                alpha.setIzq(null);
            else
                alpha.cuelga(A);
            if(B == null)
                alpha.setDer(null);
            else
                alpha.cuelga(B);
            if(C == null)
                gamma.setIzq(null);
            else
                gamma.cuelga(C);
            if(D == null)
                gamma.setDer(null);
            else
                gamma.cuelga(D);
            beta.cuelga(alpha);
            beta.cuelga(gamma);
            if(pa == null){
                raiz = beta;
                beta.setPa(null);
            }
            else
                pa.cuelga(beta);
            
            if(beta.getFactor() == 1){
                alpha.setFactor(0);
                beta.setFactor(0);
            }
            else {
                alpha.setFactor(1);
                beta.setFactor(-1);
            }
            return beta;
        }
        
        
        // Caso: derecha-izquierda.
        if(factor == 2 && actual.getDer().getFactor() == -1){
            alpha = actual;
            beta = alpha.getDer();
            gamma = beta.getIzq();
            A = alpha.getIzq();
            B = gamma.getIzq();
            C = gamma.getDer();
            D = beta.getDer();
            
            if(A == null)
                alpha.setIzq(null);
            else 
                alpha.cuelga(A);
            if(B == null)
                alpha.setDer(null);
            else 
                alpha.cuelga(B);
            if(C == null)
                beta.setIzq(null);
            else
                beta.cuelga(C);
            if(D == null)
                beta.setDer(null);
            else
                beta.cuelga(D);
            gamma.cuelga(alpha);
            gamma.cuelga(beta);
            if(pa == null){
                raiz = gamma;
                gamma.setPa(null);
            }
            else 
                pa.cuelga(gamma);
            
            switch (gamma.getFactor()) {
                case 0:
                    alpha.setFactor(0);
                    beta.setFactor(0);
                    break;
                case -1:
                    alpha.setFactor(0);
                    beta.setFactor(1);
                    gamma.setFactor(0);
                    break;
                default:
                    alpha.setFactor(-1);
                    beta.setFactor(0);
                    gamma.setFactor(0);
                    break;
            }
            return gamma;
        }
        
        return actual;
    }
    
    /**
     * Método de arreglo por mitades para generar árbol completo. -------------------------
     */
    private void mitades(T[] arr, int min, int max, ArrayStack<T> pila){
        int mit;
        
        if(min >= max)
            return;
        mit = (min + max)/2;
        pila.push(arr[mit]);
        mitades(arr, min, mit,pila);
        mitades(arr, mit+1, max,pila);
    }
    public void mitades(T[] arr, T[] res){
        ArrayStack<T> pila = new ArrayStack<T>();
        ArrayStack<T> aux = new ArrayStack<T>();
        int i;
        
        mitades(arr, 0, arr.length-1, pila);
        
        while(!pila.isEmpty())
            aux.push(pila.pop());
        
        i = 0;
        while(!aux.isEmpty()){
            res[i] = aux.pop();
            i++;
        }
    }
    
    /**
     * Método que toma dos valores aleatorios de un arreglo. -------------------------
     */
    public void selector(T[] arr, T[] res){
        Random rand = new Random();
        int v1, v2;
        
        v1 = rand.nextInt(arr.length);
        v2 = rand.nextInt(arr.length);
        while(v1 == v2)
            v2 = rand.nextInt(arr.length);
        res[0] = arr[v1];
        res[1] = arr[v2];
    }
}
