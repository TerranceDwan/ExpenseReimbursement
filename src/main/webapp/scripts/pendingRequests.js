let requests
let employees 

function approveRequest(e) {
    e.preventDefault()
    let button = e.target
    let wholeRequest = button.parentElement.parentElement.parentElement.parentElement
    let requestID = button.getAttribute("name")
    let xhr = new XMLHttpRequest()
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            wholeRequest.style.textAlign = "center"
            wholeRequest.innerHTML = "<h1>Approved</h1>"
            wholeRequest.style.backgroundColor = "#00ff2677"
            setTimeout(()=>{
                wholeRequest.remove()
            },1500)
        }
    }
    xhr.open("POST","http://localhost:8080/ExpenseReimbursement/myapi/approve")
    xhr.send(requestID)
}

function denyRequest(e) {
    e.preventDefault()
    let button = e.target
    let wholeRequest = button.parentElement.parentElement.parentElement.parentElement
    let requestID = button.getAttribute("name")
    let xhr = new XMLHttpRequest()
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            wholeRequest.style.textAlign = "center"
            wholeRequest.innerHTML = "<h1>Denied</h1>"
            wholeRequest.style.backgroundColor = "#ff212177"
            setTimeout(()=>{
                wholeRequest.remove()
            },1500)
        }
    }
    xhr.open("POST","http://localhost:8080/ExpenseReimbursement/myapi/deny")
    xhr.send(requestID)
}

function getPendingRequests(){
    
    let xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function() {
        console.log("change")
        if(xhr.readyState === 4 && xhr.status === 200){
            requests = JSON.parse(xhr.response)
            for(let request of requests){
                if(request.status == 'p'){
                    let wrapper = document.createElement("div")
                    let datesWrapper = document.createElement("div")
                    let purchaseDate = document.createElement("div")
                    let submittedDate = document.createElement("div")
                    let infoWrapper = document.createElement("div")
                    let employeeID = document.createElement("div")
                    let purpose = document.createElement("div")
                    let amount = document.createElement("div")
                    let finalWrapper = document.createElement('div')
                    let approveDeny = document.createElement("div")
                    let approve = document.createElement('form')
                    let approveButton = document.createElement('button')
                    let deny = document.createElement('form')
                    let denyButton = document.createElement("button")
                    let additionalComments = document.createElement("div")
                    let receiptImg = new Image(); 
                    let modalButton = document.createElement("button")
                    let modal = document.createElement("div")
                    let modalDialog = document.createElement('div')
                    let modalContent = document.createElement('div')
                    let modalBody = document.createElement('div')

                    
                    approveButton.setAttribute("name",request.requestID)
                    denyButton.setAttribute("name", request.requestID)
                    approveButton.addEventListener("click", approveRequest)
                    denyButton.addEventListener("click",denyRequest)

                    let pd = new Date(requests[0].purchaseDate)
                    let pDate = (pd.getMonth()+1) + '/'+pd.getDate()+'/'+pd.getFullYear()    
                    let sd = new Date(requests[0].submittedDate)
                    let sDate = (sd.getMonth()+1) + '/'+sd.getDate()+'/'+sd.getFullYear()        
                
                    purchaseDate.innerHTML = "Purchased: " + pDate
                    submittedDate.innerHTML = "Submitted: " + sDate
                    employeeID.innerHTML = "Employee ID: " + request.employeeID
                    purpose.innerHTML = "Purpose: " + request.purpose
                    amount.innerHTML = "Amount: " + request.amount
                    approveButton.innerHTML = "Approve"
                    denyButton.innerHTML = "Deny"
                    additionalComments.innerHTML = "Additional Comments: " + request.comments
                    modalButton.innerHTML = "Receipt"
                    receiptImg.src = 'data:image/png;base64,' + request.receiptImg

                    wrapper.className = "col-12 request " + request.employeeID
                    datesWrapper.className = "row justify-content-between dates"
                    purchaseDate.className = "date-purchased date col-3"
                    submittedDate.className = "date-submitted date col-3"
                    infoWrapper.className = "row justify-content-around"
                    employeeID.className = "employee col-3"
                    purpose.className = "purpose col-4"
                    amount.className = "amount col-2"
                    finalWrapper.className = "row justify-content-around"
                    approveDeny.className = "col-4 approve-deny"
                    approve.classList.add("approve")
                    deny.classList.add("deny")
                    approveButton.className = "btn btn-success"
                    denyButton.classList.add("btn")
                    denyButton.classList.add("btn-danger")
                    additionalComments.className = "comments col-5"
                    modalButton.className = "btn col-2"
                    modal.className = "modal fade"
                    modalDialog.className = "modal-dialog modal-dialog-centerd"
                    modalContent.className = "modal-content"
                    modalBody.className = "modal-body"

                    modalButton.type = 'button'
                    modalButton.setAttribute("data-toggle","modal")
                    modalButton.setAttribute("data-target","#"+request.requestID)

                    modal.id = request.requestID
                    modal.setAttribute("tabindex","-1")
                    modal.setAttribute("role","dialog")

                    modalDialog.setAttribute("role","document")

                    modalBody.append(receiptImg)
                    modalContent.append(modalBody)
                    modalDialog.append(modalContent)
                    modal.append(modalDialog)
                    wrapper.append(datesWrapper, infoWrapper,finalWrapper)
                    datesWrapper.append(purchaseDate,submittedDate)
                    infoWrapper.append(employeeID,purpose,amount)
                    approve.append(approveButton)
                    deny.append(denyButton)
                    approveDeny.append(approve, deny)
                    finalWrapper.append(approveDeny,additionalComments)
                    if(request.receiptImg){
                        finalWrapper.append(modalButton)
                    }

                    let modalWrapper = document.getElementById("modals")
                    let header = document.getElementById("head")
                    header.append(wrapper)
                    modalWrapper.append(modal)
                    
                }       
            }
            if(!document.getElementsByClassName("request").length){
                let noRequests = document.createElement("h2")
                noRequests.innerText = "No Pending Requests Found"
                noRequests.style.margin = "50px"
                let header = document.getElementById("head")
                header.append(noRequests);
            }
        }
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursement/myapi/pendingrequests')

    xhr.send()
}


let employee

function getEmployeeInfo() {
    let xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function() {
        if(xhr.readyState === 4 && xhr.status === 200){
            employee = JSON.parse(xhr.response)
            console.log(employee)
            let select = document.getElementsByClassName("employee-select")[0]
            for(let e of employee.subordinateInformation){
                let option = document.createElement("option")
                option.innerText = e.employeeID + " " + e.fname + " " + e.lname
                e.value = e.employeeID
                select.append(option)
            }
        }
    }

    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursement/myapi/employee')

    xhr.send()
}

let select = document.getElementsByClassName("employee-select")[0]

select.addEventListener("change",()=>{
    if(select.value === "All"){
        let requestArr =  document.getElementsByClassName("request")
        for(let request of requestArr){
            request.style.display = "block"
        } 
    }else{
        let employeeID = select.value.split(" ")[0]
        let requestArr =  document.getElementsByClassName("request")
        for(let request of requestArr){
            console.log(request.className)
            console.log(employeeID)
            if(request.className.endsWith(employeeID)){
                request.style.display = "block"
            }else{
                request.style.display = "none"
            }
        }
    }
})

getEmployeeInfo();
getPendingRequests();



