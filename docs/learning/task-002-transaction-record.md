# Task 002: A transaction value and its first invariant

## WHY

An enum describes the transaction type, but a transaction also needs data.
Start with a small domain slice: accountId, type, and amount. Other fields come
in later tasks. A PAYMENT amount must be present and strictly positive for our
current model. This is our chosen rule, not a universal rule for financial events.

## HOW

A Java record is a concise data carrier, similar to a frozen Python dataclass.
Java generates a constructor, accessors, equals, hashCode, and toString.
Record fields cannot be reassigned, but referenced mutable objects could still
change: records are only shallowly immutable.

Syntax illustration, unrelated to our transaction implementation:

```java
public record Label(String text) {
    public Label {
        // A compact constructor: check constructor input here.
        // Java assigns it to the record field after this body succeeds.
    }
}
```

`new Label("example")` creates an instance; `label.text()` reads its value.
Unlike ordinary JavaBeans, the accessor is not called `getText()`.

Use `java.math.BigDecimal` for amount, similar to Python's decimal.Decimal.
Construct test amounts with decimal strings, e.g. `new BigDecimal("250.50")`.
`amount.signum()` returns -1, 0, or 1 for negative, zero, or positive values.
Check null before calling a method on amount. Java's null resembles Python's None.
Use `throw new IllegalArgumentException("...")` to reject invalid input; this is
similar to raising ValueError. A compact constructor has no return type or
parameter list and must use the exact record name.

Before implementing, predict what happens if you call signum() on a null amount.
Also predict whether two separately constructed records with the same field
values should be checked with assertSame or assertEquals, and explain why.

## IMPLEMENT

Create Transaction.java in package dev.finflow.transaction with a public record
containing these components in this order:

- String accountId
- TransactionType type
- BigDecimal amount

Write a compact constructor rejecting null, zero, and negative amounts with
IllegalArgumentException. Do not add account/type validation in this task;
we will add rules deliberately rather than claim comprehensive validation.
Keep this a plain Java type: no Spring annotations, controller, or storage.

## TEST

Create TransactionTests.java in the matching test package. No @SpringBootTest.
Write four tests:

1. ACC-123, PAYMENT, and 250.50 produce a transaction whose accessors return those
   values. Use assertEquals for value comparisons.
2. Zero amount throws IllegalArgumentException.
3. Negative amount throws IllegalArgumentException.
4. Null amount throws IllegalArgumentException.

Use assertThrows for the three rejection cases. Run from the repository root:

```sh
mvn -f services/transaction-service/pom.xml test
```

Expect seven tests total: the existing three and your four new tests.
BigDecimal.equals checks both value and scale; compare with a matching decimal
string in the valid test. Numeric equality and scale policy will get their own
discussion before reconciliation.

## FAILURE

An invalid transaction can otherwise enter the system and fail much later.
Constructor checks reject this amount invariant before an instance is created.
Null checks in the wrong order can accidentally produce NullPointerException.
This model does not yet enforce every field's validity or translate exceptions
into HTTP responses.

## PRODUCTION

Money requires an explicit precision, currency, scale, and rounding policy.
BigDecimal does not decide that policy for us. Our positive-amount rule will need
review before introducing refunds, reversals, or signed accounting entries.
Later the API can validate requests for useful client errors while the domain
preserves essential invariants regardless of who constructs it.

## INTERVIEW

- How does a record differ from an ordinary class?
- How does assertEquals differ from assertSame?
- Why enforce an invariant at construction?

Suggested commit after review: feat(transaction): add transaction value with amount validation

## Learner notes

Write your predictions here or send them with your implementation for review.
