<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<ul class="collection">
	<c:forEach items="${meteos}" var="meteo">

		<li class="collection-item avatar"><i
			class="material-icons circle green">insert_chart</i> <span
			class="title"><c:out value="${meteo.lieu}" /></span>
			<p>
				<c:out value="${meteo.temps}" />
				<br>
				<c:out value="${meteo.date}" />
			</p> <c:if test="${ manager.identifier }">

				<form action="ServletConsultation" method="post">
					<input type="hidden" value="${meteo.lieu}" name="lieu"> <input
						type="hidden" value="${meteo.temps}" name="type"> <input
						type="hidden" value="${meteo.date}" name="date">
					<button class="secondary-content" type="submit" name="supprimer">
						<i class="material-icons">delete</i>
					</button>
				</form>
			</c:if></li>
	</c:forEach>


</ul>