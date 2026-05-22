FROM golang:1.25 as builder

WORKDIR /build

COPY client/go.mod .
RUN go mod download

COPY client/src /build

RUN CGO_ENABLED=0 GOOS=linux go build -a -ldflags '-w -s' -o monitor-client .

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY backend/target/monitor-server-1.0.0-SNAPSHOT.jar app.jar
COPY --from=builder /build/monitor-client monitor-client
COPY backend/start.sh start.sh

RUN chmod +x monitor-client start.sh

EXPOSE 8080

CMD ["./start.sh"]