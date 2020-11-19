## Fun with Fan

### Run the application
Run `FanApplication.java` as a console application using any IDE and follow the instruction on the console.
 
### Code Base
#### `of.cgi.assignment.lib`
This package contains reusable libraries.

* `.statemachine` package contains a state machine library. The generic interfaces are here.
* `.statemachine.fsm` is an implementation of a finite state machine.

#### `of.cgi.assignment.fan`
Provides fan implementations. The generic `Fan` interface is here. 
* `.celingfan` provides an implementation of `CeilingFan`.

#### `of.cgi.assignment.app`
Contains the main application.

### Limitations
As of the latest, the maven commands were failing with
`java.security.InvalidAlgorithmParameterException: the trustAnchors parameter must be non-empty`.
This is a keystore configuration related issue with openjdk 8. If required this can be investigated and fixed later.
Therefore, please import the project in any IDE to run the application.
