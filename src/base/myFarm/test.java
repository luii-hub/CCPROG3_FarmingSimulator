package base.myFarm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class test {
    public static void main(String [] args) throws Exception{


        BufferedReader br = new BufferedReader(new FileReader("rocks.txt"));

        String line = null;
        int[] intValues = new int[0];
        while((line = br.readLine()) != null){

            String[] values = line.split(",");

            intValues = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                // trying to parse String value as int
                try {
                    // worked, assigning to respective int[] array position
                    intValues[i] = Integer.parseInt(values[i]);
                }
                // didn't work, moving over next String value
                // at that position int will have default value 0
                catch (NumberFormatException nfe) {
                    continue;
                }
                System.out.println();
            }
            System.out.println(Arrays.toString(intValues));

        }
        br.close();

    }
}
