package com.threed;

import org.junit.jupiter.api.Test;

import com.threed.helper.Counter;

import lombok.SneakyThrows;

// import org.springframework.boot.test.context.SpringBootTest;
import java.lang.InterruptedException;

// @SpringBootTest
class SpringJavaThreadApplication {

	@Test
	void contextLoads() {
	}

    /**
     * kode ini merepresentasikan Theread yang sedang di gunakan saat ini
     */
    @Test
    void currentThreed() {
		String name = Thread.currentThread().getName();
		System.out.println("Threed saat ini yang di gunakan adalah "+name);
	}

    /*
     * Cara membuat kita bisa buat Runnable terlebih dahulu
     * Runnable adalah sebuah interface yang kita bisa gunakan untuk membuat proses Thread
     * Setelah membuat Runnable kita bisa Membuat Thread dan diikuti deggan Runnable yang telah ktia buat
     * sebelumnya
     * setelah kita buat Thread nya, untuk menjalankan Thread nya ktia harus memanggil method yang bernama start();
     * dan prohram therad yang kita buat ini akan berjalan secara asyncronus
     */
    @Test
    void createThread() {
		Runnable runnable = () -> {
			System.out.println("Ini thread yang ke : "+ Thread.currentThread().getName());
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

    /**
     * contoh penggunaan Thread.sleep(timeMilis);
     */
    @Test
    void threadSleep() {
		Runnable runnable = () -> {
			try{
				Thread.sleep(1000);
				System.out.println("Ini thread dengan nama : "+ Thread.currentThread().getName());
			}catch(InterruptedException ITX){
				ITX.printStackTrace();
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
		// ini ktia sleep thread nya selama 2 detik agar aksi print out yang ada pada Runnabel dapat ditampilkan
		// menga apa saya lakukan seperti ini ? karna program ini itu berjalanya secara asynycronus 
		try{
			Thread.sleep(2000);
		}catch(InterruptedException ITX){
			ITX.printStackTrace();
		}
		System.out.println("selesai");
	}

    /**
     * yang kita lakukan pada method testThreadSleep(); untuk melakukan menyunggu eksekusi program pada Object Runnable tidak lah baik, lebih baik kita menggunakan Thread.join();
     */
    @Test
    void threadJoin() {

		Runnable runnable = () -> {
			try{
				Thread.sleep(1000);
				System.out.println("Sekarang Sedang menggunakan thread : "+Thread.currentThread().getName());
			}catch(InterruptedException ITX) {
				ITX.printStackTrace();
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
		try {
			//setelah Thread di start maka kode eksekusi program ini akan di block oleh thread.join(); untuk menngunggy eksekusi program yang ada pada Runnable selesai
			thread.join();
			System.out.println("Selesai");
		}catch(InterruptedException ITX) {
			ITX.printStackTrace();
		}
	}

    @Test
    void interrupt() {
		Runnable runnable = () -> {
			//contoh seumpamanya disini melakukan proses yang lumayan komplex sehingga membutuhkan beberapa waktu
			for(var i = 0; i < 10; i++) {
				System.out.println("Proses Komplex Sedang di Exsekusi ke "+i);
				// jika Thread.interrupted() bernilai true maka eksekusi program pada runnable akan dihentikan
				try{
					Thread.sleep(1000);
				}catch(InterruptedException ITX) {
					return;
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
		// disini kita simulasikan jika program pada Runnable itu dierksekusi dan membuathkan waktu yang lebih dari 2 detik maka kita akan berhentikan program pada Runnable tersebut
		try {
			Thread.sleep(2000);
			// disini kita kirim sinyal interupt setelah 2 detik dan program pada Runnable akan di berhentikan
			thread.interrupt();
			System.out.println("Proses Eksekusi pada Runnable Selesai");
			thread.join();
			System.out.println("Program Selesai");
		}catch(InterruptedException ITX) {
			ITX.printStackTrace();
		}
	}


    /**
     * dalam real projek nanti kita akan menggunakan cara seperti ini karna untuk proses 
     * thread yang membutuhkan waktu itu nga di lakukan secara manual seperti kita menggunakan Thread.sleep() dalam Object Runnable dalam real case nya.
     */
    @Test
    void interruptReal() {
		Runnable runnable = () -> {
			//contoh seumpamanya disini melakukan proses yang lumayan komplex sehingga membutuhkan beberapa waktu
			for(var i = 0; i < 10; i++) {
				System.out.println("Proses Komplex Sedang di Exsekusi ke "+i);
				// jika Thread.interrupted() bernilai true maka eksekusi program pada runnable akan dihentikan
				if(Thread.interrupted()){
					return;
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
		// disini kita simulasikan jika program pada Runnable itu dierksekusi dan membuathkan waktu yang lebih dari 2 detik maka kita akan berhentikan program pada Runnable tersebut
		try {
			Thread.sleep(2000);
			// disini kita kirim sinyal interupt setelah 2 detik dan program pada Runnable akan di berhentikan
			thread.interrupt();
			System.out.println("Proses Eksekusi pada Runnable Selesai");
			thread.join();
			System.out.println("Program Selesai");
		}catch(InterruptedException ITX) {
			ITX.printStackTrace();
		}
	}


    @Test
    void tesThreadSetName() {
		Thread thread = new Thread(() -> {
			System.out.println("sedang jalan pada thread : "+Thread.currentThread().getName());
		});
		thread.setName("Fetching_Thirdparti_Api");
		thread.start();
		

		//atau kita juga bisa langsung meng set name nya pada constructor saat kita meng inisialisasi object Thread nya
		Thread thread2 = new Thread(() -> {
			System.out.println("sedang menjalakan thread dengan nama : "+Thread.currentThread().getName()); }, "Fetching_Payment");
			thread2.start();
	}


    @Test
    void threadState() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getState());
				System.out.println("test thread "+Thread.currentThread().getName());
			} }, "thread_state");
			try{
				System.out.println(thread.getState());
				thread.start();
				thread.join();
				System.out.println(thread.getState());
			}catch(InterruptedException ITX){
				ITX.printStackTrace();
			}
	}

	@Test @SneakyThrows
	public void testRaceConditon(){
		Counter counter = new Counter();
		Runnable runnable = () -> {
			for (int i = 0; i < 1000; i++) {
				counter.increment();
			}
		};
		
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);

		thread1.start();
		thread2.start();
		thread3.start();

		thread1.join();
		thread2.join();
		thread3.join();

		System.out.println(counter.getIncrement());
	}
}
