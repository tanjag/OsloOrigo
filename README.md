# Oslo Origo kodeoppgave
Kodeoppgave basert på sanntidsdata fra Oslo Bysykkel.

Prosjektet er skrevet i ```scala 2.12``` med [Scalatra](https://scalatra.org/)  som web-rammeverk for REST endepunkt.

## Build 

Prosjektet bygges med sbt i topp mappen:
```
> cd OsloOrigo
> sbt
```

## Run
Kjør opp prosjektet inni ```sbt``` consolet med jetty:
```
sbt:Bike Station Web App> jetty:start
```

Prosjektet kjører da på [http://localhost:8080/](http://localhost:8080/)

### Oppgave 1 - oversikt over bysykkelstativ status
En liste over de ulike stasjonene, og hvor mange tilgjengelige låser og ledige sykler som er på dem i øyeblikket er å finne på:

[http://localhost:8080/bysykkelstativ/status/](http://localhost:8080/bysykkelstativ/status/)

### Oppgave 2 - REST endepunkt
Rot for alle REST endepunktene er: 
[http://localhost:8080/bysykkelstativ/api/](http://localhost:8080/bysykkelstativ/api/)

#### GET alle stasjoner med status
```
http://localhost:8080/api/status/all
```

#### GET spesifikk stasjon status gitt id
Id sendes inn som parameter, tolkes som string.  
```
http://localhost:8080/api/status/station_id/{:id}
```

#### GET spesifikk stasjon status gitt navn
Navn sendes inn som en parameter, tolkes som string.
```
http://localhost:8080/api/status/name/{:name}
```

#### GET alle stasjoner med ledig sykkel i øyeblikket
```
http://localhost:8080/api/status/bikes_available
```

#### GET alle stasjoner med ledig lås i øyeblikket
```
http://localhost:8080/api/status/docks_available
```
