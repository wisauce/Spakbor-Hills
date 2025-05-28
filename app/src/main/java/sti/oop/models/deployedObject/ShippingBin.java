package sti.oop.models.deployedObject;

import sti.oop.models.assets.Asset;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;

public class ShippingBin extends Asset {
    int[] shippingBinLocation = new int [4];

    public ShippingBin(RandomizeFarm farmMap){
        super((farmMap.getShippingBinLoc()[2]+9)*Constants.TILE_SIZE, (farmMap.getShippingBinLoc()[0]+9)*Constants.TILE_SIZE, "/images/shippingBin.png",3*Constants.TILE_SIZE,2*Constants.TILE_SIZE, true);
    }
}