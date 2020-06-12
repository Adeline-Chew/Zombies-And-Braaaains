# **Assignment 1 - Team StormBreaker**

## **Zombie Attacks**
1. Modify the *getIntrinsicWeapon()* in `Zombie`  class, `Zombie` is having bite attack and punch attack to attack `Human`.
1. Probability of choosing bite or punch attack is 50%.
1. In getWeapon(), if bite attack is selected and hit, heal the `Zombie` 5 hit points.
1. Create a new class `PickUpBehaviour` implements `Behaviour` and add it into _behaviours_ in `Zombie`, so `Zombie` has the ability to pick up weapon.
1. Create a new class `ScreamBehaviour` which inherits `Action` and implements `Behaviour`, add it into _behaviours_ in `Zombie`.
1. `Zombie` has 10% chance to say things every turn.

### **Design choice**

#### Modify *getIntrinsicWeapon()* in `Zombie`
- This method has two *IntrinsicWeapon* attributes which are punch attack and bite attack, and a probability of *double* type
to decide the kind of *IntrinsicWeapon*. 
- When a `Zombie` has both arms, the probability of choosing bite attack is 50%, when `Zombie` loses one arm, the probability of choosing
punch attack is 25% and bite attack 75%, while the chance of bite attack is 100% when `Zombie` loses both arms.
- Return the chosen *IntrinsicWeapon*. 
- We also used *Math.random()* to decide the hit rate of bite attack, the lower the hit rate, the higher the bite attack's damage.

#### Design reason
* Bite attack is Zombie's attack instinct thus we make it as *IntrinsicWeapon* type and 
declared in this method for the sake of the principle **Declare things in the tightest possible scope**. 

#### Override *getWeapon()* in `Zombie`
- This method has overridden in `Zombie` class, it contains a *Weapon* type attribute named _weapon_,
and it checks the inventory of `Zombie` if there is a weapon for `Zombie` to attack and assign this to _weapon_. If not, _weapon_ will assign to an *IntrinsicWeapon*
by calling *getIntrinsicWeapon()*. 
- Declared a *boolean* variable named _hit_ to decide whether the attack is success or failed, 
probability of hitting for bite attack is 40% while others are 50%.
- Check if the _weapon_ is bite attack, then heal the Zombie 5 points when the hitting is success.
- Return _weapon_ that the Zombie going to use or _null_ if the hitting boolean failed.

#### Design reason
* We implemented the probability and heal Zombie in `Zombie` class instead of checking in `AttackAction`
as these properties are belong to `Zombie`, `Zombie` should be responsible for its own properties.**

#### Create a new class `PickUpBehaviour`
- `PickUpBehaviour` is implementing the interface `Behaviour`, this class is responsible for `Actor` to have the ability
to pick up any `Item` object on the location where the `Actor` is standing.
- Override *getAction()* method, used an enhanced for loop to iterate the *Item* on the location, 
return *getPickUpAction()* or null if there is no item the Actor desired.
- This `PickUpBehaviour` is instantiated in `Zombie` class and added in Zombie's behaviours. During Zombie's *playTurn()*,
it will iterate `PickUpBehaviour` so `Zombie` is able to pick up weapon if there is any. 

#### Design reason
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

#### Design reason
- `ScreamBehaviour` implements `Behaviour` and inherits `Action` as we follow the principle **Reduce dependencies as much 
as possible**.
- `Zombie` cannot do other `Action` while saying, as the `Zombie` is not smart enough to multi-tasking.

#### **Advantages**
* `Zombie` is extensible, more interesting behaviour can be added in the future.

#### **Disadvantages**
* No message will be displayed in console if a `Zombie` is healed.
* `Zombie` will blindly take the weapon it just pick up, it is not smart enough to select the weapon with higher damage.

## **Beating up Zombies**
1. When `Zombie` get attacked, the attacker has 25% of probability to knock off Zombie's limb. A new method *damage()* is implemented
in `ActorInterface` and overridden by `ZombieActor` and `Zombie`.
1. Modify `Zombie` class, instance variables and capabilities added.
1. If `Zombie` loses one arm, probability of punching is halved (25% punching and 75% biting), it has 50% chance of dropping
any weapon it is holding.
1. If `Zombie` loses both arms, it will drop any weapon it was holding and lost the capabilities to pick up items.
1. If `Zombie` loses one leg, start counting the turn in *playTurn()*, so it can only move every second turn. `Zombie` will lose
walking ability if it loses both legs.
1. Check the *WALK* capability of `Actor` in behaviours that return move action such as `HuntBehaviour` and `WanderBehaviour`.
1. Created `ZombieLimbs` will drop on Zombie's adjacent location, in order to randomly get the adjacent location of `Zombie`,
we created a new method *getAdjacentLocation()* in `ActorInterface`.
1. `ZombieLimbs` extends from `WeaponItem` class and has 15 damage. This `ZombieLimbs` is considered a simple weapon and can be
casted into more powerful weapons such as `ZombieClub` or `ZombieMace`.

### **Design choice**
#### Modify `Zombie` class
- Two instance variables _numberOfArms_ and _numberOfLegs_ are added in `Zombie` class.
- These instance variables tell the number of arms, or the number of legs for that particular `Zombie`.
- Created private void dropWeapon() method, we used boolean _drop_ to determine is the `Zombie` going to drop its weapon when it loses
one arm.
- If drop is true or `Zombie` loses both arms, the weapon `Zombie` is holding will drop by calling the method *removeItemFromInventory()*.

#### Design reason
- Both instance variables is properties of `Zombie` because only `Zombie` will fall to pieces at this moment, thus we follow
the principle **Classes should be responsible for their own properties**.

#### Added new `ZombieCapabilities` constants for `ZombieActor`.
- Two new constants added in enum class `ZombieCapabilities`: *WALK* and *HOLD*.
- In the constructor of `ZombieActor`, we added these two capabilities by calling *addCapabilities()*, this represents 
that every `ZombieActor` has the capability to walk and hold object.

#### Design reason
- We can reduce some redundant code to check the number of legs or arms the Actor has, and so far this capability applied to all
actor in this game.

#### New method *getRandomAdjacent(GameMap map)* in `ActorInterface`
- This is override in `ZombieActor`, it randomly returns an available exit for an `Actor`.

#### Design reason
- This method can be called in several classes such as `Zombie` and `HumanCorpse` rather than writing several duplicated code.

#### New method *damage(int points, GameMap map)* in `ActorInterface`
- We override *damage(int points, GameMap map) in `ZombieActor`, it calls *hurt(int points)* and pass the parameter _points_ to deduct
`Actor`'s hit points. 
- This method returns String that indicates the Actor current hit points.

#### Design reason
- Instead of using *hurt()* that deducts hit points and simply returns null when actor get attacked,
 *damage()* can do more with the extra parameter _map_ and returned String indicating the `Actor`'s status.
- Calling hurt() in this method as we follow the principle **Don't Repeat Yourself**.

#### Override *damage(int points, GameMap map)* in `Zombie`
- This method declared a few attributes: Location type named _adjacent_ to get a random adjacent location of `Zombie`, 
String type named _result_ to return the information of `Zombie` in this damage, int _probability_ and boolean _arm_ to decide whether arm or leg
is getting knocked off, boolean _knockOff_ inform is the attacker success to knock off `Zombie`'s limb.
- If the arm is getting knock off, decrease the _numberOfArms_ and if _numberOfArms_ reaches 0, remove *HOLD* capability of the `Zombie`,
call the method *dropWeapon()*, a `ZombieLimbs` which has the capability of *ARM* is created and drop it to adjacent location by calling _adjacent_.addItem().
- Else if the leg is getting knock off, decrease the _numberOfLegs_ and if _numberOfLegs_ reaches 0, remove *WALK* capability of the `Zombie`,
a `ZombieLimbs` which has the capability of *LEG* is created, and the way dropping is similar with `Zombie`'s arms. 
- Finally, return the String that informed `Zombie` current status.

#### Design reason
- We override this method in `Zombie` class as `Zombie` has the chance to drop its limbs when it gets attacked. This property
is unique to `Zombie` thus we **encapsulated it in the tightest possible scope**.

#### Modify playTurn() in `Zombie`
- If _numberOfLegs_ equals to 1, starts to increment _turn_, remove *WALK* capability of `Zombie` when _turn_ is an even number
while add *WALK* capability back when _turn_ is odd. 

#### Modify `HuntBehaviour` and `WanderBehaviour`
- We modify the method getAction() in both classes by checking if the `Actor` has the *WALK* capability before return any move action.

#### Design reason
- According to the design principle **Minimize dependencies that cross encapsulation boundaries**, it is considered unwisely
if we downcast the `Actor` to `Zombie` just for getting the number of legs of `Zombie`, thus we implemented *WALK* capabilities
for `ZombieActor`.

#### **Advantages**
* Further implementations can be added to make `Zombie` more "Zombielike".
* Minimise the dependencies between `Zombie` and all the weapons created.

#### **Disadvantages**
* Capability may need to change if there is a new character added in future.

## **Crafting Weapons**

### **Design choice**
#### A new class: ZombieLimbs

* A new type of simple weapon item dropped from `Zombie`.

1. This class extends `WeaponItem` class.
1. This weapon has 15 damages.
1. The constructor has a capability as parameter, it represents whether the dropped limb is *LEG* or *ARM*.
1. In the constructor, add the capability *AS_WEAPON* and *CRAFTABLE*. 

#### Design reason

* Minimise the dependencies. 
* Use capabilities to make the item easier to be identified in other classes.
* Each weapon is classified clearly with its own damage.

#### A new class: ZombieClub

* A new type of weapon item crafted from `Zombie` limbs. 

1. This class extends `WeaponItem` class.
1. This weapon has higher damage than simple limbs, which is 22 damages.
1. In the constructor, the capability *AS_WEAPON* is added.

#### Design reason

* Simple implementation. Dependencies is minimised as much as possible.
* Each weapon is classified clearly with its own damage.

#### A new class: ZombieMace

* A new type of weapon item crafted from `Zombie` limbs. 

1. This class extends `WeaponItem` class.
1. This weapon has higher damage than simple limbs, which is 25 damages.
1. In the constructor, the capability *AS_WEAPON* is added.

#### Design reason

* Simple implementation. Dependencies is minimised as much as possible.
* Each weapon is classified clearly with its own damage.

#### A new class: CraftAction

* A new type of action so that `Player` can craft a new weapon.

1. This class extends `Action` class.
1. It has an instance variable `Item` represents limb dropped on the ground.
1. It has an overridden *execute()* method.
	* A string local variable to be displayed in the UI after performing the craft action.
	* Remove the original limb from the player’s inventory first. 
	* Then use if statement to check the capability of the limb.
	* If the capability is *ARM*, a zombie club is crafted and added to inventory.
    * If the capability is *LEG*, a zombie mace is crafted and added to inventory. 
1. It has an overridden *menuDescription()* method.
	* This method shows a message in the menu, this enables `Player` to choose when to craft a new weapon.

#### Design reason

* Reduce repeated code, as remove limb item first instead of removing after deciding the new weapon type.
* Each variable is declared in its tightest possible scope.
* A new `Action` child class instead of `Behaviour` as this can be performed by the `Player` only.

#### Modify class: Zombie

1. In the overridden *damage()* method: 
	* A boolean indicating whether the dropped limb is arm or leg.
	* It has 50/50 chance to drop an arm or leg when the `Zombie` dropped a limb.
	* The dropped limb has new capability, either *LEG* or *ARM*.
	* Adjacent location will be added the dropped limb item using method *addItem()*.

##### Modify class: Player

1. In the for loop *playTurn()* method: 
	* Item in the inventory will be check for its capability. 
	* If item has capability *CRAFTABLE*, a new *CraftAction* will be added into the actions list.
1. In the overridden *getWeapon()* method: 
	* A list of weapons available in the inventory is created. 
	* Each weapon’s damage will be compared. `Player` will hold the highest damage weapon at last.

#### Design reason

* A new craft action is added only when the inventory has suitable item. 
* Player is able to attack with highest damage weapon. 
* Repeated code is reduced as much as possible, only a for loop is used.

#### **Disadvantages**
* Player always hold the first weapon in inventory. (Solved)
* While choosing highest damage weapon, a list of `Weapon` is created, this can increase the dependency. 

## **Rising from The Dead**
1. Create a `HumanCorpse` class extends from `PortableItem`.
1. When `Human` is killed by `Zombie`, his corpse will turn into `Zombie` within 5 - 10 turns.
1. Add *DEAD* capability in `ZombieCapability`.
1. Override isConscious() method in `Human` class.
1. Since `HumanCorpse` is portable, it can be carried by `Actor`.
1. Modify *execute()* in `AttackAction` so it can create `HumanCorpse` when a `Human` is killed.

### **Design choice**

#### Create new class `HumanCorpse`
- `HumanCorpse` extends from `PortableItem`, instance variables declared are int _turn_ and Random type _rand_ .
- Override *tick(Location currentLocation)* from `Item`, *tick()* will be called every time at the end of the turn, so the object can tell the passage
of the time. _revive_ is assigned to a random boolean value, then increase _turn_ by 1. When _turn_ is greater than 5
and smaller than 10, `HumanCorpse` has 50% chance to rise from the dead and in the 10th turn, it must be turned into `Zombie`.
- If the _revive_ return true or _turn_ is equal to 10, an internal method corpseRevive(currentLocation) is called.
Lastly, remove the corpse from current location using *currentLocation.removeItem(this)* so the _turn_ will stop counting.
- Override another overloaded *tick(Location currentLocation, Actor actor)* from `Item`, this method will be called every turn
if the `HumanCorpse` is in an `Actor`'s inventory. The conditions to turn a `HumanCorpse` into a `Zombie` are the same, 
but the parameter passes through corpseRevive() we pass the adjacent location of the `Actor` by calling *getRandomAdjacent*,
so the `Zombie` will be created at any adjacent near the `Actor`.
- Eventually remove the corpse from the `Actor`'s inventory, call *actor.removeItemFromInventory(this)*.
- A private method corpseRevive(Location here) is created, when this method is called, it add an actor at the current location
and pass a new `Zombie` object as the parameter, then display message on the console telling the user that the dead human has 
turned into `Zombie`.

#### Design reason
- Override two overloaded methods so even though the corpse is carried by an `Actor` halfway when the turn start counting, 
the _turn_ will still continue increase until the corpse return to `Zombie`.
- We declared _revive_ in *tick()* method rather than declared as an instance variable as the design principles
tell us **Declare things in the tightest possible scope**.

#### Override isConscious() in `Human`
- A boolean _alive_ is assigned to the return value of *super.isConscious*, if _alive_ is false, we remove the *ALIVE* capability 
and add *DEAD* capability for `Human`.

#### Modify execute() in `AttackAction`
- When the _target_ is unconscious, if the target has the *DEAD* capability, a new `HumanCorpse` object is created and assigned
to _corpse_ attribute. If not, a new `PortableItem` object will be created and assigned to _corpse_.
- _corpse_ is added on the location where the _target_ died. 

#### **Advantages**
* `HumanCorpse` will not continue counting after being removed.
* Simple implementation, few lines of code needed in `AttackAction`.
* Can be implemented further such as if `Human` die for other reasons.

#### **Disadvantages**
* Dependencies of `AttackAction` increased.
* Dependencies cross encapsulation boundaries will increase as new class created.

## **Farmers and Food**

### **Design choice**

#### A new class: Farmer

* A new type of `Human` which have same behaviours as `Human` but also can sow, fertilise and harvest crops. 

1. This class extends the `Human` class. 
1. It has a list of its own behaviours : Sow behaviour, Fertilise behaviour and Sow behaviour.
1. It has an overridden *playTurn()* method. 
    * A for loop in the behaviours list and get action for the behaviour. 
    * If the action does not return null, the actor will perform the action
    * It will perform `Human` normal behaviours if no farmer’s behaviours available.

#### Design reason

* The `Farmer` class is responsible for its own properties only. It calls super class method to perform normal human’s behaviours, instead of repeating the code.
* `Farmer` class is extensible, extra behaviours can be implemented further.

#### A new class: Crop

* A new type of `Ground` which can be grown to food.

1. This class extends `Ground` class.
1. It has capability *UNRIPE* or *RIPEN*.
1. It has an overridden *tick()* method.
	* The display character will be ‘C’ after 20 turns. It has capability *RIPEN*.
1. It has an overridden *changeGroundStatus()* method which is implemented in `GroundInterface`.
	* It will increase the turn of the crop to ripe by 10 turns. 

#### Design reason

* A new method implemented in `GroundInterface` to avoid downcasting. It makes the child classes more extensible.

#### A new class: Food

* A new type of item which can eaten by player and humans to heal damage. 

1. This class extends `Item` class.
1. It has capability *EDIBLE* and an integer represents the food value.
1. In the constructor, the food value is generated randomly between 1 and 14. 
1. It has an accessor *getFoodValue()* to get the food value.

#### Design reason 

* Capability is used to make code implementation easier. 
* Dependencies are minimised as much as possible.  

#### A new class: SowBehaviour

* A new type of behaviour of `Farmer` so that `Farmer` can sow a crop. 

1. This class extends `Action` class and implements `Behaviour` class. 
1. It has an overridden *getAction()* method.
    * It has a boolean to make the probability of sowing a crop to be 1/3.
    * It has a list of exits where the actor can take, these exits are the adjacent location of the actor.
    * A for loop in the exits list and check the capability of the ground of the location.
    * If the ground has capability *SOIL*, which means it is dirt, the ground will be set to `Crop`. 
1. It has another overridden *execute()* method.
	* This method will return a string about the description of the sow action with actor’s name.

#### A new class: FertiliseBehaviour

* A new type of behaviour of `Farmer` so that `Farmer` can fertilise a crop.

1. This class extends `Action` class and implements `Behaviour` class.
1. It has an overridden *getAction()* method.
    * It has a variable to represents current location of the actor.
    * There is an if statement to check whether the ground of the location has capability *UNRIPE* . If yes, it will call the *changeGroundStatus()* method to perform fertilisation.
1. It has another overridden *execute()* method.
	* This method will return a string about the description of the fertilise action with actor’s name.

#### Design reason

* Variable for the location is declared in the tightest possible scope. 

#### A new class: HarvestBehaviour

* A new type of behaviour of `Farmer` so that they can harvest a crop.

1. This class implements `Behaviour` class.
2. It has an overridden *getAction()* method.
    * It has a string local variable represents the food name. Food name is generated differently to make game more interesting.
    * It has a list of exits where the actor can take, these exits are the current and adjacent location of the actor.
    * A for loop in the exits list and check the capability of the ground of the location.
    * If the ground has capability *RIPEN*, which means the crop can be harvested. 
    * It will return a new HarvestAction with food name and the location as parameter.


#### Design reason

* Variable for the food name and list of exits is declared in the tightest possible scope. 

#### A new class: EatBehaviour

* A new type of behaviour of `Farmer` and `Human` so that they can perform an eat action.

1. This class implements `Behaviour` class.
1. It has an overridden *getAction()* method.
    * An if statement to check whether the actor has capability *DAMAGED*.
    * A for loop through the inventory of the actor and get item with capability *EDIBLE*.
    * It will return a new EatAction with particular food item. 

#### Design reason

* Reduce dependencies as much as possible. 
* An action will be performed only when the actor is damaged. 

#### A new class: EatAction

* A new type of action so that actor can eat and heal.

1. This class extends `Action` class.
1. It has an instance variable `Food`. 
1. It has an overridden *execute()* method.
    * The actor will be healed with the food value by calling *heal()* method. 
    * Method *removeItemFromInventory()* is called to remove food from inventory after eating.
    * This method will return a string about the description of the eat action with actor’s name and amount of damage healed.
1. It has an overridden *menuDescription()* method.
    * This returns a string describing the action to be displayed in the menu.

#### Design reason 

* Simple implementation as it just perform healing and removing item. 
* Player is able to choose when to perform the eating and healing action.

#### A new class: HarvestAction

* A new type of action so that `Player` can harvest a crop.

1. This class extends `Action` class.
1. It has an `Exit` instance variable as the location of ripen crop.
1. It has another overridden *execute()* method.
    * It will set the ground of the exit to dirt, and add a `Food` item onto the ground.
    * This method will return a string about the description of the harvest action with actor’s name.

#### Modify class: Player
1. In the *playTurn()* method, a new `FertiliseBehaviour` will be added into actions if the action performed does not return null.
1. A for loop loops through the inventory of `Player` to get item's capability.
1. A new `EatAction` will be added into actions if item has capability `EDIBLE` and `Player` is damaged

#### **Disadvantages**
* Adding new classes will increase the dependencies. For example, a `Crop` is created in `Sowbehaviour` class. 
* `Farmer` will fertilise the crop for multiple times, player can get heal easily.

# **Assignment 3 - Team StormBreaker**

## **Going to Town**

### **Design choice**

#### A new class: Vehicle

* A new type of `Item` which can move `Player` between maps. 

1. This class extends the `Item` class. 
1. It has a `MoveActorAction` as its allowable actions. 
1. It has a *moveActor()* method.
	* A list of multiple `Exit` represent the adjacent locations of the destination.
	* A for loop loops through the exits list to find an empty location without any actors.
	
#### A new abstract class: Building
* A new type of `Ground` which represents building in the game system.

#### Design reason

* Simple implementation, dependencies are reduced as much as possible as only a new class created.
* Find adjacent locations instead of a specific destination to prevent an actor exists on that location, thus can prevent error occur. 

## **New weapons and ammunition**
In `Player`'s *playTurn()*, if there is a Shotgun or SniperRifle in the inventory, a new `ChooseWeaponAction` will be added in the actions.
`ChooseWeaponAction` allows Player to select the ranged weapon available and then attacks target or fires at a specific direction.

### **Design choice**

#### A new class: Ammunition

* A new type of `Item` represents bullets of guns. 

#### A new class: AmmunitionBox

* A new type of `PortableItem` which contains 5 `Ammunition`.

1. This class extends the `PortableItem` class.
1. It has an array list of `Ammunition`. 
1. In the constructor, instances of `Ammunition` are added into list.
1. `AmmunitionBox` also has the capability *BULLET*.
1. It has an accessor *getAmmunition* to get the array list.

#### A new class: Depot

* A new sub class from `Building` to store ammunition.

1. This class extends from abstract `Building` class.
1. It has an instance variable `ArrayList<Ammunition>` stores ammunition.
1. Each Depot initially contains 5 boxes of `Ammunition`.
1. In the overridden *tick()* method, if `Player` is standing on `Depot`, we use *location.addItem()* to allow `Player` to 
pick up `Ammunition`.
1. The storage will be refilled once the `ArrayList<Ammunition>` is empty.

#### A new abstract class: RangedWeapon 

* A new type of `WeaponItem` which can do area damage and hit more than one target.

1. This class extends the `WeaponItem` class.
1. This class has a new capability *RANGED_WEAPON* in constructor. 
1. Has an accessor to get weapon’s name. 
1. An abstract method *subMenu()* to display submenu.
1. A method *getAmountOfBullet*. 
1. A method *shoot()* to remove a bullet after shooting once. 
1. A method *loadAmmunition()* to load bullets from ammunition box into the weapon. 

#### Design reason

* This class is responsible for its own properties, which is ranged weapon. It is extensible and can be inherited by other classes with similar properties. 

## **New weapon: Shotgun**

#### A new class: Shotgun

* A new type of `RangedWeapon` which does area damage. It can also be used as melee weapon without ammunitions. 

1. This class extends the `RangedWeapon` class. 
1. This weapon has 20 damages.
1. It has an overridden *subMenu()* method.
	* This method shows a submenu with 8 different directions to be selected by player. 
	* A new instance of `Actions` is created, it contains 8 `ShotDirectionAction`.
	* `Menu` *showMenu()* method is called and returned. 

#### Design reason

1. Things are declared in the tightest possible scope. For example, `ShotDirectionAction` is declared while being added into actions. 

#### A new class: ShotDirectionAction

* A new type of `Action` so that `Player` can use shotgun to shoot in a direction.

1. This class extends `Action` class.
1. It has an instance variable `RangedWeapon` represents shotgun and a string represents direction.
1. It has an overridden *execute()* method.
	* An instance of `Actions` contains actions to damage actor. 
	* Check if the direction is diagonal direction. 
	* Set `NumberRange` according to the direction.
	* Loops through `NumberRange` and get the location. If the location contains an `Actor`, an instance of `RangedAttackAction` will be added. 
	
1. It has an overridden *menuDescription()* method.
	* This method shows a message in the menu, this enables `Player` to choose which direction to shoot. 
	
#### A new class: ChooseWeaponAction

* A new type of `Action` allows `Player` to select any `RangedWeapon` available.

1. This class extends from `Action` class.
1. It has two instance variables *display* and *weapon* which assigned to the parameters from the constructor.
1. In the overridden *execute()* method, if `Actor`'s inventory contains `Ammunition`, call *weapon.loadAmmunition()* to add bullets into the weapon.
1. Call *weapon.subMenu()* to execute next subMenu.
1. otherwise, return a warning message if there is no `Ammunition` in inventory or the weapon.

## **New weapon: SniperRifle**
When Player selects sniper, a submenu is displayed to allow Player to choose a target. After selecting which target to shoot, 
another submenu will be displayed with the options: Spend a round aiming at the target, or shoot the target. 
- Given that the aiming option is chosen, in the following turn player can choose to attack the target or do other actions.
- If player choose to continue aiming, concentration will be increased, shooting the target at this round, the damage will be doubled. 
- If player choose to aim for two rounds and shoot at the third round, the sniper will instakill the target. 
- Otherwise, if player choose to do other actions or get hurt in the middle of aiming, the concentration will be broken.

#### A new class: SniperRifle

* A sub class of `RangedWeapon` which does more damage if spend more time aiming at the target. It can also be used as melee weapon without ammunition.

1. This class extends from `RangedWeapon` class.
1. Initial damage for this weapon is 20.
1. It has an overridden *subMenu()* method.
    * This method calls a private method *scanTarget()* which returns an `ArrayList<Location>` contains all the targets available within the range. 
    * Run a loop to create new `ChooseTargetAction` for each target, all the ChooseTargetActions are added into an `Actions` variable.
    * Return *menu.showMenu()* to allow Player to choose the target.
    
#### A new class: ChooseTargetAction

* A sub class of `Action` allows Player to choose targets.

1. This class extends from `Action` class.
1. It has four instance variables: `RangedWeapon`, `Menu`, `Actor` represents target and `AimAction`.
1. In the overridden *execute()* method, it adds `AimAction` instance into the `Actions` variable if the concentration at the target is lower than 2,
it also adds `RangedAttackAction` into the `Actions` variable. It will call *menu.showMenu* which allows Player to choose either aiming at the target or
shoot the target. 
1. It has a public method called *lastActionIsAim* called in `Player` class to return true if player's last action is aiming.

#### A new class: AimAction

* A sub class of `Action` which counts the concentration of the Player on a selected target.

1. This class extends from `Action` class.
1. It has two instance variables: `Actor` represents target and `int` concentration.
1. It increases the concentration in the overridden *execute()* method.
1. It has two public methods which are *resetConcentration()* to reset the concentration to zero, and *getConcentration()*.

#### Modifying *hurt()* in `Player` class

* When calling this method, player's concentration will become zero.

#### Design reason

1. This design decision is following the principle **Classes should be responsible for their own properties.** 
1. Each class has its own properties and thus decreased the dependencies.

## **Mambo Marie**

### **Design Choice**

#### A new class: MamboMarie

* A sub class of `ZombieActor` which represents a new character in this game.

1. This class extends from `ZombieActor`.
1. It has ZombieCapability.UNDEAD.
1. It has an instance variable `Behaviour[]` which contains `ChantBehaviour`, `WanderBehaviour` and `ScreamBehaviour`.

#### A new class: ChantBehaviour

* A sub class of `Actions` which allows Mambo Marie to chant and summon five new `Zombie`. 

1. This class extends from `Action` and implements `Behaviour`.
1. It has an instance variable `int` turn to count the passage of time.
1. In *getAction()* method, it will return this class every 10 turns. Otherwise, return null.
1. In the overridden *execute()* method, we use a for loop to generate five new `Zombie`, the coordinate is randomly generated 
and it will check if the `Location` contains an `Actor`. If there isn't, call *map.addActor()* to add the `Zombie` into the game.

#### A new class: NewWorld

* A sub class of `World` allows us to extend the game functionality.

1. This class extends from `World`.
1. This class has two instance variables `MamboMarie` and `int` turn.
1. In the overridden *run()* method, after processing all the actors and tick all the item on maps, check if the Mambo Marie
exists on the compound map. If Mambo Marie currently not on the map and passed the 5% chance, she will be added to the edge of the map.
1. In the same method, checks if the turn exceeds 30 and Mambo Marie still on the map, remove her from the map.

#### Design reason

1. We think it is better to make Mambo Marie a singleton in `NewWorld` because if every time she returns as a new object with full health point,
it will harder for the player to kill her and win this game.
1. The new class `NewWorld` extends from `World` so it minimises the dependencies that cross encapsulation boundaries.


## **Bonus Features**

## **Zombie self-exploded after killed (1 mark)**

#### New method in `Zombie` class : *turnIntoBomb()*

1. `Zombie` can cause 15 damages if it turns into bomb. 
1. Generate a list of exits to find adjacent locations of the `Zombie`. 
1. `Actor` exists at the adjacent location will be hurt. 
1. The probability of `Zombie` self-exploding is 0.1, and it is calculated in the *damage()* method in `Zombie`
class.

## **Hospitals and Doctors (2 marks)**

#### A new class: Doctor

* A new type of `Human` which can self-heal and inject vaccines into other `Human`.

1. This class extends `Human` class and has a new behaviour `CureBehaviour`.
1. It has an overridden *playTurn()* method. 
    * It has 50% chance to heal by 15-30 points randomly. 
    * A for loop in the behaviours list and get action for the behaviour. 
    * If the action does not return null, the actor will perform the action
    * It will perform `Human` normal behaviours if no farmer’s behaviours available.

#### Design reason

* `Doctor` is responsible for its own behaviour and responsibility, which is injecting vaccine. 
* Use super keyword to call parent class *playTurn()* method to avoid repeated code and achieve DRY principle.  
    
#### A new class: CureBehaviour

* A new type of behaviour of `Doctor` so that it can perform an injecting vaccine action.

1. This class implements `Behaviour` class.
1. It has an overridden *getAction()* method.
    * Generate a list of exits to get adjacent locations of the `Doctor`.
    * Check if that location contains an actor and that actor is `Human`.
    * Perform an `InjectVaccineAction` if above statements are true.

#### Design reason

* Reduce dependencies as much as possible. 
* Keep the method simple and easy to understand. 

#### A new class: InjectVaccineAction

* A new type of `Action` so that `Doctor` can inject vaccines into `Human` and make they immune.

1. This class extends `Action` class.
1. It has an instance variable `Actor` as the patient to be injected. 
1. It has an overridden *execute()* method.
    * The patient will be added a new capability *IMMUNE*. 
1. It has an overridden *menuDescription()* method.
    * This returns an empty string as it is not needed.
    
#### New code implementation in `Human` class.

1. An integer to record the round of immune capability. 
1. When it reaches 8 turns, the capability *IMMUNE* will be removed from the `Human`. 

#### New code implementation in `AttackAction` class.

1. In the *execute()* method, the target capability and the verb of weapon will be checked. 
1. If it is zombie bite weapon and the target has capability *IMMUNE*, no attack action happen. 

#### Design reason

1. `InjectVaccineAction` class is responsible for its own responsibility and the implementation is simple. 
1. No duplicated code occur. 

#### A new class: Hospital

* A sub class from *Building* represents a Hospital in the game system.

1. This class extends from abstract `Building` class.
1. It has a class variable `ArrayList<String> doctorsName` storing all the available doctor's name to be used for the game.
1. In the overridden method *tick()*, a new `Doctor` will be created every 10 turns. Maximum 10 doctors for each `Hospital`.
1. If a `Human` is standing on the `Hospital`'s location, heal the `Human` 50 health points.








