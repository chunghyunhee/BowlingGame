## 2020.06.04. BowlingGame.java ver1.
1. 사용자 설정(여기서는 게임 이용자 이름을 받아 각각 Frame진행하게 한다)
2. Strike나 Spare에 대한 설정 -> 이 경우 바로 점수 합산이 되면 안되고, 다음 Frame실행이 되어야 한다. 
                              -> Frame 10의 경우 3번 실행 가능성에 대한 코드
3. Board 출력 형태의 개성 (1~9값 이외의 경우는 다르게 출력해야 한다)
4. 초급자/중급자/고급자에 따라 Strike, Spare의 확률을 계산하여 개선
5. 출력형태 변경 -> 한번에 Frame10이 출력되면 안된다. 한번의 Frame별로 출력되면서 게임해야 한다
- 출력 형태는 다른 Demo를 확인하면서 수정할 것. 

## 2020.06.05. BowlingGame.java ver2.
1. 급수(초급자/중급자/고급자)에 따라 확률값 개선, 각 매서드를 따로 써야 하는지도 고려
2. 현재는 각 Frame, 각 구에서의 결과를 출력하고 있음. Board 시각화 매서드 새로 생성하고 추가

## 2020.06.06. BowlingGame.java ver3.
1. Board 개선_1 : 10일때 X, 0일때 -,G중에서 binary값으로 출력하도록 하기
   --> 각 point결과를 출력할 때는 /나 G와 같은 기호로 출력하되, board에서는 값자체로 출력하도록 한다
2. 10Frame에서 score3까지 쳐야 하는 경우에 대한 예외 매서드 처리
3. strike, spare일 때 board에서의 총합 계산 처리
4. 확률값 계산 방법 (초급자/중급자/고급자)
  --> (점수) = 10*(#spare)+10*(#strike)+4.5*(#spare)+9*(#strike)+8.55*(10-#strike-#spare)의 형태의 규격으로
  맞춘 후에 확률을 맞춰서 함수로 지정해 준다.

## 최종 출력 Board
![image](https://user-images.githubusercontent.com/49298791/83949926-c1816c00-a861-11ea-91d5-329bb98e872e.png)
