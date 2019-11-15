package client.logic;

/**
 * A class that is used to generate the NxN Omega Network verilog script
 * the class constructor accepts the N value
 */
public class OmegaScriptBuilder {

    //initialize the N size & the address size
    private int size;
    private int address_size;
    
    //Initialize the StringBuilder that will holds the script
    private StringBuilder str = new StringBuilder();

    //constructor to initialize the N size & the address size
    public OmegaScriptBuilder(int size) {
        super();
        this.size = size;
        this.address_size = (int) log2(size);
    }

    //the main method taht will generate the script into the string builder
    public void generateScript() {
        
        //write the module name, inputs and outputs
        str.append("module omega_network(clk, data_in, data_out);\n\n");        
        str.append("\tinput clk ;\n" );            
        str.append("\tinput [" + (size-1) + ":0] data_in ;\n" );      
        str.append("\toutput reg [" + (size-1) + ":0] data_out ;\n" );  
        str.append("\n");

        //calculate the number of stages and number of switches in each stage
        int stages = (int) log2(size);
        int switchNumber = size / 2;
        
        //loop to initialize all wires
        for (int currentStage = 0; currentStage <= stages; currentStage++){
            
            //add a tab for the code well formating
            str.append("\t");
            
            for(int currentPort = 0; currentPort < size; currentPort++){
                //initialize each wire
                str.append("wire wire" + currentStage + "_" + currentPort + " ; ");
            }                    
            
            //add a new line for the code well formating
            str.append("\n");
        }            
        
        //add a new line for the code well formating
        str.append("\n");
        
        //loop for all stages to initialize all Banyan switches
        for (int currentStage = 1; currentStage <= stages; currentStage++) {

            //initialize variables to hold previous stage, currentInWire and currentOutWire
            int previousStage = currentStage - 1;
            int currentInWire = 0;
            int currentOutWire = 0;

            //loop for all switches in each stage to initialize all Banyan switches
            for (int currentSwitch = 0; currentSwitch < switchNumber; currentSwitch++) {

                //write the Banyan object code
                //each input is connected to the rotated left wire from the previous stage
                //then rotate right to get the wire number
                
                str.append("\tBanyan B" + currentStage + "_" + currentSwitch + "(clk, ");
                str.append("wire" + previousStage + "_" + rotateRight(currentInWire) +
                           ",");
                currentInWire++;
                str.append("wire" + previousStage + "_" + rotateRight(currentInWire) +
                           ",");
                currentInWire++;
                str.append("wire" + currentStage + "_" + currentOutWire + ",");
                currentOutWire++;
                str.append("wire" + currentStage + "_" + currentOutWire + ");\n");
                currentOutWire++;

            }

        }
        
        //add a new line for the code well formating
        str.append("\n");
        
        //loop to assign the last wires to the network output
        for(int currentPort = 0; currentPort < size; currentPort++) {
            str.append("\tassign wire0_" + currentPort + " = data_in[" + currentPort + "] ;\n");
        }
        
        //always block to connect the first wires to the network input
        str.append("\n\talways@(*)\n");        
        str.append("\tbegin\n");
        
        //connect the first wires to the network input
        for(int currentPort = 0; currentPort < size; currentPort++) {
            str.append("\t\tdata_out[" + currentPort + "] = wire" + stages + "_" + currentPort + " ;\n");
        }
        
        //end the always block
        str.append("\tend\n");
        
        //end the module
        str.append("\nendmodule");

    }

    /**
     *a method used to return the string builder holding the script
     */
    public String getScript() {
        return str.toString();
    }

    /**
     *a method used to calculate the log2
     */
    private double log2(int n) {
        return (Math.log(n) / Math.log(2));
    }

    /**
     *a method used to return number after rotating right
     */
    private int rotateRight(int n) {

        boolean isOdd = n % 2 == 1;    
        
        int temp = n >>> 1 ;

        if (isOdd)
            temp = temp + (int) Math.pow(2, address_size - 1); 
        
        return temp ;

    }


}
