package maxflowmincut;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.LinkedList;

public class MaxFlowMinCut {

    static final int vertices = 6; // Number of vertices

    static LinkedList<Integer> MinimumCut = new LinkedList<>(); // LinkedList for Minimum Cut

    boolean bfs(int residualGraph[][], int s, int t, int parent[]) {
        boolean visited[] = new boolean[vertices];
        for (int i = 0; i < vertices; ++i) {
            visited[i] = false;
        }
        LinkedList<Integer> queue = new LinkedList<Integer>(); // LinkedList for vertices stocking
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < vertices; v++) {
                if (visited[v] == false && residualGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return (visited[t] == true);
    }

    int FordFulkerson(int graph[][], int s, int t) {
        int u, v;
        int residualGraph[][] = new int[vertices][vertices];

        for (u = 0; u < vertices; u++) {
            for (v = 0; v < vertices; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }
        int parent[] = new int[vertices];

        //Each bath stores the highest value it can hold
        int max_flow = 0;
        while (bfs(residualGraph, s, t, parent)) {
            int path_flow = Integer.MAX_VALUE;
            int min = Integer.MAX_VALUE;
            LinkedList<Integer> path = new LinkedList<>(); // LinkedList For Bath

            path.add(t);
            int a = Integer.MAX_VALUE;
            int b = Integer.MAX_VALUE;
            int c = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path.add(u);
                if (residualGraph[u][v] < min) {
                    min = residualGraph[u][v];
                    a = u;
                    b = v;
                    c = residualGraph[u][v];
                }

                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }

            MinimumCut.add(a);
            MinimumCut.add(b);
            MinimumCut.add(c);
            Iterator<Integer> itr = path.descendingIterator();
            System.out.println("The augment path of the Residual Graph is:");
            
            
//Chooses the FRTSES, determines the bath, and calculates the excess for each bath according to the capacity
            String path1 = itr.next().toString(1);
            System.out.print(path1);
            while (itr.hasNext()) {
                String path2 = itr.next().toString();
                int path2Int = Integer.parseInt(path2) + 1;
                System.out.print("---->" + path2Int);
            }

            System.out.println();
            System.out.println("The augment flow of the above path is : " + path_flow);
            System.out.println();
            System.out.println("______________________________________________________");
            System.out.println();
            
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }
            max_flow += path_flow;
        }
        return max_flow;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner inputfile = new Scanner(new File("input.txt"));
        
        // Pass on all the elements inside arry 
        // 6 = Number of Fertces
        int[][] graph = new int[6][6]; // array graph
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                graph[i][j] = 0;
            }
        }

        // Pass on all the elements inside input file 
        // 8 = The number of lines
        // The first column is the first vertex, The second column is the vertex destined for it, The third column is the Bath value
        for (int i = 0; i < 8; i++) {
            int source = inputfile.nextInt(); // vertex
            int destination = inputfile.nextInt();// vertex destined for it
            int length = inputfile.nextInt(); // Bath value
            graph[source][destination] = length; // We store the values ​​in array graph
        }

        MaxFlowMinCut maxflow = new MaxFlowMinCut();
        System.out.println("MAXIMUM FLOW :");
        System.out.println();
        System.out.println("The Maximum possible Flow is " + maxflow.FordFulkerson(graph, 0, 5));

        System.out.println();
        System.out.println("MINIMUM CUT");
        System.out.println("The Minimum Cut Edges are :");
        Iterator<Integer> itrcut = MinimumCut.iterator();
        
        int cutsum=0;
        while(itrcut.hasNext()){
                    
                    int source=itrcut.next();
                    int destination=itrcut.next();
			
                    if (graph[source][destination]==0){ //no forward edge
                        cutsum= cutsum + itrcut.next();
                        continue;
                    }
		
                    System.out.println((source+1)+"-----"+(destination+1));
              
                    cutsum= cutsum + itrcut.next();  
                    
		}
        System.out.println("The Capacity of the Minimum Cut is :" + cutsum);

    }
}
