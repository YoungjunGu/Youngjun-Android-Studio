# Intent

kotlin에서 화면을 전환할때는 `Anko` 라이브러리를 사용하면 기존의 Intent 방식보다 조금 더 간단한 방식을 제공하고 있다.  


### Activity 사이의 부모 자식 설정

<img width="380" alt="image" src="https://user-images.githubusercontent.com/33486820/59043575-7253d480-88b7-11e9-9335-1f525a18947c.png">

위와 같이 추가를 할 경우에 up 네비게이션을 설정하여 상단 툴바에 뒤로가기를 통해 바로 부모 액티비티로 넘어 갈 수 있다.

### 버튼을 누르면 다음 화면으로 전환하는 방법  

- 기존의 Intent 방식  

```kotlin
val intent = Intent(this, TargetActivity::class.java)
intent.putExtra("name", nameEditText.text.toString())
intent.putExtra("age", ageEditText.text.toString())
startActivity(intent)
```  

- Anko를 활용한 방식

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitBtn.setOnClickListener {
          
            startActivity<SecondActivity>(
                "name" to nameEditText.text.toString(),
                "age" to ageEditText.text.toString()
            )
        }
    }
```

- intent를 통해 데이터 받아오기

```kotlin
val name = intent.getStringExtra("name")
val age = intent.getStringExtra("age").toInt
```

데이터를 전달하는 액티비티에서 key값을 통해 전달 하는 액티비티 사이에서 값을 주고 받고 있다.  
이때 전달하는 데이터의 데이터타입이 일치해야한다.  

</br>

# Toast

- 기존의 방식

```kotlin
Toast.makeText(this, "출력할 contents", Toast.LENGTH_SHORT).show()
```

- Anko를 활용한 방식

```kotlin
toast("출력할 contents")
```

</br>

# SharedPreference로 데이터 저장

앱을 종료해도 마지막에 저장했던 데이터값을 남이있게 하는 기능을 구현하기 위해 사용한다.  
설정과 같이 **간단한 데이터 저장** 할때 주로 사용한다.  

### 데이터 저장 (saveData)

```kotlin
    private fun saveData(name: String, age: Int) {
      	// 1.
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // 2.
        val editor = pref.edit()

		// 3.
        editor.putString("KEY_NAME", name)
            .putInt("KEY_AGE", age)
            .apply()	// 4.
    }
```

1. 프리퍼런스 매니저를 사용해 프리퍼런스 객채를 생성  
2. 프리퍼런스 객체의 에디터 객체를 생성한다. 이 객체를 사용하여 프로퍼런스에 데이터 값을 저장할 수 있다.  
3. 에디터 객체에 `put[DataType]` 형식의 메서드를 활용하여 딕셔너리형태의 키&값 형태의 쌍으로 저장을한다.  
4. 설정한 내용을 `apply()` 반영한다.  

`saveData()` 메서드는 `OnCreate` 메서드에서 버튼을 클릭할 시에 저장을 하게 해야하기 때문에 이벤트리스너 함수에 맨처음 에 호출한다.  

### 데이터 불러오기 (loadData)  

```kotlin

    private fun loadData() {
      	// 1.
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
		// 2.
        val name = pref.getString("KEY_NAME", "")
        val age = pref.getInt("KEY_AGE",0)
		
        // 3.
        if(name != null && age != 0) {
            nameEditText.setText(name.toString())
            ageEditText.setText(age.toString())
        }

    }
```   

1. 프리퍼런스 객체를 생성한다.  
2. 프리퍼런스 객체 내부에는 앱이 종료 되기 전에 `saveData()`를 통해 저장된 키&값 형태의 객체가 있기 때문에 Key를 통해 적절히 값을 가져온다. `get[DataType]` 메서드의 오른쪽 매개변수는 Default 값이다.  
3. 프리퍼런스 객체로부터 가져온 데이터를 실제로 TextView에 적용을 시켜 사용자가 이전의 값을 볼 수 있도록 한다.  



`loadData()` 메서드는 `OnCreate` 메서드가 처음 실행 될때 호출을 하여 앱 종료 전에 저장되었던 값을 불러와 적용 시켜야한다.








