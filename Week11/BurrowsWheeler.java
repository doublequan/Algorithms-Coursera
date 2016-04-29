package week11;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.Arrays;



public class BurrowsWheeler {
    
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String input = BinaryStdIn.readString();
//        String input = "ABRACADABRA!";
        CircularSuffixArray CSA = new CircularSuffixArray(input);
        int first = 0;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (CSA.index(i) == 0) first = i;
            output.append(input.charAt((CSA.index(i) + input.length() - 1) % input.length()));
        }
        //System.out.println(first);
        BinaryStdOut.write(first);
        BinaryStdOut.write(output.toString());
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }
    
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String input = BinaryStdIn.readString();
//        int first = 3;
//        String input = "ARD!RCAAAABB";
        int[] next = new int[input.length()];
        int[] count = new int[256 + 1];
        for (int i = 0; i < input.length(); i++)
            count[input.charAt(i) + 1]++;
        for (int r = 0; r < 256; r++)
            count[r + 1] += count[r];
        for (int i = 0; i < input.length(); i++) 
            next[count[input.charAt(i)]++] = i;
        
        StringBuilder output = new StringBuilder();
        for (int i = next[first]; i != first; i = next[i]) {
            output.append(input.charAt(i));
        }
        output.append(input.charAt(first));
        
        BinaryStdOut.write(output.toString());
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}