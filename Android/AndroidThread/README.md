# Android Thread

> 들어가기 전에  

Android Thread의 개념을 단순하게 생각하고 프로젝트를 진행 하던 중에 ListView에 나열되어 보여질 데이터를 네트워킹을 통해 받아오고 Parsing 하여 추가할려고 했는데 Thread를 제어하지 않고 특히나 UI Thread에 대한 개념이 부족한 상태에서 작업을 하였습니다.  
그 결과 데이터가 담기기도 전에 Thread가 UI 를 변경해버려 ListView에 데이터가 담겼다 안담겼다 하는 문제점이 발생하였습니다. 또한 ARN과 같은 다른 문제들이 상당히 많이 발생하였고 이런 경험을 바탕으로 Thread 제어에 관한 모든것을 정리하고자 합니다.

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
</br>

**Android는 UI를 그리는 Thread는 오직 Main Thread에서만 가능하도록 설계 되었다.**  

</br>

만약 Main Thread가 아닌 Worker(작업) 또는 Background Thread에서 UI를 변경하려고 하면 Exception이 발생한다.  
일반 적으로 `onCreate()` 메서드 내에서 실행되는 모든 작업은 UI Thread에서 작동이 된다.  






