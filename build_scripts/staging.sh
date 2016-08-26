#!/bin/bash
PGPASSWORD=password psql -Upostgres -c "drop database ms;"
PGPASSWORD=password psql -Upostgres -c "create database ms WITH ENCODING 'UTF8';"
cd ../
mvn clean
cd ms-backend
mvn install -Pstaging
cd ../
cd ms-web-api
mvn tomcat7:redeploy-only -e -Pstaging
cd ../
cd ms-web
mvn tomcat7:redeploy -Pstaging
<<EOF