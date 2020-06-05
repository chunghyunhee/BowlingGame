import java.util.*;

// 해당 Frame쳤을 때 결과를 출력해주는 함수 
class ScorePrint{
	Vector<Point>v = new Vector<Point>();
	int total;
	
	public ScorePrint(Vector<Point>v,int total) {
		this.v = v; this.total = total;
	}
	// 프레임넘버 출력하는 함수 
	public void printFrameNum(int FrameNum) {
		Point p = v.get(FrameNum);
		System.out.println("Frame #"+(FrameNum+1));
	}
	// point1출력하는 매서드
	public void printPoint1(int FrameNum) {
		Point p = v.get(FrameNum);
		String tmp;
		String tmp2[] = {"G", "-"};
		double d = Math.random();
		
		if(p.score1 == 10) {
			tmp = "X";
			System.out.println("point1: "+tmp);
		}
		else if (p.score1==0) {
			if(d>0.5) tmp = tmp2[0];
			else tmp = tmp2[1];
			System.out.println("point1:"+tmp);
		}
		else {
			System.out.println("point1:"+p.score1);
		}
	}
	// point2 출력하는 매서드 
	public void printPoint2(int FrameNum) {
		Point p = v.get(FrameNum);
		String tmp;
		String tmp2[] = {"G", "-","/"};
		double d = Math.random();
		
		if (p.score1 != 10 && p.score2==0) {
			if(d>0.5) tmp = tmp2[0];
			else tmp = tmp2[1];
			System.out.println("point2:"+tmp);
		}
		else if(p.score1==10 && p.score2==0) {
			//이 경우는 원래 치면 안되는 경우에 해당한다. 
			System.out.println("point2:"+" ");
		}
		else if(p.score1+p.score2 == 10) {
			System.out.println("point2:"+ tmp2[2]);
		}
		else {
			System.out.println("point2:"+p.score2);
		}
		
		
	}
	// point3 출력하는 매서드 
	public void printPoint3(int FrameNum) {
		Point p = v.get(FrameNum);
		String tmp;
		String tmp3[] = {"G", "-"};
		double d = Math.random();
		
		if (p.score3==0) {
			if(d>0.5) tmp = tmp3[0];
			else tmp = tmp3[1];
			System.out.println("point3:"+tmp);
		}
		else {
			System.out.println("point3:"+p.score3);
		}
		
		
	}
	
	// total출력하는 매서드 
	public void printTotal(int FrameNum) {
		Point p = v.get(FrameNum);
		System.out.println("total:"+total);
	}
	
}

// 결과 board의 출력 



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
		
		if (FrameNum == 9) {
			Point Lastp = v.get(FrameNum);
			ScoreState Lastss = new ScoreState(Lastp.score1, Lastp.score2);
			if(Lastss.IsSpare()) { 
				//스페어인 경우, score3고려 
				score = Lastp.score1 + Lastp.score3;
			}
			else if (Lastss.IsStrike()) {
				// 스트라이크인 경우도 다음 프레임까지 진행해야한다
				score = Lastp.score3+Lastp.score1+Lastp.score2;
			}
			else {
				// simple compute인 경우 
				score = Lastp.score1+Lastp.score2;
			}
		}
		else {
			Point p = v.get(FrameNum);
			Point p2 = v.get(FrameNum+1);
			ScoreState ss = new ScoreState(p.score1, p.score2);

			if(ss.IsSpare()) { 
				//스페어인 경우는 다음 프레임까지 진행해야한다
				score = p2.score1 + 10;
			}
			else if (ss.IsStrike()) {
				p.score2 = 0; // 스트라이크면 다음 구는 치지 않고 넘어간다. 
				// 스트라이크인 경우도 다음 프레임까지 진행해야한다
				score = 10 + p2.score1 + p2.score2;
			}
			else {
				// simple compute인 경우 
				score = p.score1+p.score2;
			}
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
			if(score1>10) {
				score1 = 0; //결국 -나 G에 해당하므로 0점에 해당한다. 출력은 -나 G
			}
		}
		else if(level.equals("중급자")) {
			// 나올 수 있는 경우의 확률기반 계산 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
			if(score1>10)
				score1 = 0;
		}
		else {
			// 나올 수 있는 경우의 확률기반 계산 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
			if(score1>10)
				score1 = 0;
		}
		return score1;
	}
	// 각 프레임에서 두번째 구 
	public int secondBowl() {
		int score2 = 0;
		
		// 초보자
		if(level.equals("초급자")) {
			Random ran = new Random();
			score2 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
			if (score2>10) //마찬가지로 -나 G값중 하나이므로 0점에 해당한다. 
				score2 = 0;
		}
		else if(level.equals("중급자")) {
			Random ran = new Random();
			score2 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
			if(score2 > 10)
				score2 = 0;
		}
		else {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12중 하나의 값 추출 
			if(score2>10)
				score2 = 0;
		}
		return score2;
	}
	// 10 Frame에서의 세번째구가 생기는 경우의 값 생성 매서드 
	public int thirdBowl() {
		int score3 = 0;
		
		// 초보자
		if(level.equals("초급자")) {
			Random ran = new Random();
			score3 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
			if (score3>10) //마찬가지로 -나 G값중 하나이므로 0점에 해당한다. 
				score3 = 0;
		}
		else if(level.equals("중급자")) {
			Random ran = new Random();
			score3 = ran.nextInt(12)+1; //1~12중 하나의 값 추출 
			if(score3 > 10)
				score3 = 0;
		}
		else {
			Random ran = new Random();
			score3 = ran.nextInt(13)+1; //1~12중 하나의 값 추출 
			if(score3>10)
				score3 = 0;
		}
		return score3;
	}
	
}

// 각 구에서의 값 즉 score1,score2, score3를 저장하는 클래스 (벡터에 이 객체를 10개 저장할 예정)
class Point{
	public int score1, score2, score3;
	public Point(int score1, int score2, int score3) {
		this.score1 = score1; this.score2 = score2; this.score3 = score3;
	}
	// 객체를 직접 print하는 경우
	@Override
	public String toString() {
		return "point1:"+score1+", point2:"+score2 +",point3:"+score3;
	}
}


public class BowlingGame {
	public static void main(String[] args) {
		Vector<Point>v = new Vector<Point>(); 		//두구를 하나의 프레임으로 10개의 프레임을 저장할 벡터 
		Vector<Point>v2 = new Vector<Point>(); 		//두구를 하나의 프레임으로 10개의 프레임을 저장할 벡터 
		Scanner sc = new Scanner(System.in);		
		int check = 0;
		System.out.println("######### Bowling Game Start !! #########");
		System.out.print("수준을 선택해주세요 ex(초급자/중급자/고급자) >>");
		String level = sc.next(); 
		int score1, score2, score,tmp;
		int totalScore1 = 0;
		int totalScore2 = 0;

		// 선택한 수준에 따라 총 10프레임을 진행한다. 
		for(int i=0; i<10; i++) {
			// player1 
			playGame pg = new playGame(level);
			ComputeScore cs = new ComputeScore(v,totalScore1);
			ScorePrint sp = new ScorePrint(v,totalScore1);
			if(i != 9) {
				score1 = pg.firstBowl();				// first Point score
				score2 = pg.secondBowl();				// second Point score
				v.add(new Point(score1, score2,0));
			}
			else if(i==9) {
				score1 = pg.firstBowl();
				score2 = pg.secondBowl();
				ScoreState ss = new ScoreState(score1, score2);
				// spare가 나오는 경우 
				if(ss.IsSpare()) {
					check = -1;
					int score3 = pg.thirdBowl();
					v.add(new Point(score1, score2,score3));
				}
				// strike가 나오는 경우
				else if(ss.IsStrike()) {
					check = -1;
					int score3 = pg.thirdBowl();
					v.add(new Point(score1, score2,score3));
				}
				else {
					v.add(new Point(score1, score2,0));
				}
					
			}
			
			// player2
			playGame pg2 = new playGame(level);
			ComputeScore cs2 = new ComputeScore(v2,totalScore2);
			ScorePrint sp2 = new ScorePrint(v2,totalScore2);
			
			if(i != 9) {
				score1 = pg2.firstBowl();				// first Point score
				score2 = pg2.secondBowl();				// second Point score
				v2.add(new Point(score1, score2,0));
			}
			else if(i==9) {
				score1 = pg.firstBowl();
				score2 = pg.secondBowl();
				ScoreState ss = new ScoreState(score1, score2);
				// spare가 나오는 경우 
				if(ss.IsSpare()) {
					check = -1;
					int score3 = pg.thirdBowl();
					v2.add(new Point(score1, score2,score3));
				}
				// spike가 나온느 경우
				else if(ss.IsStrike()) {
					check = -1;
					int score3 = pg.thirdBowl();
					v2.add(new Point(score1, score2,score3));
				}
				else {
					v2.add(new Point(score1, score2,0));
				}
					
			}

		}

		// 게임한 값 출력
		for(int i=0; i<10; i++) {
			// for player1
			ComputeScore cs = new ComputeScore(v,totalScore1);
			ScorePrint sp = new ScorePrint(v,totalScore1);
			ScoreState ss = new ScoreState(v.get(i).score1, v.get(i).score2);
			ScoreState ss2 = new ScoreState(v2.get(i).score1, v2.get(i).score2);
			
			System.out.println("########## player1의 차례입니다 ###########");
			score = cs.Score(i); 					// ith Frame score compute
			totalScore1 += score;
			
			sp.printFrameNum(i);
			System.out.println("첫번째 구를 치실 준비가 되시면  아무키나 눌러주세요!");
			String ans = sc.next();
			sp.printPoint1(i);
			if(v.get(i).score1!=10) { // 스트라이크면 두번째 구를 칠 필요가 없음 
				System.out.println("두번째 구를 치실 준비가 되시면  아무키나 눌러주세요!");
				String ans2 = sc.next();
				sp.printPoint2(i);
			}
			if(i==9) {
				if(check == -1)
					sp.printPoint3(i);
			}
			
			// spare나 strike면  total 점수는 다음 프레임을 진행한 후에 출력한다. 
			if(ss.IsSpare() || ss.IsStrike()) {
				tmp = totalScore1; 
				System.out.println("보너스 점수가 있습니다. 다음 프레임을 치면 total점수를 계산합니다.");
			}
			else {
				System.out.println("total :"+totalScore1);
			}
			System.out.println("total :"+totalScore1);
			System.out.println();
			
			// for player2
			ComputeScore cs2 = new ComputeScore(v2,totalScore2);
			ScorePrint sp2 = new ScorePrint(v2,totalScore2);
			System.out.println("########## player2의 차례입니다 ###########");
			score = cs2.Score(i); 					// ith Frame score compute
			totalScore2 += score;
				
			sp2.printFrameNum(i);
			System.out.println("첫번째 구를 치실 준비가 되시면  아무키나 눌러주세요!");
			String ans3 = sc.next();
			sp2.printPoint1(i);
			if(v2.get(i).score1!=10) { // 스트라이크면 두번째 구를 칠 필요가 없음 
				System.out.println("두번째 구를 치실 준비가 되시면  아무키나 눌러주세요!");
				String ans4 = sc.next();
				sp2.printPoint2(i);
			}
			
			if(i==9) {
				if(check==-1)
					sp2.printPoint3(i);
			}
			// spare나 strike면  total 점수는 다음 프레임을 진행한 후에 출력한다. 
			if(ss2.IsSpare() || ss2.IsStrike()) {
				tmp = totalScore2; 
				System.out.println("보너스 점수가 있습니다. 다음 프레임을 치면 total점수를 계산합니다.");
			}
			else {
				System.out.println("total :"+totalScore2);
			}

			System.out.println();
			
			
		}
		sc.close();
	}
}



