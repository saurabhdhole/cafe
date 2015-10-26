var cafeApp = angular.module('cafeApp', ['ui.router','portalFrameControllers', 'portalOrderControllers','portalUserControllers','portalMiscControllers',
                                           'portalAuthServices','ui.bootstrap',
                                           'ui.bootstrap.showErrors','fetchUserService','foodService','reconilesrvc','orderhistorysrvc','currentOrdersrvc']);
cafeApp.constant('USER_ROLES', {
	super : 'SUPER',
	regular : 'REGULAR', 
});

cafeApp.constant('AUTH_PARAMS', {
    AUTH_TOKEN : 'X-AUTH-TOKEN',
});


cafeApp.constant('AUTH_EVENTS', {
	notAuthorized : 'auth-not-authorized'
});

cafeApp.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/app');

	$stateProvider

	// HOME STATE FOR Cafe-Frame PAGE ========================================
	.state('app', {
		url : '/app',
		templateUrl : 'src/partials/portal-frame.html',
		controller : 'frameCtrl',
	})
	// SUB PAGES WITH MULTIPLE VIEWS =================================

			.state('app.home', {
			url : '/home',
			templateUrl : 'src/partials/portal-home.html',
			controller : 'frameCtrl',
			})
			
			.state('app.user', {
			url : '/user',
			templateUrl : 'src/partials/portal-user.html',
			controller : 'userCtrl',
			})
			
			.state('app.items', {
			url : '/items',
			templateUrl : 'src/partials/portal-order.html',
			controller : 'orderCtrl',
			})
			
			
	.state('app.rechistory', {
		url : '/rechistory',
		templateUrl : 'src/partials/orderHistory.html',
		controller : 'orderHistoryCtrl',
		})
	
	.state('app.ReconcileDetail', {
		url : '/ReconcileDetail',
		templateUrl : 'src/partials/ReconcileDetail.html',
		controller : 'reconcilationCtrl',
		});
})

cafeApp.run(function($rootScope, authService, USER_ROLES, tokenStorage) {

});
