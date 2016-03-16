package br.com.marcospcruz.crossreading_analyzer.util;

import java.io.IOException;

import br.com.marcospcruz.crossreading_analyzer.controller.CrossReadingAnalyzer;

public class MainClass {

	public static void main(String args[]) {

		CrossReadingAnalyzer analyzer = new CrossReadingAnalyzer(args);

		analyzer.loadLogFiles();

		// analyzer.checkTransactionsCrossRead();

		analyzer.analyzeCrossReading();
		//
		// analyzer.geraRelatorio();

		try {
			analyzer.printReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
