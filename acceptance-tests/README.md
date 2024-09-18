Use following steps to run acceptance test locally:

1. Install Docker
2. mvn clean install -Dmaven.test.skip=true
3. Run `docker-compose.yaml` // DO NOT forget to provide absolute path to init-mongo.sh script
4. mvn clean install or run tests manually
