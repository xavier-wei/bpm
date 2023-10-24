@echo off

cd bpm
call mvn clean package -P prod_dr,no-liquibase

cd ..
cd flowable
call mvn clean package -P prod_dr
