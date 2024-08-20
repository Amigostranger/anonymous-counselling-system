<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$Relationships = $_REQUEST["Relationships"];
$Self = $_REQUEST["Self"];
$Academics = $_REQUEST["Academics"];
$Random = $_REQUEST["Random"];
$Depression = $_REQUEST["Depression"];
$Family = $_REQUEST["Family"];
$Loss = $_REQUEST["Loss"];
$Friends = $_REQUEST["Friends"];
$couns_id = $_REQUEST["Couns_ID"];


/* Select queries return a resultset */
$sql1 = "SELECT Problem_ID FROM COUNSELLORS WHERE Couns_ID = '$couns_id' " ;
$result1 = mysqli_query($link,$sql1);

if ($result1) {
while ($row=$result1->fetch_assoc()){
$output[]=$row;
}
}

$problem_id = $output[0]['Problem_ID'];

if($problem_id == "-1"){

   // Insert a new problem
   $sql2 = "INSERT INTO PROBLEMS (Relationships, Depression, Self, Academics, Family, Loss, Friends, Random)
         VALUES ('$Relationships', '$Depression', '$Self', '$Academics', '$Family', '$Loss', '$Friends', '$Random')";
   $result2 = mysqli_query($link, $sql2);
   if($result2){
        echo 55;
   }

   // get the last problem ID
   $sql3 = "SELECT Problem_ID FROM PROBLEMS";
   $result3 = mysqli_query($link, $sql3);
   $output3 = [];
   if ($result3) {
      while ($row = $result3->fetch_assoc()) {
          $output3[] = $row;
      }
   }

   $id = (int)end($output3)["Problem_ID"];

   $sql4 = "UPDATE COUNSELLORS SET Problem_ID = '$id', Couns_ID = '$couns_id' WHERE Couns_ID = '$couns_id'";
   $result4 = mysqli_query($link, $sql4);

}
else {
    // Insert a new problem
    $sql = "UPDATE PROBLEMS SET Relationships = '$Relationships', Depression = '$Depression', Self = '$Self', Academics = '$Academics', Family = '$Family', Loss = '$Loss', Friends = '$Friends', Random = '$Random' WHERE Problem_ID = '$problem_id'";
    $result = mysqli_query($link, $sql);
}



mysqli_close($link);
echo json_encode($output);
echo $problem_id;
?>