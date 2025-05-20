/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze java script file zorgt voor de visuele elementen in de html file        ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 19-04-2025                                                         ##
########################################################################################
*/


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
        if (localStorage.getItem("Algemeenbool") === "0") {
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

