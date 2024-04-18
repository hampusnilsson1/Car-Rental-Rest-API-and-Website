const Asidemenu = new menu("page-layout");
document.addEventListener("DOMContentLoaded", function() {
    let userDetails = JSON.parse(localStorage.getItem("userDetails"));
    if(userDetails){
        document.getElementById("userName").textContent = userDetails.userName;
        document.getElementById("userAddress").textContent = userDetails.address;
        document.getElementById("userTelephoneNumber").textContent = userDetails.telephoneNumber;
    }else{
        console.log("Användariformation saknas i localstorage");
    }
    const userEmail = localStorage.getItem("userEmail"); 

    console.log("user email från localstorage:" , userEmail)
    if (!userEmail) {
        console.error('No user email found in localStorage');
        return;
    }

    fetch(`http://localhost:8080/Contract/getContractByEmail?email=${encodeURIComponent(userEmail)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Http error! Status: ${response.status}");
            }
            return response.json();
        })
        .then(contracts => {
            console.log(contracts);
            const container = document.getElementById("contractsContainer");
            if (contracts && contracts.length > 0) {
                contracts.forEach(contract => {
                    contractElement = document.createElement("div");
                    contractElement.className = "contract";
                    contractElement.innerHTML = `
                        <h3>Kontrakt #: ${contract.contractNumber}</h3>
                        <p>Bil: ${contract.car.licensePlate}</p>
                        <p>Startdatum: ${new Date(contract.startDate).toLocaleDateString()}</p>
                        <p>Slutdatum: ${new Date(contract.endDate).toLocaleDateString()}</p>
                        <p>Total kostnad: ${contract.totalCost} SEK</p>
                    `;
                    container.appendChild(contractElement);
                });
            } else {
                container.innerHTML = '<p>Inga kontrakt att visa.</p>';
            }
        })
        .catch(error => {
            console.error("Failed to fetch contracts:", error);
        });
});
// Logout and go back to "startpage"
document.addEventListener("DOMContentLoaded", function(){
   const logoutButton = document.getElementById("logoutButton");
   if(logoutButton){
      logoutButton.addEventListener("click", function(){

        console.log("User is logging out");

        alert("Du har loggats ut");
        window.location.href = "/templates/homepage.html"; 
        
      });
    }else{
        console.log("utloggningsknappen hittades inte");
    }
});