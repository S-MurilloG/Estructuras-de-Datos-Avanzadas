/**
 * Implementa los métodos completos de la calculadora.
 * Contiene métodos para revisar una expresión, traducirla y evaluarla
 */
package proyectocalculadora;
import pilas.*;

/**
 * Marzo 2020
 * @author Ulisesquevedo, AngelMieres, PedroOlivares, BrunoZacatenco, SebastianMurillo
 */
public class Calculadora {
    
    //Constantes de operadores
    private final String PARABRE="(";
    private final String PARCIERRA=")";
    private final String ADD="+";
    private final String SUBTRACT="-";
    private final String MULT="*";
    private final String DIV="/";
    private final String POW = "^";
    
    public Calculadora(){
    }
    
    //Método que determina la prioridad del operador
    /**
     * Órden de prioridad según operadores:
     * 1. Paréntesis
     * 2. Potencia (^)
     * 3. Multiplicación y divison (* /)
     * 4. Suma y resta
     */
    private int prioridad(String operador){
        int i=1; //Se asume, de entrada, que el operador es + o -
        
        switch(operador){
            case (PARABRE):
                i=4;
                break;
            case(PARCIERRA) :
                i=4;
                break;
            case(POW):
                i=3;
                break;
            case(MULT):
                i=2;
                break;
            case(DIV):
                i=2;
                break; 
        }
        return i; 
    }
   
    /**
     * Método que revisa si un caracter es un operador o no.
     * @param car
     * @return verdadero si la entrada es operador 
     */
    private boolean isOperator (String car) {
        boolean resul= false;
    
        if(car.equals(ADD)||car.equals(SUBTRACT)||car.equalsIgnoreCase(MULT)||car.equalsIgnoreCase(DIV)||car.equalsIgnoreCase(POW)){
            resul=true;
        }
        return resul;
    }
    
    /**
     * Traductor que pasa la cadena de infija a sufija
     * 
     * @param infija Arreglo de una cadena en notación infija
     * @return Arrelo de la cadena en notación sufjia
     */
    private String[] traductor(String[] infija){

        String[] salida = new String[infija.length];

	ArrayStack<String> pila = new ArrayStack<String>();
	String car;
	int p = 0;
        //Se recorre cada elemento del arreglo recibido
	for(int i = 0; i < infija.length; i++){
            
            car = infija[i];
            
            if(isOperator(car)){
                while(!pila.isEmpty() && !pila.peek().equals(PARABRE)  && prioridad(pila.peek()) >= prioridad(car)) {
                    salida[p] = pila.pop();
                    p++;
                }
                pila.push(car);
            }
            else{
                
                if(car.equals(PARCIERRA)){
                    while(!pila.peek().equals(PARABRE)){
                        salida[p] = pila.pop();
			p++;
                    } 
		pila.pop();
                } 
                else{
                    if(car.equals(PARABRE)){
                    //Caso parentesis que abre
                        pila.push(car);
                    }
                    else{
                        //Caso operando
                        salida[p] = car;
			p++;
                        }
                    }
                }
        }
        
        while(!pila.isEmpty()) {
            salida[p] = pila.pop();
            p++;
        }
    
        return salida;

    }
    
    /**
     * Método que analiza si los paréntesis están balanceados
     * 
     * @param Arreglo de la cadena en String.
     * @return True si la cadena tiene los paréntesis balanceados y ocupa de manera correcta los operadores.
     */
    private boolean revisor(String[] cad){
        ArrayStack<Character> pilaC=new ArrayStack<Character>();
        boolean res=true;
        int i,n;
        char c;
        
        //inicializa variables.
        i=0; n=cad.length;
        //Recorre la cadena y procesa los paréntesis.
        while(i<n && res){
            c=cad[i].charAt(0);
            if(c != 'E'){
                if(c=='(')
                    pilaC.push(c);
                else
                    if(c==')'){
                        if(!pilaC.isEmpty() && pilaC.peek()=='(')
                            pilaC.pop();
                        else
                            res=false;
                    }
                i++;
            }
            else
                res=false;
        }
        if(!pilaC.isEmpty())
            res=false;
        return res;
    }
    
    
    /**
     * Control de mando de la calculadora. Es el método principal, que 
     * llama al resto de los métodos
     * 
     * @param entrada Cadena separada 
     * @return En forma de String, el resultado de la operación
     *      En caso de error regresa el mensaje apropiado
     */
    public String evalua(String[] cad){
        String respuesta = "Syntax Error";
        
        //Revisa si la cádena es válida con el método revisor
        if(revisor(cad)){
            
            //Si la cadena es válida, la traduce a infijo
            cad = traductor(cad);

            //Calcula el resultado con el método calcula
            respuesta = String.valueOf(calcula(cad));

            if(respuesta.equals("Infinity"))
                //Si se divide entre cero, regresa el mensaje apropiado
                respuesta = "Error: División por cero";
            
            
        }
        
        //Si la cadena no es válido, se regresa un syntax error
        else
            respuesta = "Syntax error";

        return respuesta;
    
        
    }
    
    /**
     * Separador del String a String[]
     * 
     * @param cad Cadena que se ingresa
     * @return arreglo con la cadena separada en los espacios y con "E" para errores dentro de la cadena.
     */
    public String[] separador(String cad){
        String[] arr;
        
        arr=cad.split(" ");
        return arr;
    }
    
    /**
     * 
     * Método auxiliar que realiza una única operación
     * Recibe el operador y los dos operandos
    */
    private float evalSingleOp (String operation, float op1, float op2) {
        float result = 0;
		    
        switch(operation){
            case ADD:
                result = op1 + op2;
		break;
            case SUBTRACT:
		result = op1 - op2;
		break;
            case MULT:
		result = op1 * op2;
		break;
            case POW:
                result = (float) Math.pow(op1, op2);
                break;
            case DIV:
		result = op1 / op2;
		break;
		    }
        
	return result;
    }
    
    /**
     * Evaluador que realiza las operaciones
     * 
     * @param Expresión traducida
     * @return Valor númerico del resultado
     */
    private float calcula (String[] expr) {
	float op1, op2, result = 0;
	String car;
	ArrayStack<Float> stack = new ArrayStack<Float>();
	int j = 0;
		    
	while(j < expr.length && expr[j] != null) {
            car = expr[j];
            
            if(!isOperator(car)){
		stack.push(Float.parseFloat(car));
            }
            else{
		op2 = stack.pop();
		op1 = stack.pop();
		            
		result = evalSingleOp(car, op1, op2);
		stack.push(result);
            }
            
            j++;
		        
	}
		    
	result = stack.pop();
	return result;
    }
    
    /**
     * Método que asigna valores a variables
     * 
     * @param Arreglo de la cadena en String.
     * @return Arreglo de la cadena en String con valores asignados.
     */
    public String[] asigVar(String cad,String x,String y,String z){
        int n;
        char c;
        String[] arr;
        
        arr=separador(cad);
        n=arr.length;
        x=revisorVar(x);
        y=revisorVar(y);
        z=revisorVar(z);
        for(int i=0;i<n;i++){
            c=arr[i].charAt(0);
            switch(c){
                case 'x':
                    arr[i]=x;
                    break;
                case 'y':
                    arr[i]=y;
                    break;
                case 'z':
                    arr[i]=z;
                    break;
            }
        }
        return arr;
    }
    
    /**
     * Método que revisa los valores de las variables
     * 
     * @param String, la variable.
     * @return Variable corregida por "E" si esta era "".
     */
    private String revisorVar(String var){
        
        if(var.equals(""))
            var="E";
        
        return var;
    }
}
