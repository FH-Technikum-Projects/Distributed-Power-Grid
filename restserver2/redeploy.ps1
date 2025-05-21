# Variablen definieren
$AppName = "rest-server"
$JarName = "restserver2-0.0.1-SNAPSHOT.jar"   # <- ggf. anpassen, je nach Projektnamen
$NetworkName = "database_default"            # <- Docker-Netzwerkname aus docker-compose

# Maven-Build starten
./mvnw.cmd clean package -DskipTests

# Falls Container bereits existiert → stoppen und löschen
$existing = docker ps -a --filter "name=$AppName" --format "{{.ID}}"
if ($existing) {
    docker stop $AppName | Out-Null
    docker rm $AppName | Out-Null
}

# Docker-Image bauen (verwende Dockerfile im aktuellen Verzeichnis)
docker build -t $AppName .

# Container starten und ins gemeinsame Netzwerk hängen
docker run --name $AppName --network $NetworkName -p 8080:8080 $AppName
