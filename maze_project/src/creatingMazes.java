public class creatingMazes {

    public static void main(String[] args) throws Exception {
        SimpleMaze2dGenerator simple = new SimpleMaze2dGenerator();
        System.out.println(simple.measureAlgorithmTime(60));
        System.out.println(simple.simpleMaze.toString());
    }
}
