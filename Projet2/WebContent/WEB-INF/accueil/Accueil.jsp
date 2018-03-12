<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Projet UE SOR - Mars 2018</h1>
<h2>Y. Autret - F. Singhoff Version provisoire 26 février 2018</h2>

<h3>Introduction</h3>

<p class="flow-text">Le but du projet est de construire une
	application n-tiers qui utilise les différents élé́ments é́tudié́s dans
	le cadre de l'UE SOR:</p>

<ul>
	<li>Un navigateur communique avec les Servlet (JSP) d'un serveur
		Tomcat, et les requêtes HTTP envoyées peuvent être de type AJAX</li>
	<li>Les Servlet (JSP) communiquent avec un serveur Java RMI pour
		accéder à la base de données (JDBC)</li>
</ul>

<p class="flow-text">Architecture n-tiers à construire:</p>

<img src="images/archi.png">

<h3>Description du projet</h3>

<p class="flow-text">On veut créer un site WEB pour visualiser et
	compléter des archives météo.</p>

<p class="flow-text">Une archive météo est définie par un lieu,
	une date, et des données météo (pluie, vent, soleil, ...). Elle peut
	être complétée par des photos.</p>

<h3>Liste des fonctions à réaliser</h3>

<ul class="collection">
	<li class="collection-item">Identification (par identifiant et mot
		de passe)</li>
	<li class="collection-item">Si l'identification a été effectuée
		:
		<ul class="collection">
			<li class="collection-item">Ajout d'un ensemble de données
				d'archives dans la base de données</li>
			<li class="collection-item">Ajout d'une donnée météo</li>
			<li class="collection-item">Ajout d'une photo pour compléter
				une donnée météo</li>
		</ul>
	</li>

	<li class="collection-item">Avec ou sans identification :
		<ul class="collection">
			<li class="collection-item">Consultation des données d'un jour</li>
			<li class="collection-item">Consultation des données d'un mois
				avec affichage des moyennes (en option productiond'un PDF)</li>
			<li class="collection-item">Analyse des données (proposer une
				ou deux analyses, par exemple recherche du mois dejanvier le plus
				froid, année la plus chaude, ...)</li>
		</ul>
	</li>
</ul>

<p class="flow-text">La manière de traiter ces problèmes est libre
	dans la mesure où les contraintes suivantes sont respectées:</p>

<ul class="collection">
	<li class="collection-item">Dans chaque page il faut mettre un
		menu qui donne accès aux autres pages</li>
	<li class="collection-item">La gestion des erreurs doit être
		traitée avec soin (cas des formulaires mal remplis, ...)</li>
	<li class="collection-item">Pour le développement sous Eclipse,
		il faut avoir deux projets distincts, un pour la partie JSP, un autre
		pour le serveur RMI.</li>
</ul>

<h3>Organisation et évaluation</h3>

<ul class="collection">
	<li class="collection-item">Le travail est à effectuer en binôme</li>
	<li class="collection-item">Fournir par mail à
		autret@univ-brest.fr / singhoff@univ-brest.fr un fichier WAR contenant
		<ul class="collection">
			<li class="collection-item">Les sources de l'application</li>
			<li class="collection-item">Les scripts de création des tables
				SQL</li>
		</ul>
	</li>

	<li class="collection-item">L'évaluation se fera sur ce document
		et sur une démonstration en fin de semaine (vendredi après-midi).
		Les critères d'évaluation sont les suivants :
		<ul class="collection">
			<li class="collection-item">La fiabilité de l'application. La
				gestion des erreurs doit être traitée avec soin (cas des
				formulaires mal remplis, ...)</li>
			<li class="collection-item">Le niveau fonctionnel atteint</li>
			<li class="collection-item">La qualité du jeu de tests</li>
			<li class="collection-item">La qualité du code (propreté,
				fiabilité)</li>
		</ul>
	</li>
</ul>

<h3>Indications pour le projet</h3>

<h4>Début du projet</h4>

<ul class="collection">
	<li class="collection-item">Définir les tables SQL</li>
	<li class="collection-item">Mettre en oeuvre l'architecture
		complète (Serveur RMI + Serveur Tomcat) sur un exemple, par exemple
		l'identification</li>
	<li class="collection-item">Stabiliser au plus vite l'interface
		RMI. Pour le développement sous Eclipse, il faut avoir deux projets
		distincts, un pour la partie JSP/Servlet, un autre pour le serveur
		RMI.</li>
</ul>


<h4>Accès au serveur RMI depuis le projet JSP</h4>

<ul class="collection">
	<li class="collection-item">Définir tous les beans dans le projet
		RMI</li>
	<li class="collection-item">Faire un JAR contenant les beans et
		l'interface du serveur RMI</li>
	<li class="collection-item">Copier ce JAR dans le répertoire
		WEB-INF/lib du projet JSP</li>
	<li class="collection-item">Faire le lookup dans le constructeur
		du “manager” (exécuté une fois par session) et mettre un
		“jsp:useBean id=”manager” ...” au début de chaque JSP. Même remarque
		en cas d'utilisation des Servlets.</li>
</ul>

<h4>Utilisation d'AJAX, jQuery et jQuery UI</h4>

<ul class="collection">
	<li class="collection-item">L'utilisation d'AJAX et de jQuery est
		facultative.</li>
	<li class="collection-item">Il est possible d'utiliser une
		solution classique pour certaines pages, et une solution plus
		élaborée (utilisant par exemple jQuery et jQuery UI) pour d'autres
		pages.</li>
</ul>

<h4>Utilisation des CSS</h4>

<ul class="collection">
	<li class="collection-item">Ce n'est pas un point essentiel du
		projet, on pourra s'intéresser aux CSS s'il reste du temps à la fin.</li>
</ul>

<h4>Production de PDF</h4>

<ul class="collection">
	<li class="collection-item">Trouver une librairie JAVA permettant
		de produire un PDF (Apache/FOP, iText, ...)</li>
</ul>