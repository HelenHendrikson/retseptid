import java.util.HashMap;

public class Retsept {

	public String nimi;
	public String selgitus;
	public HashMap<String, HashMap <Integer,String>>koostisosad = new HashMap<>();

	public Retsept(String nimi) {
		super();
		this.nimi = nimi;
	}

	public boolean saanValmistada(HashMap<String,Integer> olemas) {
		
		if(olemas.keySet().containsAll(this.koostisosad.keySet())){
			for (String toiduaine: koostisosad.keySet()){
				for(Integer kogus : koostisosad.get(toiduaine).keySet())
					if (kogus > olemas.get(toiduaine)){
					return false; //kui pole üht komponenti piisavalt
					//kui aega üle jääb (ei jää), siis siin võiks öelda, mida poest juurde oleks vaja
				}
			}
			return true; // kui kõik komponendid olid olemas ja neid oli piisavalt
		}
		return false; // kui pole olemas kõiki komponente
	}

	public void lisaKoostisosa(HashMap<String,HashMap<Integer,String>> koostisosa) {
		koostisosad.putAll(koostisosa);
	}

	@Override
	public String toString() {
		StringBuilder koostisosad = new StringBuilder();
		for (String koostisosa : this.koostisosad.keySet()){
			for(Integer kogus: this.koostisosad.get(koostisosa).keySet()){
				for(String ühik: this.koostisosad.get(koostisosa).values()){
					koostisosad.append(koostisosa +" "+ kogus + " "+  ühik+ "\n");
					
				}
					
				
				
			}
			
		}
		
		
		String retsept = koostisosad + "\n" + this.selgitus;
		return retsept;
	}
	
	
	public Retsept arvutaUuedKogused(int mitmele) {
		Retsept uusRetsept = new Retsept(this.nimi);
		uusRetsept.selgitus = this.selgitus;
		for (String toiduaine : this.koostisosad.keySet()) {
			HashMap<String, HashMap<Integer,String>> uuendatudKoostisosad = new HashMap<>();
			for(Integer kogus :  koostisosad.get(toiduaine).keySet()){
				Integer uuskogus = Math.round(kogus * mitmele);
				System.out.println(uuskogus);
				HashMap<Integer, String> kogus_ühik = new HashMap<>();
				String ühik = koostisosad.get(toiduaine).get(kogus);		
				kogus_ühik.put(uuskogus, ühik);
				uuendatudKoostisosad.put(toiduaine,kogus_ühik );
			}	
			
			uusRetsept.lisaKoostisosa(uuendatudKoostisosad);
			
		}
		return uusRetsept;
	}

	public String getNimi() {
		return nimi;
	}

	public HashMap<String, HashMap<Integer, String>> getKoostisosad() {
		return koostisosad;
	}

}

