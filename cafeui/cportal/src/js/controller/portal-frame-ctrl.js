'use strict';

/* Controllers */
var pAuthControllers = angular.module('portalFrameControllers', []);

pAuthControllers.controller('frameCtrl',
    ['$scope','$rootScope','$state','fetchusersrvc','foodsrvc','reconsrvc','orderHsrvc',
    function($scope,$rootScope,$state,fetchusersrvc,foodsrvc,reconsrvc,orderHsrvc) {
		
    	$scope.$broadcast('show-errors-reset');
   
    	$rootScope.showNavbar=function()
    	{
    		$rootScope.showNav=true;
    	}
    	
    	$scope.hideNavbar=function()
    	{
    		$rootScope.showNav=false;
    	}
    	
    	$scope.goToUsers=function()
    	{
    		$rootScope.showNav=true;
    		var masker = document.getElementById("bgmasker");
    		masker.classList.add("blurout");
    		sessionStorage.setItem("orderid","null");
    		sessionStorage.setItem("uid","null");
    		location.href="#/app/user"
    	}
    	
    	$scope.showReconcilations=function()
 		{
 			location.href="#/app/ReconcileDetail" 
 		}
    	
        $scope.viewhistory=function()
    	{
        	location.href="#/app/rechistory"
    	}
    	
    	$scope.goToHome=function()
    	{
    		location.href="#/app/home"
    	}
    	
    	$scope.goToOrderItems=function()
    	{
    		location.href="#/app/items"
    	}
    	
        //this methode id for retrive user id from the ui
        $scope.fetchUserId=function(id)
        {
        	$(document).ready(function(){
        	       
        	   if(sessionStorage.getItem("uid")==null)
        	   {	   
        	
        		   $("#co"+id).css("color","red");
        		   sessionStorage.setItem("uid",id);
        		  
        	   }
        	   else
        	   {
        		   $("#co"+sessionStorage.getItem("uid")).css("color","black");
        		   $("#co"+id).css("color","red");
        		   sessionStorage.setItem("uid",id);
        	
        	   }
        	});	
        }
        
        $scope.fetchItemid=function(id)
        {
        	sessionStorage.setItem("iid",id);
        }
        
        $scope.checkOrderAndUser=function()
        {
//        	var CurrOrderId = document.getElementsByName('Forder');
//        	for (var i = 0, length = CurrOrderId.length; i < length; i++) 
//        	{
//        	    if (CurrOrderId[i].checked) {
//        	        // do whatever you want with the checked radio
//        	        alert(CurrOrderId[i].value);
//
//        	        // only one radio can be logically checked, don't check the rest
//        	        break;
//        	    }
//        	}
     		if(sessionStorage.getItem("uid")!="undefined" && sessionStorage.getItem("uid")!="null" && sessionStorage.getItem("orderid")!="null" && sessionStorage.getItem("orderid")!="undefined" && $rootScope.showSelectItem=="true")
     			return true;
     		else
     			return false;
        }
        
        $scope.enableOnOrder=function()
        {
        	if($state.current.name=="app.user")
        		{
        		return true;
        		}
        		else
        		{
        		return false;
        		}
        }
        
        $scope.enableFoodItem=function()
        {
        	if($state.current.name=="app.items")
        		{
        		return true;
        		}
        		else
        		{
        		return false;
        		}
        }
        
 		$scope.gotoBack=function()
 		{
 			location.href="#/app/user" 
 		}
        
    }]);



