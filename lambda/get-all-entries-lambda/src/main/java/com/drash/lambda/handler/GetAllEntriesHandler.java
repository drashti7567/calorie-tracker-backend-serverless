package com.drash.lambda.handler;

import com.drash.common.DrashCalorieException;
import com.drash.lambda.APIGatewayProxyResponseEventHelper;
import com.drash.lambda.Injector;
import com.drash.lambda.RequestHelper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.drash.lambda.mapper.ModelMapper;
import com.drash.lambda.mapper.ModelMappingException;
import com.drash.lambda.model.UserInfo;
import com.drash.lambda.model.response.EntryModel;
import com.drash.persistence.domain.Entry;
import com.drash.persistence.repo.EntryRepo;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllEntriesHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject EntryRepo entryRepo;
    @Inject RequestHelper requestHelper;
    @Inject ModelMapper<Entry, EntryModel> entryModelMapper;
    @Inject APIGatewayProxyResponseEventHelper apiGatewayProxyResponseEventHelper;

    public GetAllEntriesHandler() {
        Injector.getInjector().inject(this);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        final var currentUser = requestHelper.getCurrentUserInfo(input);
        final var foodEntries = this.getFoodEntries(currentUser);
        return apiGatewayProxyResponseEventHelper.createResponse(foodEntries);
    }

    private List<EntryModel> getFoodEntries(final UserInfo currentUser) {
        final var entries = entryRepo.listByUserEmail(currentUser.email());
        return entries.parallelStream()
                .map(this::toModel)
                .sorted(Comparator.comparing(EntryModel::lastUpdatedTimestamp).reversed())
                .collect(Collectors.toList());
    }

    private EntryModel toModel(final Entry entry) {
        try {
            return entryModelMapper.map(entry);
        } catch (final ModelMappingException e) {
            throw new DrashCalorieException("Unable to map Entry to response model.", e);
        }
    }
}
