package br.com.marcospcruz.crossreading_analyzer.util;

public class ConversorTagUtil {

	public static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss,SSS";
	public static final String DATETIME_PATTERN2 = "yyyy/MM/dd HH:mm:ss,SSS";
	public static final long TIME_FRAME_PASSAGES = 5;

	private static final char[] CHARACTERS_MAP = { 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p' };

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public static String converteTagNumericoParaTexto(String tagId) {

		StringBuffer novoTag = new StringBuffer();

		String tagId2 = tagId.length() > 16 ? tagId.substring(8) : tagId;

		for (char caractere : tagId2.toCharArray()) {

			int indice = 0;

			if (Character.isDigit(caractere)) {

				indice = new Integer(Character.toString(caractere));

				novoTag.append(CHARACTERS_MAP[indice]);

			} else {

				novoTag.append(Character.toLowerCase(caractere));

			}

			// int indice = new Integer((int) CHARACTERS_MAP[tagId.charAt(i)]);

		}
		return novoTag.toString();
	}

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public static String converteTagTextoParaHexa(String tagId) {
		// TODO Auto-generated method stub

		StringBuffer numericTag = new StringBuffer();

		for (char character : tagId.toCharArray()) {

			Character c = findCorrespondentChar(character);

			numericTag.append(c);

		}

		return numericTag.toString();

	}

	/**
	 * 
	 * @param character
	 * @return
	 */

	private static Character findCorrespondentChar(char character) {

		for (int i = 0; i < CHARACTERS_MAP.length; i++) {

			char c = CHARACTERS_MAP[i];

			if (character == c) {

				return Character.forDigit(i, 10);

			}

		}

		return Character.toUpperCase(character);
	}

}
