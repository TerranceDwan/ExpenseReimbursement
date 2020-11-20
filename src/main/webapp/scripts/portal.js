let employee

function getEmployeeInfo() {
    let xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function() {
        if(xhr.readyState === 4 && xhr.status === 200){
            employee = JSON.parse(xhr.response)
            console.log(employee)
            if(employee.status === "m"){
                let managerDetails = document.getElementsByClassName("manager-only")
                for(let i = 0; i < managerDetails.length; i++){
                    managerDetails[i].classList.remove("manager-only")
                    i-- 
                }
            }

            let wrapper = document.getElementById("employeeInfo")
            let name = document.createElement("h5")
            let email = document.createElement("div")
            let address = document.createElement("div")

            name.innerHTML = employee.fname + " " + employee.lname
            email.innerHTML = "Email:  " + employee.email
            address.innerHTML = "Address:  " + employee.address

            wrapper.append(name, email, address);
        }
    }

    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursement/myapi/employee')

    xhr.send()
}

getEmployeeInfo();

let editButton = document.getElementById("edit")
let personalInfo = document.getElementById("employeeInfo")

function submitInfo(e){
    e.preventDefault()
    let newEmail = document.getElementById("email-input").value
    let newAddress = document.getElementById("address-input").value
    let xhr = new XMLHttpRequest()
    xhr.onreadystatechange = () => {
        if(xhr.readyState === 4 && xhr.status === 200){
            let wrapper = document.getElementById("employeeInfo")
            wrapper.innerHTML = "";

            let name = document.createElement("h5")
            let email = document.createElement("div")
            let address = document.createElement("div")

            name.innerHTML = employee.fname + " " + employee.lname
            email.innerHTML = "Email:  " + newEmail
            address.innerHTML = "Address:  " + newAddress

            wrapper.append(name, email, address);
        }
    }
    xhr.open("POST", "http://localhost:8080/ExpenseReimbursement/myapi/updatepersonalinfo")
    xhr.send("email="+newEmail+", address="+newAddress)
}

function updateInfoForm() {
    let empInfo = document.getElementById("employeeInfo")
    let form = document.createElement("form")
    let newEmail = document.createElement("input")
    let newAddress = document.createElement("input")
    let submit = document.createElement("button")

    newEmail.value = employee.email
    newAddress.value = employee.address
    submit.innerText = "Submit"

    submit.type = "submit"

    submit.addEventListener("click", submitInfo)

    newEmail.id = "email-input"
    newAddress.id = "address-input"

    newEmail.className = "form-control col-12"
    newAddress.className = "form-control col-12"
    submit.className = "btn btn-warning"

    empInfo.innerHTML = "<h5>" + employee.fname + " " + employee.lname + "</h5>"
    form.append(newEmail, newAddress, submit)
    empInfo.append(form)
}


function createEmployee(e){
    e.preventDefault();
    let xhr = new XMLHttpRequest();

    let fname = document.getElementById("first-name");
    let lname = document.getElementById("last-name");
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let email = document.getElementById("email");
    let address = document.getElementById("address");

    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status === 401){
            alert("Issue creating employee.  Please try again.")
        }else if(xhr.readyState == 4 && xhr.status === 200){
            fname.value = "";
            lname.value = "";
            username.value = "";
            password.value = "";
            email.value = "";
            address.value = "";
            alert("Employee successfully created!")
        }
    }

    console.log(lname.value)

    let params = "firstName="+fname.value+"&lastName="+lname.value+"&username="+username.value+"&password="+password.value+"&email="+email.value+"&address="+address.value;

    xhr.open('POST', 'http://localhost:8080/ExpenseReimbursement/myapi/createEmployee?'+params)
    xhr.send()
}

let employeeForm = document.getElementsByClassName("create-employee")[0]

employeeForm.addEventListener("submit",createEmployee);
editButton.addEventListener("click",updateInfoForm)

var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();
 if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 

today = yyyy+'-'+mm+'-'+dd;
document.getElementById("purchasedDate").setAttribute("max", today);