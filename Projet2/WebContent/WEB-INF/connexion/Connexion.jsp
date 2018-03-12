<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<br>
<div class="row">
	<form class="col s12" method="post" action="ServletConnexion">
		<div class="row">
			<div class="input-field col s6">
				<input id="email" type="text" class="validate" name="email" value="${validation.valeurs['email']}">
				<label for="email">Email</label>
			</div>
			<c:if test="${validation.erreurs['email']  != null}">
				<div class="input-field col s6">	
					<div class="card-panel red lighten-2">${validation.erreurs['email']}</div>
				</div>
			</c:if>
		</div>
		<div class="row">
			<div class="input-field col s6">
				<input id="password" type="password" class="validate" name="password" value="${validation.valeurs['password']}">
				<label for="password">Mot de passes</label>
			</div>
			<c:if test="${validation.erreurs['password'] != null}">
				<div class="input-field col s6">	
					<div class="card-panel red lighten-2">${validation.erreurs['password']}</div>
				</div>
			</c:if>
			
		</div>

		<button class="btn waves-effect waves-light" type="submit" name="connexion">
			Connexion <i class="material-icons right">send</i>
		</button>
		
		<div class="row">
			<c:if test="${ erreur  != null }">
				<div class="input-field col s12">	
					<div class="card-panel red lighten-2">${erreur}</div>
				</div>
			</c:if>
		</div>
		
	</form>
	
</div>