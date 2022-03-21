package com.drash.lambda.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.drash.lambda.APIGatewayProxyResponseEventHelper;
import com.drash.lambda.Injector;
import com.drash.lambda.RequestHelper;
import com.drash.lambda.model.UserInfo;
import com.drash.lambda.model.request.CreateEntryModel;
import com.drash.persistence.domain.Entry;
import com.drash.persistence.domain.User;
import com.drash.persistence.repo.EntryRepo;
import com.drash.persistence.repo.UserRepo;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

public class CreateEntryHandler extends BaseRequestHandler<CreateEntryModel> {

    @Inject UserRepo userRepo;
    @Inject EntryRepo entryRepo;
    @Inject RequestHelper requestHelper;
    @Inject APIGatewayProxyResponseEventHelper apiGatewayProxyResponseEventHelper;

    public CreateEntryHandler() {
        Injector.getInjector().inject(this);
    }

    @Override
    public Object process(final CreateEntryModel input, final APIGatewayProxyRequestEvent event) {
        final var currentUser = requestHelper.getCurrentUserInfo(event);
        this.createAndSaveEntry(input);
        return "Successfully saved new Food Entry in the system";
    }

    private void createAndSaveEntry(CreateEntryModel input) {
        Entry entry = new Entry();
        entry.setGs1pk(this.generateUniqueId());
        entry.setFoodName(input.foodName());
        entry.setPk(input.userEmail());
        entry.setCalories(input.calories());
        entry.setSk(input.eatingTime());
        entry.setCreatedAt(LocalDateTime.now());
        entryRepo.save(entry);
    }

    public String generateUniqueId() {
        Long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return Long.toString(currentTime, 36);
    }
}
