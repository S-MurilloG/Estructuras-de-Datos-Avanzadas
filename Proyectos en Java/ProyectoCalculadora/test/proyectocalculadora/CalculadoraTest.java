/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocalculadora;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ulisesquevedo
 */
public class CalculadoraTest {
    
    public CalculadoraTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    //Pruebas de los métodos publicos de la clase Calculadora
    /*
    Estos metodos llaman a los métodos privados
    Por encapsulamiento, revisar que los métodos exteriores funcionen es
    equivalente a revisar los métodos privados interiores, pues si los 
    interiores no funcionan, los exteriores tampoco. 
    /**
     * Test of evalua method, of class Calculadora.
     */
    @Test
    public void testEvalua() {
        System.out.println("evalua");
        
        Calculadora instance = new Calculadora();
        String[] cad = instance.separador("( 10 + 3 ) - ( 8 / 2 )");
        String expResult = "9.0";
        String result = instance.evalua(cad);
        assertEquals(expResult, result);
    }

    /**
     * Test of separador method, of class Calculadora.
     */
    @Test
    public void testSeparador() {
        System.out.println("separador");
        String cad = "( 4 + 2 ) * ( -7 + 1 )";
        Calculadora instance = new Calculadora();
        String[] expResult = {"(","4","+","2",")","*","(","-7","+","1",")"};
        String[] result = instance.separador(cad);
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of asigVar method, of class Calculadora.
     */
    @Test
    public void testAsigVar() {
        System.out.println("asigVar");
        String cad = "( 2 + y ) - ( 5 * x )";
        String x = "4";
        String y = "10";
        String z = "";
        Calculadora instance = new Calculadora();
        String[] expResult = {"(","2","+","10",")","-","(","5","*","4",")"};
        String[] result = instance.asigVar(cad, x, y, z);
        assertArrayEquals(expResult, result);
        
    }
    
}
