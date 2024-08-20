<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$name = $_REQUEST["Couns_ID"];
$output=array();
/* Select queries return a resultset */
if ($r = mysqli_query($link, "SELECT * from USERS where
Couns_ID='$name'")) {
while ($row=$r->fetch_assoc()){
$output[]=$row;
}
}
mysqli_close($link);
echo json_encode($output);
?>