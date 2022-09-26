#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Apr 23 14:56:08 2021

@author: Murillo
"""

import math
import time
from matplotlib import pyplot


#%% Clase Pelicula

"""
    CLASE PELICULA Y CREACIÓN DE UNA LISTA DE PELICULAS ----------------------
"""

# Clase Película
class Pelicula:
    
    # Constructor con valores esperados:
    #   'id' - numérico, 'aho' - numérico, 'nombre'  - caracteres
    def __init__(self, id, aho, nombre):
        self.id = id
        self.aho = aho
        self.nombre = nombre
    
    
    # Getters necesarios
    def getId(self):    # Id
        return self.id
    def getAho(self):   # Aho
        return self.aho
    def getNombre(self):    # Nombre
        return self.nombre
    
    
    # Get Key:
    # El valor clave asociado a los objetos película va a ser su id.
    def getKey(self):
        return self.id
    
    
    # Método equivalente a toString() para una sola instancia.
    # El 'return' muestra toda la información de la película, aunque
    # se puede descomentar la otra variante si se necesita: se muestra
    # solamente el id.
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


# Lectura de archivos y creación de lista con elementos de clase 'Pelicula'
# Como entrada se tiene 'nLin': el número de películas que se desea leer 
# del archivo "movie_titles.txt"
# Regresa una lista con los elementos en su interior. 
def listaPeli(nLin):
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
        # en su nombre tienen una coma. Regresa una lista con estos valores.
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




#%% Clase Hash Node

"""
    HASH NODE y HASH TABLE ---------------------------------------------------
"""

# Clase HashNode
class HashNode:
    
    # Constructor:
    #   elem - cualquier tipo de dato, 
    #          en este caso se almacenan películas.
    #   next - Hash Node
    def __init__(self, elem):
        self.elem = elem
        self.next = None
    
    
    # Getters necesarios
    def getElem(self):  # Elemento
        return self.elem
    def getNext(self):  # Nodo
        return self.next
    
    
    # Setters necesarios
    def setElem(self,e):    # Elemento
        self.elem = e
    def setNext(self,n):    # Nodo
        self.next = n


    # GetKey:
    # Se extrae el valor llave propio del elemento guardado en el nodo.
    def getKey(self):
        return self.elem.getKey()




#%% Clase HashTable

# Clase HashTable
class HashTable:
    
    # Constructor
    def __init__(self, tamaho):
        # Se crea una lista del tamaño dado como parámetro y en cada casilla
        # se guarda a un Nodo Hash que contiene un 'None' como elemento.
        #   Estos nodos son los 'centinelas' de las listas que se guardarán
        #   en cada casilla de la tabla para su veloz acceso.
        self.tabla = [HashNode(None) for i in range(tamaho)]
        self.cont = 0
    
    
    # Getter de la tabla
    def getTabla(self):
        return self.tabla
    
    
    # Inserción de elemento en la tabla utilizando la función de hash 
    # por el método de la división.
    def inserta(self, elem):
        # Se crea el nuevo nodo con el elemento dado como parámetro.
        nuevo = HashNode(elem)
        
        # Se obtiene la posición en donde debería ir el elemento
        pos = self.funcHashDiv(nuevo) % len(self.tabla)
        # Se inserta en la tabla, como primer elemento de la lista que 
        # se encuentra en la posición indicada
        aux = self.tabla[pos].getNext()
        self.tabla[pos].setNext(nuevo)
        nuevo.setNext(aux)
        # Aumenta el contador de elementos
        self.cont += 1
        
    # Inserción de elemento en la tabla utilizando la función de hash 
    # por el método de la multiplicación. Esta variante del método de
    # inserción es para futuras comparaciones entre ambos métodos de hash.
    def insertaM(self, elem):
        nuevo = HashNode(elem)
        
        # Se obtiene la posición en donde debería ir el elemento
        pos = self.funcHashMult(nuevo) % len(self.tabla)
        # Se inserta en la tabla
        aux = self.tabla[pos].getNext()
        self.tabla[pos].setNext(nuevo)
        nuevo.setNext(aux)
        # Aumenta el contador de elementos
        self.cont += 1


    # Método de búsqueda de un elemento en la tabla de hash.
    def busca(self, elem):
        # Se crea un nodo auxiliar con el elemento que se desea buscar
        # para poder extraer el valor de hash y encontrar la posición donde
        # debería de encontrarse el elemento.
        aux = HashNode(elem)
        # Bandera de fin de datos inicializada en False
        var = False
        
        # Se obtiene la posición.
        pos = self.funcHashDiv(aux) % len(self.tabla)
        # Se empieza a recorrer la lista almacenada en la casilla.
        actual = self.tabla[pos].getNext()
        # Mientras que la bandera de fin de datos sea False y no se haya
        # terminado la lista, se recorre nodo por nodo
        while (not var and actual != None):
            # Si el elemento guardado en el nodo es igual al elemento que
            # se desea buscar, la bandera de fin de datos cambia a True
            if actual.getElem() == elem:
                var = True
            actual = actual.getNext()
        # Regresa si el elemento se encuentra o no en la tabla de hash.
        return var
    
    
    # Método de borrado de un elemento en la tabla de hash.
    def borra(self, elem):
        # Se crea un nodo auxiliar con el elemento que se desea buscar
        # para poder extraer el valor de hash y encontrar la posición donde
        # debería de encontrarse el elemento.
        aux = HashNode(elem)
        
        # Se obtiene la posición.
        pos = self.funcHashDiv(aux) % len(self.tabla)
        # Recordamos al nodo previo al que está recorriendo la lista.
        prev = self.tabla[pos]
        # Se empieza a recorrer la lista.
        actual = prev.getNext()
        # Mientras que no se acabe la lista y el elemento almacenado en el
        # nodo no sea igual al elemento que se desea borrar, se avanza nodo
        # por nodo. Siempre se recuerda al nodo previo.
        while (actual != None and not actual.getElem() == elem):
            prev = actual
            actual = actual.getNext()
        # Si al salir del recorrido no se acabó la lista y el elemento
        # guardado en el nodo es igual al elemento que se desea borrar,
        # simplemente se elimina al nodo 'actual'.
        if actual != None and actual.getElem() == elem:
            aux = actual.getNext()
            prev.setNext(aux)
            self.cont -= 1
       
    
    # Función de Hash por división
    def funcHashDiv(self,elem):
        k = elem.getKey()
        res = k % len(self.tabla)
        return res

    # Función de Hash por multiplicación
    def funcHashMult(self,elem):
        k = elem.getKey()
        a = 1/((1+math.sqrt(5))/2)
        res = math.floor(len(self.tabla)*(k*a-math.floor(k*a)))
        return res
    
    
    # Método que regresa el número de datos contenidos en
    # cada una de las casillas de la tabla
    def numDatosXCasilla(self):
        # Se inicializa una lista vacía de resultados
        res = []
        
        # Se hace un recorrido completo de la tabla (m vueltas por el
        # tamaño m de la tabla).
        for i in range(0,len(self.tabla)):
            # Se inicializa un contador.
            cont = 0
            # Se empieza a recorrer la lista almacenada en cada casilla de
            # la tabla de hash.
            actual = self.tabla[i].getNext()
            # Mientras que no se acabe la lista, se aumenta el contador.
            while actual != None:
                cont += 1
                actual = actual.getNext()
            # Se agrega el contador a la lista de resultados.
            res.append(cont)
        # Devuelve una lista con m datos.
        return res
    
    
    # Método que devuelve el promedio de la cantidad de datos
    # de las casillas de la tabla.
    #   Se llama al méotdo {prom}, especificado más abajo.
    def promDatosXCasilla(self):
        return prom(self.numDatosXCasilla())
    
    
    # Método que devuelve el número de lugares vacíos que se encuentran
    # en tabla.
    def numCasillasVacias(self):
        cont = 0
        
        # Se hace un recorrido a las m casillas de la tabla.
        for i in range(0,len(self.tabla)):
            # Se obtiene el nodo que se encuentra exactamente al principio
            # de la lista.
            actual = self.tabla[i].getNext()
            # Si el nodo es None, aumenta el contador.
            if actual == None:
                cont +=1
        return cont
    
    
    # Método que imprime la tabla de hash.
    def toStr(self):
        
        for i in range(0,len(self.tabla)):
            actual = self.tabla[i].getNext()
            print(i)
            while actual != None:
                print(actual.getElem())
                actual = actual.getNext()




#%% Creación y manejador listas

""" 
    CREACIÓN Y MANEJADOR DE LISTAS -------------------------------------------
"""

# Método que calcula y regresa el promedio de los valores de una lista
# dada como parámetro.
def prom(lista):
    return sum(lista) / len(lista)


# Lista, valor fijo y nombre para el caso general
#   Para ambos métodos utilizados, a saber: { pruebaTamaniofijo } y 
#   { pruebaEntradaFija } se consideran en su generalidad de casos 
#   n < m, n ~ m y n > m
def casoGeneral():
    lista = [1000,2000,3500,5000,6500,8000,10000]
    valorFijo = 5000
    caso = "Caso General"
    return lista, valorFijo, caso


# Lista, valor fijo y nombre para el caso 1
# Con el método { pruebaTamaniofijo } :
#       Lista para caso     n < m
#       Es decir, cuando la entrada de datos (n) es mucho menor al tamaño 
#       de la tabla (m)
#
# Con el método { pruebaEntradaFija } :
#       Lista para caso     n > m
#       Es decir, cuando la entrada de datos (n) es mucho mayor al tamaño
#       de la tabla (m)
def caso1():
    lista = [150,300,450,600,750,900,1050,1200,1350,1500]
    valorFijo = 5000
    caso = "Caso 1"
    return lista, valorFijo, caso


# Lista, valor fijo y nombre para el caso 2
#   Para ambos métodos utilizados: { pruebaTamaniofijo } y { pruebaEntradaFija }
#   se considera el caso n ~ m , es decir, cuando la entrada de datos (n)
#   es aproximadamente igual al tamaño de la tabla (m)
def caso2():
    lista = [4000,4200,4400,4600,4800,5000,5200,5400,5600,5800]
    valorFijo = 5000
    caso = "Caso 2"
    return lista, valorFijo, caso


# Lista, valor fijo y nombre para el caso 3
# Con el método { pruebaTamaniofijo } :
#       Lista para caso     n > m
#       Es decir, cuando la entrada de datos (n) es mucho mayor al tamaño 
#       de la tabla (m)
#
# Con el método { pruebaEntradaFija } :
#       Lista para caso     n < m
#       Es decir, cuando la entrada de datos (n) es mucho menor al tamaño
#       de la tabla (m)
def caso3():
    lista = [8650,8800,8950,9100,9250,9400,9550,9700,9850,10000]
    valorFijo = 5000
    caso = "Caso 3"
    return lista, valorFijo, caso




#%% Métodos obtención de datos

""" 
    MÉTODOS DE PRUEBA --------------------------------------------------------
"""

# Método de prueba para conocer el comportamiento de la tabla de hash cuando 
# el tamaño de esta es fijo y la cantidad de datos insertados, buscados y 
# borrados cambia. 
#   Esta prueba puede ser llamada en el método { graficasCasos }
def pruebaTamanioFijo(entradas, valorFijo):
    
    # Se crean las listas que van a almacenar los promedios generales 
    # de tiempo para cada operación: insertar, buscar y borrar.
    inserta = []
    busca = []
    borra = []
    # Se recorre cada tamaño de entrada de datos de la lista dada como 
    # parámetro
    for i in range(0,len(entradas)):
        # Se obtiene el valor del tamaño de entrada correspondiente.
        ent = entradas[i]
        # Se crea una lista de películas con el tamaño de entrada indicado
        pelis = listaPeli(ent)
        # Se crea una tabla de hash con el valor fijo asignado
        tabla = HashTable(valorFijo)
        
        # Se crean las listas que almacenarán promedios particulares de tiempo
        # para cada una de las operaciones.
        promIns = []
        promBus = []
        promBorr = []
        # Se hace el cálculo de tiempo 20 veces
        for j in range(0,20):
            
            # - Se hace una primera medición del valor de tiempo antes de 
            #   procesar los n datos del tamaño de entrada propia de la vuelta
            # - Se hace una segunda medición del valor de tiempo después de 
            #   procesar los n datos
            # - Se calcula el tiempo tomado del proceso
            # - Se inserta el tiempo en la lista de promedios particulares.
            #
            # Este procedimiento se hace para los tres procesos considerados:
            # insertar, buscar, borrar un dato
            t_inicio = time.time_ns()
            for k in range(0,len(pelis)):
                tabla.inserta(pelis[k])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promIns.append(t)
            
            t_inicio = time.time_ns()
            for k in range(0,len(pelis)):
                tabla.busca(pelis[k])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promBus.append(t)
            
            t_inicio = time.time_ns()
            for k in range(0,len(pelis)):
                tabla.borra(pelis[k])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promBorr.append(t)
        
        # - Se calcula el promedio de tiempo de las 20 vueltas consideradas 
        #   para el proceso
        # - Se inserta el promedio en la lista de promedios generales del
        #   proceso
        tiempo = prom(promIns)
        inserta.append(tiempo)
        tiempo = prom(promBus)
        busca.append(tiempo)
        tiempo = prom(promBorr)
        borra.append(tiempo)
    
    # Recordatorio de la variable que cambia: la entrada de datos.
    nombre = "Cantidad de entrada de datos"
    # Regresa la lista de la cantidad de datos de entrada consideradas, las 
    # listas de promedios generales de tiempo y el recordatorio.
    return entradas, inserta, busca, borra, nombre


# Método de prueba para conocer el comportamiento de la tabla de hash cuando 
# la cantidad de datos de entrada es fija y el tamaño de la tabla cambia para
# las operaciones de inserción, búsqueda y borrado.
#
#   Esta prueba puede ser llamada en el método { graficasCasos }
def pruebaEntradaFija(tamanios, valor):
    
    # Se crean las listas que van a almacenar los promedios generales 
    # de tiempo para cada operación: insertar, buscar y borrar.
    inserta = []
    busca = []
    borra = []
    # Se recorre cada tamaño de tablas de la lista dada como parámetro.
    for i in range(0,len(tamanios)):
        # Se obtiene el valor del tamaño de la tabla de hash a considerar.
        tam = tamanios[i]
        # Se crea una lista de películas con el valor fijo asignado
        pelis = listaPeli(valor)
        # Se crea una tabla de hash con el tamaño indicado
        tabla = HashTable(tam)
        
        
        # Se crean las listas que almacenarán promedios particulares de tiempo
        # para cada una de las operaciones
        promIns = []
        promBus = []
        promBorr = []
        # Se hace el cálculo de tiempo 15 veces
        for j in range(0,15):
            
            # - Se hace una primera medición del valor de tiempo antes de 
            #   procesar los n datos del tamaño de entrada propia de la vuelta
            # - Se hace una segunda medición del valor de tiempo después de 
            #   procesar los n datos
            # - Se calcula el tiempo tomado del proceso
            # - Se inserta el tiempo en la lista de promedios particulares.
            #
            # Este procedimiento se hace para los tres procesos considerados:
            # insertar, buscar, borrar un dato
            t_inicio = time.time_ns()
            for k in range(0,len(pelis)):
                tabla.inserta(pelis[k])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promIns.append(t)
            
            t_inicio = time.time_ns()
            for k in range(0,len(pelis)):
                tabla.busca(pelis[k])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promBus.append(t)
            
            t_inicio = time.time_ns()
            for k in range(0,len(pelis)):
                tabla.borra(pelis[k])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promBorr.append(t)
        
        # - Se calcula el promedio de tiempo de las 20 vueltas consideradas 
        #   para el proceso
        # - Se inserta el promedio en la lista de promedios generales del
        #   proceso
        tiempo = prom(promIns)
        inserta.append(tiempo)
        tiempo = prom(promBus)
        busca.append(tiempo)
        tiempo = prom(promBorr)
        borra.append(tiempo)
    
    # Recordatorio de la variable que cambia: el tamaño de la tabla de hash.
    nombre = "Tamaño de Tabla de Hash"
    # Regresa la lista de los tamaños de la tabla de hash considerados, las 
    # listas de promedios generales de tiempo y el recordatorio.
    return tamanios, inserta, busca, borra, nombre


# Método de prueba relacionado a la particularidad del funcionamiento de la
# tabla de hash cuando la cantidad de datos de entrada cambia. Permite
# analizar el tiempo de ejecución de cada operación (inserción, búsqueda y
# borrado) para cada dato de entrada. 
#
#   Esta prueba es llamada en el método { graficasFuncionamiento }
def pruebaParticular(n, m, tope):
    
    # Creación de una lista de n películas, sea n dada como parámetro
    pelis = listaPeli(n)
    # Creación de una tabla de hash con tamaño m, sea m dada como parámetro
    h = HashTable(m)
    
    # Se crea una lista para los promedios generales de tiempo de la inserción
    ins = []
    # Se van a correr n vueltas para insertar las n películas
    for i in range(0,n):
        # Se crea una lista para los promedios particulares de tiempo de la 
        # inserción
        promIns = []
        # Se hace el cálculo de tiempo 30 veces para cada dato insertado
        for j in range(0,30):
            # - Se toma el inicio de tiempo del proceso
            # - Se inserta el dato en la tabla de hash
            # - Se toma el fin de tiempo del proceso
            # - Se hace la diferencia del tiempo
            t_inicio = time.time_ns()
            h.inserta(pelis[i])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            # Se inserta el resultado en la lista de promedios particulares 
            # de tiempo
            promIns.append(t)
            # Se borra al elemento para volverlo a insertar (las 30 veces)
            h.borra(pelis[i])
        # Se promedian las 30 mediciones de tiempo
        p = prom(promIns)
        # Si el promedio es más grande que el tope dado como parámetro,
        # el tiempo va a estar dado por el tope.
        if p > tope:
            tiempo = tope
        # Si no es asi, el tiempo va a estar dado por el promedio calculado
        else:
            tiempo = p
        # Se inserta el resultado en la tabla de tiempos generales 
        # correspondiente
        ins.append(tiempo)
        # Dado que el dato se insertó y se borró 30 veces, se inserta 
        # nuevamente el dato para "marcar" permanentemente esta operación.
        h.inserta(pelis[i])  
    
    # De manera análoga se repite el procedimiento ocupado en la operación
    # de inserción para la operación de búsqueda.
    bus = []
    for i in range(0,n):
        promBus = []
        for j in range(0,30):
            # - Se toma el inicio de tiempo del proceso
            # - Se busca al dato en la tabla de hash
            # - Se toma el fin de tiempo del proceso
            # - Se hace la diferencia del tiempo
            t_inicio = time.time_ns()
            h.busca(pelis[i])
            t_fin = time.time_ns()
            t = t_fin - t_inicio
            promBus.append(t)
        p = prom(promBus)
        if p > tope:
            tiempo = tope
        else:
            tiempo = p
        bus.append(tiempo)
        # Dado que solo se buscó al dato en la tabla, no se realiza ninguna
        # operación adicional.
    
    
    borr = []
    # NOTA IMPORTANTE: Dado que los elementos están "ordenados" o "acomodados"
    # en la tabla de hash debido al orden de inserción, solamente podemos borrar
    # una vez al elemento en la tabla para considerar el tiempo del proceso
    # Por lo tanto, no se tendrá una lista con promedios particulares
    for i in range(0,n):
        # - Se toma el inicio de tiempo del proceso
        # - Se busca al dato en la tabla de hash
        # - Se toma el fin de tiempo del proceso
        # - Se hace la diferencia del tiempo
        t_inicio = time.time_ns()
        h.borra(pelis[i])
        t_fin = time.time_ns()
        t = t_fin - t_inicio
        p = t
        if p > tope:
            tiempo = tope
        else:
            tiempo = p
        borr.append(tiempo)
        h.borra(pelis[i])
        
    # Se imprimen en la consola los promedios de tiempos de las diferentes 
    # operaciones consideradas.
    print("")
    print("-------------------------------")
    print("Promedio de inserción en tiempo:")
    print(prom(ins))
    print("")
    print("-------------------------------")
    print("Promedio de búsqueda en tiempo:")
    print(prom(bus))
    print("")
    print("-------------------------------")
    print("Promedio de borrado en tiempo:")
    print(prom(borr))
    print("")
    
    # Regresa las listas con el promedio general de tiempo de las operaciones.
    return ins, bus, borr


# Método de prueba relacionado al análisis de la cantidad de datos almacenados
# en cada casilla de la tabla, el número de ellas que están vacías y el
# promedio de la cantidad de datos que hay en las listas.
#
#   Esta prueba es llamada en el método { graficasFuncionamiento }
def pruebasDatos(n,m):
    
    # Creación de una lista de n películas, sea n dado como parámetro
    pelis = listaPeli(n)
    # Creación de una tabla de hash con tamaño m, sea m dado como parámetro
    h = HashTable(m)
    
    # Se insertan las n películas en la tabla de hash
    for i in range(0,n):
        h.inserta(pelis[i])
    
    # Obtención de los datos necesarios:
    # - Lista con el número de datos de cada casilla de la tabla
    datosXCasilla = h.numDatosXCasilla()
    # - Número de casillas vacías de la tabla de hash
    numVacias = h.numCasillasVacias()
    # - Promedio general de número de datos en las casillas de la tabla
    promDatos = h.promDatosXCasilla()
    
    # Se imprimen los datos importantes sobre la prueba considerada
    print("")
    print("-------------------------------")
    print("Número de casillas vacías:")
    print(numVacias)
    print("")
    print("-------------------------------")
    print("Promedio de datos por casilla:")
    print(promDatos)
    
    # Regresa la lista de los datos existentes en cada lista de la tabla.
    return datosXCasilla


# Método de prueba relacionado a la comparación del desempeño de la inserción
# con dos diferentes métodos de hash: el método de la división y el de la 
# multiplicación. En esta prueba se obtienen los datos necesarios para el
# análisis de la cantidad de datos almacenados en cada casilla de la tabla, 
# el número de ellas que están vacías y el promedio de la cantidad de datos 
# que hay en las listas después de haber la inserción correspondiente.
#
#   Esta prueba es llamada en el método { graficasComparacion }
def pruebaComparacion(n,m,tope):
    
    # Se crea una lista de películas de tamaño n (dada como parámetro).
    pelis = listaPeli(n)
    # Se crean dos tablas de hash de tamaño m (dada como parámetro) para
    # realizar los dos diferentes tipos de inserción.
    h1 = HashTable(m) # H1 para el método de la división
    h2 = HashTable(m) # H2 para el método de la multiplicación.
    
    # Se crea una lista que almacena las mediciones de tiempo de
    # inserción por el método de la división.
    insDiv = []
    # Se insertan los n datos de entrada.
    for i in range(0,n):
        # Se toma la medición inicial antes de la operación.
        t_inicio = time.time_ns()
        # Se inserta al dato en la tabla H1
        h1.inserta(pelis[i])
        # Se toma la medición final después de la operación.
        t_fin = time.time_ns()
        # Se hace la diferencia de tiempos
        t = t_fin - t_inicio
        p = t
        # Si la diferencia de tiempos es más grande que el tope dado 
        # como parámetro, el tiempo va a estar dado por el tope.
        if p > tope:
            tiempo = tope
        else:
            tiempo = p
        # Se agrega el tiempo a la lista correspondiente
        insDiv.append(tiempo)
    
    # Se crea una lista que almacena las mediciones de tiempo de
    # inserción por el método de la multiplicación.
    insMult = []
    for i in range(0,n):
        t_inicio = time.time_ns()
        # Se inserta al dato en la tabla H2
        h2.insertaM(pelis[i])
        t_fin = time.time_ns()
        t = t_fin - t_inicio
        p = t
        if p > tope:
            tiempo = tope
        else:
            tiempo = p
        insMult.append(tiempo)
        
    # Se obtienen las listas con el número de datos en cada casilla de 
    # ambas tablas
    datosDiv = h1.numDatosXCasilla()
    datosMult = h2.numDatosXCasilla()
    
    # Se calcula el número de casillas vacías de ambas tablas de hash y
    # se imprimen los resultados en la consola
    print("")
    print("-------------------------------")
    print("Número vacías por (División)")
    numVaciasDiv = h1.numCasillasVacias()
    print(numVaciasDiv)
    print("")
    print("-------------------------------")
    print("Número vacías por (Multiplicación)")
    numVaciasMult = h2.numCasillasVacias()
    print(numVaciasMult)
    
    # Se obtiene el promedio general de número de datos en las casillas 
    # de ambas tablas y se imprimen los resultados en la consola.
    print("")
    print("-------------------------------")
    print("Promedio datos por casilla (División)")
    promDatosDiv = h1.promDatosXCasilla()
    print(promDatosDiv)
    print("")
    print("-------------------------------")
    print("PromDatosXCasilla (Multiplicación)")
    promDatosMult = h2.promDatosXCasilla()
    print(promDatosMult)
    
    # Se imprimen los promedios de tiempos de los procesos de inserción
    # por el método de la división y por el de la multiplicación.
    print("")
    print("-------------------------------")
    print("Promedio de inserción función división en tiempo:")
    print(prom(insDiv))
    print("")
    print("-------------------------------")
    print("Promedio de búsqueda función multiplicación en tiempo:")
    print(prom(insMult))
    
    # Devuelve las listas de tiempos de ejecución de ambas inserciones
    # y las listas de los números de datos en cada casilla de las tablas.
    return insDiv, insMult, datosDiv, datosMult



#%% Gráficas

"""
    CREACIÓN Y OBTENCIÓN DE GRÁFICAS -----------------------------------------
"""

# Creación de gráficas relacionadas a los casos.
# Parámetros:
#  - tipoPrueba: el nombre del tipo de prueba que se desea hacer, las
#                opciones son:
#                1. pruebaTamanioFijo
#                2. pruebaEntradaFija
#  - caso: el nombre del caso que se desea considerar, las opciones son:
#          1. casoGeneral
#          2. caso1
#          3. caso2
#          4. caso3
def graficasCasos(tipoPrueba, caso):
    
    # Se llama al caso dado como parámetro para obtener la lista (de entradas
    # o de tamaños), el valor fijo y el nombre del caso.
    dataCaso = caso()
    # Se llama al tipo de prueba dado como parámetro
    valores = tipoPrueba(dataCaso[0],dataCaso[1])
    
    # Obtención de los valores necesarios para graficar.
    x = valores[0]          # Cantidad de entrada / tamaño de tabla 
    ins = valores[1]        # Tiempos de ejecución de la inserción
    bus = valores[2]        # Tiempos de ejecución de la búsqueda
    borr = valores[3]       # Tiempos de ejecución de la eliminación
    
    # Inserción de los datos en gráfica
    pyplot.plot(x,ins,'tab:blue')
    pyplot.plot(x,bus,'tab:orange')
    pyplot.plot(x,borr,'tab:green')
    
    # Asignación del título, nombres de los ejes y leyendas
    pyplot.title("Desempeño en funcionamiento"+" : "+dataCaso[2])
    pyplot.xlabel(valores[4])
    pyplot.ylabel("Tiempo de ejecución (ns)")
    pyplot.legend(["inserta()","busca()","borra()"])
    
    # Muesta la gráfica en la pestaña 'Plots'
    pyplot.show()


# Creación de gráficas relacionadas al funcionamiento.
# Parámetros:
#  - signo: hace la diferenciación entre los casos posibles. Las opciones 
#           son:
#           1. "<" : n < m
#           2. "=" : n = m
#           3. ">" : n > m
#  - cantidad: determina si se desea analizar el caso para un dataset pequeño
#              o más grande. Las opciones son:
#              1. 0 : datos pequeños
#              1. 1 : datos grandes
def graficasFuncionamiento(signo,cantidad):
    
    # Se decide el tamaño de entrada de datos y el tamaño de la tabla de hash
    # dependiendo de los parámetros
    if cantidad == 0:
        if signo == "<":
            n = 100
            m = 1000
            # Recomendado 8,000.00
            tope = 8000.00
        elif signo == "=":
            n = 1000
            m = 1000
            # Recomendado 12,000.00
            tope = 12000.00
        else:
            n = 1000
            m = 100
            # Recomendado 12,000.00
            tope = 12000.00
    else:
        if signo == "<":
            n = 500
            m = 5000
            # Recomendado 12,000.00
            tope = 12000.00
        elif signo == "=":
            n = 5000
            m = 5000
            # Recomendado 17,000.00
            tope = 17000.00
        else:
            n = 5000
            m = 500
            # Recomendado 15,000.00
            tope = 15000.00
    
    # Gráfica sobre el número de datos por casilla de la tabla
    # Obtención de los valores necesarios
    numCasillas = list(range(0,m))
    datosXCasilla = pruebasDatos(n, m)
    
    # Inserción de los datos en gráfica
    pyplot.bar(numCasillas,datosXCasilla,1.0,color='mediumvioletred')
    
    # Asignación del título, nombres de los ejes y leyendas
    pyplot.title("Cantidad de elementos por casilla")
    pyplot.xlabel("No. Casilla")
    pyplot.ylabel("No. Elementos")
   
    # Muesta la gráfica en la pestaña 'Plots'
    pyplot.show()
    
    
    # Gráfica sobre tiempos de ejecución
    # Obtención de los valores necesarios
    valoresTiempo = pruebaParticular(n, m, tope)
    cantDatos = list(range(0,n))
    ins = valoresTiempo[0]
    bus = valoresTiempo[1]
    borr = valoresTiempo[2]
    
    # Inserción de los datos en gráfica
    pyplot.plot(cantDatos,ins,'tab:blue')
    pyplot.plot(cantDatos,bus,'tab:orange')
    pyplot.plot(cantDatos,borr,'tab:green')
    
    # Asignación del título, nombres de los ejes y leyendas
    pyplot.title("Desempeño en tiempos")
    pyplot.xlabel("No. Datos")
    pyplot.ylabel("Tiempo de ejecución (ns)")
    pyplot.legend(["inserta()","busca()","borra()"])
    
    # Muesta la gráfica en la pestaña 'Plots'
    pyplot.show()
    


# Creación de gráficas relacionadas a comparaciones de las funciones hash
# por el método de la división y por el de la multiplicación.
# Parámetros:
#  - n : cantidad de entrada de datos; el tope máximo es 10,000
#  - m : tamaño de la tabla de hash
#  - tope : tope para una mejor visualización de las mediciones de tiempo
def graficasComparacion(n, m, tope):
    
    # Obtención de los datos necesarios para las gráficas 1 y 2
    datos = pruebaComparacion(n, m, tope)
    numCasillas = list(range(0,m))
    datosDiv = datos[2]
    datosMult = datos[3]
    
    # Inserción de los datos en gráfica 1 sobre cantidad de elementos 
    # por casilla
    pyplot.bar(numCasillas,datosDiv,1.0,color='mediumvioletred')
    pyplot.bar(numCasillas,datosMult,1.0,color='mediumpurple')
    # Asignación del título, nombres de los ejes y leyendas
    pyplot.title("Cantidad de elementos por casilla Mult sobre Div")
    pyplot.xlabel("No. Casilla")
    pyplot.ylabel("No. Elementos")
    pyplot.legend(["Div Hash","Mult Hash"])
    # Muesta la gráfica 1 en la pestaña 'Plots'
    pyplot.show()
    
    
    # Inserción de los datos en gráfica 2 sobre cantidad de elementos 
    # por casilla
    pyplot.bar(numCasillas,datosMult,1.0,color='mediumpurple')
    pyplot.bar(numCasillas,datosDiv,1.0,color='mediumvioletred')
    # Asignación del título, nombres de los ejes y leyendas
    pyplot.title("Cantidad de elementos por casilla Div sobre Mult")
    pyplot.xlabel("No. Casilla")
    pyplot.ylabel("No. Elementos")
    pyplot.legend(["Mult Hash","Div Hash"])
    # Muesta la gráfica 2 en la pestaña 'Plots'
    pyplot.show()
    
    
    # Obtención de los datos necesarios para la gráfica 3
    cantDatos = list(range(0,n))
    insDiv = datos[0]
    insMult = datos[1]
    # Inserción de los datos en gráfica 3 sobre los tiempos de ejecución
    pyplot.plot(cantDatos,insDiv,color='mediumvioletred')
    pyplot.plot(cantDatos,insMult,color='mediumpurple')
    # Asignación del título, nombres de los ejes y leyendas
    pyplot.title("Desempeño en tiempos")
    pyplot.xlabel("No. Datos")
    pyplot.ylabel("Tiempo de ejecución (ns)")
    pyplot.legend(["Div Hash","Mult Hash"])
    # Muesta la gráfica en la pestaña 'Plots'
    pyplot.show()


#%% Llamados generales

"""
    LLAMADAS A MÉTODOS FINALES -----------------------------------------------
"""

# Llamada a creación y obtención de gráficas para casos
#graficasCasos(pruebaEntradaFija,casoGeneral)


# Llamada a creación y obtención de gráficas sobre funcionamiento
signo = ">"
cantidad = 0
#graficasFuncionamiento(signo, cantidad)


# llamada a creación y obtención de gráficas para comparar desempeño de las
# funciones de hash de división y de multiplicación
#graficasComparacion(100,100,8000)



