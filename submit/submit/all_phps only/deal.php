<?php
$username = "s2570223";
$password = "s2570223";
$database = "d2570223";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$Relationships = "Not";
$Self = "Not";
$Academics = "Not";
$Random = "Not";
$Depression = "Not";
$Family = "Not";
$Loss = "Not";
$Friends = "Not";

$Username = $_REQUEST["Username"];
$me = $_REQUEST["what"];

switch ($me) {
    case "Relationships":
        $Relationships = $me;
        break;
    case "Academics":
        $Academics = $me;
        break;
    case "Loss":
        $Loss = $me;
        break;
    case "Family":
        $Family = $me;
        break;
    case "Depression":
        $Depression = $me;
        break;
    case "Friends":
        $Friends = $me;
        break;
    case "Random":
        $Random = $me;
        break;
    case "Self":
        $Self = $me;
        break;
    default:
        echo "That's wrong";
}

// Select the counselor and problem
$sql = "SELECT *
        FROM COUNSELLORS, PROBLEMS
        WHERE (
          PROBLEMS.Relationships = '$Relationships'
          OR PROBLEMS.Self = '$Self'
          OR PROBLEMS.Family = '$Family'
          OR PROBLEMS.Friends = '$Friends'
          OR PROBLEMS.Depression = '$Depression'
          OR PROBLEMS.Academics = '$Academics'
          OR PROBLEMS.Loss = '$Loss'
          OR PROBLEMS.Random = '$Random'
        )
        AND COUNSELLORS.Problem_ID = PROBLEMS.Problem_ID";

$result = mysqli_query($link, $sql);
$output = [];
if ($result) {
    while ($row = $result->fetch_assoc()) {
        $output[] = $row;
    }
}


$couns_id =  $output[0]["Couns_ID"];
$num = (int) $output[0]["Num_patients"];

for ($i = 0; $i < count($output); $i++) {
    if ($num > (int) $output[$i]["Num_patients"]) {
        $couns_id = $output[$i]["Couns_ID"];
        $num = (int) $output[$i]["Num_patients"];
    }
}

// Get the user's problem ID
$sqlmid = "SELECT Problem_ID,Couns_ID FROM USERS WHERE Username = '$Username'";
$resultmid = mysqli_query($link, $sqlmid);
$outputmid = [];

if ($resultmid) {
    while ($row = $resultmid->fetch_assoc()) {
        $outputmid[] = $row;
    }
}

// Delete the old problem
$prob = $outputmid[0]["Problem_ID"];
$old_couns = $outputmid[0]["Couns_ID"];
$sqlmid2 = "DELETE FROM PROBLEMS WHERE Problem_ID = '$prob'";
$result5 = mysqli_query($link, $sqlmid2);

$sqlmid3 = "UPDATE COUNSELLORS SET Num_patients = Num_patients-1 WHERE Couns_ID = '$old_couns' ";
$result3 = mysqli_query($link, $sqlmid3);

// Insert a new problem
$sql2 = "INSERT INTO PROBLEMS (Relationships, Depression, Self, Academics, Family, Loss, Friends, Random)
         VALUES ('$Relationships', '$Depression', '$Self', '$Academics', '$Family', '$Loss', '$Friends', '$Random')";
$result2 = mysqli_query($link, $sql2);

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

$sql4 = "UPDATE USERS SET Problem_ID = '$id', Couns_ID = '$couns_id' WHERE Username = '$Username'";
$result4 = mysqli_query($link, $sql4);

$sql5 = "UPDATE COUNSELLORS SET Num_patients = Num_patients + 1 WHERE Couns_ID = '$couns_id'";
$result5 = mysqli_query($link, $sql5);

mysqli_close($link);

echo $couns_id;

//echo json_encode($output);
?>