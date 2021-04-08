# FCM push 기능 추가

기능 구현 후 알게 된 사실인데... clone 대상을 master로 잡았어야 됬는데 log-in으로 잡은거 가틈... 

# 간단한 Test 방법
1. Firebase console > cloud messaging 접속
2. 테스트 메시지 작성 후 테스트 메시지 전송 클릭
3. Emulator 동작 이후 log에 뜨는 Device token을 입력한 후 해당 토큰 클릭하고 전송
4. 전송된 메시지를 확인한다
- 어플 사용중에는 하단에 간략한 알림이 뜸
- 백그라운드에서 실행되고 있으면 배너 형식으로 알림이 뜸
<img src="https://github.com/Capstone-Muyaho/Capstone-Frontend/blob/FCM-push/%EB%B0%B1%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C.PNG">
