package com.drash.lambda.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.drash.lambda.APIGatewayProxyResponseEventHelper;
import com.drash.lambda.Injector;
import com.drash.lambda.RequestHelper;
import com.drash.lambda.model.UserInfo;
import com.drash.lambda.model.request.UpdateEntryModel;
import com.drash.persistence.domain.Entry;
import com.drash.persistence.domain.User;
import com.drash.persistence.repo.EntryRepo;
import com.drash.persistence.repo.UserRepo;

import javax.inject.Inject;
import java.util.List;

public class UpdateEntryHandler extends BaseRequestHandler<UpdateEntryModel> {

    @Inject UserRepo userRepo;
    @Inject EntryRepo entryRepo;
    @Inject RequestHelper requestHelper;
    @Inject APIGatewayProxyResponseEventHelper apiGatewayProxyResponseEventHelper;

    public UpdateEntryHandler() {
        Injector.getInjector().inject(this);
    }

    @Override
    public APIGatewayProxyResponseEvent process(final UpdateEntryModel input, final APIGatewayProxyRequestEvent event) {
        final var currentUser = requestHelper.getCurrentUserInfo(event);
        return this.updateFoodEntry(event.getQueryStringParameters().get("entryId"), input, currentUser);
    }

    private APIGatewayProxyResponseEvent updateFoodEntry(String entryId, UpdateEntryModel input, UserInfo currentUser) {

        final var foodEntry = this.entryRepo.get(entryId);

        if(foodEntry.isEmpty())
            return apiGatewayProxyResponseEventHelper.createBadRequestResponse(List.of("The entry is not present in the system"));

        if(!this.checkIfUserCanDeleteEntry(currentUser, foodEntry.get()))
            return apiGatewayProxyResponseEventHelper.createBadRequestResponse(List.of("The user is not permitted to delete this food entry"));

        this.updateAndSaveEntry(foodEntry.get(), input);
        return apiGatewayProxyResponseEventHelper.createResponse("Successfully updated food Entry");
    }

    private boolean checkIfUserCanDeleteEntry(UserInfo currentUser, Entry entry) {
        final var optionalUser = userRepo.get(currentUser.email());
        if(optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        if(!user.getType().equals("ADMIN") && !user.getPk().equals(entry.getPk()))
            return false;
        return true;
    }

    private void updateAndSaveEntry(Entry entry, UpdateEntryModel input) {
        entry.setFoodName(input.foodName());
        entry.setPk(input.userEmail());
        entry.setCalories(input.calories());
        entry.setSk(input.eatingTime());

        entryRepo.save(entry);
    }
}
