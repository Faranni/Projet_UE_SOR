package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.meteo.Image;
import interfaceRmi.ServeurRmi;
import manager.Manager;


@WebServlet("/ServletImage")
public class ServletImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletImage() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int  idMeteo = Integer.parseInt((String) request.getParameter("idMeteo")); 
		
		ServeurRmi serveur=Manager.creer(request).getServeur();
		serveur.ouvrir();
		List<Image> images =  serveur.getlisteImage(idMeteo);
		serveur.fermer();

		response.getOutputStream().write(images.get(0).getImage());
	}


}
