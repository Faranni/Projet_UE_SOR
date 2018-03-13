<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="beans.meteo.Temps"%>
<br>
<form class="col s12" method="post" action="ServletAjouter2">
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
			<input id="minimum" type="text" class="validate" name="minimum"
				value="${validation.valeurs['minimum']}"> <label for="minimum">Température minimum</label>
		</div>
		<c:if test="${validation.erreurs['minimum']  != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['minimum']}</div>
			</div>
		</c:if>
	</div>
	
	<div class="row">
		<div class="input-field col s6">
			<input id="maximum" type="text" class="validate" name="maximum"
				value="${validation.valeurs['maximum']}"> <label for="maximum">Temperature maximum</label>
		</div>
		<c:if test="${validation.erreurs['maximum']  != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['maximum']}</div>
			</div>
		</c:if>
	</div>
	
	<div class="row">
		<div class="input-field col s6">
			<input id="moyenne" type="text" class="validate" name="moyenne"
				value="${validation.valeurs['moyenne']}"> <label for="moyenne">Temperature moyenne</label>
		</div>
		<c:if test="${validation.erreurs['moyenne']  != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['moyenne']}</div>
			</div>
		</c:if>
	</div>
	

	<div class="row">
		<div class="input-field col s6">
			<select name="type">
				<option value="${validation.valeurs['type']}" selected>${validation.valeurs['type']}</option>
				<c:forEach items="${Temps.values()}" var="temp">
					<option value="${temp}">${temp}</option>
				</c:forEach>
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
			<input id="date1" name="date1" type="text" class="datepicker"
				value="${validation.valeurs['date1']}"> <label for="date">Date Debut</label>
		</div>
		<c:if test="${validation.erreurs['date1'] != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['date1']}</div>
			</div>
		</c:if>

	</div>

	<div class="row">
		<div class="input-field col s6">
			<input id="date2" name="date2" type="text" class="datepicker"
				value="${validation.valeurs['date2']}"> <label for="date">Date Fin</label>
		</div>
		<c:if test="${validation.erreurs['date2'] != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['date2']}</div>
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


