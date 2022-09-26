/**
 * Main.
 */
package algoritmosordenamiento;

/**
 * 3 de febrero, 2021.
 * @author Murillo
 */
public class AlgoritmosOrdenamiento {

    public static void main(String[] args) {
        Ordenamientos <Integer> prueba = new Ordenamientos<Integer> ();
        Integer[] arr1 = {5,19,-2,1,-30};   //Patrocinado por Carmen Q
        Integer[] arr2 = {5,4,3,2,1,0};
        Integer[] arr3 = {0,-1,3,1,-9,6,17,19,34,-78,23,68,-98,34,12,-456,43};
        
        //Prueba de método Selection Sort.
//        prueba.selectionSort(arr1);
//        System.out.println(prueba.imprimeArr(arr1));
        
        //Prueba de método Insertion Sort.
//        prueba.insertionSort(arr1);
//        System.out.println(prueba.imprimeArr(arr1));
        
        //Prueba de método Bubble Sort.
//        prueba.bubbleSort(arr2);
//        System.out.println(prueba.imprimeArr(arr2));
        
        //Prueba de método Quick Sort.
//        prueba.quickSort(arr3);
//        System.out.println(prueba.imprimeArr(arr3));
        
        //Prueba de método Merge Sort.
        prueba.mergeSort(arr3);
        System.out.println(prueba.imprimeArr(arr3));
        

    }
    
}
