package serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaceRmi.ServeurRmi;

public class ClientRmi {

	public static void main(String [] args) {
		
		int port = 3000;
		
		try {
			Registry registry =	LocateRegistry.getRegistry(port);
			
			ServeurRmi serveur =(ServeurRmi) registry.lookup("serveurRmi");
			
			System.out.println("Connexion :"+serveur.ouvrir());

			
			serveur.fermer();
		}
		catch (Exception e) {
			System.out.println("Erreur client RMI");
		}
	}
}
