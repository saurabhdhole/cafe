var reconilesrvc = angular.module('reconilesrvc', []);

reconilesrvc.factory('reconsrvc',['$http',  function($http) {
	var reconsrvc={};
	var urs="http://localhost:8080/foms/reconcile/orderid/";
	var urr="http://localhost:8080/foms/reconcile/oname/";
	var urst="http://localhost:8080/foms/reconcile/status/";


//this function service is for fetch order detail
	reconsrvc.addReconcile=function(oid,uid,amtpaid,successFn,errorFn) {
		
		var request = {
             method: 'POST',
             url: urs+oid+"/uid/"+uid,
             data:{
            	 "userid":uid,
            	 "orderid":oid,
            	 "amtpaid":amtpaid
             }
          
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
	
      reconsrvc.fetchReconcile=function(oname,successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: urr+oname,
          
          
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
	
	reconsrvc.clearStatus=function(userid,orderid,successFn,errorFn) {
		
		var request = {
             method: 'GET',
             url: urst+userid+"/"+orderid,
          
          
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
	
	
	
		
	  return reconsrvc; 
  	
	
}]);
