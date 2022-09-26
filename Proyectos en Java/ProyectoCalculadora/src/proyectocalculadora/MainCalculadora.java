/**
 * Método ejecutable desde el que se corre el proyecto
 */
package proyectocalculadora;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Angel Mieres, Bruno Zacatenco, Pedro Olivares, Sebastian Murillo y Ulises Quevedo
 */
public class MainCalculadora {

    public static void main(String[] args) {
        
        InterfazGraficaCalculadora ca = new InterfazGraficaCalculadora();
        ca.setBounds(500, 150, 700, 700);
        ca.setVisible(true);
        ca.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
}
