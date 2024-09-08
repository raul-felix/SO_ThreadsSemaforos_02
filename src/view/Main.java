package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCozinha;

public class Main {
	
	public static void main(String[] args) {
		
		int permissoes = 1;
		Semaphore semaforo = new Semaphore(permissoes);
		
		for (int i = 1; i <= 5; i++) {
			Thread threadCozinha = new ThreadCozinha(i, semaforo);
			threadCozinha.start();
		}
		
	}

}
