package serveur;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			System.out.println("liste:"+serveur.getlisteImage(60).toString());
			byte[] fichier=serveur.generationPdf();
			System.out.println("taille fichier:"+fichier.length);
			Path path = Paths.get("H:\\Documents\\donnee.pdf");
			Files.write(path, fichier);
			serveur.fermer();
		}
		catch (Exception e) {
			System.out.println("Erreur client RMI");
		}
	}
}
