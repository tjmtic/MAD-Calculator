****************
**Instructions**
Code Sample
===================

We would like to get to know your coding style and see what you would consider your best work.
In subsequent interviews, we'll talk through your code and make some changes.

Mobile RPN Calculator
=====================

Implement a reverse polish notation (RPN) calculator as a native Android or iOS app. You can use any language and framework that you know well, as long as it's a native implementation (e.g. we don't want React Native or Flutter apps).

Imaginary Context
-----------------

We're building this mobile calculator for people who are comfortable with RPN calculations on their smartphones. We are starting with the basic 4 operators now but will want to eventually implement other operators and potentially on other devices. There's no need to implement these, but design with these future changes in mind.

Specifications
--------------

1. The calculator should have a user-friendly mobile interface
2. It should implement the four standard arithmetic operators
3. The calculator should handle errors and provide clear feedback to the user
4. The app should have a clear way to reset the calculation or start a new one
**************

**************
**Solution**
*Tech Stack*
Kotlin
Coroutines
Flows
Composables
Hilt
--Single Activity Pattern
--MVVM / MVI
--Unidirectional Data Flow
--Sealed Class States
--Feature/Module Architecture
--Interface/Implementation pattern definitions
--Repository pattern
--Dependency Injection
--Layered Unit Testing


*Possible Future Work*
-README
-UI Design Improvements
-ViewModel state tests with mock dependencies, and Turbine dependency for flow state testing
-Database implementation for storing result
-Calculator Feature separate module
-Core module separation (Model, Database, Repository, UI)