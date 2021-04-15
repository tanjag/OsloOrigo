# Oslo Origo kodeoppgave
Kodeoppgave basert på sanntidsdata fra Oslo Bysykkel.

Prosjektet er skrevet i ```scala 2.12``` med [Scalatra](https://scalatra.org/)  som web-rammeverk for REST endepunkt.

## Build 

Bygg prosjektet med
```
sbt
```

## Run
Kjør prosjektet inni ```sbt``` consolet med:
```
jetty:start
```

Prosjektet kjører da på [http://localhost:8080/](http://localhost:8080/)

### Oppgave 1 - oversikt over bysykkelstativ status
En liste over de ulike stasjonene, og hvor mange tilgjengelige låser og ledige sykler som er på dem i øyeblikket er å finne på:

[http://localhost:8080/bysykkelstativ/status/](http://localhost:8080/bysykkelstativ/status/)

### Oppgave 2 - REST endepunkt
[http://localhost:8080/bysykkelstativ/api/](http://localhost:8080/bysykkelstativ/api/)