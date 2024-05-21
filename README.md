# Deployment Guide for Child Bank Project

## Prerequisites
Before you begin, ensure you have the following installed:
- Java 20 (JDK)
- Apache Maven

---

# Start from Jar File
To run the project using the jar file created in the packaging step, use the following command:
```bash
java -jar childbank-<tag>-<version>-SNAPSHOT.jar
```
**Replace** `<tag>`  and `<version>` with the actual tag and version of the jar file.

---

# Start from Source Code

## Clone from Github
1. **Clone the Repository**
   ```bash
   git clone https://github.com/Qianmoxsn/childbank.git
   cd childbank
   ```

2. **Navigate to the Project Directory**
   ```bash
   cd childbank
   ```

## Testing the Project
To run the tests included in the project, use the following command:
```bash
mvn test
```
This will compile the project and run all the tests defined in the `src/test/java` directory.

## Compiling the Project
To compile the project, use the following command:
```bash
mvn compile
```
This command will compile the source code located in the `src/main/java` directory.

## Packaging the Project
To package the project into a jar file, use the following command:
```bash
mvn package
```
This command will compile the project and package it into a jar file, which will be located in the `target` directory.

## Running the Project
After you package the project, you can run the program like `# Start from Jar File` section.


## Additional Maven Commands
- **Clean the Project**: This command removes the `target` directory with all the build data before performing the actual build.
  ```bash
  mvn clean
  ```

## Troubleshooting
- **Maven not recognized**: Ensure Maven is installed and the `MAVEN_HOME` environment variable is set correctly.
- **Java version issues**: Ensure the correct Java version is installed and the `JAVA_HOME` environment variable is set correctly.
- **Dependency issues**: Run `mvn dependency:resolve` to resolve and download dependencies specified in the `pom.xml` file.

---