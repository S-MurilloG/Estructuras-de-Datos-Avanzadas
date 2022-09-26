/**
 * Clase MiLista.
 */
package listaclase;

import pilas.*;

/**
 * 13 enero, 2021
 * @author S. Murillo
 */
public class MiLista <T> {
    private Nodo<T> cabeza;
    private int cont;
    
    /**
     * Constructor.
     * Crea un nodo 'cabeza' (null) para acceder de manera sencilla a los
     * elementos de la lista. Funge como un nodo auxiliar de acceso
     */
    public MiLista(){
        cabeza = new Nodo<T>(null);
        cont = 0;
    }
    
    /**
     * Método de inserción. Inserta al inicio
     * La variable 'temp' va a ser el primer elemento de nuestra lista. Cuando 
     * creamos el nodo 'nuevo', indicamos que su siguiente nodo va a ser 'temp'.
     * Luego indicamos que el siguiente nodo de 'cabeza' va a ser el 'nuevo'.
     * @param elem : elemento que se quiere insertar 
     */
    public void inserta(T elem){
        Nodo<T> temp = cabeza.getSig();
        Nodo<T> nuevo = new Nodo<T>(elem);
        
        nuevo.setSig(temp);
        cabeza.setSig(nuevo);
        cont++;
    }
    
    /**
     * Método imprime al derecho.
     * Crea un nodo 'actual' que va a ser el que reccorre la lista.
     * Mientras que 'actual' no llegue al final, este nodo va a imprimir
     * el elemento en el que se encuentra y pasará al siguiente nodo.
     */
    public void imprime(){
        Nodo<T> actual = cabeza.getSig();
        
        while(actual!=null){
            System.out.println(actual.getElem());
            actual = actual.getSig();
        }
    }
    
    /**
     * Método imprime al revés. Ocupa una pila auxiliar
     * Crea un nodo 'actual' que va a ser el que reccorre la lista y una pila.
     * Mientras que 'actual' no llegue al final, se extrae el elemento de 'actual',
     * se hace un push del mismo a la pila y se pasa al siguiente elemento.
     * Luego, mientras la pila no esté vacía, se hace una impresión de los pops.
     */
    public void imprimeAlReves(){
        ArrayStack <T> pila = new ArrayStack<T>();
        Nodo<T> actual = cabeza.getSig();
        
        while(actual != null){
            pila.push(actual.getElem());
            actual = actual.getSig();
        }
        while(!pila.isEmpty()){
            System.out.println(pila.pop());
        }
    }
    
    /**
     * Método imprime recursivo al derecho. Ocupa método auxiliar.
     * Mientras que el nodo 'actual' no llegue al final, se va a imprimir el elemento
     * en el cque se encuentre y va a llamarse a sí mismo pero empezando con el siguiente
     * elemento.
     * @param actual : nodo que recorre la lista. 
     */
    private void imprimeRecursivoAlDerecho(Nodo<T> actual){
        
        if(actual == null)
            return;
        System.out.println(actual.getElem());   //Líneas que pueden invertirse
        imprimeRecursivoAlDerecho(actual.getSig());
    }
    public void imprimeRecDerecho(){
        imprimeRecursivoAlDerecho(cabeza.getSig());
    }
    
    /**
     * Método imprime recursivo al revés. Ocupa método auxiliar.
     * Mientras que el nodo 'actual' no llegue al final, va a llamarse de manera
     * constante a sí mismo. Cuando llegue al final, cada llamado imprimirá el
     * elemento correspondiente, pero por la estructura es que se va a imprimir
     * al revés.
     * @param actual : nodo que recorre la lista. 
     */
    private void imprimeRecursivoAlReves(Nodo<T> actual){
        
        if(actual == null)
            return;
        imprimeRecursivoAlReves(actual.getSig());   //Líneas que pueden invertirse
        System.out.println(actual.getElem());
    }
    public void imprimeRecReves(){
        imprimeRecursivoAlReves(cabeza.getSig());
    }
    
    /**
     * Método invierte apuntadores.
     * Mientras que el nodo 'temp' (el siguiente del actual) no sea null, va a
     * llamarse recursivamente. En el momento en que llegue al final, 'cabeza'
     * va a apuntar al último y se van a invertir los apuntadores de final a
     * inicio.
     * @param actual : nodo que recorre la lista
     */
    private void invierteApAux(Nodo<T> actual){
        Nodo<T> temp = actual.getSig();
        
        if(temp == null){
            cabeza.setSig(actual);
//            actual.setSig(null);
            return;
        }
        else{
            invierteApAux(actual.getSig());
            temp.setSig(actual);
            actual.setSig(null);
        }
    }
    public void invierteApuntadores(){
        invierteApAux(cabeza.getSig());
    }
    
    /**
     * Método invierte copiando los elementos.
     * Mientras que 'actual' no llegue al final, 'elem' va a guardar los elementos de
     * 'actual' en orden. La salida de cada llamado regresa el nodo 'temp', el cual
     * va a recorrer la lista en orden; al mismo tiempo, los elementos guardados en 'elem'
     * se guardan como elementos de temp mientras hace su recorrido.
     * @param actual : nodo que recuerda los elementos para la inversión.
     * @return nodo que recorrerá la lista desde el inicio para hacer la inversión
     */
    private Nodo <T> invierteCopAux(Nodo<T> actual){
        Nodo<T> temp;
        T elem;
        
        if(actual.getSig() == null){
            return cabeza.getSig();
        }
        else{
            elem = actual.getElem();
            temp = invierteCopAux(actual.getSig());
            temp.setElem(elem);
            return temp.getSig();
        }
    }
    public void invierteCopiando(){
        invierteApAux(cabeza.getSig());
    }
    
    /**
     * Método cuenta elementos.
     * Mientras que 'actual' no llegue al final de la lista, el valor de 'tot'
     * se va a actualizar por cada vuelta nueva que de.
     * @param actual : nodo que recorre la lista
     * @param tot : variable que permite el conteo
     */
    private int cuentaAux(Nodo<T> actual, int tot){
        
        if(actual == null){
            return tot;
        }
        else{
            tot = cuentaAux(actual.getSig(),tot + 1);
            return tot;
        }
    }
    public int cuentaElementos(){
        int tot = 0;
        tot = cuentaAux(cabeza.getSig(), tot);
        
        return tot;
    }
    
    /**
     * Método permuta una cadena de caracteres.
     * Parece magia y es complicado de explicar, pero sí da el resultado
     * esperado. Internamente está desglosando la cadena de caracteres
     * dada y realiza todas las permutaciones posibles dejando letras
     * fijas. A una escala muy reducida, podemos observar que lo que 
     * hace son permutaciones en pares y va cambiando la posición de las 
     * letras que va a permutar. Como dije: parece magia.
     */
    private void permutaAux(String s, String s2){
        String unaletra;
        String resto;
        
        if(s.length() == 0){
            System.out.println(s2);
            return;
        }
        for(int i=0;i<s.length();i++){
            unaletra = s.substring(i,i+1);
            resto = s.substring(0, i) + s.substring(i+1, s.length());
            permutaAux(resto, unaletra+s2);
        }
    }
    public void permuta(String s){
        permutaAux(s,"");
    }
    
    /**
     * Método calcula el menor valor entre tres números.
     * Simplemente hace una comparación entre los tres valores.
     * @param valores : 'x', 'y', 'z' son los valores a comparar
     * @return Cuál de los dos valores es el menor.
     */
    private int min(int x, int y, int z){
        
        if(x <= y && x <= z)
            return x;
        if(y <= x && y <= z)
            return y;
        else
            return z;
    }
    /**
     * Método conocido como "edit distance".
     * El método va a comparar letra por letra de una cadena para conocer
     * cuál es la 'distancia' mínima entre ellas. Para ello, tendremos opciones:
     *    - Si los caracteres son iguales, no aumenta el contador y se llama
     *      recursivamente el método para los siguientes caracteres.
     *    - Si no son iguales, tendremos la opción de intercambiar, insertar o eliminar.
     *      Cada una de las posibilidades llama al método recursivamente para que haga su
     *      propio cálculo y lo aumente a SU contador.
     *    - Por último, se compara entre el mínimo de cada contador para regresar el 
     *      valor adecuado. [Que al final resulta ser el camino más adecuado]
     * @param cadenas : s1 y s2 son las cadenas a comparar.
     * @return la distancia mínima entre dos cadenas de caracteres.
     */
    private int editAux(String s1, String s2, int cont){
        int nada;
        int res1, res2, res3;
        
        if(s1.length() == 0 || s2.length() == 0)        //Caso base : se aumenta lo que 
            return cont + s1.length() + s2.length();    // falte de la cadena restante.
        
        if(s1.charAt(0) == s2.charAt(0)){
            nada = editAux(s1.substring(1),s2.substring(1),cont);
            return nada;
        }
        else{
            res1 = editAux(s1.substring(1), s2.substring(1), cont+1);   //Intercambia
            res2 = editAux(s1, s2.substring(1), cont+1);                //Inserta
            res3 = editAux(s1.substring(1), s2, cont+1);                //Elimina
            return min(res1, res2, res3);
        }
    }
    public int edit (String s1, String s2){
        int cont = 0;
        
        cont = editAux(s1,s2,cont);
        return cont;
    }
    
}
