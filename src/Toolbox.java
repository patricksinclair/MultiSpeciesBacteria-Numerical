import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Toolbox {

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

}
