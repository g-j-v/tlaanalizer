package grammarp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablaAnalizador {
	public static String ERROR = "ERROR";
	/*
	 * La tabla es un mapa de lista de mapas, que se constuye con los simbolos directrices de una gramatica.
	 * 
	 * El primer mapa tiene como clave los simbolos TERMINALES,
	 * 	El valor es un mapa que tiene como clave los simbolos NO TERMINALES
	 * 		El valor es la produccion correspondiente a ese par. 
	 */
	private Map<Character, List<Map<Character, Production>>> tabla;

	public TablaAnalizador( Map<Production, String> simbolosDirectrices ) 
	{
		tabla = new HashMap<Character, List<Map<Character, Production>>>();
		for( Production produccion : simbolosDirectrices.keySet() )	{
//			System.out.println("Procesando produccion: " + produccion);
			
			String simbolosTerminales = simbolosDirectrices.get(produccion);
			for( Character simboloTerminal : simbolosTerminales.toCharArray() )	{
				Map<Character, Production> mapaDelNoTerminal = new HashMap<Character, Production>();
				mapaDelNoTerminal.put(produccion.noterminal.charAt(0), produccion);
				
				List<Map<Character, Production>> listaDelTerminal = tabla.get(simboloTerminal);
				if( listaDelTerminal == null ) {
					listaDelTerminal = new ArrayList<Map<Character, Production>>();
					tabla.put(simboloTerminal, listaDelTerminal);
				}
				listaDelTerminal.add(mapaDelNoTerminal);
			}
		}
	}

	public Production getProduccionTabla( Character noTerminal, Character terminal )
	{
		if( !tabla.containsKey(noTerminal) )
			return null;
		List<Map<Character, Production>> listaDelTerminal = tabla.get(noTerminal);
		for( Map<Character, Production> mapaDelNoTerminal : listaDelTerminal )	{
			if( mapaDelNoTerminal.containsKey(terminal) )
				return mapaDelNoTerminal.get(terminal);
		}
		return null;
	}
}
