package grammarp;

import java.util.HashMap;
import java.util.Map;

public class TablaAnalizador {
	public static String ERROR = "ERROR";
	/*
	 * La tabla es un mapa de mapas, que se constuye con los simbolos directrices de una gramatica.
	 * 
	 * El primer mapa tiene como clave los simbolos TERMINALES,
	 * 	El valor es un mapa que tiene como clave los simbolos NO TERMINALES
	 * 		El valor es la produccion correspondiente a ese par. 
	 */
	private Map<Character, Map<Character, Production>> tabla;

	public TablaAnalizador( Map<String, Production> simbolosDirectrices ) 
	{
		tabla = new HashMap<Character, Map<Character, Production>>();
		for( String simbolosTerminales : simbolosDirectrices.keySet() )	{
			for( Character simboloTerminal : simbolosTerminales.toCharArray() )	{
				Map<Character, Production> mapaDelNoTerminal = new HashMap<Character, Production>();
				mapaDelNoTerminal.put(simbolosDirectrices.get(simbolosTerminales).noterminal.charAt(0), simbolosDirectrices.get(simbolosTerminales));
				tabla.put(simboloTerminal, mapaDelNoTerminal);
			}
		}
	}

	public Production getProduccionTabla( Character noTerminal, Character terminal )
	{
		if( !tabla.containsKey(noTerminal) )
			return null;
		Map<Character, Production> mapaDelNoTerminal = tabla.get(noTerminal);
		if( !mapaDelNoTerminal.containsKey(terminal) )
			return null;
		return mapaDelNoTerminal.get(terminal);
	}
}
