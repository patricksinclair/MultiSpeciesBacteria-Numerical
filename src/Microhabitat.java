import java.util.Random;

public class Microhabitat {

    Random rand = new Random();
    //this is an array that keeps track of the number of each species of bacteria
    private int[] multiSpecPops;
    private double c;
    private int K;

    //death and migration rates
    private double d = 0.1, b = 0.1;

    public Microhabitat(int[] multiSpecPops, int K, double c){
        this.multiSpecPops = multiSpecPops;
        this.c = c;
        this.K = K;
    }

    public int[] getMultiSpecPops(){return multiSpecPops;}
    public int getK(){return K;}
    public double getC(){return c;}
    public double getD(){return d;}
    public double getB(){return b;}


    public double getN(){
        int runningTotal = 0;
        for(int i : multiSpecPops){
            runningTotal += i;
        }
        return runningTotal;
    }

    public double beta(int popIndex){
        return (double)popIndex;
    }

    public double phi_c(int popIndex){
        double phi_c = 1. - (c/beta(popIndex))*(c/beta(popIndex));
        return  (phi_c > 0.) ? phi_c : 0.;
    }

    //this growth rate is the same as the PRL paper, which relies on a Karrying Kapacity rather than nutrients
    public double replicationRate(int popIndex){
        double gRate = phi_c(popIndex)*(1. - getN()/K);
        return  (gRate > 0.) ? gRate : 0.;
    }


    //this adds a specified number of random bacteria to the microhabitat
    //they have a genotype randomly chosen, then the number of that kind of bacteria is increased in the
    //multiSpecPops array
    public void addSomeRandoms(int initialRandPopSize){

        for(int i = 0; i < initialRandPopSize; i++){
            //a new species is chosen, and it's added to the tally.
            multiSpecPops[rand.nextInt(multiSpecPops.length)]++;
        }

    }













}
