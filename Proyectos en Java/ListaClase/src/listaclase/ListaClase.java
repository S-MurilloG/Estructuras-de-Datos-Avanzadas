/**
 * Versión de Lista vista en la clase.
 */
package listaclase;

/**
 * 13 enero, 2021.
 * @author Murillo
 */
public class ListaClase {

    public static void main(String[] args) {
        MiLista<Integer> lista = new MiLista<Integer>();
        
        //Inserción de los números para pruebas.
        lista.inserta(1);
        lista.inserta(2);
        lista.inserta(3);
        lista.inserta(4);
        lista.inserta(5);
        
        //Imprime al derecho.
//        lista.imprime();
        
        //Imprime al revés.
//        lista.imprimeAlReves();
        
        //Imprime recursivo al derecho.
//        lista.imprimeRecDerecho();
        
        //Imprime recursivo al revés.
//        lista.imprimeRecReves();
        
        //Invierte apuntadores.
//        lista.invierteApuntadores();
//        lista.imprime();
        
        //Invierte copiando elementos.
//        lista.invierteCopiando();
//        lista.imprime();
        
        //Cuenta elementos.
//        System.out.println(lista.cuentaElementos());
        
        //Permuta letras de la palabra 'Carmen'
        String s = "Carmen";
        lista.permuta(s);
        
        //Calcula la distancia mínima entre una palabra y otra
//        String s1 = "Carmen";
//        String s2 = "Sebas";
//        
//        System.out.println(lista.edit(s1, s2));

    }
    
}
