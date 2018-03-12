<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<br>
<form class="col s12" method="post" action="ServletAjouter">
	<div class="row">
		<div class="input-field col s6">
			<input id="lieu" type="text" class="validate" name="lieu"
				value="${validation.valeurs['lieu']}"> <label for="email">lieu</label>
		</div>
		<c:if test="${validation.erreurs['lieu']  != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['lieu']}</div>
			</div>
		</c:if>
	</div>

	<div class="row">
		<div class="input-field col s6">
			<select name="type">
				<option value="${validation.valeurs['type']}" selected>${validation.valeurs['type']}</option>
				<option value="soleil">Soleil</option>
				<option value="pluie">Pluie</option>
				<option value="nuage">Nuage</option>
			</select> <label>Type</label>
		</div>
		<c:if test="${validation.erreurs['type']  != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['type']}</div>
			</div>
		</c:if>
	</div>

	<div class="row">
		<div class="input-field col s6">
			<input id="date" name="date" type="text" class="datepicker" value="${validation.valeurs['date']}">
			<label for="date">Date</label>
		</div>
		<c:if test="${validation.erreurs['date'] != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['date']}</div>
			</div>
		</c:if>

	</div>



	<button class="btn waves-effect waves-light" type="submit"
		name="ajouter">
		Ajouter <i class="material-icons right">send</i>
	</button>

	<div class="row">
		<c:if test="${ erreur  != null }">
			<div class="input-field col s12">
				<div class="card-panel red lighten-2">${erreur}</div>
			</div>
		</c:if>
	</div>


</form>


