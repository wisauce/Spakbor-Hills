/**
 * Randomize farm
 */
import java.util.Random;

public class RandomizeFarm {
    // Static size dari deployed object
    static final int MAP_SIZE = 32;
    static final int HOUSE_SIZE = 6;
    static final int POND_X = 4;
    static final int POND_Y = 3;
    static final int SHIPPINGBIN_X = 3;
    static final int SHIPPINGBIN_Y = 2;

    // Atribut Map
    Tile[][] farmMap;
    private final Random rand = new Random();

    //Generate blank map
    public void BlankMapGenerator(int MAP_SIZE){
        farmMap = new Tile[MAP_SIZE][MAP_SIZE];
        for(int i=0; i<MAP_SIZE ; i++){
            for(int j=0; j<MAP_SIZE ; j++){
                farmMap[i][j] = new Tile();
            }
        }
    }

    //Check free
    public boolean isAreaFree(Tile[][] farmMap, int row, int col, int height, int width){
        for (int i=0; i<height ; i++){
            for(int j=0; j<width ; j++){
                if(farmMap[row+i][col+j].isOcuppied()){
                    return false;
                }
            }
        }
        return true;
    }

    //Placing Object
    public void placeObject(Tile[][] farmMap, int height, int width, int row, int col, String type){
        //Atribut
        char t;
        int occupied;

        //Function
        if(type.equals("House")){
            t = 'h';
            occupied = 1;
        }
        else if(type.equals("Pond")){
            t = 'p';
            occupied = 1;
        }
        else if(type.equals("ShippingBin")){
            t = 's';
            occupied = 1;
        }
        else{
            t ='.';
            occupied =0;
        }

        //Pengisian Object
        for(int i=0; i<height ; i++){
            for(int j=0; j<width ;j++){
                farmMap[row+i][col+j].setTypeTile(t,occupied);
            }
        }
    }

    public void generateHouse() {
        // Atribut
        int maxRow = MAP_SIZE - HOUSE_SIZE;
        int maxCol = MAP_SIZE - HOUSE_SIZE;
        int startRow=0;
        int startCol=0;

        randomizePos(maxRow,maxCol,startRow,startCol);
        if(isAreaFree(farmMap,startRow,startCol,HOUSE_SIZE,HOUSE_SIZE)){
            placeObject(farmMap,HOUSE_SIZE,HOUSE_SIZE,startRow,startCol,"House");
        }
    }

    // OutputMap
    public void printMap(){
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(farmMap[i][j].type + " ");
            }
            System.out.println();
        }
    }

    //Main
    public static void main(String[] args){
        RandomizeFarm farmMap = new RandomizeFarm();
        farmMap.BlankMapGenerator(MAP_SIZE);
        farmMap.printMap();
        System.out.println();
        farmMap.generateHouse();
        farmMap.printMap();
    }


    //Randomize
    public int[] randomizePos(int maxRow, int maxCol) {
        startRow = rand.nextInt(maxRow + 1);
        startCol = rand.nextInt(maxCol + 1);
    }
}
