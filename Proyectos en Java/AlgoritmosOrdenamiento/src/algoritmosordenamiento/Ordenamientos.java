/**
 * Códigos principales para ordenamientos.
 */
package algoritmosordenamiento;
import java.util.Random;

/**
 * 3 de febrero, 2021.
 * @author Murillo
 */
public class Ordenamientos <T extends Comparable<T>>{
    
    int st = 0;
    
    /**
     * Método ordenamiento Selection Sort.      [ Ver apuntes ]
     * For interno:    Se recorre todo el arreglo en búsqueda del elemento "más chico".
     * Para ello, 'min' toma el valor del primer elemento del arreglo y mientras
     * se hace el recorrido, se compara y se decide si se actualiza el valor. Una
     * vez terminado el recorrido, se hace un 'swap' del 'min' con el primer elemento
     * del arreglo. 
     * For externo:    Se hace el mismo procedimiento anterior pero con el arreglo
     * empezando desde el siguiente elemento. Así hasta llegar al último
     * @param arr : arreglo a ordenar
     */
    public void selectionSort(T[] arr){
        int min;
        T temp;
        
//        st = 0;
        for(int j=0;j<arr.length-1;j++){
            min = j;
            for(int i=j+1;i<arr.length;i++){
                if(arr[min].compareTo(arr[i]) > 0)
                    min = i;
//                st++;
            }
            temp=arr[min];
            arr[min]=arr[j];
            arr[j]=temp;
        }
//        System.out.println(st);
    }
    
    /**
     * Método ordenamiento Insertion Sort.      [ Ver apuntes ]
     * For exterior:    Va a permitir recorrer el arreglo en "ventanas", es decir, en
     * la vuelta 1 va a tomar el primer elemento; en la vuelta 2 tomará los dos primeros
     * elementos; así será hasta que en la vuelta n tome los n elementos.
     * While interior:  Se encargará de hacer la inserción del dato. Lo que pasa es que
     * el dato nuevo de cada vuelta del For va a ser comparado de manera regresiva con 
     * los elementos que tiene antes de él y hará un 'swap' en el momento indicado.
     * De esta manera, cada vez que termina un While, la ventana correspondiente al For
     * va a estar ordenada.
     * @param arr : arreglo a ordenar
     */
    public void insertionSort(T[] arr){
        int j;
        T temp;
        
//        st = 0;
        for(int i=1;i<arr.length;i++){
            j=i;
//            st++;
            while(j>=1 && arr[j].compareTo(arr[j-1]) < 0){
//                if(j != i)
//                    st++;
                temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            }
        }
//        System.out.println(st);
    }
    
    /**
     * Método ordenamiento Bubble Sort.
     * For exterior:    Permite recorrer el arreglo y hacer el ordenamiento en cada
     * vuelta. El método es llamado de 'burbuja' porque el resultado de cada vuelta
     * es que el elemento más grande va a "elevarse" por el arreglo hasta llegar al final.
     * For interior:    Permite el ordenamiento. Este for se hace desde el inicio hasta
     * el final del arreglo. Se compara en pares de elemento: el 1 con 2, 2 con 3,
     *  n-1 con n. En esta comparación se ordena por pares. De esta manera es como el
     * elemento más grande va a salir a flote.
     * Cada vuelta del primer For hace al arreglo más chico pues se "asientan" los elementos
     * mayores al final. Por lo que las vueltas son de n, n-1, n-2, ..., 1.
     * @param arr : arreglo a ordenar
     */
    public void bubbleSort(T[] arr){
        int min;
        T temp;
        
//        st = 0;
        for(int j=0;j<arr.length;j++){
            for(int i=1;i<arr.length-j;i++){
                min = i-1;
                if(arr[i].compareTo(arr[min]) < 0){
                    temp = arr[i];
                    arr[i] = arr[min];
                    arr[min] = temp;
                }
//                st++;
            }
        }
//        System.out.println(st);
    }
    
    /**
     * Método auxiliar Partición para Quick Sort.
     * Se va a recorrer el arreglo y se van a colocar los elementos más chicos
     * del pivote a la izquierda del mismo y los más grandes a la derecha.
     * Vamos a tener como contadores relativamente independientes a 'i' [min] y a 
     * 'j' [max] y vamos a comparar a 'pivote' con su siguiente elemento y tendremos
     * dos casos a considerar.
     * Si el siguiente elemento de 'pivote' es más chico que él, se hace un swap entre 
     * ellos mismos y aumentamos a 'i'; si el siguiente elemento es más grande, se hace
     * un swap entre el siguiente elemento con el último de la partición y decrementa 'j'.
     * De esta manera vamos a poder comparar al siguiente elemento para decidir si se
     * tiene que mandar a la derecha o a la izquierda.
     * El While va a finalizar cuando los contadores se hayan encontrado entre sí.
     * @param part, índice mínimo y máximo
     * @return posición del pivote ya ordenado, la posición donde *debe* de estar en
     *         la partición.
     */
    private int particion(T[] part,int min, int max){
        Random r = new Random();
        int i = min;
        int j = max;
        T temp;
        
        /**
         * Elegir el pivote de manera aleatoria.
         * Sería interesante y más seguro elegir al elemento pivote de manera 
         * aleatoria y que se hiciera un swap con el primer elemento para que
         * 'pivote' quede al principio y podamos recorrer la partición fácilmente.
         */
        
        while(i < j){
            if(part[i+1].compareTo(part[i]) <= 0){
                temp = part[i+1];
                part[i+1] = part[i];
                part[i] = temp;
                i++;
            }
            else{
                temp = part[j];
                part[j] = part[i+1];
                part[i+1] = temp;
                j--;
            }
//            st++;
        }
        return i;
    }
    
    /**
     * Método ordenamiento Quick Sort.  [ Método recursivo ]
     * Caso base:   Si nuestro arreglo contiene solamente un elemento, se
     * puede decir que está ordenado [ es obvio pero claro ].
     * Caso recursivo:  Como se tiene un arreglo de n elementos [> 1], en la
     * variable 'pivote' se va a llamar al método auxiliar {partición}. Este método 
     * nos va a regresar la posición donde se coloca al elemento pivote.
     * Luego se va a llamar recursivamente al método Quick pero primero vamos a mandar
     * como parámetro al arreglo que se encuentra del lado izquierdo de 'pivote' con 
     * sus respectivos valores 'min' y 'max'; después se va a llamar recursivamente
     * a Quick pero con el arreglo correspondiente a la derecha de 'pivote'. Es por 
     * eso que llamamos al método auxiliar {partición}, pues nos dicta y trabaja con 
     * pedazos del arreglo por cada llamada recursiva.
     * Entonces se puede decir que por cada partición primero va a ordenar a todos los
     * elementos de la izquierda y después a los de la derecha.
     * @param arr, índice mínimo y máximo
     */
    private void quickSortA(T[] arr, int min, int max){
        int pivote;
        
        if(min >= max)
            return;
        pivote = particion(arr,min,max);
        quickSortA(arr,min,pivote-1);
        quickSortA(arr,pivote+1,max);
    }
    public void quickSort(T[] arr){
//        st = 0;
        quickSortA(arr, 0, arr.length-1);
//        System.out.println(st);
    }
    
    /**
     * Método auxiliar Merge.
     * Combina o mezcla dos mitades ya ordenadas de un arreglo.
     * Se tienen tres contadores: 'i', 'j' y 'k' y un arreglo auxiliar 'temp'.
     * 'i' empieza en el primer elemento de la mitad izquierda del arreglo y 'j'
     * empeiza en el primer elemento d ela mitad derecha. 'k' sirve para llevar 
     * el conteo de los elementos que van a ser insertados en el arreglo auxiliar.
     * Mientras que 'i' o 'j' no lleguen a sus límites [mitad y max, respectivamente],
     * se van a comparar los elementos de las dos mitades y se van a insertar en
     * 'temp' de menor a mayor. Cada vez que se hace una inserción, se aumenta el 
     * contador de la mitad correspondiente y a 'k'. 
     * Después, si alguna de las dos mitades tiene elementos en su interior, se
     * insertan en 'temp'.
     * Para finalizar, se copian los elementos de 'temp' [que ya están ordenados]
     * en el arreglo original.
     * @param arr, índice mínimo, medio y máximo
     */
    private void merge(T[] arr, int min, int mit, int max){
        Object[] temp;
        temp = new Object[max+min+1];
        int i = min;
        int j = mit +1;
        int k = 0;
        
        while(i <= mit && j<= max){
            if(arr[i].compareTo(arr[j]) <= 0){
                temp[k] = arr[i];
                i++;
            }
            else{
                temp[k] = arr[j];
                j++;
            }
            k++;
//            st++;
        }
        
        while(j <= max){
            temp[k] = arr[j];
            j++;
            k++;
        }
        while(i <= mit){
            temp[k] = arr[i];
            i++;
            k++;
        }
        
        k = 0;
        for(int c=min;c<=max;c++){
            arr[c] = (T)temp[k];
            k++;
        }
    }
    
    /**
     * Método de ordenamiento Merge Sort.     [ Método recursivo ]
     * Caso base:   Si nuestro arreglo contiene un solo elemento puede decirse
     * que está odenado [claramente]
     * Caso recursivo:  Como tenemos un arreglo de n elementos [> 1], en 'mit'
     * se determina el índice de la mitad del arreglo dado para llamar de manera
     * recursiva al método {mergeSort} primero con el arreglo correspondiente a 
     * la mitad izquierda y luego con la mitad derecha [así es como llega hasta
     * los arreglos de solo un elemento de derecha e izquierda].
     * Luego se llama al método auxiliar {merge} para ordenar ambas mitades que
     * ya están ordenadas. Por lo tanto, es un método que ordena por mitades, 
     * lo interesante está en la aplicación del método {merge}.
     * @param arr, índice mínimo y máximo
     */
    private void mergeSort(T[] arr, int min, int max){
        int mit;
        
        if(min >= max)
            return;
        mit = (min + max)/2;
        mergeSort(arr, min, mit);
        mergeSort(arr, mit+1, max);
        merge(arr,min,mit,max);
    }
    public void mergeSort(T[]arr){
//        st = 0;
        mergeSort(arr, 0, arr.length-1);
//        System.out.println(st);
    }
    
    
    // --------------------------------------------------------------------------------
    
    /**
     * Método imprime arreglo.
     * Recorre todo el arreglo mientras concatena cada uno de los
     * elementos en una cadena de caracteres.
     * @param arr : arreglo a imprimir
     */
    public String imprimeArr(T[] arr){
        StringBuilder cad = new StringBuilder();
        
        cad.append("{ ");
        for(int i=0;i<arr.length;i++){
//            cad.append("\n"+arr[i]+"  ");    
            cad.append(arr[i]+"  ");
        }
        cad.append("}");
        return cad.toString();
    }
    
    /**
     * Método invierte arreglo.
     * Se ocupó el Insertion Sort al revés.
     */
    public void invierteArr(T[] arr){
        int j;
        T temp;
        
        for(int i=1;i<arr.length;i++){
            j=i;
            while(j>=1 && arr[j].compareTo(arr[j-1]) > 0){
                temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            }
        }
    }
    
    public T[] copiarArr(T[] arr, T[] cop){
        
        for(int i=0;i<cop.length;i++)
            cop[i] = arr[i];
        return cop;
    }
}
