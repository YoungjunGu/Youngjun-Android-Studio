# Kotlin(코틀린)


## Kotlin 이란?

IntelliJ를 만든 [JetBrains](https://www.jetbrains.com)가 개발한 언어이다.  
기존의 안드로이드 스튜디오는 자바 6기반으로 개발 하였고 이러한 문제점들을 구글이 Kotlin을 공식 언어로 채택하면서 해결되었다.  

Kotlin을 사용하면 다음과 같은 이점이있다.  

- **호환성** : 코틀린은 JDK6와 완벽하게 호환이되어 구버전의 안드로이드 기기에서도 완벽하게 실행이된다. 그리고 안드로이드 스튜디오에서 완벽히 지원이 되기 떄문에 빌드 시스템과도 완벽히 호환이된다.  
- **성능** : 코틀린은 자바만큼 빠르거나 종종 람다로 실행되는 코드는 기존의 자바보다 훨씬 빠르게 동작하는 경우도 있다.  
- **상호 운용성** : 코틀린은 자바와 100% 상호 운용이 가능하여 **기존의 모든 안드로이드 라이브러리를 사용할 수 있다.**  
- **학습 곡선** : 안드로이드 스튜디오는 자바 코드를 코틀린으로 자동으로 뱐환해주는 도구를 제공한다.  

</br>

## 문법적 특성  

#### Standard Input/Output (입출력)  

- 정수 입력

```kotlin
import java.util.Scanner

fun main(args: Array<String>) {
  
  val reader = Scanner(System.'in')
  print("Integer 입력: ")
  
  var integer: Int = reader.nextInt()
  println("입력한 정수 &integer")
}
```  

- 문자열 입력  

```kotlin
fun main(args: Array<String>) {
  print("text 입력: ")
  
  val stringInput = readLine()!!
  println("입력한 문자열 :  &{stringInput}")
}
```  

#### 자료형 및 변수,상수 선언  

- var : 변수 (값의 변경이 가능)
- val : 상수 (값의 변경이 불가능 (Java의 final))

```kotlin
var a: Int = 3
var b = 5	// 자동 형추론 (=Int)

```  

#### 비교 연산  

- 문자열 비교 : `==`를 사용한다 (Java의 `.equals()`메서드와 동일)
- 오브젝트 비교 : `===`를 사용한다. 

#### 배열  

- 1차원 배열 : `arrayOf()`를 사용하여 배열의 생성과 초기화를 함께 수행한다.  

```kotlin
val array: Array<Int> = arrayOf(1,2,3,4,5)

val array2 = arrayOfNulls<Int>(5)
// null 값을 허용하는 배열

// Array Class 생성자 사용
val arr3 = Array<String>(5, {i -> i.toString()})

val arr3 = Array(5, {i -> i.tpString()})

// 배열의 모든 원서 출력
val arr3 = Array<String>(5) { i -> i.toString() }
arr3.forEach{ print(it) }
```

- 2차원 배열 

```kotlin
val arr1 = arrayOf(arrayOf(1,2,3),arrayOf(4,5,6))
val arr2 = Array(2, { Array(3, { i -> i + 1 }) })
arr1[0].forEach { print(it) }
arr1[1].forEach { print(it) }
println()
arr2[0].forEach { print(it) }
arr2[1].forEach { print(it) }
```

- 행렬 크기를 설정한 2차원 배열 생성  

```kotlin
import java.util.*

fun create2DArray(row: Int, col: Int): Array<Array<Int>> = Array<Array<Int>>(row,{Array<Int>(col, {i -> 1})})

// java.util의 Arrays 중 deppToString() 메서드를 활용하여 배열 출력
fun printForEach(arr: Array<Array<Int>>) { println(Arrays.deepToString(arr))}
```

> 참고  
코틀린에서 Array<>() 을 사용하여 배열을 만들면 컴파일러는 제네릭 타입을 기준으로 컴파일하기 때문에 불필요하게 객체를 생성하게 되어 메모리 낭비와 성능 저하가 발생할 수 있다.  


#### 자동 자료형 추론

기본 타입 변수를 선언할 때 타입을 지정하지 않으면 초기화 되는 값을 기준으로 Kotlin 컴파일러가 적합한 타입을 추론해준다.  
정수의 경우는 Int 타입, 실수의 경우 Double 타입으로 추론한다.  

```kotlin
    val a = 10
    val b = 10.0
    val c = 10f
	val d = 10L

    println(a.javaClass)
    println(b.javaClass)
    println(c.javaClass)
    println(d.javaClass)
```

- Any : 모든 클래스의 조상 , 변수 타입을 Any로 지정하면 어느 값이든 저장, 참조 할 수 있다.  

```kotlin
var data: Any = 10f
data = 10
data = "String형 자료로!"
```

단 위와 같이 하게 될경우 새로운 객체가 생성되기때문에 메모리 낭비와 성능 저하 등의 문제가 발생할 수 있다.  









