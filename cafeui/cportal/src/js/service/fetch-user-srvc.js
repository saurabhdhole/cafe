var fetchUserService = angular.module('fetchUserService', []);

fetchUserService .factory('fetchusersrvc',['$http',  function($http) {
	var fetchusersrvc={};
	var ur="http://localhost:8080/foms/user";
	var urs="http://localhost:8080/foms/order";

	fetchusersrvc.fetchUserNames=function(successFn,errorFn) {
	 	
		var request = {
	            method: 'GET',
	            url: ur
	        }
		
	     console.log(request);
	     $http(request)	         
	     .success(function(data, status, headers, config){
	         console.log("data fetched successfully");
	         
	         successFn(data,status,headers,config);
	     })
	     .error(function(data, status, headers, config){
	         console.log("Data is not fetched");
	         errorFn(data,status,headers,config);
	     });
	     
	     
	    
	};
		
	
	fetchusersrvc.addUserName=function(uname,successFn,errorFn) {
		
		var request = {
             method: 'post',
             url: ur,
             data:{
            	 "name":uname
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
	
	

	//this function service is for add order name
	
	
	fetchusersrvc.addOrderName=function(oname,successFn,errorFn) {
		
		var request = {
             method: 'post',
             url: urs,
             data:{
            	 "name":oname
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
	
			
	 return fetchusersrvc;	
		
		
	}]);

	

                  

					
                          