import java.util.*;

// ���� �������� ����� ����ϴ� Ŭ���� �ۼ� 
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
		Point p = v.get(FrameNum);
		//Point p2 = v.get(FrameNum+1);
		ScoreState ss = new ScoreState(p.score1, p.score2);

		if(ss.IsSpare()) { 
			//������� ���� ���� �����ӱ��� �����ؾ��Ѵ�
			//score = p2.score1 + 10;
		}
		else if (ss.IsStrike()) {
			// ��Ʈ����ũ�� ��쵵 ���� �����ӱ��� �����ؾ��Ѵ�
			//score = 10+p2.score1+p2.score2;
		}
		else {
			// simple compute�� ��� 
			score = p.score1+p.score2;
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
		}
		else if(level.equals("�߱���")) {
			// ���� �� �ִ� ����� Ȯ����� ��� 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
		}
		else {
			// ���� �� �ִ� ����� Ȯ����� ��� 
			Random ran = new Random();
			score1 = ran.nextInt(12)+1; //1~12�� �ϳ��� �� ���� 
		}
		return score1;
	}
	// �� �����ӿ��� �ι�° �� 
	public int secondBowl() {
		int score2 = 0;
		
		// �ʺ���
		if(level.equals("�ʱ���")) {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12�� �ϳ��� �� ���� 
		}
		else if(level.equals("�߱���")) {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12�� �ϳ��� �� ���� 
		}
		else {
			Random ran = new Random();
			score2 = ran.nextInt(13)+1; //1~12�� �ϳ��� �� ���� 
		}
		return score2;
	}
	
}

class Point{
	public int score1, score2;
	public Point(int score1, int score2) {
		this.score1 = score1; this.score2 = score2;
	}
	// ��ü�� ���� print�ϴ� ���
	@Override
	public String toString() {
		return "point1:"+score1+", point2:"+score2;
	}
}


public class BowlingGame {
	public static void main(String[] args) {
		Vector<Point>v = new Vector<Point>(); 		//�α��� �ϳ��� ���������� 10���� �������� ������ ���� 
		Scanner sc = new Scanner(System.in);		
		
		System.out.println("������ �������ּ��� ex(�ʱ���/�߱���/�����) >>");
		String level = sc.next(); 
		int score1, score2, score;
		int totalScore = 0;

		// ������ ���ؿ� ���� �� 10�������� �����Ѵ�. 
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

