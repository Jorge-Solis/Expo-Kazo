package com.pikazo.global;

import javax.inject.Inject;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
public class SharedData {

    private String testData;

    @Inject
    public SharedData(){

    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }
}
