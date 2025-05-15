// checkt de toegangscode voor de login
function checklogin() {
    event.preventDefault();
    let student_id = document.getElementById("toegangscode").value;

    $.getJSON("/student/get", {student_id:student_id}, function (data){
        student = data.student_by_pk

        if (student === null){
            alert("ingevoerde toegangscode is onjuist")
            location.reload();
        }

        if (parseInt(student.student_id) === parseInt(student_id) ) {
            localStorage.setItem("id", student_id)
            window.location.href = "invoeren.html"
        } else {
            alert("ingevoerde toegangscode is onjuist")
            location.reload();
        }

    }).fail(function (jqXHR,textStatus,errorThrown){
        alert("ingevoerde toegangscode is onjuist")
        location.reload()
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

// aanroepen en versturen data algemeen
// checkt of er al data is van de student in de algemeen tabel van deze week
function checkAlgemeen(){
    id = localStorage.getItem("id");
    $.getJSON("/algemeen/get",{alg_id:id}, function (data){
        Algemeen = data.algemeen_by_pk

        if (Algemeen === null){
            localStorage.setItem("Algemeenbool", "0")
        }

        if (parseInt(Algemeen.alg_id) === parseInt(id)) {
            localStorage.setItem("Algemeenbool", "1");
        } else{
            localStorage.setItem("Algemeenbool", "0")
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
            localStorage.setItem("Algemeenbool", "1")
        })
    }
}

// wijzigt de data uit de database vanaf het forum
function editAlgemeen(Algemeen){
    event.preventDefault();
    $.getJSON("/algemeen/update", Algemeen, function (data){
        console.log(data)
    })

}

function checkBeweging(){
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("/beweging/get",{bew_id:id, week:week},function (data){
        Beweging = data.beweging_by_pk
        if (Beweging === null){
            localStorage.setItem("Bewegingbool", "0")
        }
        if (parseInt(Beweging.bew_id) === parseInt(id) && parseInt(Beweging.week) === parseInt(week)){
            localStorage.setItem("Bewegingbool", "1")
            showBeweging()
        } else {
            localStorage.setItem("Bewegingbool", "0")
        }
    })
}

function showBeweging() {
    event.preventDefault();
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("/beweging/get", {bew_id:id, week:week}, function (data){
        Beweging = data.beweging_by_pk

        document.getElementById("matig_intensief").value = Beweging.matig_min
        document.getElementById("intensief").value = Beweging.intens_min
        document.getElementById("bot_spier").value = Beweging.strek_min
        document.getElementById("zitten").value = Beweging.zit_uur
        document.getElementById("stappen").value = Beweging.stappen_gem
    })
}

function addBeweging() {
    event.preventDefault();
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Beweging = {
        "bew_id": id,
        "week": week,
        "matig_min": document.getElementById("matig_intensief").value,
        "intens_min": document.getElementById("intensief").value,
        "strek_min": document.getElementById("bot_spier").value,
        "zit_uur": document.getElementById("zitten").value,
        "stappen_gem": document.getElementById("stappen").value
    }
    if (localStorage.getItem("Bewegingbool") === "1"){
        editBeweging(Beweging)
    } else {
        $.getJSON("beweging/new", Beweging, function (data){
            console.log(data)
            localStorage.setItem("Bewegingbool", "1")
        })
    }
}

function editBeweging(Beweging){
    event.preventDefault();
    $.getJSON("beweging/update", Beweging, function (data){
        console.log(data);
    })
}

function checkMiddelengebruik(){
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("middelengebruik/get", {geb_id:id, week:week}, function (data){
        Middelengebruik = data.middelengebruik_by_pk
        if (Middelengebruik === null){
            localStorage.setItem("Middelengebruikbool", "0")
        }
        if (parseInt(Middelengebruik.geb_id) === parseInt(id) && parseInt(Middelengebruik.week) === parseInt(week)){
            localStorage.setItem("Middelengebruikbool", "1")
            showMiddelengebruik()
        } else {
            localStorage.setItem("Middelengebruikbool", "0")
        }
    })
}

function showMiddelengebruik(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("middelengebruik/get", {geb_id:id, week:week}, function (data){
        Middelengebruik = data.middelengebruik_by_pk

        document.getElementById("alcohol").value = Middelengebruik.consum_gem
        document.getElementById("roken").value = Middelengebruik.roken_gem
        document.getElementById("softdrugs").value = Middelengebruik.softdrugs_gem
        document.getElementById("harddrugs").value = Middelengebruik.harddrugs_gem
    })
}

function addMiddelengebruik(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Middelengebruik = {
        "geb_id": id,
        "week": week,
        "consum_gem": document.getElementById("alcohol").value,
        "roken_gem": document.getElementById("roken").value,
        "softdrugs_gem": document.getElementById("softdrugs").value,
        "harddrugs_gem": document.getElementById("harddrugs").value
    }
    if (localStorage.getItem("Middelengebruikbool") === "1"){
        editMiddelengebruik(Middelengebruik)
    } else {
        $.getJSON("middelengebruik/new", Middelengebruik, function (data){
            console.log(data)
            localStorage.setItem("Middelengebruikbool", "1")
        })
    }
}

function editMiddelengebruik(Middelengebruik){
    event.preventDefault()
    $.getJSON("middelengebruik/update", Middelengebruik, function (data){
        console.log(data)
    })
}

function checkSE(){
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("subjectieve_ervaringen/get",{se_id:id, week:week},function (data){
        SE = data.subjectieve_ervaringen_by_pk
        if (SE === null){
            localStorage.setItem("SEbool", "0")
        }
        if (parseInt(SE.se_id) === parseInt(id) && parseInt(SE.week) === parseInt(week)){
            localStorage.setItem("SEbool", "1")
            showSE()
        }else {
            localStorage.setItem("SEbool", "0")
        }
    })
}

function showSE(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("subjectieve_ervaringen/get", {se_id:id, week:week}, function (data){
        SE = data.subjectieve_ervaringen_by_pk

        document.getElementById("stress").value = SE.stress_sch
        document.getElementById("vermoeidheid").value = SE.vermoeid_sch
        document.getElementById("vitaal").value = SE.vitaal_sch
        document.getElementById("studie_tevreden").value = SE.tevr_stud_sch
        document.getElementById("studiedruk").value = SE.druk_stud_sch
        document.getElementById("leefstijl_tevreden").value = SE.tevr_leef_sch
        document.getElementById("sociaal_tevreden").value = SE.tevr_soci_sch
        document.getElementById("werkdruk").value = SE.druk_werk_sch
        document.getElementById("nachtrust_tevreden").value = SE.tevr_rust_sch
        document.getElementById("hobbys_tevreden").value = SE.tevr_hobb_sch
    })
}

function addSE() {
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    SE = {
        "se_id": id,
        "week": week,
        "stress_sch": document.getElementById("stress").value,
        "vermoeid_sch": document.getElementById("vermoeidheid").value,
        "vitaal_sch": document.getElementById("vitaal").value,
        "tevr_stud_sch": document.getElementById("studie_tevreden").value,
        "druk_stud_sch": document.getElementById("studiedruk").value,
        "tevr_leef_sch": document.getElementById("leefstijl_tevreden").value,
        "tevr_soci_sch": document.getElementById("sociaal_tevreden").value,
        "druk_werk_sch": document.getElementById("werkdruk").value,
        "tevr_rust_sch": document.getElementById("nachtrust_tevreden").value,
        "tevr_hobb_sch": document.getElementById("hobbys_tevreden").value
    }
    if (localStorage.getItem("SEbool")==="1"){
        editSE(SE)
    } else {
        $.getJSON("subjectieve_ervaringen/new", SE, function (data){
            console.log(data)
            localStorage.setItem("SEbool", "1")
        })
    }
}

function editSE(SE){
    event.preventDefault()
    $.getJSON("subjectiev_ervaringen/update", SE, function (data){
        console.log(data)
    })
}

function checkTijdbesteding(){
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("tijdbesteding/get", {tbd_id:id, week:week}, function (data){
        Tijdbesteding = data.tijdbesteding_by_pk
        if (Tijdbesteding === null){
            localStorage.setItem("Tijdbestedingbool", "0")
        }
        if (parseInt(Tijdbesteding.tbd_id) === parseInt(id) && parseInt(Tijdbesteding.week) === parseInt(week)){
            localStorage.setItem("Tijdbestedingbool", "1")
            showTijdbesteding()
        } else {
            localStorage.setItem("Tijdbestedingbool", "0")
        }
    })
}

function showTijdbesteding(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("tijdbesteding/get", {tbd_id:id, week:week}, function (data){
        Tijdbesteding = data.tijdbesteding_by_pk

        document.getElementById("slaap").value = Tijdbesteding.slaap_gem_uur
        document.getElementById("sociaal").value = Tijdbesteding.sociaal_gem_uur
        document.getElementById("hobbys").value = Tijdbesteding.hobby_gem_uur
        document.getElementById("studie").value = Tijdbesteding.studie_gem_uur
        document.getElementById("werk").value = Tijdbesteding.werk_gem_uur
    })
}

function addTijdbesteding() {
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Tijdbesteding = {
        "tbd_id": id,
        "week": week,
        "slaap_gem_uur": document.getElementById("slaap").value,
        "sociaal_gem_uur": document.getElementById("sociaal").value,
        "hobby_gem_uur": document.getElementById("hobbys").value,
        "studie_gem_uur": document.getElementById("studie").value,
        "werk_gem_uur": document.getElementById("werk").value
    }
    if (localStorage.getItem("Tijdbestedingbool") === "1"){
        editTijdbesteding(Tijdbesteding)
    }else {
        $.getJSON("tijdbesteding/new", Tijdbesteding, function (data){
            console.log(data)
            localStorage.setItem("Tijdbestedingbool", "1")
        })
    }
}

function editTijdbesteding(Tijdbesteding) {
    event.preventDefault()
    $.getJSON("tijdbesteding/update", Tijdbesteding, function (data){
        console.log(data)
    })
}

function checkVoeding(){
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("voeding/get", {voed_id:id, week:week}, function (data){
        Voeding = data.voeding_by_pk
        if (Voeding === null){
            localStorage.setItem("Voedingbool", "0")
        }
        if (parseInt(Voeding.voed_id)===parseInt(id) && parseInt(Voeding.week)===parseInt(week)){
            localStorage.setItem("Voedingbool", "1")
            showVoeding()
        } else {
            localStorage.setItem("Voedingbool", "0")
        }
    })
}

function showVoeding(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("voeding/get", {voed_id:id, week:week}, function (data){
        Voeding = data.voeding_by_pk

        document.getElementById("groente").value = Voeding.groente_gem
        document.getElementById("fruit").value = Voeding.fruit_por
        document.getElementById("frisdrank").value = Voeding.fris_sap_gls
        document.getElementById("snacks").value = Voeding.ong_snack
        document.getElementById("kant_en_klaar").value = Voeding.kant_klaar
        document.getElementById("vlees_gevogelte").value = Voeding.vlees_vogel
        document.getElementById("vis").value = Voeding.vis
        document.getElementById("specifieke_voeding").value = Voeding.spec_voeding
    })
}

function addVoeding(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Voeding = {
        "voed_id": id,
        "week": week,
        "groente_gem": document.getElementById("groente").value,
        "fruit_por": document.getElementById("fruit").value,
        "fris_sap_gls": document.getElementById("frisdrank").value,
        "ong_snack": document.getElementById("snacks").value,
        "kant_klaar": document.getElementById("kant_en_klaar").value,
        "vlees_vogel": document.getElementById("vlees_gevogelte").value,
        "vis": document.getElementById("vis").value,
        "spec_voeding": document.getElementById("specifieke_voeding").value
    }
    if (localStorage.getItem("Voedingbool") === "1"){
        editVoeding(Voeding)
    } else {
        $.getJSON("voeding/new", Voeding, function (data){
            console.log(data)
            localStorage.setItem("Voedingbool", "1")
        })
    }
}

function editVoeding(Voeding){
    event.preventDefault()
    $.getJSON("voeding/update", Voeding, function (data){
        console.log(data)
    })
}

function checkCardiovasculair(){
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("cardiovasculair/get", {car_id:id, week:week}, function (data){
        Cardiovasculair = data.cardiovasculair_by_pk
        if (Cardiovasculair === null){
            localStorage.setItem("Cardiovasculairbool", "0")
        }
        if (parseInt(Cardiovasculair.car_id) === parseInt(id) && parseInt(Cardiovasculair.week) === parseInt(week)){
            localStorage.setItem("Cardiovasculairbool", "1")
            showCardiovasculair()
        } else {
            localStorage.setItem("Cardiovasculairbool", "0")
        }
    })
}

function showCardiovasculair(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("cardiovasculair/get", {car_id:id, week:week}, function (data){
        Cardiovasculair = data.cardiovasculair_by_pk

        document.getElementById("gewicht").value = Cardiovasculair.gewicht
        document.getElementById("bovendruk").value = Cardiovasculair.bovengrens
        document.getElementById("onderdruk").value = Cardiovasculair.ondergrens
        document.getElementById("hartslag").value = Cardiovasculair.hartfrequentie
    })
}

function addCardiovasculair(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Cardiovasculair= {
        "car_id": id,
        "week": week,
        "gewicht": document.getElementById("gewicht").value,
        "bovengrens": document.getElementById("bovendruk").value,
        "ondergrens": document.getElementById("onderdruk").value,
        "hartfrequentie": document.getElementById("hartslag").value
    }
    if (localStorage.getItem("Cardiovasculairbool")==="1"){
        editCardiovasculair(Cardiovasculair)
    } else {
        $.getJSON("cardiovasculair/new", Cardiovasculair, function (data){
            console.log(data)
            localStorage.setItem("Cardiovasculairbool", "0")
        })
    }
}

function editCardiovasculair(Cardiovasculair){
    $.getJSON("cardiovasculair/update", Cardiovasculair, function (data){
        console.log(data)
    })
}