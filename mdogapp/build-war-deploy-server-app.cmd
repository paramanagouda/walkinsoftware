@echo off
set tomcate_path=D:\WorkSpace\tools\Servers\Tomcat\apache-tomcat-9.0.20
set working_dir=%CD%
echo current directory : %working_dir% 
echo tomcate directory : %tomcate_path%

start /wait cmd /c "mvn -f mdog-server-app clean package"
echo build war file process success
echo remove old war file and temp files from server process 
rmdir /Q/S %tomcate_path%\webapps\mdogapp
del %tomcate_path%\webapps\mdogapp.war
rmdir /Q/S %tomcate_path%\work\Catalina\localhost
echo Copy war file to deployment location
copy mdog-server-app\target\mdogapp.war %tomcate_path%\webapps
cd %tomcate_path%\bin\
startup.bat
echo tomcate server started
cd %working_dir%
pause
REM @echo on