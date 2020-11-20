let requests
let employees 

function getArchivedRequests(){

    let xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function() {
        console.log("change")
        if(xhr.readyState === 4 && xhr.status === 200){
            requests = JSON.parse(xhr.response)
            console.log(requests)
            for(let request of requests){
                let wrapper = document.createElement("div")
                let datesWrapper = document.createElement("div")
                let purchaseDate = document.createElement("div")
                let submittedDate = document.createElement("div")
                let infoWrapper = document.createElement("div")
                let employeeID = document.createElement("div")
                let purpose = document.createElement("div")
                let amount = document.createElement("div")
                let finalWrapper = document.createElement('div')
                let managerID = document.createElement("div")
                let additionalComments = document.createElement("div")
                let receiptImg = new Image(); 
                let modalButton = document.createElement("button")
                let modal = document.createElement("div")
                let modalDialog = document.createElement('div')
                let modalContent = document.createElement('div')
                let modalBody = document.createElement('div')

                let pd = new Date(requests[0].purchaseDate)
                let pDate = (pd.getMonth()+1) + '/'+pd.getDate()+'/'+pd.getFullYear()    
                let sd = new Date(requests[0].submittedDate)
                let sDate = (sd.getMonth()+1) + '/'+sd.getDate()+'/'+sd.getFullYear()    
            
                purchaseDate.innerHTML = "Purchased: " + pDate
                submittedDate.innerHTML = "Submitted: " + sDate
                employeeID.innerHTML = "Employee ID: " + request.employeeID
                purpose.innerHTML = "Purpose: " + request.purpose
                amount.innerHTML = "Amount: " + request.amount
                managerID.innerHTML = "Manager ID: " + request.managerID
                additionalComments.innerHTML = "Additional Comments: " + request.comments
                receiptImg.src = 'data:image/png;base64,' + request.receiptImg
                modalButton.innerHTML = "Receipt"

                wrapper.className = "col-12 request " + request.employeeID
                datesWrapper.className = "row justify-content-between dates"
                purchaseDate.className = "date-purchased date col-3"
                submittedDate.className = "date-submitted date col-3"
                infoWrapper.className = "row justify-content-around"
                employeeID.className = "employee col-3"
                purpose.className = "purpose col-4"
                amount.className = "amount col-2"
                finalWrapper.className = "row justify-content-around"
                managerID.className = "manager col-3"
                additionalComments.className = "comments col-6"
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
                finalWrapper.append(managerID, additionalComments)
                if(request.receiptImg){
                    finalWrapper.append(modalButton)
                }

                let modalWrapper = document.getElementById("modals")
                let app = document.getElementById("approved")
                let den = document.getElementById("denied")

                modalWrapper.append(modal)
                if(request.status == 'a'){
                    app.append(wrapper)
                    wrapper.style.backgroundColor = "#00ff2677"
                }else if(request.status == 'd'){
                    den.append(wrapper)
                    wrapper.style.backgroundColor = "#ff212177"
                }
            }
            let app = document.getElementById("approved")
            let den = document.getElementById("denied")
            if(!app.childElementCount){
                let noAppRequests = document.createElement("h3")
                noAppRequests.innerText = "No Approved Requests Found"
                noAppRequests.style.margin = "50px"
                app.append(noAppRequests)
            }else if(!den.childElementCount){
                let noDenRequests = document.createElement("h3")
                noDenRequests.innerText = "No Denied Requests Found"
                noDenRequests.style.margin = "50px"
                den.append(noDenRequests)
            }
        }
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursement/myapi/archivedrequests')

    xhr.send()
}

let employee

function getEmployeeInfo() {

    let xhr = new XMLHttpRequest()

    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            let employees = JSON.parse(xhr.response)
            console.log(employees)
            for(let e of employees){
                
                let option = document.createElement("option")
                option.innerText = e.employeeID + " " + e.fname + " " + e.lname
                e.value = e.employeeID
                select.append(option)
            }
        }
    }

    xhr.open("GET", "http://localhost:8080/ExpenseReimbursement/myapi/getemployees")
    xhr.send()
}

getEmployeeInfo()

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

getArchivedRequests();