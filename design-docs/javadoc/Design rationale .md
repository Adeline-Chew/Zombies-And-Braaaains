##**Assignment 1 - Team StormBreaker**

###**Zombies Attack**

###**Beating up Zombies**

###**Crafting Weapons**

###**Rising from The Dead**

###**Farmers and Food**

####**Design choice**

1. Create a `Farmer` class which will extend the `Human` class
1. All the `Farmer` objects have Sow Behaviour, Wander Behaviour and Harvest Behaviour
1. Create a `SowBehaviour` class which have a method that `Farmer` can sow and fertilise a crop
1. Create a `Crop` class which extends the `Ground` class and has different food value
1. Create a `HarvestBehaviour` which have a method that `Crop` can be harvested

####**Advantages**
* Having a `Farmer` class will make the game more interesting, a `Farmer` can plant a crop, which can heal player and damaged human
* `Farmer` class is extensible, extra behaviours and actions can be implemented further
* Different behaviours is classified clearly

####**Disadvantages**
* Adding new classes will increase the dependencies
* `Farmer` will fertilise the crop for multiple times, player can get heal easily

