var currentOrdersrvc = angular.module('currentOrdersrvc', []);

currentOrdersrvc.factory('corder',['$http',  function($http) {
	var corder={};
	var urs="http://localhost:8080/foms/orderItem/oid/";
//	var urr="http://localhost:8080/foms/reconcile/oid/";


//this function service is for fetch order detail
	corder.fetchcurrentorder=function(uid,oid,successFn,errorFn) {
		
	

		var request = {
             method: 'GET',
             url: urs+oid+"/uid/"+uid
          
          
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
	
	
	
		
	  return corder; 
  	
	
}
]);
