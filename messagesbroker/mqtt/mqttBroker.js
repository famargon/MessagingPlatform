var mosca = require('mosca');

var usersService = require("messagingcommons").users;

var mongodb = {
  type: 'mongo',
  url: process.env.MONGOURL,
  pubsubCollection: process.env.BROKERCOLLECTION,
  mongo: {}
};

var settings = {
  port: 1883,
  backend: mongodb
};

module.exports = function(){

  var server = new mosca.Server(settings);

  server.on('clientConnected', function(client) {
      console.log('client connected', client.id);
  });

  // fired when a message is received
  server.on('published', function(packet, client) {
    //console.log('Published', packet.payload);
  });

  server.on('ready', setup);

  // Accepts the connection if the username and password are valid
  var authenticate = function(client, username, password, callback) {
    if(!username || !password){
      callback(null,false);
      return;
    }
    usersService.authenticateUser(username,password.toString()).then(function(result){
        if (result.authorized) {
          client.user = result.user;
          console.log("Authorized to authenticate "+username);
        }
        callback(null, result.authorized);
    },function(err){
      callback(null,false);
      console.log(err);
    });
  }

  var authorizePublish = function(client, topic, payload, callback) {
    callback(null, client.user.role === "ROLE_ADMIN");
  }

  var authorizeSubscribe = function(client, topic, callback) {
    callback(null, client.user.communicationsUser == topic.split('/')[2]);
  }

  // fired when the mqtt server is ready
  function setup() {
    server.authenticate = authenticate;
    server.authorizePublish = authorizePublish;
    server.authorizeSubscribe = authorizeSubscribe;
    console.log('MQTT BROKER bounded at port '+settings.port);
  }

}





