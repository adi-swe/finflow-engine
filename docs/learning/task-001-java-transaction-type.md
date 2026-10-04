# Task 001: First Java domain type

## WHY

A financial API needs an explicit vocabulary of supported transaction types.
Start with one tiny type so you learn Java syntax before Spring routing.

## HOW

A Java enum is a named type with a fixed set of constants, similar to Python's
`Enum`. Its source file must match the public type's name. Use package
`dev.finflow.transaction`. Package names correspond to the directory path under
`src/main/java`; a package is a namespace, not a separate running service.

JUnit 5 uses `@Test` to identify a test method, analogous to how pytest discovers
test functions. Test methods can use static assertion methods from
`org.junit.jupiter.api.Assertions`; learn imports and static imports while writing
this test. `assertSame` checks object identity, whereas `assertEquals` checks
equality. For an exception test use `assertThrows` with a lambda such as
`() -> expression`: roughly Python's `lambda: expression`, evaluated by JUnit.

## IMPLEMENT

1. Run the existing startup test and application using the README commands.
2. Create `TransactionType.java` beside the application class. Declare a public
   enum with just `PAYMENT`. Do not invent further business types yet.
3. Create `TransactionTypeTests.java` in the matching test package.
4. Write one test showing `TransactionType.valueOf("PAYMENT")` returns the
   PAYMENT constant and one showing `valueOf("REFUND")` throws
   `IllegalArgumentException`. This is a small exercise in Java types and JUnit;
   later tests will focus on our own rules rather than retesting Java itself.
5. Explain in your own words `package`, `public`, `enum`, `@Test`, and the
   difference between compilation and execution. Add your answer below or send
   it in chat with the implementation for review.

Before running the tests, predict what `valueOf("payment")` does. Explain your
prediction when you submit the task; we will review the behavior together.

## TEST

Run `mvn -f services/transaction-service/pom.xml test` from the repository root.
Expect the original startup test and your two enum tests to pass. No Spring
annotation is needed on the enum unit test: it exercises a plain Java type.
Do not implement a controller, service, storage, or transaction creation yet.

## FAILURE

A misspelled public type/file name or package can prevent compilation.
An unrecognized value at an API boundary needs deliberate handling. Predict
whether passing a bad value through to a client should be a 4xx or a 5xx response;
we will design that translation during the validation assignment.

## PRODUCTION

A type vocabulary is an API contract. Changing it can affect clients. We will
agree on supported values and error behavior rather than add arbitrary constants.

## INTERVIEW: five questions before the next step

1. What does Maven do, and how do `test`, `package`, and `spring-boot:run` differ?
2. What does `public static void main(String[] args)` mean, word by word?
3. What is a Java package, and why put future controllers under
   `dev.finflow.transaction`?
4. Trace a JSON request through the proposed API. Where do HTTP concerns end and
   transaction rules begin, and how does Spring supply the service to a controller?
5. Predict what happens to in-memory transactions when the process restarts or
   when two instances receive requests. What promise should Phase 1 avoid making?

Suggested commit after review: `feat(transaction): define payment transaction type`.

## Learner notes

Write your predictions and explanations here when completing the assignment.
