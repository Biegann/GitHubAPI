# GitHub API Client

## Description
The application retrieves information about a GitHub user's repositories that are not forks.

## Usage
1. Clone the repository.
2. Build the project using Maven:
    ```sh
    mvn clean install
    ```
3. Run the application:
    ```sh
    java -jar target/GitHubAPI-1.0-SNAPSHOT-jar-with-dependencies.jar

    ```
4. In the application code, change `username` to the GitHub username whose repositories you want to fetch.

## Dependencies
- Java 17+
- Maven
- Libraries:
  - Jackson
  - HttpClient
