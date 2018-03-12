package beans.formulaires;

import annotations.Regexp;
import validation.Validation;

public class FormulaireConnexion {

	@Regexp(expr = ".{2,}@.{2,}[.].{2,}", value = "Il faut une adresse mail")
	private String email;

	@Regexp(expr = ".{5,}", value = "Il faut au moins 5 lettres pour le mdp")
	private String password;

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Validation getValidation() {
		// debut de la validation du formulaire
		Validation validation = new Validation();

		validation.regexp(FormulaireConnexion.class, "email", this.getEmail());
		validation.regexp(FormulaireConnexion.class, "password", this.getPassword());
		
		return validation;
	}
}
