# Citizen-science web service
In deze read me file wordt uitleg gegeven 
over de citizen-science webservice applicatie
en hoe deze gebruikt en geïnstalleerd dient te worden

## Installatie
Voor het installeren van de applicatie moet als eerste 
de database en hasura zijn geïnstalleerd zoals 
uitgelegd in de documentatie.

Na het installeren van hasura en de database dienen
de volgende stappen te worden uitgevoerd voor het 
werkende krijgen van de webservice.
1. Het aanpassen van de hasura.baseURL en 
hasura.secret. De hasura.baseURL en de 
hasura.secret dienen te worden aangepast zodat 
deze verwijzen naar de hasura api. De hasura.basURL 
en hasura.secret zijn te vinden in de 
src/main/resources/application.properties. 
De waardes die ingevuld moeten worden in bij de 
hasura.baseURL en hasura.secret zijn te vinden in 
de hasura console. Open de console de hasura.baseURL
wordt gevormd door achter de console url /api/rest/ 
toe te voegen. in het geval van localhost wordt 
dit http://localhost:8080/api/rest/. De hasura.secret 
is te vinden onder het kopje x-hasura-admin-secret.
2. Na het toevoegen van de hasura.basURL en 
hasura.secret kan de applicatie gerund worden. 
Dit kan worden gedaan door in de 
src/main/java/com/example/server de serverApplication 
te runnen.

Als de server correct is ingesteld wordt de 
applicatie nu gerund op het domein adress van 
de server.

## Aanpassingen en toevoegingen van data
Op dit moment is het nog vrij lastig om data
aan te passen of toe te voegen aan de webpagina.
Op dit moment zijn de vragen van het forum nog
hard gecodeerd in de HTML-files, waardoor het
aanpassen van deze vragen ook hard gecodeerd moet
worden. Voor het toevoegen van data zoals
bijvoorbeeld toegangscodes voor studenten moet dit
op dit moment nog in de database/hasura console
handmatig worden ingevoerd en ook het verwijderen van data moet
handmatig via de database/hasura console. 

## Gebruik
Hieronder is de uitleg te vinden over het doel van de 
applicatie en het doel van de bestanden in de applicatie.

### Doel applicatie
De applicatie is gemaakt met als doel om data te
verzamelen voor geneeskunde studenten zodat zij
statistische analyses kunnen uitvoeren op deze
verzamelde data. De studenten kunnen door middel van
een persoonlijke toegangscode informatie invoeren in
het forum, waardoor de dataset wordt ingevuld 
zodat zij op deze dataset analyses kunnen uitvoeren.
Het forum dient wekelijks te worden ingevuld over
een periode van 6 weken. Daarna kunnen de studenten
bij de resultaten een csv-bestand downloaden,
waarmee in spss analyses kunnen worden uitgevoerd.
Bij het profiel kan de student data inzien die zij
eerder in het forum hebben ingevuld.

### Doel bestanden
Hieronder wordt het doel van de bestanden uitgelegd
onderverdeeld in het type bestand.

#### Java classes
De java classes zijn de backend van de applicatie
en zijn bedoeld voor het runnen van de applicatie
en de verbinding met de database.
* Apicontroller: De Apicontroller is bedoeld om de 
basis elementen vast te leggen voor de connectie
met de database. Hierin wordt gespecificeerd welke
connectie moet worden gelegd en op welke manier
deze connectie wordt gelegd.
* HasuraApicontroller: De HasuraApicontroller zorgt
voor de verbinding met alle tabellen in de database,
zodat de juiste GET, POST, DELETE, etc. request
verstuurd worden.
* ServerApplication: De ServerApplication zorgt ervoor
dat de HTML-files gehost worden zodat door andere
devices dan de server gecontacteerd kunnen worden.

#### Javascript files
De javascript files zorgen voor de connectie met de
HTML-files. Deze zorgen voor de visuele effecten op
de files en zorgen ervoor dat de juiste informatie
uit de files wordt gehaald, zodat deze doorverbonden
kan worden naar de database.
* controller_connection: De controller_connection
javascript zorgt ervoor dat de juiste informatie
wordt opgevraagd en teruggestuurd naar de database.
Ook zorgt het ervoor dat de data wordt getransformeerd
naar JSON-objecten zodat deze gelezen kan worden 
door de database en vice versa.
* visual_elements: De visual_elements javascript
zorgt ervoor dat de correcte visuele elementen 
te zien zijn op de HTML-file.

#### HTML-files
De HTML-files zorgen voor het weergeven van de tekst
en de correcte interacties met de pagina. Ze zorgen
voor het weergeven van invoer velden, knoppen, teksten
en andere vormen van interactie met de webpagina.
* index: De index is de eerste pagina die te zien is
wanneer er verbonden wordt met de applicatie. In dit
geval is dat het inlogscherm waarop de toegangscode
kan worden ingevoerd.
* invoeren: De invoeren pagina is het forum, waarop
studenten de informatie invoeren die in de database
terecht komt.
* profiel: Op de profiel pagina kunnen studenten de
informatie zien die zij hebben ingevoerd in de 
database.
* resultaten: Op de resultaten pagina kunnen 
studenten de csv file downloaden waarmee zij 
statistische analyses kunnen uitvoeren.

#### CSS files
De stylesheet.css zorgt voor de visuele layout van
de HTML-files. Het zorgt voor de vorm van de layout,
de gebruikte kleuren en de gebruikte foto's.
