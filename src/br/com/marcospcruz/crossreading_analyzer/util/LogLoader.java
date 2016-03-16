package br.com.marcospcruz.crossreading_analyzer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogLoader {

	public List<StringBuffer> loadLogFile(File file) throws IOException {

		BufferedReader bf = null;

		List<StringBuffer> content = new ArrayList<StringBuffer>();

		try {

			bf = new BufferedReader(new FileReader(file));

			while (bf.ready()) {

				StringBuffer row = new StringBuffer(bf.readLine());

				content.add(row);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			bf.close();

		}

		return content;

	}

}
