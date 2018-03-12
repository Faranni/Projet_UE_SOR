package beans.formulaires;

import annotations.Regexp;
import validation.Validation;

public class FormulaireAjouter {
	
	@Regexp(expr = ".{2,}", value = "Il faut deux lettres")
	private String lieu;
	
	@Regexp(expr = ".{2,}", value = "selectionner un type")
	private String type;
	
	@Regexp(expr = "[0-9]{2}/[0-9]{2}/[0-9]{4}", value = "Il faut une date")
	private String date;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Validation getValidation() {
		// debut de la validation du formulaire
		Validation validation = new Validation();

		validation.regexp(FormulaireAjouter.class, "lieu", this.getLieu());
		validation.regexp(FormulaireAjouter.class, "type", this.getType());
		validation.regexp(FormulaireAjouter.class, "date", this.getDate());
		
		return validation;
	}
	
}
