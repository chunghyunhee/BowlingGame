import java.util.*;

// 볼링 점수판을 만들어 출력하는 클래스 작성 
class ScorePrint{
	Vector<Point>v = new Vector<Point>();
	int total;
	
	public ScorePrint(Vector<Point>v,int total) {
		this.v = v; this.total = total;
	}
	public void printScore(int FrameNum) {
		Point p = v.get(FrameNum);
		System.out.println("Frame:"+(FrameNum+1)+", point1:"+p.score1+",point2:"+p.score2);
		System.out.println("total:"+total);
	}
}

// 스코어의 상태 판단(spare, normal, strike ->true, false판단)
class ScoreState{
	int first; int second;
	// 입력받은 값을 받는다. 
	public ScoreState(int first, int second) {
		this.first=first; this.second = second;
	}
	// 일반적으로 값을 계산하는 경우인지를 판단하는 매서드 
	public boolean Simple() {
		if(first+second<10)
			return true;
		else
			return false;
	}
	// spare이 되는 경우인지를 판단하는 매서드 
	public boolean IsSpare() {
		if(first+second == 10 && first!=10)
			return true;
		else
			return false;
	}
	//strike인지를 판단하는 매서드
	public boolean IsStrike() {
		if(first == 10)
			return true;
		else
			return false;
	}
	
}

// 각 point에서 값을 받았을 때 점수를 계산하는 클래스 작성 
class ComputeScore{
	Vector<Point>v = new Vector<Point>(); 
	int score, total;

	
	public ComputeScore(Vector<Point>v,int total) {
		this.v = v; this.total = total;
	}
	
	// compute ith Frame score 
	public int Score(int FrameNum) {
		Point p = v.get(FrameNum);
		//Point p2 = v.get(FrameNum+1);
		ScoreState ss = new ScoreState(p.score1, p.score2);

		if(ss.IsSpare()) { 
			//스페어인 경우는 다음 프레임까지 진행해야한다
			//score = p2.score1 + 10;
		}
		else if (ss.IsStrike()) {
			// 스트라이크인 경우도 다음 프레임까지 진행해야한다
			//score = 10+p2.score1+p2.score2;
		}
		else {
			// simple compute인 경우 
			score = p.score1+p.score2;
		}
		total += score; //총계
		
		return score; // this Frame의 score을 출력
	}
}

// 첫번째구, 두번째구에서의 점수를 얻어내는 클래스 작성 
class playGame{
	String level; 
	
	// 이용자의 수준을 입력받는다 -> 수준에 따라 다르게 진행 
	public playGame(String level) {
		this.level = level; 
	}
	
	// 각 프레임에서 첫번째 구
	public int firstBowl(){
		int score1 = 0;
		
		//초보자
		if(level.equals("초급자")) {
			// 나올 수 있는 경우의 확률기반 계산 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
		}
		else if(level.equals("중급자")) {
			// 나올 수 있는 경우의 확률기반 계산 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
		}
		else {
			// 나올 수 있는 경우의 확률기반 계산 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
		}
		return score1;
	}
	// 각 프레임에서 두번째 구 
	public int secondBowl() {
		int score2 = 0;
		
		// 초보자
		if(level.equals("초급자")) {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12중 하나의 값 추출 
		}
		else if(level.equals("중급자")) {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12중 하나의 값 추출 
		}
		else {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12중 하나의 값 추출 
		}
		return score2;
	}
	
}

class Point{
	public int score1, score2;
	public Point(int score1, int score2) {
		this.score1 = score1; this.score2 = score2;
	}
	// 객체를 직접 print하는 경우
	@Override
	public String toString() {
		return "point1:"+score1+", point2:"+score2;
	}
}


public class BowlingGame {
	public static void main(String[] args) {
		Vector<Point>v = new Vector<Point>(); 		//두구를 하나의 프레임으로 10개의 프레임을 저장할 벡터 
		Scanner sc = new Scanner(System.in);		
		
		System.out.println("수준을 선택해주세요 ex(초급자/중급자/고급자) >>");
		String level = sc.next(); 
		int score1, score2, score;
		int totalScore = 0;

		// 선택한 수준에 따라 총 10프레임을 진행한다. 
		for(int i=0; i<10; i++) {
			playGame pg = new playGame(level);
			ComputeScore cs = new ComputeScore(v,totalScore);
			ScorePrint sp = new ScorePrint(v,totalScore);
			
			score1 = pg.firstBowl();				// first Point score
			score2 = pg.secondBowl();				// second Point score
			v.add(new Point(score1, score2));
			
			score = cs.Score(i); 					// ith Frame score compute
			totalScore += score;
			
			sp.printScore(i);						// print score boared until ith Frame

			}
		sc.close();
			
		}
}

