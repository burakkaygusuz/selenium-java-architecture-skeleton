# Selenium Java Architecture Skeleton

A lean automation architecture built for e2e testing of web applications, developed with Java and Selenium 4.

## Prerequisites

Make sure you have installed and configured the environment variables and all the following prerequisites on your
development machine:

| OS      | JDK                            | Maven                 | Docker                                        |
|---------|--------------------------------|-----------------------|-----------------------------------------------|
| Windows | `scoop install java/openjdk`   | `scoop install maven` | `winget install -e --id Docker.DockerDesktop` |
| macOS   | `brew install openjdk`         | `brew install maven`  | `brew install docker`                         |

## Executing The Tests

- Clone the repository.

```shell
git clone git@github.com:burakkaygusuz/selenium-java-architecture-skeleton.git
```

- Change the directory.

```shell
cd selenium-java-architecture-skeleton
```

- Execute the .yml file to start the Selenium Grid.

```shell
docker-compose -f docker-compose.yml up
```

- Run the test.

```shell
mvn clean test
```
