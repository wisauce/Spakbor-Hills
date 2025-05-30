package sti.oop.models.deploys;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import sti.oop.models.Item.Fish;
import sti.oop.models.assets.Asset;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;

public class Pond extends Asset {

    public Pond(RandomizeFarm farmMap){
        super((farmMap.getPondLoc()[2]+9)*Constants.TILE_SIZE, (farmMap.getPondLoc()[0]+9)*Constants.TILE_SIZE, "/images/pond.png",4*Constants.TILE_SIZE,3*Constants.TILE_SIZE, true);
        List<Fish> fishPool = new ArrayList<>();

        fishPool.add(new Fish(
            "Carp",
            EnumSet.allOf(Fish.Season.class),
            new int[]{0, 23}, 
            EnumSet.allOf(Fish.Weather.class),
            EnumSet.allOf(Fish.Location.class),
            "COMMON"
        ));

        fishPool.add(new Fish(
            "MidnightCarp",
            EnumSet.of(Fish.Season.WINTER, Fish.Season.FALL),
            new int[]{20, 2}, 
            EnumSet.allOf(Fish.Weather.class),
            EnumSet.of(Fish.Location.POND),
            "REGULAR"
        ));

        fishPool.add(new Fish(
            "CatFish",
            EnumSet.of(Fish.Season.SPRING, Fish.Season.SUMMER, Fish.Season.FALL),
            new int[]{6, 22}, 
            EnumSet.of(Fish.Weather.RAINY),
            EnumSet.of(Fish.Location.POND),
            "REGULAR"
        ));

        fishPool.add(new Fish(
            "Angler",
            EnumSet.of(Fish.Season.SUMMER),
            new int[]{8, 20}, 
            EnumSet.allOf(Fish.Weather.class),
            EnumSet.of(Fish.Location.POND),
            "LEGENDARY"
        ));
    }

}