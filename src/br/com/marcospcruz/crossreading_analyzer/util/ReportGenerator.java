package br.com.marcospcruz.crossreading_analyzer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

	public static final String END_OF_LINE = System
			.getProperty("line.separator");

	public static final String LINHA_SEPARADORA = "************************************************************************";

	private StringBuffer relatorio;

	public ReportGenerator(String title) {
		relatorio = new StringBuffer();
		separaSecao(2);
		pulaLinha();
		escreveLinha("\t\t\t\t\t\t\t\t\t\t\t\t" + title);
		pulaLinha();
		separaSecao(2);
	}

	public ReportGenerator() {

	}

	/**
	 * 
	 * @param string
	 */
	public void escreveLinha(String string) {
		// TODO Auto-generated method stub

		relatorio.append(string);
		relatorio.append(END_OF_LINE);

	}

	/**
	 * x
	 */
	public void pulaLinha() {

		relatorio.append(END_OF_LINE);

	}

	/**
	 * 
	 * @param repeticao
	 */
	public void separaSecao(int repeticao) {

		for (int i = 0; i < repeticao; i++)

			relatorio.append(LINHA_SEPARADORA);

		pulaLinha();

	}

	/**
	 * xxx
	 */
	public void separaSecao() {

		separaSecao(1);

	}

	public void registraRelatorio(StringBuffer content) {
		// TODO Auto-generated method stub
		relatorio.append(content);
		relatorio.append(END_OF_LINE);

	}

	public StringBuffer getRelatorio() {
		return relatorio;
	}

	/**
	 * xxx
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void imprimeRelatorio(String fileName) throws IOException {
		// TODO Auto-generated method stub

		BufferedWriter out = null;

		FileWriter writer = null;

		try {

			writer = new FileWriter(new File(fileName));

			out = new BufferedWriter(writer);

			out.write(relatorio.toString());

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			writer.close();

			out.close();

		}

	}

	/**
	 * 
	 * @param readings
	 */
	public void escreveLinha(StringBuffer linha) {
		// TODO Auto-generated method stub

		relatorio.append(linha);

	}

	/**
	 * x
	 */
	public String toString() {

		return relatorio.toString();

	}
}
