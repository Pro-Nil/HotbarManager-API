@echo off
echo Building HotbarManager API manually...

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 8 or higher
    pause
    exit /b 1
)

REM Check if Maven is available
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven
    pause
    exit /b 1
)

echo Java and Maven found. Starting build...

REM Clean and compile
echo Cleaning previous builds...
mvn clean

echo Compiling source code...
mvn compile -DskipTests

echo Creating JAR file...
mvn package -DskipTests

echo Generating sources JAR...
mvn source:jar

echo Generating Javadoc...
mvn javadoc:javadoc

echo.
echo Build completed! Check the target/ directory for JAR files:
dir target\*.jar

echo.
echo JAR files created:
echo - HotbarManager-API-1.5.0.jar (main API)
echo - HotbarManager-API-1.5.0-sources.jar (source code)
echo - HotbarManager-API-1.5.0-javadoc.jar (documentation)

pause
