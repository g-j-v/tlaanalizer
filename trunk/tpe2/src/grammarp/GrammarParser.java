package grammarp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class GrammarParser {
	
	public static String filename = "error";
	
	
	public Grammar parse(String fileName) throws IOException, ParseException{
		Grammar grammar = new Grammar();
		BufferedReader reader= new BufferedReader(new FileReader(fileName));
		String line;
		int cant = 0;
		while((line = reader.readLine()) != null){
			String[] tokens = line.split(" ");
			if(tokens.length > 2 || tokens.length < 1){
				throw new ParseException("Esta mal la gramatica", cant);
			}else if(tokens.length == 1){//lambda production
				grammar.addProduction(tokens[0], "");
			}else {
				grammar.addProduction(tokens[0], tokens[1]);
			}
		}
		return grammar;
	}
	
	public static void main(String[] args) {
		GrammarParser gp = new GrammarParser();
		Grammar g = null;
		try {
			g = gp.parse(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("NT: " + g.noTerminalSymbols);
		System.out.println("T: " + g.terminalSimbols);
		for(Producction p: g.producctions){
			System.out.println(p.noterminal + "->" + p.rightpart);
		}
		g.calcularPrimeros();
		System.out.println(g.primeros);
		g.calcularSiguientes();
		System.out.println(g.siguientes);
		g.calcularSimbolosDirectrices();
		System.out.println(g.simbolosDirectrices);
	}
}
