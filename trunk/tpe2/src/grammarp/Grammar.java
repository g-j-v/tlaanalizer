package grammarp;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Grammar {
	Set<Character> terminalSimbols;
	Set<Character> noTerminalSymbols;
	Set<Production> producctions; // cambiar a algo de objeto production;
	String initSymbol = "S";
	Map<String, String> primeros;
	Map<String, String> siguientes;
	Map<String, Production> simbolosDirectrices;
	private static char lambda = '@';
	private static char EOF = '$';

	public Grammar() {
		this.terminalSimbols = new HashSet<Character>();
		this.noTerminalSymbols = new HashSet<Character>();
		this.producctions = new HashSet<Production>();
		this.primeros = new HashMap<String, String>();
		this.siguientes = new HashMap<String, String>();
		this.simbolosDirectrices = new HashMap<String, Production>();
	}

	public Map<String, Production> getSimbolosDirectrices() {
		return simbolosDirectrices;
	}

	public void setSimbolosDirectrices(Map<String, Production> simbolosDirectrices) {
		this.simbolosDirectrices = simbolosDirectrices;
	}

	private boolean addTerminalSimbol(char terString) {
		return this.terminalSimbols.add(terString);
	}

	private boolean addNoTerminalSimbol(char terString) {
		return this.noTerminalSymbols.add(terString);
	}

	private boolean parseRightPart(String rightPart) throws ParseException {
		boolean flag = true;
		if (rightPart.equals("")) {
			return addTerminalSimbol(lambda);
		}
		char c = rightPart.charAt(0);
		// if(!Character.isLetter(c) || Character.isUpperCase(c)){ //descomentar
		// para no bonus
		// throw new
		// ParseException("La parte derecha de todas las producciones debe comenzar con un simbolo Terminal (a menos que la produccion sea Lambda)",
		// 0);
		// }else
		if (Character.isLowerCase(c)) {
			flag &= addTerminalSimbol(c);
		}
		for (int i = 1; i < rightPart.length(); i++) {
			c = rightPart.charAt(i);
			if (!Character.isLetter(c)) {
				throw new ParseException("la produccion no contiene una letra",
						i);
			} else if (Character.isLowerCase(c)) {
				flag &= addTerminalSimbol(c);
			} else if (Character.isUpperCase(c)) {
				flag &= addNoTerminalSimbol(c);
			}
		}
		return flag;
	}

	public boolean addProduction(String noTerminal, String rightPart)
			throws ParseException {
		parseRightPart(rightPart);
		if (rightPart.equals("")) {
			return this.producctions.add(new Production(noTerminal, Character
					.toString(lambda)));
		}
		if (noTerminal.length() != 1) {
			throw new ParseException(
					"La parte izquierda de las producciones es un unico si�mbolo No Terminal",
					0);
		}
		addNoTerminalSimbol(noTerminal.charAt(0));
		return this.producctions.add(new Production(noTerminal, rightPart));
	}

	private Set<Production> obtenerProduccionesDe(String noTerminal) {
		HashSet<Production> resp = new HashSet<Production>();
		for (Production p : producctions) {
			if (p.noterminal.equals(noTerminal)) {
				resp.add(p);
			}
		}
		return resp;
	}

	// primeros De tal no terminal
	private String primerosDe(String noTerminal) {
		String primeros = "";
		for (Production p : obtenerProduccionesDe(noTerminal)) {
			if (terminalSimbols.contains(p.rightpart.charAt(0))) {// es terminal
				if (!primeros.contains(Character
						.toString(p.rightpart.charAt(0)))) {
					primeros += Character.toString(p.rightpart.charAt(0));
				}
			} else {// es no terminal
				String aux;
				aux = primerosDe(Character.toString(p.rightpart.charAt(0)));
				for (Character c : aux.toCharArray()) {
					if (!primeros.contains(Character.toString(c))) {
						primeros += c;
					}
				}
			}
		}
		return primeros;
	}

	public String removeLambda(String sequence) {
		return sequence.replaceAll("@", "");
	}

	public boolean isTerminal(Character c) {
		return terminalSimbols.contains(c);
	}

	/**
	 * Si este no terminal es anulable
	 * 
	 * @param noTerminal
	 * @return
	 */

	private boolean isAnulable(String noTerminal) {
		for (Production p : obtenerProduccionesDe(noTerminal)) {
			if (p.rightpart.contains(Character.toString(lambda))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este es si es anulable la parte sentencial derecha dese el no terminal
	 * 
	 * @param formaSentencialDerecha
	 * @param noTerminal
	 * @return Si es anulable o no
	 */
	private boolean isAnulableLaFormaSentencialDerecha(
			String formaSentencialDerecha, String noTerminal) {
		for (int i = 0; i < formaSentencialDerecha.length(); i++) {
			if (formaSentencialDerecha.charAt(i) == noTerminal.charAt(0)) {
				if (terminalSimbols.contains(formaSentencialDerecha
						.charAt(i + 1))) {
					return false;
				} else {
					return isAnulable(Character.toString(formaSentencialDerecha
							.charAt(i + 1)));
				}
			}
		}
		return false;
	}

	/**
	 * Todo es anulable??
	 * 
	 * @param formaSentencialDerecha
	 * @param noTerminal
	 * @return
	 */
	private boolean isAnulableEsto(String formaSentencialDerecha) {

		if (isTerminal(formaSentencialDerecha.charAt(0))) {
			return formaSentencialDerecha.charAt(0) == lambda;
		} 
		return false;
	}

	private boolean isUltimo(String formaSentencialDerecha, String noTerminal) {
		return formaSentencialDerecha
				.charAt(formaSentencialDerecha.length() - 1) == (noTerminal
				.charAt(0));
	}

	private String obtenerLaFormaSentencialDerechaDesde(
			String formaSentencialDerecha, String noTerminal) {
		Integer pos;
		if ((pos = formaSentencialDerecha.indexOf(noTerminal)) != -1) {
			if (!isUltimo(formaSentencialDerecha, noTerminal)) {
				return formaSentencialDerecha.substring(pos + 1);
			}
		}
		return "";
	}
	

	private String siguientesDe(String noTerminal) {
		String siguientes = "";
//		System.out.println("Obteniendo Siguientes de : " + noTerminal);
		for (Production p : producctions) {
			if (p.rightpart.contains(noTerminal)) {
				if (!(isUltimo(p.rightpart, noTerminal))) { // Si no es ultimo
					// en la produccion
					String aux = obtenerLaFormaSentencialDerechaDesde(
							p.rightpart, noTerminal);
					if (!isAnulableLaFormaSentencialDerecha(p.rightpart,
							noTerminal)) {
						if (isTerminal(aux.charAt(0))) {// no anulable y no
							// terminal
							if (!siguientes.contains(Character.toString(aux
									.charAt(0)))) {
								siguientes += aux.charAt(0);
							}
						} else { // no es terminal y no es anulable
							String primeros = "";
							primeros = primerosDe(Character.toString(aux
									.charAt(0)));
							String aux2 = removeLambda(primeros);
							for (Character c : aux2.toCharArray()) {
								if (!siguientes.contains(Character.toString(aux
										.charAt(0)))) {
									siguientes += c;
								}
							}
						}
					} else {
						// el NT que tengo es anulable
						// son los primeros del nt que me llego y los sig de la
						// parte iza
						String primeros = "";
						primeros = primerosDe(Character.toString(aux.charAt(0)));
						String aux2 = removeLambda(primeros);
						for (Character c : aux2.toCharArray()) {
							if (!siguientes.contains(Character.toString(c))) {
								siguientes += c;
							}
						}
						String siguientesDeLaIzq = siguientesDe(p.noterminal);
						for (Character c : siguientesDeLaIzq.toCharArray()) {
							if (!siguientes.contains(Character.toString(c))) {
								siguientes += c;
							}
						}
					}
				} else {
					if (isUltimo(p.rightpart, noTerminal)) {
						// ahora es que el noterminal este al ultimo de una
						// produccion
						if (!p.noterminal.equals(noTerminal)) {
							// para evitar la recursion infinita F->AF
							String siguientesDelNoTerminal = siguientesDe(p.noterminal);
							if (!siguientesDelNoTerminal.contains(noTerminal)) {
								for (Character c : siguientesDelNoTerminal
										.toCharArray()) {
									if (!siguientes.contains(Character
											.toString(c))) {
										siguientes += c;
									}
								}
							}
						}
					}
				}
			}
		}
		if (noTerminal.equals(initSymbol)) {
			siguientes += EOF;
		}
		return siguientes;
	}

	public void calcularSiguientes() {
		for (Character nt : noTerminalSymbols) {
			siguientes.put(Character.toString(nt), siguientesDe(Character
					.toString(nt)));
		}
	}

	public void calcularPrimeros() {
		for (Character nt : noTerminalSymbols) {
			//System.out.println(nt);
			primeros.put(Character.toString(nt), primerosDe(Character
					.toString(nt)));
		}
	}

	public void calcularSimbolosDirectrices() {
		for (Production production : producctions) {
			if (!isAnulableEsto(production.rightpart)) {
				if (isTerminal(production.rightpart.charAt(0))) {
					simbolosDirectrices.put(Character.toString(production.rightpart
							.charAt(0)), production);
				} else {
					simbolosDirectrices.put(primerosDe(Character
							.toString(production.rightpart.charAt(0))), production);
				}
			} else {
				String sd = "";
				sd += primerosDe(Character.toString(production.rightpart.charAt(0)));
				sd += siguientesDe(production.noterminal);
				simbolosDirectrices.put(sd, production);

			}
		}
	}
	
	public boolean validarPalabra(String palabra) {
		/* Valido que los caracteres sean simbolos terminales de la gramatica */
		for( Character c : palabra.toCharArray() )
			if( !terminalSimbols.contains(c) )
				return false;
		
		palabra = palabra.concat("#");
		Stack<Character> pila = new Stack<Character>();
		pila.push('#');
		pila.push('S');
		
		TablaAnalizador tablaAnalizador = new TablaAnalizador(simbolosDirectrices); 
		Integer i = 0;
		while( true )
		{
			Character tope = pila.peek();
			Character t = palabra.charAt(i);
			
			if( tope == '#' && t == '#' )
				return true;
			
			if( terminalSimbols.contains(tope) )	{
				if( tope.equals(t) )	{
					pila.pop();
					i++;
				}
				else 
					return false;
			}
			else {
				Production produccion = tablaAnalizador.getProduccionTabla(t, tope);
				if( produccion == null )
					return false;
				if( !produccion.noterminal.equals(Character.toString(tope)) )
					return false;
				pila.pop();
				for( Integer j = produccion.rightpart.length() - 1; j >= 0; j-- )
					pila.push(produccion.rightpart.charAt(j));
			}
		}
	}
}
