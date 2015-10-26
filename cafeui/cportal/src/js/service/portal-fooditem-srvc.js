var foodService = angular.module('foodService', []);

foodService.factory('foodsrvc',['$http',  function($http) {
	var foodsrvc={};
	var urs="http://localhost:8080/foms/fooditem";
	var torder="http://localhost:8080/foms/ordertoday";
	var toname="http://localhost:8080/foms/order/checkOrderName";
	
	var urlo="http://localhost:8080/foms/orderItem";
	
	var urss="http://localhost:8080/foms/orderItem/orderid/";

//this function service is for fetch order detail
	foodsrvc.fetchOrderItemDetail=function(successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: urs,
          
		}
		
		 console.log(request);
	     $http(request)	         
	     .success(function(data, status, headers, config){
	         console.log("data fetch successfully");
	         
	         successFn(data,status,headers,config);
	     })
	     .error(function(data, status, headers, config){
	         console.log("Data is not fetched");
	         errorFn(data,status,headers,config);
	     });
		
		
	};
	
	
foodsrvc.fetchOderAndUser=function(oid,successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: urss+sessionStorage.getItem("orderid")
          
		}
		
		 console.log(request);
	     $http(request)	         
	     .success(function(data, status, headers, config){
	         console.log("data fetch successfully");
	         
	         successFn(data,status,headers,config);
	     })
	     .error(function(data, status, headers, config){
	         console.log("Data is not fetched");
	         errorFn(data,status,headers,config);
	     });
		
		
	};
	
	
//this service for fetch current day order 
	//////@prev day report
foodsrvc.fetchPrevOrderdDetail=function(successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: torder,
          
		}
		
		 console.log(request);
	     $http(request)	         
	     .success(function(data, status, headers, config){
	         console.log("data fetch successfully");
	         
	         successFn(data,status,headers,config);
	     })
	     .error(function(data, status, headers, config){
	         console.log("Data is not fetched");
	         errorFn(data,status,headers,config);
	     });
		
		
	};
	
	
	

	foodsrvc.addOrderItemDetail=function(uid,iid,qty,oid,successFn,errorFn) {
		
		
		
		var request = {
	             method: 'POST',
	             url: urlo,
	             data:{
	            	 "uid":uid,
	            	 "item_id":iid,
	            	 "qty":qty,
	            	 "oid":oid
	             }
	          
			}
			
			 console.log(request);
		     $http(request)	         
		     .success(function(data, status, headers, config){
		         console.log("data insert successfully");
		         
		         successFn(data,status,headers,config);
		     })
		     .error(function(data, status, headers, config){
		         console.log("Data is not insert");
		         errorFn(data,status,headers,config);
		     });


	
	
	
	};	
	
	
	//this service is for addItem
	
	foodsrvc.addItem=function(itemname,price,successFn,errorFn)
	{
		

		var request = {
	             method: 'POST',
	             url: urs,
	             data:{
	            	 "name":itemname,
	            	 "price":price,
	             }
	          
			}
		
		 console.log(request);
	     $http(request)	         
	     .success(function(data, status, headers, config){
	         console.log("data insert successfully");
	         
	         successFn(data,status,headers,config);
	     })
	     .error(function(data, status, headers, config){
	         console.log("Data is not insert");
	         errorFn(data,status,headers,config);
	     });
		
		
	};
	
	
	
	//for current name
foodsrvc.fetchcurrentordernm=function(successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: toname,
          
		}
		
		 console.log(request);
	     $http(request)	         
	     .success(function(data, status, headers, config){
	         console.log("data fetch successfully");
	         
	         successFn(data,status,headers,config);
	     })
	     .error(function(data, status, headers, config){
	         console.log("Data is not fetched");
	         errorFn(data,status,headers,config);
	     });
		
		
	};
	
	
	
	
	  return foodsrvc; 
  	
	
}]);
