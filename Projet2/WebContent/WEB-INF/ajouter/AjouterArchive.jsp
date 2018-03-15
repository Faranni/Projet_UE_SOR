<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<br>
<form class="col s12" method="post" action="ServletAjouterArchive"
	enctype="multipart/form-data">

	<div class="row">
		<div class="input-field col s6">
			<div class="file-field input-field">
				<div class="btn">
					<span>File</span> <input type="file" name="archiveFile">
				</div>
				<div class="file-path-wrapper">
					<input class="file-path validate" type="text" name="archiveName">
				</div>
			</div>
		</div>
		<c:if test="${validation.erreurs['archiveName']  != null}">
			<div class="input-field col s6">
				<div class="card-panel red lighten-2">${validation.erreurs['archiveName']}</div>
			</div>
		</c:if>
	</div>

	<button class="btn waves-effect waves-light" type="submit"
		name="ajouterArchive">
		Envoyer <i class="material-icons right">send</i>
	</button>


</form>

<div class="row">
	<c:if test="${ erreur  != null }">
		<div class="input-field col s12">
			<div class="card-panel red lighten-2">${erreur}</div>
		</div>
	</c:if>
</div>
<br>