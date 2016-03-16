package br.com.marcospcruz.crossreading_analyzer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PistaTO {

	private Collection<File> logFiles;

	private Map<String, List<TransacaoCVTO>> transacoesMap;

	private Map<String, List<EventTagReportTO>> leiturasMap;

	public PistaTO() {
		logFiles = new ArrayList<File>();
		transacoesMap = new HashMap<String, List<TransacaoCVTO>>();
	}

	public Collection<File> getLogFiles() {
		return logFiles;
	}

	public void setLogFile(Collection<File> logFiles) {
		this.logFiles = logFiles;
	}

	public Map<String, List<TransacaoCVTO>> getTransacoesMap() {
		return transacoesMap;
	}

	public void setTransacoesMap(Map<String, List<TransacaoCVTO>> transacoesMap) {
		this.transacoesMap = transacoesMap;
	}

	public Map<String, List<EventTagReportTO>> getLeiturasMap() {
		return leiturasMap;
	}

	public void setLeiturasMap(Map<String, List<EventTagReportTO>> leiturasMap) {
		this.leiturasMap = leiturasMap;
	}

}
