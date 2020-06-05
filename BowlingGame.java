import java.util.*;

// �ش� Frame���� �� ����� ������ִ� �Լ� 
class ScorePrint{
	Vector<Point>v = new Vector<Point>();
	int total;
	
	public ScorePrint(Vector<Point>v,int total) {
		this.v = v; this.total = total;
	}
	// �����ӳѹ� ����ϴ� �Լ� 
	public void printFrameNum(int FrameNum) {
		Point p = v.get(FrameNum);
		System.out.println("Frame #"+(FrameNum+1));
	}
	// point1����ϴ� �ż���
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
	// point2 ����ϴ� �ż��� 
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
			//�� ���� ���� ġ�� �ȵǴ� ��쿡 �ش��Ѵ�. 
			System.out.println("point2:"+" ");
		}
		else if(p.score1+p.score2 == 10) {
			System.out.println("point2:"+ tmp2[2]);
		}
		else {
			System.out.println("point2:"+p.score2);
		}
		
		
	}
	// point3 ����ϴ� �ż��� 
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
	
	// total����ϴ� �ż��� 
	public void printTotal(int FrameNum) {
		Point p = v.get(FrameNum);
		System.out.println("total:"+total);
	}
	
}

// ��� board�� ��� 



// ���ھ��� ���� �Ǵ�(spare, normal, strike ->true, false�Ǵ�)
class ScoreState{
	int first; int second;
	// �Է¹��� ���� �޴´�. 
	public ScoreState(int first, int second) {
		this.first=first; this.second = second;
	}
	// �Ϲ������� ���� ����ϴ� ��������� �Ǵ��ϴ� �ż��� 
	public boolean Simple() {
		if(first+second<10)
			return true;
		else
			return false;
	}
	// spare�� �Ǵ� ��������� �Ǵ��ϴ� �ż��� 
	public boolean IsSpare() {
		if(first+second == 10 && first!=10)
			return true;
		else
			return false;
	}
	//strike������ �Ǵ��ϴ� �ż���
	public boolean IsStrike() {
		if(first == 10)
			return true;
		else
			return false;
	}
	
}

// �� point���� ���� �޾��� �� ������ ����ϴ� Ŭ���� �ۼ� 
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
				//������� ���, score3��� 
				score = Lastp.score1 + Lastp.score3;
			}
			else if (Lastss.IsStrike()) {
				// ��Ʈ����ũ�� ��쵵 ���� �����ӱ��� �����ؾ��Ѵ�
				score = Lastp.score3+Lastp.score1+Lastp.score2;
			}
			else {
				// simple compute�� ��� 
				score = Lastp.score1+Lastp.score2;
			}
		}
		else {
			Point p = v.get(FrameNum);
			Point p2 = v.get(FrameNum+1);
			ScoreState ss = new ScoreState(p.score1, p.score2);

			if(ss.IsSpare()) { 
				//������� ���� ���� �����ӱ��� �����ؾ��Ѵ�
				score = p2.score1 + 10;
			}
			else if (ss.IsStrike()) {
				p.score2 = 0; // ��Ʈ����ũ�� ���� ���� ġ�� �ʰ� �Ѿ��. 
				// ��Ʈ����ũ�� ��쵵 ���� �����ӱ��� �����ؾ��Ѵ�
				score = 10 + p2.score1 + p2.score2;
			}
			else {
				// simple compute�� ��� 
				score = p.score1+p.score2;
			}
		}
		total += score; //�Ѱ�
		
		return score; // this Frame�� score�� ���
	}
	
}

// ù��°��, �ι�°�������� ������ ���� Ŭ���� �ۼ� 
class playGame{
	String level; 
	
	// �̿����� ������ �Է¹޴´� -> ���ؿ� ���� �ٸ��� ���� 
	public playGame(String level) {
		this.level = level; 
	}
	
	// �� �����ӿ��� ù��° ��
	public int firstBowl(){
		int score1 = 0;
		
		//�ʺ���
		if(level.equals("�ʱ���")) {
			// ���� �� �ִ� ����� Ȯ����� ��� 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if(score1>10) {
				score1 = 0; //�ᱹ -�� G�� �ش��ϹǷ� 0���� �ش��Ѵ�. ����� -�� G
			}
		}
		else if(level.equals("�߱���")) {
			// ���� �� �ִ� ����� Ȯ����� ��� 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if(score1>10)
				score1 = 0;
		}
		else {
			// ���� �� �ִ� ����� Ȯ����� ��� 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if(score1>10)
				score1 = 0;
		}
		return score1;
	}
	// �� �����ӿ��� �ι�° �� 
	public int secondBowl() {
		int score2 = 0;
		
		// �ʺ���
		if(level.equals("�ʱ���")) {
			Random ran = new Random();
			score2 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if (score2>10) //���������� -�� G���� �ϳ��̹Ƿ� 0���� �ش��Ѵ�. 
				score2 = 0;
		}
		else if(level.equals("�߱���")) {
			Random ran = new Random();
			score2 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if(score2 > 10)
				score2 = 0;
		}
		else {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12�� �ϳ��� �� ���� 
			if(score2>10)
				score2 = 0;
		}
		return score2;
	}
	// 10 Frame������ ����°���� ����� ����� �� ���� �ż��� 
	public int thirdBowl() {
		int score3 = 0;
		
		// �ʺ���
		if(level.equals("�ʱ���")) {
			Random ran = new Random();
			score3 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if (score3>10) //���������� -�� G���� �ϳ��̹Ƿ� 0���� �ش��Ѵ�. 
				score3 = 0;
		}
		else if(level.equals("�߱���")) {
			Random ran = new Random();
			score3 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
			if(score3 > 10)
				score3 = 0;
		}
		else {
			Random ran = new Random();
			score3 = ran.nextInt(13)+1; //1~12�� �ϳ��� �� ���� 
			if(score3>10)
				score3 = 0;
		}
		return score3;
	}
	
}

// �� �������� �� �� score1,score2, score3�� �����ϴ� Ŭ���� (���Ϳ� �� ��ü�� 10�� ������ ����)
class Point{
	public int score1, score2, score3;
	public Point(int score1, int score2, int score3) {
		this.score1 = score1; this.score2 = score2; this.score3 = score3;
	}
	// ��ü�� ���� print�ϴ� ���
	@Override
	public String toString() {
		return "point1:"+score1+", point2:"+score2 +",point3:"+score3;
	}
}


public class BowlingGame {
	public static void main(String[] args) {
		Vector<Point>v = new Vector<Point>(); 		//�α��� �ϳ��� ���������� 10���� �������� ������ ���� 
		Vector<Point>v2 = new Vector<Point>(); 		//�α��� �ϳ��� ���������� 10���� �������� ������ ���� 
		Scanner sc = new Scanner(System.in);		
		int check = 0;
		System.out.println("######### Bowling Game Start !! #########");
		System.out.print("������ �������ּ��� ex(�ʱ���/�߱���/�����) >>");
		String level = sc.next(); 
		int score1, score2, score,tmp;
		int totalScore1 = 0;
		int totalScore2 = 0;

		// ������ ���ؿ� ���� �� 10�������� �����Ѵ�. 
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
				// spare�� ������ ��� 
				if(ss.IsSpare()) {
					check = -1;
					int score3 = pg.thirdBowl();
					v.add(new Point(score1, score2,score3));
				}
				// strike�� ������ ���
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
				// spare�� ������ ��� 
				if(ss.IsSpare()) {
					check = -1;
					int score3 = pg.thirdBowl();
					v2.add(new Point(score1, score2,score3));
				}
				// spike�� ���´� ���
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

		// ������ �� ���
		for(int i=0; i<10; i++) {
			// for player1
			ComputeScore cs = new ComputeScore(v,totalScore1);
			ScorePrint sp = new ScorePrint(v,totalScore1);
			ScoreState ss = new ScoreState(v.get(i).score1, v.get(i).score2);
			ScoreState ss2 = new ScoreState(v2.get(i).score1, v2.get(i).score2);
			
			System.out.println("########## player1�� �����Դϴ� ###########");
			score = cs.Score(i); 					// ith Frame score compute
			totalScore1 += score;
			
			sp.printFrameNum(i);
			System.out.println("ù��° ���� ġ�� �غ� �ǽø�  �ƹ�Ű�� �����ּ���!");
			String ans = sc.next();
			sp.printPoint1(i);
			if(v.get(i).score1!=10) { // ��Ʈ����ũ�� �ι�° ���� ĥ �ʿ䰡 ���� 
				System.out.println("�ι�° ���� ġ�� �غ� �ǽø�  �ƹ�Ű�� �����ּ���!");
				String ans2 = sc.next();
				sp.printPoint2(i);
			}
			if(i==9) {
				if(check == -1)
					sp.printPoint3(i);
			}
			
			// spare�� strike��  total ������ ���� �������� ������ �Ŀ� ����Ѵ�. 
			if(ss.IsSpare() || ss.IsStrike()) {
				tmp = totalScore1; 
				System.out.println("���ʽ� ������ �ֽ��ϴ�. ���� �������� ġ�� total������ ����մϴ�.");
			}
			else {
				System.out.println("total :"+totalScore1);
			}
			System.out.println("total :"+totalScore1);
			System.out.println();
			
			// for player2
			ComputeScore cs2 = new ComputeScore(v2,totalScore2);
			ScorePrint sp2 = new ScorePrint(v2,totalScore2);
			System.out.println("########## player2�� �����Դϴ� ###########");
			score = cs2.Score(i); 					// ith Frame score compute
			totalScore2 += score;
				
			sp2.printFrameNum(i);
			System.out.println("ù��° ���� ġ�� �غ� �ǽø�  �ƹ�Ű�� �����ּ���!");
			String ans3 = sc.next();
			sp2.printPoint1(i);
			if(v2.get(i).score1!=10) { // ��Ʈ����ũ�� �ι�° ���� ĥ �ʿ䰡 ���� 
				System.out.println("�ι�° ���� ġ�� �غ� �ǽø�  �ƹ�Ű�� �����ּ���!");
				String ans4 = sc.next();
				sp2.printPoint2(i);
			}
			
			if(i==9) {
				if(check==-1)
					sp2.printPoint3(i);
			}
			// spare�� strike��  total ������ ���� �������� ������ �Ŀ� ����Ѵ�. 
			if(ss2.IsSpare() || ss2.IsStrike()) {
				tmp = totalScore2; 
				System.out.println("���ʽ� ������ �ֽ��ϴ�. ���� �������� ġ�� total������ ����մϴ�.");
			}
			else {
				System.out.println("total :"+totalScore2);
			}

			System.out.println();
			
			
		}
		sc.close();
	}
}



