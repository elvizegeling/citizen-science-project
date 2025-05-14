// checkt de toegangscode voor de login
function checklogin() {
    event.preventDefault();
    let student_id = document.getElementById("toegangscode").value;

    $.getJSON("/student/get", {student_id:student_id}, function (data){
        student = data.student_by_pk

        if (parseInt(student.student_id) === parseInt(student_id) ) {
            window.location.href = "invoeren.html"
            localStorage.setItem("id", student_id)
        } else {
            alert("ingevoerde toegangscode is onjuist")
            location.reload();
        }

    }).fail(function (jqXHR, textStatus, errorThrown){
        alert("ingevoerde toegangscode is onjuist")
        location.reload();
    })
}

function getweek(){
    event.preventDefault();
    localStorage.setItem("week",document.getElementById("weeknummer").value)
}

/*
let huidigeStap = 0
const onderdelen = document.querySelectorAll('.onderdeel');
const stappen = document.querySelectorAll('.stap');
onderdelen.forEach((onderdeel, index) => {
    onderdeel.addEventListener('click', () => {
        toonStap(index);
    });
});
function toonStap(index) {
    stappen.forEach((stap, i) => {
        stap.style.display = i === index ? 'block' : 'none';
        onderdelen[i].classList.toggle('active', i === index);
    });
    huidigeStap = index;
}
document.querySelector('.volgende').addEventListener('click', () => {
    if (huidigeStap < stappen.length - 1) toonStap(huidigeStap+1);
});
document.querySelector('.vorige').addEventListener('click', () => {
    if (huidigeStap > 0) toonStap(huidigeStap-1);
});*/

function startVragenlijst() { // start de vragenlijst wanneer er op 'starten' is gedrukt en een week is geselecteerd
    const weeknummer = document.getElementById("weeknummer");
    if (weeknummer.value === "") {
        alert("Kies een weeknummer");
    } else {
        document.getElementById('invoeren').style.display = 'none';
        //document.getElementById('progress_bar').style.display = 'block';
        if (localStorage.getItem("Algemeenbool") === "1") {
            document.getElementById("start_tekst").style.display = 'none';
            document.getElementById("stap0").style.display = 'block';
        }
        else {
            document.getElementById("start_tekst").style.display = 'none';
            document.getElementById("stap1").style.display = 'block';
        }
    }
    // TODO: van het geselecteerde weeknummer checken of de data hiervoor al is ingevuld (via db)
}


function gaTerug(button) { // gaat terug naar de vorige pagina wanneer er op 'vorige' is geklikt
    const div_id = button.closest("div").id; // haalt de id van de div van de stap op (bijv. "stap1")
    const huidige = Number(div_id.match(/\d+/)[0]); // haalt mbv regex het getal uit de id (bijv. 1)
    document.getElementById(`stap${huidige}`).style.display = 'none';
    const weeknummer = document.getElementById("weeknummer");
    if (huidige === 0) {
        document.getElementById("start_tekst").style.display = 'block';
        document.getElementById('invoeren').style.display = 'block';
        //document.getElementById('progress_bar').style.display = 'none';
    } else if (huidige === 1) {
        if (weeknummer.value !== "1") { // als data van week 2 t/m 6 wordt ingevoerd, gaat het van stap 1 terug naar start
            document.getElementById("start_tekst").style.display = 'block';
            document.getElementById('invoeren').style.display = 'block';
            //document.getElementById('progress_bar').style.display = 'none';
        } else { // als data van week 1 wordt ingevoerd, gaat het van stap 1 terug naar stap 0 (algemene vragenlijst)
            document.getElementById(`stap${huidige - 1}`).style.display = 'block';
        }
    } else {
        document.getElementById(`stap${huidige - 1}`).style.display = 'block';
    }
}

function gaVerder(button) { // gaat verder naar de volgende pagina wanneer er op 'volgende' is geklikt
    const div_id = button.closest("div").id; // haalt de id van de div van de stap op (bijv. "stap1")
    const huidige = Number(div_id.match(/\d+/)[0]); // haalt mbv regex het getal uit de id (bijv. 1)
    document.getElementById(`stap${huidige}`).style.display = 'none';
    document.getElementById(`stap${huidige + 1}`).style.display = 'block';
    // TODO: zorgen dat er niet verder gegaan kan worden wanneer niet alle verplichte velden zijn ingevuld
}

// aanroepen en versturen data algemeen
// checkt of er al data is van de student in de algemeen tabel van deze week
function checkAlgemeen(){
    id = localStorage.getItem("id");
    $.getJSON("/algemeen/get",{alg_id:id}, function (data){
        Algemeen = data.algemeen_by_pk
        if (parseInt(Algemeen.alg_id) === parseInt(id)){
            localStorage.setItem("Algemeenbool", "1");
        } else {
            localStorage.setItem("Algemeenbool", "0");
        }
    })
}

// laat de data zien in het forum als deze er is
function showAlgemeen() {
    event.preventDefault();
    id = localStorage.getItem("id")
    $.getJSON("/algemeen/get", {alg_id:id}, function (data){
        Algemeen = data.algemeen_by_pk
        document.getElementById("leeftijd").value = Algemeen.leeftijd
        document.getElementById("geslacht").value = Algemeen.geslacht
        document.getElementById("lichaamslengte").value = Algemeen.lengte_cm
        document.getElementById("thuis").value = Algemeen.thuis
        document.getElementById("reistijd").value = Algemeen.reistijd_min
    })
}

// voegt de data uit het forum toe aan de database
function addAlgemeen(){
    event.preventDefault();
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Algemeen = {
        "alg_id": id,
        "week": week,
        "leeftijd": document.getElementById("leeftijd").value,
        "geslacht": document.getElementById("geslacht").value,
        "lengte_cm": document.getElementById("lichaamslengte").value,
        "thuis": document.getElementById("thuis").value,
        "reistijd_min": document.getElementById("reistijd").value
    }
    if (localStorage.getItem("Algemeenbool") === "1"){
        editAlgemeen(Algemeen)
    }else {
        $.getJSON("/algemeen/new", Algemeen, function (data){
            console.log(data)
            window.location.href = "invoeren.html"
        })
    }
}

// wijzigt de data uit de database vanaf het forum
function editAlgemeen(Algemeen){
    event.preventDefault();
    $.getJSON("/algemeen/update", Algemeen, function (data){
        console.log(data)
        window.location.href="invoeren.html"
    })

}
