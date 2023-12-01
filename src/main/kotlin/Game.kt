import kotlin.random.Random

    var bossHealth = 1500
    var bossDamage = 60
    var bossDefence: String? = null
    var heroesHealth = intArrayOf(270, 260, 250, 300, 400, 280, 320, 310)
    var heroesDamage = intArrayOf(10, 15, 20, 0, 5, 25, 10, 20)
    var heroesAttackType = arrayOf("Physical", "Magical", "Kinetic", "Medic", "Lucky", "Thor")
    var roundNumber = 0
    var message = ""

    fun main() {
        printStatistics()
        while (!isGameFinished()) {
            playRound()
        }
    }

    private fun playRound() {
        roundNumber++
        message = ""
        chooseBossDefence()
        bossHits()
        heroesHit()
        treatHeroes()
        printStatistics()
    }

    private fun chooseBossDefence() {
        val randomIndex = Random.nextInt(heroesAttackType.size)
        bossDefence = heroesAttackType[randomIndex]
    }

    private fun bossHits() {
        val lucky = Random.nextBoolean()
        val didThorStopBoss = Random.nextBoolean()
        for (i in heroesHealth.indices) {
            if (heroesHealth[i] > 0 && !didThorStopBoss) {
                if (heroesHealth[i] - bossDamage < 0 || (heroesHealth[i] - ((bossDamage / 5) * (heroesAttackType.size - 1) + bossDamage)) < 0) {
                    heroesHealth[i] = 0
                } else if (heroesAttackType[i] == "Golem") {
                    heroesHealth[i] = heroesHealth[i] - ((bossDamage / 5) * (heroesAttackType.size - 1) + bossDamage)
                } else if (lucky && heroesAttackType[i] == "Lucky") {
                    heroesHealth[i] = heroesHealth[i]
                } else if (heroesAttackType[i] == "Berserk") {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage / 10) * 9
                } else {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage / 5) * 4
                }
            }
        }
        println(didThorStopBoss)
    }

    private fun heroesHit() {
        for (i in heroesDamage.indices) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                var damage = heroesDamage[i]
                if (bossDefence == "Medic") {
                    continue
                }
                if (heroesAttackType[i] == bossDefence) {
                    val coefficient = (2..10).random()
                    damage *= coefficient
                    message = "Critical damage: $damage"
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0
                } else if (heroesAttackType[i] == "Berserk") {
                    bossHealth -= (bossDamage / 10 + damage)
                } else {
                    bossHealth -= damage
                }
            }
        }
    }

    private fun treatHeroes() {
        for (i in heroesHealth.indices) {
            if (heroesHealth[i] in 1..99 && heroesHealth[3] > 0) {
                heroesHealth[i] += 10
                break
            }
        }
    }

    private fun isGameFinished(): Boolean {
        if (bossHealth <= 0) {
            println("Heroes won!!!")
            return true
        }
        var allHeroesDead = true
        for (element in heroesHealth) {
            if (element > 0) {
                allHeroesDead = false
                break
            }
        }
        if (allHeroesDead) {
            println("Boss won!!!")
        }
        return allHeroesDead
    }

    private fun printStatistics() {
        println("ROUND $roundNumber ----------")
        println(
            "Boss health: " + bossHealth + " damage: " + bossDamage
                    + " defence: " + if (bossDefence == null) "No defence" else bossDefence
        )
        for (i in heroesHealth.indices) {
            println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i])
        }
        println(message)
    }

