#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Feb 26 12:50:09 2021

@author: Murillo
"""

# Importación de las librerías utilizadas para:
# - random: creación y manejo de listas
# - time: medición el tiempo de ejecución de los algoritmos de ordenamiento
# - math: obtención de logaritmo de los tiempos de ejecución (para mejor 
#   visualización de los componentes de la gráfica)
# - pyplot: creación de gráfica
import random
import time
import math
from matplotlib import pyplot


#%%
"""
    Algoritmos de Ordenamiento
"""

# Método de ordenación por selección directa.
def selectionSort(lista):
    
    for i in range(0,len(lista)-1):
        min = i
        for j in range (i+1,len(lista)):
            if lista[j] < lista[min]:
                min = j
        aux = lista[min]
        lista[min] = lista[i]
        lista[i] = aux


# Método de ordenación por inserción directa.
def insertionSort(lista):
    
    for i in range(1,len(lista)):
        aux = lista[i]
        j = i
        while j > 0 and aux < lista[j-1]:
            lista[j] = lista[j-1]
            j = j-1
        lista[j]  = aux
        

# Método auxiliar para combinar las sub-colecciones.
def merge(lista, izq, der):
    i = 0
    j = 0
    k = 0
    
    while i < len(izq) and j < len(der):
        if izq[i] < der[j]:
            lista[k] = izq[i]
            i += 1
        else:
            lista[k] = der[j]
            j += 1
        k += 1
    
    while i < len(izq):
        lista[k] = izq[i]
        i += 1
        k += 1
    while j < len(der):
        lista[k] = der[j]
        j += 1
        k += 1

# Método de ordenación por combinación de sub-colecciones.
def mergeSort(lista):
    
    if len(lista) <= 1:
        return
    mit = len(lista) // 2
    izq = lista[:mit]
    der = lista[mit:]
    mergeSort(izq)
    mergeSort(der)
    merge(lista, izq, der)


#%%
"""
    Clase Pelicula
"""
class Pelicula:
    
    # Constructor con valores esperados:
    #   'id' - numérico, 'aho' - numérico, 'nombre'  - caracteres
    def __init__(self, id, aho, nombre):
        self.id = id
        self.aho = aho
        self.nombre = nombre
    
    
    # Método equivalente a toString() para UNA SOLA instancia.
    # El 'return' muestra toda la información de la película, aunque
    # se puede descomentar la otra variante si se necesita.
    def __str__(self):
        return str(self.id)+" ("+str(self.aho)+") "+": "+self.nombre
        #return str(self.id)
        
        
    # Método equivalente a toString() para cuando la instancia se
    # encuentra en alguna colección.
    # El 'return' muestra solamente el 'id' de la película. Esta variante
    # facilita la lectura del orden de los datos en una colección, pues 
    # si se imprime una lista con muchos datos de películas, convendría
    # no tener todos los atributos, sino solo aquellos que nos sirvan para
    # su análisis.
    def __repr__(self):
        #return str(self.id)+" ("+str(self.aho)+") "+" : "+self.nombre
        return str(self.id)
    
    
    # Método equivalente a compareTo(otro). Compara las 'id' de
    # las películas.
    def __lt__(self, otro):
        return self.id < otro.id
    

#%%
"""
    Métodos de creación y manejador de listas.
"""
# Método que imprime lista de manera vertical, es decir, imprime
# un elemento de la lista por línea en la consola.
def imprime(lista):

    n = len(lista)
    for i in range(0,n):
        print(lista[i])


# Método calcula y regresa el promedio de los valores de una lista.
def prom(lista):
    return sum(lista) / len(lista)
    

# Método que reordena de manera aleatoria una lista.
# En otras palabras, "barajea" el orden de los elementos de 
# la lista de entrada. Esto nos sirve para analizar los algoritmos de
# ordenamiento en su CASO PROMEDIO.
# No regresa una lista, sino que altera la de entrada
def reordenar(lista):
    random.shuffle(lista)
    

# Método ordena una lista por Insertion Sort.
# En este caso podemos poner cualquier algoritmo de ordenamiento, pues
# este método no lo ocupamos para evaluar el desempeño. Más bien, nos 
# sirve para analizar los algoritmos de ordenamiento en su MEJOR CASO.
# No regresa una lista, sino que altera la de entrada
def ordenar(lista):
    insertionSort(lista)
    

# Método que invierte el orden de los elementos de una lista.
# Llama al método {ordenar} para que ordene los elementos de entrada y 
# después apliqua el método propio de las listas llamado {reverse()}. Nos 
# sirve para analizar los algoritmos de ordenamiento en su PEOR CASO.
# No regresa una lista, sino que altera la de entrada
def invertir(lista):
    ordenar(lista)
    lista.reverse()


# Método creación de una lista aleatoria de números con repetición.
# Como entrada se tiene 'n': el número de elementos en la lista que se
# desea obtener.
# Regresa una lista.
def listaNumAl(n):
    lista = []
    
    # 'factor' nos sirve para considerar el rango en el cual el método
    # va a poder elegir números aleatorios para la inserción de la 
    # lista a generar
    factor = n // 2
    for i in range(n):
        # El valor aleatorio se obtiene de la selección de un número
        # entero entre '-factor' y 'factor'. Esto permite que se puedan
        # elegir valores negativos y positivos. Si el tamaño de la lista
        # se desea que sea 10, se eligirán números entre -5 y 5, por ejemplo.
        temp = random.randint(-factor, factor)
        lista.append(temp)
    return lista


# Método creación de una lista aleatoria de caracteres con repetición.
# Como entrada se tiene 'n': el número de elementos en la lista que se
# desea obtener.
# Regresa una lista.
def listaCarAl(n):
    lista = []
    
    for i in range(n):
        # El valor aleatorio se obtiene de la función {chr()} con los
        # valores entre 32 y 127 (códigos ASCII). El método regrsa el 
        # caracter correspondiente al código solicitado.
        # Se pueden agregar más caracteres de la misma manera.
        temp = chr(random.randint(32, 127)) + chr(random.randint(32, 127))
        #temp = chr(random.randint(32, 127))
        lista.append(temp)
    return lista


# Lectura de archivos & creación de lista con elementos de clase 'Pelicula'
# Como entrada se tiene 'nLin': el número de películas que se desea leer 
# del archivo "movie_titles.txt"
# Regresa una lista. 
def leeArch(nLin):
    lista = []
    
    i = 0
    lee = open('/Users/Murillo/Downloads/ITAM/Estructuras de Datos Avanzadas/Otros/movie_titles.txt')
    # Mientras todavía existan líneas por leer, se extrae la información.
    while i < nLin:
        linea = lee.readline()
        # Se ocupa el método {split()} para separar los valores que se
        # encuentran por línea en el documento de texto. Si nos damos 
        # cuenta, los valores se separan por dos comas: una para separar 
        # 'id' y 'año' y otra para separar 'año' y 'nombre'. Se pone un
        # máximo de separación de dos comas porque existen películas que
        # en su nombre tienen una coma. Regresa una lista.
        valores = linea.split(',',maxsplit = 2)
        # Asignación de los valores extraídos para la creación de la
        # instancia película.
        id = int(valores[0])
        aho = int(valores[1])
        nombre = valores[2]
        peli = Pelicula(id,aho,nombre)
        # Inserción de la película creada a la lista de salida.
        lista.append(peli)
        i += 1
    lee.close()
    return lista


#%%
"""
    Pruebas de los métodos de creación y manejo de listas
"""
      
# Pruebas de creación de las tres diferentes tipos de listas que se
# pueden generar: números con repetición, caracteres y películas
l0 = [5,4,3,2,1]
num = listaNumAl(50)
car = listaCarAl(50)
peli = leeArch(50)


# Prueba de impresión de lista
#imprime(num)


# Prueba de distintos ordenamientos de listas
#print('---------- Lista Original ----------')
#print(num)
#print('---------- Lista Ordenada Aleatoriamente ----------')
#reordenar(num)
#print(num)
#print('---------- Lista Ordenada ----------')
#ordenar(num)
#print(num)
#print('---------- Lista Ordenada Inversamente ----------')
#invertir(num)
#print(num)


"""
    Método prueba. De vital importancia para el análisis de los
    algoritmos de ordenamiento. Algunas funciones o métodos utilizados son:
        time.ns(): se obtiene el tiempo en nanosegundos del momento en que 
                   se corre la línea con el método. Lo ocupamos para obtener
                   el tiempo que se tarda un algoritmo de ordenamiento en
                   realizar su trabajo
        math.log(): se obtiene el logartimo del valor de entrada. Lo
                    ocupamos para obtener el logaritmo de los valores de
                    tiempo para que la gráfica que posteriormente se va a
                    crear sea más clara en sus valores
"""

# Método prueba. Tiene como entrada:
# - tipoLista: nombre de la función para la creación de listas 
#              antes mencionadas: listaNumAl, listaCarAl, leeArch
# - tipoOrden: nombre de la función para alterar el orden de los datos
#              de una lista por: reordenar, ordenar, invertir
# Regresa tres listas que contienen los datos de tiempo de ejecución 
# promedio que corresponden con el tamaño de entrada para selection,
# insertion y merge sort.
def prueba(tipoLista, tipoOrden):
    
    # Lista que permite el "control" del tamaño de entrada para
    # la creación y posterior ordenamiento de la lista.
    entrada = [100,500,1000,2500,5000,7500,10000,12000]
    
    # Listas que guardarán los valores de tiempos de ejecución por
    # entrada para cada algoritmo de ordenamiento.
    select = []
    insert = []
    merge = []
    
    # Estructura cíclica que permite la medición y la captación de
    # los tiempos de ejecución por entrada.
    for i in range(0,len(entrada)):
        # Extracción del tamaño de entrada a probar.
        ent = entrada[i]
        # Invoca a la función que recibe como parámetro para la
        # creación de la lista correspondiente, de tamaño indicado
        # por el valor de la lista de entrada.
        lista = tipoLista(ent)
        # Invoca a la función que recibe como parámetro para alterar
        # el orden de la lista que se va a ordenar por los algoritmos
        # de ordenamiento.
        tipoOrden(lista)
        
        # Listas que guardarán la medición de los 10 tiempos de 
        # ejecución por algoritmo para posteriormente obtener un promedio.
        promSelect = []
        promInsert = []
        promMerge = []
        
        # Estructura cíclica que permite la medición y captación de
        # tiempo para hacer el promedio.
        for j in range(0,10):
            # Selection Sort
            # Se copia la lista generada para alterar una copia y que
            # la original pueda ser utilizada por los demás métodos.
            copia = lista.copy()
            # Medición del tiempo de ejecución de uno de los algoritmos
            # de ordenamiento.
            t_inicio = time.time_ns()
            selectionSort(copia)
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            # Captación del tiempo a la lista correspondiente de promedios
            promSelect.append(t)
            
            # Insertion Sort
            copia = lista.copy()
            t_inicio = time.time_ns()
            insertionSort(copia)
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promInsert.append(t)
            
            # Merge Sort
            copia = lista.copy()
            t_inicio = time.time_ns()
            mergeSort(copia)
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promMerge.append(t)
            
        # Obtención y captación de tiempos promedio por entrada para
        # cada algoritmo de ordenamiento.
        tiempo =  prom(promSelect)
        select.append(math.log(tiempo))
        tiempo =  prom(promInsert)
        insert.append(math.log(tiempo))
        tiempo =  prom(promMerge)
        merge.append(math.log(tiempo))

    return select, insert, merge


#%%
"""
    Implementación del método {prueba} para la creación y obtención 
    de las gráficas indicadas para comparar resultados.
"""

# Llamado al método {prueba} para probar (valga la redundancia)
# los algoritmos de ordenamiento. 
# Para fines de este programa se pueden ordenar listas de: 
# - números enteros [listaNumAl], caracteres [listaCarAl] y películas [leeArch]
# Además se pueden elegir tres tipos de ordenamientos de la lista de entrada: 
# - en orden [ordenar], en orden inverso [invertir] y en orden aleatorio [reordenar]
tiempos = prueba(listaNumAl, ordenar)


# Componente de la abcisa: tamaño de entrada de datos.
x = [100,500,1000,2500,5000,7500,10000,12000]


# Componente de la ordenada: tiempo de ejecución promedio
# por tamaño de entrada. Los valores de cada algoritmo de
# ordenamiento se extrajeron del método {prueba}
select = tiempos[0]
insert = tiempos[1]
merg = tiempos[2]


# Inserción de los datos: (tamaño entrada, tiempo de ejecución) de
# los tres algoritmos de ordenamiento utilizados
pyplot.plot(x,select)
pyplot.plot(x,insert)
pyplot.plot(x,merg)


# Asignación del título, nombres de los ejes y leyenda que muestra
# cuál color corresponde al algoritmo de ordenamiento
pyplot.title("Números Ordenados")
pyplot.xlabel("Tamaño de entrada")
pyplot.ylabel("Tiempo de ejecución")
pyplot.legend(["Selection","Insertion","Merge"])


# Muesta la gráfica en la pestaña 'Plots'
pyplot.show()


#leeArch(12095)


