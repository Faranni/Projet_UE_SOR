package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.formulaires.FormulaireAjouter2;
import beans.meteo.Meteo;
import beans.meteo.Temps;
import interfaceRmi.ServeurRmi;
import manager.Manager;
import validation.Validation;

@WebServlet("/ServletAjouter2")
public class ServletAjouter2 extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ServletAjouter2() {
		super();		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("titre", "Ajouter2");
		request.setAttribute("contenu", "/WEB-INF/ajouter/Ajouter2.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String lieu = (String) request.getParameter("lieu");
		String type = (String) request.getParameter("type");
		String date1 = (String) request.getParameter("date1");
		String date2 = (String) request.getParameter("date2");
		String minimum = (String) request.getParameter("minimum");
		String maximum = (String) request.getParameter("maximum");
		String moyenne = (String) request.getParameter("moyenne");

		// debut de la validation du formulaire
		FormulaireAjouter2 formulaireAjouter2 = new FormulaireAjouter2();
		formulaireAjouter2.setDate1(date1);
		formulaireAjouter2.setDate2(date2);
		formulaireAjouter2.setLieu(lieu);
		formulaireAjouter2.setType(type);
		formulaireAjouter2.setMinimum(minimum);
		formulaireAjouter2.setMaximum(maximum);
		formulaireAjouter2.setMoyenne(moyenne);
		Validation validation = formulaireAjouter2.getValidation();
		
		if (validation.isValide()) {
			// creation de l'obkjet meteo
			Meteo meteo1 = new Meteo();
			meteo1.setLieu(lieu);
			meteo1.setTemps(Temps.valueOf(type));
			meteo1.setDate(date1);
			
			meteo1.setMin(Double.parseDouble(minimum));
			meteo1.setMax(Double.parseDouble(maximum));
			meteo1.setMoy(Double.parseDouble(moyenne));
			
			Calendar calendar = Calendar.getInstance();
			String [] tabDate = date2.split("/");
			calendar.set(Calendar.YEAR, Integer.parseInt(tabDate[2]));
			calendar.set(Calendar.MONTH, Integer.parseInt(tabDate[1]) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tabDate[0]));
			Date sqlDate = new Date(calendar.getTimeInMillis());

			
			// ajout a la bdd
			ServeurRmi serveur=Manager.creer(request).getServeur();
			serveur.ouvrir();
			serveur.ajouterMeteos(meteo1, sqlDate);
			serveur.fermer();
			
			// redirection vers la servlet consultation
			response.sendRedirect("ServletConsultation");
			
		} else {
			// redirection vers le formulaire
			request.setAttribute("titre", "Ajouter2");
			request.setAttribute("contenu", "/WEB-INF/ajouter/Ajouter2.jsp");
			request.setAttribute("validation", validation);
			request.setAttribute("erreur", "Il y a une erreur dans le formulaire d'ajoue");
			request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
		}

	}

}
