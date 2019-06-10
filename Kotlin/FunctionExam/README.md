# Kotlin 의 함수

기존의 다른 언어의 함수와 다른 특징, 작성시 유의 사항 등을 유념하면서 정리하였습니다.  
  
</br>

## 함수의 구조  


```kotlin
visibilityModifier fun functionName(parameter1: DataType,,,): return Type { }
```  
- example  


```kotlin
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
```

## 함수 리팩터링

```kotlin
val skyVisible = isFresh && fineDUst < 30 || !isChina 
val skyColor = if (skyVisible) "Blue" else "Yellow" 
```

위의 두 변수들을 함수로 리팩터링을 진행한다.  

```kotlin
private fun getCurrentSkyColor(isFresh: Boolean, fineDust: Int, isChina: Boolean): String {
    val skyVisible = isFresh && fineDust < 30 || !isChina
    val skyColor = if (skyVisible) "Blue" else "Yellow"
    
    return skyColor
}
```  
</br>

### 함수 기본 인자  

함수 매개변수에 기본 인자값을 주어 파라미터를 생략하여 호출할 수 있다.  

```kotlin
private fun getCurrentWeatherState(weatherState: String = "맑음") {
    println("날씨 $weatherState")
}
```  

</br>




