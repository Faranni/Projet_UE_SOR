package beans.meteo;

import java.io.Serializable;

public enum Temps  implements Serializable {
	
	PLUIE ("Pluie"),SOLEIL("Soleil"),NUAGE("Nuage"),NEIGE("Neige");
	
	String temps="";
	
	Temps(String t){
		this.temps=t;
	}

	public String getTemps() {
		return temps;
	}

	public void setTemps(String temps) {
		this.temps = temps;
	}
	
	

}
