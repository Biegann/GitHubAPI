# GitHub API Client

## Description

The GitHub API Client is a Java application that retrieves information about a GitHub user's repositories that are not forks. The application uses GitHub's REST API to fetch repository and branch details and processes them to display relevant information.

## Usage

### Prerequisites

1. **Java 21+**: Make sure you have Java 21 or a later version installed on your machine.
2. **Maven**: Ensure Maven is installed to build the project.

### Steps to Run the Application

1. **Clone the Repository**

    ```bash
    git clone https://github.com/Biegann/GitHubAPI
    cd GitHubAPI
    ```

2. **Build the Project**

   Use Maven to build the project and package it into a fat JAR file:

    ```bash
    mvn clean install
    ```

3. **Run the Application**

   Execute the fat JAR file to run the application:

    ```bash
    java -jar target/GitHubAPI-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```

4. **View the Results**

   The application will print the result in JSON format to the console, showing repository names, owners, and branch information.

## Dependencies

- **Java 21+**
- **Maven**

### Libraries

- **Jackson**: For JSON processing.
- **HttpClient**: For making HTTP requests to the GitHub API.

## Configuration

- The application does not require additional configuration files.

## Troubleshooting

- **Error 404**: If you get a 404 error, it might mean the specified GitHub username does not exist or the user has no public repositories.
- **Network Issues**: Ensure you have a working internet connection to access the GitHub API.




