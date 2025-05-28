package sti.oop.models.DeployedObject;

import sti.oop.models.Asset;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;
import sti.oop.utils.Tile;

public class House extends Asset {
    public House(RandomizeFarm farmMap){
        super((farmMap.getHouseLoc()[2]+9)*Constants.TILE_SIZE, (farmMap.getHouseLoc()[0]+9)*Constants.TILE_SIZE, "/images/house.png",6*Constants.TILE_SIZE,6*Constants.TILE_SIZE, true);
    }
}