### version scheme
`overhaul.feature.hotfix`
- **overhaul** number is increased when significant parts of code are replaced or introduced
- **feature** number is increased when a new feature is added, and resets after an overhaul
- **hotfix** number is increased when something minor is tweaked or a bug is fixed, and resets after a new feature

this mimics the version scheme used by minecraft between 2011 and 2024.

# v1.1.4
###### Apr 1, 2026
- fixed compatibility with sodium 0.8.7 in mc1.21.11
- fixed unreadable config inputs when caramelchat is installed
- fixed localization breaking decimal number inputs
- fixed number inputs not accepting negative values
- Added small puddle edge model variant

# v1.1.3
###### Mar 24, 2026
- fixed sodium & iris integration to function in minecraft 26.1

# v1.1.2
###### Mar 21, 2026
- fixed crash on neoforge when sodium is loaded without iris
- fixed shader pack integration not functioning on 1.21.1

# v1.1.1
###### Mar 21, 2026
- added minecraft 26.1 support (github build only; not published yet)
- fixed server crash caused by puddle block updates running client code on the server
- fixed iris shader packs sometimes not applying water shaders to puddles

# v1.1.0
###### Mar 11, 2026
- fixed lava creating water puddles
- Added footstep splash sounds

# v1.0.1
###### Feb 5, 2026
- added config toggles for all features
- fixed crash on 1.20.1 with sodium
- fixed cauldron desyncing on break when on servers without the mod
- fixed mod not loading on the server


# v1.0
###### Jan 31, 2026
- first release featuring puddles:
  - during rain
  - below dripping blocks
  - left behind by flowing water
  - left behind by splash potions
  - left behind by broken filled cauldrons
  - left behind by melted snow
- puddles will combine to form floods at river/sea level
- shader pack support via sodium
- rain puddle placement editable by resource packs
- config screen openable by mod list menu or `/puddleflood`
  - set puddle coverage amount for rain and during storms
  - set the range puddles spawn in around the player
  - set speed at which puddles collect and evaporate
  - disable shaderpack water on puddles
  