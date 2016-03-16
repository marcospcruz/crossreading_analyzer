package br.com.marcospcruz.crossreading_analyzer.delegate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.marcospcruz.crossreading_analyzer.model.EventTagReportTO;
import br.com.marcospcruz.crossreading_analyzer.model.PistaTO;
import br.com.marcospcruz.crossreading_analyzer.model.TransacaoCVTO;
import br.com.marcospcruz.crossreading_analyzer.util.LogLoader;
import br.com.marcospcruz.crossreading_analyzer.util.UtilClass;

public class ProcessadorLogSLT extends ProcessadorLog {

	private static final String DATETIME_PATTERN2 = "yyyy/MM/dd HH:mm:ss,SSS";
	private static final int DATA_EVENT_REPORT = 0;
	private static final int HORA_EVENT_REPORT = 1;
	private static final int TAG_ID_EVENT_REPORT = 7;
	private static final int TAG_TYPE_EVENT_REPORT = 8;
	private static final int RSSI_EVENT_REPORT = 9;

	/**
	 * 
	 * @param sltLog
	 * @return
	 */
	public Map<String, List<EventTagReportTO>> loadEventReport(File sltLog) {
		// TODO Auto-generated method stub

		LogLoader loader = new LogLoader();

		Map<String, List<EventTagReportTO>> retorno = new HashMap<String, List<EventTagReportTO>>();

		try {

			List<StringBuffer> rows = loader.loadLogFile(sltLog);

			System.out.println("Carregando Log " + sltLog.getCanonicalPath());

			int linha = 0;

			for (StringBuffer row : rows) {

				linha++;

				reportProgress(rows.size(), linha);

				List<EventTagReportTO> events = null;

				if (row.toString().contains(
						EventTagReportTO.EVENT_TAG_IDENTIFICATOR)) {

					StringBuffer[] array = UtilClass.splitStringBuffer(row
							.toString().split(" "));

					if (array.length < 8 || !array[7].toString().contains(","))
						continue;

					String string;

					EventTagReportTO eventTagReport = new EventTagReportTO();

					eventTagReport.setDataHora(UtilClass.stringToDataHora(
							array[DATA_EVENT_REPORT] + " " + array[HORA_EVENT_REPORT], DATETIME_PATTERN2));

					eventTagReport.setLinhaSlt(row);

					string = array[TAG_ID_EVENT_REPORT].toString().split("=")[1].substring(0,
							array[TAG_ID_EVENT_REPORT].toString().split("=")[1].length() - 1);

					try {

						eventTagReport.setTagId(new StringBuffer(string));

						string = array[TAG_TYPE_EVENT_REPORT].toString().split("=")[1].substring(0,
								array[TAG_TYPE_EVENT_REPORT].toString().split("=")[1].length() - 1);

						eventTagReport.setTagType(new StringBuffer(string));

						string = array[RSSI_EVENT_REPORT].toString().split("=")[1].substring(0,
								array[RSSI_EVENT_REPORT].toString().split("=")[1].length() - 1);

						eventTagReport.setRssi(new Float(string));

					} catch (OutOfMemoryError e) {

						e.printStackTrace();
						// System.out.print(new Date());
						//
						// e.printStackTrace();

					} catch (Exception e) {

						// e.printStackTrace();

					}

					if (eventTagReport.getTagId().toString()
							.equals("0x05800250BC078085")) {

						System.out.println(eventTagReport.getDataHora());

					}

					events = retorno.get(eventTagReport.getTagId().toString());

					if (events == null)
						events = new ArrayList<EventTagReportTO>();

					events.add(eventTagReport);

					retorno.put(eventTagReport.getTagId().toString(), events);

				}

			}

		} catch (IOException e) {

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
	@SuppressWarnings("unchecked")
	public Map analisaLeiturasCruzadas(PistaTO pista1, PistaTO pista2) {

		Map retorno = new HashMap();

		for (String key : pista1.getLeiturasMap().keySet()) {

			if (key.equals("0x05800250BC078085")) {

				toString();

			}

			if (pista2.getLeiturasMap().containsKey(key)) {

				List<EventTagReportTO> leiturasPista1 = pista1.getLeiturasMap()
						.get(key);

				List<EventTagReportTO> leiturasPista2 = pista2.getLeiturasMap()
						.get(key);

				EventTagReportTO leituraAnterior = null;

				List<EventTagReportTO> leituras = new ArrayList<EventTagReportTO>();

				Object[] leiturasCruzadas;

				for (int i = 0; i < leiturasPista1.size(); i++) {

					EventTagReportTO evt1 = leiturasPista1.get(i);

					if (leituraAnterior != null) {

						long diffTimeEvts = calculaDiffTimeInMinutes(evt1
								.getDataHora().getTime(), leituraAnterior
								.getDataHora().getTime());

						if (diffTimeEvts > TransacaoCVTO.TIME_FRAME) {
							// || i == leiturasPista1.size()) {

							continue;

						}

					}

					leituras.add(evt1);

					leituraAnterior = evt1;

					// for (EventTagReportTO evt2 : leiturasPista2) {
					//
					//
					// }

				}

				leiturasCruzadas = verificaLeiturasCruzadas(leituras,
						leiturasPista2);

				if (leiturasCruzadas != null)

					retorno.put(key, leiturasCruzadas);

			}

		}

		return retorno;

	}

	/**
	 * 
	 * @param leiturasPista1
	 * @param leiturasPista2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object[] verificaLeiturasCruzadas(
			List<EventTagReportTO> leiturasPista1,
			List<EventTagReportTO> leiturasPista2) {

		Object[] leiturasCruzadas = new Object[2];

		leiturasCruzadas[0] = leiturasPista1;
		leiturasCruzadas[1] = new ArrayList<EventTagReportTO>();

		EventTagReportTO ultimaLeituraPista1 = leiturasPista1
				.get(leiturasPista1.size() - 1);

		for (int i = 0; i < leiturasPista2.size(); i++) {

			EventTagReportTO evt = leiturasPista2.get(i);

			long diffLeituras = calculaDiffTimeInMinutes(ultimaLeituraPista1
					.getDataHora().getTime(), evt.getDataHora().getTime());

			if (diffLeituras <= TransacaoCVTO.TIME_FRAME) {

				((List) leiturasCruzadas[1]).add(evt);

			}

		}

		if (((List<EventTagReportTO>) leiturasCruzadas[1]).size() == 0)

			return null;

		return leiturasCruzadas;
	}

}
