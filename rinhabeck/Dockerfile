FROM alpine:latest

WORKDIR /app

COPY build/native/nativeCompile/rinhabeck .

RUN chmod +x ./rinhabeck

EXPOSE 8080

CMD ["./rinhabeck"]