package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Array;

import beans.meteo.Meteo;
import interfaceRmi.ServeurRmi;
import manager.Manager;

/**
 * Servlet implementation class consulterGraph
 */
@WebServlet("/ServletStatistique")
public class ServletStatistique extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletStatistique() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("titre", "Statistique");
		request.setAttribute("contenu", "/WEB-INF/statistique/Statistique.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("actualiserStat") != null) {			
			
		
			List<Meteo> listeMeteo =new ArrayList<Meteo>();
			try {
				ServeurRmi serveur = Manager.creer(request).getServeur();
				serveur.ouvrir();
				listeMeteo =serveur.getMeteo();
				serveur.fermer();
				List<Meteo> listeMeteoBis =new ArrayList<>();
				for(int i=0;i<listeMeteo.size();i++) {
					String[] date = listeMeteo.get(i).getDate().toString().split("-");
					if(date[0].equals(request.getParameter("annee"))){
						listeMeteoBis.add(listeMeteo.get(i));
					}
				}
				request.setAttribute("statMeteo", listeMeteoBis);
				request.setAttribute("titre", "Statistique");
				request.setAttribute("contenu", "/WEB-INF/statistique/Statistique.jsp");
				request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
				
			} catch (Exception e) {
				System.out.println("Erreur client RMI");
			}
			
		}
	}

}
