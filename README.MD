STEGANOGRAPHY
where a picture has another message, literally

- What is Steganography? -
Steganografi berasal dari kata bahasa Yunani steganos yang berarti tersembunyi dan graphein yang berarti tulisan

Secara keseluruhan Steganografi adalah teknik menyembunyikan pesan sehingga tidak ada orang yang curiga akan keberadaan pesan tersebut

Pada steganografi, media pembawa pesan disebut sebagai cover-object, sedangkan pesan yang disembunyikan disebut embedded message. Hasil dari penyembunyian pesan tersebut disebut sebagai stego-object.

- Digital Steganography -
Steganografi sudah berkembang dari jaman dulu hingga sekarang. Pada jaman dulu, steganografi memanfaatkan media fisik seperti gambar dan tulisan fisik. Namun seiring berkembangnya jaman, steganografi berkembang hingga ke media digital.

Jika pada jaman dahulu, stego-object berbentuk fisik, maka pada era digital saat ini, stego-object berbentuk digital seperti gambar, audio, video, dll.

Salah satu metode steganografi pada gambar adalah dengan metode LSB. Metode LSB memanfaatkan bit pada satu pixel yang dimiliki gambar. Pada gambar grayscale, terdapat  8 bit warna, pada gambar berwarna terdapat 24 bit warna.

Pada gambar grayscale, 8 bit warna tersebut menandakan keabuan gambar. Pada gambar berwarna, 24 bit tersebut terbagi menjadi 3 channel, masing-masing 8 bit, yaitu red, green ,dan blue.

8 bit tersebut dapat direpresentasikan dengan angka 1 dan 0, seperti pada contoh di bawah

	10110010
	
Bit paling kanan disebut least significant bit(LSB) karena bit tersebut hanya sedikit mempengaruhi pergeseran nilai dari warna. Akibatnya, angka tersebut dapat dimodifikasi untuk menyimpan pesan yang disembunyikan.

- Imageception -
Melalui metode LSB, kita dapat menyembunyikan informasi berupa gambar di dalam gambar. Pada contoh di bawah, akan ditunjukkan salah satu cara menyembunyikan gambar hitam putih pada gambar berwarna.

Secara singkat, proses menyembunyikan gambar hitam putih pada gambar berwarna dapat diringkas menjadi poin berikut
	1. Mengambil warna pixel gambar berwarna (carrier file)
	2. Mengambil warna pixel gambar hitam-putih (embedded message)
	3. Mengubah LSB dari gambar berwarna sesuai dengan warna dari gambar hitam-putih
	4. Menyimpan gambar berwarna kembali

1. Mengambil warna pixel gambar berwarna
Untuk mendapatkan nilai warna setiap pixel pada gambar, kita dapat menggunakan kode program Java di bawah untuk memperoleh nilai RGB pada sebuah gambar.
Kita asumsikan bahwa gambar yang digunakan tidak menggunakan transparency/alpha seperti yang terdapat pada gambar berformat .png

public static byte[][][] readImage(BufferedImage img){
    byte[][][] pixel = new byte[img.getWidth()][img.getHeight()][3];
    for(int i = 0; i < img.getWidth(); i++){
      for(int j = 0; j < img.getHeight(); j++){
        int color = img.getRGB(i,j);
        pixel[i][j][0] = (byte)(color >> 16);
        pixel[i][j][1] = (byte)(color >> 8);
        pixel[i][j][2] = (byte)color;
      }
    }

    return pixel;
}

2. Mengambil warna pixel gambar hitam-putih
Setelah mendapatkan warna setiap pixel pada gambar berwarna, kita memerlukan pesan yang akan dimasukkan dalam gambar berwarna tadi. Untuk mempermudah proses, kita akan menggunakan gambar yang memiliki dua warna (hitam-putih) saja karena akan lebih mudah untuk dimasukkan.

Kode program pada langkah pertama dapat digunakan kembali untuk membaca warna pada gambar hitam-putih yang akan disembunyikan

3. Mengubah LSB gambar berwarna
Setelah mendapatkan warna dari kedua gambar kita hanya perlu mengubah LSB warna dari gambar berwarna sesuai dengan warna pada gambar hitam-putih. Pada bagian berwarna hitam LSB diubah menjadi 0, dan pada bagian berwarna putih LSB diubah menjadi angka 1.
Untuk melakukan langkah di atas dapat digunakan kode program Java sebagai berikut

public static byte[][][] encodeImage(byte[][][] cover, byte[][][] message) {
    byte[][][] stego = new byte[cover.length][cover[0].length][3];

    for (int i = 0; i < stego.length; i++) {
      for (int j = 0; j < stego[j].length; j++) {
        if (i < message.length && j < message[i].length) {
          if (message[i][j][0] == 0) {
            stego[i][j][0] = (byte)((cover[i][j][0] >> 1) << 1);
          }
          else {
            stego[i][j][0] = (byte)((cover[i][j][0] >> 1) << 1 + (byte)1);
          }

          stego[i][j][1] = cover[i][j][1];
          stego[i][j][2] = cover[i][j][2];
        } else {
          stego[i][j][0] = cover[i][j][0];
          stego[i][j][1] = cover[i][j][1];
          stego[i][j][2] = cover[i][j][2];
        }
      }
    }
    return stego;
  }
  
4. Menyimpan gambar berwarna kembali
Setelah mengubah LSB pada cover-image, gambar kembali di simpan. Kali ini gambar yang sudah disimpan telah menyimpan hidden message.

Untuk memperoleh pesan tersebut kembali, diambil LSB dari setiap pixel warna di stego-image. Bit 0 diartikan sebagai warna hitam, dan bit 1 diartikan sebagai warna putih. Kode di bawah dapat digunakan untuk melakukan dekripsi pada pesan

public static byte[][][] decodeMessage(byte[][][] stego){
    byte[][][] message = new byte[stego.length][stego[0].length][3];

    for (int i = 0; i < stego.length; i++) {
      for (int j = 0; j < stego[j].length; j++) {
        if ((stego[i][j][0] & 0x01) == 0) {
          message[i][j][0] = -1;
          message[i][j][1] = -1;
          message[i][j][2] = -1;
        } else {
          message[i][j][0] = 0;
          message[i][j][1] = 0;
          message[i][j][2] = 0;
        }
      }
    }
    return message;
  }

Sumber :
Rinaldi Munir, Slide Kuliah Steganografi, 2015