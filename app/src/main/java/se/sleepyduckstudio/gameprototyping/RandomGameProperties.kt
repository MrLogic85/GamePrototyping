package se.sleepyduckstudio.gameprototyping

import java.util.*

val randon = Random()

public enum class Property {
    Perspective,
    Theme,
    Setting,
    Type,
    Feature
}

private val emptyList = listOf("", "", "")

// Make a THEME PERSPECTIVE TYPE game
private val theme = listOf(
        "steampunk",
        "dieselpunk",
        "postmodern",
        "post apocalyptic",
        "dystopic",
        "world war I/II",
        "futuristic",
        "wild west",
        "medieval",
        "new world"
)

private val perspective = listOf(
        "first person",
        "third person",
        "isometric",
        "top down",
        "tile based",
        "text based",
        "side scrolling")


private val type = listOf(
        "shooter",
        "strategy",
        "adventure",
        "puzzle",
        "racer",
        "base defence",
        "farming sim",
        "platform",
        "fighting",
        "rhythm",
        "metroidvania",
        "educational",
        "hack and slash",
        "life sim",
        "sports",
        "dating sim"
)

// Set XXX
private val setting = listOf(
        "on earth",
        "in space",
        "in a fantasy world",
        "in a cartoon world",
        "in a manga universe",
        "as a tabletop game",
        "underwater",
        "underground"
)

// Features XXX and
private val feature = listOf(
        "block placement",
        "crafting",
        "economy",
        "physical materials",
        "drilling",
        "card management",
        "creative mode",
        "dice rolling",
        "physics",
        "idle gameplay",
        "a persistent world",
        "swarms",
        "survivalism",
        "role playing",
        "horror elements",
        "roguelike gameplay",
        "a sandbox environment",
        "turn based tactics",
        "logical thinking",
        "a open world",
        "a interactive world",
        "online multiplayer",
        "city management",
        "flying",
        "swimming",
        "simulations",
        "4X",
        "a arena",
        "programming"
)

private val properties = mapOf(
        Pair(Property.Perspective, perspective),
        Pair(Property.Setting, setting),
        Pair(Property.Type, type),
        Pair(Property.Theme, theme),
        Pair(Property.Feature, feature)
)

public fun getRandomPropertySet(property: Property, size: Int) = properties
        .getOrDefault(property, emptyList).shuffled()
        .subList(0, size)

public fun getCompleteRandomPropertySet(size: Int) = Property.values().map {
    getRandomPropertySet(it,
            size)
}
