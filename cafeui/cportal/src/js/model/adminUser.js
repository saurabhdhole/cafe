/**
 * Merchant Model.js
 **/

var AdminUser = function () {
    // Constructor, sets the defaults values of the object
    var adminUser = function (attributes) {
        var defaults = {
        		userName:null,
        		password:null,
        		firstName:null,
        		lastName:null,
        		role:null
        		
  
        };
        _.extend(this, defaults, attributes);
    };
    
    // Class Methods
    _.extend(adminUser.prototype, {
        /**
         * Useful method to parse server response and populate the fields.
         */
        populateData: function (data) {
            _.extend(this, data);
        } 
    });
 
    return adminUser;
}();/**
 * 
 */