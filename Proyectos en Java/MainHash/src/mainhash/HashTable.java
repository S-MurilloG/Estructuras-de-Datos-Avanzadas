/**
 * Implementación de la clase Tablas de Hash
 */
package mainhash;

/**
 * 21 abril, 2021.
 * @author Murillo
 */
public class HashTable <T> {
    
    // Atributos
    private HashNode<T>[] tabla;
    private final double factorCarga;     // Nos dice cuándo es buena idea expandir.
    private int cont;
    
    // Constructor
    public HashTable(){
        tabla = creaTabla(10);
        factorCarga = 0.7;
        cont = 0;
    }
    public HashTable(int t){
        tabla = creaTabla(t);
        factorCarga = 0.7;
        cont = 0;
    }
    
    // Algunos Getters
    public HashNode<T>[] getTabla(){
        return tabla;
    }
    public int getCont(){
        return cont;
    }
    
    // Creación de tabla
    private HashNode<T>[] creaTabla(int t){
        HashNode<T>[] tablaAux =  new HashNode[t];
        
        for(int i=0;i<tablaAux.length;i++)
            tablaAux[i] = new HashNode(null);
        return tablaAux;
    }
    
    // Inserta
    public void inserta(T e){
        HashNode<T> nuevo = new HashNode(e);
        HashNode<T> aux;
        int pos;
        
        pos = nuevo.funcHash()%tabla.length;
        // Se inserta en (pos % tabla.lenght)
        aux = tabla[pos].getNext();
        tabla[pos].setNext(nuevo);
        nuevo.setNext(aux);
        cont++;
        
        // Se expande si es necesario
        if((cont/tabla.length) > factorCarga)
            expande();
    }
    
    // Método de expansión.
    private void expande(){
        HashNode<T>[] tablaAux, aux = tabla;
        HashNode<T> actual;
        
        tablaAux = creaTabla(tabla.length*2);
        tabla = tablaAux;
        tablaAux = aux;
        
        for(int i=0;i<tablaAux.length;i++){
            actual = tablaAux[i].getNext();
            while(actual != null){
                inserta(actual.getElem());
                actual = actual.getNext();
            }
        }
    }
    
    // Método de búsqueda.
    public boolean busca(T e){
        HashNode<T> actual;
        boolean var = false;
        int pos;
        
        pos = e.hashCode()%tabla.length;
        actual = tabla[pos].getNext();
        while(!var && actual != null){
            if(actual.getElem().equals(e))
                var = true;
            actual = actual.getNext();
        }
        return var;
    }
    
    // Método de borrado.
    public void borra(T e){
        HashNode<T> actual, aux;
        int pos;
        
        pos = e.hashCode()%tabla.length;
        actual = tabla[pos].getNext();
        while(actual != null && !actual.getNext().getElem().equals(e))
            actual = actual.getNext();
        if(actual != null && actual.getElem().equals(e)){
            aux = actual.getNext();
            actual.setNext(aux);
            cont--;
        }
    }
    
    
    
    
    /**
     * Método de cardinalidad de intersección de m arreglos.
     * No precisamente los m arreglos tienen que tener n elementos, pueden ser
     * de diferentes tamaños y funciona bien.
     * Único problema: si en los arreglos se encuentran elementos repetidos, 
     * aumenta el contador, entonces la intersección queda también con elementos
     * repetidos. Faltaría mejorar esta parte, pero si los elementos NO se repiten,
     * funciona perfecto.
     * @param mat : matriz que puede tomar valores diferentes.
     * @return : el número de elementos que se encuentran en la intersección
     *           de los m arreglos.
     */
    public int intersection(T[][] mat){
        T[] reng;
        T e;
        int m,n, res = -1;
        
        // Se crea la primera tabla de Hash con el primer arreglo [le llamaremos 
        // 'antigua']
        reng = mat[0];
        tabla = creaTabla(reng.length);
        n = 0;
        while(n < reng.length){
            inserta(reng[n]);
            n++;
        }
        
        // Se van recorriendo los arreglos empezando por el segundo
        // hasta llegar al último arreglo.
        m = 1;
        while(m < mat.length){
            reng = mat[m];
            // Se crea una nueva tabla de hash para los nuevos valores [le 
            // llamaremos 'nueva']
            HashTable<T> tablaAux = new HashTable(reng.length);
            n = 0;
            // Se recorre el arreglo m (en el que nos encontramos)
            while(n < reng.length){
                // Si el elemento en el arreglo 'm' se encuentra en la
                // tabla 'antigua', se inserta en la tabla 'nueva'
                if(busca(reng[n]))
                   tablaAux.inserta(reng[n]);
                n++;
            }
            // La tabla 'nueva' se convierte en la tabla 'antigua' para comparar
            // los elementos que se encuentran en el siguiente arreglo.
            tabla = tablaAux.getTabla();
            // Se toma el contador de la tabla nueva para "renovar" el contador.
            cont = tablaAux.getCont();
            m++;
        }
        return res = this.getCont();
    }
}
