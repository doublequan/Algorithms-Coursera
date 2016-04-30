

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
    
    private void sort(int[] indices, String s) {
        sort(indices, s, 0, indices.length - 1, 0);
//        int R = 256;
//        int N = indices.length;
//        int[] aux = new int[N];
//        
//        for (int d = s.length() - 1; d >= 0; d--) {
//            int[] count = new int[R + 1];
//            for (int i = 0; i < N; i++) 
//                count[getChar(s, indices[i], d) + 1]++;
//            for (int r = 0; r < R; r++)
//                count[r + 1] += count[r];
//            for (int i = 0; i < N; i++)
//                aux[count[getChar(s, indices[i], d)]++] = indices[i];
//            System.arraycopy(aux, 0, indices, 0, N);
//        }
    }
    
    private void sort(int[] indices, String s, int lo, int hi, int d) {
        if (hi <= lo || d > s.length() - 1) return;
        int lt = lo, gt = hi;
        int v = charAt(indices[lo], s, d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(indices[i], s, d);
            if (t < v) exch(indices, lt++, i++);
            else if (t > v) exch(indices, i, gt--);
            else i++;
        }
        sort(indices, s, lo, lt - 1, d);
        sort(indices, s, lt, gt, d + 1);
        sort(indices, s, gt + 1, hi, d);
    }
    
    private char charAt(int i, String s, int d) {
        return s.charAt((i + d) % s.length());
    }
    
    private void exch(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
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