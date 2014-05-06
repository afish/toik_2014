toik_2014
=========

Application for synchronization.

Deps:
maven 3.x (won't work with 2)
java 1.7

Aplication was broken into multiple modules:
* cloudsync-gui
* cloudsync-logic
* cloudync-root
* ...


cloudsync-root aggregates the rest of the dependencies (manually - issue with spring-boot i dont know how to get around).
Shared deps are groupped in parent pom.xml in root directory of the repo.

In order to start the project, you have to build all the modules first with `mvn install` and then execute `mvn spring-boot:run` in cloudsync-root