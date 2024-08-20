<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
/* Select queries return a resultset */
$username = $_REQUEST["Username"];
$password = $_REQUEST["Password"];
$couns_id =(int)$_REQUEST["Couns_ID"];
$problem_id =(int) $_REQUEST["Problem_ID"];
$email = $_REQUEST["Email"];

$result = mysqli_query($link, "INSERT INTO USERS (Username,Password,Couns_ID,Problem_ID,Email) VALUES('$username','$password','$couns_id','$problem_id','$email')");
if($result){
echo 1;
} else {echo 0;}

mysqli_close($link);

?>