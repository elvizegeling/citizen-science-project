/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze java script file zorgt voor de visuele elementen in de html file        ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 26-05-2025                                                         ##
########################################################################################
*/

let huidigeStap = 0;
let onderdelen, stappen, volgende, vorige;

document.addEventListener("DOMContentLoaded", function () {
    onderdelen = document.querySelectorAll('.onderdeel');
    stappen = document.querySelectorAll('.stap');
    volgende = document.querySelectorAll('.volgende');
    vorige = document.querySelectorAll('.vorige');

    onderdelen.forEach((onderdeel) => {
        onderdeel.addEventListener('click', () => {
            const stapIndex = parseInt(onderdeel.getAttribute('data-stap'), 10);
            toonStap(stapIndex);
        });
    });
    volgende.forEach((knop) => {
        knop.addEventListener('click', () => {
            if (huidigeStap < stappen.length - 1) {
                toonStap(huidigeStap+1);
            }
        });
    });
    vorige.forEach((knop) => {
        knop.addEventListener('click', () => {
            if (localStorage.getItem("Algemeenbool") === "0") {
                if (huidigeStap > 0) {
                    toonStap(huidigeStap-1);
                }
            } else {
                if (huidigeStap > 1) {
                    toonStap(huidigeStap-1);
                }
            }
        });
    });
});

function toonStap(index) {
    stappen.forEach((stap, i) => {
        if (i === index) {
            stap.style.display = 'block';
        } else {
            stap.style.display = 'none';
        }
    });
    onderdelen.forEach((onderdeel) => {
        const stapIndex = parseInt(onderdeel.getAttribute('data-stap'), 10);
        onderdeel.classList.toggle('active', stapIndex === index);
    });
    huidigeStap = index;
}

document.addEventListener("DOMContentLoaded", function () {
    const studieInput = document.getElementById('studie');
    const geenStudieCheckbox = document.getElementById('geen_studie');
    geenStudieCheckbox.addEventListener('change', () => {
        if (geenStudieCheckbox.checked) {
            studieInput.disabled = true;
            studieInput.value = null
        } else {
            studieInput.disabled = false;
        }
    })

    const werkInput = document.getElementById('werk');
    const geenWerkCheckbox = document.getElementById('geen_werk');
    geenWerkCheckbox.addEventListener('change', () => {
        if (geenWerkCheckbox.checked) {
            werkInput.disabled = true;
            werkInput.value = null;
        } else {
            werkInput.disabled = false;
        }
    })

    const studiedrukInput = document.getElementById('studiedruk');
    const geenStudie2Checkbox = document.getElementById('geen_studie2');
    geenStudie2Checkbox.addEventListener('change', () => {
        if (geenStudie2Checkbox.checked) {
            studiedrukInput.disabled = true;
            studiedrukInput.value = null;
        } else {
            studiedrukInput.disabled = false;
        }
    })

    const werkTevredenInput = document.getElementById('werk_tevreden');
    const geenWerk2Checkbox = document.getElementById('geen_werk2');
    geenWerk2Checkbox.addEventListener('change', () => {
        if (geenWerk2Checkbox.checked) {
            werkTevredenInput.disabled = true;
            werkTevredenInput.value = null;
        } else {
            werkTevredenInput.disabled = false;
        }
    })

    const werkDrukInput = document.getElementById('werkdruk');
    const geenWerk3Checkbox = document.getElementById('geen_werk3');
    geenWerk3Checkbox.addEventListener('change', () => {
        if (geenWerk3Checkbox.checked) {
            werkDrukInput.disabled = true;
            werkDrukInput.value = null;
        } else {
            werkDrukInput.disabled = false;
        }
    })

    const gewichtInput = document.getElementById('gewicht');
    const geenWeegschaalCheckbox = document.getElementById('geen_weegschaal');
    geenWeegschaalCheckbox.addEventListener('change', () => {
        if (geenWeegschaalCheckbox.checked) {
            gewichtInput.disabled = true;
            gewichtInput.value = null;
        } else {
            gewichtInput.disabled = false;
        }
    })

    const bovendrukInput = document.getElementById('bovendruk');
    const onderdrukInput = document.getElementById('onderdruk');
    const apparaatInput = document.getElementById('apparaat_bloeddruk');
    const geenMeterCheckbox = document.getElementById('geen_meter');
    geenMeterCheckbox.addEventListener('change', () => {
        if (geenMeterCheckbox.checked) {
            bovendrukInput.disabled = true;
            bovendrukInput.value = null;
            onderdrukInput.disabled = true;
            onderdrukInput.value = null;
            apparaatInput.disabled = true;
            apparaatInput.value = null;
        } else {
            bovendrukInput.disabled = false;
            onderdrukInput.disabled = false;
            apparaatInput.disabled = false;
        }
    })
});

function startVragenlijst() {
    const weeknummer = document.getElementById("weeknummer");
    if (weeknummer.value === "") {
        alert("Kies een weeknummer");
    } else {
        document.getElementById('invoeren').style.display = 'none';
        if (localStorage.getItem("Algemeenbool") === "0") {
            document.getElementById("start_tekst").style.display = 'none';
            document.getElementById("stap0").style.display = 'block';
            document.getElementById("progress_versie1").style.display = 'block';
            toonStap(0);
        }
        else {
            document.getElementById("start_tekst").style.display = 'none';
            document.getElementById("stap1").style.display = 'block';
            document.getElementById("progress_versie2").style.display = 'block';
            toonStap(1);
        }
    }
}

function gaTerug(button) {
    const div_id = button.closest("div").id;
    const huidige = Number(div_id.match(/\d+/)[0]);
    document.getElementById(`stap${huidige}`).style.display = 'none';
    if (huidige === 0) {
        document.getElementById("start_tekst").style.display = 'block';
        document.getElementById("invoeren").style.display = 'block';
        document.getElementById("progress_versie1").style.display = 'none';
        huidigeStap = 0;
    } else if (huidige === 1) {
        if (localStorage.getItem("Algemeenbool") === "1") {
            document.getElementById("start_tekst").style.display = 'block';
            document.getElementById("invoeren").style.display = 'block';
            document.getElementById("progress_versie2").style.display = 'none';
            huidigeStap = 0;
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
}

document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('mijnEnkeleGrafiek').getContext('2d');

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5', 'Week 6'],
            datasets: [{
                label: 'Stappen per week',
                data: [60000, 80000, 55000, 100000, 60000, 75000],
                backgroundColor: 'rgba(188, 0, 49, 0.6)',
                borderColor: 'rgba(132, 0, 34, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});

