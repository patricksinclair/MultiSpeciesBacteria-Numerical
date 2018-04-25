import java.util.Random;

public class BioSystem {

    Random rand = new Random();
    private int nSpecies, K, L;
    private double alpha;
    Microhabitat[] microhabitats;

    private int initialRandPop = 100;
    private double timeElapsed;

    public BioSystem(int L, int K, int nSpecies, double alpha){
        this.L = L;
        this.K = K;
        this.nSpecies = nSpecies;
        this.alpha = alpha;
        this.timeElapsed = 0.;

        //here all of the microhabitats are instantiated
        this.microhabitats = new Microhabitat[L];
        for(int i = 0; i < L; i++){
            int[] multiPops = new int[nSpecies];
            //double c_i = Math.exp(alpha*(double)i) - 1.;
            double c_i = alpha; //this line is used for constant antibiotic distributions
            microhabitats[i] = new Microhabitat(multiPops, K, c_i);
            microhabitats[i].addSomeRandoms(initialRandPop);
        }

    }

    public int getL(){return L;}
    public int getK(){return K;}
    public int getnSpecies(){return nSpecies;}
    public Microhabitat getMicrohabitat(int i){return microhabitats[i];}
    public double getTimeElapsed(){return timeElapsed;}

    public int getTotalPopulation(){
        int runningTotal = 0;
        for(Microhabitat m: microhabitats){
            runningTotal += m.getN();
        }
        return runningTotal;
    }


    //this method provides the microhabitat index and the multiPop array index
    //used in randomly selecting a bacteria
    public int[] getRandIndexes(int randBacteriaNumber){
        int totalCounter = 0;
        int microhab_index = 0;
        int species_index = 0;

        forloop:
        for(int i = 0; i < L; i++){
            if(totalCounter + microhabitats[i].getN() <= randBacteriaNumber){
                totalCounter += microhabitats[i].getN();
                continue forloop;

            }else{
                microhab_index = i;
                innerforloop:
                for(int j = 0; j < microhabitats[i].getMultiSpecPops().length; j++){
                    if(totalCounter+microhabitats[i].getMultiSpecPops()[j] <= randBacteriaNumber){
                        totalCounter += microhabitats[i].getMultiSpecPops()[j];
                        continue innerforloop;
                    }else{
                        species_index = j;
                        break forloop;
                    }
                }
            }
        }
        return new int[]{microhab_index, species_index};
    }


    public void migrate(int microhab_index, int species_index){
        //handles boundary conditions
        if(microhab_index == 0){
            microhabitats[0].getMultiSpecPops()[species_index]--;
            microhabitats[1].getMultiSpecPops()[species_index]++;

        }else if(microhab_index == L-1){
            microhabitats[L-1].getMultiSpecPops()[species_index]--;
            microhabitats[L-2].getMultiSpecPops()[species_index]++;
        //randomly moves the bacteria forward or backward
        }else{
            if(rand.nextBoolean()){
                microhabitats[microhab_index].getMultiSpecPops()[species_index]--;
                microhabitats[microhab_index+1].getMultiSpecPops()[species_index]++;

            }else{
                microhabitats[microhab_index].getMultiSpecPops()[species_index]--;
                microhabitats[microhab_index-1].getMultiSpecPops()[species_index]++;
            }
        }
    }


    public void death(int mh_index, int sp_index){
        microhabitats[mh_index].getMultiSpecPops()[sp_index]--;
    }

    public void replicate(int mh_index, int sp_index){
        microhabitats[mh_index].getMultiSpecPops()[sp_index]++;
    }


    public void performAction(){

        int N = getTotalPopulation();
        int randBacIndex = rand.nextInt(N);
        int[] indexes = getRandIndexes(randBacIndex);
        int mh_index = indexes[0];
        int species_index = indexes[1];

        Microhabitat rand_mh = microhabitats[mh_index];

        double migrate_rate = rand_mh.getB();
        double death_rate = rand_mh.getD();
        double replication_rate = rand_mh.replicationRate(species_index);

        double R_max = 1.2;
        double rand_chance = rand.nextDouble()*R_max;

        if(rand_chance <= migrate_rate) migrate(mh_index, species_index);
        else if(rand_chance > migrate_rate && rand_chance <= (migrate_rate + death_rate)) death(mh_index, species_index);
        else if(rand_chance > (migrate_rate + death_rate) && rand_chance <= (migrate_rate + death_rate + replication_rate))
        {replicate(mh_index, species_index);}

        timeElapsed += 1./((double)N*R_max);
    }



    public static void multiSpeciesDistribution(double inputAlpha){

        int L = 500, K = 500, nSpecies = 11; //here resistance ranges from 0 to 10
        double duration = 500., interval = 20.;
        //double c = inputAlpha;
        int nReps = 20;
        String filename = "multiSpecies_popDistributions-c="+String.valueOf(inputAlpha)+"-death";

        int[][][] repeatedPopData = new int[nReps][][];


        ///////-STUFF HAPPENS HERE-////////////////
        for(int r = 0; r < nReps; r++){

            boolean alreadyRecorded = false;
            int[][] multiSpeciesPopSizes = new int[L][];
            BioSystem bioSystem = new BioSystem(L, K, nSpecies, inputAlpha);

            //this while loop runs the system until the duration of the experiment has elapsed
            while(bioSystem.timeElapsed <= duration){

                bioSystem.performAction();

                if((bioSystem.getTimeElapsed()%interval >= 0. && bioSystem.getTimeElapsed()%interval <= 0.01) && !alreadyRecorded){
                    System.out.println("rep: "+ String.valueOf(r)+"\ttime elapsed: "+String.valueOf(bioSystem.getTimeElapsed()));
                    alreadyRecorded = true;
                }
                if(bioSystem.getTimeElapsed()%interval >= 0.1 && alreadyRecorded) alreadyRecorded = false;
            }

            //once the run has completed, the no. of each species in each microhab are collected
            for(int i = 0; i < L; i++){

                multiSpeciesPopSizes[i] = bioSystem.microhabitats[i].getMultiSpecPops();
            }
            repeatedPopData[r] = multiSpeciesPopSizes;
        }

        double[][] averagedMultiSpeciesPopSizes =  Toolbox.averagePopulationResults(repeatedPopData);
        Toolbox.writeMultiSpeciesPopSizesToFile(filename, averagedMultiSpeciesPopSizes);

    }





}
