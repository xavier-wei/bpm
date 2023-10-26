

set projectPath=%cd%\repo
set destinationDirPath=%cd%\eipWar\

if not exist "%destinationDirPath%" (
    mkdir "%destinationDirPath%"
)

cd repo

git fetch origin master
git checkout master
git pull



git fetch origin deploy/packageForPcc
git checkout -B deploy/packageForPcc origin/deploy/packageForPcc --

git merge master

git push origin deploy/packageForPcc
echo "pull and push finish, prepare to package"
pause

cd %projectPath%\bpm
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



