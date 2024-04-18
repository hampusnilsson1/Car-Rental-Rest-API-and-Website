const Asidemenu = new menu("page-layout");
document.addEventListener("DOMContentLoaded", function(){
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    if(userDetails){
        document.getElementById("userName").textContent = userDetails.userName;
        document.getElementById("userAddress").textContent = userDetails.address;
        document.getElementById("userTelephoneNumber").textContent = userDetails.telephoneNumber;

        document.getElementById("userName").parentNode.addEventListener("click", enableUserNameEditing);
        document.getElementById("userAddress").parentNode.addEventListener("click", enableUserAddressEditing);
        document.getElementById("userTelephoneNumber").parentNode.addEventListener("click", enableUserNumberEditing);

    }else{
        console.log("Användariformation saknas i localstorage");
    }
});

function enableUserNameEditing() {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    enableEditing("userName", userDetails.userName);
}
function enableUserAddressEditing() {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    enableEditing("userAddress", userDetails.address);
}
function enableUserNumberEditing() {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    enableEditing("userTelephoneNumber", userDetails.telephoneNumber);
}

function enableEditing(field, currentValue) {
    console.log("Enabling Editing!");
    const parent = document.getElementById(field).parentNode;
    switch (field) {
        case "userName":
            parent.removeEventListener("click", enableUserNameEditing);
            break;
        case "userAddress":
            parent.removeEventListener("click", enableUserAddressEditing);
            break;
        case "userTelephoneNumber":
            parent.removeEventListener("click", enableUserNumberEditing);
            break;    
    }
    parent.innerHTML = `<input type="text" id="input_${field}" value="${currentValue}">
    <button onclick="saveChanges('${field}')">Spara</button>`;
}
function saveChanges(field) {
    const newValue = document.getElementById(`input_${field}`).value;
    console.log("New Value: " + newValue);
    switch(field) {
        case "userName":
            updateUserName(newValue);
            break;
        case "userAddress":
            updateUserAddress(newValue);
            break;
        case "userTelephoneNumber":
            updateUserTelephoneNumber(newValue);
            break;
        default:
            console.log("Okänt fält: " + field);
    }
}

function updateUserName(newUserName) {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    const apiURL = "http://localhost:8080"; 
    fetch(apiURL + "/users/updateUserName" , {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email: userDetails.email,name: newUserName}),
    })
    .then(handleResponse)
    .then(() => updateLocalAndUI("userName", newUserName))
    .catch(error => console.error("Error updating username:", error));
}

function updateUserAddress(newUserAddress) {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    const apiURL = "http://localhost:8080"; 
    fetch(apiURL + "/users/updateAddress" , {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email: userDetails.email,address: newUserAddress}),
     })
    .then(handleResponse)
    .then(() => updateLocalAndUI("userAddress", newUserAddress))
    .catch(error => console.error("Error updating address:", error));
}

 function updateUserTelephoneNumber(newUserTelephoneNumber) {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    const apiURL = "http://localhost:8080"; 
    fetch(apiURL + "/users/updateTelephoneNumber" , {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email: userDetails.email,telephoneNumber: newUserTelephoneNumber}),
     })
    .then(handleResponse)
    .then(() => updateLocalAndUI("userTelephoneNumber", newUserTelephoneNumber))
    .catch(error => console.error("Error updating telephone number:", error));
}

function handleResponse(response) {
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
}

function updateLocalAndUI(field, newValue) {
    console.log("How is life?");
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    userDetails[field] = newValue;
    localStorage.setItem("userDetails", JSON.stringify(userDetails));

    // Choose wich label to update
    let label = '';
    if (field === 'userName') {
        label = 'Namn';
    } else if (field === 'userAddress') {
        label = 'Adress';
    } else if (field === 'userTelephoneNumber') {
        label = 'Telephonenumber';
    }

    // Find the 'parentfield' and update the field with the new value.(name, adress or telephoneNumber)
    const parent = document.getElementById(`input_${field}`).parentNode;
    parent.innerHTML = `<p>${label}: <span id="${field}">${newValue}</span></p>`;

    if (field === 'userName') {
        document.getElementById("userName").parentNode.addEventListener("click", enableUserNameEditing);
    } else if (field === 'userAddress') {
        document.getElementById("userAddress").parentNode.addEventListener("click", enableUserAddressEditing);
    } else if (field === 'userTelephoneNumber') {
        document.getElementById("userTelephoneNumber").parentNode.addEventListener("click", enableUserNumberEditing);
    }

    // shows the new updated value
    document.getElementById(field).textContent = newValue;
}

//Getting the logged in user from localStorage
document.addEventListener("DOMContentLoaded", function() {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    if(userDetails){
        document.getElementById("userName").textContent = userDetails.userName;
        document.getElementById("userAddress").textContent = userDetails.address;
        document.getElementById("userTelephoneNumber").textContent = userDetails.telephoneNumber;
    }else{
        console.log("Användariformation saknas i localstorage");
    }
    const deleteAccountButton = document.getElementById("deleteAccountButton");
    if(deleteAccountButton) {
        deleteAccountButton.addEventListener("click", function(){
            const userEmail = localStorage.getItem("userEmail");
            if(userEmail && confirm("Är du säker på att du vill radera ditt konto?")){
                fetch(`http://localhost:8080/users/deleteUser?email=${encodeURIComponent(userEmail)}`,{
                    method:"DELETE"

                })
                .then(response => {
                    if(response.ok){
                        alert("Ditt konto har tagits bort");
                        localStorage.clear();
                        window.location.href = "homepage.html";
                    }else{
                        alert("Kunde inte radera konto");
                        return response.text();
                    }
                })
                .catch(error =>{
                    console.error("Error:", error);
                    alert("Ett fel har inträffat. Försök igen.");
                })
            }
        });
    }
});
