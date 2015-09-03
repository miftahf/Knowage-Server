<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<link rel="stylesheet" href="/athena/themes/glossary/css/font-awesome-4.3.0/css/font-awesome.min.css">
	
	<!-- angular reference-->
	<script type="text/javascript" src="/athena/js/lib/angular/angular_1.4/angular.js"></script>
	<script type="text/javascript" src="/athena/js/lib/angular/angular_1.4/angular-animate.min.js"></script>
	<script type="text/javascript" src="/athena/js/lib/angular/angular_1.4/angular-aria.min.js"></script>

	<!-- angular-material-->
	<link rel="stylesheet" href="/athena/js/lib/angular/angular-material_0.10.0/angular-material.min.css">
	<script type="text/javascript" src="/athena/js/lib/angular/angular-material_0.10.0/angular-material.js"></script>
	
	<script type="text/javascript" src="/athena/js/src/angular_1.4/tools/commons/RestService.js"></script>
	<script type="text/javascript" src="/athena/js/src/angular_1.4/tools/federateddataset/federatedDataset.js"></script>
	<link rel="stylesheet" type="text/css" href="/athena/themes/federateddataset/css/federateddatasetStyle.css">
	<link rel="stylesheet" type="text/css" href="/athena/themes/glossary/css/generalStyle.css">

</head>
<body class="bodyStyle" ng-app="MYAPPNIKOLA">

	<div ng-controller="MyCRTL" layout="column"
		style="width: 100%; height: 100%; padding-bottom: 15px; padding-right: 15px; padding-left: 15px; padding-top: 15px;"
		class="contentdemoBasicUsage">
		<md-toolbar class="minihead"
			style="border-left: 2px solid grey; border-top: 2px solid grey; border-right: 2px solid grey; ">
		<div class="md-toolbar-tools">
			<i class="fa fa-bar-chart fa-2x"></i>
			<h2 class="md-flex" style="padding-left: 14px">DATASET FEDERATION</h2>
		</div>
		</md-toolbar>

		<md-content layout-padding=""
			style="height: 100%; padding: 20px;border:2px solid grey;"">
		<div ng-show="state" layout="row" layout-wrap>
			<div flex="49" style="margin-right: 20px; border: 2px solid grey;"">
				<md-toolbar class="minihead" style="border-bottom: 2px solid grey;">
				<div class="md-toolbar-tools">
					<i class="fa fa-list-alt fa-2x"></i>
					<h2 class="md-flex" style="padding-left: 14px">AVALIABLE DATASETS</h2>
				</div>
				</md-toolbar>
				<md-content style="height:700px;"> <md-list
					ng-repeat="k in list" style="border: 1px solid #ddd;">
				<md-list-item ng-click="moveToListNew(k)"> <i
					class="fa fa-angle-double-right fa-2x" style="padding-right: 5px"></i>
				{{k.label | uppercase}} </md-list-item> </md-list> </md-content>
			</div>

			<div flex="49" style="border: 2px solid grey;">
				<md-toolbar class="minihead" style="border-bottom: 2px solid grey;">
				<div class="md-toolbar-tools">
					<i class="fa fa-list-alt fa-2x"></i>
					<h2 class="md-flex" style="padding-left: 14px">SELECTED DATASETS</h2>
				</div>
				</md-toolbar>
				<md-content style="height:700px;"> <md-list
					ng-repeat="k in listaNew" style="border: 1px solid #ddd;">
				<md-list-item md-ink-ripple> <i
					class="fa fa-angle-double-right fa-2x" style="padding-right: 5px"></i>
				{{k.label | uppercase}} <span flex=""></span> <i class="fa fa-times"
					ng-click="kickOutFromListNew(k)"></i> </md-list-item> </md-list> </md-content>
			</div>
		</div>

		<div ng-hide="state">
			<md-toolbar class="minihead"
				style="border-left: 2px solid grey; border-top: 2px solid grey; border-right: 2px solid grey;">
			<div class="md-toolbar-tools">
				<h2 class="md-flex" style="padding-left: 14px">ASSOCIATIONS EDITOR</h2>
			</div>
			</md-toolbar>
			<md-content
				style=" padding: 5px; border: 2px solid grey; height:340px">
			<div ng-repeat="dataset in listaNew">
				<div style="width: 250px; float: left; padding: 5px;">
					<md-toolbar class="minihead"
						style="border-left: 2px solid grey; border-top: 2px solid grey; border-right: 2px solid grey;">
					<div class="md-toolbar-tools">
						<h2 class="md-flex">{{dataset.label | uppercase}}</h2>
					</div>
					</md-toolbar>
					<md-content style="border: 2px solid grey; height:300px;">
					<div ng-show="true">
						<md-list ng-repeat="field in dataset.metadata.fieldsMeta"
							ng-click="selektuj(field,dataset)"> <md-list-item
							md-ink-ripple ng-class="{prova : field.selected }">
							{{field.name}} </md-list-item> </md-list>
					</div>
					</md-content>
				</div>
			</div>
			</md-content>
		</div>

		<div ng-hide="state" style="padding-top: 5px">
			<md-toolbar class="minihead"
				style="border-left: 2px solid grey; border-top: 2px solid grey; border-right: 2px solid grey;">
			<div class="md-toolbar-tools">
				<h2 class="md-flex" style="padding-left: 14px">ASSOCIATIONS LIST</h2>
				<span flex=""></span> <i class="fa fa-plus-circle fa-2x"
					ng-click="createAssociations()"></i>

			</div>


			</md-toolbar>
			<md-content style="border: 2px solid grey; height:300px">

			<div style="padding: 10px">
				<md-toolbar class="minihead"
					style="border-left: 2px solid grey; border-top: 2px solid grey; border-right: 2px solid grey;">
				<div class="md-toolbar-tools">
					<h2 class="md-flex">Relations</h2>
				</div>
				</md-toolbar>
				<md-content style=" border: 2px solid grey; height:235px">
				<div>
					<md-list> <md-list-item style="list-style: none;"
						ng-repeat="k in associationArray"> {{k}} <span
						flex=""></span> <i class="fa fa-trash-o"
						ng-click="kickOutFromAssociatonArray(k)"></i> </md-list-item> </md-list>
				</div>

				</md-content>
			</md-content>
		</div>
	</div>

	<div ng-show="state">
		<md-button class="md-raised" aria-label="Aggiungi_Attributo"
			style=" margin-top: 20px; float:right;" ng-click="toggle()">NEXT
		STEP</md-button>
	</div>
	<div ng-hide="state">
		<md-button class="md-raised" aria-label="Aggiungi_Attributo"
			style=" margin-top: 20px;" ng-click="toggle()">BACK</md-button>
		<md-button class="md-raised" aria-label="Aggiungi_Attributo"
			style=" margin-top: 20px; float:right;"
			ng-click="showAdvanced($event)">SAVE federation</md-button>

	</div>
	</md-content>
	</div>

</body>
</html>
