
class CarGeneration{
    constructor(infoPanelParentID,showRentButton)
    {
        this.createFullInfo(infoPanelParentID,showRentButton);
    }
    carCard(car,locDivParentId)
    {
        const productContainer = document.createElement("div");
        document.getElementById(locDivParentId).appendChild(productContainer);
        productContainer.setAttribute("class","productcontainer");



        //PictureContainer
        const productPictureContainer = document.createElement("div");
        productPictureContainer.setAttribute("class","imgcontainer");
        productContainer.appendChild(productPictureContainer);

        //Picture
        const productPicture = document.createElement("img");
        productPicture.setAttribute("class","carimg");
        productPicture.setAttribute("src","/static/images/cars/" + car.getCarBrand() + ".webp");
        productPictureContainer.appendChild(productPicture);



        //ProductInfoContainer
        const productInfoContainer = document.createElement("div");
        productInfoContainer.setAttribute("class","productinfocontainer");
        productContainer.appendChild(productInfoContainer);

        //Model
        const productModel = document.createElement("p");
        productModel.setAttribute("class","productname");
        productModel.textContent = car.getCarBrand();
        productInfoContainer.appendChild(productModel);

        //Price
        const productPrice = document.createElement("p");
        productPrice.setAttribute("class","productprice");
        productPrice.textContent = car.getPricePerDay()+"kr/Dag";
        productInfoContainer.appendChild(productPrice);

        let onProductPanel = false;
        const infoPanel = document.getElementById("fullInfoPanel");
        let onInfoPanel = false;

        productContainer.addEventListener("mouseenter",() =>{
            const infoPanel = document.getElementById("fullInfoPanel");
            const parentRect = productContainer.getBoundingClientRect();
            const adjustedTop = parentRect.top + window.scrollY;
            infoPanel.style.top = (adjustedTop+parentRect.height-20)+"px";
            infoPanel.style.left = (parentRect.left-66)+"px";
            infoPanel.style.pointerEvents ="auto";
            this.showFullInfo(car);
            onProductPanel = true;
            onInfoPanel = false;

            const timeoutID = setTimeout(()=>{
                infoPanel.style.opacity = 1;
            },1)
        });

        productContainer.addEventListener("mouseleave",() =>{
            onProductPanel = false;
            setTimeout(()=>{
                if(onInfoPanel != true){
                    const infoPanel = document.getElementById("fullInfoPanel");
                    infoPanel.style.pointerEvents ="none";
                    infoPanel.style.opacity = 0;
                }
            },1);
        });

        infoPanel.addEventListener("mouseenter",()=>{
            onInfoPanel = true;
        })
        infoPanel.addEventListener("mouseleave",()=>{
            onInfoPanel = false;
            setTimeout(()=>{
                if(onProductPanel != true){
                    const infoPanel = document.getElementById("fullInfoPanel");
                    infoPanel.style.pointerEvents ="none";
                    infoPanel.style.opacity = 0;
                }
            },1);
        })
    }

    createFullInfo(ParentId, showRentButton){
        const fullInfoPanel = document.createElement("div");
        fullInfoPanel.setAttribute("id","fullInfoPanel");
        const carBrand = document.createElement("p");
        carBrand.setAttribute("id","infoCarBrand");
        const price = document.createElement("p");
        price.setAttribute("id","infoPrice");
        const licensePlate = document.createElement("p");
        licensePlate.setAttribute("id","infoLicensePlate");
        const model = document.createElement("p");
        model.setAttribute("id","infoModel");
        const milage = document.createElement("p");
        milage.setAttribute("id","infoMilage");
        const automatic = document.createElement("p");
        automatic.setAttribute("id","infoAutomatic");
        const carSeats = document.createElement("p");
        carSeats.setAttribute("id","infoCarSeats");
        const carYear = document.createElement("p");
        carYear.setAttribute("id","infoCarYear");
        const carType = document.createElement("p");
        carType.setAttribute("id","infoCarType");
        const engineType = document.createElement("p");
        engineType.setAttribute("id","infoEngineType");

        const totalPrice = document.createElement("p");
        totalPrice.setAttribute("id","infoTotalPrice");
        totalPrice.style.textAlign = "center";
        totalPrice.style.marginTop = "20px";
        totalPrice.style.fontSize = "12px";

        document.getElementById(ParentId).appendChild(fullInfoPanel);

        fullInfoPanel.appendChild(carBrand);
        fullInfoPanel.appendChild(model);
        fullInfoPanel.appendChild(carYear);
        fullInfoPanel.appendChild(carType);//SKA ADDAS
        fullInfoPanel.appendChild(engineType);//SKA ADDAS
        fullInfoPanel.appendChild(carSeats);
        fullInfoPanel.appendChild(licensePlate);
        fullInfoPanel.appendChild(milage);
        fullInfoPanel.appendChild(automatic);
        fullInfoPanel.appendChild(price);
        fullInfoPanel.appendChild(totalPrice);

        //Kolla ifall man är inloggad Kanske? Isåfall se köp knappar
        if(showRentButton == true){
            //Buy Button
            const rentButton = document.createElement("button");
            fullInfoPanel.appendChild(rentButton);
            rentButton.textContent ="Hyr!";
            rentButton.setAttribute("id","rentbutton");

            rentButton.addEventListener("click", (event)=>{
                //Get Licence Plate of car
                const infoPanel = event.target.parentNode;
                const licencePlateObj = infoPanel.querySelector("#infoLicensePlate");
                const licencePlate = licencePlateObj.textContent.substring(licencePlateObj.textContent.length - 6);
                console.log("Got Licence Plate: "+licencePlate);

                //Get current signed in
                const userEmail = localStorage.getItem("userEmail");
                console.log(userEmail+" is requesting to buy");

                //Get start and end date
                const startDate = new Date(document.getElementById("startDate").value);
                console.log("Got Start Date: "+ startDate);

                const endDate = new Date(document.getElementById("endDate").value);
                console.log("Got End Date: "+ endDate);

                //Get daily price
                const price = parseInt(infoPanel.querySelector("#infoPrice").textContent.replace(/\D/g, ''));

                //Calculate total price
                const days = (endDate-startDate)/(1000*60*60*24)+1;// +1 for the current day morning to night
                const totalPrice = days*price;
                console.log(totalPrice);

                //Create contract
                const contractData = {
                    email: userEmail,
                    licensePlate: licencePlate,
                    startDate: startDate,
                    endDate: endDate,
                    totalCost: totalPrice
                };

                if(confirm("Är du helt säker på köpet?")){
                    const apiUrl = 'http://localhost:8080';
                    fetch(apiUrl + '/Contract/addContract', { // HÄR SÄTT IN POSTMAPPEN FÖR SKAPA CONTRAKT
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(contractData),
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Nätverkssvar var inte ok!");
                        }
                        return response.json(); 
                    })
                    .then(data => {
                        window.alert("Köp gick igenom!");
                        console.log(data);
                    })
                    .catch((error) => {
                        console.error('Fel:', error);
                        window.alert("Köp misslyckades!");
                    });
                }
            });
        }
    }

    showFullInfo(car){
        document.getElementById("infoCarBrand").textContent = "Märke: "+car.getCarBrand();
        const infoTotalPrice = document.getElementById("infoTotalPrice");
        if(window.location.href == "http://127.0.0.1:5500/templates/car.html"){
            const startDate = new Date(document.getElementById("startDate").value);
            const endDate = new Date(document.getElementById("endDate").value);
            const totalPrice = car.getPricePerDay()*((endDate-startDate)/(1000*60*60*24)+1);
            infoTotalPrice.textContent = ("Totalkostnad: " + totalPrice+" kr");
        }else{
            infoTotalPrice.textContent = "";
        }
        document.getElementById("infoPrice").textContent = "Pris: "+car.getPricePerDay()+"kr/Dag";
        document.getElementById("infoLicensePlate").textContent = "RegNr: "+car.getLicensePlate();
        document.getElementById("infoModel").textContent = "Modell: "+car.getCarName();
        document.getElementById("infoMilage").textContent = "Miltal: "+car.getMilage();
        document.getElementById("infoAutomatic").textContent = "Växellåda: "+car.getAutomatic();
        document.getElementById("infoCarSeats").textContent = "Säten: "+car.getCarSeats();
        document.getElementById("infoCarYear").textContent = "Modell År: "+car.getCarYear();
        document.getElementById("infoEngineType").textContent = "Motortyp: "+car.getEngineType();
        document.getElementById("infoCarType").textContent = "Biltyp: "+car.getCarType();
    }
    
}

if(document.getElementById("rentbutton") != null){
    document.getElementById("rentbutton").addEventListener("click", (event)=>{
        infoPanel = event.target.parentNode;
        licencePlateObj = infoPanel.querySelector("#infoLicensePlate");

        licencePlate = licencePlateObj.textContent;
        console.log(licencePlate);
    });
}

