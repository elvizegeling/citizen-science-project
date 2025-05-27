/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze javascript file zorgt ervoor dat de data uit de html file               ##
## getransformeerd wordt in json objecten zodat deze kunnen worden doorverstuurd naar ##
## de database en vice versa                                                          ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 26-05-2025                                                         ##
########################################################################################
*/

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

// vraagt de week op waarin de data is gemeten
function getweek(){
    event.preventDefault();
    localStorage.setItem("week",document.getElementById("weeknummer").value)
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
        document.getElementById("lichaamslengte").value = Algemeen.lengte_cm
    })
}

// voegt de data uit het forum toe aan de database of wijzigt deze
function addupdAlgemeen(){
    event.preventDefault();
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Algemeen = {
        "alg_id": id,
        "week": week,
        "lengte_cm": document.getElementById("lichaamslengte").value
    }
    if (localStorage.getItem("Algemeenbool") === "1"){
        $.getJSON("/algemeen/update", Algemeen, function (data){
            console.log(data)
        })
    }else {
        $.getJSON("/algemeen/new", Algemeen, function (data){
            console.log(data)
            localStorage.setItem("Algemeenbool", "1")
        })
    }
}

// aanroepen en versturen van beweging data
//checkt of er al data van de gekozen week in de beweging tabel is
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

// laat de data van de beweging tabel, als deze bestaat zien in het forum
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

// voegt de data uit het forum toe aan de beweging tabel of wijzigt deze
function addupdBeweging() {
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
        $.getJSON("beweging/update", Beweging, function (data){
            console.log(data);
        })
    } else {
        $.getJSON("beweging/new", Beweging, function (data){
            console.log(data)
            localStorage.setItem("Bewegingbool", "1")
        })
    }
}

// aanroepen en versturen van middelengebruik data
// checkt of er al data van de gekozen week in de middelengebruik tabel is
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

//laat de data van de middelengebruik tabel, als deze bestaat zien in het forum
function showMiddelengebruik(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    $.getJSON("middelengebruik/get", {geb_id:id, week:week}, function (data){
        Middelengebruik = data.middelengebruik_by_pk

        document.getElementById("alcohol").value = Middelengebruik.consum_gem
        document.getElementById("roken").value = Middelengebruik.roken_gem
        document.getElementById("drugs").value = Middelengebruik.drugs_gem
    })
}

// voegt de data uit het forum toe aan de middelengebruik tabel of wijzigt deze
function addupdMiddelengebruik(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Middelengebruik = {
        "geb_id": id,
        "week": week,
        "consum_gem": document.getElementById("alcohol").value,
        "roken_gem": document.getElementById("roken").value,
        "drugs_gem": document.getElementById("drugs").value
    }
    if (localStorage.getItem("Middelengebruikbool") === "1"){
        $.getJSON("middelengebruik/update", Middelengebruik, function (data){
            console.log(data)
        })
    } else {
        $.getJSON("middelengebruik/new", Middelengebruik, function (data){
            console.log(data)
            localStorage.setItem("Middelengebruikbool", "1")
        })
    }
}

// aanroepen en versturen van subjectieve ervaringen data
// checkt of er al data van de gekozen week in de subjectieve ervaringen tabel is
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

//laat de data van de subjectieve ervaringen tabel, als deze bestaat zien in het forum
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
        document.getElementById("werk_tevreden").value = SE.tevr_werk_sch
        document.getElementById("werkdruk").value = SE.druk_werk_sch
        document.getElementById("nachtrust_tevreden").value = SE.tevr_rust_sch
        document.getElementById("hobbys_tevreden").value = SE.tevr_hobb_sch
    })
}

// voegt de data uit het forum toe aan de subjectieve ervaringen tabel of wijzigt deze
function addupdSE() {
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
        "tevr_werk_sch": document.getElementById("werk_tevreden").value,
        "druk_werk_sch": document.getElementById("werkdruk").value,
        "tevr_rust_sch": document.getElementById("nachtrust_tevreden").value,
        "tevr_hobb_sch": document.getElementById("hobbys_tevreden").value
    }
    if (document.getElementById("geen_studie2").checked){SE["druk_stud_sch"] = "0"}
    if (document.getElementById("geen_werk2").checked){SE["tevr_werk_sch"] = "0"}
    if (document.getElementById("geen_werk3").checked){SE["druk_werk_sch"] = "0"}
    if (localStorage.getItem("SEbool")==="1"){
        $.getJSON("subjectieve_ervaringen/update", SE, function (data){
            console.log(data)
        })
    } else {
        $.getJSON("subjectieve_ervaringen/new", SE, function (data){
            console.log(data)
            localStorage.setItem("SEbool", "1")
        })
    }
}

// aanroepen en versturen van tijdbesteding data
// checkt of er al data van de gekozen week in de tijdbesteding tabel is
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

//laat de data van de tijdbesteding tabel, als deze bestaat zien in het forum
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

// voegt de data uit het forum toe aan de tijdbesteding tabel of wijzigt deze
function addupdTijdbesteding() {
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
    if (document.getElementById("geen_studie").checked){Tijdbesteding["studie_gem_uur"] = "0"}
    if (document.getElementById("geen_werk").checked){Tijdbesteding["werk_gem_uur"] = "0"}
    if (localStorage.getItem("Tijdbestedingbool") === "1"){
        $.getJSON("tijdbesteding/update", Tijdbesteding, function (data){
            console.log(data)
        })
    }else {
        $.getJSON("tijdbesteding/new", Tijdbesteding, function (data){
            console.log(data)
            localStorage.setItem("Tijdbestedingbool", "1")
        })
    }
}

// aanroepen en versturen van voeding data
// checkt of er al data van de gekozen week in de voeding tabel is
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

//laat de data van de voeding tabel, als deze bestaat zien in het forum
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
        document.getElementById("gezond_eten").value = Voeding.gezond_sch
    })
}

// voegt de data uit het forum toe aan de voeding tabel of wijzigt deze
function addupdVoeding(){
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
        "gezond_sch": document.getElementById("gezond_eten").value
    }
    if (localStorage.getItem("Voedingbool") === "1"){
        $.getJSON("voeding/update", Voeding, function (data){
            console.log(data)
        })
    } else {
        $.getJSON("voeding/new", Voeding, function (data){
            console.log(data)
            localStorage.setItem("Voedingbool", "1")
        })
    }
}

// aanroepen en versturen van cardiovasculair data
// checkt of er al data van de gekozen week in de cardiovasculair tabel is
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

//laat de data van de cardiovasculair tabel, als deze bestaat zien in het forum
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
        document.getElementById("apparaat_bloeddruk").value = Cardiovasculair.apparaat_bloed
        document.getElementById("apparaat_hartslag").value = Cardiovasculair.apparaat_hart
    })
}

// voegt de data uit het forum toe aan de cardiovasculair tabel of wijzigt deze
function addupdCardiovasculair(){
    event.preventDefault()
    id = localStorage.getItem("id")
    week = localStorage.getItem("week")
    Cardiovasculair= {
        "car_id": id,
        "week": week,
        "gewicht": document.getElementById("gewicht").value,
        "bovengrens": document.getElementById("bovendruk").value,
        "ondergrens": document.getElementById("onderdruk").value,
        "hartfrequentie": document.getElementById("hartslag").value,
        "apparaat_bloed": document.getElementById("apparaat_bloeddruk").value,
        "apparaat_hart": document.getElementById("apparaat_hartslag").value
    }
    if (document.getElementById("geen_weegschaal").checked){Cardiovasculair["gewicht"] = "0"}
    if (document.getElementById("geen_meter").checked){
        Cardiovasculair["bovengrens"] = "0"
        Cardiovasculair["ondergrens"] = "0"
        Cardiovasculair["apparaat_bloed"] = ""
    }
    if (localStorage.getItem("Cardiovasculairbool")==="1"){
        $.getJSON("cardiovasculair/update", Cardiovasculair, function (data){
            console.log(data)
        })
    } else {
        $.getJSON("cardiovasculair/new", Cardiovasculair, function (data){
            console.log(data)
            localStorage.setItem("Cardiovasculairbool", "0")
        })
    }
}