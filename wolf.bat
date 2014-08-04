@REM
@REM  Licensed to the Apache Software Foundation (ASF) under one or more
@REM  contributor license agreements.  See the NOTICE file distributed with
@REM  this work for additional information regarding copyright ownership.
@REM  The ASF licenses this file to You under the Apache License, Version 2.0
@REM  (the "License"); you may not use this file except in compliance with
@REM  the License.  You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.

@echo off
if "%OS%" == "Windows_NT" setlocal

set ARG=%1
set INSTALL="INSTALL"
set UNINSTALL="UNINSTALL"

pushd %~dp0..
if NOT DEFINED WOLF_HOME set WOLF_HOME=%CD%
popd

if NOT DEFINED WOLF_MAIN set WOLF_MAIN=org.apache.wolf.service.WolfServiceDaemon
if NOT DEFINED JAVA_HOME goto err

set WOLF_CLASSPATH= %CLASSPATH% %WOLF_HOME%wolf\buildClass

set JAVA_OPTS=-ea^
 -javaagent:"%WOLF_CLASSPATH%jamm-0.2.5.jar"^

echo "The hoem is " 
echo %WOLF_CLASSPATH%
:runDaemon
echo Starting WolfServer
"%JAVA_HOME%\bin\java"    -jar %WOLF_CLASSPATH%\wolf_service-1.0.jar  %WOLF_MAIN%

goto finally

:err
echo JAVA_HOME enviroment variable must be set!
pause

:finally

ENDLOCAL
