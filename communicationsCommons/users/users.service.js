var bcrypt = require('bcrypt');

var usersRepository = require('./users.repository');

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

//authenticateUser("b","b").then(function(result){
//    console.log(result);
//});

module.exports = {authenticateUser}