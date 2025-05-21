$AppName = "restserver"
$JarName = "restserver-0.0.1-SNAPSHOT.jar"
$NetworkName = "database_default"

./mvnw.cmd clean package -DskipTests

$existing = docker ps -a --filter "name=$AppName" --format "{{.ID}}"
if ($existing) {
    docker stop $AppName | Out-Null
    docker rm $AppName | Out-Null
}

docker build -t $AppName .

docker run --name $AppName --network $NetworkName -p 8080:8080 $AppName
