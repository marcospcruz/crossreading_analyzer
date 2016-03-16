package br.com.marcospcruz.crossreading_analyzer.delegate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.marcospcruz.crossreading_analyzer.model.PistaTO;
import br.com.marcospcruz.crossreading_analyzer.model.TransacaoCVTO;
import br.com.marcospcruz.crossreading_analyzer.util.ConversorTagUtil;
import br.com.marcospcruz.crossreading_analyzer.util.LogLoader;
import br.com.marcospcruz.crossreading_analyzer.util.UtilClass;

public class ProcessadorLogCV extends ProcessadorLog {

	/**
	 * 
	 * @param cvLogName12
	 * @return
	 */
	public Map<String, List<TransacaoCVTO>> loadTransacoes(File file) {

		LogLoader loader = new LogLoader();

		Map<String, List<TransacaoCVTO>> retorno = new HashMap();

		List<TransacaoCVTO> events = null;

		int linha = 0;

		try {

			System.out.println("Carregando Log " + file.getCanonicalPath());

			List<StringBuffer> rows = loader.loadLogFile(file);

			for (StringBuffer row : rows) {

				linha++;

				reportProgress(rows.size(), linha);

				if (row.toString().contains(
						TransacaoCVTO.CV_TRANSACTION_IDENTIFICATOR)) {

					String[] array = row.toString().split(" ");

					TransacaoCVTO trn = new TransacaoCVTO(new StringBuffer(
							file.getName()));

					trn.setLogRow(new StringBuffer(row.toString()));

					trn.setDataHora(UtilClass.stringToDataHora(array[1] + " "
							+ array[2], ConversorTagUtil.DATETIME_PATTERN));

					try {

						trn.setTagId(new StringBuffer(array[21]));

					} catch (Exception e) {

						// e.printStackTrace();
						trn.setTagId(new StringBuffer("Violator"));

					}

					events = retorno.get(trn.getTagId().toString());

					if (events == null) {

						events = new ArrayList<TransacaoCVTO>();

					}

					events.add(trn);

					retorno.put(trn.getTagId().toString(), events);

				}

			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;

	}

	/**
	 * 
	 * @param pista1
	 * @param pista2
	 * @return
	 */
	public Map<String, List<TransacaoCVTO>> analisaTransacoesLeituraCruzada(
			PistaTO pista1, PistaTO pista2) {

		Map<String, List<TransacaoCVTO>> retorno = new HashMap<String, List<TransacaoCVTO>>();

		for (String key : pista1.getTransacoesMap().keySet()) {

			if (pista2.getTransacoesMap().containsKey(key)) {

				List<TransacaoCVTO> transacoesPista1 = pista1
						.getTransacoesMap().get(key);

				List<TransacaoCVTO> transacoesPista2 = pista2
						.getTransacoesMap().get(key);

				for (TransacaoCVTO trn1 : transacoesPista1) {

					for (TransacaoCVTO trn2 : transacoesPista2) {

						Long diffTimeTrns = calculaDiffTimeInMinutes(trn1
								.getDataHora().getTime(), trn2.getDataHora()
								.getTime());

						if (diffTimeTrns <= TransacaoCVTO.TIME_FRAME) {

							List<TransacaoCVTO> lista = new ArrayList<TransacaoCVTO>();

							lista.add(trn1);

							lista.add(trn2);

							retorno.put(key, lista);

						}

					}

				}

			}

		}

		return retorno;

	}

}
