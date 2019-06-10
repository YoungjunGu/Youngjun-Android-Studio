fun main(args: Array<String>) {

    var fineDust = 100
    var isFresh = false
    var isChina = false


    getCurrentSkyColor(isFresh, fineDust, isChina)


    println(airPollutionSate(fineDust))
    getCurrentWeatherState()
}

private fun getCurrentSkyColor(isFresh: Boolean, fineDust: Int, isChina: Boolean): String {
    val skyVisible = isFresh && fineDust < 30 || !isChina
    val skyColor = if (skyVisible) "Blue" else "Yellow"

    return skyColor
}

private fun airPollutionSate(fineDust: Int): String {
    val airCondition = when (fineDust) {
        10 -> "최고"
        20 -> "양호"
        30 -> "나쁨"
        100 -> "최악"
        in 101..130 -> "외출금지"
        else -> "측정불가"
    }
    return airCondition
}

private fun getCurrentWeatherState(weatherState: String = "맑음") {
    println("날씨 $weatherState")
}


