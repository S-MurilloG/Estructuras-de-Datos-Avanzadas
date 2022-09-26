/**
 * Clase Heaps.
 * Se implementa la estructura de datos MIN-HEAP utilizando un arreglo.
 */
package arboles;

/**
 * 22 marzo, 2021.
 * @author Murillo
 */
public class Heaps <T extends Comparable<T>> {
    
    // Atributos.
    private Object[] datos;
    private int cont;
    
    // Constructores.
    public Heaps(){
        cont = 0;
        datos = new Object[100]; 
    }
    public Heaps(T elem){
        cont = 1;
        datos = new Object[100];
        datos[cont] = elem;
    }
    
    
    /**
     * Método de inserción.
     * Se inserta al elemento en la siguiente casilla vacía del arreglo. Se 
     * guarda la posición del elemento 'actual' y la de su 'pa' [que es el 
     * resultado de dividir entre dos la de actual y quedarnos con la parte
     * entera]. 
     * Mientras que la posición de 'pa' no sea cero, se va a verificar una
     * propiedad de la estructura: que para cada subárbol, la raíz es el 
     * elemento más chico. Si 'actual' es más chico que 'pa', se hace un 
     * swap de los dos elementos y se continúa subiendo por el árbol para
     * checar que se siga cumpliendo la condición. Si no es más chico 'actual'
     * que 'pa', no hay nada que hacer [por eso la bandera de fin: 'var'].
     * @param elem : elemento que se desea insertar en la estructura
     */
    public void inserta(T elem){
        boolean var = false;
        int actual, pa;
        T a,p,aux;
        
        // Se inserta al elemento en la estructura.
        cont++;
        datos[cont] = elem;
        
        // Se empieza a recorrer la estructura de manera ascendente.
        actual = cont;
        pa = cont >> 1;
        while(pa != 0 && !var){
            a = (T)datos[actual];
            p = (T)datos[pa];
            // Chequeo si se necesita hacer un swap.
            if(a.compareTo(p) < 0){
                aux = (T)datos[pa];
                datos[pa] = datos[actual];
                datos[actual] = aux;
            }
            else
                var = true;
            pa >>= 1;   // pa = pa >> 1
            actual >>= 1;
        }
    }
    
    
    /**
     * Método de borrado del mínimo.
     * Como el mínimo de la estructura se encuentra en la raíz, se sustituye
     * al elemento que está en la posición 1 del arrelo por el que se encuentra
     * en la última posición del mismo y se decrementa al contador.
     * Heapificación: se empieza por la raíz y se recorre la estructura de
     * manera descendente. Se obtiene la posición de los dos hijos de 'actual'.
     *  - Si 'actual' es una hoja, terminamos de recorrer la estructura. 
     *  - Si solo tiene un hijo [el izquierdo], se verifica que este sea mayor 
     *    a 'actual' y se termina de recorrer. 
     *  - Si tiene dos hijos, se encuentra la posición del elemento menor entre
     *    los hijos y se compara con 'actual' para ver si se requiere hacer un
     *    swap entre ellos dos. Si no se necesita, terminamos de recorrer.
     * Se regresa al elemento borrado.
     * @return : elemento borrado.
     */
    public T borraMin(){
        int actual, izq, der, min;
        boolean var = false;
        T a,i,d,borr,m;
        
        // Borrado del primer elemento.
        borr = (T)datos[1];
        datos[1] = datos[cont];
        cont--;
        
        // Se empieza a recorrer la estructura de manera descendente.
        actual = 1;
        while(!var){
            izq = actual << 1;
            der = (actual << 1) +1;
            
            // Caso es una hoja.
            if(izq > cont)
                var = true;
            
            else{
                
                // Caso tiene solo un hijo.
                if(der > cont){
                    a = (T)datos[actual];
                    i = (T)datos[izq];
                    if(a.compareTo(i) > 0)
                        swap(datos,actual,izq);
                    var = true;
                }
                
                // Caso tiene dos hijos.
                else{
                    i = (T)datos[izq];
                    d = (T)datos[der];
                    // Encontrar la posición del mínimo valor entre los hijos.
                    if(i.compareTo(d) < 0)
                        min = izq;
                    else
                        min = der;
                    // Chequeo si necesita hacer un swap.
                    m = (T)datos[min];
                    a = (T)datos[actual];
                    if(m.compareTo(a) < 0){
                        swap(datos,actual,min);
                        actual = min;
                    }
                    else
                        var = true;
                }
            }
        }
        
        // Regresa elemento borrado.
        return borr;
    }
    
    
    /**
     * Método swap.
     * Intercambia los elementos de dos posiciones diferentes en un arreglo.
     */
    private void swap(Object[] arr, int i, int j){
        T aux;
        
        aux = (T)datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
    }
}
