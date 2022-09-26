/**
 * Implementación del main de la tabla hash.
 */
package mainhash;

/**
 * 21 abril, 2021
 * @author Murillo
 */
public class MainHash {

    public static void main(String[] args) {
        
        
        /**
         * Prueba del método de intersección --------------------------------------------.
         */
        HashTable<Integer> h = new HashTable();
        Integer[][] mat = {{2,3,4},{5,6,7},{9,8}};
        int res;
        
        res = h.intersection(mat);
        System.out.println(res);
    }
    
}
