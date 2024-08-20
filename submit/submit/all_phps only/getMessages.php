<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
$sender = $_REQUEST["Sender"];
$receiver = $_REQUEST["Receiver"];
/* Select queries return a resultset */
if ($result = mysqli_query($link, "SELECT * from Messages WHERE (Sender = '$sender' AND Receiver = '$receiver') OR (Sender = '$receiver' AND Receiver = '$sender') ORDER BY Time")) {
while ($row=$result->fetch_assoc()){
$output[]=$row;
}
}
mysqli_close($link);
echo json_encode($output);
?>