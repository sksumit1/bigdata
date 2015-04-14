package com.sumit.metdata.programs;

import java.io.Serializable;

public class MetaRecordParser implements Serializable {
	
	private boolean complete = true;
	private Integer year;
	private Integer month;
	private Double maxTemp;
	private Double minTemp;
	private Integer daysCalculated;
	private Double rain;
	private Double sunHours;
	
	public boolean parser(String record) {
		if(record == null || record.startsWith("#")) {
			complete = false;
		}
		record = record.trim().replaceAll("\\s+", " ");
		String[] elements = record.split(" ");
		if(elements.length < 7) {
			complete = false;
		}
		if(elements.length >= 1) {
			try {
				year = Integer.parseInt(elements[0]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 2) {
			try {
				month = Integer.parseInt(elements[1]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 3) {
			try {
				maxTemp = Double.parseDouble(elements[2]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 4) {
			try {
				minTemp = Double.parseDouble(elements[3]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 5) {
			try {
				daysCalculated = Integer.parseInt(elements[4]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 6) {
			try {
				rain = Double.parseDouble(elements[5]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 7) {
			try {
				sunHours = Double.parseDouble(elements[6]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		return complete;
	}

	public boolean isComplete() {
		return complete;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getMonth() {
		return month;
	}

	public Double getMaxTemp() {
		return maxTemp;
	}

	public Double getMinTemp() {
		return minTemp;
	}

	public Integer getDaysCalculated() {
		return daysCalculated;
	}

	public Double getRain() {
		return rain;
	}

	public Double getSunHours() {
		return sunHours;
	}
	
}
