# Fire Bending

This is a Fabric Minecraft server-side mod that allows server administrators to control with more detail the behaviour of fire ticking.

## Fire Sources

Fire blocks produced by lightning, lava, or flint-and-steel are now their own fire blocks. The mod adds 3 new gamerules that let you configure the fire tick delay of each fire source individually:

- `/gamerule lightningFireTickDelay <integer-argument>`
- `/gamerule lavaFireTickDelay <integer-argument>`
- `/gamerule flintAndSteelFireTickDelay <integer-argument>`

The integer argument supplied to the gamerule, will modify the fire tick delay of the corresponding fire source, using the formula `tickDelay = argument + RandomNumber(0, 10)`. Additionally, setting this value to `-1` disables fire ticking for that fire source.
All of these gamerules have the default value of `30`.

## Tick fire based on nearby players

Furthermore, the mod adds two new gamerules:

- `/gamerule doTickFireOnlyWhenPlayerNearby <boolean-argument>`: This gamerule toggles whether fire blocks should tick only when a player is within a certain user-configurable distance. This is `false` by default.
- `/gamerule tickFireWhenPlayerNearbyDistance <integer-argument>` This gamerule sets the distance at which a player has to be in order for fire to tick, if the `doTickFireOnlyWhenPlayerNearby` gamerule is enabled. This is `30` by default, and the minimum value is `1`.

## Flammable blocks blacklist

Finally, the mod offers a configuration file, found at `./config/fire-bender-config.json`, where the user can configure a list for blocks that shouldn't be flammable.
You can add block identifier entries to the `flammable_blocks_blacklist` array, and then reload the config using the command `/fire-bender reload_config`.

```json
{
  "flammable_blocks_blacklist": [
    "minecraft:my_block",
    "modid:other_block"
  ]
}
```

## Support

If you would like to report a bug, or make a suggestion, you can do so via the mod's [issue tracker](https://github.com/ArkoSammy12/fire-bending/issues), or join my [Discord server](https://discord.gg/wScNgcvJ3y). 

