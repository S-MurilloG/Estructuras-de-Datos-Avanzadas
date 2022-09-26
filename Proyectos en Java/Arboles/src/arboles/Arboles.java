/**
 * Main para pruebas y ejercicios relacionados con la estructura Árbol.
 */
package arboles;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 15 febrero, 2021.
 * @author Murillo
 */
public class Arboles {

    public static void main(String[] args) {
        
        /**
         * Pruebas Recorridos ------------------------------------------------------------.
         */
        
//        LinkedBinaryTree<Integer> a = new LinkedBinaryTree<Integer>();
//        Integer[] data = {25,36,75,24,67,23};
//        
//        for(int i=0;i<data.length;i++)
//            a.inserta(data[i]);
//        
//        Iterator<Integer> i = a.inOrder();
//        while(i.hasNext())
//            System.out.println(i.next());
        

        /**
         * Pruebas BST / Tarea -----------------------------------------------------------.
         */

//        LinkedBinaryTree<Integer> a = new LinkedBinaryTree<Integer>();
//        Integer[] data = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
//        Integer[] res = data;
//        Integer[] r = {0,0};
//        BinaryNode<Integer> ant;
//        
//        a.mitades(data,res);
//        for(int i=0;i<data.length;i++)
//            a.inserta(res[i]);
//        System.out.println("Niveles: "+a.niveles());
//        a.selector(data, r);
//        System.out.println("Datos: ");
//        System.out.println(r[0]);
//        System.out.println(r[1]);
//        ant = a.ancestor(r[0], r[1]);
//        System.out.println("Antecesor: "+ant.getElem());
//        System.out.println("Niveles ant: "+a.niveles(ant));
//        a.factores();
        

        /**
         * Pruebas AVL -------------------------------------------------------------------.
         */
        
//        LinkedBinaryTree<Integer> a = new LinkedBinaryTree<Integer>();
//        Integer[] ins = {-50,15,7,3,-25,5,-2,123,4,90,-300,-40,-555,100,29,0,-31,50,47,-66};
//        Integer[] borr = {3,100,-40,-25,90,50,29,123,4,7,47,-300,-50,15,-66,-555,0,-2,5,-31};
//        
//        for(int i=0;i<ins.length;i++)
//            a.insertaAVL(ins[i]);
//        
//        for(int j=0;j<borr.length;j++){
//            a.borraAVL(borr[j]);
//        }
//        a.factores();
//        
//        System.out.println("");
//        System.out.println("Niveles: "+a.niveles());
//        System.out.println("No. elementos: "+a.size());
//        System.out.println("¿Vacío?: "+a.isEmpty());
//        System.out.println("¿Encontró elemento? "+a.find(29));
        
        /**
         * Pruebas Heap -------------------------------------------------------------------.
         */
        Heaps<Integer> h = new Heaps<Integer>();
        Integer[] data = {3,4,8,-12,6,-30,25,-500,150};
        Integer[] salida = data;
        
        for(int i=0;i<data.length;i++)
            h.inserta(data[i]);
        
        for(int j=0;j<data.length;j++)
            salida[j] = h.borraMin();
        
        for(int k=0;k<salida.length;k++)
            System.out.println(salida[k]);
        
    }
    
}
