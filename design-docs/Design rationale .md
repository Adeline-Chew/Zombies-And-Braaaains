# **Assignment 1 - Team StormBreaker**

## **Zombie Attacks**
1. Modify the *getIntrinsicWeapon()* in `Zombie`  class, `Zombie` is having bite attack and punch attack to attack `Human`.
1. Probability of choosing bite or punch attack is 50%.
1. In getWeapon(), if bite attack is selected and hit, heal the `Zombie` 5 hit points.
1. Create a new class `PickUpBehaviour` implements `Behaviour` and add it into _behaviours_ in `Zombie`, so `Zombie` has the ability to pick up weapon.
1. Create a new class `ScreamBehaviour` which inherits `Action` and implements `Behaviour`, add it into _behaviours_ in `Zombie`.
1. `Zombie` has 10% chance to say things every turn.

#### **Design choice**

#### Modify *getIntrinsicWeapon()* in `Zombie`
- This method has two *IntrinsicWeapon* attributes which are punch attack and bite attack, and a probability of *double* type
to decide the kind of *IntrinsicWeapon*. 
- When a `Zombie` has both arms, the probability to choose bite attack is 50%,
return the chosen *IntrinsicWeapon*. 
- We also used *Math.random()* to decide the hit rate of bite attack, the lower the hit rate, the higher the bite attack's damage.

##### Design reason
* Bite attack is Zombie's attack instinct thus we make it as *IntrinsicWeapon* type and 
declared in this method for the sake of the principle **Declare things in the tightest possible scope**. 

#### Override *getWeapon* in `Zombie`
- This method has overridden in `Zombie` class, it contains a *Weapon* type attribute named _weapon_,
and it checks the inventory of `Zombie` if there is a weapon for `Zombie` to attack and assign this to _weapon_. If not, _weapon_ will assign to an *IntrinsicWeapon*
by calling *getIntrinsicWeapon()*. 
- Declared a *boolean* variable named _hit_ to decide whether the attack is success or failed, 
probability of hitting for bite attack is 40% while others are 50%.
- Check if the _weapon_ is bite attack, then heal the Zombie 5 points when the hitting is success.
- Return _weapon_ that the Zombie going to use or _null_ if the hitting boolean failed.

##### Design reason
* We implemented the probability and heal Zombie in `Zombie` class instead of checking in `AttackAction`
as these properties are belong to `Zombie`, `Zombie` should be responsible for its own properties.**

#### Create a new class `PickUpBehaviour`
- `PickUpBehaviour` is implementing the interface `Behaviour`, this class is responsible for `Actor` to have the ability
to pick up any `Item` object on the location where the `Actor` is standing.
- Override *getAction()* method, used an enhanced for loop to iterate the *Item* on the location, 
return *getPickUpAction()* or null if there is no item the Actor desired.
- This `PickUpBehaviour` is instantiated in `Zombie` class and added in Zombie's behaviours. During Zombie's *playTurn()*,
it will iterate `PickUpBehaviour` so `Zombie` is able to pick up weapon if there is any. 

##### Design reason
* It implements `Behaviour` as picking up item is an objective every Actor can have. 
Implementation of `Behaviour` will return an `Action` to achieve this objective, in this case we return `PickUpItemAction`.

#### Create a new class `ScreamBehaviour`
- The new class `ScreamBehaviour` inherits from `Action` and implements `Behaviour`, in this class we override *getAction()* from `Behaviour`,
*execute()* and *menuDescription()* from `Action`. 
- In *getAction()*, we get the probability using Math.random() and if it is 10%, we return _this_ as `ScreamBehaviour` since
considered as an `Action` type. 
- In *menuDescription()*, we declared some zombie-like words and randomly return one of these words. 
While in *execute()* we simply return *menuDescription()* 
- This `ScreamBehaviour` is instantiated in `Zombie` class and added in Zombie's behaviours. During Zombie's *playTurn()*, 
if `Zombie` get the chance to speak, the message will display on the console. 

##### Design reason
- `ScreamBehaviour` implements `Behaviour` and inherits `Action` as we follow the principle **Reduce dependencies as much 
as possible**.
- `Zombie` cannot do other `Action` while saying, as the `Zombie` is not smart enough to multi-tasking.

#### **Advantages**
* `Zombie` is extensible, more interesting behaviour can be added in the future.

#### **Disadvantages**
* No message will be displayed in console if a `Zombie` is healed.
* `Zombie` will blindly take the weapon it just pick up, it is not smart enough to select the weapon with higher damage.

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
1. Create a `ZombieLimbs` class extends `WeaponItem` class, which is a primitive weapon dropped by `Zombie` body, it has capability *CRAFTABLE*. 
1. Create two different classes, `ZombieMace` and `ZombieClub` classes, which represent the weapons where the player crafted from Zombies fallen limbs.
1. `ZombieMace` and `ZombieClub` classes extend from `WeaponItem` class, the constructor remains mostly unchanged. A capability *AS_WEAPON* is added.
1. Create a new class `CraftAction` which extends `Action` class, which `Player` can choose whether to craft a weapon from the menu.
1. In `CraftAction` *execute()* method, loop through the inventory of `Player`, if a limb exists, craft into mace or club, depends on the limb capability *LEG* or *ARM*. 
1. Inside `Player` *playTurn()* method, add `CraftAction` into actions if its inventory has item with capability *CRAFTABLE*. 

#### **Advantages**
* Simple implementation and keep the code DRY. Use capabilities to make the code easier to understand. 
* Check inventory of Player with item capabilities to add suitable actions. Only one loop required.
* No changes to engine code. 
* Different kinds of weapon with different damages are classified clearly. 
* Player can hold weapon with highest damage.

#### **Disadvantages**
* Player always hold the first weapon in inventory. (Solved)
* While choosing highest damage weapon, a list of `Weapon` is created, this can increase the dependency. 

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

##### *A new class: Farmer*

* A new type of `Human` which have same behaviours as `Human` but also can sow, fertilise and harvest crops. 

1. This class extends the `Human` class. 
1. It has a list of its own behaviours : Sow behaviour, Fertilise behaviour and Sow behaviour.
1. It has an overridden *playTurn()* method. 
    * A for loop in the behaviours list and get action for the behaviour. 
    * If the action does not return null, the actor will perform the action
    * It will perform `Human` normal behaviours if no farmer’s behaviours available.

###### *Design reason* 

* The `Farmer` class is responsible for its own properties only. It calls super class method to perform normal human’s behaviours, instead of repeating the code.
* `Farmer` class is extensible, extra behaviours can be implemented further.

##### *A new class: Crop*

* A new type of `Ground` which can be grown to food.

1. This class extends `Ground` class.
1. It has capability *UNRIPE* or *RIPEN*.
1. It has an overridden *tick()* method.
	* The display character will be ‘C’ after 20 turns. It has capability *RIPEN*.
1. It has an overridden *changeGroundStatus()* method which is implemented in `GroundInterface`.
	* It will increase the turn of the crop to ripe by 10 turns. 

###### *Design reason* 

* A new method implemented in `GroundInterface` to avoid downcasting. It makes the child classes more extensible.

##### *A new class: Food*

* A new type of item which can eaten by player and humans to heal damage. 

1. This class extends `Item` class.
1. It has capability *EDIBLE* and an integer represents the food value.
1. In the constructor, the food value is generated randomly between 1 and 14. 
1. It has an accessor *getFoodValue()* to get the food value.

###### *Design reason* 

* Capability is used to make code implementation easier. 
* Dependencies are minimised as much as possible.  

##### *A new class: SowBehaviour*

* A new type of behaviour of `Farmer` so that `Farmer` can sow a crop. 

1. This class extends `Action` class and implements `Behaviour` class. 
1. It has an overridden *getAction()* method.
    * It has a boolean to make the probability of sowing a crop to be 1/3.
    * It has a list of exits where the actor can take, these exits are the adjacent location of the actor.
    * A for loop in the exits list and check the capability of the ground of the location.
    * If the ground has capability *SOIL*, which means it is dirt, the ground will be set to `Crop`. 
1. It has another overridden *execute()* method.
	* This method will return a string about the description of the sow action with actor’s name.



##### *A new class: FertiliseBehaviour*

* A new type of behaviour of `Farmer` so that `Farmer` can fertilise a crop.

1. This class extends `Action` class and implements `Behaviour` class.
1. It has an overridden *getAction()* method.
    * It has a variable to represents current location of the actor.
    * There is an if statement to check whether the ground of the location has capability *UNRIPE* . If yes, it will call the *changeGroundStatus()* method to perform fertilisation.
1. It has another overridden *execute()* method.
	* This method will return a string about the description of the fertilise action with actor’s name.

###### *Design reason* 

* Variable for the location is declared in the tightest possible scope. 

##### *A new class: HarvestBehaviour*

* A new type of behaviour of `Farmer` and `Player` so that they can harvest a crop.

1. This class extends `Action` class and implements `Behaviour` class.
2. It has an overridden *getAction()* method.
    * It has a string local variable represents the food name. Food name is generated differently to make game more interesting.
    * It has a list of exits where the actor can take, these exits are the current and adjacent location of the actor.
    * A for loop in the exits list and check the capability of the ground of the location.
    * If the ground has capability *RIPEN*, which means the crop can be harvested. 
    * The ripen crop will be dropped onto the ground.
3. It has another overridden *execute()* method.
    * This method will return a string about the description of the harvest action with actor’s name.

###### *Design reason* 

* Variable for the food name and list of exits is declared in the tightest possible scope. 

##### *A new class: EatBehaviour*

* A new type of behaviour of `Farmer` and `Human` so that they can perform an eat action.

1. This class implements `Behaviour` class.
1. It has an overridden *getAction()* method.
    * An if statement to check whether the actor has capability *DAMAGED*.
    * A for loop through the inventory of the actor and get item with capability *EDIBLE*.
    * It will return a new EatAction with particular food item. 

###### *Design reason* 

* Reduce dependencies as much as possible. 
* An action will be performed only when the actor is damaged. 

##### *A new class: EatAction*

* A new type of action so that actor can eat and heal.

1. This class extends `Action` class.
1. It has an instance variable `Food`. 
1. It has an overridden *execute()* method.
    * The actor will be healed with the food value by calling *heal()* method. 
    * Method *removeItemFromInventory()* is called to remove food from inventory after eating.
    * This method will return a string about the description of the eat action with actor’s name and amount of damage healed.
1. It has an overridden *menuDescription()* method.
    * This returns a string describing the action to be displayed in the menu.

###### *Design reason* 

* Simple implementation as it just perform healing and removing item. 
* Player is able to choose when to perform the eating and healing action.

#### **Disadvantages**
* Adding new classes will increase the dependencies. For example, a `Crop` is created in `Sowbehaviour` class. 
* `Farmer` will fertilise the crop for multiple times, player can get heal easily.

