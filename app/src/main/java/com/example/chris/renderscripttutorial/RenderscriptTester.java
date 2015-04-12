package com.example.chris.renderscripttutorial;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

import java.lang.reflect.Type;

/**
 * Created by Chris on 4/11/2015.
 */
public class RenderscriptTester {


    ScriptC_SampleKernel sampleKernel;

    //Input allocations
    Allocation allocIn;
    Allocation allocOut;

    //Types
    Type inputType;
    Type outputType;

    //Renderscript object
    RenderScript rs;

    //Arbitrary data that means nothing
    byte[] dataInput;
    byte[] dataOutput;

    //Context
    Context context;

    RenderscriptTester(Context context){
        this.context = context;
        generateData();
        buildTypes();
        initAllocations();
        runScript();
    }


    private void generateData(){
        dataOutput = new byte[50];
        dataInput = new byte[50];

        for(int i = 0; i < 50; i++){
            dataInput[i] = (byte)(i+1);
        }

    }

    private void initAllocations(){

    }

    private void buildTypes(){
        sampleKernel = new ScriptC_SampleKernel(rs);
    }


    private void runScript(){

    }

}
