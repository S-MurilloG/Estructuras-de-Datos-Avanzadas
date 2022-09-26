#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 23 18:14:45 2021

@author: Murillo
"""

import random
import math
from matplotlib import pyplot


#%%
"""
    Clases BinaryNode y BinarySearchTree.
"""

class BinaryNode:
    
    # Constructor
    def __init__(self, elem):
        self.elem = elem
        self.izq = None
        self.der = None
        self.pa = None
    
    
    # Getters necesarios
    def getElem(self):
        return self.elem
    def getIzq(self):
        return self.izq
    def getDer(self):
        return self.der
    def getPa(self):
        return self.pa 
    
    
    # Setters necesarios
    def setElem(self, e):
        self.elem = e
    def setIzq(self, izq):
        self.izq = izq
    def setDer(self, der):
        self.der = der
    def setPa(self, pa):
        self.pa = pa
    
    
    # Método elimina subárbol
    def eliminaSubArb(self, elem):
        
        if elem == None:
            return
        if elem <= self.elem:
            self.setIzq(None)
        else:
            self.setDer(None)
    
    
    # Método cuelga
    def cuelga(self, node):
        
        if node.getElem() <= self.elem:
            self.setIzq(node)
        else:
            self.setDer(node)
        node.setPa(self)
    
    
    # Método equivalente a compareTo(otro)
    def __lt__(self, otro):
        return self.elem < otro.elem
    

class BinarySearchTree:
    
    # Constructor
    def __init__(self):
        self.raiz = None
        self.cont = 0
        
    
    # Método is Empty
    def isEmpty(self):
        res = False
        
        if self.cont == 0:
            res = True
        return res
    
    
    # Método size
    def size(self):
        return self.cont
    
    
    # Método find
    def findAux(self, actual, elem):
        
        if actual == None:
            return None
        
        if actual.getElem() == elem:
            return actual
        
        aux = self.findAux(actual.getIzq(), elem)
        if aux != None:
            return aux
        
        aux = self.findAux(actual.getDer(), elem)
        return aux
    
    def find(self, elem):
        return self.findAux(self.raiz, elem) != None
    
    
    # Método levels
    def levelsAux(self, actual):
        
        if actual == None:
            return 0
        izq = self.levelsAux(actual.getIzq())
        der = self.levelsAux(actual.getDer())
        val = max(izq, der)
        return val + 1
    
    def levels(self):
        return self.levelsAux(self.raiz)
    
    def levelsNode(self,nodo):
        return self.levelsAux(nodo)
    
    
    # Método lowest common ancestor
    def lowAux(self, actual, e1, e2):
        
        if actual == None:
            return None
        
        act = actual.getElem()
        if act > e1 and act > e2:
            return self.lowAux(actual.getIzq(), e1, e2)
        
        if act < e1 and act < e2:
            return self.lowAux(actual.getDer(), e1, e2)
        
        return actual
    
    def lowComAnc(self, e1, e2):
        
        ancestor = self.lowAux(self.raiz, e1, e2)
        anc = ancestor.getElem()
        if anc == e1 or anc == e2:
            if ancestor == self.raiz:
                return ancestor
            else:
                return ancestor.getPa()
        return ancestor
    
    
    def inserta(self, elem):
        actual = self.raiz
        pa = None
        nuevo = BinaryNode(elem)
        
        if self.raiz == None:
            self.raiz = nuevo
            self.cont += 1
            return
        
        while actual != None: 
            pa = actual
            if elem <= actual.getElem():
                actual = actual.getIzq()
            else:
                actual = actual.getDer();
        pa.cuelga(nuevo)
        self.cont += 1
        
        
    # Método de borrado de elementos
    def borra(self, elem):
        actual = self.findAux(self.raiz, elem)
        
        if actual == None:
            return None
        
        pa = actual.getPa()
        if actual.getIzq() == None and actual.getDer() == None:
            if actual.getPa() == None:
                self.raiz = None
            else:
                pa.eliminaSubArb(elem)
            self.cont -= 1
            return pa
        
        if actual.getIzq() == None or actual.getDer() == None:
            if actual == self.raiz:
                if actual.getizq() == None:
                    self.raiz = actual.getDer()
                else:
                    self.raiz = actual.getIzq()
                self.raiz.setPa(None)
            else:
                if actual.getIzq() != None:
                    pa.cuelga(actual.getIzq())
                else:
                    pa.cuelga(actual.getDer())
            self.cont -= 1
            return pa
        
        sucesor = actual.getDer()
        paSuc = sucesor.getPa()
        while sucesor.getIzq() != None:
            sucesor = sucesor.getIzq()
            paSuc = sucesor.getPa()
        actual.setElem(sucesor.getElem())
        if sucesor != actual.getDer():
            sucesor.getPa().setIzq(sucesor.getDer())
        else:
            sucesor.getPa().setDer(sucesor.getDer())
        if sucesor.getDer() != None:
            sucesor.getDer().setPa(sucesor.getPa())
        self.cont -= 1
        return paSuc
    
    
    # Método imprime árbol
    def imprimeAux(self,actual,lista):
        
        if actual == None:
            return
        self.imprimeAux(actual.getIzq(),lista)
        lista.append(actual.getElem())
        self.imprimeAux(actual.getDer(),lista)
        
        return lista
    
    def imprime(self):
        lista = []
        
        return self.imprimeAux(self.raiz,lista)


#%%
"""
    Creación y manejador de listas.
"""

# Método calcula y regresa el promedio de los valores de una lista.
def prom(lista):
    return sum(lista) / len(lista)
    

# Método que barajea el orden de los elementos de una lista.
def barajear(lista):
    random.shuffle(lista)
    

# Método que ordena a los elementos de una lista.
def ordenar(lista):
    lista.sort()
    

# Método que invierte el orden de los elementos de una lista.
def invertir(lista):
    ordenar(lista)
    lista.reverse()


# Método que acomoda la lista en su mejor caso.
def mitadesAux(lista,res):
    if len(lista) <= 1:
        return
    mit = len(lista) // 2
    res.append(lista[mit])
    izq = lista[:mit]
    mitadesAux(izq,res)
    der = lista[mit:]
    mitadesAux(der,res)
def mitades(lista):
    res = []
    ordenar(lista)
    
    mitadesAux(lista,res)
    res.append(lista[0])
    return res


# Método creación de una lista aleatoria de números con repetición.
# Regresa una lista.
def listaNum(n):
    lista = []
    
    for i in range(n):
        lista.append(i)
    barajear(lista)
    return lista
 
# Método que elige aleatoriamente dos elementos de una lista.
def selector(lista):
    
    v1 = random.choice(lista)
    v2 = random.choice(lista)
    while(v2 == v1):
        v2 = random.choice(lista)
    return v1,v2
    

#%%
"""
    Métodos de funciones fijas.
"""

def log(lista, n):
    res = []
    
    for i in range(0,n):
        val = lista[i]
        v = math.log2(val)
        res.append(v)
    return res


def root(lista, n):
    res = []
    
    for i in range(0,n):
        val = lista[i]
        v = math.sqrt(val)
        res.append(v)
    return res


def linear(lista,n):
    res = []
    
    for i in range(0,n):
        v = lista[i]
        res.append(v)
    return res


#%%
"""
    Métodos altura esperada TOTAL
"""

def averageCase(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        barajear(data)
        p = []
        for j in range(0,20):
            a = BinarySearchTree()
            copia = data.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            v = a.levels()
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


def worstCase1(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        ordenar(data)
        p = []
        for j in range(0,2):
            a = BinarySearchTree()
            copia = data.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            v = a.levels()
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


def worstCase2(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        invertir(data)
        p = []
        for j in range(0,2):
            a = BinarySearchTree()
            copia = data.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            v = a.levels()
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


def bestCase(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        best = mitades(data)
        p = []
        for j in range(0,20):
            a = BinarySearchTree()
            copia = best.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            v = a.levels()
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


#%%
"""
    Métodos altura esperada ANTECESOR
"""

def averageCaseAnt(lista,n):
    res = []
    av = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        barajear(data)
        p = []
        o = []
        tup = selector(data)
        for j in range(0,10):
            a = BinarySearchTree()
            copia = data.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            ant = a.lowComAnc(tup[0], tup[1])
            v = a.levelsNode(ant)
            n = a.levels()
            p.append(v)
            o.append(n)
        niveles = prom(p)
        res.append(niveles)
        niveles = prom(o)
        av.append(niveles)
    return res, av


def worstCase1Ant(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        ordenar(data)
        p = []
        tup = selector(data)
        for j in range(0,2):
            a = BinarySearchTree()
            copia = data.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            ant = a.lowComAnc(tup[0], tup[1])
            v = a.levelsNode(ant)
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


def worstCase2Ant(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        invertir(data)
        p = []
        tup = selector(data)
        for j in range(0,2):
            a = BinarySearchTree()
            copia = data.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            ant = a.lowComAnc(tup[0], tup[1])
            v = a.levelsNode(ant)
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


def bestCaseAnt(lista,n):
    res = []
    
    for i in range(0,n):
        ent = lista[i]
        data = listaNum(ent)
        best = mitades(data)
        p = []
        tup = selector(data)
        for j in range(0,10):
            a = BinarySearchTree()
            copia = best.copy()
            for k in range(0,ent):
                a.inserta(copia[k])
            ant = a.lowComAnc(tup[0], tup[1])
            v = a.levelsNode(ant)
            p.append(v)
        niveles = prom(p)
        res.append(niveles)
    return res


#%%
"""
    Entradas
"""

def entradasNormales():
    res = []
    
    i = 500
    while i <= 15000:
        res.append(i)
        i += 1000
    return res;


def entradasCompletas():
    res = []
    
    i = 1
    while (2**i) <= 32768: # 32768  16383
        res.append(2**i - 1)
        i += 1
    return res;


#%%
"""
    Gráficas Altura TOTAL.
"""

#entrada = [50,100,300,500,700,900,1100,1300,1500,1700,1900,2000,2200,2500,2800]
#entrada = entradasCompletas()
entrada = entradasNormales()
n = len(entrada)

print("Tamaños de entrada")
print(entrada)

x = entrada

#logB2 = log(entrada,n)
#lineal = linear(entrada,n)
average = averageCase(entrada,n)
#worst1 = worstCase1(entrada,n)
#worst2 = worstCase2(entrada,n)
best = bestCase(entrada,n)

#print("Logaritmo base 2")
#print(logB2)
#print("Lineal")
#print(lineal)
#print("Average Case")
#print(average)
#print("Worst Case 1")
#print(worst1)
#print("Worst Case 2")
#print(worst2)
#print("Best Case")
#print(best)

#pyplot.plot(x,logB2,'tab:red')
#pyplot.plot(x,lineal,'goldenrod')
pyplot.plot(x,average,'tab:orange')
#pyplot.plot(x,worst1,'tab:green')
#pyplot.plot(x,worst2,'tab:blue')
pyplot.plot(x,best,'tab:purple')

pyplot.title("Título")
pyplot.xlabel("Tamaño de entrada")
pyplot.ylabel("Altura esperada")
pyplot.legend(["Caso Promedio","Mejor Caso"])

pyplot.show()



#%%
"""
    Gráficas Altura Antecesor.
"""

#entrada = [50,100,300,500,700,900,1100,1300,1500,1700,1900,2000,2200,2500,2800]
entrada = entradasNormales()
n = len(entrada)

print("Tamaños de entrada")
print(entrada)

x = entrada

av = averageCaseAnt(entrada,n)

#logB2 = log(entrada,n)
#lineal = linear(entrada,n)
#average = av[0]
#average1 = av[1]
#worst1 = worstCase1Ant(entrada,n)
#worst2 = worstCase2Ant(entrada,n)
best = bestCase(entrada,n)

#print("Logaritmo base 2")
#print(logB2)
#print("Lineal")
#print(lineal)
#print("Average Case")
#print(average)
#print("Worst Case 1")
#print(worst1)
#print("Worst Case 2")
#print(worst2)
print("Best Case")
print(best)

#pyplot.plot(x,logB2,'tab:red')
#pyplot.plot(x,lineal,'goldenrod')
#pyplot.plot(x,average,'tab:orange')
#pyplot.plot(x,worst1,'tab:green')
#pyplot.plot(x,worst2,'tab:blue')
pyplot.plot(x,best,'tab:purple')

pyplot.title("C. Altura del antecesor común en el caso promedio")
pyplot.xlabel("Tamaño de entrada")
pyplot.ylabel("Altura esperada")
pyplot.legend(["Caso Promedio","Referencia","Mejor Caso"])

pyplot.show()


