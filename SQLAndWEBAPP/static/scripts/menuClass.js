class menu{
  menuItems;
  constructor(ParentDiv){
    const parentdiv = document.getElementById(ParentDiv);
    console.log(parentdiv)
    //Aside
    const aside = document.createElement("aside");
    parentdiv.insertBefore(aside, parentdiv.firstChild);

    //Content Div
    const contentDiv = document.createElement("div");
    contentDiv.className = "content";
    const menuTitle = document.createElement("p");
    menuTitle.className = "menu-title";
    menuTitle.textContent = "Menyval";
    aside.appendChild(contentDiv);
    contentDiv.appendChild(menuTitle);

    //Navigator
    const nav = document.createElement("nav");
    aside.appendChild(nav);

    //Menu List
    const ul = document.createElement("ul");
    ul.setAttribute("id","menulist");
    nav.appendChild(ul);

    //Menu contents
    if(localStorage.getItem("userEmail") != null){
      this.menuItems = [
        { text: "Hem", href: "homepage.html" },
        { text: "Våra bilar", href: "car.html" },
        { text: "Profil", href: "profile.html" },
        { text: "Om oss", href: "about.html" },
        { text: "Logga ut", id: "logoutLink" }
      ];
    }else{
      this.menuItems = [
        { text: "Hem", href: "homepage.html" },
        { text: "Våra bilar", href: "car.html" },
        { text: "Om oss", href: "about.html" },
        { text: "Ny användare?", href: "register.html" },
        { text: "Logga in", id: "loginLink" }
      ];
    }

    // Loop through menu items and create li and a elements
    this.menuItems.forEach(function(item) {
      const li = document.createElement("li");
      const a = document.createElement("a");
      a.textContent = item.text;
      a.href = item.href;
      //If Login Option
      if (item.id) {
        a.id = item.id;
        a.style.cursor = "pointer";
      }
      console.log("MenuTab")
      ul.appendChild(li);
      li.appendChild(a);
    });

    //LoginForm
    const loginForm = document.createElement("div");
    loginForm.id = "loginForm";
    loginForm.style.display = "none";

    //EmailInput
    const loginEmailInput = document.createElement("input");
    loginEmailInput.type = "text";
    loginEmailInput.placeholder = "Email";
    loginEmailInput.id = "loginEmail";
    //PasswordInput
    const loginPasswordInput = document.createElement("input");
    loginPasswordInput.type = "password";
    loginPasswordInput.placeholder = "Password";
    loginPasswordInput.id = "loginPassword";
    //LoginButton
    const loginButton = document.createElement("button");
    loginButton.textContent = "Logga in";
    loginButton.id = "loginButton";

    loginForm.appendChild(loginEmailInput);
    loginForm.appendChild(loginPasswordInput);
    loginForm.appendChild(loginButton);
    nav.appendChild(loginForm);

    if(window.location.href == "http://127.0.0.1:5500/templates/profile.html"){
      const deleteAcc = document.createElement("button");
      deleteAcc.setAttribute("id","deleteAccountButton");
      deleteAcc.textContent = "Radera användare";
      aside.appendChild(deleteAcc);
    }
    if(localStorage.getItem("userEmail") != null){
      //Functionality of Log out Link
      const logoutLink = document.getElementById("logoutLink");
      logoutLink.addEventListener('click', function(event) {
        event.preventDefault();
        //Clear storage and go home
        localStorage.removeItem("userDetails",null);
        localStorage.removeItem("userEmail",null);
        window.location.href = "/templates/homepage.html"; 
      });
    } else{
      //Functionality of Log in Link
      const loginLink = document.getElementById("loginLink");
      loginLink.addEventListener('click', function(event) {
        event.preventDefault();
        if(loginForm.style.display == "none"){
          loginForm.style.display = "block";
        }else{
          loginForm.style.display = "none";
        }
      });

      //When log in pressed
    loginButton.addEventListener('click', function() {
      const email = document.getElementById("loginEmail").value;
      const password = document.getElementById("loginPassword").value;

      console.log("Attempting login: ", email,password);

      fetch("http://localhost:8080/users/loginUser", {
          method: "POST",
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({email: email, password: password}),
      })
      .then(response => {
          if (response.ok) {
              response.json().then(user =>{
                  console.log("User logged in:", user);                    
                  localStorage.setItem("userDetails", JSON.stringify(user));
                  localStorage.setItem("userEmail", user.email);
                  alert("Inloggningen lyckades! Välkommen.");
                  window.location.href = "/templates/profile.html"; 
              });
          } else {
              response.text().then(text => alert(text)); 
          }
      })
      .catch(error => {
          console.error("Det uppstod ett fel", error);
          alert("Ett fel inträffade under inloggningsförsöket.");
      });
    });
    }
  }

}

