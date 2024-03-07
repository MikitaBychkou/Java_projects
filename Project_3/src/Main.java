import java.io.*;

public
    class Main {

    public static void main(String[] args) {
    File file = new File("wilki.txt");
        if (file.exists()) {
            Ssak[] newWilki = zaladuj("wilki.txt");
        } else {
            Ssak[] stado1 = new Ssak[8];
            stado1[0] = new Wilk("Noah", (short) 2015, false, "Stado1", 1);
            stado1[1] = new Wilk("Liam", (short) 2013, false, "Stado1", 2);

            stado1[2] = new Wilk("Mason", (short) 2022, true, "Stado1", 3);
            stado1[3] = new Wilk("Jacob", (short) 2022, true, "Stado1", 4);
            stado1[4] = new Wadera("Amanda", (short) 2022, true, 0, new Ssak[]{});
            stado1[5] = new Wadera("Amelia", (short) 2022, true, 0, new Ssak[]{});

            stado1[6] = new Wadera("Iris", (short) 2016, false, 2, new Ssak[]{stado1[2],stado1[3]});
            stado1[7] = new Wadera("Alice", (short) 2015, false, 2, new Ssak[]{stado1[4],stado1[5]});

            Ssak[] stado2 = new Ssak[8];
            stado2[0] = new Wilk("Samuel", (short) 2014, false, "Stado2", 1);
            stado2[1] = new Wilk("Eberhard", (short) 2012, false, "Stado2", 2);

            stado2[2] = new Wilk("Harry", (short) 2020, true, "Stado2", 3);
            stado2[3] = new Wilk("George", (short) 2020, true, "Stado2", 4);
            stado2[4] = new Wadera("Cheril", (short) 2020, true, 0, new Ssak[]{});
            stado2[5] = new Wadera("Ella", (short) 2020, true, 0, new Ssak[]{});

            stado2[6] = new Wadera("Emma", (short) 2017, false, 2, new Ssak[]{stado2[2],stado2[3]});
            stado2[7] = new Wadera("Emily", (short) 2012, false, 2, new Ssak[]{stado2[4],stado2[5]});

            try {
                FileOutputStream fos = new FileOutputStream(file);
                for (Ssak wilk : stado1) {
                    wilk.zapisz(fos);
                }
                for (Ssak wilk : stado2) {
                    wilk.zapisz(fos);
                }
                fos.close();
            } catch (IOException e) {
                System.err.println("Cos nie poszło");
                e.printStackTrace();
            }
        }
    }
    public static Ssak[] zaladuj(String sciezka) {
        File file = new File(sciezka);
        Ssak[] ssaki = new Ssak[70];
        int ileWilkow = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String string;
            while ( (string = br.readLine())!=null) {
                String[] daneZpliku = string.split("\n");
                for (String ssakDane : daneZpliku) {
                    String[] atr = ssakDane.split("[,;]");
                    if (atr.length > 2) {
                        String imie = atr[0];
                        short rokUrodzenia = Short.parseShort(atr[1]);
                        boolean mlody = atr[2].equals("mlody");
                        if (atr.length == 5) {
                            String nazwaStad=atr[3];
                            int poz=Integer.parseInt(atr[4]);
                            ssaki[ileWilkow++] = new Wilk(imie, rokUrodzenia, mlody,nazwaStad,poz);
                            System.out.println("Wilk " + imie +", "+(mlody?"mlody":"dorosly")+", "+rokUrodzenia+ ", stado: " + nazwaStad + ", pozycja: " + poz);
                        } else if (atr.length == 4) {
                            int iloscSzczeniat = Integer.parseInt(atr[3]);
                            ssaki[ileWilkow++] = new Wadera(imie, rokUrodzenia, mlody, iloscSzczeniat, new Ssak[]{});
                            System.out.println("Wadera " + imie + ", "+(mlody?"mloda":"dorosla")+", "+rokUrodzenia+", ilosc szczeniat: " +iloscSzczeniat);
                        }
                        else{
                            int iloscSzczeniat = Integer.parseInt(atr[3]);
                            String imie1=atr[4];
                            String imie2=atr[9];
                            ssaki[ileWilkow++] = new Wadera(imie, rokUrodzenia, mlody, iloscSzczeniat, new Ssak[]{});
                            System.out.println("Wadera " + imie + ", "+(mlody?"mloda":"dorosla")+", "+rokUrodzenia+", ilosc szczeniat: " +iloscSzczeniat+", szczenieta: "+imie1+", "+imie2);
                            Ssak[] szczenieta = new Ssak[iloscSzczeniat];
                            ssaki[ileWilkow++] = new Wadera(imie, rokUrodzenia, mlody, iloscSzczeniat, szczenieta);
                        }
                    }
                }
            }

            br.close();

        } catch (IOException e) {
            System.err.println("Cos nie poszło");
            e.printStackTrace();
        }
        Ssak[] wynikowaTabWilkow = new Ssak[ileWilkow];
        for (int i = 0; i < ileWilkow; i++) {
            wynikowaTabWilkow[i] = ssaki[i];
        }

        return wynikowaTabWilkow;
    }
}

class Ssak {
    private String imie;
    private short rokUrodzenia;
    private boolean mlody;

    public Ssak(String imie, short rokUrodzenia, boolean mlody) {
        this.imie = imie;
        this.rokUrodzenia = rokUrodzenia;
        this.mlody = mlody;
    }

    public String przedstawSie() {
        return imie + "," + rokUrodzenia + "," + (mlody ? "mlody" : "dorosly") + ";";
    }

    public void zapisz(FileOutputStream fos) throws IOException {
        String s=przedstawSie()+'\n';
        fos.write(s.getBytes());
    }
}
 class Wadera extends Ssak{
    private int iloscSzczeniat;
    private Ssak[] szczenieta;

    public Wadera(String imie, short rokUrodzenia, boolean mlody, int iloscSzczeniat, Ssak[] szczenieta) {
        super(imie, rokUrodzenia, mlody);
        this.iloscSzczeniat = iloscSzczeniat;
        this.szczenieta = szczenieta;
    }

     @Override
     public void zapisz(FileOutputStream fos) throws IOException {
         super.zapisz(fos);
     }

     @Override
    public String przedstawSie() {
        String szczenietki=";";
        for (Ssak ssak:szczenieta
             ) {
            szczenietki=szczenietki+ssak.przedstawSie();
        }
        return super.przedstawSie() + iloscSzczeniat + szczenietki+";";
    }
}

class Wilk extends Ssak {
    private String nazwaStada;
    private int pozycja;

    public Wilk(String imie, short rokUrodzenia, boolean mlody, String nazwaStada, int pozycja) {
        super(imie, rokUrodzenia, mlody);
        this.nazwaStada = nazwaStada;
        this.pozycja = pozycja;
    }

    @Override
    public void zapisz(FileOutputStream fos) throws IOException {
        super.zapisz(fos);
    }

    @Override
    public String przedstawSie() {
        return super.przedstawSie() + nazwaStada + "," + pozycja + ";";
    }
}