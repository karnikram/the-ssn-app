 var fileURL,fileName;
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
                   var percentage = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                   uploader.value = percentage;
               },

               function error(err) {

               },

               function complete() {
                   fileURL = task.snapshot.downloadURL;
                   console.log(fileURL);
               }

           );
       }
   });

   function createPost() {
       document.getElementById('loader').style.display = "block";
       var title = document.getElementById('title').value;
       var description = document.getElementById('description').value;
       var contactno = document.getElementById('contactno').value;
       var email = document.getElementById('email').value;
       console.log(title);
       console.log(description);
       console.log(contactno);
       console.log(email);
       var user = firebase.auth().currentUser;
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
                       // key will be "ada" the first time and "alan" the second time
                       var key = childSnapshot.key;
                       // childData will be the actual contents of the child
                       var childData = childSnapshot.val();
                       if (uemail === childData.emailid) {
                           found = 1;
                           var date = Date();
                           var pid = firebase.database().ref('posts/').push();
                           if (pid.key) {
                               console.log(pid.key);
                           }
                           if (fileURL === undefined) {
                               fileURL = "";
                               fileName = "";
                           }
                           pid.set({
                               title: title,
                               description: description,
                               contactno: contactno,
                               email: email,
                               'category': childData.permission,
                               'postedby': uemail,
                               'fileName': fileName,
                               fileURL: fileURL,
                               date: date,
                           }, function(error) {
                               if (error) {
                                   document.getElementById('loader').style.display = "none";
                                   alert("Data could not be saved." + error);
                               } else {
                                   document.getElementById('loader').style.display = "none";
                                   title.value = "";
                                   description.value = "";
                                   contactno.value = "";
                                   email.value = "";
                                   document.getElementById('modalText').innerHTML = "Success! Your Post was successful";
                                   $('#templateModal').modal('toggle');
                               }
                           });

                           var categoryWise = firebase.database().ref('categorywise_posts/' + childData.permission + '/' + pid.key);
                           categoryWise.set({
                               'pid': pid.key,
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
           var page = document.getElementById('page');
           page.innerHTML = "  <div id='mainContent'><center><img src='img/theSsnAppLogo.png' id='theSsnAppLogo'><h3 id='main-txt'>The SSN App Web Console</h3><h4>You are not authorized to view this page. Please login with registered emailid.</h4><div id='mainbutton'><a href='index.html'><button class='btn btn-default'><span class='signin-txt'>Go to HomePage</span></button></a></div></center><div>";
       }
   }
