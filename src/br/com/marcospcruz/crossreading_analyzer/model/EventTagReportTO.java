package br.com.marcospcruz.crossreading_analyzer.model;

import java.util.Date;

public class EventTagReportTO implements Comparable<EventTagReportTO> {

	public static final CharSequence EVENT_TAG_IDENTIFICATOR = "event.tag.report";

	private StringBuffer tagId;
	private StringBuffer tagType;
	private Float rssi;
	private Date dataHora;
	private StringBuffer linhaSlt;

	public StringBuffer getTagId() {
		return tagId;
	}

	public void setTagId(StringBuffer tagId) {
		this.tagId = tagId;
	}

	public StringBuffer getTagType() {
		return tagType;
	}

	public void setTagType(StringBuffer tagType) {
		this.tagType = tagType;
	}

	public Float getRssi() {
		return rssi;
	}

	public void setRssi(Float rssi) {
		this.rssi = rssi;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public StringBuffer getLinhaSlt() {
		return linhaSlt;
	}

	public void setLinhaSlt(StringBuffer linhaSlt) {
		this.linhaSlt = linhaSlt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataHora == null) ? 0 : dataHora.hashCode());
		result = prime * result
				+ ((linhaSlt == null) ? 0 : linhaSlt.hashCode());
		result = prime * result + ((rssi == null) ? 0 : rssi.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((tagType == null) ? 0 : tagType.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "EventTagReportTO [tagId=" + tagId + ", tagType=" + tagType
				+ ", rssi=" + rssi + ", dataHora=" + dataHora + ", linhaSlt="
				+ linhaSlt + "]";
	}

	@Override
	public int compareTo(EventTagReportTO o) {

		long time = o.dataHora.getTime();

		return (int) (this.dataHora.getTime() - time);
	}

}

// class EventTagReportComparator implements Comparator<EventTagReportTO>{
//
// @Override
// public int compare(EventTagReportTO arg0, EventTagReportTO arg1) {
// // TODO Auto-generated method stub
// return arg0.;
// }
//
// }