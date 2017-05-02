var postCount;
var callCount;
var postsArray;
var category;

function deletePost(postRef){
    console.log("Inside delete post function");
    var categorywisePostRef =  firebase.database().ref("categorywise_posts/" + category +"/"+ postRef);
    var PostRef =  firebase.database().ref("posts/" + postRef);
    categorywisePostRef.remove();
    PostRef.remove();
    document.getElementById('deleteModalFooter').innerHTML = "<center><h4>Deleting Post...Page will automatically reload once the post is deleted succesfully.</h4> <img src='img/hourglass.svg'  id='smallLoader'></center>"
    console.log("post deleted");
    location.reload();
}

function createDeletePostModal(postTitle,postRef){
    console.log("Inside Function createDeletePostModal")
    document.getElementById('deleteModalBodyText').innerHTML = postTitle;
    $('#deletePostButton').attr("onclick","deletePost('"+postRef+"')");
    $('#deletePostModal').modal('toggle');
}

function finalCallBack() {
                                                console.log('all done');
                                                if (!postCount) {
                                                    document.getElementById('loader').style.display = "none";
                                                    $("#subConsoleArea").append("<center><h3>You haven't made any posts yet! Create a new post to manage it!</h3></center>");
                                                }
                                            }

function constructPostView(postChildSnapshot) {
    return new Promise(function(resolve) {
        
        var postKey = postChildSnapshot.key;
        var postPath = firebase.database().ref("posts/" + postKey);
        console.log(postKey);
        postPath.once("value").then(function(postSnapshot) {
            ++callCount;
            var postID = postSnapshot.key;
            var postData = postSnapshot.val();
            console.log(postData);
            if (postData.postedby == uemail) {
                document.getElementById('loader').style.display = "none";
                var postHTML = `<div class="post">
                                                                <span class="postTitle">` + postData.title + `</span>
                                                                <span class="postDate">` + postData.date + `</span>
                                                                <span class="postDescription">` + postData.description + `</span>
                                                                <span class="uploadedFile"><strong>Uploaded File : </strong><a href="` + postData.fileURL + `" target="_blank"><button type="button" class="btn btn-default"> View File </button></a></span>
                                                                <span class="contactNo"><strong>Contact No : </strong> ` + postData.contactno + `</span>
                                                                <span class="contactEmail"><strong>Contact Email : </strong>` + postData.email + `</span>
                                                                <center>
                                                                <button class="btn btn-default editButton" onclick="">Edit</button>
                                                                <button class="btn btn-danger deleteButton" onclick="createDeletePostModal('`+postData.title+`','`+postID+`');">Delete</button>
                                                                </center>
                                                            </div>`;
                $("#subConsoleArea").append(postHTML);
                ++postCount;
            }
             console.log("postcount : "+postCount);
                console.log("before checking");
                console.log("Call count" + callCount);
                console.log("Array length:"+postsArray.length);
                if (callCount == (postsArray.length)) {
                    console.log("checking complete");
                    resolve();
                }
        });


    });
}

function managePosts() {

    firebase.auth().onAuthStateChanged(function(user) {
        var name, email, uid;
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
                            category = childData.permission;
                            var categorywisePostsPath = firebase.database().ref("categorywise_posts/" + category).orderByKey();

                            categorywisePostsPath.once("value")
                                .then(function(categorywisePostsSnapshot) {
                                    postsArray = [];
                                    categorywisePostsSnapshot.forEach(function(postSnapshot) {
                                        postsArray.push(postSnapshot);
                                    });
                                })
                                .then(function() {
                                    postsArray.reverse();
                                    postCount = 0;
                                    callCount = 0;
                                    if(postsArray.length==0){
                                        finalCallBack();
                                    }
                                    for (var i = 0; i < postsArray.length; i++) {
                                        constructPostView(postsArray[i])
                                            .then(finalCallBack);
                                    }
                                });
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
/*
function refreshPage(){
    location.reload();
}

var PostsRef = firebase.database().ref("posts/");
PostsRef.on('child_added' , refreshPage);
PostsRef.on('child_changed' , refreshPage);
PostsRef.on('child_removed' , refreshPage)
*/