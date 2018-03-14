<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<ul class="collection">
	<c:forEach var="entry" items="${mapMeteo}">
		<li class="collection-item avatar">
			<div class="row">
				<div class="col s4">
					<c:if test="${entry.value.isEmpty()}">
						<i class="material-icons circle green">insert_chart</i>
					</c:if>
					<c:if test="${! entry.value.isEmpty()}">
						<i class="material-icons circle green">broken_image</i>
						<!--  <img src="${pageContext.servletContext.contextPath}/ServletImage?tab=${ entry.value.get(0).image }" /> -->
					</c:if>
					
				</div>
				<div class="col s4">
					<p>
						<span class="title">${ entry.key.lieu }</span>
						<br>${ entry.key.date }<br> ${ entry.key.temps }
					</p>
				</div>
				<div class="col s2">
					<p>
						${ entry.key.min }<br> ${ entry.key.max }<br> ${ entry.key.moy }
					</p>
				</div>
				<div class="col s1">
					<form action="ServletConsultation" method="post">
						<input type="hidden" value="${entry.key.idMeteo}" name="idMeteo">
						<button type="submit" name="editer">
							<i class="material-icons">edit</i>
						</button>
					</form>
				</div>
				<div class="col s1">
					<form action="ServletConsultation" method="post">
						<input type="hidden" value="${entry.key.idMeteo}" name="idMeteo">
						<button type="submit" name="supprimer">
							<i class="material-icons">delete</i>
						</button>
					</form>
				</div>
			</div>

		</li>
	</c:forEach>
</ul>





