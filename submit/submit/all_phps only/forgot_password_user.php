<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
$email = $_REQUEST["Email"];
$username = $_REQUEST["Username"];
$password = $_REQUEST["Password"];
$couns_id = (int)$_REQUEST["Couns_ID"];
$Problem_ID = (int)$_REQUEST["Problem_ID"];
/* Select queries return a resultset */
$result = mysqli_query($link, "INSERT INTO USERS VALUES('$username','$password','$email','$couns_ID','$Problem_ID') ");
if($result){
    echo 1;
}
else{
    echo 0;
}
mysqli_close($link);
?>