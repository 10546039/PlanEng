<?php
    $con = mysqli_connect("localhost", "id9704377_108401", "10840100", "id9704377_planeng");
    
    $name = $_POST["name"];
    $password = $_POST["password"];
    $email = $_POST["email"];
    $statement = mysqli_prepare($con, "INSERT INTO memberdata (m_name,m_password,m_email) VALUES (1,1,1)");
    mysqli_stmt_bind_param($statement, "siss", $name,$password,$email);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>