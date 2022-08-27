import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SearchTree {
    public class Instance{
        private MyGraph graph;
        private int k;

        public Instance(MyGraph G,int k){
            this.graph = G;
            this.k = k;
        }

        public Graph getGraph() {
            return graph;
        }

        public int getK() {
            return k;
        }
        public void setK(int k){this.k = k;};
    }
    private boolean solve(Instance i){
        if(i.getK() < 0){
            return false;
        }
        if(i.getGraph().getEdgeCount() == 0){
            return true;
        }
        Instance m = i;
        i.getGraph().deleteVertex(i.getGraph().getAdjazentzlist());
        i.setK(i.getK() - 1);
        if(solve(i)){
            return true;
        }
        m.getGraph().deleteVertex(getRandomHash());
        m.setK(m.getK() - 1);
        if(solve(m)) {
            return true;
        }
        return false;
    }

    public void getRandomHash(HashMap h) {
        List<Integer> valuesList = new ArrayList<Integer>(h.values());
        int randomIndex = new Random().nextInt(valuesList.size());
        Integer randomValue = valuesList.get(randomIndex);
    }
}
