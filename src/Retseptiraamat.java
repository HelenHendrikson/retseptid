import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Retseptiraamat {

	public ArrayList<Retsept> retseptid = new ArrayList<Retsept>(); // list
																	// kõigist
																	// retsepridest,
																	// mis
																	// kaustas
																	// olid
	
	public void loeSisseKoikRetseptid(String asukoht) {
		File dir = new File(asukoht);

		ArrayList<Retsept> retseptid = new ArrayList<Retsept>();

		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".txt"))) {
				retseptid.add(loeRetseptFailist(file));
				System.out.println(retseptid);
			}
		}
		this.retseptid = retseptid;
	}

	@Override
	public String toString() {
		return "Retseptiraamat [retseptid=" + retseptid + "]";
	}

	public Retseptiraamat(ArrayList<Retsept> retseptid) {
		super();
		this.retseptid = retseptid;
	}

	public Retseptiraamat() {
	}

	public Retsept loeRetseptFailist(File file) {

		String nimi = file.getName().replaceAll(".txt", "");
		Retsept uusRetsept = new Retsept(nimi);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String rida;
			while ((rida = br.readLine()) != null) {
				System.out.println(rida);
				if (rida.equals("")) {
					uusRetsept.selgitus = br.readLine();
					break;
				}
				HashMap<String, HashMap <Integer,String>> koostisosad = new HashMap<>();
				String koostisosa = rida.split(" ")[0];
				int kogus = Integer.parseInt(rida.split(" ")[1]);
				String ühik = rida.split(" ")[2];
				
				HashMap<Integer,String> kogus_ühik  = new HashMap <>();
				kogus_ühik.put(kogus, ühik);
				
				koostisosad.put(koostisosa,kogus_ühik);
				uusRetsept.lisaKoostisosa(koostisosad);
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uusRetsept;
	}

	private void lisaRetsept(Retsept uus) {
		retseptid.add(uus);
	}

	public ArrayList<Retsept> pakuRetsepte(HashMap<String, Integer> olemas) {
		ArrayList<Retsept> sobivadRetseptid = new ArrayList<Retsept>();
		for (Retsept retsept : this.retseptid) {
			if (retsept.saanValmistada(olemas)) {
				sobivadRetseptid.add(retsept);
			}
			
		}
		return sobivadRetseptid;
	}
	
	
	public Retseptiraamat suurendaKoguseid(int mitmele){
		Retseptiraamat uuteKogustegaRetseptid = new Retseptiraamat();
		for (Retsept retsept : this.retseptid) {
			uuteKogustegaRetseptid.lisaRetsept(retsept.arvutaUuedKogused(mitmele));
		}
		return uuteKogustegaRetseptid;
	}
	
}


