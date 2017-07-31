var bcrypt = require('bcrypt');

var usersRepository = require('./users.repository');

/**
 * Return an object indicating if the username and password provided are valid in the system
 * if the credentials are valid in the response is included the user object with all of its properties 
 * @param {*} username 
 * @param {*} password 
 */
var authenticateUser = function(username, password){
    return new Promise(function (resolve, reject) {
            usersRepository.findByUsername(username).then(function(data){
                var result = {};
                if(data){
                    var isValid = bcrypt.compareSync(password, data.pwd);
                    result.authorized = isValid;
                    if(isValid){
                        result.user = data;
                    }
                    resolve(result);
                }else{
                    result.authorized = false;
                    resolve(result);
                }
            },function(err){
                reject(err);
            });
    });    
}

module.exports = {authenticateUser}