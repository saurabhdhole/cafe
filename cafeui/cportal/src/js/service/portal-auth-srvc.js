'use strict';

/* Basic Services */
var adminAuthServices = angular.module('portalAuthServices', ['ngResource']);

adminAuthServices.factory('tokenStorage', ['$window', 'AUTH_PARAMS', function($window, AUTH_PARAMS) {

    var storageKey = AUTH_PARAMS.AUTH_TOKEN;
    console.log(storageKey);


    return {
        store : function(authToken) {
            console.log("Storing the authToken token on the local storage =  " + authToken);
            return $window.localStorage.setItem(storageKey, authToken);
        },
        retrieve : function() {
            console.log("Fetching the authToken token on the local storage ");
            return $window.localStorage.getItem(storageKey);
        },
        clear : function() {
            console.log("Removing the authToken token from the local storage");
            return $window.localStorage.removeItem(storageKey);
        }

	};
}]);

/**
 * We should on each request/response add the Auth_TOKEN as a header and
 */
adminAuthServices.factory('tokenAuthInterceptor',
    ['$rootScope', '$q','tokenStorage', 'AUTH_EVENTS', 'AUTH_PARAMS',
    function($rootScope, $q, tokenStorage, AUTH_EVENTS, AUTH_PARAMS) {
        return {
            request : function(config) {
                
                var authToken = tokenStorage.retrieve();
                if (authToken) {
                    console.log("Setting the authToken on the request = " + authToken);
                    config.headers[AUTH_PARAMS.AUTH_TOKEN] = authToken;
                }

                //Always add the AUTH_PARAMS.API_REQUEST_CHANNEL_HEADER on every request
                config.headers[AUTH_PARAMS.API_REQUEST_CHANNEL_HEADER_NAME] = AUTH_PARAMS.API_REQUEST_CHANNEL_ADMIN;

                return config;
            },

            response : function(req) {

                //angular.element('#ajaxLoading').hide();
            	// TODO might have to fetch new token on each request.
                	return req;
            },

            responseError : function(error) {

                //angular.element('#ajaxLoading').hide();

                if(error.status == 401){
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }

                return $q.reject(error);
            }
        };
    }]);

adminAuthServices.config(function($httpProvider) {
	
	$httpProvider.defaults.headers.common["Cache-Control"] = "no-cache";
    $httpProvider.defaults.headers.common.Pragma = "no-cache";
//    $httpProvider.defaults.headers.common["If-Modified-Since"] = "0";


    $httpProvider.interceptors.push('tokenAuthInterceptor');
});

adminAuthServices.factory('authService',
    ['$http', 'tokenStorage', 'AUTH_PARAMS', '$timeout',
    function($http, tokenStorage, AUTH_PARAMS, $timeout) {
        var authService = {};
        var authenticated = false;
        var token;
        
        var baseUrl = "http://localhost:8080/cafe/";        
        
        authService.login = function(username, password, successFn, errorFn) {

            console.log(("Starting the admin login request process"));

            var completeUrl;
            if((username !== undefined) && (username !== null)){
                completeUrl = baseUrl + 'login';
            }else{
                console.log("Login will fail since userId is null or undefined");
            }

            //we are sending out a login request.
            var existingAuthToken = tokenStorage.retrieve();
            if(existingAuthToken){
                console.log("Deleting existing authToken header");
                tokenStorage.clear();
            }

            var request = {
                method: 'POST',
                url: completeUrl,
                data: {
                    "username":username,
                    "password": password
                }
            };


            var httReturnPromise = $http(request);

            httReturnPromise.success(function(data, status, headers, config){

                $timeout(function(){

                    console.log("Admin Login response success status = " + status);

                    authenticated = true;

                    var authToken = headers(AUTH_PARAMS.AUTH_TOKEN);
                    if(authToken){
//                        console.log("authToken token returned on the response =  " + authToken);
                        tokenStorage.store(authToken);
                    }
               successFn(data,status,headers,config, authToken);

                }, 100, true);
            });

            httReturnPromise.error(function(data, status, headers, config){
                console.log("Admin Login response failed status = " + status);
                authenticated = false;
                tokenStorage.clear();

                errorFn(data,status,headers,config);
            });

        };
        
        

        authService.getUserDetails = function(adminId) {
            console.log("fetching Logged User Details.");
            var completeUrl;
            if((adminId == undefined) || (adminId == null))
            console.log("Unable to fetch User Details since admin Id is Null.");
            else
            {
                completeUrl = baseUrl + 'adminuser/' + adminId;
                var request = {
                        method: 'GET',
                        url: completeUrl
                    };
               $http(request)
               .success(function(data, status, headers, config){
            	   localStorage.setItem("LoggedUser",JSON.stringify(data))
               })
               .error(function(data, status, headers, config){
            	   console.log(data)
               });
            }
        };

        authService.logout = function(adminId, successFn, errorFn) {

            console.log(("Starting the admin logout request process"));

            //logout Url should be on the format: baseUrl +  'users/{{userId}}/logout'
            var completeUrl;
            if((adminId !== undefined) && (adminId !== null)){
                completeUrl = baseUrl + 'users/' + adminId + '/logout';
            }else{
                console.log("Logout will fail since userId is null or undefined");
            }

            var request = {
                method: 'POST',
                url: completeUrl
            };

            var httReturnPromise = $http(request);

            httReturnPromise.success(function(data, status, headers, config){

                console.log("Admin Logout response success status = " + status);
                authenticated = false;

                //Clear out any Auth token.
                //we successfully logged out.
                var existingAuthToken = tokenStorage.retrieve();
                if(existingAuthToken){
                    console.log("Deleting existing authToken header");
                    tokenStorage.clear();
                }

                successFn(data,status,headers,config);

            });

            httReturnPromise.error(function(data, status, headers, config){
                console.log("Logout response failed status = " + status);
                authenticated = true;

                errorFn(data,status,headers,config);
            });


        };

        authService.isAuthenticated = function() {
            return authenticated;
        };

        /*
         Here we need to check that:

         1: The Auth TOKEN token is set on browser
         2: The merchant has the required USER_ROLE
         3: The merchant has the required permissions USER_PERMISSION to access this area of the portal.
         */
        
        authService.isAuthorized = function(authorizedRoles) {

            var authToken = tokenStorage.retrieve();
            if((authToken === undefined) || (authToken === null)) {
                console.log("Could not find the auth token on the local storage");
                authenticated = false;
                return false;
            }

            if (!(angular.isUndefined(authToken))) {
                authenticated = true;
            }

            return authenticated;
             };

       
        authService.getToken = function() {
            var token = tokenStorage.retrieve();
            if(token){
                return token;
            }else{
                return null;
            }

        };

        return authService;
    }]);
