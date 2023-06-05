document.querySelectorAll(".card__text").forEach(element => {
    text = element.innerText.split("");

    element.innerText = "";

    text.forEach(letter => {
        const span = document.createElement("span");

        span.className = "letter";
        span.innerText = letter;
        element.appendChild(span);
    })
})

function successfullyLogout() {
    swal("Sukces!", "Wylogowano pomyślnie", "success")
        .then(() => {
            document.logoutForm.submit();
        });
}

function JSalert(msg) {
    swal({
        title: msg,
        icon: "warning",
        confirmButtonColor: "#0275d8",
        confirmButtonText: "OK",
        closeOnConfirm: true
    })
}

function validatePracownik() {
    const letters = /^[\s\p{L}]+$/u;

    // Check if name contains only letters
    let name = document.forms["addPracownik"]["imie"].value;
    if (!name.match(letters)) {
        JSalert("Imię może zawierać wyłącznie litery");
        return false;
    }

    // Check if surname contains only letters
    let surname = document.forms["addPracownik"]["nazwisko"].value;
    if (!surname.match(letters)) {
        JSalert("Nazwisko może zawierać wyłącznie litery");
        return false;
    }

    // Check if date of birth is not from the future
    let date = document.forms["addPracownik"]["data_urodzenia"].value;
    date.split("/");
    date = new Date(parseInt(date[2]), parseInt(date[1]) - 1, parseInt(date[0]), 0, 0);
    if (date > new Date()) {
        JSalert("Nie można wpisać daty późniejszej niż dzisiejsza: Data urodzenia!");
        return false;
    }

    // Check PESEL number
    let pesel = document.forms["addPracownik"]["pesel"].value;
    if (!/^[0-9]+$/.test(pesel) || pesel.length !== 11) {
        JSalert("Niepoprawny numer PESEL");
        return false;
    }

    // Check hire date is not from the future
    let dateHire = document.forms["addPracownik"]["data_zatrudnienia"].value;
    dateHire = dateHire.split("/");
    dateHire = new Date(parseInt(dateHire[2]), parseInt(dateHire[1]) - 1, parseInt(dateHire[0]), 0, 0);
    if (dateHire > new Date()) {
        JSalert("Nie można wpisać daty późniejszej niż dzisiejsza: Data zatrudnienia");
        return false;
    }

    // Check account number
    let accountNumber = document.forms["addPracownik"]["nr_konta"].value;
    if (!/^[0-9]+$/.test(accountNumber) || accountNumber.length !== 26) {
        JSalert("Niepoprawny numer konta");
        return false;
    }

    // Check salary
    let salary = document.forms["addPracownik"]["wynagrodzenie"].value;
    if (salary < 0) {
        JSalert("Wynagrodzenie nie może być ujemne");
        return false;
    }

    // Check e-mail
    let email = document.forms["addPracownik"]["email"].value;
    if (email.length > 30) {
        JSalert("Adres e-mail jest zbyt długi!")
        return false;
    }

    // Check phone number
    let phoneNumber = document.forms["addPracownik"]["nr_telefonu"].value;
    if (phoneNumber.length > 11 || !/^(\d+ )*(\d+)$/.test(phoneNumber)) {
        JSalert("Niepoprawny numer telefonu (maks dlugość: 11 znaków)")
        return false;
    }
}

function validateAdres() {
    const letters = /^[\s\p{L}]+$/u;

    // Check street name
    let ulica = document.forms["addAdres"]["ulica"].value;
    if (ulica.length > 50) {
        JSalert("Nazwa ulicy jest za długa (maks: 50 znaków)");
        return false;
    }

    // Check building number
    let nr_budynku = document.forms["addAdres"]["nr_budynku"].value;
    if (nr_budynku.length > 5) {
        JSalert("Numer budynku jest za długi (maks: 5 znaków)");
        return false;
    }

    // Check accommodation
    let nr_lokalu = document.forms["addAdres"]["nr_lokalu"].value;
    if (nr_lokalu.length > 4) {
        JSalert("Numer lokalu jest za długi (maks: 4 znaki)");
        return false;
    }

    //Check city
    let miasto = document.forms["addAdres"]["miasto"].value;
    if (miasto.length > 30 || !letters.test(miasto)) {
        JSalert("Nazwa miasta jest niepoprawna (maks 30 znaków, wyłącznie polski alfabet)");
        return false;
    }
}

function validateSpoldzielnia() {
    // Check spoldzielnia name
    let nazwa = document.forms["addSpoldzielnia"]["nazwa"].value;
    if (nazwa.length > 100) {
        JSalert("Nazwa spółdzielni jest zbyt długa (maks 100 znaków)");
        return false;
    }

    // Check creation date
    let data_zalozenia = document.forms["addSpoldzielnia"]["data_zalozenia"].value;
    data_zalozenia = data_zalozenia.split("/");
    data_zalozenia = new Date(parseInt(data_zalozenia[2]), parseInt(data_zalozenia[1]) - 1, parseInt(data_zalozenia[0]), 0, 0);
    if (data_zalozenia > new Date()) {
        JSalert("Nie można wpisać daty późniejszej niż dzisiejsza: Data założenia");
        return false;
    }
}

function validateStanowisko() {
    const letters = /^[\s\p{L}]+$/u;

    // Check position name
    let stanowisko = document.forms["addStanowisko"]["nazwa_stanowiska"].value;
    if (stanowisko.length > 20 || !letters.test(stanowisko)) {
        JSalert("Niepoprawna nazwa stanowiska (maks 20 znaków, wyłącznie polski alfabet)");
        return false;
    }

    //Check description
    let opis = document.forms["addStanowisko"]["opis"].value;
    if (opis.length > 400) {
        JSalert("Opis jest zbyt długi (maks 400 znaków)");
        return false;
    }
}

