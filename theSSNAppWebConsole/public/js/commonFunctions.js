 $(document).ready(function() {
        $('[data-toggle="tooltip"]').tooltip();
        firebase.auth().onAuthStateChanged(function(user) {
            if (user) {
                // User is signed in.
                var tempName = firebase.auth().currentUser.displayName;
                var userName = "" + tempName;
                console.log(userName);
                $('#signedInUser').text(userName);
            } else {
                // No user is signed in.
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

    
