const searchForm = document.getElementById('searchForm');
const Asidemenu = new menu("page-layout");
const carGen = new CarGeneration("page-layout", true);
searchForm.addEventListener('submit', (event)=> {
    console.log('Form submitted');
    event.preventDefault();
    const startDate = document.getElementById('startDate').value.trim();
    const endDate = document.getElementById('endDate').value.trim();
    const carName = document.getElementById("carName").value.trim();
    const carBrand = document.getElementById("carBrand").value.trim();
    const milage = document.getElementById("milage").value.trim();
    const automatic = document.getElementById("automatic").value.trim();
    const carSeats = document.getElementById("carSeats").value.trim();
    const carYear = document.getElementById("carYear").value.trim();
    const engineType = document.getElementById("engineType").value.trim();
    const carType = document.getElementById("carType").value.trim();
    const pricePerDay = document.getElementById("pricePerDay").value.trim();

    let newParamsString = '';
    if (carName !== '') {
        newParamsString += `carName=${carName}&`;
    }
    if (carBrand !== '') {
        newParamsString += `carBrand=${carBrand}&`;
    }
    if (milage !== '') {
        newParamsString += `milage=${milage}&`;
    }
    if (automatic !== '') {
          newParamsString += `automatic=${automatic}&`;
    }
    if (carSeats !== '') {
        newParamsString += `carSeats=${carSeats}&`;
    }
    if (carYear !== '') {
        newParamsString += `carYear=${carYear}&`;
    }
    if (engineType !== '') {
        newParamsString += `engineType=${engineType}&`;
    }
    if (carType !== '') {
        newParamsString += `carType=${carType}&`;
    }
    if (pricePerDay !== '') {
        newParamsString += `pricePerDay=${pricePerDay}&`;
    }
    if (startDate !== ''){
        newParamsString += `startDate=${startDate}&`;
    }
    if (endDate !== ''){
            newParamsString += `endDate=${endDate}&`;
        }
    if (newParamsString.endsWith('&')) {
        newParamsString = newParamsString.slice(0, -1);
    }
    //URL to get data from API
    let searchedCars = [];

    const newUrl = `http://localhost:8080/searchAllCars?${newParamsString}`;
        fetch(newUrl)
          .then(response => response.json())
          .then(data => {
            searchedCars = data.map(carData => {
              return new Car(
                carData.licensePlate,
                carData.carName,
                carData.carBrand,
                carData.milage,
                carData.automatic,
                carData.carSeats,
                carData.carYear,
                carData.engineType,
                carData.carType,
                carData.pricePerDay
              );
            });

            // See if date matches
            const startDate = new Date(document.getElementById('startDate').value.trim());
            const endDate = new Date(document.getElementById('endDate').value.trim());

            if(startDate.getTime() > endDate.getTime()){
                alert("Upphämtningsdatum kan inte vara efter Inlämningsdatum!");
            }else{
                document.getElementById("shopgrid").innerHTML = "";
                searchedCars.forEach(car => {
                    carGen.carCard(car, "shopgrid");
                });
            }

        });

});