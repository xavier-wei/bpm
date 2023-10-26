@echo off

set projectPath=%cd%
set destinationDirPath=%projectPath%\war\

if not exist "%destinationDirPath%" (
    mkdir "%destinationDirPath%"
)

git fetch

git checkout -B deploy/packageForPcc origin/deploy/packageForPcc

git merge master

git push origin/deploy/packageForPcc

cd bpm
call mvn clean package -P prod_dr,no-liquibase


cd %projectPath%\flowable
call mvn clean package -P prod_dr


cd %projectPath%\java
call mvn clean package


set bpmWarPath=%projectPath%\bpm\target\bpm.war
set flowableWarPath=%projectPath%\flowable\target\flowable.war
set eipWarPath=%projectPath%\java\target\eip.war

move "%bpmWarPath%" "%destinationDirPath%"
move "%flowableWarPath%"  "%destinationDirPath%"
move "%eipWarPath%" "%destinationDirPath%"

git checkout master