package serveur;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import beans.meteo.Meteo;
import interfaceRmi.ServeurRmi;

public class ClientRmi {

	public static void main(String [] args) {
		
		int port = 3000;
		
		try {
			Registry registry =	LocateRegistry.getRegistry(port);
			
			ServeurRmi serveur =(ServeurRmi) registry.lookup("serveurRmi");
			
			System.out.println("Connexion :"+serveur.ouvrir());
			List<Meteo> listeMeteo=serveur.getMeteo();
			int[] ids=new int[listeMeteo.size()];
			for(int i=0;i<listeMeteo.size();i++) {
				ids[i]=listeMeteo.get(i).getIdMeteo();
			}
			
			byte[] fichier=serveur.generationPdf(new int[] {3,26});
			
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
