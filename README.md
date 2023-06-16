# Teleios

Welcome to the Teleios Minecraft Plugin.
This is a small project I code on my own, I will update it sporadically. The design philosophy is: 
- features and commands should be easy to understand and user friendly
- all functionality should be optional and precisely selectable

<br/>

## Current functionality
Teleios is set up in segments which can be turned on and off individually. Some functionality can be managed more precisely 
than switching the whole segment on or off. What can be switched on or off is marked with "*" in the following list.

<br/>

### Core segment (always active)
- /manageteleios (/mtl) command: opens an inventory to manage all plugin functionality. When you add the plugin to your server, 
  everything will be deactivated standardly.
- /setdebuglevel (/sdl) command: lets you show and change the debug level affecting the plugin outputs. You can set it from 0 - 10: <br/>
  0 = no messages <br/>
  1 = plugin start/end messages will be written to the terminal <br/>
  2 = plugin start/end messages will be written to the terminal & in-game chat <br/>
  3 = plugin start/end + fail messages will be written to the terminal <br/>
  4 = plugin start/end + fail messages will be written to the terminal & in-game chat <br/>
  5 = plugin start/end + fail + warning messages will be written to the terminal <br/>
  6 = plugin start/end + fail + warning messages will be written to the terminal & in-game chat <br/>
  7 = plugin start/end + fail + warning + info messages will be written to the terminal <br/>
  8 = plugin start/end + fail + warning + info messages will be written to the terminal & in-game chat <br/>
  9 = plugin start/end + fail + warning + info + success messages will be written to the terminal <br/>
  10 = plugin start/end + fail + warning + info + success messages will be written to the terminal & in-game chat <br/>
  
  Fail messages print a short message and the exception stack trace on encountered exception. <br/>
  Warning messages inform the console that something unexpected happened or action is necessary. <br/>
  Exit messages (v1.3WIP) inform the console that and how a method finished.

<br/>

### AdminFeatures segment (*)
The AdminFeatures segment adds convenient admin-only commands.
- /chatclear (/cc): "clears" the chat by printing 60 blank lines
- /damage (/d): damages the sender or another player
- /gma: sets you or another player in adventure mode
- /gmc: sets you or another player in creative mode
- /gms: sets you or another player in survival mode
- /gmsp: sets you or another player in spectator mode
- /heal (/h): heals you or another player
- /inventories (/invs): lets you create, open and remove inventories with an amount of rows lower than 7
- /joinmessage (/jm): lets you enable or disable the custom join message
- /mute (/m): lets you mute players, restricting them from writing in chat
- /openinventory (/openinv, /oi): opens the specified player's inventory
- /tphere (/tph): teleports the specified player to you
- /unmute (/um): lets you unmute players which have been muted with /mute
- /warppoint (/wp): lets you set up a "warppoint" at your current location that players can teleport to. In comparison to teleporters from <br/> 
  the BetterGameplay segment, warppoints are not tied to a block and warping to them is free. To use the /warppoint command, the <br/> 
  AdminFeature segment must be active. To use the /warp command, the BetterGameplay segment must be active. <br/> 
  Warppoints can be listed alongside teleporters with "/warp list".

<br/>

### BetterGameplay segment (*)
The BetterGameplay segment adds features for a better survival experience. Most commands from this segment can be used by everyone if <br/> 
the respective command has been activated.
- /enderchest (/ec) (*): opens your enderchest
- Teleporters (*): you can craft teleporters with the scheme "ESE", "SBS", "ESE" where E = ender pearl, S = sandstone and B = blaze powder. <br/>
  To set up the teleporter, hold it in your hand and use "/configureteleporter *TeleporterName*". The specified name must be unique. <br/> 
  To list all available (warppoints and) teleporters, use "/warp list". Warping to a teleporter costs ender pearls which are stored in your <br/> 
  warp pouch. To put ender pearls in your warp pouch, have some in your inventory and use the command "/warppouch put *Amount*". <br/> 
  This will move the specified amount of ender pearls out of your inventory and into your warp pouch. To see the current amount of <br/> 
  ender pearls in your warp pouch, use "/warppouch view" or simply "/warppouch". You can warp to a teleporter in your dimension by <br/> 
  using "/warp *TeleporterName*". This will cost some ender pearls from your warp pouch. To remove a teleporter, click on it to call <br/> 
  an interactive inventory. Click on the green block to pick up the teleporter.
  - /configureteleporter (/ctp): lets you change the name of a teleporter you hold in your hand
  - /setblocksperpearl (/setbpp, /sbpp): lets admins change the amount of blocks you can travel per ender pearl when teleporting
  - /warp (/w): used to list all warppoints and teleporters and to warp you to them
  - /warppouch (/wpp): lets you see and deposit ender pearls in your warp pouch

<br/>

### PassiveSkills segment (*)
The PassiveSkills segment is currently WIP and doesn't work yet.

<br/>

### WorldMaster segment (*)
The WorldMaster segment is not implemented yet.

<br/>

## Version updates
This is a history of the past changes coming with the respective versions. "WIP" (Work In Progress) versions are not available yet.

### v1.3WIP
**Server owners should adjust their DebugLevel arguments.**
- this version is native to Minecraft 1.20.1
- the DebugLevel values and their effect have been changed from <br/>
  0 = no messages <br/>
  1 = plugin start/end messages will be written to the terminal <br/>
  2 = plugin start/end messages will be written to the terminal & in-game chat <br/>
  3 = plugin start/end + fail messages will be written to the terminal <br/>
  4 = plugin start/end + fail messages will be written to the terminal & in-game chat <br/>
  5 = plugin start/end + fail + warning messages will be written to the terminal <br/>
  6 = plugin start/end + fail + warning messages will be written to the terminal & in-game chat <br/>
  7 = plugin start/end + fail + warning + info messages will be written to the terminal <br/>
  8 = plugin start/end + fail + warning + info messages will be written to the terminal & in-game chat <br/>
  9 = plugin start/end + fail + warning + info + success messages will be written to the terminal <br/>
  10 = plugin start/end + fail + warning + info + success messages will be written to the terminal & in-game chat <br/>
  to <br/>
  0 = no messages <br/>
  1 = plugin start/end messages will be written to the terminal <br/>
  2 = plugin start/end messages will be written to the terminal & in-game chat <br/>
  3 = plugin start/end + fail messages will be written to the terminal <br/>
  4 = plugin start/end + fail messages will be written to the terminal & in-game chat <br/>
  5 = plugin start/end + fail + warning messages will be written to the terminal <br/>
  6 = plugin start/end + fail + warning messages will be written to the terminal & in-game chat <br/>
  7 = plugin start/end + fail + warning + exit messages will be written to the terminal <br/>
  8 = plugin start/end + fail + warning + exit messages will be written to the terminal & in-game chat <br/>
  Please adjust your DebugLevel accordingly.

### v1.2
**Server owners should adjust their SetBlocksPerPearl and DebugLevel arguments.**
- this version is native to Minecraft 1.19
- when warping to teleporters, the distance is now computed as a straight line to the teleporter instead of <br/> 
  the added difference of the x, y and z coordinates of you and the teleporter. This lowers the teleportation cost <br/> 
  by about 42% of the previous value. I advice you to adjust your blocksperpearl argument with /setblocksperpearl.
- the custom join message now only displays if the join message is enabled AND the /joinmessage command is active
- "/joinmessage" will now tell you whether the custom join message is enabled or disabled
- "/warp" will now list the available warppoints and teleporters. "/warp list" will now ignore any added arguments
- "/inventories" will now list the available inventories
- non-admins can now use "/setblocksperpearl" to see the BlockPerPearl argument
- non-admins can now use "/setdebuglevel" to see the DebugLevel argument
- warppoint and teleporter names are now case insensitive when warping
- when a player places a teleporter, their yaw is saved to the teleporter and applied when teleporting
- only left clicks on teleporters open the "Pick up teleporter?" inventory now
- the DebugLevel values and their effect have been changed from <br/>
  0 = plugin start/end messages will be written to the terminal, <br/>
  1 = plugin start/end + error messages will be written to the terminal, <br/>
  2 = plugin start/end + error + method success + method skipped messages will be written to the terminal, <br/>
  3 = plugin start/end + error + method success + method skipped messages will be written to the terminal and the in-game chat <br/>
  to <br/>
  0 = no messages <br/>
  1 = plugin start/end messages will be written to the terminal <br/>
  2 = plugin start/end messages will be written to the terminal & in-game chat <br/>
  3 = plugin start/end + fail messages will be written to the terminal <br/>
  4 = plugin start/end + fail messages will be written to the terminal & in-game chat <br/>
  5 = plugin start/end + fail + warning messages will be written to the terminal <br/>
  6 = plugin start/end + fail + warning messages will be written to the terminal & in-game chat <br/>
  7 = plugin start/end + fail + warning + info messages will be written to the terminal <br/>
  8 = plugin start/end + fail + warning + info messages will be written to the terminal & in-game chat <br/>
  9 = plugin start/end + fail + warning + info + success messages will be written to the terminal <br/>
  10 = plugin start/end + fail + warning + info + success messages will be written to the terminal & in-game chat <br/>
  Please adjust your DebugLevel accordingly.
- general optimisation
