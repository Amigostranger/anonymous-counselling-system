<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
$username = $_REQUEST["Username"];
$password = $_REQUEST["Password"];
$email=$_REQUEST["Email"];
/* Select queries return a resultset */
$result = mysqli_query($link, "UPDATE USERS SET Password = '$password' WHERE Username='$username' AND Email='$email'");

if($result){
    echo 1;
}
else{
    echo 0;
}

mysqli_close($link);

echo 1;
?>