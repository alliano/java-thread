
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
 misal kita gambarkan dalam kehidupan nyata adalah, ketika kita main mobilejen dan maki-maki musuh kita.....ya kali pas main ML/mobile legend nga maki-maki ..nga enak jirr...kurang greged minimal harus keluarin kata-kata "Ahh...anjig, goblog..., Ahh Pantek, anjig, Dalampuki eeeee..." dan maka dari itu saya nobadkan gem ML/mobilegend jangan di mainkan saat bulan puasa nanti ya gaes heheh

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
 Example ada pada unit test package com.threed; line 30 dengan nama method testCreateThread()

# Thread Sleep

 Pada proses development, kadang kita memerlukan simulasi proses yang berjalan dalam waktu tertentu
 Untuk melakukan hal ini kita bisa memanfaatkan fitur Thread.sleep(timeMilis); yang terdapat pada Java programing langguage.
 Dengan menggunakan Thread.sleep(timeMilis); kita bisa membuat thread tertidur dan berhenti dalam waktu yang kita tentukan
 Untuk melakukan hal ini, kita bisa memanggil static method sleep(); di class Thread, Maka secara otomatis Thread saat itu akan tertidur atau berhenti berproses sementara sesuai waktu yang telah kita berikan pada peremeter method sleep();
 Namun perlu diperhatikan, Method sleep bisa menyebabkan error InterruptedException Example ada pada unit test package com.threed; line 30 dengan nama method testThreadSleep(); Line 45

# Thread Join

 Jika thread di sleep atau thread sendang dalam keadaan proses yang lumayan komplex, kadang kita perlu mennunggu thread tersebut selesai mengerjakan pekerjaanya.
 Di cara yang sebelum nya kita menggunakan Thread.sleep(TimeMilis); untuk mengatasinya, sebenarnya cara tersebut 
 tidak baik karna pada Real nya nanti kita tidak tau berapa lama thread akan berjalan meng eksekusi program yang kita buat
 Untuk mengatasi permasalah tersebut kita bisa menggunakan Thread.join();
 Example ada pada unit test package com.thread; line ke 71 dengan nama method testThreadJoin()

# Thread Interrupt

 Interrupted merupakan mengirim sinyal ke thread bahwa thread tersebut harus berhenti melakukan pekerjaanya
 Untuk melakukan Interrupt, kita bisa menggunkan method inturept(); pada thread
 Saat kita memanggil method intrrupt();, secara otomatid Thread.interupted() akan bernilai true
 Saat kita membuat interrupt();, pada kode Runnable kita harus mengecek Interupted() tersebut, jika nga ngecek 
 maka interrupt kita nga ada gunanya
 Example ada pada unit test package com.thread; line 93 -128 dengan nama method testInterrupt() dan  testInterruptReal()

# Thread Name

 Secara Default Thread di java memiliki nama, 
 Thread name secara default akan menggunkan nama Thread-{counter}
 Namun kita bisa juga mengubahnya dengan menggunkan method setName(name), dab getName() untuk mendapatkan thread name nya
 Example adapa pada unit test package com.thread; line 158 dengan nama method tesThreadSetName()

# Thread state

 Thread state yaitu informasi dari state pada thread
 State digunakan jika kita ingin melihat atau mendapatkan informasi pada thread yang kita inignkan
 State akan berubah setatus nya sesuai dengan apa yang terjadi di thread
 untuk menggunkan data state kita bisa menggunakan method getState() dan akan mengembalikan informasi dengan tipe data enum
 Example ada pada unit test package com.thread; line 174 dengan nama method testThreadState()

# Thread Deamond

 secara default, saat kita membuat thread,thread tersebut disebut sebagai user thread
 untuk program java (bukan program java unit test/JUNIT) secara defaut akan menunggu semua user thread selesai 
 sebelum program di berhentikan.
 jika kita mengubah thread menjadi deamon thread, menggunakan method setDeamon(true) maka secara otomatis thread 
 tersebut akan menjadi deamon thread
 Deamon thread tidak akan di tunggu jika memang program java akan berhenti
 Namun jika kita menghetikan program java dengan System.exit() maka semua user thread pun akan berhenti.
 Example ada pada java main package com.thread; class DaemonThread
