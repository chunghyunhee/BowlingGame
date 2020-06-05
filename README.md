## 2020.06.04. BowlingGame.java ver1.
1. 사용자 설정(여기서는 게임 이용자 이름을 받아 각각 Frame진행하게 한다)
2. Strike나 Spare에 대한 설정 -> 이 경우 바로 점수 합산이 되면 안되고, 다음 Frame실행이 되어야 한다. 
                              -> Frame 10의 경우 3번 실행 가능성에 대한 코드
3. Board 출력 형태의 개성 (1~9값 이외의 경우는 다르게 출력해야 한다)
4. 초급자/중급자/고급자에 따라 Strike, Spare의 확률을 계산하여 개선
5. 출력형태 변경 -> 한번에 Frame10이 출력되면 안된다. 한번의 Frame별로 출력되면서 게임해야 한다
- 출력 형태는 다른 Demo를 확인하면서 수정할 것. 
- refer code : https://github.com/030ii/NHNNEXT_JAVA_Bowling/tree/master/Bowling/src/main

## 2020.06.05. BowlingGame.java ver2.
1. 급수(초급자/중급자/고급자)에 따라 확률값 개선, 각 매서드를 따로 써야 하는지도 고려
2. 현재는 각 Frame, 각 구에서의 결과를 출력하고 있음. Board 시각화 매서드 새로 생성하고 추가
