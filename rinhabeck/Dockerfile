# Dockerfile simples para compatibilidade universal
FROM ubuntu:22.04

WORKDIR /app

# Copiar o executável nativo
COPY build/native/nativeCompile/rinhabeck .

# Dar permissão de execução
RUN chmod +x ./rinhabeck

EXPOSE 8080

CMD ["./rinhabeck"]