FROM oracle/graalvm-ce:21 as builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew .

RUN ./gradlew build --no-daemon

COPY src ./src

RUN ./gradlew nativeCompile --no-daemon


FROM scratch

WORKDIR /app

COPY --from=builder /app/build/native/nativeCompile/rinha-backend .

EXPOSE 8080

CMD ["./rinha-backend"]