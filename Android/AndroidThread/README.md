# Android Thread

> 들어가기 전에  

Android Thread의 개념을 단순하게 생각하고 프로젝트를 진행 하던 중에 ListView에 나열되어 보여질 데이터를 네트워킹을 통해 받아오고 Parsing 하여 추가할려고 했는데 Thread를 제어하지 않고 특히나 UI Thread에 대한 개념이 부족한 상태에서 작업을 하였습니다.  
그 결과 데이터가 담기기도 전에 Thread가 UI 를 변경해버려 ListView에 데이터가 담겼다 안담겼다 하는 문제점이 발생하였습니다. 또한 ANR과 같은 다른 문제들이 상당히 많이 발생하였고 이런 경험을 바탕으로 Thread 제어에 관한 모든것을 정리하고자 합니다.

</br>

안드로이드 어플리케이션이 실행되면 안드로이드 시스템은 **하나의 실행 스레드**로 어플리케이션의 프로세스를 실행한다.  
어플리케이션의 구성요소가 생성될 때 별도의 스레드가 생성되는 것이 아니라 **Main Thread** 라는 하나의 실행 스레드로 실행된다.  
</br>

Main Thread은 화면 구성에 관한 역활을 담당 하기 때문에 **UI Thread** 라고도 한다.  

<img width="656" alt="image" src="https://user-images.githubusercontent.com/33486820/59277272-da7d2e80-8c9a-11e9-9bf1-3614f0364114.png">

`adb shell ps` 명령어를 사용하여 실행된 APK를 보면 하나의 Main Thraed가 있는 것을 확 인할 수 있다.  


## Main Thread  


어플리케이션은 일반적으로 프로세스 하나 위에 여러 멀티 스레드가 돌고 있는 형태이다.  
멀티 스레드를 사용하게 되면 성능은 향상 되지만 무조건 멀티 스레드를 사용하게 되면 안된다.  
만약 UI에 대한 변경 작업을 멀티 스레드로 변경해버린다면 원하는 순서대로 UI가 변경 되지 않을 뿐더러 다른 이상현상이 많이 발생 할 것이다.(View 계층 구조 순서 위반 , ListView에 담을 데이터를 네트워킹 해서 받아오기도 전에 ListView 추가 등)  
사용자가 버튼을 누르거나 화면을 터치했는데 반응성(응답성)이 떨어진다면 사용자는 답답함을 느낀다.  
무엇보다 ANR(Application Not Respoding)이 발생하여 어플레이센이 강제종료되는 큰 위험이 있다.  

</br>

안드로이 UI tool Kit 는 Thread로 부터 **Not Safe**하다.  
그렇기 때문에 UI Thread가 아닌 Worker(작업) 또는 Background Thread에서 UI를 변경하려고 하면 Exception이 발생한다. 
그렇다면 UI 구성요소는 모두 UI Thread에서 조작되어야 하고 결국은 UI의 동작은 **단일 Thread로 동작**하는 구조가된다. </br>
UI Thread에서 시간이 오래 걸리는 작업을 수행하는 것은 그 작업이 수행되는 동안 UI 업데이트가 지연 또는 차단 되고 있다는 것을 의미한다. 그렇기 때문에 **Android는 UI를 그리는 Thread는 오직 Main Thread에서만 가능하도록 설계** 되었고 이는 반응성이 좋은 프로그램을 만들기 위함이다.  

</br>

#### Thread 사용 시 유의 사항  

1. 시간이 오래 걸리는 작업은 UI Thread로 부터 분리하고 별도의 Thread에서 실행함으로써 UI Thread 작업이 지연 또는 차단 되는 것을 방지한다.  

2. UI Thread가 아닌 Thread에서는 UI 구성 요소를 조작하지 않는다.  


#### 예제 


<img width="323" alt="image" src="https://user-images.githubusercontent.com/33486820/59284179-c5a69800-8ca6-11e9-8850-99f849554360.png">  

Download, Cancel 이라는 두 버튼에 대해 터치시 각각의 함수를 호출한다.  



```kotlin
private fun onDownload() {
        Log.d(TAG, "Press Download Button")
        isDownloading = true

        for (i in 0..10) {

            if(!isDownloading) {
                break
            }
            try {
                Thread.sleep(1000)
                Log.d(TAG, "Downloading .. ${i*10}%" )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        isDownloading = false
}

private fun onCancel() {
        Log.d(TAG, "Press Cancel Button")
        isDownloading = false
}
```  

Download 버튼을 누르게 되면 1초마다 log가 찍히는 것을 확인 할 수 있다.  

![image](https://user-images.githubusercontent.com/33486820/59283910-516bf480-8ca6-11e9-8a4c-84d5630abea1.png)  

이때 Cancel 버튼을 여러번 터치를 하게 되면 **ANR** 다이얼로그가 발생한 것을 볼수 있다.  

<img width="307" alt="image" src="https://user-images.githubusercontent.com/33486820/59284342-10281480-8ca7-11e9-9847-939af1c710bf.png">  

위와 같이 ANR과 같은 심각한 문제를 발생하여 죽어버리는 앱은 반응성이 좋지 못한 어플리케이션의 사례라고 할 수 있다.  
ANR 과 같은 회피하고 반응성이 좋은 어플리케이션을 만들기 위해서는 위의 예제에서 볼 수 있는 `onDownload` 에서 진행하는 **시간이 많이 걸리는 작업은 UI Thread에서 분리하라** 라고 볼 수 있다.  

</br>

#### 시간이 많이 걸리는 작업을 UI Thread에서 분리  

`onDownload()` 함수를 수행하게 되면 내부에서 Thread를 상속하는 Download 인스턴스 객체를 할당하게 되고 start 메서드를 통해 내부 run 함수를 수행하게 된다. 이때 아래의 gif 처럼 버튼의 text를 매순간 바꾸게 하기 위해서 **runOnUiThread**를 사용하였다.  


<img src ="https://user-images.githubusercontent.com/33486820/59289904-30110580-8cb2-11e9-94fa-393dd838fbcf.gif" width = 500>


```kotlin
    private fun onDownload() {
        Log.d(TAG, "Press Download Button")
        download = Download()
        isDownloading = true
        download.start()

    }

    private fun onCancel() {
        Log.d(TAG, "Press Cancel Button")
        isDownloading = false

    }

    inner class Download: Thread() {

        override fun run() {
            for (i in 0..10) {

                if(!isDownloading) {
                    break
                }
                try {
                    Thread.sleep(1000)
                    Log.d(TAG, "Downloading .. ${i*10}%" )

                    runOnUiThread {
                        if(i == 10) {
                            downloadBtn.text = "COMPLETE"
                        } else {
                            downloadBtn.text = "${i*10}..%"
                        }
                    }

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }

            isDownloading = false
        }
    }
```

TextView,Button,Layout,Color,, 등등 모든 UI에 변경을 요하는 작업은 **runOnUiThread**에서 수행해야한다.  
위에서 runOnUiThread 람다식을 사용하지 않고 downloadBtn의 text를 변경하려고 시도하면  
**CalledFromWrongThreadException**이 발생과 동시에 앱은 죽어버린다.  
이 말을 지격하게 되면 뷰 계층 구조를 만든 원래 Thread 즉 **Main(UI) Thread**만 해당 뷰를 조작할 수 있다는 의미다.  

</br>

안드로이드에서는 UI thread에 접근할 수 있는 몇가지 방법을 제공한다.  
위에서 본 `runOnUiThread`람다식또한 그 중 하나의 예이다.  

1. **`runOnUiThread`의 람다식 사용**  
2. **`Handler`** 와 **`Lopper.getMainLopper()`** 이용한 방법  
3. **`View.post`** 이용한 방법  


</br>

## runOnUiThread 사용  

위의 예제에서 보면 

```kotlin
...

runOnUiThread {
	if(i == 10) {
		downloadBtn.text = "COMPLETE"
	} else {
		downloadBtn.text = "${i*10}..%"
	}
}
...
```  

Android Developer에 설명에는 이렇게 명시가 되어있다.  
"특정 동작을 UI Thread에서 동작하도록 한다. 만약 현재 Thread가 UI Thread면 그 동작은 **즉시 수행**된다. 하지만 현재 Thread 가 UI Thread가 아니면 필요한 동작을 UI Thread의 **Event Queue로 전달**한다."  

<img width="819" alt="image" src="https://user-images.githubusercontent.com/33486820/59325813-6df84300-8d1f-11e9-9add-0d0d0063fed5.png">  


`runOnUiThread()` 함수가 작동하는 것을 코드에서 볼 수 있듯이  runOnUiThread가 실행된 스레드가 UI Thread가 아니므로  
**mHandler.post(action)** 가 수행된다.  
Runnable r은 UI Thread의 queue에 메시지 형식으로 enqueue가 된다.  
후에 queue에 있는 메시지를 꺼내서 UI Thread에서 동작을 수행한다.  

1. `post(Runnable)`  
2. `sendMessageDelayed(Messgage msg, long delayMillis)` 
3. `sendMessageAtTime(Message msg, long uptimeMills)
	- `MessageQueue queue = mQueue;`  
4. `enqueueMessage(MessageQueue queue, Message msg, long uptimeMills)`  

처음 `startActivity()` 함수를 통해서 MainActivity가 생성되는 과정은 zygotelnit에 의해서 ActivirtThread가 생성된다.  
ActivityThread는 아래와 같이 **Main Lopper**와 **Handler**를 생성한다.  

</br>

```java

 // frameworks/base/core/java/android/app/ActivityThread.java

public final class ActivityThread {

...

public static void main(String[] args) { 

...
		Looper.prepareMainLooper(); // 1. UI thread의 루퍼를 생성 
		ActivityThread thread = new ActivityThread(); 
		thread.attach();

		if(sMainThreadHandler == null) {     
			sMainThreadHandler = thread.getHandler(); // 2. 핸들러 설정
		} 
		Looper.loop(); // 3. 루퍼 동작 시작
	}

}
```  

ActivityThread는 ActivityManagerService와 바인더 통신을 하여 MainActivty를 생성한다.  
따라서 ActivityThread가 생성한 MainActivity는 ActivityThread에서 생성한 Looper와 Handler를 사용하게된다.  
`Looper.loop()`을 시작하면 mQueue는 UI Thread의 Looper가 관리하는 queue를 가르킨다.  

</br>

queue에 Messeage형태로 들어한 Runnable 작업은 UI Thread에서 dequeue가 되어 UI Thread상에서 수행된다.  

</br>

## Handler.post() 를 사용하여 제어

```kotlin

    inner class Download: Thread() {

        override fun run() {
            for (i in 0..10) {

                if(!isDownloading) {
                    break
                }
                try {
                    SystemClock.sleep(1000)
                    Log.d(TAG, "Downloading .. ${i*10}%" )

                    var r = Runnable {
                        downloadBtn.text = "${i*10}%"
                    }
                    handler?.postDelayed(r, 1000)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }

            isDownloading = false
        }
    }
```

`postDelayed()` 함수에 매개변수로 Runnable action 객체를 전달하여 UI Thread에서 실행된다.  


> 참고 post(r: Runnable) 는 delay 없이 바로 Message Queue에 Runnable action을 전달한다.  


## View.post() 사용 하여 제어

> kotlin의 view.post()의 자료를 아직 찾지 못해서 java의 View.post를 정리 하겠습니다.  


Handler만 쓰는 거와 가장 큰 차이는 attach가 되었는지 여부를 확인 하는 것이다.  
View에 보여지는 시기에 맟쳐서 실행을 할려면 View.post()를 사용해야한다.  
View.post()는 attach되지 않으면 post된 모든 runnable들의 실행이 attach 될때까지 연기(postpone)된다.

참고 : https://codetravel.tistory.com/16?category=978866  




</br>
<hr>


## Reference  

- https://codetravel.tistory.com/16?category=978866
- https://developer.android.com
- https://academy.realm.io/kr/posts/android-thread-looper-handler/

























