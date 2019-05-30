<?php
    $con = mysqli_connect("localhost", "id9704377_108401", "10840100", "id9704377_planeng");
    
    $name = $_POST["name"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE name = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $name, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name,$password,$email);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["name"] = $name;
        $response["password"] = $password;
        $response["email"] = $email;
    }
    
    echo json_encode($response);
?>