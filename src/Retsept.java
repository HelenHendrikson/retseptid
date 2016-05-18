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
					return false; //kui pole �ht komponenti piisavalt
					//kui aega �le j��b (ei j��), siis siin v�iks �elda, mida poest juurde oleks vaja
				}
			}
			return true; // kui k�ik komponendid olid olemas ja neid oli piisavalt
		}
		return false; // kui pole olemas k�iki komponente
	}

	public void lisaKoostisosa(HashMap<String,HashMap<Integer,String>> koostisosa) {
		koostisosad.putAll(koostisosa);
	}

	@Override
	public String toString() {
		StringBuilder koostisosad = new StringBuilder();
		for (String koostisosa : this.koostisosad.keySet()){
			for(Integer kogus: this.koostisosad.get(koostisosa).keySet()){
				for(String �hik: this.koostisosad.get(koostisosa).values()){
					koostisosad.append(koostisosa +" "+ kogus + " "+  �hik+ "\n");
					
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
				HashMap<Integer, String> kogus_�hik = new HashMap<>();
				String �hik = koostisosad.get(toiduaine).get(kogus);		
				kogus_�hik.put(uuskogus, �hik);
				uuendatudKoostisosad.put(toiduaine,kogus_�hik );
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

