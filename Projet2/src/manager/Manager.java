package manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.http.HttpServletRequest;

import beans.utilisateur.Utilisateur;
import interfaceRmi.ServeurRmi;

public class Manager {
	
	// Attribues
	
	boolean identifier = false;
	
	Utilisateur utilisateur;
	private ServeurRmi serveur;
	final int port = 3000;
	
	
	
	// methode
	
	public static Manager creer(HttpServletRequest request) {
		
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		
		if (manager == null) {
			manager = new Manager();
			request.getSession().setAttribute("manager", manager);
			
			//initilisation de l'objet rmi
			try {
		    	Registry registry =	LocateRegistry.getRegistry(manager.getPort());
		    	manager.setServeur((ServeurRmi) registry.lookup("serveurRmi"));
		    }
		    catch (Exception e) {
				System.out.println("Erreur client RMI");
		    }
		}		 
				
		return manager;
	}
	
	
	// getteur / setteur

	public boolean isIdentifier() {
		return identifier;
	}

	public void setIdentifier(boolean identifier) {
		this.identifier = identifier;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public ServeurRmi getServeur() {
		return serveur;
	}


	public void setServeur(ServeurRmi serveur) {
		this.serveur = serveur;
	}


	public int getPort() {
		return port;
	}
	

	
	
	
}
