package beans.formulaires;

import annotations.Regexp;
import validation.Validation;

public class FormulaireAjouterArchive {


	@Regexp(expr = "(.+)", value = "Il faut une archive")
	private String archiveName;

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public Validation getValidation() {
		// debut de la validation du formulaire
		Validation validation = new Validation();

		validation.regexp(FormulaireAjouterArchive.class, "archiveName", this.getArchiveName());

		return validation;
	}
}
