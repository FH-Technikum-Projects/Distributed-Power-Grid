$AppName = "energy-producer"
$JarName = "CommunityEnergyUser-0.0.1-SNAPSHOT.jar"
$NetworkName = "database_default"

./mvnw.cmd clean package -DskipTests

docker stop $AppName -ErrorAction SilentlyContinue
docker rm $AppName -ErrorAction SilentlyContinue

docker build -t $AppName .

docker run --name $AppName --network $NetworkName $AppName
