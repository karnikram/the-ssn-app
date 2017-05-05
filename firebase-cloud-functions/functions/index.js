const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendFeedNotification = functions.database.ref('/user_posts/{pushId}').onWrite(event => {
  const snapshot = event.data;
  const text = snapshot.val().content;

  const payload = {
    notification: {
      title: `News Feed : ${snapshot.val().userName} posted a message`,
      body: text ? (text.length <= 100 ? text : text.substring(0, 97) + '...') : '',
      icon: snapshot.val().userProfileURL,
      sound: "default"
    },
    data: {
        title: `News Feed : ${snapshot.val().userName} posted a message`,
        content: text ? (text.length <= 100 ? text : text.substring(0, 97) + '...') : '',
        color: '#2196F3'
    }
  };

  const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 * 7 * 4
   };

  return admin.messaging().sendToTopic("user_posts", payload, options);
});

exports.sendWebConsoleNotification = functions.database.ref('/posts/{pushId}').onWrite(event => {
  const snapshot = event.data;
  const text = snapshot.val().description;
  const category = snapshot.val().category;

  const payload = {
    notification: {
      title: `${category} : ${snapshot.val().title}`,
      body: text ? (text.length <= 100 ? text : text.substring(0, 97) + '...') : '',
      sound: "default"
    },
    data: {
        title: `${category} : ${snapshot.val().title}`,
        content: text ? (text.length <= 100 ? text : text.substring(0, 97) + '...') : '',
        color: '#2196F3'
    }
  };

  const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 * 7 * 4
   };


  return admin.messaging().sendToTopic(category, payload, options);
});
