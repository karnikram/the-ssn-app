var postCount;
var callCount;
var postsArray;
var category;
var oldFileName;
var oldFileURL;

function deletePost(postRef, postFileName) {
    console.log("Inside delete post function");
    document.getElementById('deleteModalHeaderText').innerHTML = "<strong><h3>Deleting Post...Page will automatically reload once the post is deleted succesfully.</h3></strong>";
    document.getElementById('deleteModalFooter').innerHTML = "<center><img src='img/hourglass.svg'  id='smallLoader'></center>";
    var categorywisePostRef = firebase.database().ref("categorywise_posts/" + category + "/" + postRef);
    var PostRef = firebase.database().ref("posts/" + postRef);
    categorywisePostRef.remove();
    PostRef.remove();
    console.log("post deleted");
    if (postFileName == "") {
        console.log("No file attached with this post");
    } else {
        // Create a reference to the file to delete
        var fileToBeDeleted = firebase.storage().ref('posts/' + postFileName);
        // Delete the file
        fileToBeDeleted.delete().then(function() {
            // File deleted successfully
            console.log("File deleted succesfully");
        }).catch(function(error) {
            // Uh-oh, an error occurred!
            console.log("Error occured while deleting file");
        });
    }
    location.reload();
}

function createDeletePostModal(postTitle, postRef, postFileName) {
    console.log("Inside Function createDeletePostModal");
    document.getElementById('deleteModalBodyText').innerHTML = postTitle;
    $('#deletePostButton').attr("onclick", "deletePost('" + postRef + "','" + postFileName + "')");
    $('#deletePostModal').modal('toggle');
}

function createEditPostModal(postRef, postTitle, postDescription, contactno, email, uploadedFileURL, uploadedFileName) {
    console.log("Inside Function createEditPostModal");
    $('#title').attr("value", postTitle);
    document.getElementById('description').value = postDescription;
    $('#contactno').attr("value", contactno);
    $('#email').attr("value", email);
    if (uploadedFileURL == "") {
        document.getElementById('UploadedFile').innerHTML = "No File Uploaded";
    } else {
        $('#FileLink').attr("href", uploadedFileURL);
    }
    oldFileURL = uploadedFileURL;
    oldFileName = uploadedFileName;
    $('#editPostButton').attr("onclick", "editPost('" + postRef + "')");
    $('#editPostModal').modal('toggle');
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

            if (postData.fileURL == "") {
                var fileViewContent = "No file Uploaded";
            } else {
                var fileViewContent = `<a href="` + postData.fileURL + `" target="_blank"><button type="button" class="btn btn-default"> View File </button></a>`;
            }
            if (postData.postedby == uemail) {
                document.getElementById('loader').style.display = "none";
                var postHTML = `<div class="post">
                                                                <span class="postTitle">` + postData.title + `</span>
                                                                <span class="postDate">` + postData.date + `</span>
                                                                <span class="postDescription">` + postData.description + `</span>
                                                                <span class="uploadedFile"><strong>Uploaded File : </strong>` + fileViewContent + `</span>
                                                                <span class="contactNo"><strong>Contact No : </strong> ` + postData.contactno + `</span>
                                                                <span class="contactEmail"><strong>Contact Email : </strong>` + postData.email + `</span>
                                                                <div id="buttonSection">
                                                                <center>
                                                                <button class="btn btn-default editButton" onclick="createEditPostModal('` + postID + `','` + postData.title + `','` + postData.description + `','` + postData.contactno + `','` + postData.email + `','` + postData.fileURL + `','` + postData.fileName + `');">Edit</button>
                                                                <button class="btn btn-danger deleteButton" onclick="createDeletePostModal('` + postData.title + `','` + postID + `','` + postData.fileName + `');">Delete</button>
                                                                </center>
                                                                </div>
                                                            </div>`;
                $("#subConsoleArea").append(postHTML);
                ++postCount;
            }
            console.log("postcount : " + postCount);
            console.log("before checking");
            console.log("Call count" + callCount);
            console.log("Array length:" + postsArray.length);
            if (callCount == (postsArray.length)) {
                console.log("checking complete");
                resolve();
            }
        });


    });
}

function managePosts() {
    /*
    if($(window).width()<1000){
         document.getElementById('modalText').innerHTML = "For best experience use the console from a desktop or laptop.";
         $('#templateModal').modal('toggle');
     }
    */
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
                                    if (postsArray.length == 0) {
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
                        console.log("Unauthorized User Signed In");
                        var page = document.getElementById('page');
                        page.innerHTML = "  <div id='mainContent'><center><img src='img/theSsnAppLogo.png' id='theSsnAppLogo'><h3 id='main-txt'>The SSN App Web Console</h3><h4>You are not authorized to view this page. Please login with registered emailid.</h4><div id='mainbutton'><a href='index.html'><button class='btn btn-default'><span class='signin-txt'>Go to HomePage</span></button></a></div></center><div>";
                    }
                });
        } else {
            console.log("No User Signed In");
            window.location.replace("../signedout.html");
        }
    });
}


/** File Upload function***/
var fileURL, fileName;
var uploader = document.getElementById('uploader');
var fileButton = document.getElementById('fileButton');

fileButton.addEventListener('change', function(e) {
    //Get file
    var file = e.target.files[0];
    var sFileName = file.name;
    var sFileExtension = sFileName.split('.')[sFileName.split('.').length - 1].toLowerCase();
    var iFileSize = file.size;
    var iConvert = (file.size / 1048576).toFixed(2);

    if (!(sFileExtension === "pdf" ||
            sFileExtension === "png" ||
            sFileExtension === "jpg" || sFileExtension === "jpeg") || iFileSize > 10485760 / 2) { /// 5 mb
        txt = "File type : " + sFileExtension + "<br><br>";
        txt += "Size: " + iConvert + " MB <br><br>";
        txt += "Please make sure your file is in pdf or any image format (png,jpg,jpeg) and less than 5 MB.\n\n";
        document.getElementById('modalText').innerHTML = txt;
        $('#templateModal').modal('toggle');
    } else {
        //Create a storage ref
        var storageRef = firebase.storage().ref('posts/' + file.name);
        fileName = file.name;

        //Upload File
        var task = storageRef.put(file);

        task.on('state_changed',

            function progress(snapshot) {
                document.getElementById('fileHelpText').innerHTML="Uploading File";
                var percentage = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                uploader.value = percentage;
                if(uploader.value == 100){
                     document.getElementById('fileHelpText').innerHTML="File Succesfully Uploaded";
                 }
            },

            function error(err) {

            },

            function complete() {
                fileURL = task.snapshot.downloadURL;
                $('#FileLink').attr("href", fileURL);
                console.log(fileURL);
            }

        );
    }
});


/***Edit post function***/
function editPost(postRef) {
    document.getElementById('editModalHeaderText').innerHTML = "<strong>Saving Post...Page will automatically reload once the post is saved succesfully.</strong>";
    document.getElementById('editModalFooter').innerHTML = "<center><img src='img/hourglass.svg'  id='smallLoader'></center>";
    var title = document.getElementById('title').value;
    var description = document.getElementById('description').value;
    var contactno = document.getElementById('contactno').value;
    var email = document.getElementById('email').value;
    var date = Date();
    console.log(title);
    console.log(description);
    console.log(contactno);
    console.log(email);

    var pid = firebase.database().ref('posts/' + postRef);
    if (fileURL === undefined) {
        fileURL = oldFileURL;
        fileName = oldFileName;
    }
    pid.update({
        title: title,
        description: description,
        contactno: contactno,
        email: email,
        'fileName': fileName,
        fileURL: fileURL,
        date: date,
    }, function(error) {
        if (error) {
            alert("Data could not be saved." + error);
        } else {
            // Create a reference to the file to delete
            var oldFileToBeDeleted = firebase.storage().ref('posts/' + oldFileName);
            // Delete the file
            oldFileToBeDeleted.delete().then(function() {
                // File deleted successfully
                console.log("Old File deleted succesfully");
            }).catch(function(error) {
                // Uh-oh, an error occurred!
                console.log("Error occured while deleting old file");
            });

            location.reload();
        }
    });
}
