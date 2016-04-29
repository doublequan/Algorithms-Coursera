package week11;

public class CircularSuffixArray {
    private int[] index;
    private final int len;
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        len = s.length();
        index = new int[len];
        for (int i = 0; i < len; i++) index[i] = i;
        sort(index, s);
    } 
    
    private void sort(int[] index, String s) {
        int R = 256;
        int N = index.length;
        int[] aux = new int[N];
        
        for (int d = s.length() - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++) 
                count[getChar(s, index[i], d) + 1]++;
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            for (int i = 0; i < N; i++)
                aux[count[getChar(s, index[i], d)]++] = index[i];
            System.arraycopy(aux, 0, index, 0, N);
        }
    }
    
    private char getChar(String s, int i, int d) {
        return s.charAt((i + d) % s.length());
    }
    
    // length of s
    public int length() {
        return len;
    }             
    
    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= len) throw new IndexOutOfBoundsException();
        return index[i];
    }               
            
    // unit testing of the methods (optional)        
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray test = new CircularSuffixArray(s);
        System.out.println(test.index(11));
    }
}