import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MyGraph implements Graph {

    private HashMap<Integer, Set<Integer>> adjazenzlist = new HashMap<>();
    private HashMap<String, Integer> IDlist = new HashMap<>();
    private int edges = 0;

    public MyGraph(String filename) throws IOException {
        int j = 0;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String[] str = readAllLines(reader).split("\\s+");
        for(int i = 0; i < str.length; i+=2){
            if(!IDlist.containsKey(str [i])){
                IDlist.put(str[i],j);
                addVertex(IDlist.get(str[i]));
                j++;
            }
            if(!IDlist.containsKey(str [i+1])){
                IDlist.put(str[i+1],j);
                addVertex(IDlist.get(str[i+1]));
                j++;
            }
            addEdge(IDlist.get(str[i]),IDlist.get(str[i+1]));
        }
    }

    public static void main (String [] args) throws IOException {
        MyGraph c = new MyGraph("Texte/bio-dmelamtx.sec");
        System.out.println(c.getEdgeCount());
        c.deleteVertex(c.IDlist.get("3"));
        System.out.println(c.getEdgeCount());
    }

    public HashMap<Integer, Set<Integer>> getAdjazenzlist() {
        return adjazenzlist;
    }

    public HashMap<String, Integer> getIDlist() {
        return IDlist;
    }

    public MyGraph() {
        this.adjazenzlist = new HashMap<>();
    }

    public String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        return content.toString();
    }

    @Override
    public void addVertex(Integer v) {
        Set<Integer> m = new HashSet<>();
        adjazenzlist.put(v, m);
    }

    @Override
    public void addEdge(Integer v, Integer w) {
        if(v == w){return;}
        if (adjazenzlist.containsKey(v) && adjazenzlist.containsKey(w)) {
            adjazenzlist.get(v).add(w);
            adjazenzlist.get(w).add(v);
            edges += 1;
        } else {
            System.out.println("One of the passed nodes is not in the adjacency list.");
        }
    }

    @Override
    public void deleteVertex(Integer v) {
        if (adjazenzlist.containsKey(v)) {
            edges -= adjazenzlist.get(v).size();
            for (Integer vertex : adjazenzlist.get(v)) {
                adjazenzlist.get(vertex).remove(v);
            }
            adjazenzlist.remove(v);
        } else {
            System.out.println("The key is not in the adjacency list and therefore cannot be deleted.");
        }
    }

    @Override
    public void deleteEdge(Integer u, Integer v) {
        if (adjazenzlist.containsKey(u)) {
            if (adjazenzlist.get(u).contains(v)) {
                adjazenzlist.get(u).remove(v);
                adjazenzlist.get(v).remove(u);
                edges -= 1;
            }
        } else {
            System.out.println("The key is not included in the adjacency list.");
        }
    }

    @Override
    public boolean contains(Integer v) {
        return adjazenzlist.containsKey(v);
    }

    @Override
    public int degree(Integer v) {
        return adjazenzlist.get(v).size();
    }

    @Override
    public boolean adjacent(Integer v, Integer w) {
        return adjazenzlist.get(v).contains(w);
    }

    @Override
    public Graph getCopy() {
        Graph graphCopy = new MyGraph();

        for (Map.Entry<Integer, Set<Integer>> entry : adjazenzlist.entrySet()) {
            graphCopy.addVertex(entry.getKey());
            for (Integer neighbor : entry.getValue()) {
                graphCopy.addEdge(entry.getKey(), neighbor);
            }
        }
        return graphCopy;
    }

    @Override
    public Set<Integer> getNeighbors(Integer v) {
        return this.adjazenzlist.get(v);
    }

    @Override
    public int size() {
        return adjazenzlist.size();
    }

    @Override
    public int getEdgeCount() {
        return edges;
    }

    @Override
    public Set<Integer> getVertices() {
        return adjazenzlist.keySet();
    }
}