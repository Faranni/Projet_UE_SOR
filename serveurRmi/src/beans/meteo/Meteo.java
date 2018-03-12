package beans.meteo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

public class Meteo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lieu;
	private String type;
	private Date date;
	
	
	
	
	@Override
	public String toString() {
		return "Meteo [lieu=" + lieu + ", type=" + type + ", date=" + date + "]";
	}
	
	
	public String getLieu() {
		return lieu;
	}
	
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		Calendar calendar = Calendar.getInstance();
		String [] tabDate = date.split("/");
		calendar.set(Calendar.YEAR, Integer.parseInt(tabDate[2]));
		calendar.set(Calendar.MONTH, Integer.parseInt(tabDate[1]) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tabDate[0]));
		Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());
		this.date = sqlDate;
	}
	
	
	/**
	 * si la date et la ville sont egaux return true
	 * else return false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (!(o instanceof Meteo)) {
			return false;
		}

		Meteo meteo = (Meteo) o;
		boolean b1 = this.lieu.equals(meteo.getLieu());
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(meteo.getDate());
		
		boolean b2 = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
				calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
				calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
		
		return b1 && b2;
		
	}
	
	
}
