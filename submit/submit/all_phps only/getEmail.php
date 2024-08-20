<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
$username = $_REQUEST["Username"];
$email = $_REQUEST["Email"];
/* Select queries return a resultset */
if ($result = mysqli_query($link, "SELECT * from USERS WHERE Username='$username' AND Email='$email'")) {
while ($row=$result->fetch_assoc()){
$output[]=$row;
}
}
mysqli_close($link);
echo json_encode($output);
?>