
# Concurency 

 Concurrency adalah proses eksekusi program secara satu per satu
 jadi dalam pemrosesan program nya tidak dapat multiple eksekusi atau secara 1 waktu mengeksekusi lebih dari 1 program
 misal nya jika kita gambarkan dalam kehidupan sehari2 adalah, ketika ktia mandi setelah itu makan
 tentunya kita nga mungkin dong kita makan sambil mandi...awowaowaoawoaw...ya kali mau nasinya rasa sabun dan sampo :v, 
 nah pasti proses tersebut akan di lakukan secara satu per satu atau dalam istilah programming adalah Concurrency programming, 
 Sincronus, atau Blocking.

# Parallel

 Paraallel adalah proses eksekusi banyak program dalam 1 waktu.
 ini kebalikanya dari Concurrency
 misal kita gambarkan dalam kehidupan nyata adalah, ketika kita main mobilejen dan maki-maki musuh kita.....ya kali pas main ML/mobile legend nga maki-maki ..nga enak jirr...kurang greged minimal harus keluarin kata-kata "Ahh...anjig goblog..., Ahh Pantek, anjig, Dalampuki eeeee..." dan maka dari itu saya nobadkan gem ML/mobilegend jangan di mainkan saat bulan puasa nanti ya gaes heheh

# Thread

 di java implementasi consurrency dan parallel dapat menggunkan Thread
 Thread di representasikan oleh class yang bernama Thread yang berada di packaga java.lang

# Thread Utama

 secara implisit atau secara kita tidak buatkan thread, ketika program java berjalan itu otomatis java akan membuat thread
 Dalam program java biasanya kode program kita akan jalan dalam thread yang bernama main
 begitupun jikalau kita menjallankann Unit test, program unit tes kita ketika di jalankan itu juga akan menggunakan thread

# Membuat Thread secara manual

 Thread merupakan proses ringan, membuat Thread bukan berarti kita melakukan pekerjan.
 Untuk mmebuat Pekerjaan dalam Thread, kita perlu membaut onbject dari interface Runnable, selanjutnya object Runnable tersebut kita jadikan parameter saat kita meng inisialisasi Object Thread nya
 Saat Thread berjalan, Thread akan berjalan secara ascyncronus, artinya dia akan berjalan sendiri dan kode program kita akan berlanjut ke kode program selanjutnya
 Untuk menjalankan Thread kita harus menanggil method yang bernama start();
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

 Pada proses development, kadang kita memerlukan simulasi proses yang berjalan dalam waktu tertentu
 Untuk melakukan hal ini kita bisa memanfaatkan fitur Thread.sleep(timeMilis); yang terdapat pada Java programing langguage.
 Dengan menggunakan Thread.sleep(timeMilis); kita bisa membuat thread tertidur dan berhenti dalam waktu yang kita tentukan
 Untuk melakukan hal ini, kita bisa memanggil static method sleep(); di class Thread, Maka secara otomatis Thread saat itu akan tertidur atau berhenti berproses sementara sesuai waktu yang telah kita berikan pada peremeter method sleep();
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
 Di cara yang sebelum nya kita menggunakan Thread.sleep(TimeMilis); untuk mengatasinya, sebenarnya cara tersebut 
 tidak baik karna pada Real nya nanti kita tidak tau berapa lama thread akan berjalan meng eksekusi program yang kita buat
 Untuk mengatasi permasalah tersebut kita bisa menggunakan Thread.join();
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

# Thread Interrupt

 Interrupted merupakan mengirim sinyal ke thread bahwa thread tersebut harus berhenti melakukan pekerjaanya
 Untuk melakukan Interrupt, kita bisa menggunkan method inturept(); pada thread
 Saat kita memanggil method intrrupt();, secara otomatid Thread.interupted() akan bernilai true
 Saat kita membuat interrupt();, pada kode Runnable kita harus mengecek Interupted() tersebut, jika nga ngecek 
 maka interrupt kita nga ada gunanya
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
 Namun kita bisa juga mengubahnya dengan menggunkan method setName(name), dab getName() untuk mendapatkan thread name nya
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

 Thread state yaitu informasi dari state pada thread
 State digunakan jika kita ingin melihat atau mendapatkan informasi pada thread yang kita inignkan
 State akan berubah setatus nya sesuai dengan apa yang terjadi di thread
 untuk menggunkan data state kita bisa menggunakan method getState() dan akan mengembalikan informasi dengan tipe data enum
 Example :
 ``` java
    @Test
 	public void testThreadState() {

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

 ```

# Thread Deamond

 secara default, saat kita membuat thread,thread tersebut disebut sebagai user thread
 untuk program java (bukan program java unit test/JUNIT) secara defaut akan menunggu semua user thread selesai 
 sebelum program di berhentikan.
 jika kita mengubah thread menjadi deamon thread, menggunakan method setDeamon(true) maka secara otomatis thread 
 tersebut akan menjadi deamon thread
 Deamon thread tidak akan di tunggu jika memang program java akan berhenti
 Namun jika kita menghetikan program java dengan System.exit() maka semua user thread pun akan berhenti.
 Example :
 ``` java
 import java.lang.InterruptedException;

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

# 