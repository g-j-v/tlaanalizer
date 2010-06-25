package grammarp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TablaAnalizador {
	public static String ERROR = "ERROR";
	/*
	 * La tabla es un mapa de lista de mapas, que se constuye con los simbolos directrices de una gramatica.
	 * 
	 * El primer mapa tiene como clave los simbolos TERMINALES,
	 * 	El valor es un mapa que tiene como clave los simbolos NO TERMINALES
	 * 		El valor es la produccion correspondiente a ese par. 
	 */
	private Map<Character, Map<Character, Production>> tabla;

	public TablaAnalizador( Grammar gramatica, Map<Production, String> simbolosDirectrices ) 
	{
		Set<Character> terminales = gramatica.terminalSimbols;
		terminales.add(Grammar.EOF);
		
		tabla = new HashMap<Character, Map<Character, Production>>();
		for( Character terminal: terminales )	{
//			System.out.println("Procesando produccion: " + produccion);
			//para todos los terminales, busco su prodcuccion corresp en los simbolos directrices
			for(Production p: simbolosDirectrices.keySet()){
				if(simbolosDirectrices.get(p).contains(Character.toString(terminal))){
					Map<Character, Production> mapadelnoterminal = tabla.get(terminal);
					if(mapadelnoterminal==null){
						mapadelnoterminal = new HashMap<Character, Production>();
					}
					mapadelnoterminal.put(p.noterminal.charAt(0), p);
					tabla.put(terminal, mapadelnoterminal);
				}
			}
		/*String simbolosTerminales = simbolosDirectrices.get(produccion);
			for( Character simboloTerminal : simbolosTerminales.toCharArray() )	{
				Map<Character, Production> mapaDelNoTerminal = new HashMap<Character, Production>();
				mapaDelNoTerminal.put(produccion.noterminal.charAt(0), produccion);
				
				List<Map<Character, Production>> listaDelTerminal = tabla.get(simboloTerminal);
				if( listaDelTerminal == null ) {
					listaDelTerminal = new ArrayList<Map<Character, Production>>();
					tabla.put(simboloTerminal, listaDelTerminal);
				}
				listaDelTerminal.add(mapaDelNoTerminal);
			}*/
		}
	}
	public void printTable(){
		for(Character c: tabla.keySet()){
			System.out.println(c );
			System.out.println(tabla.get(c));
		}
	}

	public Production getProduccionTabla( Character terminal, Character noTerminal )
	{
		if(tabla.get(terminal) == null){
			return null;
		}else{
			return tabla.get(terminal).get(noTerminal);
		}
		/*
		if( !tabla.containsKey(noTerminal) )
			return null;
		List<Map<Character, Production>> listaDelTerminal = tabla.get(noTerminal);
		for( Map<Character, Production> mapaDelNoTerminal : listaDelTerminal )	{
			if( mapaDelNoTerminal.containsKey(terminal) )
				return mapaDelNoTerminal.get(terminal);
		}
		return null;*/
	}
}
