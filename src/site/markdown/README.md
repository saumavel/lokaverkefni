# HBV202G - Final Assignment

---

A Rock-Paper-Scissors game implementing the *Observer* pattern based on JUnit4 tests.

---
### Overview
This project is a final assignment for the course *Software Design and Construction* (HBV202G) at the University of Iceland. The project is an extended version of assignment 7 from this course, which was to create a "Rock, Paper, Scissors" game with the help of an AI coding assistant. We have gotten a confirmation from our teachers that the project is suitable to be submitted. 

---

### Project Structure
- `src/main/java`:
    - `is.hi.hbv202g.lokaverkefni.command`: Handles game command operations.
    - `is.hi.hbv202g.lokaverkefni.game`: Handles the game logic and user interface in the game.
    - `is.hi.hbv202g.lokaverkefni.model`: Refactored implementation using the *Template Method* pattern.
    - `is.hi.hbv202g.lokaverkefni.observer`: Observer pattern implementation.
    - `is.hi.hbv202g.lokaverkefni.options`: Game options.
    - `is.hi.hbv202g.lokaverkefni.score`: Score manager.
    - `is.hi.hbv202g.lokaverkefni.strategy`: Player strategies.
- `src/test/java`:
  - `is.hi.hbv202g.lokaverkefni.command`: Test cases for command implementation.
  - `is.hi.hbv202g.lokaverkefni.game`: Game.
  - `is.hi.hbv202g.lokaverkefni.model`: Refactored implementation using the *Template Method* pattern.
  - `is.hi.hbv202g.lokaverkefni.observer`: Observer pattern implementation.
  - `is.hi.hbv202g.lokaverkefni.options`: Game options.
  - `is.hi.hbv202g.lokaverkefni.score`: Score manager.
  - `is.hi.hbv202g.lokaverkefni.strategy`: Player strategies.

---

## Getting Started
### Prerequisites
- Java JDK 17 or higher
- Maven 3.6+
### Installation
#### Clone the repository:
```bash
git clone https://github.com/saumavel/lokaverkefni.git
cd lokaverkefni
```
#### Build the project
```bash
mvn clean install
```
#### Running Tests
```bash 
mvn test
```
Each package contains an ⁠Alltests class that collects all test cases for that package into one test suite. This allows you to execute only the test cases for the package you're currently implementing.
#### Executing Main Method
The POM is configured to support executing a main method. You can run the main class using:
```bash
mvn exec:java 
```

---

## Packaging and Running Without an IDE
To create a runnable `.jar` file with all dependencies included (fat JAR), run:

```bash
mvn clean package assembly:single
```

#### Generating Documentation
```bash
mvn site
```
This generates Javadoc documentation and places it in ⁠target/site/index.html.

---

## Design Patterns Implemented
#### Observer Pattern

The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
Template Method Pattern

The project uses JUnit4 for testing. Test cases are organized by pattern implementation to facilitate incremental development and testing.
License

This project is licensed under the MIT License - see the LICENSE file for details.
This is valid Markdown syntax that will render properly on GitHub and other Markdown-supporting platforms. It includes all the requested improvements:

1. Documentation about executing a main method.
2. Additional headings and section titles.
3. A relative hyperlink to the [licence file](LICENSE).
4. A link to the [GitHub repository](https://github.com/saumavel/lokaverkefni).
5. A link to our [design document](../markdown/design.md). 
