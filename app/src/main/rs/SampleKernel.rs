
#pragma version(1)
#pragma rs java_package_name(com.example.chris.renderscripttutorial)

rs_allocation rsAllocationIn;
rs_allocation rsAllocationOut;

void root(const uchar4* in, uchar4* out, uint32_t x, uint32_t y) {
    uchar4 modifiedData;

    //Get item from input allocation
    modifiedData = rsGetElementAt_uchar4(rsAllocationIn, x, y);

    uchar addVal = 10;
    //Increment all values by addVal
    modifiedData.w += addVal;
    modifiedData.x += addVal;
    modifiedData.y += addVal;
    modifiedData.z += addVal;

    //Place modified data in output allocation
    rsSetElementAt_uchar4(rsAllocationOut, modifiedData, x, y);



    /**=========================================================================================**/
    /**Another way to modify data. Renderscript automatically populates the in and out data
        in the function prototypes                                                              **/
    /**=========================================================================================**/


        //This also works; Renderscript automatically populates the uchar4* in
        //parameter in the function prototype
        out->w = (in->w)+addVal;
        out->x = (in->x)+addVal;
        out->y = (in->y)+addVal;
        out->z = (in->z)+addVal;
}