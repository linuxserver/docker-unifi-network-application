package com.unifi.driver.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DockerContainerManager {

    private DockerClient dockerClient;

    public String getContainerName() {
        return containerName;
    }

    private String containerName = "unifi-network-application";

    public DockerContainerManager() {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .withDockerTlsVerify(false)
                .build();
        this.dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
    }

    public void restartContainer(String containerId) {
        try {
            StopContainerCmd stopCmd = dockerClient.stopContainerCmd(containerId);
            stopCmd.exec();
            System.out.println("Container " + containerId + " stopped.");
            
            StartContainerCmd startCmd = dockerClient.startContainerCmd(containerId);
            startCmd.exec();
            System.out.println("Container " + containerId + " started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String runCommand(String command) {
        StringBuilder output = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Error executing command: " + command);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return output.toString().trim();
    }

    public void editFile(String filePath, String searchPattern, String replacement) {
        String escapedSearchPattern = searchPattern.replace("/", "\\/");
        String escapedReplacement = replacement.replace("/", "\\/");

        String command = String.format(
                "docker exec %s sed -i 's/%s/%s/g' %s",
                containerName, escapedSearchPattern, escapedReplacement, filePath
        );
        runCommand(command);
    }

    public void editSystemProperties(){
        String filePath = "/usr/lib/unifi/data/system.properties";
        String searchPattern = "is_default=false";
        String replacement = "is_default=true";
        this.editFile(filePath, searchPattern, replacement);
    }

}
