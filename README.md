# Financial Event Processing Platform

A guided backend project built one small, reviewed task at a time. The existing
repository name is `finflow-engine`; there is no need to rename it.

## Current scope

Phase 0 skeleton only: one Java 25 / Spring Boot 3.5.16 service, Maven, and a
startup integration test. No transaction endpoint or business logic exists yet.
Phase 1 will build an in-memory transaction API. Phase 2 requires an explicit
statement from the learner that Phase 1 is complete.

## Prerequisites and local commands

Use JDK 25 and Maven 3.9.x. Maven 3.9.12 is installed on the initial development
machine. No wrapper is included yet; commands use the installed Maven.

On macOS, select Java 25 in the terminal where you run Maven:

```sh
export JAVA_HOME="$(/usr/libexec/java_home -v 25)"
export PATH="$JAVA_HOME/bin:$PATH"
java -version
mvn -version
```

Both commands should report Java 25. `JAVA_HOME` selects Maven's JDK; putting its
`bin` directory first in `PATH` selects the same `java` executable in the shell.
Configure your IDE's project JDK to 25 too.

From the repository root:

```sh
mvn -f services/transaction-service/pom.xml test
mvn -f services/transaction-service/pom.xml spring-boot:run
```

The first run downloads dependencies. The server listens on localhost port 8080.
In a second terminal:

```sh
curl -i http://localhost:8080/transactions
```

Expect HTTP 404: the web server is running, but no transaction route exists.
This is a startup check, not a health endpoint. Stop the server with Ctrl+C.
If port 8080 is occupied, stop the other process or use
`mvn -f services/transaction-service/pom.xml spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`.

To build and run an executable JAR (Java archive):

```sh
mvn -f services/transaction-service/pom.xml package
java -jar services/transaction-service/target/transaction-service-0.0.1-SNAPSHOT.jar
```

`test` compiles production and test code and executes tests. `package` also
packages the application; the Boot plugin bundles dependencies and the server
so it can run with `java -jar`. `target/` is generated output and is ignored by Git.

## Project map

```text
finflow-engine/
├── .gitignore
├── README.md
├── docs/
│   ├── architecture/
│   │   └── phase-0-and-1.md
│   ├── adr/
│   │   └── 0001-single-service-and-maven.md
│   └── learning/
│       ├── mentoring-contract.md
│       └── task-001-java-transaction-type.md
└── services/
    └── transaction-service/
        ├── pom.xml
        └── src/
            ├── main/
            │   ├── java/dev/finflow/transaction/
            │   │   └── TransactionServiceApplication.java
            │   └── resources/
            │       └── application.properties
            └── test/java/dev/finflow/transaction/
                └── TransactionServiceApplicationTests.java
```

## What each starter file does

- `pom.xml`: Maven's project definition, comparable to dependency/build metadata
  in Python's `pyproject.toml`. `groupId` identifies the project namespace,
  `artifactId` names this service, and `version` labels its build. `SNAPSHOT`
  means a development version. The Boot parent supplies compatible library
  versions and build defaults. `java.version`   targets Java 25. The Boot plugin
  provides `spring-boot:run` and executable packaging; it is a build tool, not
  an application dependency.
- `TransactionServiceApplication.java`: the executable entry point. `package`
  gives the class its namespace, similar to a Python module path. `import`
  brings named types into scope. Braces delimit blocks; semicolons end statements.
  `public class` declares a publicly accessible class whose name matches its file.
  `public static void main(String[] args)` is the JVM entry method: public means
  accessible, static means no instance is needed, void means no returned value,
  and `String[]` is an array of command-line strings. The JVM executes compiled
  Java bytecode. `TransactionServiceApplication.class` is the class's runtime
  metadata, not an instance. `SpringApplication.run(...)` starts Spring and the
  embedded web server.
- `@SpringBootApplication`: an annotation (metadata inspected by the framework,
  loosely comparable to decorator-driven registration in Python). It enables
  configuration, auto-configuration based on dependencies, and scanning for
  Spring-managed components in this package and its subpackages. A class placed
  outside that tree will not automatically be discovered by this scan.
- `application.properties`: runtime configuration. It names the application and
  binds the server to localhost:8080 for early local learning. No secrets belong
  here. Later containers will require an appropriate bind address. Properties
  can be overridden by environment variables or command-line arguments.
- `TransactionServiceApplicationTests.java`: startup integration test. `@Test`
  marks a JUnit 5 test method. `@SpringBootTest` starts a Spring application
  context; a failure to initialize causes the test to fail. The empty method is
  intentional: context startup is the assertion. It does not send HTTP requests
  or prove API correctness, and its default mode does not open a listening port.
- `.gitignore`: excludes build output, editor state, local environment files,
  and logs from commits. It is not a substitute for secret management.
- `docs/architecture/phase-0-and-1.md`: the proposed boundaries and request flow.
- `docs/adr/0001-single-service-and-maven.md`: records the first architectural
  decision, its rationale, and its trade-offs. ADR means Architecture Decision Record.
- `docs/learning/`: preserves the mentoring rules and your first assignment.

## Minimum Phase 1 dependencies

- `spring-boot-starter-web`: Spring MVC routing, JSON serialization via Jackson,
  and an embedded Tomcat HTTP server. Rough analogy: FastAPI routing plus JSON
  handling plus the server runtime, supplied as a compatible bundle.
- `spring-boot-starter-validation`: request-field validation using Jakarta Bean
  Validation and Hibernate Validator. This validator does not require a database.
- `spring-boot-starter-test` (test scope only): JUnit 5, Spring testing support,
  assertions, and Mockito. Mockito is available, but we will use real objects
  unless a mock makes the test meaningfully better.

A starter pulls in other libraries transitively, so these are three direct
dependencies, not three total JARs. No Lombok: write explicit Java while learning.

## Learning and Git

Task 001's enum and two tests have been reviewed and committed.
Task 002's record and validation tests have been reviewed (10 tests pass).
Task 003's service and tests have been reviewed and committed (12 tests pass).
Current assignment: [task 004](docs/learning/task-004-in-memory-storage.md).
For each task: explain the problem, predict behavior, implement, test, review,
then commit a small coherent change. Use `git status` and `git diff` before staging;
review `git diff --cached` before committing. Stage only files belonging to the
step. The mentor has not created any commits.

Suggested first commit: `chore(transaction): initialize Spring Boot project skeleton`.

## References

- [Spring Boot 3.5 system requirements](https://docs.spring.io/spring-boot/3.5/system-requirements.html)
- [Spring Boot build systems and starters](https://docs.spring.io/spring-boot/3.5/reference/using/build-systems.html)
- [Spring dependency injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)
