import edu.princeton.cs.introcs.In;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// A Tree node
class Node
{
    Character ch;
    Integer freq;
    Node left = null, right = null;

    Node(Character ch, Integer freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(Character ch, Integer freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

class mazeCompression {


    // Traverse the Huffman Tree and store Huffman Codes in a map.
    public static void encode(Node root, String str,
                              Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }

        // Found a leaf node
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }

        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }

    // Traverse the Huffman Tree and decode the encoded string
    public static int decode(Node root, int index, StringBuilder sb) {
        if (root == null) {
            return index;
        }

        // Found a leaf node
        if (isLeaf(root)) {
            System.out.print(root.ch);
            return index;
        }

        index++;

        root = (sb.charAt(index) == '0') ? root.left : root.right;
        index = decode(root, index, sb);
        return index;
    }

    // Utility function to check if Huffman Tree contains only a single node
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    // Builds Huffman Tree and decodes the given input text
    public static void buildHuffmanTree(String text) {
        // Base case: empty string
        if (text == null || text.length() == 0) {
            return;
        }

        // Count the frequency of appearance of each character
        // and store it in a map

        Map<Character, Integer> freqCharAmountMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqCharAmountMap.put(c, freqCharAmountMap.getOrDefault(c, 0) + 1);
        }

        // create a priority queue to store live nodes of the Huffman tree.
        // Notice that the highest priority item has the lowest frequency

        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        // create a leaf node for each character and add it
        // to the priority queue.

        for (var entry : freqCharAmountMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // do till there is more than one node in the queue
        while (pq.size() != 1) {
            // Remove the two nodes of the highest priority
            // (the lowest frequency) from the queue

            Node left = pq.poll();
            Node right = pq.poll();

            // create a new internal node with these two nodes as children
            // and with a frequency equal to the sum of both nodes'
            // frequencies. Add the new node to the priority queue.

            if (left.freq != null && right.freq != null) {
                int sum = left.freq + right.freq;
                pq.add(new Node(null, sum, left, right));
            }
        }

        // `root` stores pointer to the root of Huffman Tree
        Node root = pq.peek();

        // Traverse the Huffman tree and store the Huffman codes in a map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // Print the Huffman codes
        System.out.println("Huffman Codes are: " + huffmanCode);
        System.out.println("The original string is: " + text);

        // Print encoded string
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        System.out.println("The encoded string is: " + sb);
        System.out.print("The decoded string is: ");


        if (isLeaf(root)) {
            // Special case: For input like a, aa, aaa, etc.
            while (root.freq-- > 0) {
                System.out.print(root.ch);
            }
        } else {
            // Traverse the Huffman Tree again and this time,
            // decode the encoded string
            int index = -1;
            while (index < sb.length() - 1) {
                index = decode(root, index, sb);
            }
        }
    }

    private String compToStr(Compressor comp) {

        final StringBuilder sb = new StringBuilder();

        sb.append(comp.entranceX);
        sb.append(",");
        sb.append(comp.entranceY);
        sb.append("-");
        sb.append(comp.exitX);
        sb.append(",");
        sb.append(comp.exitY);
        sb.append("-");
        sb.append(comp.size);
        sb.append("-");
        for (int i=0; i<comp.structure[0].length; i++) {
            for (int j = 0; j < comp.structure[1].length; j++) {
                if (comp.structure[i][j])
                    sb.append(1);
                else
                    sb.append(0);
            }
        }

        return sb.toString();
    }

    public Compressor strToComp(String str) {

        String[] splittedStr = str.split("-");

        int size = Integer.parseInt(splittedStr[2]);

        boolean [][] structure = new boolean[size][size];

        int structureIdx = 0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                structure[i][j] = splittedStr[3].charAt(structureIdx) == '1';
                structureIdx++;
            }
        }

        return new Compressor(new Point(Integer.parseInt(splittedStr[0].split(",")[0]),Integer.parseInt(splittedStr[0].split(",")[1])),
                new Point(Integer.parseInt(splittedStr[1].split(",")[0]), Integer.parseInt(splittedStr[1].split(",")[1])),
                size,structure);
    }





    public void encodeHuffman(Compressor comp) {
        String huffInputStr = compToStr(comp);

    }

    public void decodeHuffman(Compressor comp) {

    }


    // Huffman coding algorithm implementation in Java
    public void mainHuffman(Compressor comp) {
        String huffInputStr = compToStr(comp);

        Compressor retComp = strToComp(huffInputStr);


        buildHuffmanTree(huffInputStr);


    }

}