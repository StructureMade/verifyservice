dev:
	mvn clean
	mvn install -DskipTests=true
	docker build -f Dockerfile -t verifyservice .
	docker-compose up