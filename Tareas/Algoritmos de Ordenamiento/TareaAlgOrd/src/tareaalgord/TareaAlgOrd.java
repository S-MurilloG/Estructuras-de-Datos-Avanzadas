/**
 * Main para realizar la Tarea de Algoritmos de Ordenamiento.
 * Se hace la prueba de los algoritmos:
 *          Selection, Insertion, Bubble, Quick y Merge.
 * Con la cantidad n de datos:
 *          100, 500, 1500, 4000, 8000 y 12214.
 * En el que cada uno se probará: 
 *          12 veces para promediar 10 mediciones:
 *              Se elimina el valor más alto y el valor más bajo.
 * Con orden:
 *          aleatorio, en orden y orden inverso.
 */
package tareaalgord;

import algoritmosordenamiento.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities; 
import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**
 * 28 de febrero, 2020.
 * @author Murillo
 */
public class TareaAlgOrd {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        // Necesario para leer archivos.
        File file = new File("/Users/Murillo/Downloads/ITAM/Estructuras de Datos Avanzadas/Tareas/movie_titles2.txt");
        Scanner scan = new Scanner(file);
        
        /** 
         * ----------------------------------------------------------------------------------------------------------.
         */
        // Arreglo y variables necesarias para crear e insertar películas en un arreglo.
        
        int tamañoEn = 100;
//        int tamañoEn = 500;
//        int tamañoEn = 1500;
//        int tamañoEn = 4000;
//        int tamañoEn = 8000;
//        int tamañoEn = 12214;
        Ordenamientos prueba = new Ordenamientos();
        Pelicula[] p = new Pelicula[tamañoEn];
        String[] str = new String[3];
        String line,nombre;
        int id, aho, cont;
        Pelicula peli;
        
        cont = 0;
        while(scan.hasNext() && cont < tamañoEn){
            line = scan.nextLine();
            str = line.split(",");
            id = Integer.parseInt(str[0]);
            aho = Integer.parseInt(str[1]);
            nombre = str[2];
            peli = new Pelicula(id,aho,nombre);
            p[cont] = peli;
            cont ++;
        }
        System.out.println(cont);

        /** 
         * Arreglos y variables necesarios para probar los métodos.--------------- TIEMPO ---------------------------------.
         */
        
        long tInicio, tFin, tiempo;
        Pelicula[] aleatorio = new Pelicula[p.length];
        Pelicula[] ordenado = new Pelicula[p.length];
        Pelicula[] alReves = new Pelicula[p.length];
        
//        System.out.println("Inicio:");
//        System.out.println(prueba.imprimeArr(p));
        
        /**
         * Prueba de arreglo Aleatorio. [ Se ocupó el mismo orden del archivo ]
         */
//        for(int i=0;i<12;i++){
//            aleatorio = (Pelicula[]) prueba.copiarArr(p, aleatorio);
////            System.out.println("Inicio: "+(i+1));
////            System.out.println("\t Ai: "+prueba.imprimeArr(aleatorio));
//            tInicio = System.nanoTime();
//            
//            prueba.selectionSort(aleatorio);
////            prueba.insertionSort(aleatorio);
////            prueba.bubbleSort(aleatorio);
////            prueba.quickSort(aleatorio);
////            prueba.mergeSort(aleatorio);
//
//            tFin =System.nanoTime();
////            System.out.println("\t Af: "+prueba.imprimeArr(aleatorio));
//            tiempo = tFin - tInicio;
////            System.out.println("");
////            System.out.println("Tiempo: "+tiempo+" ns");
////            System.out.println("");
//        }
        
//        System.out.println("Final:");
//        System.out.println(prueba.imprimeArr(p));
        
        prueba.quickSort(p);
        
//        System.out.println("Inicio");
//        System.out.println(prueba.imprimeArr(p));
        
        /**
         * Prueba de Arreglo Ordenado. [ Se ordenó previamente ]
         */
//        for(int i=0;i<12;i++){
//            ordenado = (Pelicula[]) prueba.copiarArr(p, ordenado);
////            System.out.println("Inicio: "+(i+1));
////            System.out.println("\t Ai: "+prueba.imprimeArr(ordenado));
//            tInicio = System.nanoTime();
//            
//            prueba.selectionSort(ordenado);
////            prueba.insertionSort(ordenado);
////            prueba.bubbleSort(ordenado);
////            prueba.quickSort(ordenado);
////            prueba.mergeSort(ordenado);
//
//            tFin = System.nanoTime();
////            System.out.println("\t Af: "+prueba.imprimeArr(ordenado));
//            tiempo = tFin - tInicio;
////            System.out.println("");
////            System.out.println("Tiempo: "+tiempo+" ns");
////            System.out.println("");
//        }
        
//        System.out.println("Final:");
//        System.out.println(prueba.imprimeArr(p));
          
        prueba.invierteArr(p);
        
//        System.out.println("Inicio");
//        System.out.println(prueba.imprimeArr(p));
        
        /**
         * Prueba de Arreglo Invertido. [ Se invirtió previamente ]
         */
//        for(int i=0;i<12;i++){
//            alReves = (Pelicula[]) prueba.copiarArr(p, alReves);
////            System.out.println("Vuelta: "+(i+1));
////            System.out.println("\t Ai: "+prueba.imprimeArr(alReves));
//            tInicio = System.nanoTime();
//            
//            prueba.selectionSort(alReves);
////            prueba.insertionSort(alReves);
////            prueba.bubbleSort(alReves);
////            prueba.quickSort(alReves);
////            prueba.mergeSort(alReves);
//
//            tFin =System.nanoTime();
////            System.out.println("\t Af: "+prueba.imprimeArr(alReves));
//            tiempo = tFin - tInicio;
////            System.out.println("");
////            System.out.println("Tiempo: "+tiempo+" ns");
////            System.out.println("");
//        }
        
//        System.out.println("Final");
//        System.out.println(prueba.imprimeArr(p));
        
        /**
         * Creación de tablas. -------------------------------------------------------------------
         */
        
//        final XYSeries selection = new XYSeries( "Selection" );
//        selection.add( 100 , Math.log(4950) );
//        selection.add( 500 , Math.log(124750) );
//        selection.add( 1500 , Math.log(1124250) );
//        selection.add( 4000 , Math.log(7998000) );
//        selection.add( 8000 , Math.log(31996000) );
//        selection.add( 12214 , Math.log(74584791) );
//        
//        final XYSeries insertion = new XYSeries( "Insertion" );
//        insertion.add( 100 , Math.log(4950) );
//        insertion.add( 500 , Math.log(124750) );
//        insertion.add( 1500 , Math.log(1124250) );
//        insertion.add( 4000 , Math.log(7998000) );
//        insertion.add( 8000 , Math.log(31996000) );
//        insertion.add( 12214 , Math.log(74584791) );
//        
//        final XYSeries bubble = new XYSeries( "Bubble" );
//        bubble.add( 100 , Math.log(4950) );
//        bubble.add( 500 , Math.log(124750) );
//        bubble.add( 1500 , Math.log(1124250) );
//        bubble.add( 4000 , Math.log(7998000) );
//        bubble.add( 8000 , Math.log(31996000) );
//        bubble.add( 12214 , Math.log(74584791) );
//        
//        final XYSeries quick = new XYSeries( "Quick" );
//        quick.add( 100 , Math.log(4950) );
//        quick.add( 500 , Math.log(124750) );
//        quick.add( 1500 , Math.log(1124250) );
//        quick.add( 4000 , Math.log(7998000) );
//        quick.add( 8000 , Math.log(31996000) );
//        quick.add( 12214 , Math.log(74584791) );
//        
//        final XYSeries merge = new XYSeries( "Merge" );
//        merge.add( 100 , Math.log(316) );
//        merge.add( 500 , Math.log(2216) );
//        merge.add( 1500 , Math.log(7664) );
//        merge.add( 1400 , Math.log(23728) );
//        merge.add( 8000 , Math.log(51456) );
//        merge.add( 12214 , Math.log(81175) );
//        
//        final XYSeriesCollection dataset = new XYSeriesCollection( );
//        dataset.addSeries( selection );
//        dataset.addSeries( insertion );
//        dataset.addSeries( bubble );
//        dataset.addSeries( quick );
//        dataset.addSeries( merge );
//        
//        String tit = "Cantidad de comparaciones";
//        String x = "Tamaño entrada";
//        String y = "Comparaciones";
//        boolean lbl = true;
//        JFreeChart xyChart = ChartFactory.createXYLineChart(tit,x,y,dataset,PlotOrientation.VERTICAL, lbl, true, false);
//        
//        XYPlot plot = xyChart.getXYPlot();
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        Color lightBlue = new Color(30,144,255);
//        Color lightGreen = new Color(34,183,22);
//        Color pink = new Color(255,51,153);
//        Color purple = new Color(153,50,204);
//        Color orange = new Color(255,140,0);
//        
//        //Colores de las líneas
//        renderer.setSeriesPaint(0, lightBlue);
//        renderer.setSeriesPaint(1, lightGreen);
//        renderer.setSeriesPaint(2, pink);
//        renderer.setSeriesPaint(3, purple);
//        renderer.setSeriesPaint(4, orange);
//        //Grosor de las líneas
//        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
//        renderer.setSeriesStroke(1, new BasicStroke(4.0f));
//        renderer.setSeriesStroke(2, new BasicStroke(4.0f));
//        renderer.setSeriesStroke(3, new BasicStroke(4.0f));
//        renderer.setSeriesStroke(4, new BasicStroke(4.0f));
//        //Color y grosor del contorno
//        plot.setOutlinePaint(Color.BLACK);
//        plot.setOutlineStroke(new BasicStroke(5.0f));
//        //Color del fondo
//        plot.setBackgroundPaint(Color.WHITE);
//        //Color de cuadrícula
//        plot.setRangeGridlinesVisible(true);
//        plot.setRangeGridlinePaint(Color.BLACK);
//        plot.setDomainGridlinesVisible(true);
//        plot.setDomainGridlinePaint(Color.BLACK);
//        
//        plot.setRenderer(renderer);
//        
//        int width = 1280;
//        int height = 960;
//        File XYChart = new File( "PasosRev.jpeg" ); 
//        ChartUtilities.saveChartAsJPEG( XYChart, xyChart, width, height);
        
    }
    
}
