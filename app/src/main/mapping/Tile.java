public class Tile {
    //atribut
    /* Note *
     * Type : House = h, Pond = p, ShippingBin = s
     * occupied : 1 = true, 0 = false "blm terisi"
     */
    int occupied;
    char type; 

    public Tile() {
        this.occupied = 0;
        this.type = '.';
    }

    public boolean isOcuppied() {
        if(this.occupied == 0){
            return false;
        }
        return true;
    }

    public void setTypeTile(char type,int occupied){
        this.type = type;
        this.occupied = occupied;
    }
}