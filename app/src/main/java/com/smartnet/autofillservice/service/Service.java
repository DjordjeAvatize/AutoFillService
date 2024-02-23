package com.smartnet.autofillservice.service;

import static com.smartnet.autofillservice.R.layout.activity_main;

import android.annotation.SuppressLint;
import android.app.assist.AssistStructure;
import android.graphics.Color;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class Service extends AutofillService {

    private static final String TAG = "Service";


    @Override
    public void onFillRequest(FillRequest request, CancellationSignal cancellationSignal,
                              FillCallback callback) {

        List<FillContext> context = request.getFillContexts();
        AssistStructure structure = context.get(context.size() - 1).getStructure();

        // Create an empty list
        List<AssistStructure.ViewNode> emailFields = new ArrayList<>();
        // Populate the list
        identifyEmailFields(structure
                .getWindowNodeAt(0)
                .getRootViewNode(), emailFields);

        if(emailFields.size() == 0) {
            Toast.makeText(getApplicationContext(), "Nema", Toast.LENGTH_LONG).show();
            return;
        }

        String primaryEmail = "Every game 3 monkeys in my team";

        RemoteViews usernamePresentation = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
        usernamePresentation.setTextViewText(android.R.id.text1, primaryEmail);
        RemoteViews passwordPresentation = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
        passwordPresentation.setTextViewText(android.R.id.text1, primaryEmail);


        AssistStructure.ViewNode emailField = emailFields.get(0);

        // Add a dataset to the response
        FillResponse fillResponse = new FillResponse.Builder()
                .addDataset(new Dataset.Builder()
                        .setValue(emailField.getAutofillId(),
                                AutofillValue.forText(primaryEmail), usernamePresentation)
                        .setValue(emailField.getAutofillId(),
                                AutofillValue.forText(primaryEmail), passwordPresentation)
                        .build())
                .build();

        // If there are no errors, call onSuccess() and pass the response
        callback.onSuccess(fillResponse);

    }

    @Override
    public void onSaveRequest(SaveRequest request, SaveCallback callback) {

    }


    private void identifyEmailFields(AssistStructure.ViewNode node,
                                     List<AssistStructure.ViewNode> emailFields) {
        // More code goes here

        if (node.getClassName().contains("EditText")) {
            String viewId = node.getIdEntry();
            if (viewId != null && (viewId.contains("email")
                    || viewId.contains("password"))) {
                emailFields.add(node);
                return;
            }
        }

        for(int i=0; i<node.getChildCount();i++) {
            identifyEmailFields(node.getChildAt(i), emailFields);
        }
    }
}
