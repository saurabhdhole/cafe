'use strict';

/* Controllers */
var pAuthControllers = angular.module('portalUserControllers', []);

pAuthControllers.controller('userCtrl',
    ['$scope','$rootScope','fetchusersrvc','foodsrvc',
    function($scope,$rootScope,fetchusersrvc,foodsrvc) 
    {
    	    
    	$scope.getUsersList=function()
    	{
    		fetchusersrvc.fetchUserNames(userListSuccess,userListFailure)
    	}
    	
    	function userListSuccess(data,status,headers,config){
        	$scope.users=data;
        	foodsrvc.fetchPrevOrderdDetail(todayOrderdetailSuccess,todayOrderdetailFailure)     	
    	}		
   		
        function userListFailure(data,status,headers,config){
        	// Error functions 
        }
        
        
    	$scope.addUser=function()
    	{
    		fetchusersrvc.addUserName($scope.uname,userAddSuccess,userAddFailure)
    	}
    	
     	$scope.showAddUserModal=function()
    	{
    		$("#myModal").modal("show")
    	}	
    	

    	function userAddSuccess(data,status,headers,config){
        	$scope.users=data;
        	sweetAlert("User Added..");
        	$scope.uname="";
        	
       		fetchusersrvc.fetchUserNames(userListSuccess,userListFailure)
       		$scope.a=0;
       		
              	
        }
    	
        function userAddFailure(data,status,headers,config){
        }
        
 		$scope.mouseUp=function()
 		{
 			$scope.scrollup=true;
 		
 			 $scope.scrollup=setInterval(function()
 					 {
 	 			var a=document.getElementById("hest").scrollTop-=3;
 					 },20)
 	
 		}
 		
 		$scope.mouseDown=function()
 		{
 			$scope.scrolldown=true;
 			 $scope.scrollup=setInterval(function()
 					 {

 	 			var a=document.getElementById("hest").scrollTop+=3;
 					 },20)
 		}
 		
 		
 		$scope.clearintervals=function()
 		{
 			clearInterval($scope.scrollup);
 			clearInterval($scope.scrolldown);
 		}
 		
 	    $scope.gotohome=function()
        {
        	sessionStorage.setItem("uid",null)
        	sessionStorage.setItem("orderid",null)
        	location.href="#/app/home" 
        	
        }
        
 	    //on click fuction to select the order from radio button
 	    $scope.selectRadio=function()
 	    {
 	    	var CurrOrderId = document.getElementsByName('Forder');
        	for (var i = 0, length = CurrOrderId.length; i < length; i++) 
        	{
        		$rootScope.showSelectItem="false";
        	    if (CurrOrderId[i].checked) {
        	        // do whatever you want with the checked radio
        	       // alert(CurrOrderId[i].value);
        	    	if(CurrOrderId[i].value=="Lunch")
        	    		sessionStorage.setItem("orderid",$scope.orderIdL);
        	    	if(CurrOrderId[i].value=="Breakfast")
        	    		sessionStorage.setItem("orderid",$scope.orderIdB);
        	    	if(CurrOrderId[i].value=="Dinner")
        	    		sessionStorage.setItem("orderid",$scope.orderIdD);
        	    	//for enable select button
        	    	$rootScope.showSelectItem="true";
        	    	
        	        // only one radio can be logically checked, don't check the rest
        	        break;
        	    }
        	}
  
 	    }
 	    
 	    
 	    
 		function todayOrderdetailSuccess(data,status,headers,config)
 		{
    		angular.element('#ajaxLoading').hide();
    		$scope.prevOrder=data;
    		if($scope.prevOrder.ordername=="recExist")
    		{
    			$scope.orderExists=false;
    			$scope.orderCurrExists=false;
    			$scope.prevExist=true;
    			$scope.doneView=false;
    		}	
    		if($scope.prevOrder.ordername=="done")
    		{
    			$scope.orderExists=false;
    			$scope.orderCurrExists=false;
    			$scope.prevExist=false;
    			$scope.doneView=true;
    		}
    		if($scope.prevOrder.ordername=="recClear")
    		{
    			$scope.orderExists=false;
    			$scope.prevExist=false;
    			$scope.doneView=false;
    			foodsrvc.fetchcurrentordernm(OrderNameSuccess,OrderNameFailure)
    		}
    		
    	}
    	
        function todayOrderdetailFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        }
        ///currentordername success error fn
        function OrderNameSuccess(data,status,headers,config){
        	console.log(data);
        	$scope.ord=data;
        	$scope.OrderName=$scope.ord.ordername;
        	if($scope.OrderName=="Noexist")
        	{
        		//$scope.ord=data;
        		//console.log(data);
        			$scope.orderExists=true;

        		//session
            
        	}
        	else
        	{
        		if(data!=null && data!="")
        		{
        			$scope.orderExists=false;
        			$scope.orderCurrExists=true;
        			$scope.orderIdL=$scope.ord.orderidL;
                	$scope.orderIdB=$scope.ord.orderidB;
                	$scope.orderIdD=$scope.ord.orderidD;
        		}
        		$scope.orderdate= new Date($scope.ord.orderdate);
            	$scope.year=$scope.orderdate.getFullYear();
            	$scope.month=$scope.orderdate.getMonth()+1;
            	$scope.day=$scope.orderdate.getDate();	
            	$scope.hr=$scope.orderdate.getHours();
            	$scope.minit=$scope.orderdate.getMinutes();

        	//	sessionStorage.setItem("orderid",$scope.ord.orderid);
        		//set all to false
        		$scope.cOrderB=false;
        		$scope.cOrderL=false;
        		$scope.cOrderD=false;
        		$scope.cOrderLB=false;
        		$scope.cOrderBD=false;
        		$scope.cOrderLD=false;
        		$scope.cOrderLBD=false;
        		$scope.radioL=false;
        		$scope.radioB=false;
        		$scope.radioD=false;
        		
        	if($scope.ord.lunchrec=="false")
            	$scope.radioL=true;
        	if($scope.ord.breakfastrec=="false")
            	$scope.radioB=true;
        	if($scope.ord.dinnerrec=="false")
            	$scope.radioD=true;
            
        	if($scope.OrderName=="Breakfast")
        	{
        		$scope.cOrderB=true;    	
        	}
        	if($scope.OrderName=="Lunch")
        	{
        		$scope.cOrderL=true;
        	}
        	if($scope.OrderName=="Dinner")
        	{
        		$scope.cOrderD=true;
        	}
        	if($scope.OrderName=="orderLB")
        	{
        		$scope.cOrderLB=true;
        	}
        	if($scope.OrderName=="orderBD")
        	{
        		$scope.cOrderBD=true;
        	}
        	if($scope.OrderName=="orderLD")
        	{
        		$scope.cOrderLD=true;
        	}
        	if($scope.OrderName=="orderLBD")
        	{
        		$scope.cOrderLBD=true;
        	}
        	}
    	}		
   		
        function OrderNameFailure(data,status,headers,config){
        	// Error functions 
        }
        
        $scope.addOrder=function()
    	{
    		fetchusersrvc.addOrderName($scope.oname,orderdetailSuccess,orderdetailFailure)
    	}
        
        function orderdetailSuccess(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        	$scope.orders="Order Added";
        	$scope.odata=data;
//        	$scope.orderIdL=$scope.odata.orderidL;
//        	$scope.orderIdB=$scope.odata.orderidB;
//        	$scope.orderIdD=$scope.odata.orderidD;
        	
        	$scope.odata.orderdate= new Date($scope.odata.orderdate);
        	$scope.times=$scope.odata.orderdate.getTime();
        	$rootScope.showSelectItem="false";
        	$scope.orderExists=true;
        	
        	
        	//sessionStorage.setItem("orderid",$scope.odata.id);
        	
        	foodsrvc.fetchPrevOrderdDetail(todayOrderdetailSuccess,todayOrderdetailFailure)
        	//location.href = "#/login";        	
        }
    	
        function orderdetailFailure(data,status,headers,config){
        	angular.element('#ajaxLoading').hide();
        }
    	
        
        
    }]);



