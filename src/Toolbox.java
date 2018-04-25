import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Toolbox {

    public static double[] convertArrayListToPrimitiveArray(ArrayList<Double> inputData){
        double[] outputData = new double[inputData.size()];

        for(int i = 0; i < inputData.size(); i++){
            outputData[i] = inputData.get(i);
        }
        return outputData;
    }



    public static double[] averagedJaggedResults(double[][] inputData){

        int nReps = inputData.length;
        int maxColVals = 0;
        for(int i = 0; i < inputData.length; i++){
            if(inputData[i].length > maxColVals)
                maxColVals = inputData[i].length;
        }

        double[] outputData = new double[maxColVals];
        double[] tData = new double[maxColVals];

        for(int i = 0; i < maxColVals; i++){
            double sum = 0.;
            double nVals = 0;
            for(int j = 0; j < inputData.length; j++){
                if(i < inputData[j].length){
                    sum += inputData[j][i];
                    nVals += 1.;
                }
            }
            outputData[i] = sum/nVals;
            //tData[i] = (double)i*interval;
        }
        return outputData;
    }

    public static void writeMultiSpeciesPopSizesToFile(String filename, int[][] multiSpeciesData){

        try{
            File file = new File(filename+".txt");
            if(!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            int L = multiSpeciesData.length;
            int nSpecies = multiSpeciesData[0].length;

            for(int l = 0; l < L; l++){
                String output = String.valueOf(l)+" ";
                for(int m = 0; m < nSpecies; m++){
                    output += String.valueOf(multiSpeciesData[l][m]) +" ";

                }
                bw.write(output);
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){}
    }

    public static void writeMultiSpeciesPopSizesToFile(String filename, double[][] multiSpeciesData){

        try{
            File file = new File(filename+".txt");
            if(!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            int L = multiSpeciesData.length;
            int nSpecies = multiSpeciesData[0].length;

            for(int l = 0; l < L; l++){
                String output = String.valueOf(l)+" ";
                for(int m = 0; m < nSpecies; m++){
                    output += String.valueOf(multiSpeciesData[l][m]) +" ";

                }
                bw.write(output);
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){}
    }


    public static double[][] averagePopulationResults(int[][][] inputPops){

        int nReps = inputPops.length;
        int L = inputPops[0].length;
        int M = inputPops[0][0].length;

        double[][] averagedResults = new double[L][M];

        for(int l = 0; l < L; l++){

            //averagedResults[l][0] = (double)l*100.;

            for(int m = 0; m < M; m++){

                double runningTotal = 0.;
                for(int r = 0; r < nReps; r++){
                    runningTotal += (double)inputPops[r][l][m];
                }
                averagedResults[l][m] = runningTotal/(double)nReps;
            }

        }
        return averagedResults;
    }


    public static void writeTwoArraysToFile(String filename, double[] xdata, double[] ydata){

        try{
            File file = new File(filename+".txt");
            if(!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i < xdata.length; i++){
                String output = String.valueOf(xdata[i])+" "+String.valueOf(ydata[i]);

                bw.write(output);
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){}

    }

    public static void writeThreeArraysToFile(String filename, double[] xdata, double[] ydata1, double[] ydata2){

        try{
            File file = new File(filename+".txt");
            if(!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i < xdata.length; i++){
                String output = String.valueOf(xdata[i])+" "+String.valueOf(ydata1[i])+" "+String.valueOf(ydata2[i]);

                bw.write(output);
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){}
    }
}
