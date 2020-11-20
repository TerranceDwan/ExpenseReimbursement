function login(e){
    e.preventDefault();
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status === 401){
            alert("Incorrect username or password")
        }else if(xhr.readyState == 4 && xhr.status === 200){
            window.location.replace("http://localhost:8080/ExpenseReimbursement/views/portal.html")
        }
    }

    let username = document.getElementById("username");
    let password = document.getElementById("password");

    let params = "username="+username.value+"&password="+password.value;

    xhr.open('POST', 'http://localhost:8080/ExpenseReimbursement/myapi/session?'+params)
    xhr.send()
}

let loginForm = document.getElementsByClassName("login")[0]

loginForm.addEventListener("submit",login);