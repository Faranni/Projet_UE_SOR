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
	private Date date;
	private int idMeteo;
	private Double min; 
	private Double max ;
	private Double moy;
	private Temps temps;	
	
	
	
	
	
	@Override
	public String toString() {
		return "Meteo [lieu=" + lieu + ", date=" + date + ", idMeteo=" + idMeteo + ", min=" + min + ", max=" + max
				+ ", moy=" + moy + ", temps=" + temps + "]";
	}

	public String getLieu() {
		return lieu;
	}
	
	public void setLieu(String lieu) {
		this.lieu = lieu;
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
	
	
	
	
	public int getIdMeteo() {
		return idMeteo;
	}


	public void setIdMeteo(int idMeteo) {
		this.idMeteo = idMeteo;
	}


	public Double getMin() {
		return min;
	}


	public void setMin(Double min) {
		this.min = min;
	}


	public Double getMax() {
		return max;
	}


	public void setMax(Double max) {
		this.max = max;
	}


	public Double getMoy() {
		return moy;
	}


	public void setMoy(Double moy) {
		this.moy = moy;
	}

	public Temps getTemps() {
		return temps;
	}


	public void setTemps(Temps temps) {
		this.temps = temps;
	}


	/**
	 * si les atribut date et  ville sont egaux retourne true
	 * sinon retourne false
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
