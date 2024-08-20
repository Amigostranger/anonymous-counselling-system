<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
/* Select queries return a resultset */
$sender = $_REQUEST["Sender"];
$receiver = $_REQUEST["Receiver"];
$message = $_REQUEST["Message"];
$time = $_REQUEST["Time"];

$result = mysqli_query($link, "INSERT INTO Messages (Sender,Receiver,Message,Time) VALUES('$sender','$receiver','$message','$time')");
if($result){
echo 1;
} else {echo 0;}

mysqli_close($link);

?>