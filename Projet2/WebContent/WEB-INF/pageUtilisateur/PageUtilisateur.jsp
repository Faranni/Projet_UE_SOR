<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
	<div class="col s12 m6">
		<div class="card blue-grey darken-1">
			<div class="card-content white-text">
				<span class="card-title">${ manager.utilisateur }</span>
				<p>${ manager.utilisateur.email }</p>
				<p>${ manager.utilisateur.password }</p>
			</div>
		</div>
	</div>
</div>