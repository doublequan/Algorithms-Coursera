/*
 *  the programming assignment of Algorithms, Part II 
 *  Week1 Part II
 *  author: Bill Quan  
 *  Last edited: 20160404
 *  BaseballElimination.java
 */
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class BaseballElimination {
    private Map<String, Integer> teams;
    private String[] teamName;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;

    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        teams = new HashMap<>(N);
        teamName = new String[N];
        w = new int[N];
        l = new int[N];
        r = new int[N];
        g = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            String name = in.readString();
            teams.put(name, i);
            teamName[i] = name;
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < N; j++) g[i][j] = in.readInt();
        }
        
        
        
    }           
    
    // number of teams
    public int numberOfTeams() {
        return w.length;
    }               
    
    // all teams
    public Iterable<String> teams() {
        List<String> rst = new LinkedList<>();
        rst.addAll(teams.keySet());
        return rst;
    }    
    
    // number of wins for given team
    public int wins(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("in wins()"); 
        return w[teams.get(team)];
    }                      
    
    // number of losses for given team
    public int losses(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("in losses()"); 
        return l[teams.get(team)];
    }
    
    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("in remaining()"); 
        return r[teams.get(team)];
    }
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.containsKey(team1)) throw new IllegalArgumentException("team1 illegal in against()"); 
        if (!teams.containsKey(team2)) throw new IllegalArgumentException("team2 illegal in against()"); 
        return g[teams.get(team1)][teams.get(team2)];
    }    
    
    // is given team eliminated?
    public boolean isEliminated(String team) {              
        if (!teams.containsKey(team)) throw new IllegalArgumentException("in isEliminated()"); 
        
        int index = teams.get(team);
        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (w[index] + r[index] < w[i]) return true;
        }
        
        FlowNetwork fn = buildFN(index);
        new FordFulkerson(fn, 0, fn.V() - 1);
        //System.out.println(fn);
        for (FlowEdge edge : fn.adj(0)) {
            if (edge.flow() != edge.capacity()) return true;
        }
        return false;
    }
    
    private FlowNetwork buildFN(int index) {
        
        int n = 2 + (this.numberOfTeams() * (this.numberOfTeams() - 1)) / 2;
        FlowNetwork fn = new FlowNetwork(n);
        
        int count = 1;
        for (int i = 0; i < this.numberOfTeams() - 1; i++) {
            if (i == index) continue;
            for (int j = i + 1; j < this.numberOfTeams(); j++) {
                if (j == index) continue;    
                fn.addEdge(new FlowEdge(0, count, g[i][j]));
                fn.addEdge(new FlowEdge(count, n - this.numberOfTeams() + i - (i > index ? 1 : 0), Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(count, n - this.numberOfTeams() + j - (j > index ? 1 : 0), Double.POSITIVE_INFINITY));
                count++;
            }
        }
        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (i != index) {
                //System.out.println(w[index] + r[index] - w[i]);
                fn.addEdge(new FlowEdge(count, n - 1, w[index] + r[index] - w[i] >= 0 ? w[index] + r[index] - w[i] : Double.POSITIVE_INFINITY));
                count++;
            }
        }
        return fn;
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("in isEliminated()"); 
        List<String> rst = new LinkedList<>();
        boolean isEliminated = false;
        int index = teams.get(team);
        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (w[index] + r[index] < w[i]) {
                isEliminated = true;
                rst.add(teamName[i]);
            }
        }

        FlowNetwork fn = buildFN(index);
        FordFulkerson f = new FordFulkerson(fn, 0, fn.V() - 1);
        for (FlowEdge edge : fn.adj(0)) {
            if (edge.flow() != edge.capacity()) isEliminated = true;
        }
        if (!isEliminated) return null;
        //System.out.println(fn);
        int n = 2 + (this.numberOfTeams() * (this.numberOfTeams() - 1)) / 2 - this.numberOfTeams();
        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (i != index && f.inCut(n + i - (i > index ? 1 : 0))) rst.add(teamName[i]);
        }
        return rst;
    }
    
}
