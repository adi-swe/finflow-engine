# Task 004: Store and retrieve transactions in memory

## WHY

Creation currently returns an object but keeps no record of it. Keep transactions
inside the service, keyed by server-generated identifiers, so they can be read
back. Account IDs cannot serve as transaction IDs: one account has many payments.

## HOW

UUID is a Java identifier type; UUID.randomUUID() generates a random identifier.
It is neither sequential nor a mathematical guarantee of uniqueness.
Map<UUID, Transaction> is a typed key/value collection, similar to a Python dict.
The angle brackets are generics: the compiler checks key and value types.

Use an instance field with this declaration:

```java
private final Map<UUID, Transaction> transactions = new ConcurrentHashMap<>();
```

Imports: java.util.Map, java.util.UUID, java.util.Optional, and
java.util.concurrent.ConcurrentHashMap. private limits access to the class;
final prevents reassigning the map reference, not changing its contents.
Do not make the map static: each service instance should own its state.

ConcurrentHashMap supports concurrent individual operations. Ordinary HashMap
does not support concurrent mutation safely. Thread safety of put/get does not
make arbitrary sequences of operations atomic or make the state durable.

Optional<Transaction> explicitly represents a present or missing result, somewhat
like Python's Transaction | None, but as a container with methods.
Optional.ofNullable(value) wraps a possibly null value. map.get(id) returns null
for a missing key. Use isEmpty() to test absence and orElseThrow() to access a
value expected to exist in a test.

## IMPLEMENT

Modify TransactionService.java only for production code. Keep Transaction unchanged.

- Change create's return type to UUID. Construct the validated Transaction first,
  generate an ID, store the transaction under it, and return the ID.
- Add public Optional<Transaction> findById(UUID transactionId).
- Explicitly reject a null lookup ID with IllegalArgumentException; Optional
  represents an unknown non-null ID, not invalid input.
- Keep state in the service instance field above. Do not create a repository
  interface or HTTP endpoint. Do not implement client IDs or idempotency yet.

We deliberately keep the identifier in the map key for this exercise. When we
add an HTTP response DTO, it can expose the ID alongside the transaction fields.

## TEST

Modify TransactionServiceTests.java. Update valid creation to capture the UUID,
look it up, and assert the retrieved Transaction equals the expected record.
Keep the negative-amount rejection test. Add four tests:

1. Unknown non-null UUID returns an empty Optional.
2. Two creates with identical inputs return different IDs and both are retrievable.
   Duplicated requests currently create distinct transactions; this is intentional
   before we design idempotency, not a financial reliability guarantee.
3. A transaction created in service A is not visible in new service B.
4. Null lookup ID throws IllegalArgumentException.

Use a fresh service per test, no Spring context and no mocks. Expect 16 tests
total if previous tests are unchanged. These verify sequential behavior, not a
proof of concurrency correctness.

## FAILURE: predict before implementing

What happens to these records when the JVM restarts? If we run two application
instances, can one instance read a transaction created by the other? Why must
validation happen before inserting into the store?

## PRODUCTION

This map is unbounded, process-local, and volatile. It offers no disk durability,
cross-instance sharing, or atomic coordination with future Kafka publishing.
It is a learning store, not a financial system of record. PostgreSQL remains
deferred until the learner explicitly completes Phase 1.

## INTERVIEW

- Why does final not make this map immutable?
- Why use ConcurrentHashMap if Spring manages one service instance?
- Does generating a UUID prevent duplicate business transactions?

Suggested commit after review: feat(transaction): store transactions in memory by generated ID
