function getEmployeeInfo() {

    let xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            let employees = JSON.parse(xhr.response)
            console.log(employees)
            for(let employee of employees){
                
                let wrapper = document.createElement("div")
                let employeeID = document.createElement("div")
                let managerID = document.createElement("div")
                let name = document.createElement("h3")

                employeeID.innerText = "Employee ID: " + employee.employeeID
                managerID.innerText = "Manager's ID: " + employee.managerID
                name.innerText = employee.fname + " " + employee.lname

                wrapper.className = "col-3 person"

                wrapper.append(name, employeeID, managerID)

                let mans = document.getElementById("managers")
                let emps = document.getElementById("employees")

                if(employee.status == 'm'){
                    mans.append(wrapper)
                }else if(employee.status == 'e'){
                    emps.append(wrapper)
                }
            }
        }
    }

    xhr.open("GET", "http://localhost:8080/ExpenseReimbursement/myapi/getemployees")
    xhr.send()
}

getEmployeeInfo()