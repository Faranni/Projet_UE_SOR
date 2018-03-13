package beans.meteo;

public enum Temps {
	
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
