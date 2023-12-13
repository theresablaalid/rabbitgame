## Rogue 101
Denne filen beskriver det [Rogue](https://en.wikipedia.org/wiki/Rogue_(video_game))-like spillet "Rogue One-Oh-One" fra semesteroppgave 1 i INF101.v20. 

Spillet vårt er inspirert av [Rogue](https://en.wikipedia.org/wiki/Rogue_(video_game)), et tekstbasert spill laget av to computer science-studenter ved UCSC på slutten av 70-tallet. Les mer om det originale spillet [her](https://docs.freebsd.org/44doc/usd/30.rogue/paper.pdf). 

![original game screen shot](https://upload.wikimedia.org/wikipedia/commons/1/17/Rogue_Screen_Shot_CAR.PNG)

# Introduksjon 
I Rogue One-Oh-One styrer du en spiller rundt i en to-dimensjonal labyrint. Labyrinten inneholder ting som spilleren kan plukke opp, som gull og amuletter, og andre aktører, som edderkopper og kaniner. Aktører kan angripe ting, og både aktører og ting dør dersom de tar nok skade. 

Vi kjører spillet som et grafisk grensesnitt så du kan se spilleren og monstrene bevege seg rundt på brettet. Noen spill-elementer er representert som symboler (for eksempel spilleren), mens andre er tegnet grafisk (for eksempel edderkopper). Hendelser i spillet beskrives ved hjelp av tekst på skjermen (ikke til terminalen): 

`You hear a faint crunching (rabbit monster eats a a juicy carrot)`


### Likhet til lab 3 og lab 4
Du kjenner kanskje igjen ideen om en labyrint som to-dimensjonal grid fra lab 4. Vi bruker den samme typen generisk datastruktur `Grid<T>` i denne oppgaven som i lab 4, og spiller fungerer nokså likt som celleautomatene: 

- spillet er runde-basert
- spillet foregår på et todimensjonalt grid
- hver rute på gridden har "ting" på seg
- hver runde får alle "ting" på brettet gjør noe 
- hver "ting" har sin egen logikk for å bestemme hva den skal gjøre, med unntak av spilleren

I oppgavene som handlet om celleautomater, så inneholdt alle posisjonene på gridden celler, og alle cellene brukte samme logikk for å oppdatere seg. I Rogue så inneholder gridden forskjellige ting, og hver type ting har sin egen logikk. "Dumme" ting som gull og støv har lite logikk og gjør kanskje ingenting per runde, mens "smartere" ting kan flytte på seg, spise og angripe. Monster-"ting" har egen logikk for hvordan de flytter seg, basert på hva som ligger i nabo-rutene på brettet, og spiller-"tingen" styres ved hjelp av input fra tastaturet. 


## Spill-elementer 

### Spillet
Rogue-spillet går ut på å flytte spilleren rundt i et kart, finne og plukke opp en medaljong, og nå slutt-posisjonen som lar den slippe ut av labyrinten. Spilleren kommer seg ikke ut av labyrinten uten medaljongen. Dersom spilleren dør er spillet over. Spillet inneholder regler for hvordan spilleren kan bevege seg på kartet og hvordan den interagerer med andre _aktører_ og _ting_. Spillet har et grafisk grensesnitt, så kartet og aktører og ting vises på skjermen ved hjelp av grafikk og tekst; hendelser skrives ut til skjermen.

### Spillekartet 
Rogue 101 består av en labyrint med _ting_ og _aktører_. Kartet er et [todimensjonalt rutenett](https://en.wikipedia.org/wiki/Tile-based_video_game) der hver rute inneholder maks én aktør, og et varierende antall ting. Hver runde kan aktørene bevege seg rundt i labyrinten. Eksempler på aktører er edderkopper og spilleren; eksempel på ting er gull og støv. Hver rute har kun plass til én aktør, men det kan ligge mange ting på samme sted. Veggene i labyrinten er også ting: det vil si at en rute kan for eksempel inneholde en vegg, eller en spiller og en gulrot, eller en kanin, en gulrot og støv. Hvilke ting og aktører vi ønsker å plassere på brettet er helt opp til oss selv. Vi er utviklerne, og det finnes ingen begrensninger.

Kartet og inneholdet kan representeres både grafisk og som tekst: for et eksempel på et tekstrepresentasjon, se på filen `level1.txt`. 


### Ting 
Kartet inneholder "ting". Tingene typisk i ro, og har på egenhånd ingen effekt på spillet. Det utleverte spillet inneholder tingene støv, gulrot, vegger og amuletten. I tillegg skal du legge til gull. 

Alle ting har en HP - health points - som indikerer om de er ødelagt eller ikke. Noe som har negativ HP er ødelagt, og når ting tar skade reduseres HP-verdien deres.

#### Gulrot
På brettet ligger det noen gulrøtter strødd. Vi kan se at på egenhånd har disse ingen effekt på spillet. Gulrøttene, samt de andre tingene våre, gjør seg nyttig på en annen måte:

### Aktører 
_Aktører_ i spillet arver alle trekkene til tingene, men kan i tillegg bevege seg rundt på brettet. De kan også bruke stasjonære ting de finner på kartet, og bruke dem for å ha en effekt på spillet. Ulike aktører kan være interessert i ulike ting, og ulike ting kan ha ulik effekt på spillet.

Den utleverte koden inneholder aktørene kanin, edderkopp og spiller. 
Alle aktørene har, som tingene, en HP - health points. Denne indikerer hvor mye helse aktøren har igjen. Når en aktør tar skade reduseres HP-verdien, og om den kommer lavt nok vil aktøren dø. Akkurat som i så mange andre spill!

#### Kanin

Når du kjører koden som den er nå, er kartet fylt med massevis av kaniner. Hver runde kan kaninen flytte seg rundt på kartet. Etter et par runder dør kaninene av sult! <b>Herregud så trist!</b> _Kaninenes HP reduseres hver runde, og de må spise gulrøtter for å holde seg i live._

#### Edderkopp
Hver runde kan edderkoppen flytte seg rundt på kartet. Edderkopper er som kjent skumle vesener, og spilleren må selvfølgelig passe seg for dem. Om spilleren heller vil møte sin store frykt, skal det også kunne være mulig å sloss mot dem. (Men her må du kanskje hjelpe oss med litt implementasjon, akkurat nå er edderkoppene egentlig ganske harmløse...)

#### Spiller  
Spiller-klassen er hvor du som spiller kommer inn. 

Du kan navigere et spiller-objekt fritt rundt på kartet, og utforske den nye verdenen du har blitt plassert i. Men du må også være forsiktig! Spilleren har også en HP-verdi, og om du ikke spiller godt nok, kan spilleren også dø.

Hver runde leser spilleren input fra tastaturet. Avhengig av inputet kan den enten 

- bevege seg NORD/SØR/ØST/VEST på kartet 
- angripe en annen aktør på en rute som spilleren kan bevege seg i retning av 
- plukke opp en ting fra samme rute som den selv
- legge fra seg en ting på samme rute som den selv 

## Interaksjoner 


### Regler for angrep og defens

Hver i hver celle på kartet kan det befinne seg maks 1 aktør.

Se for deg at en aktør A står i cellen (x, y). A prøver å bevege seg inn i en nabo-celle (dx, dy), hvor det står en aktør B.
I dette tilfellet vil A _angripe_ B. 

Hver aktør har en "attack"-verdi, og en "defense"-verdi.
Angrepet lykkes hvis "attack"-verdien til A er høyere enn "defense"-verdien til B. (Og er dermed mislykket om "attack"-verdien til A er lavere eller lik "defense"-verdien til B)

Om angrepet er mislykket skjer ingen ting.

Om angrepet lykkes, vil A gjøre skade på B basert på skade-verdien til A. Om a gjøre x skade, vil B sin HP = gammel HP - x.
Hvis HP-verdien til B kommer _under 0_, blir B "ødelagt" og dør. I dette tilfellet, hvor B dør som følge av et angrep fra A, vil a bevege seg inn i cellen B sto i.

## Jeg har kjørt main()-metoden og skjønner ikke hva jeg ser på:
Dette er helt greit! Det første man nok legger merke til når man kjører koden er selve kartet. Vinduet består av massevis av celler i et rutenett, og dette tilsvarer posisjonene som er mulig å plassere seg i på kartet. De svarte boksene som danner kartet vårt er vegger. Veggene er ugjennomtrengelige, så de sørger for at spilleren vår holder seg innenfor kartet. (Vi vil jo helst unngå IndexOutOfBoundsExceptions!)

Strødd utover kartet vil du (hvertfall de første par trekkene av spillet) se mange "R"-symboler. Disse symbolene tilsvarer kanin-aktører. Om du gjør et par trekk vil du se at det ikke tar særlig lang tid før kaninene begynner å dø.

Oppe til venstre på kartet ser du muligens et "@"-tegn. Dette er spiller-aktøren som er mulig for deg å kontrollere.

## Hva nå?
I løpet av denne semesteroppgaven vil vi at du skal ta vår ganske enkle implementasjon av Rougue One-Oh-One, og gjøre den mer avansert. Følg oppgaveteksten, og prøv deg frem. Lykke til!
