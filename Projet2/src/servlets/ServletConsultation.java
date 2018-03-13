package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.meteo.Meteo;
import beans.meteo.Temps;
import interfaceRmi.ServeurRmi;
import manager.Manager;



@WebServlet("/ServletConsultation")
public class ServletConsultation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public ServletConsultation() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			ServeurRmi serveur=Manager.creer(request).getServeur();		

			serveur.ouvrir();
			List<Meteo> meteos = serveur.getMeteo();
			serveur.fermer();

			request.setAttribute("titre", "Consulation");
			request.setAttribute("contenu", "/WEB-INF/consulation/Consulation.jsp");
			request.setAttribute("meteos", meteos);
			request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
			
		}
		catch (Exception e) {
			System.out.println("Erreur client RMI");
		}	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("supprimer") != null) {
			String lieu = (String) request.getParameter("lieu");
			String type = (String) request.getParameter("type");
			String date = (String) request.getParameter("date");
			
			Meteo meteo = new Meteo();
			meteo.setLieu(lieu);
			meteo.setTemps(Temps.valueOf(type));
			String [] tabeDate = date.split("-");
			date = tabeDate[2] +"/"+tabeDate[1]+"/"+tabeDate[0];
			meteo.setDate(date);
			
			
			try {
				ServeurRmi serveur=Manager.creer(request).getServeur();
				serveur.ouvrir();
				//serveur.supprimerMeteo(meteo);
				serveur.fermer();				
			}
			catch (Exception e) {
				System.out.println("Erreur client RMI");
			}	
			

			
		}
		
		response.sendRedirect("ServletConsultation");
	}

}
