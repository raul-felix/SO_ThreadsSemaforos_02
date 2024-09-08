package controller;

import java.util.concurrent.Semaphore;

public class ThreadCozinha extends Thread {
	
	int id;
	Semaphore semaforo;
	String nome;
	double tempoPreparo;
	
	public ThreadCozinha(int id, Semaphore semaforo) {
		this.id = id;
		this.semaforo = semaforo;
		
		if (id % 2 == 1) {
			nome = "Sopa de Cebola";
			tempoPreparo = tempoRandom(0.5, 0.8);
		} else {
			nome = "Lasanha";
			tempoPreparo = tempoRandom(0.6, 1.2);
		}
	}

	@Override
	public void run() {
		cozinhar();
		// Início da sessão crítica
		try {
			semaforo.acquire();
			entregar();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			semaforo.release();
			// Fim da sessão crítica
		}
	}
	
	private void cozinhar() {
		double tempoTotal = 0.0;
		while(tempoTotal < tempoPreparo) {
			int percentual = (int) (tempoTotal * (100 / tempoPreparo));
			System.out.println(String.format("#%d %s - Percentual de preparo: %d%%", this.id, nome, percentual));
			try {
				sleep(100);
				tempoTotal += 0.1;
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println(String.format("#%d %s - PRONTO!", this.id, nome));
	}
	
	private void entregar() {
		try {
			sleep(500);
			System.out.println(String.format("#%d %s - ENTREGUE!", this.id, nome));
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private double tempoRandom(double min, double max) {
		return Math.random() * (max - min) + min;
	}

}
