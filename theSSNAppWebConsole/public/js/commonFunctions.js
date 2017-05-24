 var signedInUserCategory;
 var signedInUserName;
 var signedInUserEmail;
 $(document).ready(function() {
    var found = 0;
     $('[data-toggle="tooltip"]').tooltip();
     firebase.auth().onAuthStateChanged(function(user) {
         if (user != null) {
             signedInUserName = user.displayName + "";
             signedInUserEmail = user.email;
             console.log(signedInUserName);


             var query = firebase.database().ref("webconsole_users").orderByKey();
             query.once("value")
                 .then(function(snapshot) {
                     snapshot.forEach(function(childSnapshot) {
                         var key = childSnapshot.key;
                         var childData = childSnapshot.val();
                         if (signedInUserEmail === childData.emailid) {
                             found = 1;
                             signedInUserCategory = childData.permission;
                             $('#signedInUser').text(signedInUserName);
                             $('#signedInUserPermissions').text(signedInUserCategory.toUpperCase());
                         }
                     });
                      if (!found) {
                       console.log("Unauthorized User Signed In");
                       var page = document.getElementById('page');
                       page.innerHTML = "  <div id='mainContent'><center><img src='img/theSsnAppLogo.png' id='theSsnAppLogo'><h3 id='main-txt'>The SSN App Web Console</h3><h4>You are not authorized to view this page. Please login with registered emailid.</h4><div id='mainbutton'><a href='index.html'><button class='btn btn-default'><span class='signin-txt'>Go to HomePage</span></button></a></div></center><div>";
                   }
                 });
         }else {
            console.log("No User Signed In");
            window.location.replace("../signedout.html");
        }
     });

 });

 function signOutWithGoogle() {
     firebase.auth().signOut().then(function() {
         // Sign-out successful.
         console.log("signedout");
         window.location.replace("../signedout.html");
     }).catch(function(error) {
         // An error happened.
     });
 }

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validatePhone(phoneNumber){
   var phoneNumberPattern = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;  
   return phoneNumberPattern.test(phoneNumber); 
}