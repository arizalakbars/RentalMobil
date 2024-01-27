import java.util.ArrayList;
import java.util.Scanner;

// Kelas abstrak untuk pengguna
abstract class Pengguna {
    protected String namaPengguna;
    protected String kataSandi;

    public Pengguna(String namaPengguna, String kataSandi) {
        this.namaPengguna = namaPengguna;
        this.kataSandi = kataSandi;
    }

    public abstract String getNamaPengguna();

    public abstract String getKataSandi();
}

// Kelas mobil yang mengelompokkan detail mobil
class Mobil {
    private String jenis;
    private double hargaPerHari;
    private boolean sedangDipinjam;

    public Mobil(String jenis, double hargaPerHari) {
        this.jenis = jenis;
        this.hargaPerHari = hargaPerHari;
        this.sedangDipinjam = false;
    }

    public String getJenis() {
        return jenis;
    }

    public double getHargaPerHari() {
        return hargaPerHari;
    }

    public boolean isSedangDipinjam() {
        return sedangDipinjam;
    }

    public void setSedangDipinjam(boolean sedangDipinjam) {
        this.sedangDipinjam = sedangDipinjam;
    }
}

// Kelas PemberiMobil turunan dari Pengguna
class PemberiMobil extends Pengguna {
    private ArrayList<Mobil> mobils;
    private ArrayList<Pelanggan> pelanggans;

    public PemberiMobil(String namaPengguna, String kataSandi) {
        super(namaPengguna, kataSandi);
        this.mobils = new ArrayList<>();
        this.pelanggans = new ArrayList<>();
    }

    @Override
    public String getNamaPengguna() {
        return namaPengguna;
    }

    @Override
    public String getKataSandi() {
        return kataSandi;
    }

    public ArrayList<Mobil> getMobils() {
        return mobils;
    }

    public void tambahMobil(String jenis, double hargaPerHari) {
        mobils.add(new Mobil(jenis, hargaPerHari));
    }

    public void hapusMobil(int indeks) {
        if (indeks >= 0 && indeks < mobils.size()) {
            mobils.remove(indeks);
        }
    }

    public void lihatInfoPeminjaman() {
        System.out.println("Informasi Peminjaman:");
        for (Pelanggan pelanggan : pelanggans) {
            System.out.println("Pelanggan: " + pelanggan.getNamaPengguna());
        }
    }

    public void sewaMobil(Pelanggan pelanggan, int indeksMobil, int hari) {
        if (indeksMobil >= 0 && indeksMobil < mobils.size()) {
            Mobil mobil = mobils.get(indeksMobil);
            if (!mobil.isSedangDipinjam()) {
                mobil.setSedangDipinjam(true);
                pelanggans.add(pelanggan);
                double totalHarga = mobil.getHargaPerHari() * hari;
                System.out.println("Peminjaman berhasil!");
                System.out.println("Jenis Mobil: " + mobil.getJenis());
                System.out.println("Total Harga: $" + totalHarga);
            } else {
                System.out.println("Mobil sudah dipinjam.");
            }
        } else {
            System.out.println("Indeks mobil tidak valid.");
        }
    }
}

// Kelas Pelanggan turunan dari Pengguna
class Pelanggan extends Pengguna {
    public Pelanggan(String namaPengguna, String kataSandi) {
        super(namaPengguna, kataSandi);
    }

    @Override
    public String getNamaPengguna() {
        return namaPengguna;
    }

    @Override
    public String getKataSandi() {
        return kataSandi;
    }
}

// Kelas SistemRentalMobil implementasi program utama
public class SistemRentalMobil {
    private static PemberiMobil pemberiMobil;
    private static Pelanggan pelanggan;

    public static void main(String[] args) {
        inisialisasiData();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selamat datang di Sistem Rental Mobil!");
            System.out.println("1. Masuk sebagai Pemberi Mobil");
            System.out.println("2. Masuk sebagai Pelanggan");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");

            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    masukSebagaiPemberiMobil(scanner);
                    break;
                case 2:
                    masukSebagaiPelanggan(scanner);
                    break;
                case 3:
                    System.out.println("Keluar dari Sistem Rental Mobil. Sampai jumpa!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void inisialisasiData() {
        pemberiMobil = new PemberiMobil("Arizal", "Arizal");
        pemberiMobil.tambahMobil("Sedan", 50.0);
        pemberiMobil.tambahMobil("SUV", 70.0);

        pelanggan = new Pelanggan("Arizal", "Arizal");
    }

    private static void masukSebagaiPemberiMobil(Scanner scanner) {
        System.out.print("Masukkan nama pengguna: ");
        String namaPengguna = scanner.next();
        System.out.print("Masukkan kata sandi: ");
        String kataSandi = scanner.next();

        if (pemberiMobil.getNamaPengguna().equals(namaPengguna) && pemberiMobil.getKataSandi().equals(kataSandi)) {
            menuPemberiMobil(scanner);
        } else {
            System.out.println("Nama pengguna atau kata sandi tidak valid. Silakan coba lagi.");
        }
    }

    private static void menuPemberiMobil(Scanner scanner) {
        while (true) {
            System.out.println("Selamat datang, " + pemberiMobil.getNamaPengguna() + " (Admin)!");
            System.out.println("1. Tambah Mobil");
            System.out.println("2. Hapus Mobil");
            System.out.println("3. Lihat Informasi Peminjaman");
            System.out.println("4. Logout");
            System.out.print("Pilih opsi: ");

            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    tambahMobil(scanner);
                    break;
                case 2:
                    hapusMobil(scanner);
                    break;
                case 3:
                    pemberiMobil.lihatInfoPeminjaman();
                    break;
                case 4:
                    System.out.println("Keluar sebagai Pemberi Mobil.");
                    return;
                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void tambahMobil(Scanner scanner) {
        System.out.print("Masukkan jenis mobil: ");
        String jenis = scanner.next();
        System.out.print("Masukkan harga per hari: ");
        double hargaPerHari = scanner.nextDouble();

        pemberiMobil.tambahMobil(jenis, hargaPerHari);
        System.out.println("Mobil berhasil ditambahkan!");
    }

    private static void hapusMobil(Scanner scanner) {
        System.out.println("Mobil yang tersedia untuk dihapus:");
        ArrayList<Mobil> mobils = pemberiMobil.getMobils();
        for (int i = 0; i < mobils.size(); i++) {
            System.out.println(i + ". " + mobils.get(i).getJenis());
        }

        System.out.print("Masukkan indeks mobil yang akan dihapus: ");
        int indeks = scanner.nextInt();

        pemberiMobil.hapusMobil(indeks);
        System.out.println("Mobil berhasil dihapus!");
    }

    private static void masukSebagaiPelanggan(Scanner scanner) {
        System.out.print("Masukkan nama pengguna: ");
        String namaPengguna = scanner.next();
        System.out.print("Masukkan kata sandi: ");
        String kataSandi = scanner.next();

        if (pelanggan.getNamaPengguna().equals(namaPengguna) && pelanggan.getKataSandi().equals(kataSandi)) {
            menuPelanggan(scanner);
        } else {
            System.out.println("Nama pengguna atau kata sandi tidak valid. Silakan coba lagi.");
        }
    }

    private static void menuPelanggan(Scanner scanner) {
        while (true) {
            System.out.println("Selamat datang, " + pelanggan.getNamaPengguna() + " (Pelanggan)!");
            System.out.println("1. Cari Mobil");
            System.out.println("2. Sewa Mobil");
            System.out.println("3. Logout");
            System.out.print("Pilih opsi: ");

            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    cariMobil(scanner);
                    break;
                case 2:
                    sewaMobil(scanner);
                    break;
                case 3:
                    System.out.println("Keluar sebagai Pelanggan.");
                    return;
                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void cariMobil(Scanner scanner) {
        System.out.println("Mobil yang Tersedia:");
        ArrayList<Mobil> mobils = pemberiMobil.getMobils();
        for (int i = 0; i < mobils.size(); i++) {
            System.out.println(i + ". " + mobils.get(i).getJenis());
        }
    }

    private static void sewaMobil(Scanner scanner) {
        System.out.println("Mobil yang Tersedia:");
        ArrayList<Mobil> mobils = pemberiMobil.getMobils();
        for (int i = 0; i < mobils.size(); i++) {
            System.out.println(i + ". " + mobils.get(i).getJenis());
        }

        System.out.print("Masukkan indeks mobil yang akan disewa: ");
        int indeksMobil = scanner.nextInt();
        System.out.print("Masukkan jumlah hari penyewaan: ");
        int hari = scanner.nextInt();

        pemberiMobil.sewaMobil(pelanggan, indeksMobil, hari);
    }
}
