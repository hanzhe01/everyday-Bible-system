@echo off
echo.
echo [��Ϣ] ʹ��Jar��������Web���̡�
echo.

cd %~dp0
cd ../Bible-admin/target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% Bible-admin.jar

cd bin
pause