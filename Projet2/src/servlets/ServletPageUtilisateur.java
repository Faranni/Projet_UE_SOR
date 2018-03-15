package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.Manager;


@WebServlet("/ServletPageUtilisateur")
public class ServletPageUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletPageUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!Manager.creer(request).isIdentifier()) {
			response.sendRedirect("ServletConnexion");
			return ;
		}
		request.setAttribute("titre", "PageUtilisateur");
		request.setAttribute("contenu", "/WEB-INF/pageUtilisateur/PageUtilisateur.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!Manager.creer(request).isIdentifier()) {
			response.sendRedirect("ServletConnexion");
			return ;
		}

	}

}
