package com.serhat.kmeans;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class K_Clusterer extends ReadDataset {

	public K_Clusterer() {
		// TODO Auto-generated constructor stub
	}

	public void merkez(double[] tablo, int kume) {

	}

//main method
	public static void main(String args[]) throws IOException {
		ReadDataset r1 = new ReadDataset();
		r1.features.clear();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the filename with path");
		String file = sc.next();
		r1.read(file); // load data
		//int serhat = r1.sayi;
		int ex = 1;
		do {
			System.out.println("Enter the no. of clusters");
			int k = sc.nextInt();
			System.out.println("Enter maximum iterations");
			int max_iterations = sc.nextInt();
			System.out.println("Enter distance metric 1 or 2: \n1. Euclidean\n2. Manhattan");
			int distance = sc.nextInt();
			// Hashmap to store centroids with index
			
			
			
			// benim kod bloðum burada basliyor ve amacim nesneleri ozelliklerine gore
						// kucukten buyuge siralamak
						// siraladigim nesneleri de kume sayisina oranla es bloklara ayirarak bu
						// bloklarin merkezlerini ortalama
						// degerler uzerinden belirleyerek bunlari merkez noktalar olarak ele almak

						
			
						List<double[]> data = r1.features;
						ArrayList<double[]> mMatrisi = new ArrayList<>();
						int nesneSayisi = sayi;
						int boyut = nesneSayisi / k; //her kumede kac eleman olduðu hesaplanir

						//her sutundaki elemanlar bir array icerisine alinir
						
						for (int i = 0; i < kolon; i++) {
							double[] temp = new double[nesneSayisi];
							int counter = 0;
							for (double[] iter : data) {
								temp[counter] = iter[i];
								counter++;
							}
							Arrays.sort(temp);  //array siralanir
							double[] temp1 = new double[kolon];  //sutun ayrildigi kume sayisi kadar merkeze sahip olacaktir
							
							//merkezler siralanmis elemanlarin ortalamalarýndan elde edilerek bir merkez matrisine eklenir
							
							for (int j = 0; j < k; j++) {
								int min = j * boyut;
								int max = min + boyut;
								double toplam = 0;
								if (max > nesneSayisi)
									max = nesneSayisi;
								for (int l = min; l < max; l++)
									toplam += temp[l];
								toplam /= boyut;
								temp1[j] = toplam;
							}
							mMatrisi.add(temp1);
						}

						//merkez matrisini bulduk ama kolonlar arasýndaki yakin noktalarý da bulmamiz gerekli
						//bunun icin bir ozellige gore siralama yapilir ve siralanmamis elemanlarin siralanmis 
						//elemanlardan elde edilen merkezlerden hangisine daha yakin olduguna bagli olarak
						//koordinat eslestirmesi yapilir.
						
						for (int i = 0; i < kolon; i++) {
							List<double[]> temp = data;
							double[] merkez = new double[kolon];
							double[] first = new double[kolon];
							double[] mid = new double[kolon];
							double[] next = new double[kolon];
							if (i == 0) {
								int counter = 0;
								while (counter < nesneSayisi - 1) {
									first = temp.get(counter);
									next = temp.get(counter + 1);
									if (first[i + 1] > next[i + 1]) {
										mid = first;
										temp.set(counter, next);
										temp.set(counter + 1, mid);
										counter = 0;
									} else {
										counter++;
									}
								}
								double[] temp1 = new double[nesneSayisi];
								int c = 0;
								for (double[] iter : temp) {
									temp1[c] = iter[i + 1];
									c++;
								}
								for (int j = 0; j < k; j++) {
									int min = j * boyut;
									int max = min + boyut;
									double toplam = 0;
									if (max > nesneSayisi)
										max = nesneSayisi;
									for (int l = min; l < max; l++)
										toplam += temp1[l];
									toplam /= boyut;
									merkez[j] = toplam;
								}
								double m[] = mMatrisi.get(i + 1);

								for (int j = 0; j < k; j++) {
									int minIndex = 0;
									double fark = Math.abs(merkez[j] - m[j]);
									for (int l = j; l < k; l++) {
										if (Math.abs(merkez[j] - m[l]) < fark) {
											minIndex = l;
											fark = Math.abs(merkez[j] - m[l]);
										}
										double tempValue = m[j];
										m[j] = m[minIndex];
										m[minIndex] = tempValue;
									}
								}
								mMatrisi.set(i + 1, m);
							}

						}

						/*System.out.println("Matris:");
						for (double[] iter : mMatrisi) {
							System.out.println("Özellik:");
							for (int i = 0; i < k; i++) {
								System.out.println(iter[i]);
							}
						}*/
						
						
						//Ben merkez matrisin her array dizisinde bir özellige ait noktalari tutuyordum fakat algoritma
						//noktalarin koordinatlari ile calistigi icin her elemanda bir noktaya ait tüm özelliklerin 
						//koordinati olarak yeni bir liste yarattim.
						
						ArrayList<double[]> cDimension= new ArrayList<>();
						for(int i = 0; i<k ; i++) {
							int counter = 0;
							double[] temp = new double[kolon];
							for(double[] iter : mMatrisi) {
								temp[counter] = iter[i];
								counter++;
							}
							cDimension.add(temp);
						}
						
						

						// kod blogum burada bitiyor. Bu asamada kaynak kod benim merkez belirleme
						// yontemim uzerinden calismiyor. Sadece
						// kullandigim yontemin buldugu merkez noktalari denemek icin bir merkez
						// noktalar matrisini ekrana bastiriyor.
						
						
						//Ýlerleyen satirlarda basalngic degerlerini benim hesapladiklarimla sececek sekilde algoritmayi modifiy
			
			
			
			Map<Integer, double[]> centroids = new HashMap<>();
			// calculating initial centroids
			double[] x1 = new double[numberOfFeatures];
			int r = 0;
			for (int i = 0; i < k; i++) {
				
				//x1 = r1.features.get(r++);
				
				//Yukaridaki satiri iptal ederek baslangic degerini kendi yontemim ile hesapladýgým degerlerle
				//degistirdim.
				
				x1 = cDimension.get(i);
				centroids.put(i, x1);

			}
			

			
			// Hashmap for finding cluster indexes
			Map<double[], Integer> clusters = new HashMap<>();
			clusters = kmeans(r1.features, distance, centroids, k);
			// initial cluster print
			/*
			 * for (double[] key : clusters.keySet()) { for (int i = 0; i < key.length; i++)
			 * { System.out.print(key[i] + ", "); } System.out.print(clusters.get(key) +
			 * "\n"); }
			 */
			double db[] = new double[numberOfFeatures];
			// reassigning to new clusters
			for (int i = 0; i < max_iterations; i++) {
				if(i == 0) {
					clusters.clear();
					clusters = kmeans(r1.features, distance, centroids, k);
				} else {
					for (int j = 0; j < k; j++) {
						List<double[]> list = new ArrayList<>();
						for (double[] key : clusters.keySet()) {
							if (clusters.get(key) == j) {
								list.add(key);
//						for(int x=0;x<key.length;x++){
//							System.out.print(key[x]+", ");
//							}
//						System.out.println();
							}
						}
						db = centroidCalculator(list);
						centroids.put(j, db);

					}
					clusters.clear();
					clusters = kmeans(r1.features, distance, centroids, k);
				}
				

			}

			// final cluster print
			System.out.println("\nFinal Clustering of Data");
			System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");
			for (double[] key : clusters.keySet()) {
				for (int i = 0; i < key.length; i++) {
					System.out.print(key[i] + "\t \t");
				}
				System.out.print(clusters.get(key) + "\n");
			}

			// Calculate WCSS
			double wcss = 0;

			for (int i = 0; i < k; i++) {
				double sse = 0;
				for (double[] key : clusters.keySet()) {
					if (clusters.get(key) == i) {
						sse += Math.pow(Distance.eucledianDistance(key, centroids.get(i)), 2);
					}
				}
				wcss += sse;
			}
			String dis = "";
			if (distance == 1)
				dis = "Euclidean";
			else
				dis = "Manhattan";
			System.out.println("\n*********Results************\nDistance Metric: " + dis);
			System.out.println("Iterations: " + max_iterations);
			System.out.println("Number of Clusters: " + k);
			System.out.println("WCSS: " + wcss);
			System.out.println("Press 1 if you want to continue else press 0 to exit..");

			

			ex = sc.nextInt();

		} while (ex == 1);

	}

	// method to calculate centroids
	public static double[] centroidCalculator(List<double[]> a) {

		int count = 0;
		// double x[] = new double[ReadDataset.numberOfFeatures];
		double sum = 0.0;
		double[] centroids = new double[ReadDataset.numberOfFeatures];
		for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
			sum = 0.0;
			count = 0;
			for (double[] x : a) {
				count++;
				sum = sum + x[i];
			}
			centroids[i] = sum / count;
		}
		
		return centroids;

	}

	// method for putting features to clusters and reassignment of clusters.
	public static Map<double[], Integer> kmeans(List<double[]> features, int distance, Map<Integer, double[]> centroids,
			int k) {
		Map<double[], Integer> clusters = new HashMap<>();
		int k1 = 0;
		double dist = 0.0;
		for (double[] x : features) {
			double minimum = 999999.0;
			for (int j = 0; j < k; j++) {
				if (distance == 1) {
					dist = Distance.eucledianDistance(centroids.get(j), x);
				} else if (distance == 2) {
					dist = Distance.manhattanDistance(centroids.get(j), x);
				}
				if (dist < minimum) {
					minimum = dist;
					k1 = j;
				}

			}
			clusters.put(x, k1);
		}

		return clusters;

	}

}