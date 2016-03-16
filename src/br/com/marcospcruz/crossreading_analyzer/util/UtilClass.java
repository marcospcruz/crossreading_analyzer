package br.com.marcospcruz.crossreading_analyzer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilClass {

	public static Date stringToDataHora(String stringDate, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date date = null;

		try {

			date = sdf.parse(stringDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return date;
	}

	public static StringBuffer[] splitStringBuffer(String[] splittedString) {
		// TODO Auto-generated method stub

		StringBuffer array[] = new StringBuffer[splittedString.length];

		int i = 0;

		for (String string : splittedString) {

			array[i++] = new StringBuffer(string);

		}

		return array;

	}

	/**
	 * 
	 * @param size
	 * @param progresso
	 * @return
	 */
	public static float calculaPorcentagem(int size, float progresso) {

		float retorno = (progresso / size) * 100f;

		return retorno;
	}

	/**
	 * 
	 * @param time
	 * @param time2
	 * @return
	 */
	public static long calculaDiffTimeInMinutes(long time, long time2) {
		// TODO Auto-generated method stub
		return (time > time2 ? (time - time2) : (time2 - time)) / 1000 / 60;
	}

}
