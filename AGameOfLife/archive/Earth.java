import java.util.Scanner;

import net.ehaumont.xutilities.XTable;

public class Earth {
	public static int[][] world;

	public static int howManyFriends(int[][] tab, int line, int row){
		int height = tab.length, width = tab[0].length;
		int count = 0;
		int a =(line==0)? 0 : 1;
		int b =(line==height-1)? 0 :1;
		int c =(row==0)? 0 : 1;
		int d =(row==width-1)? 0 : 1;
		for (int i=line-a; i<=line+b; i++)
			for (int j=row-c; j<=row+d; j++)
				if (tab[i][j]==1)
					count++;
		if(tab[line][row] == 1) count--;
		return count;
	}
	
	public static int [][] evolutionRetour (int [][] univ){
		int height = univ.length, width = univ[0].length;
		int[][] newUniv = new int[height][width];
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++) {
				int friends = howManyFriends(univ,i,j);
				if (univ[i][j] == 0)
					newUniv[i][j] = (friends == 3)? 1 : 0;
				else newUniv[i][j] = (friends > 1 && friends < 4)? 1 : 0;
			}
		return newUniv;
	}
	
	public static void evolutionParam (int[][] univ) {
		world = evolutionRetour(univ);
	}
	
	public static boolean apocalypse(int [][] univ) {
		int height = univ.length, width = univ[0].length;
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				if (univ[i][j] == 1) return false;
		return true;
	}
	
	public static int[][] random(int height, int width) {
		int[][] rTab = new int[height][width];
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++)
				rTab[i][j]=(int)(Math.random()*2);
		}
		return rTab;
	}
	
	public static void main (String args[]) {
	Scanner sc = new Scanner(System.in);
	System.out.println("entrée fichier ou random ?");
	int choice;
	if("random".equals(sc.nextLine())) choice = 1;
	else choice = 2;
	System.out.println("temps de latence en millisecondes ?");
	int latency = sc.nextInt();
	System.out.print("\nhauteur du monde ?");
	int height = sc.nextInt();
	System.out.print("largeur du monde ?");
	int width = sc.nextInt();
	if(choice == 1) world = random(height,width);
	else world = XTable.toInt(XTable.fromFile("test1.txt", ' ', height, width));
	MyWindow window = new MyWindow();
	System.out.print("\nCombien de jours avant destruction ?");
	int daysRemaining = sc.nextInt();
	
	for (int day=1; day<=daysRemaining; day++) {
		evolutionParam(world);
		window.repaint();
		if(apocalypse(world)) day = daysRemaining;
		try {
		    Thread.sleep(latency);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	}
}
