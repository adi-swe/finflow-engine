# Task 003: A concrete transaction service

## WHY

A record represents data. A service provides operations. Give transaction
creation a home separate from future HTTP handling. At this size it is only a
small delegation method; the boundary becomes useful when IDs and storage arrive.
Do not introduce an interface with only one implementation.

## HOW

A normal Java class can have instance methods. Method syntax is visibility,
return type, name, typed parameters, then a body. A method returning Transaction
must return an instance on its normal completion path. `new` calls a constructor.
Compare this to a Python class method using `self`; Java has an implicit `this`
instead of an explicit self parameter.

`@Service` (import org.springframework.stereotype.Service) registers this class
for Spring's component scan. A Spring-managed object is called a bean. The
annotation does not supply business logic, persistence, or thread safety.
Ordinary tests can still construct it with `new TransactionService()` without
starting Spring. Constructor injection will be introduced when the controller
needs this service in the next task.

## IMPLEMENT

Create src/main/java/dev/finflow/transaction/TransactionService.java with package
dev.finflow.transaction. Declare a public class, annotate it @Service, and add:

```java
public Transaction create(String accountId, TransactionType type, BigDecimal amount) {
    // Construct and return a Transaction using these inputs.
}
```

Use an instance method, not static. Do not copy constructor validation into the
service, catch its exceptions, return null on failure, or add storage yet.

## TEST

Create src/test/java/dev/finflow/transaction/TransactionServiceTests.java.
Instantiate the service directly; use no SpringBootTest or mocks.

- A valid create call returns a Transaction equal to the expected record.
- A create call with negative amount throws IllegalArgumentException.

The second test checks that the service preserves the domain's rejection rather
than swallowing it; do not duplicate the entire domain validation test matrix.
Use assertEquals and assertThrows. Run Maven test; expect 12 tests total if the
previous 10 are unchanged. Creating an object does not persist it.

## FAILURE

Before implementing, predict what happens if the constructor throws while the
service is returning its result. Does the caller get a Transaction, null, or an
exception? Explain your answer before adding exception handling.

## PRODUCTION

This service has no mutable shared state yet. Spring normally creates one bean
instance used across requests; future memory storage will require deliberate
concurrency choices. A separate service keeps transport-specific HTTP behavior
out of financial operations and allows direct unit testing.

## INTERVIEW

- What is the difference between a record and a service class?
- What does @Service do, and why can a unit test use new anyway?
- Why avoid duplicating validation rules in two places?

Suggested commit after review: feat(transaction): introduce transaction creation service
