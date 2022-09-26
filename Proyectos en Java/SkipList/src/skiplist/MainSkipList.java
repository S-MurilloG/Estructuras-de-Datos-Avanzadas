/**
 * Main para pruebas y ejercicios relacionados con la estructura SkipList.
 */
package skiplist;

/**
 * 12 abril, 2021.
 * @author Murillo
 */
public class MainSkipList {

    public static void main(String[] args) {
        
        
        SkipList<Integer> sk = new SkipList();
        Integer[] d = {1,2,3,4,5,6};
        
        for(int i=0;i<d.length;i++){
            sk.inserta(d[i]);
            System.out.println("Ya");
            sk.toStrRes();
        }
        
        System.out.println("");
        System.out.println("Est:");
        sk.toStrComp();
        
        System.out.println(sk.pilar());
        
        
    }
    
}
