#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun May  2 21:43:26 2021

@author: Murillo
"""

import math
import random
import numpy as np
import matplotlib.pyplot as plt

#%% 
"""
    Clase SkipNode.
"""

class SkipNode:
    
    # Constructor
    def __init__(self, elem):
        self.elem = elem
        self.izq = None
        self.der = None
        self.arr = None
        self.ab = None
    
    # Getters necesarios
    def getElem(self):      # Elemento
        return self.elem
    def getIzq(self):       # Izquierda
        return self.izq
    def getDer(self):       # Derecha
        return self.der
    def getArr(self):       # Arriba
        return self.arr
    def getAb(self):        # Abajo
        return self.ab
    
    # Setters necesarios
    def setElem(self, e):   # Elemento
        self.elem = e
    def setIzq(self, izq):  # Izquierda
        self.izq = izq
    def setDer(self, der):  # Derecha
        self.der = der
    def setArr(self, arr):  # Arriba
        self.arr = arr
    def setAb(self, ab):    # Abajo
        self.ab = ab
        

#%%
"""
    Clase SkipList.
"""


class SkipList:
    
    # Constructor
    def __init__(self):
        self.cabeza = SkipNode(None)    
        self.cola = SkipNode(None)
        self.ligaIzqDer(self.cabeza, self.cola)
        self.numListas = 1
        self.cont = 0
        
    
    # Getters necesarios
    def getNumListas(self):
        return self.numListas
    def getCont(self):
        return self.cont
    
    
    # Método que liga izquierda-derecha dos nodos dados como parámetros
    def ligaIzqDer(self, izquierda, derecha):
        izquierda.setDer(derecha)
        derecha.setIzq(izquierda)
    
    # Método que liga arriba-abajo dos nodos dados como parámetros
    def ligaArrAb(self, arriba, abajo):
        arriba.setAb(abajo)
        abajo.setArr(arriba)
    
    
    # Método de inserción
    def inserta(self, elem):
        actual = self.buscaAux(elem)
        nuevo = SkipNode(elem)
        sig = actual.getDer()
        
        self.cont += 1
        if math.log(self.cont, 2) > self.numListas:
            nCab = SkipNode(None)
            nCol = SkipNode(None)
            
            self.ligaIzqDer(nCab, nCol)
            self.ligaArrAb(nCab, self.cabeza)
            self.ligaArrAb(nCol, self.cola)
            
            self.cabeza = nCab
            self.cola = nCol
            
            self.numListas += 1
            
        self.ligaIzqDer(actual, nuevo)
        self.ligaIzqDer(nuevo, sig)
        
        i = 1
        v = [1,2,3,4,5,6,7,8,9,10]
        val = random.choice(v)
        #val = random.randint(0,1)
        while val <= 5 and i < self.numListas:
        #while val == 1 and i < self.numListas:
            
            while actual.getArr() == None:
                actual = actual.getIzq()
            
            n = SkipNode(elem)
            actual = actual.getArr()
            sig = actual.getDer()
            
            self.ligaIzqDer(actual, n)
            self.ligaIzqDer(n, sig)
            self.ligaArrAb(n, nuevo)
            
            nuevo = n
            #val = random.choice(v)
            val = random.randint(0,1)
            i += 1
    
    
    # Método de búsqueda auxiliar
    def buscaAux(self, elem):
        actual = self.cabeza
        
        i = 0
        while i < self.numListas:
            der = actual.getDer().getElem()
            while (der != None and der < elem):
                actual = actual.getDer()
                der = actual.getDer().getElem()
            if actual.getAb() != None:
                actual = actual.getAb()
            i += 1
        
        return actual
    
    # Método de búsqueda de un elemento
    def busca(self, elem):
        actual = self.buscaAux(elem)
        
        res = actual.getElem() == elem
        return res
    
    
    # Método de borrado
    def borra(self, elem):
        actual = self.buscaAux(elem)
        actual = actual.getDer()
        
        if not actual.getElem() == elem:
            print("- H -")
            return
        
        while actual != None:
            izq = actual.getIzq()
            der = actual.getDer()
            self.ligaIzqDer(izq, der)
            actual = actual.getArr()
        self.cont -= 1
        
        if self.cont != 0:
            listas = math.log(self.cont, 2)
            if self.numListas > listas+1 and self.numListas > 1:
                self.cabeza = self.cabeza.getAb()
                self.cola = self.cola.getAb()
                actual = self.cabeza
                while actual != None:
                    actual.setArr(None)
                    actual = actual.getDer()
                self.numListas -= 1
    
    
    # Método número de elementos por lista
    def numElemXList(self):
        res = []
        
        c = self.cabeza
        while c.getAb() != None:
            c = c.getAb()
            
        while c != None:
            actual = c.getDer()
            cont = 0
            while actual.getElem() != None:
                cont += 1
                actual = actual.getDer()
            res.append(cont)
            c = c.getArr()
        return res
    
    
    # Método que regresa el primer elemento "pilar"
    def pilar(self):
        actual = self.cabeza
        c = self.cabeza
        var = False
        
        i = 0
        while not var and i < self.numListas:
            actual = actual.getDer()
            while not var and actual != None:
                if actual.getElem() != None:
                    res = actual
                    var = True
                actual = actual.getDer()
            actual = c.getAb()
            c = c.getAb()
            i += 1
        return res.getElem()
        
    
    # Método de toString
    def toStrComp(self):
        
        c = self.cabeza
        while c.getAb() != None:
            c = c.getAb()
            
        print("")
        while c != None:
            cad = []
            actual = c.getDer()
            while actual.getElem() != None:
                cad.append(actual.getElem())
                actual = actual.getDer()
            c = c.getArr()
            print(cad)
    
    
    # Método de reestructuración
    def reestructura(self):
        
        c = self.cabeza
        while c.getAb() != None:
            c = c.getAb()
        
        self.cabeza = c
        self.cabeza.setArr(None)
        actual = self.cabeza.getDer()
        while actual.getElem() != None:
            actual.setArr(None)
            actual = actual.getDer()
        actual.setArr(None)
        self.cola = actual
        self.numListas = 1
        
        
        n = math.log(self.cont,2)
        while self.numListas < n:
            cab = self.cabeza
            c = SkipNode(cab.getElem())
            self.ligaArrAb(c,cab)
            self.cabeza = c
            r = c
            actual = cab.getDer()
            i = 1
            while actual.getElem() != None:
                if i % 2 == 0:
                    a = SkipNode(actual.getElem())
                    self.ligaArrAb(a,actual)
                    self.ligaIzqDer(r,a)
                    r = a
                actual = actual.getDer()
                i += 1
            tail = self.cola
            t = SkipNode(tail.getElem())
            self.ligaArrAb(t,tail)
            self.ligaIzqDer(r,t)
            self.cola = t
            self.numListas += 1
            

#%%
"""
    Creación y manejador de listas.
"""

# Creación de lista con valores lineales
def linear(n):
    res = []
    
    for i in range(0,n):
        res.append(i)
    return res


# Creación de lista con valores para logaritmo en base 2
def log(n):
    res = []
    
    l = list(range(1,n+1))
    
    for i in range(0,len(l)):
        c = math.log2(l[i])
        res.append(c)
    return res


# Creación de lista con los valores ideales
def ideal(entrada):
    res = []
    
    n = entrada[0]
    res.append(n)
    for i in range(1,len(entrada)):
        c = math.floor(n/2)
        res.append(c)
        n = c
    return res
        
            
#%% 
"""
    Gráficas
"""

# Obtención de gráfica de número de listas x inserción
def grafListIns(lista):
    
    # Obtención de datos necesarios
    x = list(range(0,len(lista)))
    lg = log(len(lista))
    
    # Inserción de los datos en gráfica
    plt.plot(x,lg)
    plt.plot(x,lista)
    # Asignación del título, nombres de los ejes y leyendas
    plt.title("Número de listas por Inserción")
    plt.xlabel("No. elementos insertados en la estructura")
    plt.ylabel("No. listas")
    plt.legend(["log_2","No. Listas"])
    # Muesta la gráfica en la pestaña 'Plots'
    plt.show()


# Obtención de gráfica de número de listas x inserción
def grafListBorr(lista):
    
    # Obtención de datos necesarios
    x = list(range(0,len(lista)))
    lg = log(len(lista))
    lg.reverse()
    
    # Inserción de los datos en gráfica
    plt.plot(x,lg)
    plt.plot(x,lista)
    # Asignación del título, nombres de los ejes y leyendas
    plt.title("Número de listas por Borrado")
    plt.xlabel("No. elementos borrados de la estructura")
    plt.ylabel("No. listas")
    plt.legend(["log_2","No. Listas"])
    # Muesta la gráfica en la pestaña 'Plots'
    plt.show()
    

# Obtención de la gráfica de datos reales vs ideales
def grafRealIdeal(real, ideal):
    
    numLista = linear(len(real))
    x = np.arange(len(numLista))
    width = 0.2
    
    # Inserción de los datos en gráfica
    fig, ax = plt.subplots()
    dataReal = ax.bar(x - width/2, real, width, label = 'Real')
    dataIdeal = ax.bar(x + width/2, ideal, width, label = 'Ideal')
    # Asignación del título, nombres de los ejes y leyendas
    ax.set_title("Número de elementos x Lista")
    ax.set_ylabel("No. Elementos")
    ax.set_xticks(x)
    ax.set_xticklabels(numLista)
    ax.legend()
    fig.tight_layout()
    # Muesta la gráfica en la pestaña 'Plots'
    plt.show()
    

#%% 
"""
    Pruebas Específicas
"""

#random.seed(12341445658252451346537624565)

# Buenas : 365969432

# Malas : 12341445658252451346537624565


s = SkipList()

ent = list(range(0,32))
#ent.reverse()
random.shuffle(ent)

ins = []
for i in range(0,len(ent)):
    s.inserta(ent[i])
    num = s.getNumListas()
    ins.append(num)
    #s.toStrComp()
    
s.reestructura()
print("")
print("SkipList")
s.toStrComp()

# Llamado a la gráfica de listas en inserción
#grafListIns(ins)

# Llamado al método de número de elementos x lista
numElem = s.numElemXList()
print("")
print("Número de elementos x Lista Real")
print(numElem)
numIdeal = ideal(numElem)
print("")
print("Número de elementos x Lista Ideal")
print(numIdeal)
grafRealIdeal(numElem, numIdeal)

print("")
print("Primer Elemento Pilar")
pilar = s.pilar()
print(pilar)

s.toStrComp()

borr = []
for i in range(0,len(ent)):
    s.borra(ent[i])
    num = s.getNumListas()
    borr.append(num)
    #s.toStrComp()

#s.toStrComp()

# Llamado a la gráfica de listas en borrado
#grafListBorr(borr)


