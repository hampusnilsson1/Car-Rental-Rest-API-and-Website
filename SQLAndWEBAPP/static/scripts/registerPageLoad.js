const Asidemenu = new menu("page-layout");
const apiUrl = 'http://localhost:8080';

document.querySelector('.registrationForm form').addEventListener('submit', function(event) {
    console.log("Form submission triggered");
    event.preventDefault(); 
   

    const formData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        confirmPassword: document.getElementById('confirmPassword').value,
        name: document.getElementById('name').value,
        telephoneNumber: document.getElementById('telephoneNumber').value,
        address: document.getElementById('address').value,
    };

    fetch(apiUrl + '/users/addUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
    .then(response => {
        if(!response.ok){
            throw new Error("E-post finns redan registrerad")
        }
        return response.json();
    })
    .then(data => {
        console.log('Success:', data);
        window.alert("Du är registrerad!");
        window.location.href = "/templates/homepage.html"; 
        })
    .catch((error) => {
        console.error('Error:', error);
        window.alert("Registrering misslyckades, användaren finns redan");
    });
});
