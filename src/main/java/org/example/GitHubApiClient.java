package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GitHubApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Put GitHub username: ");
        String username = scanner.nextLine();
        try {
            JsonNode result = getUserRepos(username);
            System.out.println("Result:\n" + result.toPrettyString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static JsonNode getUserRepos(String username) throws IOException, InterruptedException {
        String url = "https://api.github.com/users/" + username + "/repos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return createErrorResponse(404, "The specified user does not exist.");
        }

        JsonNode repos = objectMapper.readTree(response.body());
        return filterRepos(repos);
    }

    private static JsonNode filterRepos(JsonNode repos) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode result = mapper.createArrayNode();

        for (JsonNode repo : repos) {
            if (!repo.get("fork").asBoolean()) {
                String repoName = repo.get("name").asText();
                String ownerLogin = repo.get("owner").get("login").asText();
                String branchesUrl = repo.get("branches_url").asText().replace("{/branch}", "");

                HttpRequest branchRequest = HttpRequest.newBuilder()
                        .uri(URI.create(branchesUrl))
                        .header("Accept", "application/json")
                        .build();

                HttpResponse<String> branchResponse = client.send(branchRequest, HttpResponse.BodyHandlers.ofString());

                JsonNode branches = objectMapper.readTree(branchResponse.body());

                JsonNode repoInfo = mapper.createObjectNode()
                        .put("Repository Name", repoName)
                        .put("Owner Login", ownerLogin)
                        .set("Branches", createBranchesNode(branches));
                result.add(repoInfo);
            }
        }
        return result;
    }

    private static JsonNode createBranchesNode(JsonNode branches) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode branchesArray = mapper.createArrayNode();

        for (JsonNode branch : branches) {
            ObjectNode branchInfo = mapper.createObjectNode();
            branchInfo.put("Name", branch.get("name").asText());
            branchInfo.put("Last Commit SHA", branch.get("commit").get("sha").asText());
            branchesArray.add(branchInfo);
        }
        return branchesArray;
    }

    private static JsonNode createErrorResponse(int status, String message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorResponse = mapper.createObjectNode();
        errorResponse.put("status", status);
        errorResponse.put("message", message);
        return errorResponse;
    }
}
