const db = require('monk')(process.env.MONGOURL);

var findByUsername = function(username){
    const users = db.get('messagingUser')
    return users.findOne({"communicationsUser":username}).then(function(data){
        db.close();
        return data;
    },function(err){
        console.log(err)
        db.close();
        return undefined;
    });
}

module.exports = {findByUsername}