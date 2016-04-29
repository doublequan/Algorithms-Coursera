package week11;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++) ascii[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char input = BinaryStdIn.readChar();
            char index = 0, newchar = input;
            while (ascii[index] != input) {
                char tmp = ascii[index];
                ascii[index] = newchar;
                newchar = tmp;
                index++;
            }
            ascii[index] = newchar;
            BinaryStdOut.write(index);
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++) ascii[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char input = BinaryStdIn.readChar();
            BinaryStdOut.write(ascii[input]);
            char target = ascii[input];
            for (int i = 1; i <= input; i++) {
                ascii[i] = ascii[i - 1];
            }
            ascii[0] = target;
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}