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

    // Atribut Object
    int[] start = new int[2];
    int[] housePosition = new int[4]; // [minRow,maxRow,minCol,maxCol]
    /*Note Atribut start*
     * Start[0] akan mewakili startRow
     * start[1] akan mewakili startCol
     */
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
    public boolean isAreaFree(Tile[][] farmMap, int row, int col, int width, int height){
        for (int i=0; i<height ; i++){
            for(int j=0; j<width ; j++){
                if(farmMap[row+i][col+j].isOcuppied()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isArea2Tileto(Tile[][] farmMap, int row, int col, int width, int height){
        int checkRow, checkCol, baseRow,baseCol;

        // Memastikan agar tidak lebih dari 32 x 32
        baseRow = (row - 2 >= 0 ? (row-2) : row);
        baseCol = (col - 2 >= 0 ? (col-2) : col);
        checkRow = (row > 1 ? 2 : 0) + height + ((row+height) < 30 ? 2 : 0);
        checkCol = (col > 1 ? 2 : 0) + width + ((col+width) < 30 ? 2 : 0);

        for (int i=1; i<=checkRow ; i+= (height+2)){
            for(int j=0; j<width ; j++){
                if(farmMap[baseRow+i][col+j].getType() == 'h'){
                    return false;
                }
            }
        }

        for (int j=1; j<=checkCol ; j+= (width+2)){
            for(int i=0; i<height ; i++){
                if(farmMap[row+i][baseCol+j].getType() == 'h'){
                    return false;
                }
            }
        }

        for (int i=0; i<=checkRow ; i+= (height+4)){
            for(int j=0; j<width ; j++){
                if(farmMap[baseRow+i][col+j].getType() == 'h'){
                    return true;
                }
            }
        }
        
        for (int j=0; j<=checkCol ; j+= (width+4)){
            for(int i=0; i<height ; i++){
                if(farmMap[row+i][baseCol+j].getType() == 'h'){
                    return true;
                }
            }
        }
        return false;
    }
    //Placing Object
    public void placeObject(Tile[][] farmMap, int width, int height, int row, int col, String type){
        //Atribut
        char t;
        int occupied;

        //Function
        if(type.equals("House")){
            t = 'h';
            occupied = 1;
        }
        else if(type.equals("Pond")){
            t = 'o';
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

        // Acak lokasi
        randomizePos(maxRow,maxCol,start);
        int startRow = start[0];
        int startCol = start[1];
        
        if(isAreaFree(farmMap,startRow,startCol,HOUSE_SIZE,HOUSE_SIZE)){
            placeObject(farmMap,HOUSE_SIZE,HOUSE_SIZE,startRow,startCol,"House");
        }
        housePosition[0] = startRow;
        housePosition[1] = startRow + HOUSE_SIZE;
        housePosition[2] = startCol;
        housePosition[3] = startCol + HOUSE_SIZE;
    }

    public void generateShippingBin() {
        // Atribut
        int minRow = start[0] - (start[0] > 2 ? 3 : 0);
        int maxRow = start[0] + HOUSE_SIZE + ((start[0] + HOUSE_SIZE)<31 ? 1 : 0);
        int minCol = start[1] - (start[1] > 3 ? 4 : 0);
        int maxCol = start[1] + HOUSE_SIZE + ((start[1] + HOUSE_SIZE)<31 ? 1 : 0);

        int loop=0;
        // Memastikan tidak lebih dari 32x32
        maxRow = (maxRow + SHIPPINGBIN_Y > 31 ? (32-SHIPPINGBIN_Y) : maxRow);
        maxCol = (maxCol+ SHIPPINGBIN_X > 31 ? (32-SHIPPINGBIN_X) : maxCol);

        randomizePosInterval(minRow,maxRow,minCol,maxCol,start);
        int startRow = start[0];
        int startCol = start[1];

        // Acak Lokasi
        while ((!isArea2Tileto(farmMap,startRow,startCol,SHIPPINGBIN_X,SHIPPINGBIN_Y) || !isAreaFree(farmMap,startRow,startCol,SHIPPINGBIN_X,SHIPPINGBIN_Y)) && loop <50){
            randomizePosInterval(minRow,maxRow,minCol,maxCol,start);
            startRow = start[0];
            startCol = start[1];
            loop++;
            if(loop==40){
                if(housePosition[0] <= 4){
                    minRow = housePosition[0] + HOUSE_SIZE + 1;
                }
                if(housePosition[1] >= 28){
                    maxRow = housePosition[0];
                }
                if(housePosition[2] <= 4){
                    minRow = housePosition[2] + HOUSE_SIZE + 1;
                }
                if(housePosition[3] >= 28){
                    maxRow = housePosition[2];
                }
            }
        }
        placeObject(farmMap,SHIPPINGBIN_X,SHIPPINGBIN_Y,startRow,startCol,"ShippingBin");
    }

    public void generatePond() {
        // Atribut
        int maxRow = MAP_SIZE - POND_Y;
        int maxCol = MAP_SIZE - POND_X;
        randomizePos(maxRow,maxCol,start);
        int startRow = start[0];
        int startCol = start[1];  

        // Acak lokasi
        while(!isAreaFree(farmMap,startRow,startCol,POND_X,POND_Y)){
            randomizePos(maxRow,maxCol,start);
            startRow = start[0];
            startCol = start[1];
        }
        placeObject(farmMap,POND_X,POND_Y,startRow,startCol,"Pond");
    }

    public void generateMap(RandomizeFarm farmMap){
        farmMap.BlankMapGenerator(MAP_SIZE);
        farmMap.generateHouse();
        farmMap.generateShippingBin();
        farmMap.generatePond();
        farmMap.printMap();
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

    public void printMapCollition(){
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(farmMap[i][j].occupied + " ");
            }
            System.out.println();
        }
    }

    //Main
    public static void main(String[] args){
        RandomizeFarm farmMap = new RandomizeFarm();
        farmMap.generateMap(farmMap);
    }


    //Randomize
    public int[] randomizePos(int maxRow, int maxCol, int[] start) {
        start[0] = rand.nextInt(maxRow + 1);
        start[1] = rand.nextInt(maxCol + 1);
        return start;
    }

    public int[] randomizePosInterval(int minRow, int maxRow, int minCol, int maxCol, int[] start) {
        int row, col;

        do {
            row = rand.nextInt(maxRow - minRow + 1) + minRow;
        } while (row >= housePosition[0] && row <= housePosition[1]);

        do {
            col = rand.nextInt(maxCol - minCol + 1) + minCol;
        } while (col >= housePosition[2] && col <= housePosition[3]);

        start[0] = row;
        start[1] = col;
        return start;
    }
}
