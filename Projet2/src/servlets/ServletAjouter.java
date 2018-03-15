package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import beans.formulaires.FormulaireAjouter;
import beans.meteo.Meteo;
import beans.meteo.Temps;
import interfaceRmi.ServeurRmi;
import manager.Manager;
import validation.Validation;

@WebServlet("/ServletAjouter")
public class ServletAjouter extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ServletAjouter() {
		super();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!Manager.creer(request).isIdentifier()) {
			response.sendRedirect("ServletConnexion");
			return ;
		}
		request.setAttribute("titre", "Ajouter");
		request.setAttribute("contenu", "/WEB-INF/ajouter/Ajouter.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!Manager.creer(request).isIdentifier()) {
			response.sendRedirect("ServletConnexion");
			return ;
		}
		String lieu = (String) request.getParameter("lieu");
		String type = (String) request.getParameter("type");
		String date = (String) request.getParameter("date");
		String minimum = (String) request.getParameter("minimum");
		String maximum = (String) request.getParameter("maximum");
		String moyenne = (String) request.getParameter("moyenne");

		// debut de la validation du formulaire
		FormulaireAjouter formulaireAjouter = new FormulaireAjouter();
		formulaireAjouter.setDate(date);
		formulaireAjouter.setLieu(lieu);
		formulaireAjouter.setType(type);
		formulaireAjouter.setMinimum(minimum);
		formulaireAjouter.setMaximum(maximum);
		formulaireAjouter.setMoyenne(moyenne);
		Validation validation = formulaireAjouter.getValidation();

		if (validation.isValide()) {
			// creation de l'obkjet meteo
			Meteo meteo = new Meteo();
			meteo.setLieu(lieu);
			meteo.setTemps(Temps.valueOf(type));
			meteo.setDate(date);
			meteo.setMin(Double.parseDouble(minimum));
			meteo.setMax(Double.parseDouble(maximum));
			meteo.setMoy(Double.parseDouble(moyenne));
			

			ServeurRmi serveur=Manager.creer(request).getServeur();
			serveur.ouvrir();
			serveur.ajouterMeteo(meteo);
			serveur.fermer();

			// redirection vers la servlet consultation
			response.sendRedirect("ServletConsultation");

		} else {
			// redirection vers le formulaire
			request.setAttribute("titre", "Ajouter");
			request.setAttribute("contenu", "/WEB-INF/ajouter/Ajouter.jsp");
			request.setAttribute("validation", validation);
			request.setAttribute("erreur", "Il y a une erreur dans le formulaire d'ajoue");
			request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
		}

	}

}
