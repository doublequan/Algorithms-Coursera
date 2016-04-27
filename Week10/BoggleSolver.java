/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Administrator
 */
public class BoggleSolver {
    private final TrieNode root;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        root = new TrieNode();
        for (String s : dictionary) {
            TrieNode tn = root;
            for (int i = 0; i < s.length(); i++) {
                int index = s.charAt(i) - 'A';
                if (tn.next[index] == null) {
                    tn.next[index] = new TrieNode();
                }
                tn = tn.next[index];
            }
            tn.value = s.length();
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> set = new HashSet<>();
        int row = board.rows();
        int col = board.cols();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                dfs(board, r, c, new boolean[row][col], new StringBuilder(), set);
            }
        }
        return set;
    }
    
    // do dfs in the board
    private void dfs(BoggleBoard board, int r, int c, boolean[][] marked, StringBuilder path, Set<String> rst) {
        
        marked[r][c] = true;
        path.append(board.getLetter(r, c));
        if (board.getLetter(r, c) == 'Q') path.append('U');
        if (path.length() > 2 && containsWord(path.toString())) {
            rst.add(path.toString());
        }
        
        if (containsPrefix(path.toString())) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (r + i >= 0 && r + i < marked.length
                     && c + j >= 0 && c + j < marked[0].length
                     && !marked[r + i][c + j]) {

                        dfs(board, r + i, c + j, marked, path, rst);

                    }
                }
            }
        }
        path.deleteCharAt(path.length() - 1);
        if (board.getLetter(r, c) == 'Q') path.deleteCharAt(path.length() - 1);
        marked[r][c] = false;
    }
    
    // Returns true if there exists words starts with the input String prefix in the dictionary
    private boolean containsPrefix(String prefix) {
        TrieNode tn = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'A';
            if (tn.next[index] == null) return false;
            else tn = tn.next[index];
        }
        return true;
    }
    
        // Returns true if there exists words starts with the input String prefix in the dictionary
    private boolean containsWord(String word) {
        TrieNode tn = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'A';
            if (tn.next[index] == null) return false;
            else tn = tn.next[index];
        }
        return tn.value != 0;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!containsWord(word) || word.length() <= 2) return 0;
        else if (word.length() <= 4) return 1;
        else if (word.length() == 7) return 5;
        else if (word.length() >= 8) return 11;
        else return word.length() - 3;
    }
    
    private class TrieNode {
        int value;
        TrieNode next[] = new TrieNode[26];
    }
    
    public static void main(String args[]) {
        String dir = "E:\\coursera\\WorkStation\\Netbeans\\src\\week10\\boggle\\";
        String dictDir = dir + "dictionary-yawl.txt";
        String boardDir = dir + "board-points26539.txt";
        In in = new In(dictDir);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(boardDir);
        System.out.println(board);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
    
}
