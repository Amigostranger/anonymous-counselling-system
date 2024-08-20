<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();
/* Select queries return a resultset */
$fname = $_REQUEST["Lname"];
$lname = $_REQUEST["Fname"];
$password = $_REQUEST["Password"];
$email = $_REQUEST["Email"];


$result = mysqli_query($link, "INSERT INTO COUNSELLORS (Fname,Lname,Password,Problem_ID,Email,Num_patients) VALUES('$fname','$lname','$password',-1,'$email',0)");


if($result){
$output=array();
if ($result1 = mysqli_query($link, "SELECT * from COUNSELLORS")) {
while ($row=$result1->fetch_assoc()){
$output[]=$row;
}
}
mysqli_close($link);
echo json_encode($output);
} else {echo 0;}

mysqli_close($link);

?>