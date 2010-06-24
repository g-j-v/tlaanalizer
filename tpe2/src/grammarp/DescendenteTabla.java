package grammarp;

import java.util.HashMap;
import java.util.Map;

public class DescendenteTabla {
	private static String ERROR = "ERROR";
	private Grammar g;
	private Map<Character, Map<Character, Producction>> tabla;

	public DescendenteTabla(Grammar g) {
		tabla = new HashMap<Character, Map<Character, Producction>>(
				g.noTerminalSymbols.size());
		for (Character noTerminal : g.noTerminalSymbols) {
			Map<Character, Producction> partedeterminales = tabla.put(
					noTerminal, new HashMap<Character, Producction>());
			for (Character terminal : g.terminalSimbols) { //agregarle $
				//para todas las producciones si 
			}
		}
	}

	public void llenarTabla() {
		// mapeo los terminales
	}

	public static void main(String[] args) {

	}
}
