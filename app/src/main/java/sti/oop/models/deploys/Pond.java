package sti.oop.models.deploys;

import sti.oop.models.assets.Asset;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;

public class Pond extends Asset {

    public Pond(RandomizeFarm farmMap){
        super((farmMap.getPondLoc()[2]+9)*Constants.TILE_SIZE, (farmMap.getPondLoc()[0]+9)*Constants.TILE_SIZE, "/images/pond.png",4*Constants.TILE_SIZE,3*Constants.TILE_SIZE, true);

    }

}