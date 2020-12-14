dev:
	mvn clean
	mvn install -DskipTests=true
	docker build -f Dockerfile -t userservice .
	docker-compose up