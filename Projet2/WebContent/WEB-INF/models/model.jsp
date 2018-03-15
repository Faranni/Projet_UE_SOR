<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0" />

<title>${titre}</title>

<!--Import Google Icon Font-->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">



</head>

<body>
	<!-- bandeau -->
	<div class="navbar-fixed">
		<nav class="navbar-fixed light-blue lighten-1" role="navigation">
			<div class="nav-wrapper container">
				<a href="ServletAccueil" class="brand-logo"><i
					class="material-icons left">home</i>Accueil</a>

				<ul class="right hide-on-med-and-down">
					<li class="site-nav--has-submenu"><a href="#"
						class="site-nav__link dropdown-button" data-activates="dropdown1"
						data-beloworigin="true" data-constrainwidth="false"
						data-hover="true"> Consultation <i
							class="material-icons right">arrow_drop_down</i>
					</a>
						<ul id="dropdown1" class="site-nav__submenu dropdown-content">

							<li><a href="ServletConsultation"
								class="site-nav__link">des données méteo</a></li>


							<li><a href="#!" class="site-nav__link">Analyse des
									données</a></li>


						</ul></li>
					<c:if test="${ manager.identifier }">

						<li class="site-nav--has-submenu"><a href="#"
							class="site-nav__link dropdown-button" data-activates="dropdown2"
							data-beloworigin="true" data-constrainwidth="false"
							data-hover="true"> Ajouter <i class="material-icons right">arrow_drop_down</i>
						</a>
							<ul id="dropdown2" class="site-nav__submenu dropdown-content">

								<li><a href="ServletAjouterArchive" class="site-nav__link">une archive</a></li>

								<li><a href="ServletAjouter2" class="site-nav__link">un
										ensemble de données</a></li>

								<li><a href="ServletAjouter" class="site-nav__link">une
										donnée météo</a></li>

								<li><a href="ServletAjouterPhoto" class="site-nav__link">une photo</a></li>


							</ul></li>

						<li><a href="ServletDeconnexion">Deconnexion</a></li>
						<li><a href="ServletPageUtilisateur"><i
								class="material-icons right">face</i>Compte</a></li>
					</c:if>
					<c:if test="${ ! manager.identifier }">
						<li><a href="ServletInscription">Inscription</a></li>
						<li><a href="ServletConnexion">Connexion</a></li>
					</c:if>
				</ul>

			</div>
		</nav>
	</div>




	<div class="container">

		<jsp:include page="${contenu}"></jsp:include>

	</div>

	<!-- pied de page -->
	<footer class="page-footer orange">
		<div class="container">
			<div class="row">
				<div class="col l6 s12">
					<h5 class="white-text">Footer Content</h5>
					<p class="grey-text text-lighten-4">You can use rows and
						columns here to organize your footer content.</p>
				</div>
				<div class="col l4 offset-l2 s12">
					<h5 class="white-text">Links</h5>
					<ul>
						<li><a class="grey-text text-lighten-3" href="#!">Link 1</a></li>
						<li><a class="grey-text text-lighten-3" href="#!">Link 2</a></li>
						<li><a class="grey-text text-lighten-3" href="#!">Link 3</a></li>
						<li><a class="grey-text text-lighten-3" href="#!">Link 4</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="footer-copyright">
			<div class="container">
				© 2014 Copyright Text <a class="grey-text text-lighten-4 right"
					href="#!">More Links</a>
			</div>
		</div>
	</footer>

	<!--Import jQuery before materialize.js-->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<!-- Compiled and minified JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<script src="js/script.js" type="text/javascript"></script>

</body>
</html>