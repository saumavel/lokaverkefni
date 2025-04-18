# HBV202GAssignment8
A Maven project skeleton. The provided Maven POM sets the Java version to 17.

All classes need to be located in Java package `is.hi.hbv202g.assignment8`.

Class and method names of the submitted solution need to be **exactly** the same 
as in the UML class diagram in the assignment PDF. 

If you like, you can import from project directory `UML` the file 
`library_system.uxf` into the online UML editor https://www.umletino.com/ 
and copy/paste from there class and method names: click on a class and then, 
you can copy from the `properties` window on the right hand side class 
and method names.


# HBV202GAssignment9
A project implementing the *Composite* pattern, *Observer* pattern, and a variation of the *Template Method* pattern based on JUnit4 tests.
### Overview
This project demonstrates design pattern implementations through a structured Maven project. It provides test cases to validate each pattern implementation.
### Project Structure
- `src/main/java`:
    - `is.hi.hbv202g.ass9.composite`: Implementation of the *Composite* pattern.
    - `is.hi.hbv202g.ass9.compositeObserved`: Implementation of the *Composite* pattern combined with the *Observable* pattern.
    - `is.hi.hbv202g.ass9.compositeObservedTemplateMethod`: Refactored implementation using the *Template Method* pattern.

- `src/test/java`:
    - `is.hi.hbv202g.ass9.composite`: Test cases for the Composite implementation.
    - `is.hi.hbv202g.ass9.compositeObserved`: Test cases for the Composite with Observer implementation.
    - `is.hi.hbv202g.ass9.compositeObservedTemplateMethod`: Test cases for the Template Method refactoring.
## Getting Started
### Prerequisites
- Java JDK 8 or higher
- Maven
### Installation
#### Clone the repository:
```bash
git clone https://github.com/yourusername/HBV202GAssignment9.git
cd HBV202GAssignment9
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
mvn exec:java -Dexec.mainClass="is.hi.hbv202g.ass9.CompositeMain"
```
#### Generating Documentation
```bash
mvn site
```
This generates Javadoc documentation and places it in ⁠target/site/index.html.
## Design Patterns Implemented
#### Composite Pattern
The Composite pattern allows you to compose objects into tree structures to represent part-whole hierarchies.
Observer Pattern

The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
Template Method Pattern

The Template Method pattern defines the skeleton of an algorithm in a method, deferring some steps to subclasses.
Testing Approach

The project uses JUnit4 for testing. Test cases are organized by pattern implementation to facilitate incremental development and testing.
License

This project is licensed under the MIT License - see the LICENSE file for details.

#### Contributing
Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.
Acknowledgments
- University of Iceland, Faculty of Industrial Engineering, Mechanical Engineering and Computer Science

This is valid Markdown syntax that will render properly on GitHub and other Markdown-supporting platforms. It includes all the requested improvements:

1. Documentation about executing a main method.
2. Additional headings and section titles.
3. A relative hyperlink to the [licence file](LICENSE).
4. A relative hyperlink to the [Design Documentation file](/src/site/markdown/DesignDocumentation.md).
