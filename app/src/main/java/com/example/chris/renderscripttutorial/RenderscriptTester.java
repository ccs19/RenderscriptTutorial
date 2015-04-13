package com.example.chris.renderscripttutorial;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.Type;


/**
 * Created by Christopher Schneider on 4/11/2015.
 */
public class RenderscriptTester {


    ScriptC_SampleKernel sampleKernel;

    //Input allocations
    Allocation allocIn;
    Allocation allocOut;

    //Types
    Type.Builder inputType;
    Type.Builder outputType;

    //Renderscript object
    RenderScript rs;

    //Arbitrary data that means nothing
    byte[] dataInput;
    byte[] dataOutput;

    //Context
    Context context;

    //Allocation USAGE
    int USAGE = Allocation.USAGE_SCRIPT;

    //Launch options to determine how many iterations over data
    Script.LaunchOptions launchOptions;

    //Matrix size
    int matrixWidth = 8;
    int matrixHeight = 8;


    RenderscriptTester(Context context){
        this.context = context;
        generateData();
        buildTypes();
        initAllocations();
        runScript();
    }


    private void generateData(){
        dataOutput = new byte[matrixWidth*matrixHeight];
        dataInput = new byte[matrixWidth*matrixHeight];

        byte b = 1;
        for(int i = 0; i < matrixWidth*matrixHeight; i++){
            dataInput[i] = b++;
        }
    }

    private void buildTypes(){
        rs = RenderScript.create(context);

        //Input/Output type is U8_4
        //This is byte in Java, uchar in Renderscript. The 4 represents 4 bytes per element
        inputType = new Type.Builder(rs, Element.U8_4(rs))
                .setX(matrixWidth/4)   //These values determine the amount of memory allocated
                .setY(matrixHeight);   //In this case, we divide by four because the U8_4 has four bytes
        outputType = new Type.Builder(rs, Element.U8_4(rs))
                .setX(matrixWidth/4)
                .setY(matrixHeight);
    }


    private void initAllocations(){
        allocIn = Allocation.createTyped(rs, inputType.create(), USAGE);
        allocOut = Allocation.createTyped(rs, outputType.create(), USAGE);
    }




    private void runScript(){
        //Create script instance first
        sampleKernel = new ScriptC_SampleKernel(rs);

        //Set allocations in script
        sampleKernel.set_rsAllocationIn(allocIn);
        sampleKernel.set_rsAllocationOut(allocOut);

        //Copy data into allocation
        allocIn.copyFrom(dataInput);

        //Invoke script
        sampleKernel.forEach_root(allocIn, allocOut);

        //Retrieve data from allocation
        allocOut.copyTo(dataOutput);

        //Results!
        String input = "INPUT DATA: \n";
        String output = "OUTPUT DATA: \n";

        for(int i = 0; i < matrixWidth; i++){
            input += "\n";
            for(int j = 0; j < matrixHeight; j++){
                input += String.format("%3d ", dataInput[i*matrixWidth + j]);
            }
        }
        input += "\n";

        for(int i = 0; i < matrixWidth; i++){
            output += "\n";
            for(int j = 0; j < matrixHeight; j++){
                output += String.format("%3d ", dataOutput[i*matrixWidth + j]);
            }
        }

        System.out.println(input);
        System.out.println(output);

    }

}
