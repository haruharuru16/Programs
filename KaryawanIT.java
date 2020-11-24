package tugaspbo113.TM7;

import java.text.NumberFormat;
import java.util.Locale;

public class KaryawanIT {

    private static Karyawan [] daftarKaryawan;
    private static Programmer [] daftarProgrammer;
    private static SeniorProgrammer [] daftarSeniorProgrammer;
    private static ProjectLeader dataProjectLeader;

    public static void main(String[] args) {
        //3 Jenis Karyawan
        Karyawan [] kr = new Karyawan[3];
        kr[0] = new Karyawan("Cica","123456789");
        kr[1] = new Karyawan("Marceline", "123455667");
        kr[2] = new Karyawan("Finn", "123466778");

        kr[0].setGajiPokok(3500000);
        kr[0].setJenisKaryawan("Administrasi");

        kr[1].setGajiPokok(4000000);
        kr[1].setJenisKaryawan("Keuangan");

        kr[2].setGajiPokok(4500000);
        kr[2].setJenisKaryawan("Keamanan");

        //3 orang programmer
        Programmer [] pr = new Programmer[3];
        pr[0] = new Programmer("Marzuki", "234567890", 40);
        pr[1] = new Programmer("Bubblegum", "234566778", 45);
        pr[2] = new Programmer("Hordak", "234577889", 52);

        //2 orang senior programmer
        SeniorProgrammer [] sr = new SeniorProgrammer[2];
        sr[0] = new SeniorProgrammer("Entrapta", "345677889", 35);
        sr[1] = new SeniorProgrammer("Haru", "345688990",38);

        //1 orang project leader
        ProjectLeader pl = new ProjectLeader("Glimmer","456788990");
        pl.setBonusProject(50000000);

        showData(kr, pr, sr, pl);   //menampilkan info seluruh karyawan
        totalGaji(kr, pr, sr, pl); //menampilkan total gaji yang harus dibayar perusahaan
    }

    public static void showData(Karyawan[] kr, Programmer[] pr, SeniorProgrammer[] sr, ProjectLeader pl){
        daftarKaryawan = kr;
        daftarProgrammer = pr;
        daftarSeniorProgrammer = sr;
        dataProjectLeader = pl;
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s | %-15s | %-20s | %-20s| %-20s | %-20s %n", "NAMA", "NOMOR", "STATUS PEGAWAI", "GAJI POKOK", "BONUS (LEMBUR+PROJECT+TUNJANGAN)", "TOTAL GAJI");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
        for (Karyawan karyawan : daftarKaryawan) {
            karyawan.info();
        }
        for (Programmer programmer : daftarProgrammer) {
            programmer.info();
        }
        for (SeniorProgrammer seniorProgrammer : daftarSeniorProgrammer) {
            seniorProgrammer.info();
        }
        dataProjectLeader.info();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void totalGaji(Karyawan[] kr, Programmer[] pr, SeniorProgrammer[] sr, ProjectLeader pl){
        //definisikan fungsi menghitung total gaji seluruh karyawan IT
        int gajiKaryawan = 0;
        int gajiProgrammer = 0;
        int gajiSrProgrammer = 0;
        int gajiPrLeader = pl.gajiTotal();
        for(int i = 0; i < kr.length; i++){
            gajiKaryawan += kr[i].gajiTotal();
            gajiProgrammer += pr[i].gajiTotal();
        }
        for (SeniorProgrammer seniorProgrammer : sr) {
            gajiSrProgrammer += seniorProgrammer.gajiTotal();
        }
        int total = gajiKaryawan + gajiProgrammer + gajiSrProgrammer + gajiPrLeader;
        String nominal = Integer.toString(total);

        KaryawanIT.RupiahCurrencyFormat rcf =  new KaryawanIT.RupiahCurrencyFormat();
        String newNominal = rcf.toRupiahFormat(nominal);
        System.out.println("Total gaji seluruh karyawan : Rp. " + newNominal);
    }

    public static class RupiahCurrencyFormat{
        Locale locale = null;
        NumberFormat rupiahFormat = null;

        public String toRupiahFormat(String nominal){
            String rupiah;
            locale = new Locale("ca", "CA");
            rupiahFormat = NumberFormat.getCurrencyInstance(locale);
            rupiah = rupiahFormat.format(Double.parseDouble(nominal)).substring(4);

            return rupiah;
        }
    }
}

class Karyawan{
    protected String nama;
    protected String kodeKaryawan;
    protected int gajiPokok;
    protected String jenisKaryawan;
    
    Karyawan(String dataNama, String dataKodeKaryawan){
        this.nama=dataNama;
        this.kodeKaryawan=dataKodeKaryawan;
    }
    public String getNama(){
        return this.nama;
    }

    public String getKodeKaryawan(){
        return this.kodeKaryawan;
    }
    
    public void setGajiPokok(int dataGajiPokok){
        this.gajiPokok = dataGajiPokok;
    }

    public void setJenisKaryawan(String dataJenisKaryawan){
        this.jenisKaryawan= dataJenisKaryawan;
    }
    
    public String getJenisKaryawan(){
        return this.jenisKaryawan;
    }
    
    public int gajiTotal(){
        return this.gajiPokok;
    }

    public String getRupiah(){
        KaryawanIT.RupiahCurrencyFormat rcf =  new KaryawanIT.RupiahCurrencyFormat();
        String newGaji = Integer.toString(this.gajiPokok);
        return rcf.toRupiahFormat(newGaji);
    }

    public String getRupiah2(){
        int gajiTotal = this.gajiTotal();
        String nominal = Integer.toString(gajiTotal);

        KaryawanIT.RupiahCurrencyFormat rcf =  new KaryawanIT.RupiahCurrencyFormat();
        return rcf.toRupiahFormat(nominal);
    }
    
    public void info(){
        System.out.printf("%-15s | %-15s | %-20s | Rp. %-15s | Rp. %-28s | Rp. %-15s%n", this.getNama().toUpperCase(), this.getKodeKaryawan(), this.getJenisKaryawan(), this.getRupiah(),"0,00", this.getRupiah2());
    }
    

}

class Programmer extends Karyawan{
    private final int BONUSLEMBURPERJAMPROGRAMMER = 100000;
    protected int jumlahJamLembur;
    protected int feeLembur;
    
    Programmer(String dataNama, String dataKodeKaryawan, int dataJumlahJamLembur){
        super(dataNama,dataKodeKaryawan);
        this.jumlahJamLembur = Math.min(dataJumlahJamLembur, 50);
        this.gajiPokok = 5000000; //gaji pokok khusus programmer
        this.jenisKaryawan = "Programmer";
        this.feeLembur = jumlahJamLembur*BONUSLEMBURPERJAMPROGRAMMER;
    }

    @Override
    public int gajiTotal(){
        return gajiPokok + feeLembur;
    }

    public String getRupiahPr(){
        int feeLembur = this.feeLembur;
        String strFeeLembur = Integer.toString(feeLembur);

        KaryawanIT.RupiahCurrencyFormat rcf =  new KaryawanIT.RupiahCurrencyFormat();
        return rcf.toRupiahFormat(strFeeLembur);
    }

    @Override
    public void info(){
        System.out.printf("%-15s | %-15s | %-20s | Rp. %-15s | Rp. %-28s | Rp. %-15s%n", this.getNama().toUpperCase(), this.getKodeKaryawan(), "Programmer", this.getRupiah(), this.getRupiahPr(), this.getRupiah2());
    }    
}

class SeniorProgrammer extends Programmer{
    private final int BONUSLEMBURPERJAMSENIORPROGRAMMER = 200000;
    private final int TUNJANGANSENIORPROGRAMMER = 3000000;

    public SeniorProgrammer(String dataNama, String dataKodeKaryawan, int dataJumlahJamLembur) {
        super(dataNama, dataKodeKaryawan, dataJumlahJamLembur);
        //tambahkan berdasarkan aturan yang anda buat
        this.jumlahJamLembur = Math.min(dataJumlahJamLembur, 40);

        this.gajiPokok = 6000000;
        this.jenisKaryawan = "Senior Programmer";
        this.feeLembur = jumlahJamLembur*BONUSLEMBURPERJAMSENIORPROGRAMMER;
    }

    @Override
    public String getRupiahPr(){
        int feeSrBonus = this.feeLembur + this.TUNJANGANSENIORPROGRAMMER;
        String strFeeBonus = Integer.toString(feeSrBonus);

        KaryawanIT.RupiahCurrencyFormat rcf =  new KaryawanIT.RupiahCurrencyFormat();
        return rcf.toRupiahFormat(strFeeBonus);
    }

    @Override
    public int gajiTotal(){
        return this.gajiPokok + this.TUNJANGANSENIORPROGRAMMER + feeLembur;
    }

    @Override
    public void info(){
        System.out.printf("%-15s | %-15s | %-20s | Rp. %-15s | Rp. %-28s | Rp. %-15s%n", this.getNama().toUpperCase(), this.getKodeKaryawan(), "Senior Programmer", this.getRupiah(), this.getRupiahPr(), this.getRupiah2());
    }
}

class ProjectLeader extends Karyawan{
    private final int TUNJANGANPROJECTLEADER = 5000000;
    protected int bonusProject;

    ProjectLeader(String dataNama, String dataKodeKaryawan) {
        super(dataNama, dataKodeKaryawan);
        this.gajiPokok = 8000000;
        this.jenisKaryawan = "Project Leader";
    }

    public void setBonusProject(int rateProject){
        this.bonusProject = (int) (0.05 * rateProject);
    }

    public String getRupiahPl(){
        int feeSrBonus = this.bonusProject + this.TUNJANGANPROJECTLEADER;
        String strFeeBonus = Integer.toString(feeSrBonus);

        KaryawanIT.RupiahCurrencyFormat rcf =  new KaryawanIT.RupiahCurrencyFormat();
        return rcf.toRupiahFormat(strFeeBonus);
    }

    @Override
    public int gajiTotal(){
        return this.gajiPokok + this.TUNJANGANPROJECTLEADER + this.bonusProject;
    }

    @Override
    public void info(){
        System.out.printf("%-15s | %-15s | %-20s | Rp. %-15s | Rp. %-28s | Rp. %-15s%n", this.getNama().toUpperCase(), this.getKodeKaryawan(), "Project Leader", this.getRupiah(), this.getRupiahPl(), this.getRupiah2());
    }
}
