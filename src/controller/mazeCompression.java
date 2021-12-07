package controller;

import java.awt.*;
import java.io.*;
import java.util.*;


// Huffman tree node:
class Node {
    Character ch;
    Integer freq;
    Node left = null;
    Node right = null;

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

    public Node() {}
}


class mazeCompression {

    public static int mazeCnt;
    File mazeCntFile = new File("mazeCnt.bin");

    public mazeCompression() throws IOException {

        // if mazeCnt.txt exists:
        if (mazeCntFile.exists() && !mazeCntFile.isDirectory()) {

            // read the existing mazeCnt:
            DataInputStream dis = new DataInputStream(new FileInputStream(mazeCntFile));
            mazeCnt = dis.read();
            dis.close();
        }

        // if mazeCnt.txt not exists:
        else{
            // create the file and set the val to 0:
            boolean mazeCntTxtCreated = mazeCntFile.createNewFile();
            if (!mazeCntTxtCreated) {
                throw new IOException("Unable to create mazeCnt file at specified path. Please check that!");
            }

            DataOutputStream dos = new DataOutputStream(new FileOutputStream(mazeCntFile));
            dos.write(0);
            dos.close();
            }
    }


    public int getUpdateMazeCnt() throws IOException {

        // read the value of mazeCntTxt:
        DataInputStream dis = new DataInputStream(new FileInputStream(mazeCntFile));
        int updatedMazeCnt = dis.read();
        dis.close();

        // increment updatedMazeCnt:
        updatedMazeCnt++;

        // write updatedMazeCnt to mazeCntTxt:
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(mazeCntFile));
        dos.write(updatedMazeCnt);
        dos.close();

        return updatedMazeCnt;
    }

    // Traverse huffman tree, then storing codes in a map:
    public static void encode(Node root, String str, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }

        // Leaf node encountering:
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }
        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }


    // check if node is a leaf:
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }


    public static Node huffmanTreeBuilder(String text) {
        // Validate not an empty string:
        if (text == null || text.length() == 0) {
            return new Node();
        }

        // Count the frequency of appearance of each character and store it in a map:
        Map<Character, Integer> freqCharAmountMap = new HashMap<>();

        // Priority queue to store live nodes of the Huffman tree:
        PriorityQueue<Node> pq;

        for (char c : text.toCharArray()) {
            freqCharAmountMap.put(c, freqCharAmountMap.getOrDefault(c, 0) + 1);
        }

        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));


        // create a leaf node for each character and add it to the priority queue:
        for (var entry : freqCharAmountMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // main looping until last node:
        while (pq.size() != 1) {

            // Remove two nodes with highest frequency:
            Node left = pq.poll();
            Node right = pq.poll();



            // Create new node with 2 removed nodes as it's children, freq of the new one equal to sum of removed nodes:
            if (left.freq != null && right.freq != null) {
                int sum = left.freq + right.freq;
                pq.add(new Node(null, sum, left, right));
            }
        }

        // Return father node:
        return pq.peek();
    }

    public String huffmanEncoder(String text) {

        StringBuilder encodedStr = new StringBuilder();
        Node root = huffmanTreeBuilder(text);

        // Traverse the Huffman tree and store the Huffman codes in a map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        encodedStr.append(huffmanCode);
        encodedStr.append("\n");

        for (char c : text.toCharArray()) {
            encodedStr.append(huffmanCode.get(c));
        }

        return encodedStr.toString();
    }


    public static String huffmanDecoder(String encodedHuffmanStrDict) {
        int index = 1;
        String decodedStr = "";
        Map<String, Character> huffHm = new HashMap<>();

        String codeDict = encodedHuffmanStrDict.split("\n")[0];
        codeDict = codeDict.substring(1,codeDict.length()-1);
        String encodedHuffmanStr = encodedHuffmanStrDict.split("\n")[1];

        for(String pair : codeDict.split(", ")) {
            huffHm.put(pair.split("=")[1], pair.split("=")[0].charAt(0));
        }

        // Decoding the encoded string:
        while (encodedHuffmanStr.length()>0) {
            if (huffHm.containsKey(encodedHuffmanStr.substring(0,index))) {
                decodedStr = decodedStr.concat(String.valueOf(huffHm.get(encodedHuffmanStr.substring(0,index))));
                encodedHuffmanStr = encodedHuffmanStr.substring(index);
                index = 0;
            }
            index++;
        }
        return decodedStr;
    }

    private String mazeToHuffStr(maze2d maze) {

        final StringBuilder sb = new StringBuilder();

        sb.append(maze.getEntrance().x);
        sb.append(",");
        sb.append(maze.getEntrance().y);
        sb.append("-");
        sb.append(maze.getExit().x);
        sb.append(",");
        sb.append(maze.getExit().y);
        sb.append("-");
        sb.append(maze.mazeSize);
        sb.append("-");
        for (int i = 0; i < maze.mazeStructure[0].length; i++) {
            for (int j = 0; j < maze.mazeStructure[1].length; j++) {
                if (maze.mazeStructure[i][j])
                    sb.append(1);
                else
                    sb.append(0);
            }
        }
        return sb.toString();
    }

    public maze2d huffStrToMaze(String str) {

        String[] splitStr = str.split("-");

        int size = Integer.parseInt(splitStr[2]);

        boolean [][] structure = new boolean[size][size];

        int structureIdx = 0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                structure[i][j] = splitStr[3].charAt(structureIdx) == '1';
                structureIdx++;
            }
        }

        return new maze2d(Integer.parseInt(splitStr[2]),
                new Point(Integer.parseInt(splitStr[0].split(",")[0]),
                        Integer.parseInt(splitStr[0].split(",")[1])),
                new Point(Integer.parseInt(splitStr[1].split(",")[0]),
                        Integer.parseInt(splitStr[1].split(",")[1])), structure);
    }



    // encoding using Huffman and saving the
    public String encodeHuffmanAndSave(maze2d currMaze) throws IOException {

        String compressedMazeFilename = currMaze.getClass().getSimpleName() + "@" + getUpdateMazeCnt() + ".bin";

        // Converting compressor to string and encoding:
        String huffInputStr = mazeToHuffStr(currMaze);
        String actualEncoded = huffmanEncoder(huffInputStr);

        // Creating binary file
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(compressedMazeFilename));

        dos.writeBytes(actualEncoded);
        dos.close();

        return compressedMazeFilename;
    }


    public maze2d decodeHuffmanAndSave(String encodedFilename) throws IOException {

        String actualDecoded;
        DataInputStream ois = new DataInputStream(new FileInputStream(encodedFilename));

        byte[] binData = ois.readAllBytes();
        String retrievedString = new String(binData);
        ois.close();

        actualDecoded = huffmanDecoder(retrievedString);

        return huffStrToMaze(actualDecoded);
    }
}