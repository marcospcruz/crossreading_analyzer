package br.com.marcospcruz.crossreading_analyzer.model;

import java.util.Collection;
import java.util.Date;

public class TransacaoCVTO implements Comparable<TransacaoCVTO> {

	public static final CharSequence CV_TRANSACTION_IDENTIFICATOR = "VEIC SeqMens";

	public static final long TIME_FRAME = 5;

	private StringBuffer tagId;
	private Date dataHora;

	private StringBuffer logRow;

	private StringBuffer cvLogName;

	private Collection<EventTagReportTO> leituras;

	public TransacaoCVTO(StringBuffer cvLogName) {

		this.cvLogName = cvLogName;

	}

	public StringBuffer getTagId() {
		return tagId;
	}

	public void setTagId(StringBuffer tagId) {
		this.tagId = tagId;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public void setLogRow(StringBuffer logRow) {

		this.logRow = logRow;

	}

	public StringBuffer getLogRow() {
		return logRow;
	}

	public StringBuffer getCvLogName() {
		return cvLogName;
	}

	public void setCvLogName(StringBuffer cvLogName) {
		this.cvLogName = cvLogName;
	}

	public Collection<EventTagReportTO> getLeituras() {
		return leituras;
	}

	public void setLeituras(Collection<EventTagReportTO> leituras) {
		this.leituras = leituras;
	}

	@Override
	public String toString() {
		return "TransacaoCV [tagId=" + tagId + ", dataHora=" + dataHora + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataHora == null) ? 0 : dataHora.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransacaoCVTO other = (TransacaoCVTO) obj;
		if (dataHora == null) {
			if (other.dataHora != null)
				return false;
		} else if (!dataHora.equals(other.dataHora))
			return false;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		return true;
	}

	@Override
	public int compareTo(TransacaoCVTO o) {
		// TODO Auto-generated method stub
		int retorno = (int) (this.dataHora.getTime() - o.dataHora.getTime());
		return retorno;
	}

}
