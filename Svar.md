# Svar på spørsmål

## Kommentarer
I denne semesteroppgaven har jeg diskutert om mulige løsninger og utfordringer, og det praktiske som ligger til grunn, med Sanna Rosøy, Eiril Ugulen, Martin Svestad, og Veslemøy Jørgensen.

## Spørsmål

## Oppgave 1 - Abstrakte Ting

### 1.1) 

1. Bevegelse - alle tingene kan beveges/flytte
2. Farge - alle tingene kan ha en farge
3. Størrelse - alle tingene må ha en størrelse
4. HP-verdi - alle tingene må ha en HP-verdi
5. Navn - alle tingene må ha en form for navn e.l. for å kunne identifisere tingene

### 1.2) 
1. getSize() - forteller oss hvor mye plass som vil bli brukt til ting/aktøren. Forteller størrelsen til tingen. 
2. getDefence() -  forteller oss hvor vanskelig det er å slå/ta tak i et objekt, hvor sterkt forsvaret til tingen er.  
3. getCurrentHealth() - forteller oss hvor mye skade tingen eller aktøren tåler før den/det blir ødelagt/drept. 
4. getEmoji() - henter emoji-symbol for en ting.  
5. getShortName() - forteller oss en kort versjon av det lange navnet.  


### 1.3)
Carrot-klassen implementerer desse tre egenskapene:
1. getCurrentHealth()
2. getDefence() 
3. getEmoji()
De blir implementert gjennom Interfacet IItem.
4. getSize() - en ekte gulrot har også en størrelse
5. getEmoji() - en ekte gulrot har ikke en emoji, men det har en gulrot i dette spillet

I handleDamage():
Dersom amount er større enn/er lik getCurrentHealth, vil damage bli satt lik til getCurrentHealth,
slik at hp ender opp med å bli null. Dette fordi den totale skaden ikke kan ende opp som ett negativt tall.  
Dersom amount er mindre enn null, får vi exception fordi skaden kan ikke være ett negativt tall.


### 1.4)
draw(IBrush painter, double w, double h)
getArticle()
getCurrentHealth()
getDefence()
getEmoji()
getGraphicTextSymbol()
getHealthStatus()
getLongName()
getMaxHealth()
getShortName()
getSize()
getSymbol()
handleDamage(int amount)
isDestroyed()


## Oppgave 2 - The Rabbit

### 2.1)
IActor utvider interfacet IItem. Det vil si at klasser som implementerer IActor, får også med interfacet IItem. 

### 2.2)
Kaninen vil stå i ro og hvile dersom den er mett og ikke trenger mat, og vil bevege seg tilfeldig rundt dersom den verken er sulten eller er mett. Etterhvert som kaninen beveger seg, vil den forbrenne energi og til slutt bli sulten igjen, og deretter spise, så hvile.

### 2.3)
getPossibleMoves():
Her sjekker vi alle de mulige directions vi kan gå, og bruker deretter canGo() metoden for å se om det er mulig. Dersom det er mulig, blir de lagt til i den nye listen.

### 2.4)
doTurn():
Her må vi først sjekke om kaninen får mat, og dersom den får mat, spiser den. dersom kaninen ikke får mat, må den lete etter en gulrot. Her bruker vi deler av eatIfPossible() metoden, og sjekker om Item er en instans av en gulrot. Dersom den er en gulrot, får vi en ny direction, og utfører "flyttet" (performMove()).

## Oppgave 3 - Objektfabrikken

### 3.1)
Et Player-objekt er representert av symbolet '@', og dette fant jeg i ItemFactory-klassen i metoden createItem().
Dust er representert av '.' , og dette fant jeg i klassen dust, i metoden getSymbol().

### 3.2)
For å endre symbolet til rabbit måtte vi endret i flere klasser fordi alle klassene inneholder informasjon om hvilket symbol som tilhører hvilken klasse.
Problemet med single responsibilty er fikset fordi symbol er kun spesifisert inne i en klasse, og ikke flere. ItemFactory må gå inn i klassen den skal hente symbolet fra, og dette gjør ItemFactory mer generalisert. 

### 3.3)
La til Gold.java under objects, og la til gold i kartet level1.txt. 

## Oppgave 4 - Et smartere kart

### 4.1, 4.2, 4.3)
Først må vi lage en ny arraylist under getNeighbourhood() som vi kan bruke senere i koden. Deretter må vi ha to for-løkker, en for kolonner og en for rader. Deretter har vi en if-setning, som hjelper oss med å unngå duplikasjoner i svaret vi får returnert. 
Så har vi en variabel, temporaryLoc, til å foreløpig lagre lokasjonen til kolonnen/raden som vi har iterert over. Dersom den foreløpige lokasjonen ikke er på rutenettet, fortsetter loopen. For å unngå å få lokasjoner med vegger, bruker vi en if-setning igjen. Dersom den foreløpige lokasjonen inneholder vegger, starter loopen på nytt. Hvis ikke, fortsetter loopen, og alle de foreløpige lokasjonene, som ligger i griden, og som ikke inneholder vegger, blir lagt til i en annen liste som til slutt blir returnert. I tillegg sorterer vi svaret vi får returnert ved hjelp av collections.sort. 

4.4)
I getReachable():
Lagde en ny liste, og la til startpunkt, og sendte den inn i hjelpemetoden findPath(). I findPath() sender vi inn reachable locations og distansen. Denne metoden er rekursiv, og avslutter når dist=0. Det første den returnerer er alle lokasjoner for ytterste distansen i forhold til startpunkt. Samler så desse lokasjonene i path, og returnerer når dist=1. Da har den iterert dist antall ganger. Resultatet blir da alle reachable lokasjoner innenfor gitt distanse. 


## Oppgave 5

### 5.2, 5.3)

Jeg endret doTurn() i Rabbit.java da den bare fann gulrøtter lokalt på sin egen lokasjon, og derfor aldri søkte i nabocellene. Opprettet et carrot-objekt som da getDirectionTo() skal søke etter. I getDirectionTo() har vi en for-løkke for visionFactor der den søker 1 ut. Om den ikke finner noe, går den ett steg lenger ut osv. Når den søker for den gitte distansen, aka i, så sjekker den alle reachable lokasjonene, som den får fra getReachable(). Når den har reachable lokasjoner, fjerner vi startlokasjonen, og har en ny for-løkke. Her søker den etter en lokasjon som inneholder gulrøtter. om den finner en gulrot, så finner den gridDistanceTo() og setter den lik til dist. Den returnerer retningen til gulroten. Dersom den nye retninger er lik null, bruker vi selectMove()-metoden. 
I selectMove() bruker vi getPossibleMoves og sjekker om det er noen gyldige flytt man kan gjøre. Dersom det ikke er det, returnerer vi center, som er den posisjonen vi allerede stod på, og kaninen vil bli stående i ro. 
I Location:DirectionTo(): Setter directionToTake=gridDirection(center) slik den ikke beveger seg om den ikke finner en retning til en gulrot. I for-løkka går den gjennom alle 8 retningene, og dersom det fins en nabo som er lik der gulroten er, returnerer den retningen den skal gå. Om det ikke er tilfellet, så finner den nabo, og sjekker distansen til gulroten, da den naboen som har en distanse mindre enn startposisjonen til kaninen. Startposisjonen vil da bli lagt til i DirectionToTake. Resultatet er at den returnerer retningen til den naboen med minst distanse til gulroten, og derfor riktig retning. 
  
Endret visionFactor=2 i gameview.java. Det er urealistisk at kaninen skal ha noe bedre visionFactor enn dette. 


## Oppgave 6

### 6.1)
I pickUp() tar vi inn game som argument, og putter alle tingene brukeren plukker opp i backpack-listen, og printer dette til konsollen.
I hasItem() sjekker vi om spilleren holder ett spesifikt objekt, og returnerer true om den gjør det.


### 6.2)
I drop() legger brukeren ifra seg tingen den har på lokasjonen den står på, og dersom den har flere ting i backpacken, legger den ifra seg den første tingen brukeren plukket opp, dvs første tingen i listen.

### 6.3)
I showStatus() printer vi ut hvor mye hp spilleren har igjen, og eventuelt hva spilleren har plukket opp. Dersom backpack ikke er tom printer vi hva som er igjen i backpacken ved hjelp av hjelpemetoden pickedItem().
I pickedItem() tar vi alle items som er plukket opp og legger til navnet til tingen i ei liste, og gjør listen om til ei arrayliste, som igjen blir gjort om til ei liste med strings for å kunne printe dette. Her har jeg forandret longname til shortname fordi det er mer oversiktlig i spillet. 

## Fri oppgave (Oppg. 7)

### Plan
-Ønsker å legge til flere level/kart, og forbedre selve designet på spillet. 
-Ønsker å bruke metodene i rabbit for å også gjøre spider smartere, og legge til at spider kan angripe spiller/kanin. 
-Ønsker å legge til at spilleren kan plukke opp armour og beskytte seg selv mot mulige angrep, ved å få høyere defence.  

### Utførelse
-Har forandret emojiesene ved å bytte til "true" i emojifactory.java, og gikk deretter inn i en og en klasse og forandret/la til emoji.
-Endret farge på spillbrettet i screen.java.
-Har lagt til en ny fil level2.txt som er level 2 i spillet, men klarte ikke å legge inn slik at map til level2 kom etter å ha vunnet level1. 
-Implementerte game:rangedAttack(): Der henter vi ut attackdamage og defence. Dersom attackdamage er større enn defence, så vil det være mulig å drepe det som er target.
-La til slik at spilleren kan plukke opp skjold/beskyttelse og få mer defence. Dette gjorde jeg ved å lage en ny klasse kalt Armour.java. Der brukte jeg interfacet IItem og la til verdiene/navnene osv som er naturlig for ett skjold å ha. La også inn emoji her. La også til armour i ItemFactory. Deretter implementerte jeg getDefence() i Player.java, og sjekket om armour ligger i backpack(dvs at spilleren plukket opp armour). Dersom armour er i backpack, øker defence på spilleren med +2.
Etter dette lagde jeg testen ArmourTest.java. Der sjekket jeg om armour faktisk gav mer defence om spilleren plukket opp armour. Først sjekket jeg om defence fra før av er lik to, og deretter legger jeg til armour i backpack, og sjekket at denne nå er blitt 2+2=4.
 

### Flere utvidelser
For å eventulet få brukt rangedAttack() senere, kan vi kalle på metoden i feks spider, som kan angripe en kanin eller en spiller. Her var planen å bruke mange av metodene som rabbit bruker for edderkoppene, men slik at edderkoppene leter etter en potensiell spiller den kan angripe, istedenfor å lete etter gulrøtter slik som kaninene gjør. En annen mulig utvidelse kan være at edderkoppen angriper kaniner, eller at spilleren og edderkoppene slåss mot hverandre. 