'use strict';

/* Controllers */
var pAuthControllers = angular.module('portalOrderControllers', []);

pAuthControllers.controller('orderCtrl',
    ['$scope','$rootScope','foodsrvc','reconsrvc','corder',
    function($scope,$rootScope,foodsrvc,reconsrvc,corder) 
    {
    	    
    	$scope.showNavbar=function()
    	{
    		$rootScope.showNav=true;
    		foodsrvc.fetchOrderItemDetail(orderitemdetailSuccess,orderitemdetailFailure)
     	}
    	
    	function orderitemdetailSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	
        	console.log(data)
        	$scope.orderitem=data;
        	$scope.orid=data;
        	corder.fetchcurrentorder(sessionStorage.getItem("uid"),sessionStorage.getItem("orderid"),fetchCureentSuccess,fetchCureentFailure)
    	}
     
     	function orderitemdetailFailure(data,status,headers,config){
            angular.element('#ajaxLoading').hide();
     	}
     		
    	
    	$scope.checkOrder=function()
    	{
    		$("#viewo").modal("show");
    		foodsrvc.fetchOderAndUser(sessionStorage.getItem("orderid"),orderdetailSuccess,orderdetailFailure)
    	}	
    	
    	$scope.addQty=function()
    	{
  		$("#buyitem").modal("show");
    	}	
    	
    	//this controller is for fetch user and orderdetail
    
        function orderdetailSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	$scope.OAndUDetail=data;
    	}		
     		
        function orderdetailFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        }
    	
        $scope.adduopqty=function()
        {
        	foodsrvc.addOrderItemDetail(sessionStorage.getItem("uid"),sessionStorage.getItem("iid"),$scope.qty,sessionStorage.getItem("orderid"),createOrderQtydetailSuccess,createOrderQtydetailFailure)
        }
        
        function createOrderQtydetailSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	corder.fetchcurrentorder(sessionStorage.getItem("uid"),sessionStorage.getItem("orderid"),fetchCureentSuccess,fetchCureentFailure)
        	$scope.qty="";
    	}		
        
        //this is methode for current selected order
 
    	function fetchCureentSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	$scope.cdata=data;
        	console.log(data)
        	$scope.i=0;
        	$scope.qt = 0;
        	$scope.tot=0;
        	for(var i=0; i<$scope.cdata.length; i++)
        	{
        	//$scope.qtyx += $scope.qty + parseInt($scope.cdata[i].qty);
        	$scope.tot=parseInt($scope.cdata[i].qty)*parseInt($scope.cdata[i].price)+parseInt($scope.tot);
        	}
        	   	
    	}
        
        function  fetchCureentFailure(data,status,headers,config){
           angular.element('#ajaxLoading').hide();
     	}
     		
        function createOrderQtydetailFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        }
        
        $scope.calculateReconcile=function()
    	{
    		if($scope.amtpaid <= 0 || $scope.amtpaid == undefined){
    			$scope.validAmount = true;
    			return;
    		}
    		reconsrvc.addReconcile(sessionStorage.getItem("orderid"),sessionStorage.getItem("uid"),$scope.amtpaid,addReconciledetailSuccess,addReconcildetailFailure)
    	}
    	
    	function addReconciledetailSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	console.log(data)
        	sweetAlert("Order Processed..");
 		}
 		
 		function addReconcildetailFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
 		}
 		
 		$scope.mouseUp=function()
 		{
 			 $scope.scrollup=setInterval(function()
 					{
 	 			var a=document.getElementById("i").scrollTop-=6;
 				console.log(a)		 
 					 },15)
 		}
 		
 		$scope.mouseDown=function()
 		{
 			 $scope.scrollup=setInterval(function()
 					 {
 	 			var a=document.getElementById("i").scrollTop+=3;
 				console.log(a)		 
 					 },15)
 		}
 		
 		$scope.clearintervals=function()
 		{
 			clearInterval($scope.scrollup);
 			clearInterval($scope.scrolldown);
 		}
 		

 		//this function is for order Item
 		$scope.mouseUp1=function()
 		{
 			 $scope.scrollup=setInterval(function()
 					 {
 	 			var a=document.getElementById("o").scrollTop-=6;
 				console.log(a)		 
 					 },15)
 		}
 		
 		$scope.mouseDown1=function()
 		{
 			 $scope.scrollup=setInterval(function()
 					 {
 	 			var a=document.getElementById("o").scrollTop+=3;
 				console.log(a)		 
 					 },15)
 		}
 		
		$scope.clearintervals1=function()
 		{
 			clearInterval($scope.scrollup);
 			clearInterval($scope.scrolldown);
 		}

 		$scope.addOrderItem=function()
 		{
 			foodsrvc.addItem($scope.itemname,$scope.itemprice,addItemSuccess,addItemFailure);
 		}
 		
 		function addItemSuccess(data,status,headers,config)
 		{
 	        angular.element('#ajaxLoading').hide();
 	        $scope.itemprice="";
 	        $scope.itemname="";
 	        foodsrvc.fetchOrderItemDetail(orderitemdetailSuccess,orderitemdetailFailure)
 	    }		
 	    	
 	    function addItemFailure(data,status,headers,config){
 	        angular.element('#ajaxLoading').hide();
 	    }

 	   $scope.addOrderItemdetail=function()
       {
       	$("#additem").modal("show");
       }
 	   
    }]);



