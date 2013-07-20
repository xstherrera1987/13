#### mvn -DskipTests goal
+ skip tests

#### mvn exec:java
+ execute application normally (assuming it is already packaged)
+ + otherwise: **mvn package exec:java**

#### mvn exec:java -Dexec.args="arg1 arg2 arg3..."
+ execute application by passing arguments
