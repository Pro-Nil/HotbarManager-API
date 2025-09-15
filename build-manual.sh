#!/bin/bash

echo "Building HotbarManager API manually..."

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 8 or higher"
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven"
    exit 1
fi

echo "Java and Maven found. Starting build..."

# Clean and compile
echo "Cleaning previous builds..."
mvn clean

echo "Compiling source code..."
mvn compile -DskipTests

echo "Creating JAR file..."
mvn package -DskipTests

echo "Generating sources JAR..."
mvn source:jar

echo "Generating Javadoc..."
mvn javadoc:javadoc

echo ""
echo "Build completed! Check the target/ directory for JAR files:"
ls -la target/*.jar

echo ""
echo "JAR files created:"
echo "- HotbarManager-API-1.5.0.jar (main API)"
echo "- HotbarManager-API-1.5.0-sources.jar (source code)"
echo "- HotbarManager-API-1.5.0-javadoc.jar (documentation)"
