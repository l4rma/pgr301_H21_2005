# Eksamen - DevOps
PGR301 Høst 2021

## Oppgave - DevOps

### Drøft:

### 1.1
Q: Beskriv med ord eller skjermbilder hvordan man kan konfigurere GitHub på en
måte som gir bedre kontroll på utviklingsprosessen. Spesielt med tanke på å
hindre kode som ikke kompilerer og feilende tester fra å bli integrert i main
branch.

A: Det første som må gjøres er å sette opp en workflow med github actions.
Dette gjøres ved å legge til direcoryet .github/workflows. Her oppretter man en
yml-fil med instruksjoner for github actions. Instruksjonene inneholder navn,
når, hva, hvor, hvordan alt skal gjøres (bilde 1.1). Dette vil på hver push
bygge applikasjonen og kjøre testene. Github action vil registrere om testen
godkjennes eller ikke. Dette gjør det mulig å gå til Settings/Branches og ved
Branch Protection Rules trykke add og legge til en "rule" på main som sier
"Require status checks to pass before merging" (bilde 1.2). Deretter velger man
bygg, og koden vil nå ikke la seg merge til main hvis testene ikke passerer.

<img src="bilde1_1.jpg">
<img src="bilde1_2.jpg">

### 1.2
Q: Beskriv med ord eller skjermbilder hvordan GitHub kan konfigureres for å
sikre at minst ett annet medlem av teamet har godkjent en pull request før den
merges.

A: For å gjøre dette kan man gå til Settings/Branches og ved "Branch Protection
Rules" klikk add. Velg "Require a pull request before merging" og "Require
aoorivaks". Under kan man velge antall approvals som trengs (bilde 2).

<img src="bilde2.jpg">

### 1.3
Q: Beskriv hvordan arbeidsflyten for hver enkelt utvikler bør være for å få en
effektiv som mulig utviklingsprosess, spesielt hvordan hver enkelt utvikler bør
jobbe med Brancher i Github hver gang han eller hun starter en ny oppgave.

A: For at det ikke skal bli rot og kaos som hos SkalBank er det viktig å holde
main branch så clean som mulig. Alt som merges til main bør kjøre tester uten
feil for ikke å skape problemer på main. Derfor bør man alltid når man skal
legge til kode først opprette en ny branch, denne bør navngis så det er enkelt
å forstå hva som jobbes med på branchen. Som med alt i IT er det mange meninger
om hva som er den beste konvensjonen her er noen eksempler: 

\<gruppe\>/\<branch-navn\> - \<utvikler\>\_\<branch-type\>\_\<branch-navn> - \<branch-type\>/\<branch-navn\>
```shell
group1/loginModule
glenn_wip_exam_grading_automator
feature/coolButtons
fix/all_users_get_admin_privliges
```

### 2
Q: SkalBank har bestemt seg for å bruke DevOps som underliggende prinsipp for
allsystemutvikling i banken. Er fordeling av oppgaver mellom API-teamet og
"Team Dino"problematisk med dette som utgangspunkt? Hvilke prinsipper er det
som ikke etterleves her? Hva er i så fall konsekvensen av dette?

A: Hva DevOps egentlig er kan være litt vagt, men det handler blandt annet om
flyt. Det vil si prinsipper som fjerne "waste" i verdikjeden, færrest mulig
overleveringer og identifisere flaskehalser. Kontinuelig itegrasjon,
kontinuerlige leveranser og automatiserte tester. Alle disse er prinsipper som
SkalBank ikke etterlever. Og konsekvensen av endringen til SkalBank er nok
dessverre at 100+ stillinger (Team Dino) blir overflødige. Det API-teamet vil
gjøre er å sette opp en pipeline hvor blandt annet bygging og testing av kode
er automatisert. Feks på samme måte som vist i neste oppgave.

## Oppgave - Pipline

Fil: .github/workflows/maven.yml

* 3.1 [x] Kjører enhetstester
* 3.2 [x] Kompilerer koden
* 3.3 [x] Bygger artifakt (JAR)

## Oppgave - Feedback

4.1 Applikasjonen skal produsere metrics med Micrometer og levere metrics til Influx DB lokalt

* [x] Lagt til kode som registerer målepunkter i applikasjonen

I DevOps ånd valgte jeg å bruke litt (for mye) tid på å skrive et shell script
for å sende requester, slik at jeg slapp å gjøre det manuelt igjen og igjen
eller styre med Postman. Har lagt det med i repo (doTenRequests.sh). 
Dette kan også brukes av sensor for å gjøre requester under testingen :)

Spørringsforslag til influxDB: 
```sql
SELECT * FROM get_account WHERE time > now() - 1h AND count > 0
SELECT * FROM update_account WHERE time > now() - 1h AND count > 0
SELECT * FROM transfer_money WHERE time > now() - 1h AND count > 0
```

Spørringene viser henholdsvis requests til de tre endepunktene gjort siste timen.
Da det er en egen kolonne "exceptions" kan man veldig lett se hvilke exceptions
som skjer og når (Bilde 4.1). På Grafana satt jeg opp y-aksen til ms og x-aksen er tidspunkt
spørringen måles for å kunne se hvor lang tid det tok å få svar (Bilde 4.2).

![InfluxDB](imgs/bilde4_1.png)

![Grafana](imgs/bilde4_2.png)

## Oppgave - Terraform

### Drøft:

### 5.1

Q: Hvorfor funket terraformkoden i dette repoet for "Jens" første gang det ble
kjørt? Og hvorfor feiler det for alle andre etterpå, inkludert Jens etter at
han ryddet på disken sin og slettet terraform.tfstate filen?

A: Første gang han kjørte koden fungerte det, siden det ikke er feil i koden,
og det ble det opprettet en statefil med all informasjonen terraform trenger.
Hvis alle de andre kjører nøyaktig samme kode, så vil den feile siden de alle
prøver å opprette buckets med samme navn. S3 buckets trenger globalt unike
navn, så de må endre på navnet (i linje 1 som feilmeldingen sier) for å få det
til å kjøre.

//TODO: Se over legg til litt mer

* 5.2 [ ] Lag en S3 bucket i klassens AWS konto

### 5.3 Hvordan lage bucket fra CLI? 
Først er sensor nødt til å skaffe seg no nøkler og credentials. For å gjøre dette ...
//TODO: skriv hvordan skaffe nøkler og credentials

Deretter kan sensor konfigurere aws klienten med sin nyanskaffede nøkkel-id og secret access key

```shell
$ aws configure
```

Så er det bare å bruke s3apiet for å lage seg en bøtte:

```shell 
$ aws s3api create-bucket --bucket my-bucket --region eu-west-1 --create-bucket-configuration LocationConstraint=eu-west-1
```

* [ ] 5.3 Bruk S3 backend for state

### Terraformkode

* [ ] 5.4 Lag Terraform kode som oppretter ECR repository

### 5.5 Terraform i pipline

Implementer en workflow med GitHub actions som:

* [ ] kjører ``Terraform init & apply`` for hver endring av kode i main branch.
* [ ] kjører ``Terraform init & plan`` for hver pull request som lages mot main branch
* [ ] feiler dersom Terraformkode som pushes til main ikke er riktig formatert.
* [ ] bare kjører dersom det er endringer in ifra/ katalogen.

### 5.6 Forklaring til sensor som vil lage fork

Q: Beskriv hva sensor må gjøre etter han/hun har laget en fork for å få pipeline til å fungere for i sin AWS/gitHub konto.

A: //TODO: skriv svar

Q: Hvilke verdier må endres i koden?

A: //TODO: skriv svar

Q: Hvilke hemmeligheter må legges inn i repoet. Hvordan gjøres dette?

A: //TODO: skriv svar

## Oppgave - Docker

* [ ] 6.1 Dockerfile (skal både kompilere, bygge og kjøre applikasjonen.

Q: Hva vil kommandolinje for å bygge et container image være?

A: ``$ docker build``

Q: Hva vil kommando for å starte en container være? Applikasjonen skal lytte på port 7777 på din
maskin

A: ``$ docker run <image-navn> p7777:8080``

Q: Medlemmer av "Team Dino" har av og til behov for å kjøre to ulike versjoner av applikasjonen lokalt på maskinen sin, samtidig .Hvordan kan de gjøre dette uten å få en port-konflikt? Hvilke to kommandoer kan man kjøre for å starte samme applikasjon to ganger, hvor den ene bruker port 7777 og den andre 8888?

A: 
```shell
$ docker run <image-navn> p7777:8080 
$ docker run <image-navn> p8888:8080
```
(Evt kan man skrive alt på en linje med "&&" i mellom)
//TODO: dobbelt sjekk at man kan kjøre med "&&"

### 6.2 Lag en GitHub Actions workflow som bygger et Docker image av Spring Boot applikasjonen.
* [ ] GitHub Workflowen skal kjøres ved hver push til main branch.
* [ ] Hvert Container image skal ha en unik tag som identifiserer hvilken commit i GitHub som ble brukt som grunnlag for å bygge container image.
* [ ] Container image skal pushes til ECR repository som ble laget i Terraform oppgaven.
* [ ] Hvis du ikke har fått til Terraform oppgaven, kan du lage et ECR repository manuelt via AWS console (UI), og du får ikke poengtrekk i denne oppgaven dersom du gjør dette

