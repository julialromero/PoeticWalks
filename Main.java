package assignment3;
/* EE422C Assignment #3 submission by
 * Julia Romero
 * JLR5576
 */


import assignment3.GraphPoet;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * Example program using assignment3.GraphPoet.
     */


        /**
         * Generate example poetry.
         *
         * @param args unused
         * @throws IOException if a poet corpus file cannot be found or read
         */
        public static void main(String[] args) throws IOException {
            final GraphPoet nimoy = new GraphPoet(new File("src/assignment3/corpus.txt"));
            System.out.println(nimoy.poem(new File("src/assignment3/input.txt")));
        }
}
