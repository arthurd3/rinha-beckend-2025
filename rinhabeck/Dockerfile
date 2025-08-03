# Multi-stage build para garantir compatibilidade multiplataforma
FROM ubuntu:22.04 as runtime

# Instalar apenas as dependências mínimas necessárias
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    ca-certificates && \
    rm -rf /var/lib/apt/lists/* && \
    useradd --create-home --shell /bin/bash app

WORKDIR /app

# Copiar o executável nativo
COPY build/native/nativeCompile/rinhabeck .

# Definir permissões e usuário
RUN chmod +x ./rinhabeck && \
    chown app:app ./rinhabeck

# Executar como usuário não-root para segurança
USER app

EXPOSE 8080

CMD ["./rinhabeck"]