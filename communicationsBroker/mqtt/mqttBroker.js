var mosca = require('mosca');

var usersService = require("communications-commons").users;
    //    "communications-commons": "file:D:/DEVELOP/CommunicationSystem/communicationsCommons",

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
        //console.log(result);
    },function(err){
      callback(null,false);
      console.log(err);
    });
  }

  // In this case the client authorized as alice can publish to /users/alice taking
  // the username from the topic and verifing it is the same of the authorized user
  var authorizePublish = function(client, topic, payload, callback) {
    callback(null, client.user.role === "ROLE_ADMIN");
  }

  // In this case the client authorized as alice can subscribe to /users/alice taking
  // the username from the topic and verifing it is the same of the authorized user
  var authorizeSubscribe = function(client, topic, callback) {
    callback(null, client.user.communicationsUser == topic.split('/')[2]);
  }

  // fired when the mqtt server is ready
  function setup() {
    server.authenticate = authenticate;
    server.authorizePublish = authorizePublish;
    server.authorizeSubscribe = authorizeSubscribe;
    console.log('Mosca server is up and running');
  }

}





