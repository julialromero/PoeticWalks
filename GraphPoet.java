package assignment3;/* EE422C Assignment #3 submission by
 * Julia Romero
 * JLR5576
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GraphPoet {
    private Graph<String> graph = new Graph<>();

    /**
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    // Constructor of a weighted graph
    public GraphPoet(File corpus) throws IOException {
        buildGraph(corpus);
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {
        buildGraph(input);

        /* Read in input and use graph to complete poem */
        StringBuilder poem = new StringBuilder();
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);
        String line;
        Vertex<String> lastinToken = null;
        boolean punctuation = false;
        while ((line = br.readLine()) != null) {
            List<String> preservedTokens = Arrays.asList(
                    line.replaceAll("[^a-zA-Z.!? ]", "").split(" "));
            List<String> inTokens = Arrays.asList(
                    line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split(" "));

            // no bridge words needed to start sentence if the previous line ended with punctuation
            if (punctuation) lastinToken = null;

            // Retrieve bridge words that span across lines
            if (lastinToken != null) {
                String rootWord = inTokens.get(0).toLowerCase();
                List<Vertex<String>> bridges = new ArrayList<>();
                graph.findBridges(lastinToken, new HashSet<>(), 1, rootWord, bridges);
                List<Vertex<String>> myBridges = filterBridges(bridges, lastinToken);

                for (Vertex<String> x : myBridges) {
                    poem.append(x.value);
                    poem.append(" ");
                }
            }
            for (int i = 0; i < inTokens.size() - 1; i++) {
                String rootWord = inTokens.get(i).toLowerCase();
                String nextWord = inTokens.get(i + 1).toLowerCase();
                List<Vertex<String>> bridges = new ArrayList<>();
                Vertex<String> rootVert = graph.vertices.get(rootWord);
                if (rootVert != null) {
                    graph.findBridges(rootVert, new HashSet<>(), 1, nextWord, bridges);
                }
                List<Vertex<String>> myBridges = filterBridges(bridges, rootVert);

                // make string builder and insert root and then the bridge word
                if (i == 0) poem.append(preservedTokens.get(0) + " ");
                if (i != 0) poem.append(rootWord + " ");
                for (Vertex<String> x : myBridges) {
                    poem.append(x.value + " ");
                }
            }
            int last = inTokens.size() - 1;
            lastinToken = graph.fetchAndAddVertex(inTokens.get(last));
            poem.append(preservedTokens.get(inTokens.size() - 1) + "\n");
            int n = poem.length() - 1;
            char t = poem.charAt(n);
            if ((t == '.') || (t == '!') || (t == '?')) punctuation = true;
        }

        return poem.toString();
    }

    public List<Vertex<String>> filterBridges(List<Vertex<String>> bridges, Vertex<String> rootVert) {
        int maxWeight = 1;
        int weight = 1;
        // finds the max weight of the bridge words
        for (Vertex<String> x : bridges) {
            String y = x.value;
            weight = rootVert.edges.get(x.value);
            if (weight > maxWeight) maxWeight = weight;
        }
        // collects bridge words with the max weight
        List<Vertex<String>> myBridges = new ArrayList<>();
        for (Vertex<String> x : bridges) {
            weight = rootVert.edges.get(x.value);
            if (weight == maxWeight) myBridges.add(x);
        }
        return myBridges;
    }


    public void buildGraph(File corpus) throws IOException {
        // Read input file
        FileReader fr = new FileReader(corpus);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String lastToken = null;
        // Iterate thru lines in corpus
        while ((line = br.readLine()) != null) {
            List<String> tokens = Arrays.asList(
                    line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split(" "));
            if (lastToken != null) {
                graph.fetchAndAddVertex(lastToken).incrementWeight(tokens.get(0));
                Vertex<String> nextVertex = graph.fetchAndAddVertex(tokens.get(0));
                nextVertex.inEdges.add(lastToken);
            }

            for (int i = 0; i < tokens.size(); i++) {
                String curWord = tokens.get(i);
                Vertex<String> curVertex = graph.fetchAndAddVertex(curWord);

                if (i != tokens.size() - 1) {
                    String nextWord = tokens.get(i + 1);
                    curVertex.incrementWeight(nextWord);
                    Vertex<String> nextVertex = graph.fetchAndAddVertex(nextWord);
                    nextVertex.inEdges.add(curWord);
                } else {
                    lastToken = curWord;
                }
            }
        }
    }
}
