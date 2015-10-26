'use strict';

/* Controllers */
var pAuthControllers = angular.module('portalMiscControllers', []);

	pAuthControllers.controller('orderHistoryCtrl',
    ['$scope','$rootScope','orderHsrvc',
    function($scope,$rootScope,orderHsrvc) 
    {
    	$scope.showNavbar=function()
    	{
    		$rootScope.showNav=true;
    	}
    	$scope.mouseUp=function()
 		{
 			$scope.scrollup=true;
 		
 			 $scope.scrollup=setInterval(function()
 					 {
 	 			var a=document.getElementById("hisList").scrollTop-=3;
 					 },20)
 	
 		}
 		
 		$scope.mouseDown=function()
 		{
 			$scope.scrolldown=true;
 			 $scope.scrollup=setInterval(function()
 					 {

 	 			var a=document.getElementById("hisList").scrollTop+=3;
 					 },20)
 		}
    
 		$scope.clearintervals=function()
 		{
 			clearInterval($scope.scrollup);
 			clearInterval($scope.scrolldown);
 		}
    	$scope.getOrderHistory=function()
    	{
         	$scope.searchDate=new Date(document.getElementById("startdate").value);
        
    		orderHsrvc.viewHistory($scope.searchDate,orderHistorySuccess,orderHistoryFailure)
    		
    		function orderHistorySuccess(data,status,headers,config){
            	angular.element('#ajaxLoading').hide();
            	
            	console.log(data)
            	$scope.hshow=true;
            	$scope.orderHitem=data;
            }
     		function orderHistoryFailure(data,status,headers,config){
            	angular.element('#ajaxLoading').hide();
            	$scope.hshow=false;
     		}
    	}
    	
    }]);


	pAuthControllers.controller('reconcilationCtrl',
	['$scope','$rootScope','reconsrvc',
	function($scope,$rootScope,reconsrvc) 
	{
		$scope.showList=function()
		{
    	$rootScope.showNav=true;
		reconsrvc.fetchReconcile($scope.recOrder,ReconciledetailSuccess,ReconciledetailFailure);
		}
		$scope.checkRec=function(status)
		{
			if(status=="Closed")
				return false;
			else
				return true;
		}
		
		$scope.changeStatus=function(userid,orderid)
		{
			reconsrvc.clearStatus(userid,orderid,clearStatusSuccess,clearStatusFailure);
		}
		function clearStatusSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	reconsrvc.fetchReconcile($scope.recOrder,ReconciledetailSuccess,ReconciledetailFailure);
        	
        }
    	
        function clearStatusFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        }
    	function ReconciledetailSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	$scope.recol=data;
        	if($scope.recol[0].status=="Closed")
        		$scope.dispRec=false;
        	else
        		$scope.dispRec=true;
        }
    	
        function ReconciledetailFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        }
		
        //move up and down
        $scope.mouseUp=function()
 		{
 			$scope.scrollup=true;
 		
 			 $scope.scrollup=setInterval(function()
 					 {
 	 			var a=document.getElementById("recList").scrollTop-=3;
 					 },20)
 	
 		}
 		
 		$scope.mouseDown=function()
 		{
 			$scope.scrolldown=true;
 			 $scope.scrollup=setInterval(function()
 					 {

 	 			var a=document.getElementById("recList").scrollTop+=3;
 					 },20)
 		}
    
 		$scope.clearintervals=function()
 		{
 			clearInterval($scope.scrollup);
 			clearInterval($scope.scrolldown);
 		}
        
        
	}]);


