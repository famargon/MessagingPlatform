const db = require('monk')(process.env.MONGOURL || "mongodb://guest:guest@ds157112.mlab.com:57112/mqttbroker");

var findByUsername = function(username){
    const users = db.get('communicationsUser')
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