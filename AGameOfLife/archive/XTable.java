package net.ehaumont.xutilities;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Contient des méthodes utiles pour manipuler des tableaux.
 * Gère principalement les tableaux de String, int et double.
 * @author Edern Haumont
 * @version 0.4
 */
public class XTable {

	/**
	 * Extrait un String[][] d'un fichier.
	 * @since 0.1
	 * @param path chemin du fichier
	 * @param sep séparateur
	 * @param width largeur
	 * @param height hauteur
	 * @return String[][] généré
	 */
	public static String[][] fromFile(String path, char sep, int width, int height){
		
		String[][] sTab = new String[height][width];
		for(int a=0; a<height; a++) for (int b=0; b<width; b++) sTab[a][b]="";
		try {
			InputStream ips=new FileInputStream(path); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			
			String line;
			int i=0;
			while ((line=br.readLine())!=null){
				int j=0;
				for (int a=0; a<line.length(); a++){
					if (line.charAt(a) == sep)
						j++;
					else sTab[i][j]+=line.charAt(a);
				}
				i++;
			}
			br.close();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
	    }catch (IOException e){
	    	e.printStackTrace();
	    }
		
		return sTab;
	}
	
	/**
	 * @since 0.1
	 * @param sTab
	 * @return tableau converti
	 */
	public static int[][] toInt(String[][] sTab){
		int height = sTab.length;
		int width = sTab[0].length;
		int[][] iTab = new int[height][width];
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				iTab[i][j]=Integer.parseInt(sTab[i][j]);
		return iTab;
	}
	
	/**
	 * @since 0.1
	 * @param sTab
	 * @return tableau converti
	 */
	public static double[][] toDouble(String[][] sTab){
		int height = sTab.length;
		int width = sTab[0].length;
		double[][] dTab = new double[height][width];
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				dTab[i][j]=Double.parseDouble(sTab[i][j]);
		return dTab;
	}
	
	/**
	 * @since 0.1
	 * @param iTab
	 * @return tableau converti
	 */
	public static String[][] toString(int[][] iTab){
		int height = iTab.length;
		int width = iTab[0].length;
		String[][] sTab = new String[height][width];
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				sTab[i][j]=String.valueOf(iTab[i][j]);
		return sTab;
	}
	
	/**
	 * @since 0.1
	 * @param dTab
	 * @return tableau converti
	 */
	public static String[][] toString(double[][] dTab){
		int height = dTab.length;
		int width = dTab[0].length;
		String[][] sTab = new String[height][width];
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				sTab[i][j]=String.valueOf(dTab[i][j]);
		return sTab;
	}
	
	/**
	 * @since 0.4
	 * @param sTab
	 * @param sep seperator
	 */
	public static void affTab(Object[][] sTab, String sep){
		int height = sTab.length;
		int width = sTab[0].length;		
		for(int i=0; i< height; i++){
			System.out.print("\n"+sep);
			for(int j=0; j<width; j++)   
				System.out.print(sTab[i][j]+sep);
		}
	}
	
	/**
	 * @since 0.1
	 * @param sTab
	 * @param sep seperator
	 */
	public static void affTab(String[][] sTab, String sep){
		int height = sTab.length;
		int width = sTab[0].length;	
		for(int i=0; i<height; i++){
			System.out.print("\n"+sep);
			for(int j=0; j<width; j++)
				System.out.print(sTab[i][j]+sep);
		}
	}
	
	/**
	 * @since 0.1
	 * @param sTab
	 * @param sep seperator
	 */
	public static void affTab(int[][] sTab, String sep){
		int height = sTab.length;
		int width = sTab[0].length;		
		for(int i=0; i< height; i++){
			System.out.print("\n"+sep);
			for(int j=0; j<width; j++)   
				System.out.print(sTab[i][j]+sep);
		}
	}
	
	/**
	 * @since 0.1
	 * @param sTab
	 * @param sep seperator
	 */
	public static void affTab(double[][] sTab, String sep){
		int height = sTab.length;
		int width = sTab[0].length;		
		for(int i=0; i< height; i++){
			System.out.print("\n"+sep);
			for(int j=0; j<width; j++)   
				System.out.print(sTab[i][j]+sep);
		}
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static double[][] add(double[][] tab1, double[][] tab2){
		int height = tab1.length;
		int width = tab1[0].length;
		double[][] res = new double[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] + tab2[i][j];
			return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static String[][] add(String[][] tab1, String[][] tab2){
		int height = tab1.length;
		int width = tab1[0].length;
		String[][] res = new String[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] + tab2[i][j];
			return res;
	}

	/**
	 * @since 0.1
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static int[][] add(int[][] tab1, int[][] tab2){
		int height = tab1.length;
		int width = tab1[0].length;
		int[][] res = new int[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] + tab2[i][j];
			return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static double[][] multiply(double[][] tab1, double[][] tab2){
		int height = tab1.length;
		int width = tab1[0].length;
		double[][] res = new double[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] * tab2[i][j];
			return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static int[][] multiply(int[][] tab1, int[][] tab2){
		int height = tab1.length;
		int width = tab1[0].length;
		int[][] res = new int[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] + tab2[i][j];
			return res;
	}
	
	/**
	 * @since 0.4
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static int[] cartesianX(int[] tab1, int[] tab2){
		int length1 = tab1.length, length2 = tab2.length;
		int[] res = new int[length1*length2];
		for (int a=0; a<length1; a++)
			for (int b=0; b<length2; b++)
				res[a*length2+b] = tab1[a]+tab2[b];
		return res;
	}
	
	/**
	 * @since 0.4
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static double[] cartesianX(double[] tab1, double[] tab2){
		int length1 = tab1.length, length2 = tab2.length;
		double[] res = new double[length1*length2];
		for (int a=0; a<length1; a++)
			for (int b=0; b<length2; b++)
				res[a*length2+b] = tab1[a]+tab2[b];
		return res;
	}
	
	/**
	 * @since 0.4
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static int[][] matricialX(int[][] tab1, int[][] tab2){
		int height1 = tab1.length;
		int width1 = tab1[0].length, width2 = tab2[0].length;
		int[][] res = new int [height1] [width2];
		for (int i=0; i<res.length; i++)
			for (int j=0; j<res[0].length; j++) {
				for (int a=0; a<width1; a++)
					res[i][j] += tab1[i][a]+tab2[a][j];
			}
		return res;
	}
	
	/**
	 * @since 0.4
	 * @param tab1
	 * @param tab2
	 * @return resultat
	 */
	public static double[][] matricialX(double[][] tab1, double[][] tab2){
		int height1 = tab1.length;
		int width1 = tab1[0].length, width2 = tab2[0].length;
		double[][] res = new double [height1] [width2];
		for (int i=0; i<res.length; i++)
			for (int j=0; j<res[0].length; j++) {
				for (int a=0; a<width1; a++)
					res[i][j] += tab1[i][a]+tab2[a][j];
			}
		return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param a2
	 * @return resultat
	 */
	public static double[][] add(double[][] tab1, double a2){
		int height = tab1.length;
		int width = tab1[0].length;
		double[][] res = new double[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] + a2;
			return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param a2
	 * @return resultat
	 */
	public static int[][] add(int[][] tab1, int a2){
		int height = tab1.length;
		int width = tab1[0].length;
		int[][] res = new int[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] + a2;
			return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param a2
	 * @return resultat
	 */
	public static double[][] multiply(double[][] tab1, double a2){
		int height = tab1.length;
		int width = tab1[0].length;
		double[][] res = new double[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] * a2;
			return res;
	}
	
	/**
	 * @since 0.1
	 * @param tab1
	 * @param a2
	 * @return resultat
	 */
	public static int[][] multiply(int[][] tab1, int a2){
		int height = tab1.length;
		int width = tab1[0].length;
		int[][] res = new int[height][width];
		for (int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				res[i][j] = tab1[i][j] * a2;
			return res;
	}
	
	/**
	 * @since 0.4
	 * @param tab
	 * @param n ligne
	 * @return tableau modifié
	 */
	public static Object[][] delLine(Object [][] tab, int n){
		int height = tab.length;
		int width = tab[0].length;
		int x=0;
		Object [][] res = new Object[height-1][width];
		for (int i=0; i<height; i++)
			if (i!=n){
				res[x] = Arrays.copyOf(tab[i], width);
				x++;
			}
		return res;
	}
	
	/**
	 * @since 0.2
	 * @param tab
	 * @param n ligne
	 * @return tableau modifié
	 */
	public static int[][] delLine(int [][] tab, int n){
		int height = tab.length;
		int width = tab[0].length;
		int x=0;
		int [][] res = new int[height-1][width];
		for (int i=0; i<height; i++)
			if (i!=n){
				res[x] = Arrays.copyOf(tab[i], width);
				x++;
			}
		return res;
	}

	/**
	 * @since 0.2
	 * @param tab
	 * @param n ligne
	 * @return tableau modifié
	 */
	public static double[][] delLine(double [][] tab, int n){
		int height = tab.length;
		int width = tab[0].length;
		int x=0;
		double [][] res = new double[height-1][width];
		for (int i=0; i<height; i++)
			if (i!=n){
				res[x] = Arrays.copyOf(tab[i], width);
				x++;
			}
		return res;
	}

	/**
	 * @since 0.2
	 * @param tab
	 * @param n ligne
	 * @return tableau modifié
	 */
	public static String[][] delLine(String [][] tab, int n){
		int height = tab.length;
		int width = tab[0].length;
		int x=0;
		String [][] res = new String[height-1][width];
		for (int i=0; i<height; i++)
			if (i!=n){
				res[x] = Arrays.copyOf(tab[i], width);
				x++;
			}
		return res;
	}
	
	/**
	 * Génère un tableau contenant des valeurs aléatoires comprises dans [0,1[
	 * @since 0.2
	 * @param height
	 * @param width
	 * @return tableau généré
	 */
	public static double[][] random(int height,int width){
		double[][] rTab = new double[height][width];
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++)
				rTab[i][j]=Math.random();
		}
		return rTab;
	}

	/**
	 * Inverse les lignes et les colonnes d'un tableau.
	 * @since 0.3
	 * @param tab1.
	 * @return tableau switché.
	 */
	public static double[][] switchLC(double[][] tab1){
		int width = tab1.length;
		int height = tab1[0].length;
		double[][] tab2 = new double[height][width];
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				tab2[i][j] = tab1[j][i];
		return tab2;
	}
	
	/**
	 * Inverse les lignes et les colonnes d'un tableau.
	 * @since 0.3
	 * @param tab1.
	 * @return tableau switché.
	 */
	public static int[][] switchLC(int[][] tab1){
		int width = tab1.length;
		int height = tab1[0].length;
		int[][] tab2 = new int[height][width];
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				tab2[i][j] = tab1[j][i];
		return tab2;
	}
	
	/**
	 * Inverse les lignes et les colonnes d'un tableau.
	 * @since 0.3
	 * @param tab1.
	 * @return tableau switché.
	 */
	public static String[][] switchLC(String[][] tab1){
		int width = tab1.length;
		int height = tab1[0].length;
		String[][] tab2 = new String[height][width];
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				tab2[i][j] = tab1[j][i];
		return tab2;
	}
}
