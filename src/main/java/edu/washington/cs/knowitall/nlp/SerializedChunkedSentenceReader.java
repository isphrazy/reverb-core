package edu.washington.cs.knowitall.nlp;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.Iterator;

import com.google.common.base.Predicate;


import edu.washington.cs.knowitall.extractor.SentenceExtractor;
import edu.washington.cs.knowitall.io.BufferedReaderIterator;
import edu.washington.cs.knowitall.io.TextBlockIterator;
import edu.washington.cs.knowitall.util.IterableAdapter;

/**
 * A class that combines a directly reads chunked sentences from an input stream--one per line.
 * @author schmmd
 *
 */
public class SerializedChunkedSentenceReader implements Iterable<ChunkedSentence> {

    BufferedReaderIterator bri = null;

    /**
     * Constructs a reader from <code>r</code>.
     * @param r
     * @param se
     * @throws IOException
     */
    public SerializedChunkedSentenceReader(BufferedReader r) throws IOException {
        bri = new BufferedReaderIterator(r);
    }
    
    /**
     * @return an iterator over the sentences from the <code>BufferedReader</code> given during construction.
     */
    public Iterator<ChunkedSentence> iterator() {
        return new Iterator<ChunkedSentence>() {
           public boolean hasNext() {
               return bri.hasNext();
           }

           public ChunkedSentence next() {
               String line = bri.next();
               String[] parts = line.split("\t");
               return new ChunkedSentence(parts[0].split("\\s+"), parts[1].split("\\s+"), parts[2].split("\\s+"));
           }

           public void remove() {
               throw new UnsupportedOperationException();
           }
        };
    }

    /**
     * @return an iterable object over the sentences from the <code>BufferedReader</code> given during construction.
     */
    public Iterable<ChunkedSentence> getSentences() {
        return new IterableAdapter<ChunkedSentence>(this.iterator());
    }
}
