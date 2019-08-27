

fun main(args: Array<String>) {

    var fineDust = 100
    var isFresh = false
    var isChina = false



    getCurrentSkyColor(isFresh, fineDust, isChina)



    println(airPollutionSate(fineDust))
    getCurrentWeatherState()

    println({
        val local = "seoul"
        "$local 의 현재 날씨는"
    })
}

private fun getCurrentSkyColor(isFresh: Boolean, fineDust: Int, isChina: Boolean): String {
    val skyVisible = isFresh && fineDust < 30 || !isChina
    val skyColor = if (skyVisible) "Blue" else "Yellow"

    return skyColor
}

// 단일 표현식을 사용하여 함수를 변경
private fun airPollutionSate(fineDust: Int) =
    when (fineDust) {
        10 -> "최고"
        20 -> "양호"
        30 -> "나쁨"
        100 -> "최악"
        in 101..130 -> "외출금지"
        else -> "측정불가"
    }

private fun getCurrentWeatherState(weatherState: String = "맑음") {
    println("날씨 $weatherState")
}

