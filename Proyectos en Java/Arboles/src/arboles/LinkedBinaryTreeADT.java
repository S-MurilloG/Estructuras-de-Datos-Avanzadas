/**
 * Implementación de la interfaz para Árbol.
 */
package arboles;

import java.util.Iterator;

/**
 * 15 febrero, 2020.
 * @author Murillo
 */
public interface LinkedBinaryTreeADT <T extends Comparable<T>> {
    
    /**
    * Throw true or false if the binary tree is empty or not.
    */
    public boolean isEmpty();
   
   /**
    * Returns te size of the binary tree.
    */
    public int size();
   
   /**
    * If found the element, returns the element.
    */
//    public T find(T element);
   
   /**
    * Visit node, left child, right child.
    */
    public Iterator<T> preOrder();
   
   /**
    * Left child, visit node, right child.
    */
    public Iterator<T> inOrder();
   
   /**
    * Left child, right child, visit node.
    */
    public Iterator<T> postOrder();
   
   /**
    * By Levels.
    */
//    public Iterator<T> iteratorLevelOrder();
}
