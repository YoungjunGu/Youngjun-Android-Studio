# Opeartor Overloading(연산자 오버로딩)

## Kotlin의 null 처리

kotlin에서는 자바보다 null 처리를 좀더 명확하게 한다.  
NPE(NullPointerException)가 발생하는 빈도를 낮출 수 있다.  

### Null이 될수 있는 type  

- type에 **?** 를 붙임으로써 null 이 가능한 변수임을 명시적으로 표현한다.  

```kotlin
fun strLenSafe(s: String?): Int =
  if(s != null) s.length else 0
```

위의 예제에서 `if(s != null)` 구문이 존재하지 않는 다면 컴파일 에러가 발생한다.
**null 이 들어올 수 있는 타입이지만 내부에서 null check 없이 사용했기때문이다.**  

</br>

### Null safe Operator  

null 을 안전하게 처리하기 위해 코틀린은 **?.** 연산자를 지원한다.  

```kotlin
fun printAllCaps(s: String?) {
  val allCaps: String? = s?.toUpperCase()
  println(allCaps)
}
```  

`?.` 연산자를 사용하면, 앞의 변수가 null 이 아닐때만 오른쪽 함수가 수행되고 null 이면 null을 반환한다.  
즉 `if(s != null) s.toUpperCase() else null` 과 동일하다.  
s의 값이 null 이면 바로 null을 반환한다.  

<img width="847" alt="image" src="https://user-images.githubusercontent.com/33486820/59050145-97027900-88c4-11e9-9bcc-ce6bed8992f4.png">  


`?.` 연산자는 프로퍼티접근 시에도 null 처리를 통해 NPE발생을 막을 수 있다.  

<img width="842" alt="image" src="https://user-images.githubusercontent.com/33486820/59050653-cfef1d80-88c5-11e9-8aeb-f974fe7b6a91.png">  

프로퍼티의 값이 null 일 시에도 `?.`를 통해 NPE 발생을 막고 Default 값을 출력해 내는 것을 확인할 수 있다.  

</br>

### Elvis Operator  

`?.` 연산자는 왼쪽이 null 이면 null 을 반환한다.  
앞전 과 같이 null 이되면 Default 값을 주고 싶을 때 `?:`(엘비스 연산자) 를 사용한다.  

```kotlin
fun getName(str: String) {
	val name = str ?: "Unknown" 
  	// if(str != null) str else "Unknown" 과 동일
}
```  

<img width="844" alt="image" src="https://user-images.githubusercontent.com/33486820/59052831-f6638780-88ca-11e9-906a-177076ddfb9b.png">  

엘비스 연산자 우항애 throw를 통해 강제로 exception을 발생시켜 실행이 되지 않게 했다.
또한 with를 통해 address 객체의 반복없이 간결하게 프로퍼티를 호출할 수 있다.  




## 연산자  

|표기|code|
|:---|:---|
|a == b|a?.equals(b) ?: (b == null)|
|a === b|오버로딩 안됨|
|a != b|!(a?.equals(b)) ?: (b == null)|
|a !== b| 오버로딩 안됨|

- === : 변수가 똑같은 객체를 참조하는지를 비교할 때 사용하는 연산자
- `a?.equals(b) ?: (b == null)` : a가 null 인지 판단 하고 아니라면 equals() 함수를 호출한다. 그리고 equals(b)를 실행한 결과값이 null 이 아니면 그 값을 리턴한다. 만약 null 이라면 `b == null` 의 값을 반환다.

