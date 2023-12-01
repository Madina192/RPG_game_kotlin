import kotlin.random.Random

fun main() {
    println("Welcome to the Adventure RPG!")

    // Player creation
    println("Create your character:")
    val playerName = createPlayer()

    // Introduction
    println("\n$playerName, you find yourself in a mystical land. Your adventure begins!\n")

    // Game loop
    var isGameOver = false
    var playerHealth = 50

    while (!isGameOver) {
        println("Options:")
        println("1. Explore")
        println("2. Rest")
        println("3. Quit")

        print("Choose your action: ")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                val enemyName = generateRandomEnemy()
                println("\nYou encounter a $enemyName!")
                fight(playerHealth)
            }

            2 -> {
                playerHealth = rest(playerHealth)
                println("\nYou take a rest and feel refreshed!")
            }

            3 -> {
                println("Thanks for playing!")
                isGameOver = true
            }

            else -> println("Invalid choice. Try again.")
        }

        // Check if player is still alive
        if (playerHealth <= 0) {
            println("Game over! $playerName has fallen in battle.")
            isGameOver = true
        }
    }
}

fun createPlayer(): String {
    print("Enter your character's name: ")
    return readLine() ?: "Player1"
}

fun generateRandomEnemy(): String {
    val enemyNames = listOf("Goblin", "Skeleton", "Orc", "Dragon")
    return enemyNames.random()
}

fun rest(currentHealth: Int): Int {
    val restoredHealth = currentHealth + Random.nextInt(5, 15)
    println("Health restored. Current health: $restoredHealth")
    return restoredHealth
}

fun fight(playerHealth: Int) {
    var currentHealth = playerHealth
    println("Battle starts!")

    while (currentHealth > 0) {
        print("\nOptions:")
        println("1. Attack")
        println("2. Run")

        print("Choose your action: ")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                val enemyDamage = Random.nextInt(3, 10)

                println("You attack and deal $enemyDamage damage!")

                currentHealth -= enemyDamage
                println("Current health: $currentHealth")

                if (currentHealth <= 0) {
                    println("You've been defeated!")
                }
            }

            2 -> {
                println("You run away from the battle.")
                break
            }

            else -> println("Invalid choice. Try again.")
        }
    }
}
