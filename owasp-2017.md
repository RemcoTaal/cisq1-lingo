# OWASP kwetbaarheden analyse

## A1:2017 Injection

### Beschrijving
Een applicatie gebruikt niet betrouwbare inputdata en stuurt dit direct naar een interpreter, hierdoor kan bijvoorbeeld een SQL query dermate worden aangepast dat gevoelige data wordt teruggegeven.

### Risico
Aanpassen van SQL door bijvoorbeeld de WHERE clause altijd true te laten zijn. Waardoor alle gegevens van een gekozen table blootgesteld kunnen worden. 

### Maatregelen
In dit project word gebruik gemaakt van parameterisatie. Bij een request waar bijvoorbeeld een game id wordt meegegeven wordt deze waarde omgezet naar een Long value. Daarnaast wordt bij een raadpoging de waarde van het geraden wordt niet direct gebruikt in een SQL query.

## A4:2017-XML External Entities (XXE)

### Beschrijving

Een applicatie maakt gebruik van kwetsbare XML processors die aanvallers kunnen misbruiken. Hierdoor kunnen er gegevens opgevraagd worden van bijvoorbeeld de server. Met deze informatie kan de aanvaller gerichter zijn aanval uitvoeren.

### Risico

Oudere XML processors kunnen externe entities verwerken, hierdoor kan gevoelige data worden blootgegeven. Dit is met name het geval bij oudere XML processors

### Maatregelen

In dit project word gebruik gemaakt van sonarcloud dat een report geeft over vulnaribilities, daarnaast wordt gebruik gemaakt van de dependabot die pullrequest zal maken om gebruikte dependencies te updaten. Hierdoor zal de kans klein zijn dat een kwetsbare XML processor als dependency wordt gebruikt.

## A9:2017-Using Components with Known Vulnerabilities

### Beschrijving

Een applicatie maakt gebruik van dependencies waarbij de kwetsbaarheden bekend zijn, deze zijn voor een mogelijke aanvaller ook bekend en die kan dit dus misbruiken

### Risico

Het is makkelijk om een aanval uit te voeren op bekende kwetsbaarheden, hierdoor is de applicatie kwetsbaar voor aanvallen

### Maatregelen

In dit project wordt gebruik gemaakt van de maven owasp plugin die checkt op kwetsbaarheden van de dependencies. Hierbij wordt gebruik gemaakt van de National Vulnerability Database (NVD) om te controleren welke dependencies mogelijk kwetsbaarheden bevatten.   
