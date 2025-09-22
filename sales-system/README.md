# Sales System (Spring Boot)

Simple sales system with:
- **Products Management**: list / create / update
- **Clients Management**: list / create / update
- **Sales**: list / create with multiple transactions / update quantities & prices
- **Logging**: every update to a sale transaction (qty/price) is written to `sale_transaction_logs`

## Tech
- Java 17+ (tested with Java 25)
- Spring Boot 3.1.x
- Spring Data JPA (Hibernate)
- Maven

---

## Quick Start

```bash
# 1) Build (skip tests for faster build)
mvn -q -DskipTests package

# 2) Run on port 8081
java -jar target/sales-system-0.0.1-SNAPSHOT.jar
