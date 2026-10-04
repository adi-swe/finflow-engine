# ADR-001: Start with one service and Maven

Date: 2026-10-04
Status: Accepted for Phase 0 and Phase 1

## Context

The learner understands Python backends and distributed systems but is new to
Java syntax and Spring. The eventual financial event platform needs several
technologies; introducing them together would hide basic behavior and failures.

## Decision

Use one Spring Boot application, Java 25, and Maven. Pin Boot to 3.5.16, a stable
3.5 release compatible with Java 25 and the requested JUnit 5 learning path.
This is a deliberate baseline, not a claim that 3.5 is the latest Boot major.
Reassess dependencies and support before deployment.

Use three direct starters: web, validation, and test. Keep Phase 1 storage in
memory, inside the concrete transaction service. Introduce storage abstraction
only when persistence creates a useful boundary. Do not add infrastructure yet.

## Alternatives and consequences

Maven uses declarative XML and a conventional lifecycle (compile, test, package).
Its verbosity is acceptable for this small application. Gradle uses a Kotlin or
Groovy build DSL and offers flexible task configuration and incremental build
features; that flexibility adds another language/tool surface for a Java beginner.
Both can build production Spring applications. Choose Maven to reduce early
decisions, not because Gradle is unsuitable for production.

One service makes debugging and review straightforward. In-memory state permits
isolated learning but is neither durable nor shared across instances. We cannot
claim reliable financial processing from this design. Later architectural
decisions will get separate ADRs when their actual problems arise.
