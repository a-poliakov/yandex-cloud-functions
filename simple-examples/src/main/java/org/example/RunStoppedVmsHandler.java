package org.example;

import yandex.cloud.api.compute.v1.InstanceOuterClass;
import yandex.cloud.api.compute.v1.InstanceServiceGrpc;
import yandex.cloud.api.compute.v1.InstanceServiceOuterClass;
import yandex.cloud.sdk.ServiceFactory;
import yandex.cloud.sdk.auth.Auth;

import java.util.function.Function;

public class RunStoppedVmsHandler implements Function<String, String> {
    @Override
    public String apply(String folderId) {
        // Авторизация в SDK при помощи сервисного аккаунта
        var defaultComputeEngine = Auth.computeEngineBuilder().build();
        var factory = ServiceFactory.builder()
                .credentialProvider(defaultComputeEngine)
                .build();
        var instanceService = factory.create(InstanceServiceGrpc.InstanceServiceBlockingStub.class, InstanceServiceGrpc::newBlockingStub);
        var listInstancesRequest = InstanceServiceOuterClass.ListInstancesRequest.newBuilder().setFolderId(folderId).build();
        // Получение списка Compute Instance по заданному запросом FolderId
        var listInstancesResponse = instanceService.list(listInstancesRequest);
        var instances = listInstancesResponse.getInstancesList();
        var count = 0;
        for (var instance : instances) {
            if (instance.getStatus() != InstanceOuterClass.Instance.Status.RUNNING) {
                var startInstanceRequest = InstanceServiceOuterClass.StartInstanceRequest.newBuilder().setInstanceId(instance.getId()).build();
                // Операция запуска Compute Instance с указанным ID
                var startInstanceResponse = instanceService.start(startInstanceRequest);
                if (!startInstanceResponse.hasError()) {
                    count++;
                }
            }
        }
        return String.format("Started %d instances", count);
    }
}