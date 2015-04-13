package com.sumit.metdata.programs;

import java.io.Serializable;

public class MetaRecordParser implements Serializable {
	
	private boolean complete = true;
	private Integer year;
	private Integer month;
	private Integer maxTemp;
	private Integer minTemp;
	private Integer daysCalculated;
	private Double rain;
	private Integer sunHours;
	
	public boolean parser(String record) {
		if(record == null || record.startsWith("#")) {
			return false;
		}
		String[] elements = record.trim().split("   ");
		if(elements.length < 7) {
			complete = false;
		}
		if(elements.length >= 1) {
			try {
				year = Integer.parseInt(elements[1]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 2) {
			try {
				month = Integer.parseInt(elements[2]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 3) {
			try {
				maxTemp = Integer.parseInt(elements[3]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 4) {
			try {
				minTemp = Integer.parseInt(elements[4]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 5) {
			try {
				daysCalculated = Integer.parseInt(elements[5]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 6) {
			try {
				rain = Double.parseDouble(elements[6]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		if(elements.length >= 7) {
			try {
				sunHours = Integer.parseInt(elements[7]);
			} catch(NumberFormatException ex) {
				complete = false;
			}
		}
		return true;
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

	public Integer getMaxTemp() {
		return maxTemp;
	}

	public Integer getMinTemp() {
		return minTemp;
	}

	public Integer getDaysCalculated() {
		return daysCalculated;
	}

	public Double getRain() {
		return rain;
	}

	public Integer getSunHours() {
		return sunHours;
	}
	
}
