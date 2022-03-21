package com.drash.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.drash.common.DrashCalorieException;
import com.drash.lambda.APIGatewayProxyResponseEventHelper;
import com.drash.lambda.Injector;
import com.drash.lambda.RequestHelper;
import com.drash.lambda.model.UserInfo;
import com.drash.persistence.domain.Entry;
import com.drash.persistence.domain.User;
import com.drash.persistence.repo.EntryRepo;
import com.drash.persistence.repo.UserRepo;

import javax.inject.Inject;
import java.util.List;

public class DeleteEntryHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject UserRepo userRepo;
    @Inject EntryRepo entryRepo;
    @Inject RequestHelper requestHelper;
    @Inject APIGatewayProxyResponseEventHelper apiGatewayProxyResponseEventHelper;

    public DeleteEntryHandler() {
        Injector.getInjector().inject(this);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        final var currentUser = requestHelper.getCurrentUserInfo(input);
        return this.deleteFoodEntry(input.getQueryStringParameters().get("entryId"), currentUser);
    }

    private APIGatewayProxyResponseEvent deleteFoodEntry(String entryId, UserInfo currentUser) {

        final var foodEntry = this.entryRepo.get(entryId);
        if(foodEntry.isEmpty()) throw new DrashCalorieException("Food Entry is not present in the system");

        if(!this.checkIfUserCanDeleteEntry(currentUser, foodEntry.get()))
            return apiGatewayProxyResponseEventHelper.createBadRequestResponse(List.of("The user is not permitted to delete this food entry"));

        entryRepo.delete(foodEntry.get());
        return apiGatewayProxyResponseEventHelper.createResponse("Successfully deleted food Entry");
    }

    private boolean checkIfUserCanDeleteEntry(UserInfo currentUser, Entry entry) {
        final var optionalUser = userRepo.get(currentUser.email());
        if(optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        if(!user.getType().equals("ADMIN") && !user.getPk().equals(entry.getPk()))
            return false;
        return true;
    }
}
