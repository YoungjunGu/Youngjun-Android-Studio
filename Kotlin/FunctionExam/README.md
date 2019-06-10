# Kotlin 의 함수

기존의 다른 언어의 함수와 다른 특징, 작성시 유의 사항 등을 유념하면서 정리하였습니다.  
  
</br>

## 함수의 구조  


```kotlin
visibilityModifier fun functionName(parameter1: DataType,,,): return Type { }
```  
- example  


```kotlin
private fun airPollutionState(fineDust: Int): String {
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


### 단일 표현식 함수  

코틀린은 하나의 표현식만 같는(하나의 명령문만 실행되는) 함수에 대해 정의하는데 필요한 코드 양을 줄여준다.  
**단일 표현식**을 갖는 함수에서는 헤더에 정의하는 반환타입, 중괄호, return 문 모두 생략 가능하다.  

- 예제

~~private fun airPollutionState(fineDust: Int): String {~~  
~~val airCondition = when (fineDust) {~~


```kotlin
private fun airPollutionState(fineDust: Int) =
  when (finDust) {
        10 -> "최고"
        20 -> "양호"
        30 -> "나쁨"
        100 -> "최악"
        in 101..130 -> "외출금지"
        else -> "측정불가"
  }
```  

~~return airCondition~~  
~~}~~  


### Unit 함수  

반환하는 값의 타입이 **Unit(Java의 Void)** 인 함수를 Unit라고 한다.  
즉, 반환하는 값이 없는, return 이 없는 함수이다.  
코틀린 이전의 언어에서 가령 Java를 예로 들어보면 void 타입의 함수는 `return;`으로 로 void 반환값의 함수를 처리했다. 
하지만 이는 **제네릭**을 처리하기 어렵다.  
제네릭 함수에서는 반드시 반환 타입을 나타내야하며 아무것도 반환하지 않는 제네릭 함수를 구현할 수 있는 방법이 없다.  
void 의 경우 타입이 아니며, '타입의 정보가 의미가 없기에 생략해도된다'를 근거에 두고 있다.  
</br>

```kotlin
private fun getCurrentWeatherState(weatherState: String = "맑음") {
    println("날씨 $weatherState")
}
```  

코틀린에서는 Unit을 지정하여 이 문제를 해결한다.Unit은 아무것도 반환하지 않는 함수의 반ㅁ환 타입을 나타낸다.  
그러나 이와 동시에 반드시 반환 타입을 가져야 하는 제네릭 함수에도 사용할 수 있다.  


</br>

### 지명 함수 인자  

```kotlin
private fun getCurrentSkyColor(isFresh: Boolean, fineDust: Int, isChina: Boolean){...}

getCurrentSkyColor(isFresh = false, finDust = 100, isChina = false)
```  

```kotlin
getCurrentSkyColor(isFresh: Boolean, fineDust: Int, isChina: Boolean)
```

매개변수를 지명하여 함수를 호출 할 수 있다.  
지명 함수 인자를 사용할 경우 몇가지 장점을 제공한다.  

- 원하는 순서로 인자를 전달 할 수 있다.  
- 코드의 명확성을 확보 할 수 있다.


</br>

### Nothing 타입  


Nothing 타입은 Unit타입처럼 값을 반환하지 않는 함수를 나타내는 데 사용된다.  
두 가지의 차이점은 **Nothing 타입은 함수의 실행이 끝나더라도 호출 코드로 제어가 복귀되지 않는다.**  
Nothing 타입의 용도는 의도적으로 예외발생 시킬때 사용할 수 있다.  
예를들어 코틀린 표준 라이브러리에 있는 TODO함수에 사용된다.  


```kotlin
@kotlin.internal.INlineOnly
public inline fun TODO(): Nothing = throw NotImplementedError()
```  

TODO 함수의 사용 시점은 **여전히 해야 할 것이 있다는 것을 알리기 위해 사용**된다.  

- 사용 예제  

```kotlin
fun shouldReturnAString(): String {
  TODO("문자열을 반환하는 코드를 여기에 구현해야 한다")
}
```  

위 함수가 String 값을 반환하는 해야하는 것을 개발자는 알고있지만 이 함수를 구현하는데 필요한 다른 기능이 아직 완성되지 않아서 보류하고 TODO함수를 호출하여 나중에 할 일을 알 수 있도록 한 것이다.  
추가적으로 이함수에 뭐가 구현되어야 할지를 TODO에 적어 두게 되면 개발자는 이 함수를 test를 위해 Call 할때마다 직접 확인하고 상기할 수있는 기능이라고 보면된다.  

</br>

### 함수 오버로딩  

자바의 함수 오버로딩과 똑같다.  
함수의 이름은 같지만 매개변수의 타입, 이름, 개수 가 다른 함수를 새로 정의하는 것을 **오버로딩**이라고 한다.  

</br>


# 익명 함수  

count 함수는 문자열에서 문자의 개수를 반환해주는 함수이다.  

```kotlin
val numLetters = "KnuStudentComputer".count()
```  

만약 주어진 문자열에서 특정 문자의 개수는 몇개인지 알고 싶을때 익명함수를 count 함수의 인자로 전달해야한다.  

```kotlin
val numLetters = "KnuStudentComputer".count({ letter ->
    					letter == 'u'
				})
println(numLetters)
// 3출력
```  

이때 count 함수는 `({ })` 중괄호 안에 지정된 익명 함수를 매개변수로 받아 익명함수내에 정의한 문자열의 문자 개수를 세는 방법을 결정한다. 이때 익명함수가 true 일때만 개수를 하나씩 증가시킨다.  


```kotlin
println({
    val local = "seoul"
    "$local 의 현재 날씨는"
})
```    

</br>


# 함수 타입  

익명함수도 타입을 가지며 , 이것을 **함수 타입**이라고 한다.  
함수 타입의 변수들은 값으로 익명 함수를 저장한다. 그리고 다른 변수처럼 익명함수가 코드 어디든 전달 될 수 있다.  


- 익명함수를 변수에 지정하기

```kotlin

val seoulWeatherInfo: () -> String = {
    val local = "seoul"
    "$local 의 현재 날씨는"
}

println(seoulWeatherInfo())
```  

위의 예제에서 `() -> String` 은 함수타입을 나타내며 변수가 저장하는 함수 타입을 컴파일러에게 알려준다.  
일반 적으로 함수를 선언할 때 반환 타입을 `():DataType`으로 선언하는 것이 `() -> DataType` 으로 변경 되었다고 보면 된다.  

</br>

# 암시적 반환  

함수 타입의 예제는 return 키워드가 없다 하지만 `() -> String` 로 명시가 되어있어 String 을 반환하도록 지정되어있다.  
우선 익명 함수의 경우 데이터를 반환하기 위한 return 키워드가 필요없다.  
익명함수는 **암시적으로 또는 자동으로 함수 정의의 마지막 코드 결과를 반환**하기 때문이다.  
익명함수에서 return 키워드의 사용은 금지되어있다. 익명함수에 return 이 있게 될 경우 어떤 곳으로 제어가 복귀 되어야 하는지 컴파일러가 알 수 없기 때문이다.  

> 참고  
	익명함수는 익명 클래스 인스턴스로 생성된다.  
    
</br>    

























