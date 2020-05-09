# **Assignment 1 - Team StormBreaker**

## **Zombie Attacks**

#### **Design choice**
1. Modify the *getIntrinsicWeapon()* in `Zombie`  class, implement *Math.random()* to choose between punch and bite.
1. In `AttackAction`, bite attack's chance of hitting is lower than punch attack's, but it outputs greater damage based on the random hit rate.
1. In order to confirm `Zombie`'s weapon and to heal `Zombie`, check if an `Actor` is an instance of `Zombie` in *execute()*.
1. *actor.heal(5)* is used after a successful bite attack.
1. Create a `PickUpBehaviour` and add it into *behaviour* in `Zombie`: If there is a `WeaponItem` at the `Zombie`'s location, `PickUpBehaviour` will return *pickUpAction()*.
1. Create a `ScreamBehaviour` which extends `Action` and implements `Behaviour`, add it into *behaviour* in `Zombie`: This class has a 25% to display "braaaains" and return null.
1. A `Zombie` is able to say words while doing other action.

#### **Advantages**
* `Zombie` is extensible, more interesting behaviour can be added in the future.
* `Zombie` will only pick up weapon if it doesn't have any.
* A Multitask `Zombie` makes this game more worth playing.

#### **Disadvantages**
* No message will be displayed in console if a `Zombie` is healed.

## **Beating up Zombies**

#### **Design choice**
1. Implement a *playerAttack()* in `AttackAction`, call it when running *execute()* if a `Player` is attacking a `Zombie`,
this method has 25% probability of knocking off one of the four `Zombie`'s limbs. It will then call a method in `Zombie`
to decrease the number of limbs of `Zombie`.
1. Each `Zombie` has two instance variables to record the number of its arms and legs.
1. In `AttackAction` when the actor is `Zombie` : (i)its leg has left one, (ii) the `IntrinsicWeapon` returned from `Zombie` 
is punching, when these two conditions meet, a random boolean need to throw again to half the probability of punching. 
1. In `Zombie` class, start counting the turn after it lost one leg, modify the classes which will return a moving action such as 
`WanderBehaviour` and `HuntBehaviour` so that they will return null when the turn of `Zombie` is even and greater than 0.
1. When a `Zombie` losing its limbs, a new `ZombieLimbs` object created and added on the `Location` where `Zombie` standing.
1. This `ZombieLimbs` can be casted into a more powerful weapon : `ZombieClub` or `ZombieMace`.

#### **Advantages**
* Further implementations can be added to make `Zombie` more "Zombielike" such as `Zombie` can pick up its limb 
to protect itself from `Player`'s attack.
* Minimise the dependencies between `Zombie` and all the weapons created.
* This design allows `Human` to attack `Zombie` as well if we need to implement it in the future.

#### **Disadvantages**
* Code in the method *execute()* of `AttackAction` is too complicated, may be troublesome to maintain.

## **Crafting Weapons**

#### **Design choice**
1. Create two different classes, `ZombieMace` and `ZombieClub` classes, which represent the weapons where the player crafted from Zombies fallen limbs.
1. `ZombieMace` and `ZombieClub` classes extend from `WeaponItem` class, the constructor remains mostly unchanged.
1. Inside `Player` *playTurn()* method, loop through the items in inventory. If limbs object exist, remove the simple clubs, and add crafted club/mace into the inventory. 

#### **Advantages**
* Simple implementation and keep the code DRY.
* A new class does not have to be implemented for crafting weapons.
* No changes to engine code.

#### **Disadvantages**
* Player always hold the first weapon in inventory.

## **Rising from The Dead**

#### **Design choice**
1. Create a `HumanCorpse` class, which will be created in `AttackAction` when a `Human` is dead.
1. `HumanCorpse` extends `Item`, overrides the inherited *tick()* method to count the turns of each `HumanCorpse`.
1. After 5 rounds, it has 50% of probability to revive and in 10th round, it must be returned from dead.
1. To revive a dead human, a new `Zombie` object is created in the same location where the `HumanCorpse` located.
1. Display the message of new `Zombie`.
1. Remove the `HumanCorpse` from the current location.

#### **Advantages**
* `HumanCorpse` will not continue counting after being removed.
* Simple implementation, few lines of code needed in `AttackAction`.
* Can be implemented further such as if `Human` die for other reasons.

#### **Disadvantages**
* Dependencies of `AttackAction` increased.

## **Farmers and Food**

#### **Design choice**

1. Create a `Farmer` class which will extend the `Human` class.
1. All the `Farmer` objects have Sow Behaviour, Wander Behaviour and Harvest Behaviour.
1. Create a `SowBehaviour` class which have a method that `Farmer` can sow and fertilise a crop.
1. Create a `Crop` class which extends the `Ground` class.
1. `Crop` has a *speedGrowth()* method which its turn can be increased by 10.
1. Create a `HarvestBehaviour` which have a method that `Crop` can be harvested.
1. Create a `Food` class which extends the `Item` class, its food value is generated by random between 1 to 14.
1. In `HarvestBehaviour` class, get location of `Farmer` and check whether the `Ground` display character is 'C', indicating `Crop` can be harvested. `Farmer` can set the ground to `Dirt` and add a `Food` item to ground.
1. `Player` can pick up the `Food` item and heal damage with the food value given.
1. Create `EatBehaviour` class which `Human` can heal if `Human` picked up `Food`.

#### **Advantages**
* Having a `Farmer` class will make the game be more interesting, a `Farmer` can plant a crop, which can heal player and damaged human
* `Farmer` class is extensible, extra behaviours and actions can be implemented further
* Different behaviours is classified clearly

#### **Disadvantages**
* Adding new classes will increase the dependencies
* `Farmer` will fertilise the crop for multiple times, player can get heal easily

