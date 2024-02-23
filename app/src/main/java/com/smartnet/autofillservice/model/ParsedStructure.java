package com.smartnet.autofillservice.model;

import android.app.assist.AssistStructure;
import android.view.autofill.AutofillId;

public class ParsedStructure {

    public AutofillId usernameId;
    public AutofillId passwordId;
    public AssistStructure assistStructure;


    public ParsedStructure(AssistStructure assistStructure) {
        this.assistStructure = assistStructure;
    }
}
