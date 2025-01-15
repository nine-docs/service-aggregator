# Service Aggregator
- Swagger URI : /swagger

# 이미지 빌드 및 실행
## Dockerfile로 이미지 빌드
`docker build -t 이미지이름 -f Dockerfile경로 빌드컨텍스트경로`  

예시에서 현재 위치는 프로젝트 루트로 가정  
```bash
# build 까지 한번에 수행하는 경우 (JDK가 셋팅되어있지 않은 환경을 위함)
$ docker build -t ninedocs-service-aggregator -f cicd/DockerfileWithBuild .
```
```bash
# build 와 이미지빌드를 별도로 수행하는 경우
$ ./gradlew clean build
$ docker build -t ninedocs-service-aggregator -f cicd/DockerfileWithoutBuild build/libs
```

## 이미지 실행
`docker run -d -p (호스트 포트):(컨테이너 포트) --env-file (.env 파일 위치) (imageRepo이름):(tag)`
```bash
docker run --name service-aggregator -d -p 80:8080 --env-file ./cicd/.env ninedocs-service-aggregator:latest
```
