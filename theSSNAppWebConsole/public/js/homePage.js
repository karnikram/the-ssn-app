var provider = new firebase.auth.GoogleAuthProvider();

$(document).ready(function() {
if($(window).width()<1000){
         document.getElementById('modalText').innerHTML = "For best experience use the console from a desktop or laptop.";
         $('#templateModal').modal('toggle');
     }
});
function signInWithGoogle() {
    //var maincontent = document.getElementById("maincontent");
    document.getElementById("mainbutton").innerHTML=" <img src='img/hourglass.svg'  id='smallLoader'>"
    var found = 0;
    var userName;
    console.log("before signing in");
    firebase.auth().signInWithPopup(provider).then(function(result) {
        // This gives you a Google Access Token. You can use it to access the Google API.
        var token = result.credential.accessToken;
        // The signed-in user info.
        var user = result.user;
        // ...
        var userPermission;
        if (user != null) {
            user.providerData.forEach(function(profile) {
                console.log("Sign-in provider: " + profile.providerId);
                console.log("  Provider-specific UID: " + profile.uid);
                console.log("  Name: " + profile.displayName);
                console.log("  Email: " + profile.email);
                console.log("  Photo URL: " + profile.photoURL);
                //  maincontent.innerHTML = "<h1>profile.displayName</h1>"
                userName = profile.displayName;

                var query = firebase.database().ref("webconsole_users").orderByKey();
                query.once("value")
                    .then(function(snapshot) {
                        snapshot.forEach(function(childSnapshot) {
                            // key will be "ada" the first time and "alan" the second time
                            var key = childSnapshot.key;
                            // childData will be the actual contents of the child
                            var childData = childSnapshot.val();
                            if (profile.email === childData.emailid) {
                                found = 1;
                                userPermission = childData.permission;
                                console.log(profile.email === childData.emailid);
                            }
                        });
                        var mainbutton = document.getElementById("mainbutton");
                        if (found) {
                            $("#theSsnAppLogo").attr("src", "../img/entrance.png");
                            mainbutton.innerHTML = "<h4>Welcome <strong>" + profile.displayName + "</strong>!</h4><h5>You have permissions to post under : <strong>"+userPermission.toUpperCase() +"</strong> category .</h5><br/><a href='createPost.html'><button class='btn btn-default'><span class='signin-txt'>Proceed</span></button></a>";

                        } else {
                            mainbutton.innerHTML = "<h4><strong>" + userName + "</strong>, you are not authorised to access the webconsole. To use our android app please visit PlayStore.</h4><a href='https://goo.gl/lgzwPe' target='_blank'><button class='btn btn-default'><span class='signin-txt'>Playstore - The SSN App</span></button></a>";
                        }
                    });
            });
        }

    }).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        // The email of the user's account used.
        var email = error.email;
        // The firebase.auth.AuthCredential type that was used.
        var credential = error.credential;
        mainbutton.innerHTML = "<h4>" + errorCode +" " + errorMessage + "</h4><br/><h4>Please refresh page.</h4>";
    });

}
