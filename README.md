# Distributed Power Grid

Ein verteiltes System zur Visualisierung und Verarbeitung von Energieverbrauch und -produktion in einer Community-Energienetzwerk-Simulation.  
Das Projekt besteht aus mehreren Microservices, die über REST und RabbitMQ miteinander kommunizieren, sowie einer JavaFX-GUI zur Visualisierung.

## Komponenten

- REST-Server: stellt aktuelle und historische Energie-Daten bereit (`/energy/current`, `/energy/historical`)
- Usage Service: verarbeitet Nutzungsdaten (z. B. Verbrauch, Einspeisung)
- Current Percentage Service: berechnet Netzanteile (Community/Grid)
- Community Energy Producer: simuliert Produktion durch Community
- JavaFX UI: zeigt aktuelle und historische Werte an (mit Datumsauswahl)
- PostgreSQL + RabbitMQ: laufen via Docker Compose

## Voraussetzungen

- Java JDK 24 installiert
- Docker + Docker Compose
- Maven

## Projekt starten

### 1. Datenbank & RabbitMQ

Manuell starten:
```bash
docker compose up
```
Öffne danach http://localhost:15672/ (RabbitMQ-Interface) und erstelle:
- einen User mit Benutzername user und Passwort user
- eine Queue mit dem Namen energy-queue


### 2. Prozesse starten

Jeder Service (Usage, CurrentPercentage, Producer etc.) enthält ein PowerShell-Skript zum schnellen Neu-Deployen:
```bash
./redeploy.ps1
```
Dieses Skript erledigt:
- Maven-Build (mvn clean package -DskipTests)
- Löschen bestehender Container
- Docker Image Build starten im gemeinsamen Docker-Netzwerk database_default

### 3. JavaFX App starten

Die JavaFX-GUI wird manuell gestartet, z. B. via:
```bash
target/app/bin/app.bat

./mvnw clean javafx:run
```
oder über IntelliJ