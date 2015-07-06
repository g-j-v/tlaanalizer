trabajo práctico de tla
1.	Ob jetivo
Trabajo Práctico Especial
Parser de Gram ́aticas LL(1)
El trabajo consiste en generar un programa que dada una gramática LL(1), genere un parser para dicha gramática que al recibir como input una cadena decida si pertenece o no al lenguaje generado por la gram ́atica. El trabajo se realizar ́a en los mismos grupos que el trabajo pr ́actico nu ́mero 1 y en el lenguaje de programaci ́on Java.
2.	Descripción del funcionamiento esperado
El programa debe leer el archivo (donde se especifica la gram ́atica) y quedar a la espera de una entrada por la entrada estandar. Cada l ́ınea ingresada se considera una palabra y el programa debe imprimir en la salida estandar ”true” si la palabra pertenece al lenguaje de la gram ́atica o ”false” en caso contrario.
La gram ́atica del archivo grammar.txt debe cumplir los siguientes requisitos:
1. Ser una gram ́atica LL(1).
2. La parte derecha de todas las producciones debe comenzar con un s ́ımbolo Terminal (a menos que la producci ́on sea Lambda)
3. No habr ́a s ́ımbolos Lambda dentro de una producci ́on (a menos que sea el u ́nico s ́ımbolo de la producci ́on). 4. La parte izquierda de las producciones es un u ́nico s ́ımbolo No Terminal
3.
Formato de la gram ́atica
La gram ́atica en grammar.txt debe tener una producci ́on por l ́ınea de la siguiente forma. Primero un s ́ımbolo No Terminal, luego un espacio y, finalmente, una seguidilla de s ́ımbolos Terminales y No Terminales. En caso de requerir una producci ́on lambda entonces debe aparecer en una l ́ınea que tiene u ́nicamente al s ́ımbolo No Terminal.