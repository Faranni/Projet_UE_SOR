<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


	<div class="section">
		<div id="chartjs" class="section">
			<h4 class="header">Line Chart</h4>
			<div class="row">
				<div class="col s12">				
					<form class="col s12" method="post" action="ServletStatistique" id="stat">
						<div class="row">
							<div class="input-field col s6">
								<select name="annee">
									<option value="" selected></option>
									<option value="2018">2018</option>							
								</select> 
								<label>Année111</label>
								<button class="btn waves-effect waves-light" type="submit"	name="actualiserStat">
									ok <i class="material-icons right">send</i>
								</button>
									
							</div>
								
						</div>
					</form>
					
				</div>
				<div>
					
				</div>
				<div class="col s6 ">
					<div class="sample-chart-wrapper">
						<canvas id="myChart" height="740" width="1854"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>


	<script>
		var ctx = document.getElementById("myChart").getContext('2d');
		var minimum = new Chart(ctx, {
			type : 'line',
			data : {
				labels : [ "Janvier", "Fevrier", "Mars", "Avril", "Mai",
						"Juin", "Juillet", "Aout", "Septembre", "Octobre",
						"Novembre", "Decembre" ],
				datasets : [ {
					label : 'min',
					data : [
						${list}
						],
					backgroundColor : [ 'rgba(51, 102, 255, 0.2)'],
					borderColor : [ 'rgba(51, 51, 255,1)'],
					borderWidth : 1
				} ]				
			}
		});
		
	</script>