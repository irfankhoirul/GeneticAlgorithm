import util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irfan Khoirul on 6/28/2016.
 */
public class Test {

    public static char[] target = {'S', 'E', 'L', 'A', 'M', 'A', 'T', 'H', 'A', 'R', 'I', 'R', 'A', 'Y', 'A'};
    public static int targetFitness = 26 * target.length;
    public static int populasiAwal = 20;
    public static List<Character[]> listIndividu = new ArrayList<>();
    public static List<Character[]> listBestIndividu = new ArrayList<>();
    public static Character[] newIndividu;
    public static boolean ketemu = false;

    public static void main(String args[]) {
        //Populasi awal
        System.out.println("Populasi Awal :");
        for (int i = 0; i < populasiAwal; i++) {
            Character[] individu = new Character[target.length];
            for (int j = 0; j < target.length; j++) {
                int gen = Util.randInt(65, 90); //Mendapatkan ascii
                char ch = (char) gen;
                individu[j] = ch;
                System.out.print(ch + " ");
            }

            getFitness(individu);
            listIndividu.add(individu);

            System.out.print(" FITNESS = " + getFitness(individu));
            System.out.print("\n");
        }

        System.out.println("Individu Baru :");
        int iterasi = 0;
        while (!ketemu) {
            iterasi++;
            listBestIndividu.clear();
            getBestIndividu(listIndividu);

            //CrossOver
            newIndividu = doCrossover(listBestIndividu);
            boolean cekHasilCrossOver = checkKetemu(newIndividu);
            if (cekHasilCrossOver) {
                break;
            }

            //Mutasi
            newIndividu = doMutasi(listBestIndividu.get(0));
            boolean cekHasilMutasi = checkKetemu(newIndividu);
            if (cekHasilMutasi) {
                break;
            }

            for (int i = 0; i < newIndividu.length; i++) {
                System.out.print(newIndividu[i] + " ");
            }
            System.out.print(" FITNESS = " + getFitness(newIndividu));
            System.out.print("\n");

        }

        for (int i = 0; i < newIndividu.length; i++) {
            System.out.print(newIndividu[i] + " ");
        }
        System.out.print("\n");

        System.out.println("YES!! KETEMU!!\nTotal Iterasi = " + iterasi);

    }

    private static int getFitness(Character[] individu) {
        int fitness = 0;
        for (int j = 0; j < individu.length; j++) {
            fitness += Math.abs(individu[j] - target[j]);
        }
        fitness = ((26 * target.length) - fitness);
        return fitness;
    }

    private static Character[] getBestIndividu(List<Character[]> listIndividu) {
        int index = 0;
        for (int i = 0; i < listIndividu.size(); i++) {
            if (getFitness(listIndividu.get(i)) >= getFitness(listIndividu.get(index))) {
                index = i;
            }
        }
        listBestIndividu.add(listIndividu.get(index));
//        System.out.println("Best Individu" + index);

        int index2 = 0;
        for (int i = 0; i < listIndividu.size(); i++) {
            if (i == index) {
                continue;
            }
            if (getFitness(listIndividu.get(i)) >= getFitness(listIndividu.get(index2))) {
                index2 = i;
            }
        }
        listBestIndividu.add(listIndividu.get(index2));
//        System.out.println("Best Individu" + index);

        return listIndividu.get(index);
    }

    private static Character[] doCrossover(List<Character[]> listIndividuTerpilih) {
//        System.out.println("CrossOver:BestIndividu1Fitness" + getFitness(listIndividuTerpilih.get(0)));
//        System.out.println("CrossOver:BestIndividu2Fitness" + getFitness(listIndividuTerpilih.get(1)));

        int indexCrossOver1 = Util.randInt(0, target.length - 1);
//        System.out.println("CrossOver:Index1 = " + indexCrossOver1);
        int indexCrossOver2 = Util.randInt(0, target.length - 1);
//        System.out.println("CrossOver:Index2 = " + indexCrossOver2);

        Character[] newIndividu1 = new Character[target.length];
        for (int i = 0; i < target.length; i++) {
            newIndividu1[i] = listIndividuTerpilih.get(0)[i];
        }
        newIndividu1[indexCrossOver1] = listIndividuTerpilih.get(1)[indexCrossOver1];
        newIndividu1[indexCrossOver2] = listIndividuTerpilih.get(1)[indexCrossOver2];
//        System.out.println("CrossOver:NewIndividu1Fitness" + getFitness(newIndividu1));

        Character[] newIndividu2 = new Character[target.length];
        for (int i = 0; i < target.length; i++) {
            newIndividu2[i] = listIndividuTerpilih.get(1)[i];
        }
        newIndividu2[indexCrossOver1] = listIndividuTerpilih.get(0)[indexCrossOver1];
        newIndividu2[indexCrossOver2] = listIndividuTerpilih.get(0)[indexCrossOver2];
//        System.out.println("CrossOver:NewIndividu2Fitness" + getFitness(newIndividu2));

        int fitnessAwal1 = getFitness(listIndividuTerpilih.get(0));
        int fitnessAwal2 = getFitness(listIndividuTerpilih.get(1));
        int fitnessAkhir1 = getFitness(newIndividu1);
        int fitnessAkhir2 = getFitness(newIndividu2);

        int maxDataAwal = Math.max(fitnessAwal1, fitnessAwal2);
        int maxDataAkhir = Math.max(fitnessAkhir1, fitnessAkhir2);

        int max = Math.max(maxDataAwal, maxDataAkhir);
//        System.out.println("CrossOver:Max = " + max);

        listIndividu.clear();

        if (max == fitnessAwal1) {
//            System.out.println("CrossOver:ReturnFitness" + getFitness(listIndividuTerpilih.get(0)));
            listIndividu.add(listIndividuTerpilih.get(0));
            listIndividu.add(listIndividuTerpilih.get(1));
            return listIndividuTerpilih.get(0);
        } else if (max == fitnessAwal2) {
//            System.out.println("CrossOver:ReturnFitness" + getFitness(listIndividuTerpilih.get(1)));
            listIndividu.add(listIndividuTerpilih.get(1));
            listIndividu.add(listIndividuTerpilih.get(0));
            return listIndividuTerpilih.get(1);
        } else if (max == fitnessAkhir1) {
//            System.out.println("CrossOver:ReturnFitness" + getFitness(newIndividu1));
            listIndividu.add(newIndividu1);
            listIndividu.add(newIndividu2);
            return newIndividu1;
        } else {
//            System.out.println("CrossOver:ReturnFitness" + getFitness(newIndividu2));
            listIndividu.add(newIndividu2);
            listIndividu.add(newIndividu1);
            return newIndividu2;
        }

    }

    private static Character[] doMutasi(Character[] individuTerpilih) {
        Character[] tmpIndividuTerpilih = new Character[target.length];
        for (int i = 0; i < target.length; i++) {
            tmpIndividuTerpilih[i] = individuTerpilih[i];
        }
        int fitnessAwal = getFitness(tmpIndividuTerpilih);
//        System.out.println("Mutasi:FitnessAwal-->" + fitnessAwal);

        int mutationIndex = Util.randInt(0, target.length - 1);
        int mutationValue = Util.randInt(65, 90);
        char ch = (char) mutationValue;
        individuTerpilih[mutationIndex] = ch;

        int fitnessAkhir = getFitness(individuTerpilih);
//        System.out.println("Mutasi:FitnessAkhir-->" + fitnessAkhir);

        listIndividu.clear();

        if (fitnessAkhir >= fitnessAwal) {
//            System.out.println("Mutasi:ReturnFitness" + getFitness(individuTerpilih));
            listIndividu.add(individuTerpilih);
            return individuTerpilih;
        } else {
//            System.out.println("Mutasi:ReturnFitness" + getFitness(tmpIndividuTerpilih));
            listIndividu.add(tmpIndividuTerpilih);
            return tmpIndividuTerpilih;
        }

    }

    private static boolean checkKetemu(Character[] individu) {
        if (getFitness(individu) == targetFitness) {
            ketemu = true;
        } else {
            ketemu = false;
        }
        return ketemu;
    }

}
