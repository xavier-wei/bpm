@echo off

set projectPath=%cd%
set destinationDirPath=%projectPath%\war\

if not exist "%destinationDirPath%" (
    mkdir "%destinationDirPath%"
)

git fetch

git checkout -b deploy/packageForPcc origin/deploy/packageForPcc

git merge master

cd bpm
call mvn clean package -P prod_dr,no-liquibase


cd ..\flowable
call mvn clean package -P prod_dr


cd ..\java
call mvn clean package


set bpmWarPath=%projectPath%\repo\bpm\target\bpm.war
set flowableWarPath=%projectPath%\repo\flowable\target\flowable.war
set eipWarPath=%projectPath%\repo\java\target\eip.war

move "%bpmWarPath%" "%destinationDirPath%"
move "%flowableWarPath%"  "%destinationDirPath%"
move "%eipWarPath%" "%destinationDirPath%"

git checkout master