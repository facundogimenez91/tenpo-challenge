FROM postgres:11.5-alpine
COPY create_tables.sql /docker-entrypoint-initdb.d/