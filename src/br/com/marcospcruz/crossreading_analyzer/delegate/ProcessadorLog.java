package br.com.marcospcruz.crossreading_analyzer.delegate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.marcospcruz.crossreading_analyzer.util.UtilClass;

public abstract class ProcessadorLog {

	private int contagemPorcentoPrint = 0;

	/**
	 * 
	 * @param total
	 * @param currentStep
	 */
	protected void reportProgress(int total, int currentStep) {
		// TODO Auto-generated method stub

		int percentage = (int) UtilClass.calculaPorcentagem(total, currentStep);

		if (percentage == 1.0 && contagemPorcentoPrint == 0) {

			System.out.print("PROGRESS: " + percentage + "% #");
			contagemPorcentoPrint++;

		} else if (percentage > 0) {

			if (percentage % 10 == 0 && (contagemPorcentoPrint % 10) != 0) {

				System.out.print("#");
				contagemPorcentoPrint = 10;

			} else if ((percentage % 10 != 0) && percentage > 10
					&& contagemPorcentoPrint == 10) {

				contagemPorcentoPrint = 1;

			} else if ((total - currentStep) == 1) {

				System.out.println(" " + (percentage + 1) + "% concluído.");

				contagemPorcentoPrint = 0;

			}

		}

	}

	/**
	 * 
	 * @param time
	 * @param time2
	 * @return
	 */
	protected long calculaDiffTimeInMinutes(long time, long time2) {
		// TODO Auto-generated method stub

		return UtilClass.calculaDiffTimeInMinutes(time, time2);

	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public int mapCollectionsSize(Map map) {

		int size = 0;

		Iterator iterator = map.keySet().iterator();

		while (iterator.hasNext()) {

			String x = iterator.next().toString();

			List teste = (List) map.get(x);

			size += teste.size();

		}

		return size;

	}

}
