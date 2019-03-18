local
1. docker build -t swarm-backend .
2. docker run --name swarm-backend -e DEMO_ALLOWEDORIGINS=http://localhost:8300 -e SERVER_PORT=4000 -p 4000:4000 -d swarm-backend


gcp
1. docker tag swarm-backend:latest asia.gcr.io/XXXX/swarm-backend:1.0.0

2. local push remote
gcloud auth configure-docker
gcloud components update
gcloud docker -- push asia.gcr.io/jovial-archive-216204/swarm-backend:1.0.0

3.gcp compute engine
1.docker pull asia.gcr.io/XXXX/swarm-backend:1.0.0
2.docker run --name swarm-backend -e DEMO_ALLOWEDORIGINS='http://localhost:8300' -e SERVER_PORT=4000 -p 4000:4000 -d asia.gcr.io/jovial-archive-216204/swarm-backend:1.0.0

4.Swagger
http://localhost:4000/swagger-ui.html#/