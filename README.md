# [Semesteroppgave 1: “Rogue One oh one”](https://git.app.uib.no/ii/inf101/21v/assignments)

I semesteroppgaven skal du implementere et spill inspirert av [Rogue](information/rogue.md). 

Oppgaven skal leveres inn via GitLab innen **fredag 12. mars kl. 16:00**. 

*Hvis du ikke har brukt GitLab enda, bør du gå gjennom tidligere lab oppgaver.*

Hvis du får mindre enn 6 poeng på én eller begge av semesteroppgaven **får du ikke ta eksamen**.  

Spillet er delvis skrevet; du skal endre eksisterende kode, legge til ny kode, og skrive tekst-svar. 

Det er 7 del-oppgaver i denne semesteroppgaven.

For utfyllende forklaring av Java-konsepter see [Oracle Java Tutorial](https://docs.oracle.com/javase/tutorial/ information/konsepter.md).

## Bekreft at du har lest viktig informasjon

Utfylende praktisk informasjon om semesteroppgaven og innlevering finner dere i [praktiskinfo.md](information/praktiskinfo.md). 

👉 Les [praktiskinfo.md](information/praktiskinfo.md) og åpne java filen `inf101.GetStarted.java` for å bekrefte at du har lest informasjonen. JUnit testene vil ikke virke før du har gjort dette.

`add-commit-push`

### Tester
Det er mange tester som følger med semesteroppgaven, noen passerer (er grønne) og noen feiler (er røde).
De fleste oppgavene har en eller flere tester som i utgangspunktet feiler og skal passere når oppgaven er gjort. Som alltid, så betyr ikke det at en test passerer nødvendigvis at alt er riktig, men det sier deg at du er på riktig vei. 

Vi anbefaler at du løser hver oppgave ved å først kjøre testen(e) og sjekke om de feiler. Så løser du oppgaven og kjører testen(e) på nytt og sjekker at de passerer. Så comitter du og pusher. Dette kalles [(testdrevet utvikling, TDD)](https://en.wikipedia.org/wiki/Test-driven_development).

_Eclipse-tips: Forsvinner testene i stedet for å bli grønne? Trykk på ⋮menyen i JUnit tabben og slå av Show Failures Only – det er mye er motiverende å se at det dukker opp grønne bokser når vi får til noe!_

## Oppgave 1 - Abstrakte Ting

_I denne oppgaven skal du bli kjent med interfacet `IItem`. Der oppgavene ber om tekst-svar, skal du skrive disse i innleveringsfilen [Svar.md](Svar.md)._

### 1.1) Rogue-“Ting”
Les [beskrivelsen av Rogue 101](information/rogue.md). Hvordan ville du abstrahert “ting” fra beskrivelsen av Rogue-spillet? 

👉 Skriv ned 5 egenskaper eller metoder du mener må være del av et interface som abstraherer “ting” på spillbrettet og en kort begrunnelse for hver av dem. Skriv svaret i [Svar.md](Svar.md).

Ikke gå videre før du har skrevet ned svaret ditt under oppgave 1.1 i [Svar.md](Svar.md) og gjort `add-commit-push` i git. Du kan gå tilbake og endre på svaret senere. Poengsummen baseres på det _siste_ svaret som lastes opp.

### 1.2) IItem.java
Åpne interfacet `IItem`. Sammenlikne metode-deklarasjonene i interfacet med de 5 egenskapene du skrev ned i 1.1 og med beskrivelsen av spillet fra [Rogue](information/rogue).

👉 Skriv en kort tekst i [Svar.md](Svar.md) som beskriver hvordan IItem *abstraherer* minst 5 egenskaper ved spill-elementer av typen “ting” i et Rogue-spill. Skriv svaret i [Svar.md](Svar.md).

🤔 - Selv om du kanskje skrev ned andre egenskaper enn det som ligger i IItem, så betyr det gjerne ikke at noen av delene er feil. For eksempel så har hver ting en posisjon, men i vår kode er det spill-kartet som holder styr på det, ikke tingen selv. Med et annet design kunne det like godt være en egenskap i IItem.

_Tips: Hvis du vil endre på svar du allerede har pushet så kan du fritt gjøre det. Bare bruke en beskrivende commit-melding, f.eks. “Forbedret oppgave 1.1 etter gruppeleder forklarte abstraksjon.” Det er siste versjon før fristen som teller._

### 1.3) Carrot.java
Klassen `Carrot` implementerer interfacet `IItem` og representerer en Gulrot-“ting” på spillkartet. Et objekt av typen `Carrot` er altså på et vis både en abstraksjon av en _ekte_ gulrot, og av et spillobjekt fra Rogue. Åpne `Carrot`-klassen og se hvordan den implementerer metodene fra `IItem`. 

✅ `ItemTest:testHandleDamage`

Hvilke egenskaper ved en  _ekte_  gulrot finnes i den abstrakte `Carrot`-klassen, og hvilke egenskaper har en gulrot som  _ikke_  finnes i `Carrot`-klassen? 

👉 List opp 3 egenskaper fra oppgave 1.2 som `Carrot`-klassen implementerer, og beskriv hvordan den implementerer dem. Skriv svaret i [Svar.md](Svar.md).

👉 List opp 1 egenskap ved en  _ekte_  gulrot som er representert i `Carrot`-klassen og 1 some  _ikke_  er det. Skriv svaret i [Svar.md](Svar.md).

Metoden `Carrot::handleDamage()` er ikke rett implementert. Vi tenker oss at gulrøtter blir skadet når en Rabbit spiser på den. Rabbit gir gulroten beskjed om hvor mye den spiser ved å kalle `Carrot::handleDamage()` og Carrot sin health går ned tilsvarende

(Denne notasjonen referer til metoden `handleDamage()` i klassen `Carrot`.)

 Du kan kjøre denne testen alene ved å høyreklikke på metodenavnet → Run As → JUnit Test).

👉 Implementer `handleDamage()`. Sjekk om den funker ved å kjøre `CarrotTest` og `IItemTest:testHandleDamage`. 

_Tips: I Eclipse kan du se dokumentasjonen til en metode (f.eks. handleDamage(), som er dokumentert i IItem) ved å la muspekeren hvile over metodenavnet. Ved implementasjonen av handleDamage() finner du også en liten trekant i margen som du kan trykke på for å gå til interfacet._

### 1.4) Spillobjekter
Hvilke andre klasser implementerer `IItem`? 

👉 List opp alle klassene som implementerer dette interfacet. Skriv svaret i [Svar.md](Svar.md).

_Tips: høyreklikk på IItem og velg Open Type Hierarchy for å få opp en liste av referanser til IItem-deklarasjonen._
(I IntelliJ heter det “Find usage”)

### 1.5) Gold.java

Nå skal du utvide støtten for spill-objekter til å også kunne representere gull. 

👉 Opprett en klasse `Gold.java` som implementerer interfacet `IItem` i samme mappe som `Carrot.java`. 

Det finnes tester i `IItemTest.java` for gull som krever at **klassen Gull har symbolet 'G'**. (se hvordan de andre typene IItem har implementert SYMBOL og gjør det likt.)
Testene i `IItemTest.java` vil ikke virke ennå, vi skal jobbe videre med de testene i Oppgave 3. 
Men vær sikker på at du bruker rett symbol på Gold klassen ellers får du problemer i Oppgave 3.

For å implementere metodene kan det være nyttig for deg å se på hvordan de andre klassene for spill-objekter fra 1.4 implementerer dem. 

`add-commit-push`

*Protip: [default metoder](information/konsepter.md) trenger ikke å implementeres av sub-klasser, med mindre man ønsker annen funksjonalitet enn det som tilbys av default-implementasjonen.*

Sjekk at filen `Gold.java` finnes i ditt online repositorie i samme mappe som `Carrot.java` **FØR** du går videre. Det vil spare deg for trøbbel og bugs senere. 

_Tips: Du kan velge mer eller mindre tilfeldige verdier for *max health* og *defence* – forløpig har vi ikke tenkt på om gull skal kunne skades eller angripes. For `getSize()`, bør du sette den til å være større enn andre items, slik at den blir synlig på kartet (det er den største tingen som blir vist / plukket opp)._

## Oppgave 2 - The Rabbit
_I Oppgave 1 ble du kjent med interfacet `IItem` og hva de ulike metodene brukes til. Vi skal fortsette med å se på interfacene for spillobjektene, og i denne oppgaven skal vi se på `IActor` og eksempler på en “aktor” i spillet vårt._

Husk at du kan alltids sjekke ut dokumentasjonen i linkene øverst i denne filen dersom du syns det er vanskelig å forstå hvordan disse interface-bitene henger sammen.

### 2.1) IActor.java
Se på `IActor.java` i `rogue101.objects`-pakken. 
Legg merke til at `IActor` *utvider* et annet interface.

👉	Hvilket interface utvider IActor, og hva betyr dette for klasser som skal implementere `IActor`? Skriv et kort og konkret svar i `Svar.md`.


_(Vanskelig? Vi minner nok en gang om dokumentasjons-linkene øverst i denne filen)._

### 2.2) doTurn()
Se på `Rabbit.java`. Hvordan bestemmer Rabbit hvilken vei den skal gå i `doTurn()`? 

👉 Skriv et kort og konkret svar i `Svar.md`.

### 2.3) getPossibleMoves()

Et naturlig spørsmål en `IActor` kan stille kartet (via `IGameView`) er “Hvilke muligheter har jeg til å bevege meg?”. Metoden `GameMap::getPossibleMoves` gir svar på dette ved å returnere en liste med de retningene som en rolle har *lov* til å gå. Se eksempel på bruk av denne i `Rabbit::performMove()`. Per nå så returnerer `getPossibleMoves` bare en liste med retningen 'EAST'.

👉 Implementer metoden `GameMap::getPossibleMoves`.

✅ `GameMapTest:testGetPossibleMoves` skal passere når du er ferdig med denne oppgaven.


🤔 Ligner dette på noe du har gjort på tidligere ukeoppgave? 

_Tips:Det eksisterer allerede en metode `GameMap::canGo`._

### 2.4) Finne gulrot
I `doTurn()` flytter Rabbit på seg dersom den ikke allerede har brukt opp turen på å spise noe. Som du ser er ikke flyttingen så veldig smart – hva om det ligger en gulrot rett ved siden av kaninen?

Metoden `doTurn()` tar et argument av typen `IGameView`, som er et relativt stort interface. Gjør deg kjent med `IGameView`. 

Hvordan kan en `IActor` bruke `IGameView` til å hente informasjon om miljøet sitt og utføre handlinger som påvirker andre elementer i spillet?
Du trenger ikke å skrive svaret i svar-filen, men merk at forståelsen din av `IGameView` vil ha mye å si for resten av innleveringen. 

_Tips: Hvis du er i `Rabbit` så kan du `ctrl`/`cmd`-klikke på_  `IGameView`  _for å hoppe dit._

👉 Gjør Rabbit (litt) smartere ved å se om det ligger en gulrot på en av de ledige plassene ved siden av den på brettet, og gå dit dersom det gjør det.

✅ `RabbitTest` skal passere når du er ferdig med denne oppgaven.

## Oppgave 3 - Objektfabrikken
_I denne oppgaven skal du se på hvordan IItems blir opprettet og lagt til på brettet. Du skal utvide spillet til å støtte gull-objektene fra Oppgave 1 og endre koden så den følger et viktig objekt-orientert design prinsipp._

### 3.1 ItemFactory.java
For å lage nye objekter av en klasse i Java så må vi kalle på konstruktøren til klassen. Hvis vi vet at vi trenger en gulrot kaller vi på `new Carrot()`, og hvis vi vet at vi trenger en edderkopp kaller vi på `new Spider()`. Når spillelementene skal bygges er det derfor viktig å få tak i riktig konstruktør. 

For å løse dette uten å rote til koden vår med referanser til konkrete klasser og symboler, bruker vi et kjent  _Design Pattern_  som heter  _Factory Pattern_ . Et  _Design Pattern_  er en standardisert måte å løse et problem som stadig dukker opp når man programmerer objektorientert - uavhengig av programmeringsspråk. De gjør at man ikke må finne opp hjulet på nytt, og gjør det også lettere for andre å forstå hva du har gjort ettersom de gjerne har sett  _mønsteret_  før. (Sjekk gjerne ut den populære boken [Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns))

Factory Pattern går ut på å ha en metode i en “Factory”-klasse som vet vet hvilken konstruktør den skal velge avhengig av argumentet den får. I vårt tilfelle har vi en klasse `ItemFactory` som gjør dette.

👉 Hvilket symbol representerer et `Player`-objekt og hvilken representerer `Dust`? Skriv svaret i [Svar.md](Svar.md) og i hvilken klasse og metode du fant svaret.

Fabrikken mangler et valg for å legge til `Dust`. 

✅ `IItemTest::testItemFactoryCreatesDust()`  
✅ `IItemTest::testItemFactoryCreatesGold()`

👉 Legg til støtte i fabrikken for å opprette Dust-objekter. 
👉 Legg til støtte i fabrikken for å opprette Gull-objekter. 

✅ Når du er ferdig med denne oppgaven skal alle testene i `IItemTest` passere.`
### 3.2 S.O.L.I.D.
[SOLID](https://en.wikipedia.org/wiki/SOLID) er en forkortelse for fem prinsipper som gjør objektorientert kode forståelig, fleksibel og lett å vedlikeholde. Det første prinsippet – prinsippet om  _Single Responsibility_  – sier at:

> “Each class should have a single responsibility and that responsibility should only lie with that class.”

Hvis vi ønsker å endre symbolet til f.eks. Rabbit fra `'R'` til `'r'` i hele programmet så måtte vi gjort endringer i mer enn én klasse.

👉 Hvorfor måtte vi endret på mer enn én klasse for å endre symbolet til Rabbit? Skriv svaret i [Svar.md](Svar.md).

Når vi må endre flere klasser for å gjøre én endring betyr at vi har brutt det første prinsippet i SOLID. Grunnen til at dette ikke er bra, er at hvis Rolf som ikke kjenner koden skal gjøre denne endringen om 3 år, og bare gjør endringen i én av klassene (hvordan kunne ha visst at han måtte endre flere plasser?) så ville det vært en feil i programmet.

👉 Endre koden i ItemFactory slik at den ikke  _inneholder_  informasjon om hvilket symbol som hører til hvilken klasse. (Merk at med inneholder så mener vi at symbolet er hardkodet i klassen).

👉 Hvorfor er problemet med  _Single Responsibility_  nå fikset? Skriv en kort forklaring i `Svar.md`.

### 3.3 Gold.java
I denne oppgaven skal du legge til støtte for spillobjekter av typen Gold. 
Du må ha gjort oppgave 1 og tidligere deler av oppgave 3 for å kunne løse denne oppgaven. 

Åpne IItemTest i pakken `inf101.rogue101.objects`.

👉 Legg til et nytt objekt av typen Gold i `IItemTest::getTestData()`-metoden etter samme mønster som for de andre objektene. Kjør testene. 

👉 Finn filen `level1.txt` i `resources/inf101/rouge101/map/maps`, åpne den og erstatt noen av symbolene med gull-symbolet du valgte i Oppgave 1. Lagre filen. Kjør programmet. Gull-symbolene skal vises på skjermen der du la dem inn i kartet. 

## Oppgave 4 - Et smartere kart

### 4.1 getNeighbourhood

Et annet spørsmål som `IActor`s kan stille kartet er hva som befinner seg i *området* rundt seg. Til dette har vi en metode `GameMap::getNeighbourhood` som tar en lokasjon og et heltall `dist` anse som argument, og returnerer alle lokasjonene innen `dist` steg fra lokasjonen.

F.eks. dersom en rolle står på en `loc`, og 
- spør etter nabofeltet med `dist=1`, så skal de 8 feltene rundt `loc` returneres. 
- spør om nabofeltet med `dist=2`, så skal de 8 lokasjonene rundt `loc` returneres sammen med de 16 lokasjonene som er et steg lenger ut.
- osv.

👉 Implementer metoden `GameMap::getNeighbourhood`.

✅ `GameMapTest::testGetNeighbourhoodCardinality`  
✅ `GameMapTest::testGetNeighbourhoodEdgeCardinality`

### 4.2 En bedre getNeighbourhood

Når en rolle lurer på hvilke lokasjoner som befinner seg i nærheten, så er den ikke interessert i lokasjoner med vegger. 

👉 Forbedre metoden `GameMap::getNeighbourhood` slik at den ikke returnerer lokasjoner som er vegger.

✅ `GameMapTest::testGetNeighbourhoodDoesNotReturnWall`

### 4.3 Sort Neighbourhood
Gå til `IGameMap::getNeighbourhood` og sorter listen med lokasjoner før den returneres. `IList::sort` trenger en `Comparator<Location>`, her kan du bruke `LocationComparator` som tar en lokasjon og sammenligner distansene til to andre lokasjoner.

Du kan bruke: `Collections.sort(neighborhood,new LocationComparator(loc));`

👉 Forbedre metoden `GameMap::getNeighbourhood` slik at den returnerer lokasjoner i sortert rekkefølge.

✅ `GameMapTest::testGetNeighbourhoodSorted`

### 4.4 getReachable
Noen ganger kan en lokasjon være nært men vanskelig å nå fordi andre IItem er i veien. Du skal finne de lokasjonene som er mulig å nå på `dist` antall steg. 

Dette kan være vanskelig og vi regner ikke med at alle får til denne oppgaven.
Du kan gjøre resten av oppgavene selv om denne oppgaven ikke er ferdig.

👉 Implementer metoden `GameMap:getReachable` slik at den returnerer de lokasjoner som er mulig å gå til på `dist` antall steg.

_Tips: For å finne de lokasjonene som kan nåes på 1 steg kan du kanskje gjenbruke noe fra_
 `getPossibleMoves()`  
Er det lurt med en helpemetode `List<Location> expand(List<Location> found)` som legger til alle lokasjoner du kan nå med et ekstra steg?

✅ `GameMapTest::testGetReachableDoesNotWalkPastWalls`

## Oppgave 5 - Smartere kaniner

La oss gå tilbake til Rabbit. I Oppgave 2 gjorde du denne litt smartere enn den var ved å se om det eksisterte gullrøtter i en av lokasjonene den kunne nå med et move.

Nå som vi vet litt mer om Game og GameMap kan vi gjøre den enda smartere ved å se om det eksisterer noen gullrøtter i nærheten av en Rabbit.

### 5.1 Test Rabbit strategy
Kjør testene til prosjektet, og sjekk hvor mange nivåer kaninen din klarer seg på i `TestRabbitStrategy`. Merk at ettersom kaninen din kanskje oppfører seg litt “tilfeldig”, så kan testresultatet variere fra gang til gang.

Vi ønsker å gjøre kaninen enda smartere ved å lukte etter gullrøtter i nærheten ved å bruke metodene fra oppgave 4 som gir alle items som er maks `dist` steg unna.
Men før vi kommer så langt skal vi se om vi kan finne ut hva kaninene gjør.

👉 Gå til `inf101.rouge101.Main` og bytt om hvilken applikasjon som kjøres og se hvordan din kanin gjør det.

_Tips: Kaniner skal spise opp guleroten og få energi av det, så skal gulleroten forsvinne. Det er store gullerøtter så kaninen spiser ikke hele gulleroten på en gang, noe blir liggende igjen og så kommer den kanskje igjen senere for å sise resten. Hvis gulleroten ikke forsvinner er det kanskje noe galt i gullerot klassen?_

### 5.2 Get direction
Bruk `IGameMap::getNeighbourhood` eller `IGameMap::getReachable` fra oppgave 4  til å hente alle synlig lokasjoner fra kaninen sin posisjon, og sjekk om det ligger noe gullrøtter i nærheten. Beveg Rabbit i retning av gulroten dersom den kan se noen. Det er altså 3 steg du må gjøre:
* Finn en gullerot i nærheten
* Finn en retning som går mot denne gulleroten.
* Hvis kaninen kan hoppe denne veien, så gjør den det.
Dette kan erstatte eller komplimentere at kaninen ser etter en gulrot ved siden av seg.

👉 Implementer hjelpemetoden `Location::directionTo` og gjør slik at din Rabbit bruker metoden `IGameView::getDirectionTo`.


### 5.3 Rabbit AI
Kjør testene på nytt, og se om kaninen klarer seg bedre nå.
Klarer du å få enda flere tester i `TestRabbitStrategy` til å passere så kan det gi god uttelling for kreativitet. (Du trenger ikke få alle testene til å passere for å få full pott på denne.)

👉 Forbedre Rabbit sin AI. Skriv i `svar.md` hva du har gjort/prøvd/tenkt.

_Tips 1: Poenget er ikke å gi Rabbit superkrefter, det er å få Rabbit til å oppføre seg normalt i spillet. Du har kanskje sett spill der en AI sitter fast og bare går rett i en vegg eller lignende? Det ser ikke profft ut._

_Tips 2:_  `GameView::getNearbyItems`  _er allerede implementert, men kanskje du kan gjøre en liten endring på implementasjonen for å gjøre den litt mer anvennelig?_

## Oppgave 6 - Player klassen

Player-klassen er litt mer avansert enn de andre klassene. Denne klassen har blant annet en `keyPressed`-metode som ser på input fra tastaturet, og velger hva den skal gjøre ut i fra hvilken tast brukeren har trykket på.

Tasten `P` og `D` skal henholdsvis plukke opp og legge fra seg ting den står på på kartet.

Det finnes en del nyttige metoder i `IGameView` som du kan få bruk for her. `IGameView` kan f.eks. la deg plukke en spesifikk ting fra lokasjonen du står på (gitt at tingen ligger på denne lokasjonen), la deg legge en ting på kartet, og la deg spørre om hvilke ting (som ikke er `IActor`) som finnes på den lokasjonen du står på.

I denne oppgaven får du *noe* uttelling for at Player kan plukke opp 1 ting, og *full* uttelling dersom Player kan plukke opp flere ting. Hvis Player kun kan plukke opp 1 ting, så må den legge dette fra seg dersom den prøver å plukke opp noe annet (slik at tingen han hadde ikke forsvinner fra spillet).

### 6.1 Player pickUp

“Plukk opp” skal prøve å plukke opp den første tingen den finner på lokasjonen den står på. (Igjen, hvis din Player kun kan holde 1 ting, så skal denne tingen legges tilbake på kartet, og den nye tingen skal plukkes opp).

“Har ting” sjekker om spilleren holder ett spesifikt objekt. NB: objekter er like hvis de *er* det samme objektet (objekt1 == objekt2). 

Metodene er delvis implementert men fungerer ikke helt, kan du finne ut hva som mangler?

👉 Gjør ferdig `Player::pickUp`.

👉 Implementer `Player::hasItem`.

### 6.2 Player drop
“Dropp” skal legge fra seg tingen den har på lokasjonen den står på. Hvis den holder på flere ting skal den legge fra seg *den første* tingen den plukket opp.

👉 Implementer `Player::drop`.

### 6.3 Player status
Nå kan den som spiller se hvor mange liv spilleren har ettersom `showStatus`-metoden informerer om dette. Vi ønsker å også kunne se hva spilleren eventuelt har plukket opp.

👉 Endre `showStatus`-metoden i `Player`-klassen til å *også* si hvilken ting spilleren har dersom den har noe. Hvis spilleren har plukket opp flere ting kan disse listes, komma-seperert. (Hold statusmeldingen til én linje - ikke bruk newline-karakter).

Eks:
  
>    Player has 100 hp left holding items(s) carrot, carrot

## Oppgave 7 - Utvid programmet

Oppgave 1-6 har hjulpet deg med å bli kjent med de ulike spillelementene og hvordan de interegarer med spillet, med spillkartet og med hverandre. Herfra og ut kan du gjøre spillet til ditt eget, og det er bare kreativiteten som setter grenser for hva du kan gjøre.

Legg merke til at Carrot, Rabbit og Spider ligger i en pakke `objects`. Disse er eksempler på spillobjekter, og ikke noen “fasit” på hvordan spillet skal være. 

Du skal sørge for at utvidelsen av spillet ikke ødelegger eksisterende kode, det vil si at du skal få testene til å passere selv om du endrer på spillet.

 Utvid spillet med en funksjonalitet. Gjør det på følgende måte:

1. Skriv en liten plan for utvidelsen i `Svar.md`
2. Opprett de klassene du trenger og lag noen JUnit-tester for planen din
3. Fullfør implementasjonen. Sørg for å ha ryddige kommentarer og gode navn på klasser/metoder/variabler.
4. Skriv kort om uførelsen i `Svar.md`.

Det er bare kjekt om du legger inn flere funksjonaliteter/utvidelser. Pass på å få med det du har gjort i `Svar.md` slik at den som retter legger merke til det og kan gi poeng for det. Men det er begrenset hvor mange poeng du kan få for denne oppgaven så du trenger ikke gjøre så veldig mye.

Vi ønsker å se **ny** funksjonalitet så tenk litt på hva du kan få til, det er ikke antall kodelinjer som avgjør poengsum på denne oppgaven. Kreativitet blir belønnet.

Trenger du litt starthjelp? Her er noen eksempler på noen utvidelser man kan ha (men ikke la disse sette begrensinger for hva du kan gjøre):

- Legg til en ny type IActor med helt annen oppførsel enn de som finnes. Hvordan interagerer denne rollen med andre roller og ting på kartet?
- Utvide Rabbit til å kunne parre seg dersom den står inntil et annet Rabbit, litt ala Game Of Life. Dette bør være noen kriterier for at dette skal skje for å unngå å dekke hele kartet med kaniner.
- Legg til nye IItem som spilleren (og evt. andre) kan plukke opp for å bli bedre, f.eks. sverd gir økt attack og damage, rustning gir økt defence, etc.
- Sjekk ut støtten for Emoji ved å se på klassen `EmojiFactory`
- Sjekk ut `level1.txt` i `map.maps`-pakken, og lag et eller flere nye kart her. Du bruker kartet ved å endre strengen `maps/level1.txt` i `Game`-konstruktøren. (Nytt kart alene gir ikke full uttelling, men kan være gøy å gjøre sammen med noe annet).
- Det er en gammeldags løsning å spille med piltastene. De fleste dataspill støtter i dag WASD (W: nord, A: vest, S: sør, D: øst), så kanskje vi kan støtte begge deler? og noe lettere enn å bruke 'P' og 'D'? (Obs husk å endre tester også.)
