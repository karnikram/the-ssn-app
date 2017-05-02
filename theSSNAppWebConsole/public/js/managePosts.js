function managePosts() {
    
    firebase.auth().onAuthStateChanged(function(user) {
    var name, email, uid;
    var postCount=0;
    var found = 0;
    if (user != null) {
        name = user.displayName;
        uemail = user.email;
        uid = user.uid;

        var query = firebase.database().ref("webconsole_users").orderByKey();
        query.once("value")
            .then(function(snapshot) {
                snapshot.forEach(function(childSnapshot) {
                    var key = childSnapshot.key;
                    var childData = childSnapshot.val();
                    if (uemail === childData.emailid) {
                        found = 1;
                        var category = childData.permission;
                        var categorywisePostsPath =  firebase.database().ref("categorywise_posts/"+ category).orderByKey();

                        categorywisePostsPath.once("value")
                            .then(function(categorywisePostsSnapshot) {
                                categorywisePostsSnapshot.forEach(function(postChildSnapshot) {
                                var postKey = postChildSnapshot.key;
                                var postPath =   firebase.database().ref("posts/"+ postKey);
                                console.log(postKey);


                                postPath.once("value").then(function (postSnapshot){
                                 var postID = postSnapshot.key;
                                var postData = postSnapshot.val();
                                console.log(postData);
                                if(postData.postedby == uemail)
                                {
                                    document.getElementById('loader').style.display = "none";
                                    var postHTML = `<div class="post">
                                                                <span class="postTitle">`+postData.title+`</span>
                                                                <span class="postDate">`+postData. date+`</span>
                                                                <span class="postDescription">`+postData.description+`</span>
                                                                <span class="uploadedFile"><strong>Uploaded File : </strong><a href="`+postData.fileURL +`" target="_blank"><button type="button" class="btn btn-default"> View File </button></a></span>
                                                                <span class="contactNo"><strong>Contact No : </strong> `+postData.contactno+`</span>
                                                                <span class="contactEmail"><strong>Contact Email : </strong>`+postData.email+`</span>
                                                                <center>
                                                                <button class="btn btn-default editButton" onclick="">Edit</button>
                                                                <button class="btn btn-danger deleteButton" onclick="">Delete</button>
                                                                </center>
                                                            </div>`;
                                    $("#subConsoleArea").append(postHTML);
                                    ++postCount;
                                }
                                });

                                    });
                            });

                            if(!postCount){
                                    document.getElementById('loader').style.display = "none";
                                    $("#subConsoleArea").append("<center><h3>You haven't made any posts yet! Create a new post to manage it!</h3></center>");
                            }

                    }
                });
                if (!found) {
                    console.log("inside first if");
                    var page = document.getElementById('page');
                    page.innerHTML = "  <div id='mainContent'><center><img src='img/theSsnAppLogo.png' id='theSsnAppLogo'><h3 id='main-txt'>The SSN App Web Console</h3><h4>You are not authorized to view this page. Please login with registered emailid.</h4><div id='mainbutton'><a href='index.html'><button class='btn btn-default'><span class='signin-txt'>Go to HomePage</span></button></a></div></center><div>";
                }
            });
    } else {
        console.log("inside second if");
        window.location.replace("../signedout.html");
    }
});
}
