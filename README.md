# Requirement
* Java dasar
* Java oop

# Concurency 
 Concurrency adalah proses eksekusi program secara satu per satu, jadi dalam pemrosesan program nya tidak dapat melakukan multiple eksekusi atau dalam 1 waktu mengeksekusi lebih dari 1 program.  

 misal nya jika kita gambarkan dalam kehidupan sehari2 adalah :  
 ketika ktia mandi setelah itu makan tentunya kita nga mungkin dong kita makan sambil mandi...awowaowaoawoaw...ya kali mau nasinya rasa sabun dan sampo :v,  

 nah pasti proses tersebut akan di lakukan secara satu per satu atau dalam istilah programming adalah ***Concurrency*** ***programming***, 
 ***Sincronus***, atau ***Blocking***.

# Parall
 Paraallel adalah proses eksekusi banyak program dalam 1 waktu.  
 ini kebalikanya dari Concurrency, misal kita gambarkan dalam kehidupan nyata adalah, ketika kita main mobilejen dan maki-maki musuh kita.....ya kali pas main ML/mobile legend nga maki-maki ..nga enak jirr...kurang greged minimal harus keluarin kata-kata "Ahh...anjig goblog..., Ahh Pantek, anjig, Dalampuki eeeee..." dan maka dari itu saya nobadkan gem ML/mobilegend jangan di mainkan saat bulan puasa nanti ya gaes heheh.  
   
 Pada intinya Palarel programming adalah proses eksekusi beberapa program dalam 1 waktu.

# Thread
 Pada bahasa pemograman Java, implementasi dari palarel programming dapat menggunakan `java.lang.Thread`.

# Thread Utama(main thread)
 Ketika kita menjalankan program java, maka secara implisit java akan membuat thread dengan nama `main` untuk mengeksekusi baris program tersebut.  
 Hal tersebut berlaku pada unit test juga.

# Membuat Thread secara manual
 Thread merupakan proses ringan, membuat Thread bukan berarti kita melakukan pekerjan. Untuk mmebuat Pekerjaan dalam Thread, kita perlu membaut object dari interface [`Runnable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html), setelah itu object `Runnable` tersebut kita jadikan parameter saat kita meng inisialisasi Object `Thread` nya.  

 Saat `Thread` berjalan, `Thread` akan berjalan secara ascyncronus, artinya dia akan berjalan sendiri dan kode program kita akan berlanjut ke kode program selanjutnya
 Untuk menjalankan `Thread` kita harus menanggil method yang bernama start();
 Example :
 ``` java
@Test
public void testCreateThread() {
	Runnable runnable = () -> {
		System.out.println("Ini thread yang ke : "+ Thread.currentThread().getName());
	};
	Thread thread = new Thread(runnable);
	thread.start();
}
 ```

# Thread Sleep
 Pada proses development, kadang kita memerlukan simulasi proses yang berjalan dalam waktu yang agak lama untuk mensimulasikan proses yang kompleks.  
 Untuk melakukan hal tersebut kita bisa memanfaatkan `Thread.sleep(timeMilis);` yang terdapat pada Java programing langguage.  
 Dengan menggunakan `Thread.sleep(timeMilis);` kita bisa membuat thread tertidur dan berhenti dalam waktu yang kita tentukan. Untuk melakukan hal ini, kita bisa memanggil static method `sleep();` di class Thread, Maka secara otomatis Thread saat itu akan tertidur atau berhenti berproses sementara sesuai waktu yang telah kita berikan pada peremeter method `sleep();`  
 Namun perlu diperhatikan, Method sleep bisa menyebabkan error InterruptedException
 Example :
 ``` java 
@Test
public void testThreadSleep() {
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
 ```

# Thread Join
 Jika thread di sleep atau thread sendang dalam keadaan proses yang lumayan komplex, kadang kita perlu mennunggu thread tersebut selesai mengerjakan pekerjaanya.

 Di cara yang sebelum nya kita menggunakan `Thread.sleep(TimeMilis);`  
 untuk mengatasinya, sebenarnya cara tersebut tidak baik karna pada Real nya nanti kita tidak tau berapa lama thread akan berjalan meng eksekusi program yang kita buat. Untuk mengatasi permasalah tersebut kita bisa menggunakan `Thread.join();`  
 Example :
 ``` java
@Test
public void testThreadJoin() {
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
 ```

# Thread Interrupted
 Interrupted merupakan sinyal yang dikirim ke current thread bahwa thread tersebut harus berhenti melakukan pekerjaanya.
 
 Interrupted ini biasanya digunakan jikalau terdapat permasalahan tertentu saat thread kita berjalan, dan kita ingin memberhentikan thread tersebut.  

 Ketika kita mengirimkan sinyal `interrupt` kepada current thread maka pada `runnable` kita harus mengecek apakah sinyal `interrupt` ada, jikalau ada maka kita bisa membuatkan exception handling.

 Example :
 ``` java
 @Test
public void testInterrupt() {
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
 ```

 ``` java
/**
	 * dalam real projek nanti kita akan menggunakan cara seperti ini karna untuk proses 
	 * thread yang membutuhkan waktu itu nga di lakukan secara manual seperti kita menggunakan Thread.sleep() dalam Object Runnable dalam real case nya.
	 */
@Test
public void testInterruptReal() {
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
 ```

# Thread Name
 Secara Default Thread di java memiliki nama, 
 Thread name secara default akan menggunkan nama Thread-{counter}
 Namun kita bisa juga mengubahnya dengan menggunkan method `setName(name)`, dan `getName()` untuk mendapatkan thread name nya
 Example :
 ``` java
 @Test
public void tesThreadSetName() {
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
 ```

# Thread state
 Thread state yaitu informasi dari state pada thread. Terdapat 3 state dalam thread, yaitu :
 | State 	 | Description
 |---------- |---------------
 | NEW		 | Thread pertama kali dibuat
 | Runnable  | Thread Sedang Berjalan
 | TERMINATED| Thread dimatikan

 Thread state ini biasanya kita gunakan unutk mengetahui sampai mana status thread, apakah ***Runnable*** atau ***Terminated*** dan sebagainya.  
 
 Untuk mengetahui status state thread kita bisa menggunakan method `getState()`.
 Example :
 ``` java
@Test @SneakyThrows
public void threadState1(){
	Runnable runnable = () -> {
		// thread sedang berjalan : RUNNABLE
		System.out.println(Thread.currentThread().getState().name());
	};
	Thread thread = new Thread(runnable);
	// Thread pertama kali dubuat : NEW
	System.out.println(thread.getState());
	thread.start();
	thread.join();
	// Thread dimatikan : TERMINATED
	System.out.println(thread.getState());
}
 ```

# Thread Deamond
 Secara default, saat kita membuat thread,thread tersebut disebut sebagai user thread.
 Untuk program java (bukan program java unit test/JUNIT) secara defaut akan menunggu semua user thread selesai 
 sebelum program di berhentikan.  

 jika kita mengubah thread menjadi deamon thread, menggunakan method `setDeamon(true)` maka secara otomatis thread tersebut akan menjadi deamon.  
 

 Daemon thread tidak akan di tunggu jika memang program java akan berhenti Namun jika kita menghetikan semua thread maka ktia bisa menggunakan `System.exit()` maka semua user thread pun akan berhenti.  
 **Example :**
 ``` java

public class DaemonThread {

    public static void main(String... args){

        // ini bisa di sebut user thread 
        Thread thread = new Thread(() -> {
            System.out.println("jalan pada thread "+Thread.currentThread().getName());
            }, "user_thread");
        thread.start();

        // ini busa disebut deamon thread karna bisa dibilang thread ini akan berjalan di bacground
        // daemon thread bisa kita gunakan sebagai job tertentu yang mana job tersebut tidak terlalu di perdulikan
        // misal nya aja seperti proses export data ke file csv 

        Thread daemonThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            }catch(InterruptedException ITX) {
                ITX.printStackTrace();
            }
        }, "deamon_thread");
        // ini akan mngubah thread ini menjadi deamon thread yang mana proses eksekusi tread ini berada pada bacground dari app kita
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}

 ```
# Race Condition
Trouble yang sering dijumpai saat kita menggunakan paralel programming atau asyncronus programming adalah race condition. ***Race condition*** ini merupakan sutu kejadian yang mana beberapa thread melakukan update pada 1 data secara bersamaan.

``` java
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class Counter {

    private Long increment = 0L;

    public void increment() {
        this.increment++;
    }
}
```

``` java
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
	// beberapa thread melakukan update pada data counter secara bersamaan
	thread1.start();
	thread2.start();
	thread3.start();

	thread1.join();
	thread2.join();
	thread3.join();

	// seharusnya jumlah dari incremnt nya 3000 tapi karena terjadi race condition hasilnya tidak sesuai
	System.out.println(counter.getIncrement());
}
```

# Syncronized Method Counter
Syncronization ini memungkinkan suatu block code hanya boled di eksekusi oleh 1 thread dalam satu waktu.  
Pada bahsa pemograman java terdapat dua jenis syncronization :
* synchronized method
* synchronized statement

### synchronized method
Unutuk menggunakan synchronized method caranya cukup mudah, kita hanya perlu menambahkan keyword `synchronized` sebelum nama method yang hanya boleh di eksekusi oleh 1 thread dalam 1 waktu.
``` java
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SyncronusMethodCounter {

    private Long increment = 0L;

    public synchronized void increment() {
        this.increment++;
    }
}
```
``` java
@Test @SneakyThrows
public void testSysncronizedMethod() {
	SyncronusMethodCounter syncronusMethodCounter = new SyncronusMethodCounter();
	Runnable runnable = () -> {
		for (int i = 0; i < 1000; i++) {
			syncronusMethodCounter.increment();		
		}
	};
	
	Thread thread1 = new Thread(runnable);
	Thread thread2 = new Thread(runnable);
	Thread thread3 = new Thread(runnable);
	
	thread1.run();
	thread2.run();
	thread3.run();

	thread1.join();
	thread2.join();
	thread3.join();
	
	Assertions.assertEquals(3000, syncronusMethodCounter.getIncrement());
}
```
### sysncronized statement
Untuk menggunakan sysncronized statement caranya cukup mudah, kita hanya perlu menggunakan keyword `sysncronized (object)` dengan parameter object yang kita iniginkan.  
  
Object pada parameter `sysncronized` akan digunakan untuk melakukan locking. Sehingga dengan demikian setiap 1 thread yang sedang melakukan eksekusi block kode akan di lock, artinya dalam 1 waktu hanya 1 thread yang diizinkan melakukan eksekusi block code.  


``` java
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SyncronizeStatementCounter {
    
    private Long counter = 0L;

    private Long counter2 = 0L;

    private Long counter3 = 0L;

    public void increment() {
        // counter2 bisa dieksekusi multiple thread
		counter2++;
        synchronized (this){
			// counter hanya bisa di eksekusi 1 thread dalam 1 waktu
            this.counter++;
        }
		// counter3 bisa di eksekusi multiple thread
        counter3++;
    }
}
```

``` java
@Test @SneakyThrows
public void testSynchronizedStatement(){
	SyncronizeStatementCounter syncronizeStatementCounter = new SyncronizeStatementCounter();
	Runnable runnable = () -> {
		for (int i = 0; i < 1000; i++) {
			syncronizeStatementCounter.increment();
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

	Assertions.assertEquals(3000, syncronizeStatementCounter.getCounter());
}
```

# Dead Lock
Permasalahan saat kita menggunakan `synchronized` adalah ***dead lock***.  
Dead lock adalah suatu kondisi ketika beberapa thread saling menunggu dengan thread lainya, misalnya :  
Thread 1 melakukan lock kepada thread 2, dan thread 2 melakukan lock pada thread 1. Maka yang akan terjadi keuda thread tersebut saling menunggu.

``` java
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Ewallet {

    private Long balance = 0L;

    @SneakyThrows
    public static void tranferDeadLock(Ewallet from, Ewallet to, Long blance) {
        synchronized(from) {
            Thread.sleep(1000L);
            synchronized(to) {
                from.setBalance(from.getBalance() - blance);
                to.setBalance(to.getBalance() + blance);
            }
        }
    }
}
```

``` java
@Test @SneakyThrows
public void testDeadLock() {
	Ewallet ewallet1 = new Ewallet(10_000L);
	Ewallet ewallet2 = new Ewallet(10_000L);
		
	Runnable tranfer1 = () -> {
		Ewallet.tranferDeadLock(ewallet1, ewallet2, 5_000L);
	};
	
	Runnable tranfer2 = () -> {
		Ewallet.tranferDeadLock(ewallet2, ewallet1, 5_000L);
	};

	Thread thread1 = new Thread(tranfer1);
	Thread thread2 = new Thread(tranfer2);

	thread1.start();
	thread2.start();

	thread1.join();
	thread2.join();

	System.out.println("Ewalet 1 : " + ewallet1.getBalance());
	System.out.println("Ewalet 2 : " + ewallet2.getBalance());
}
```

Untuk menghindari dead lock tidak ada cara khusus nya, melainkan kita sebagai programmer harus benar-benar mepertimbangkan saat menggunakan synchronized. misalnya untuk meresolve kode dead lock diatas kita bisa memisahkan proses locing `synchronized` nya

``` java
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Ewallet {

    private Long balance = 0L;

    @SneakyThrows
    public static void tranfer(Ewallet from, Ewallet to, Long blance) {
        synchronized(from) {
            Thread.sleep(1000L);
            from.setBalance(from.getBalance() - blance);
        }
        
        synchronized(to) {
            Thread.sleep(1000L);
            to.setBalance(to.getBalance() + blance);
        }
    }
}
```


