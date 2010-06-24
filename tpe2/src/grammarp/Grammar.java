package grammarp;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class Grammar {
	Set<Character> terminalSimbols;
	Set<Character> noTerminalSymbols;
	Set<Producction> producctions; // cambiar a algo de objeto production;
	private static char lambda = '@';

	public Grammar() {
		this.terminalSimbols = new HashSet<Character>();
		this.noTerminalSymbols = new HashSet<Character>();
		this.producctions = new HashSet<Producction>();
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
			return this.producctions.add(new Producction(noTerminal, "@"));
		}
		if (noTerminal.length() != 1) {
			throw new ParseException(
					"La parte izquierda de las producciones es un unico siõmbolo No Terminal",
					0);
		}
		addNoTerminalSimbol(noTerminal.charAt(0));
		return this.producctions.add(new Producction(noTerminal, rightPart));
	}

}
