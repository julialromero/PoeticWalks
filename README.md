# PoeticWalks
I implement an N-gram model to complete sentences with bridge words.

## N-gram Construction
The graph reads in corpus.txt and creates a graph with words as vertices. Feel free to write your own corpus.txt input to test it out. Words that are subsequent multiple times will have edges with weights that increment with each occurence.

## N-gram Traversal
The graph takes input.txt and searches the graph to find single or multiple bridge words between the input words. It adds these words and adds new words from input.txt to the graph.
