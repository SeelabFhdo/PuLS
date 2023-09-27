FROM mariadb
ADD initialize_databases.sql /docker-entrypoint-initdb.d/initialize_databases.sql
RUN chmod -R 775 /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD password
ENV MYSQL_USER puls
ENV MYSQL_PASSWORD password
