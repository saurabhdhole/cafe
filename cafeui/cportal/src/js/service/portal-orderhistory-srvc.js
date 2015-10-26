var orderhistorysrvc = angular.module('orderhistorysrvc', []);

orderhistorysrvc.factory('orderHsrvc',['$http',  function($http) {
	var orderHsrvc={};
	var urs="http://localhost:8080/foms/orderItem/date/";
//	var urr="http://localhost:8080/foms/reconcile/oid/";


//this function service is for fetch order detail
	orderHsrvc.viewHistory=function(date,successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: urs+date
          
          
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
	
	
	
		
	  return orderHsrvc; 
  	
	
}]);
