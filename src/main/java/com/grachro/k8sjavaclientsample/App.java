package com.grachro.k8sjavaclientsample;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1ServiceList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class App {

    public static KubeConfig getKubeConfig() throws IOException {
        String userHome = System.getProperty("user.home");
        Path path = Paths.get(userHome + "/.kube/config");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return KubeConfig.loadKubeConfig(br);
        }
    }

    public static Set<String> getContext(KubeConfig kubeConfig) {
        TreeSet<String> result = new TreeSet<>();
        for (Object conf : kubeConfig.getContexts()) {
            Map<String, Object> contexts = (Map<String, Object>) conf;
            result.add((String) contexts.get("name"));
        }
        return result;
    }


    public static void main(String[] args) throws IOException {

        System.out.println("##start");

        KubeConfig kubeConfig = getKubeConfig();
        Set<String> contexts = getContext(kubeConfig);

        contexts.forEach((context) -> {
            kubeConfig.setContext(context);

            ApiClient client;
            try {
                client = Config.fromConfig(kubeConfig);
                Configuration.setDefaultApiClient(client);
                CoreV1Api api = new CoreV1Api();
                V1ServiceList svcList = api.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null);
                for (V1Service svc : svcList.getItems()) {
                    System.out.println(context + "\t" + svc.getMetadata().getNamespace() + "\t" + svc.getMetadata().getName());
                }
            } catch (IOException | ApiException e) {
                e.printStackTrace();
            }
        });

        System.out.println("##finish");
    }

}
