/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze java script file zorgt voor de visuele elementen in de html file        ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 26-05-2025                                                         ##
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

const studieInput = document.getElementById('studie');
const geenStudieCheckbox = document.getElementById('geen_studie');
geenStudieCheckbox.addEventListener('change', () => {
    if (geenStudieCheckbox.checked) {
        studieInput.disabled = true;
        studieInput.value = '';
    } else {
        studieInput.disabled = false;
    }
});

const werkInput = document.getElementById('werk');
const geenWerkCheckbox = document.getElementById('geen_werk');
geenWerkCheckbox.addEventListener('change', () => {
    if (geenWerkCheckbox.checked) {
        werkInput.disabled = true;
        werkInput.value = '';
    } else {
        werkInput.disabled = false;
    }
});

const studiedrukInput = document.getElementById('studiedruk');
const geenStudie2Checkbox = document.getElementById('geen_studie2');
geenStudie2Checkbox.addEventListener('change', () => {
    if (geenStudie2Checkbox.checked) {
        studiedrukInput.disabled = true;
        studiedrukInput.value = '';
    } else {
        studiedrukInput.disabled = false;
    }
});

const werkTevredenInput = document.getElementById('werk_tevreden');
const geenWerk2Checkbox = document.getElementById('geen_werk2');
geenWerk2Checkbox.addEventListener('change', () => {
    if (geenWerk2Checkbox.checked) {
        werkTevredenInput.disabled = true;
        werkTevredenInput.value = '';
    } else {
        werkTevredenInput.disabled = false;
    }
});

const werkDrukInput = document.getElementById('werkdruk');
const geenWerk3Checkbox = document.getElementById('geen_werk3');
geenWerk3Checkbox.addEventListener('change', () => {
    if (geenWerk3Checkbox.checked) {
        werkDrukInput.disabled = true;
        werkDrukInput.value = '';
    } else {
        werkDrukInput.disabled = false;
    }
});

const gewichtInput = document.getElementById('gewicht');
const geenWeegschaalCheckbox = document.getElementById('geen_weegschaal');
geenWeegschaalCheckbox.addEventListener('change', () => {
    if (geenWeegschaalCheckbox.checked) {
        gewichtInput.disabled = true;
        gewichtInput.value = '';
    } else {
        gewichtInput.disabled = false;
    }
});

const bovendrukInput = document.getElementById('bovendruk');
const onderdrukInput = document.getElementById('onderdruk');
const apparaatInput = document.getElementById('apparaat_bloeddruk');
const geenMeterCheckbox = document.getElementById('geen_meter');
geenMeterCheckbox.addEventListener('change', () => {
    if (geenMeterCheckbox.checked) {
        bovendrukInput.disabled = true;
        bovendrukInput.value = '';
        onderdrukInput.disabled = true;
        onderdrukInput.value = '';
        apparaatInput.disabled = true;
        apparaatInput.value = '';
    } else {
        bovendrukInput.disabled = false;
        onderdrukInput.disabled = false;
        apparaatInput.disabled = false;
    }
})

function startVragenlijst() {
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
}

function gaTerug(button) {
    const div_id = button.closest("div").id;
    const huidige = Number(div_id.match(/\d+/)[0]);
    document.getElementById(`stap${huidige}`).style.display = 'none';
    const weeknummer = document.getElementById("weeknummer");
    if (huidige === 0) {
        document.getElementById("start_tekst").style.display = 'block';
        document.getElementById('invoeren').style.display = 'block';
        //document.getElementById('progress_bar').style.display = 'none';
    } else if (huidige === 1) {
        if (weeknummer.value !== "1") {
            document.getElementById("start_tekst").style.display = 'block';
            document.getElementById('invoeren').style.display = 'block';
            //document.getElementById('progress_bar').style.display = 'none';
        } else {
            document.getElementById(`stap${huidige - 1}`).style.display = 'block';
        }
    } else {
        document.getElementById(`stap${huidige - 1}`).style.display = 'block';
    }
}

function gaVerder(button) {
    const div_id = button.closest("div").id;
    const huidige = Number(div_id.match(/\d+/)[0]);
    document.getElementById(`stap${huidige}`).style.display = 'none';
    document.getElementById(`stap${huidige + 1}`).style.display = 'block';
    // TODO: zorgen dat er niet verder gegaan kan worden wanneer niet alle verplichte velden zijn ingevuld
}

