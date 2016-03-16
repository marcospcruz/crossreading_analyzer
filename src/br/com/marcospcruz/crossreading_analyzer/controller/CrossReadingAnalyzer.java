package br.com.marcospcruz.crossreading_analyzer.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

import br.com.marcospcruz.crossreading_analyzer.delegate.ProcessadorLogCV;
import br.com.marcospcruz.crossreading_analyzer.delegate.ProcessadorLogSLT;
import br.com.marcospcruz.crossreading_analyzer.model.EventTagReportTO;
import br.com.marcospcruz.crossreading_analyzer.model.PistaTO;
import br.com.marcospcruz.crossreading_analyzer.model.TransacaoCVTO;
import br.com.marcospcruz.crossreading_analyzer.util.ConversorTagUtil;
import br.com.marcospcruz.crossreading_analyzer.util.ReportGenerator;
import br.com.marcospcruz.crossreading_analyzer.util.UtilClass;

public class CrossReadingAnalyzer {

	private static final String EOL = ReportGenerator.END_OF_LINE;

	private static final Object VIOLATOR = "Violator";

	private List<PistaTO> pistas;

	private ProcessadorLogCV processadorCv;

	private ProcessadorLogSLT processadorSlt;

	private Map<String, List<TransacaoCVTO>> transacoesLeituraCruzada;

	private Map<String, Object[]> leiturasCruzadasMap;

	private ReportGenerator report;

	/**
	 * constructor
	 * 
	 * @param args
	 */
	public CrossReadingAnalyzer(String[] args) {

		processadorCv = new ProcessadorLogCV();

		processadorSlt = new ProcessadorLogSLT();

		iniciaPistas(args);

	}

	/**
	 * 
	 * @param args
	 */
	private void iniciaPistas(String[] args) {
		// TODO Auto-generated method stub

		pistas = new ArrayList<PistaTO>();

		for (int i = 0; i < args.length / 2; i++) {

			PistaTO pista = new PistaTO();

			pista.getLogFiles().add(new File(args[i]));

			pista.getLogFiles().add(new File(args[i + 2]));

			pistas.add(pista);

		}

	}

	/**
	 * x
	 */
	public void loadLogFiles() {
		// TODO Auto-generated method stub

		for (PistaTO pista : pistas) {

			List<File> logs = (List<File>) pista.getLogFiles();

			File cvLog = logs.get(0);

			pista.setTransacoesMap(processadorCv.loadTransacoes(cvLog));

			File sltLog = logs.get(1);

			pista.setLeiturasMap(processadorSlt.loadEventReport(sltLog));

		}

	}

	/**
	 * x
	 * 
	 * @throws IOException
	 */
	public void printReport() throws IOException {
		// TODO Auto-generated method stub
		report = new ReportGenerator(
				"Relatório de Análise de Leituras Cruzadas");

		report.pulaLinha();

		// report.escreveLinha("\t\t\t\tTotal de Transações registradas simultâneamente nas 2 vias.");
		// report.pulaLinha();
		report.escreveLinha(totalTransacoesLeituraCruzada());
		report.pulaLinha();
		report.escreveLinha("* Total de Leituras Cruzadas registradas simultâneamente nas 2 vias: "
				+ leiturasCruzadasMap.size());
		report.escreveLinha("* Total de Transações registradas simultâneamente nas 2 vias para o mesmo Tag: "
				+ transacoesLeituraCruzada.size());

		report.pulaLinha();
		report.separaSecao(2);
		report.pulaLinha();
		report.escreveLinha("\t\t\t\tLeituras cruzadas que resultaram em Transações:");
		report.pulaLinha();
		report.separaSecao();
		report.escreveLinha(detalhaTransacoesLeituraCruzada());
		report.separaSecao(2);
		report.escreveLinha("\t\t\t\tLeituras cruzadas sem registro de Transações:");
		report.separaSecao(2);
		report.escreveLinha(imprimeLeiturasCruzadas());

		// imprimindo relatório
		imprimeRelatorio();

	}

	/**
	 * x
	 * 
	 * @return
	 */
	private StringBuffer imprimeLeiturasCruzadas() {
		// TODO Auto-generated method stub

		StringBuffer buffer = new StringBuffer();

		int counter = 0;

		for (String key : leiturasCruzadasMap.keySet()) {

			Object[] obj = leiturasCruzadasMap.get(key);

			buffer.append(++counter + ") Leituras registradas para o tag "
					+ key + "." + EOL + EOL);

			for (int i = 0; i < obj.length; i++) {

				buffer.append(getLeiturasTransacaoCruzada(null, key, i));

			}

		}

		return buffer;

	}

	/**
	 * 
	 * @return
	 */
	private StringBuffer detalhaTransacoesLeituraCruzada() {

		StringBuffer buffer = new StringBuffer();

		int contador = 0;

		for (String tag : transacoesLeituraCruzada.keySet()) {

			buffer.append((++contador)
					+ ") Transações registradas nas 2 vias para o tag " + tag
					+ EOL + EOL);

			List<TransacaoCVTO> trnCruzadas = transacoesLeituraCruzada.get(tag);

			buffer.append(EOL);
			buffer.append(separaLeituraCruzadaTag(trnCruzadas));

			buffer.append(EOL + EOL);

		}

		return buffer;

	}

	/**
	 * 
	 * @param trnCruzadas
	 * @return
	 */
	private StringBuffer separaLeituraCruzadaTag(List<TransacaoCVTO> trnCruzadas) {

		StringBuffer buffer = new StringBuffer();

		int index = 0;

		for (TransacaoCVTO trn : trnCruzadas) {

			buffer.append(trn.getCvLogName() + ": " + trn.getLogRow() + EOL
					+ EOL);

			String tagHexa = ConversorTagUtil.converteTagTextoParaHexa(trn
					.getTagId().toString());

			buffer.append(getLeiturasTransacaoCruzada(trn, tagHexa, index++));

			// buffer.append(ReportGenerator.LINHA_SEPARADORA);

		}

		return buffer;

	}

	/**
	 * 
	 * @param trn
	 * @param tagHexa
	 * @param index
	 * @return
	 */
	private StringBuffer getLeiturasTransacaoCruzada(TransacaoCVTO trn,
			String tagHexa, int index) {
		// TODO Auto-generated method stub

		StringBuffer buffer = new StringBuffer();

		Object[] arrayListas = separaLeituraCruzadaTag(tagHexa);

		if (arrayListas != null) {

			List<EventTagReportTO> leituras = (List<EventTagReportTO>) arrayListas[index];

			PistaTO pista = pistas.get(index);

			File log = ((List<File>) pista.getLogFiles()).get(1);

			String canonicalName = log.getAbsolutePath();

			buffer.append("Leituras registradas no log " + canonicalName + ":"
					+ EOL);

			float rssiMin = 0;
			float rssiMax = 0;
			float rssiAvg = 0;
			float rssiTotal = 0;

			int counter = 0;

			StringBuffer tmp = new StringBuffer();

			while (leituras.size() > 0) {
				EventTagReportTO leitura = leituras.get(0);

				leituras.remove(0);

				if (trn != null) {

					long diffTrnLeitura = UtilClass.calculaDiffTimeInMinutes(
							trn.getDataHora().getTime(), leitura.getDataHora()
									.getTime());

					if (diffTrnLeitura > TransacaoCVTO.TIME_FRAME) {

						break;

					}

					// else {
					//
					// break;
					//
					// }

				}

				if (leitura.getRssi() != null) {

					rssiMin = rssiMin == 0 || leitura.getRssi() < rssiMin ? leitura
							.getRssi() : rssiMin;

					rssiMax = rssiMax == 0 || leitura.getRssi() > rssiMax ? leitura
							.getRssi() : rssiMax;

					rssiTotal += leitura.getRssi();

					rssiAvg = rssiTotal / ++counter;

					tmp.append(leitura.getLinhaSlt() + EOL);

				}
			}

			buffer.append("Total de Leituras: " + counter + " - rssi min:"
					+ rssiMin + " rssi max:" + rssiMax + " rssi avg:" + rssiAvg
					+ EOL + EOL);

			buffer.append(tmp + EOL);

			buffer.append(ReportGenerator.LINHA_SEPARADORA + EOL);
		}

		return buffer;
	}

	/**
	 * 
	 * @param tagHexa
	 * @return
	 */
	private Object[] separaLeituraCruzadaTag(String tagHexa) {
		// TODO Auto-generated method stub

		String key = locateKey(tagHexa, leiturasCruzadasMap);

		Object[] arrayListas = leiturasCruzadasMap.get(key);

		return arrayListas;

	}

	/**
	 * 
	 * @param tagHexa
	 * @param mapa
	 * @return
	 */
	private String locateKey(String tagHexa, Map mapa) {
		// TODO Auto-generated method stub

		for (Object key : mapa.keySet()) {

			System.out.println(key);

			if (key.toString().contains(tagHexa)) {

				return key.toString();
			}

		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	private StringBuffer totalTransacoesLeituraCruzada() {
		// TODO Auto-generated method stub

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < pistas.size(); i++) {

			String linha;

			PistaTO pista = pistas.get(i);

			// log cv
			File logCv = ((List<File>) pista.getLogFiles()).get(0);

			linha = "* Total de transações registradas em "
					+ logCv.getName()
					+ ": "
					+ processadorCv
							.mapCollectionsSize(pista.getTransacoesMap())
					+ report.END_OF_LINE;

			buffer.append(linha);

		}

		return buffer;
	}

	/**
	 * x
	 */
	@SuppressWarnings("unchecked")
	public void analyzeCrossReading() {
		// TODO Auto-generated method stub

		for (int i = 0; i < pistas.size(); i++) {

			if (i < pistas.size() - 1) {

				PistaTO pista1 = pistas.get(i);
				PistaTO pista2 = pistas.get(i + 1);

				transacoesLeituraCruzada = processadorCv
						.analisaTransacoesLeituraCruzada(pista1, pista2);

				leiturasCruzadasMap = processadorSlt.analisaLeiturasCruzadas(
						pista1, pista2);

				confereTransacoesLeituraCruzada();

			}

		}

	}

	/**
	 * x
	 */
	private void confereTransacoesLeituraCruzada() {
		// TODO Auto-generated method stub

		try {

			transacoesLeituraCruzada.remove(VIOLATOR);

			for (String tag : transacoesLeituraCruzada.keySet()) {

				if (tag.equals("gloggilgbcgnogol")) {
					toString();
				}

				String hexaTag = locateKey(
						ConversorTagUtil.converteTagTextoParaHexa(tag),
						leiturasCruzadasMap);

				Object[] leiturasObjArray = null;

				if (hexaTag != null) {

					if (hexaTag.equals("0x05800250BC078085")) {

						toString();

					}

					leiturasObjArray = leiturasCruzadasMap.get(hexaTag);

					int count = 0;

					if (leiturasObjArray != null) {

						for (int i = 0; i < leiturasObjArray.length; i++) {

							List<EventTagReportTO> leiturasList = (List<EventTagReportTO>) leiturasObjArray[i];

							count += leiturasList.size();

						}

						if (count == 0) {

							try {

								// transacoesLeituraCruzada.remove(tag);
								continue;

							} catch (ConcurrentModificationException e) {

								e.printStackTrace();

							}

						}

					}

				}

			}

		} catch (ConcurrentModificationException e) {

			e.printStackTrace();

		}

	}

	/**
	 * x
	 * 
	 * @throws IOException
	 */

	private void imprimeRelatorio() throws IOException {
		// TODO Auto-generated method stub

		// String string1 = processadorLogCV.getCvLogFile(0).getName();
		// string1 = string1.substring(0, string1.indexOf('.'));
		//
		// String string2 = processadorLogCV.getCvLogFile(1).getName();
		// string2 = string2.substring(0, string2.indexOf('.'));

		// String fileName = path.contains("/") ? path + "/" : path + "\\";

		// String fileName = "report_analysis_of_" + string1 + "-" + string2
		String fileName = "crossReadingReport.log";

		report.imprimeRelatorio(fileName);

		Desktop.getDesktop().open(new File(fileName));

	}

}
