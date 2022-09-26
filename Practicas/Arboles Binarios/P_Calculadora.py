#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Mar 14 12:36:55 2021

@author: Murillo
"""

"""
------ CLASES NECESARIAS PARA ÁRBOLES BINARIOS -------------------------------
"""

# Clase de Nodo Binario
class BinaryNode:
    
    # Constructor con:
    #   Elem - elemento contenido en el nodo
    #   Hijos izquierdo y derecho - nulos
    def __init__(self, elem):
        self.elem = elem
        self.izq = None
        self.der = None
        
    # Getters necesarios
    def getElem(self):  # Elemento
        return self.elem
    def getIzq(self):   # Hijo izquierdo
        return self.izq
    def getDer(self):   # Hijo derecho
        return self.der
    
    # Setters necesarios
    def setElem(self, e):   # Elemento
        self.elem = e
    def setIzq(self, izq):  # Hijo izquierdo
        self.izq = izq
    def setDer(self, der):  # Hijo derecho
        self.der = der


# Clase de Árbol Binario
class BinaryTree:
    
    # Constructor con:
    #   Elem - elemento contenido en la raíz
    def __init__(self,elem):
        nuevo = BinaryNode(elem)
        self.raiz = nuevo
    
    
    # Getters necesarios
    def getRaiz(self):  # Raíz
        return self.raiz
    def getElem(self):  # Elemento de la raíz
        return self.raiz.getElem()
    def getIzq(self):   # Subárbol izquierdo
        return self.raiz.getIzq()
    def getDer(self):   # Subárbol derecho
        return self.raiz.getDer()
    
    
    # Método cuelga
    #   Dados dos subárboles dados como parámetros, los cuelga
    #   como hijo izquierdo e hijo derecho del nodo raíz.
    def cuelga(self,p,s):
        actual = self.raiz
        actual.setIzq(s)
        actual.setDer(p)
    
    
    # Método imprime árbol
    # Es un método auxiliar recursivo para verificar el árbol de 
    # expresión final para el posterior método de evaluación.
    #   Imprime el árbol haciendo un recorrido in-order. La impresión
    #   se hace por medio de la creación de una lista y la inserción
    #   de los elementos de los nodos en los cuales nos encontramos.
    #   Regresa una lista con los elementos ordenados in-order.
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
------ CLASES NECESARIAS PARA PILAS --------------------------------------------
"""

# Clase EmptyCollectionException
class EmptyCollectionException(RuntimeError):
    def __init__(self,arg):
        self.args = arg


# Clase ListStack
class ListStack:
    
    # Constructor con:
    #   Data - lista inicialmente vacía
    def __init__(self):
        self.data = []
        
    
    # Método Is Empty?
    #   Regresa True si la pila está vacía y False si no lo está.
    def isEmpty(self):
        return len(self.data) == 0
    
    
    # Método Push
    #   Agrega el elemento dado como parámetro en el tope de la pila.
    def push(self, elem):
        self.data.append(elem)
    
    
    # Método Pop
    #   Elimina el elemento que se encuentra en el tope de la pila y regresa
    #   una referencia del mismo.
    def pop(self):
        if(not self.isEmpty()):
            res = self.data.pop()
            return res
        else:
            raise EmptyCollectionException("¡Pila vacía!")
    
    
    # Método Peek
    #   Regresa una referencia del elemento que se encuentra 
    #   en el tope de la pila.
    def peek(self):
        if(not self.isEmpty()):
            res = self.data[len(self.data)-1]
            return res
        else:
            raise EmptyCollectionException("¡Pila vacía!")


#%%      
"""
------ MÉTODOS SOBRE REVISORES -----------------------------------------------
"""

# Método Is Float?
#   Revisa si un dato dado como parámetro es del tipo float.
def isFloat(value):
  try:
    float(value)
    return True
  except:
    return False


# Método Casos
#   Verifica si se cumple o no la restricción del sucesor de
#   un token dado como parámetro. Si el sucesor del token está en 
#   la lista correspondiente de sucesores prohibidos, regresa False.
def casos(val, sig):
    res = True
    # Listas de tokens sucesores prohibidos de...
    # Paréntesis Izquierdo.
    parA = [')','*','/','^']
    # Paréntesis Derecho o una Variable
    parC_alpha = ['(','.']
    # Operador
    operador = [')','+','-','*','/','^','//']
    
    # Si el token es...
    # Paréntesis izquierdo
    if val == '(':
        if sig in parA:
            res = False
            
    # Paréntesis cerrado o es una letra
    elif val == ')' or val.isalpha():
        if sig in parC_alpha or isFloat(sig) or sig.isalpha():
            res = False
            
    # Operador
    elif val in ['+','-','*','/','^','//']:
        if sig in operador:
            res = False
            
    # Número
    elif isFloat(val):
        # Como sucesores prohibidos se tiene a un paréntesis izquierdo
        # o una variable.
        if sig == '(' or sig.isalpha():
            res = False
            
    # Cualquier otra cosa
    else:
        res = False
    return res
    

# Método Revisor Paréntesis.
#   Verifica que la cadena tenga los paréntesis balanceados.
#   Se apoya de una pila para la verificación: mientras que la
#   cadena no se encuentre vacía, si el token es un paréntesis 
#   izquierdo, hace un push del token a la pila. 
#   Ahora bien, si el token es un paréntesis derecho, la pila 
#   no está vacía y el tope de la pila es un paréntesis izquierdo,
#   se hace un pop de la pila.
#   Entonces se verifica por medio de la correspondencia entre pares
#   de parénteis.
def revisorPar(tokens):
    pila = ListStack()
    res = True
    
    i = 0
    while i < len(tokens) and res:
        c = tokens[i]
        if c == '(':
            pila.push(c)
        elif c == ')':
            if not pila.isEmpty() and pila.peek() == '(':
                pila.pop()
            else:
                res = False
        i += 1   
        
    # Si al terminar el recorrido la pila no está vacía, quiere decir
    # que faltó un paréntesis derecho y que la expresión no está
    # balanceada.
    if not pila.isEmpty():
        res = False
    return res


# Método Revisor general.
#   Revisa de manera general la cadena de entrada. Ocupa del método
#   Revisor Paréntesis y del Método Casos para la verificación. También
#   toma en cuenta algunos casos especiales.
def revisor(tokens):
    # Listas de tokens prohibidos para el inicio y el final de la cadena
    inicio = [')','*','/','//','^']
    final = ['(','+','-','*','/','//','^']
    # Llama al método Revisor Paréntesis
    res = revisorPar(tokens)
    
    first = tokens[0]
    last = tokens[len(tokens)-1]
    # Revisa que el primer y el último token no se encuentren en la
    # lista de sucesores prohibidos.
    if (first in inicio) or (last in final) or (' ' in tokens):
        res = False
    
    # Hace una revisión general de los sucesores de los tokens por
    # medio del llamado al método Casos. Se detiene en el penúltimo
    # token de la cadena.
    i = 0
    while i < len(tokens)-1 and res:
        c = tokens[i]
        res = casos(c,tokens[i+1])
        i += 1   
        
    if last != ')' and not (isFloat(last) or last.isalpha()):
        res = False
    return res


# Método Tokenizar
#   Convierte en tokens una cadena de caracteres dada como parámetro.
#   Revisa caracter por caracter para determinar la asignación de 
#   tokens.
#   Para la creación de los tokens se trabaja tanto con caracteres "solos"
#   como con rebanadas de la cadena. Para ello ocupa dos contadores: uno
#   para recorrer la cadena y otro para utilizar las rebanadas.
def tokenizar(cadena):
    n = len(cadena)
    tokens = []
    
    # Considera un caso especial: que empiece con un signo de 
    # adición o de sustracción. Solamente agrega un cero antes 
    # de dicho signo.
    if cadena[0] in ['+','-']:
        tokens.append('0')
        
    i = 0        
    while i < n:
        c = cadena[i]
        token = c
        # Si el caracter es un dígito o un punto:
        if c.isdigit() or c == '.':
            j = i + 1
            # Si no se ha acabado la cadena y el siguiente caracter es
            # un número o un punto, aumenta el segundo contador para
            # tener la rebanada deseada: un número completo positivo.
            while j < n and (cadena[j].isdigit() or cadena[j] == '.'):
                j += 1
        # Si el caracter es una letra
        elif c.isalpha():
            j = i + 1
            # Si no se ha acabado la cadena y el siguiente caracter es
            # una letra, aumenta el segundo contador para tener la
            # rebanada deseada: una palabra completa.
            while j < n and cadena[j].isalpha():
                j += 1
        # Si el caracter es un slash
        elif c == '/':
            j = i + 1
            # Si el siguiente caracter es otro slash, aumenta el segundo
            # contador para tener la rebanada deseada: el signo de división
            # entera
            if cadena[j] == '/':
                j += 1
        # Si el caracter es un signo de sustracción y su anterior
        # es un paréntesis izquierdo
        elif c == '-' and cadena[i-1] == '(':
            tokens.append('0')
            tokens.append(token)
            i += 1
            # Si no se ha acabado la cadena y el siguiente caracter es
            # un número o un punto, aumenta el segundo contador para
            # tener la rebanada deseada: un número completo negativo.
            if cadena[i].isdigit():
                j = i + 1
                while j < n and (cadena[j].isdigit() or cadena[j] == '.'):
                    j += 1
            # Si no se ha acabado la cadena y el siguiente caracter es
            # una letra, aumenta el segundo contador para tener la
            # rebanada deseada: una palabra completa negativa.
            else:
                j = i + 1
                while j < n and cadena[j].isalpha():
                    j += 1
        # Si es cualquier caracter, no maneja rebanada.
        else:
            j = i+1
        # Tokeniza el caracter o la rebanada indicada
        token = cadena[i:j]
        tokens.append(token)
        i = j-1
        i += 1
    return tokens

          
#%%      
"""
------ MÉTODOS PROPIOS DE CALCULADORA ----------------------------------------
"""

# Clase InvalidExpression
class InvalidExpression(RuntimeError):
    def __init__(self,arg):
        self.args = arg

# Método prioridad
#   Devuelve el grado de prioridad que tiene un operador dado como parámetro.
def prioridad(c):
    res = 1     # Se asume de entrada que el operador es + o -
    
    if c in ['*','/','//']:
        res = 2
    elif c == '^':
        res = 3
    return res


# Método Expression TRee
#   Crea  y regresa el árbol de expresión que representa a la 
#   expresión dada como parámetro.
def expressionTree(tokens):
    pilaA = ListStack()
    pilaB = ListStack()
    
    # Recorre todos los tokens
    for i in range(0,len(tokens)):
        t = tokens[i]
        
        # Si el token es...
        # Un operando
        if isFloat(t) or t.isalpha():
            a = BinaryTree(t)
            pilaB.push(a)
            
        # Un paréntesis izquierdo
        elif t == '(':
            pilaA.push(t)
            
        # Un paréntesis derecho
        elif t == ')':
            while(pilaA.peek() != '('):
                op = pilaA.pop()
                p = pilaB.pop()
                s = pilaB.pop()
                a = BinaryTree(op)
                a.cuelga(p, s)
                pilaB.push(a)
            pilaA.pop()
        
        # Un operador
        else:
            while not (pilaA.isEmpty() or pilaA.peek() == '(' or prioridad(pilaA.peek()) < prioridad(t)):
                op = pilaA.pop()
                p = pilaB.pop()
                s = pilaB.pop()
                a = BinaryTree(op)
                a.cuelga(p, s)
                pilaB.push(a)
            pilaA.push(t)
        
    # Si al terminar el recorrido de los tokens, la pila A no está vacía
    if not pilaA.isEmpty():
        while not pilaA.isEmpty():
            op = pilaA.pop()
            p = pilaB.pop()
            s = pilaB.pop()
            a = BinaryTree(op)
            a.cuelga(p, s)
            pilaB.push(a)
            
    return pilaB.pop()


# Método Evaluar
#   Evalúa de manera recursiva un árbol de expresión en su totalidad 
#   por medio de un recorrido post-order.
def evAux(actual):
    
    # Si llegamos a algún hijo de un nodo hoja.
    if actual is None:
        return 0
    
    # Se obtiene el elemento del nodo en el que nos encontramos.
    e = actual.getElem()
    
    # Si el elemento del nodo es...
    # Un número
    if isFloat(e):
        return float(e)
    # Una variable
    elif e.isalpha():
        # Llama al método de Entrada Variable para validar y evaluar la entrada.
        e = entVar(e)
        return float(e)
    # Un operador
    else:
        # Realiza propiamente el recorrido post-order.
        i = evAux(actual.getIzq())
        d = evAux(actual.getDer())
        # Realiza la operación
        if e == '+':
            return i + d
        elif e == '-':
            return i - d
        elif e == '*':
            return i * d
        elif e == '/':
            return i / d
        elif e == '//':
            return i // d
        else:
            return i ** d
        
def evaluar(arbol):
    res = evAux(arbol.getRaiz())
    return res
 
   
# Método Entrada Variable
#   Revisa la entrada de la variable, la evalúa y regresa el valor
#   correspondiente.
def entVar(e):
    
    # Manda un mensaje a la consola para incitar al usuaro a 
    # asignar un valor a la variable que este puso en la entrada
    # original
    print("")
    print("¿Qué valor desea asignar para la variable { "+e+" } ?")
    # Se lee el valor de entrada dada por el usuario a la variable
    alpha = input()
    # Se tokeniza la entrada de la variable
    tokens = tokenizar(alpha)
    # Se revisa la cadena de entrada
    check = revisor(tokens)
    # Si la entrada es válida
    if check:
        # Si la entrada representa solo un token regresa el valor
        # float de ese token
        if len(tokens) == 1:
            e = tokens[0]
            return float(e)
        # Si la entrada es mayor a un token, crea un árbol de 
        # expresión que representa a la entrada de la variable
        # y evalúa este nuevo árbol para regresar el valor
        # obtenido solamente de esa variable y continuar con la 
        # evaluación del árbol general
        else:
            arbol = expressionTree(tokens)
            e = evaluar(arbol)
            return float(e)
    # Si la entrada no es válida
    else:
        # Termina ejecución y manda mensaje de error.
        raise InvalidExpression("Entrada de variable INVÁLIDA")


#%%
"""
--------- MÉTODO GENERAL -----------------------------------------------------
"""

# Método Calcular
def calcular(cadena):
    
    if cadena != '':
        # Se tokeniza la cadena de entrada
        tokens = tokenizar(cadena)
        # Se revisa la cadena de entrada
        check = revisor(tokens)
        # Si la cadena de entrada es válida
        if check:
            # Crea al árbol que representa a la expresión
            arbol = expressionTree(tokens)
            # Evalúa el árbol de expresión
            res = evaluar(arbol)
            return res
        else:
            raise InvalidExpression("La expresión de entrada NO ES VÁLIDA")
    else:
        raise InvalidExpression("¡ERROR! Entrada vacía, coloque una expresión")



#%%
"""
-------- PRUEBAS IMPORTANTES -------------------------------------------------
"""

print("")
print("Coloque la expresión a calcular: ")
cadena = input()
res = calcular(cadena)
print("")
print("Resultado: ")
print(res)




