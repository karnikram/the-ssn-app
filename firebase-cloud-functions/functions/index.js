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

  var expansion = {"admin":"Administrative Block",
                           "busdept":"Bus Department",
                           "clubs":"Clubs",
                           "examcell":"Exam Cell",
                           "biomed":"BME Department",
                           "chem":"Chemical Engineering",
                           "civil":"Civil Engineering",
                           "cse":"CSE Department",
                           "ece":"ECE Department",
                           "eee":"EEE Department",
                           "human":"Humanities and Science Department",
                           "it":"IT Department",
                           "mech":"Mechanical Engineering Department",
                           "somca":"School of Management",
                           "sase":"School of Advanced Career Education"}

   const expanded = expansion[category];
   console.log(expanded);

  const payload = {
    notification: {
      title: expanded,
      body: snapshot.val().title,
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
