
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Retseptiraamat111 extends Application{
	Stage aken;	

	public static void main(String[] args){
		launch(args);
	}
	
	
	public void start(Stage primaryStage) throws FileNotFoundException {
		aken = primaryStage;
		primaryStage.setTitle("Retseptigeneraator");
		
		ArrayList <String> toiduainetevalikud = new ArrayList <String>();	
		toiduainetevalikud.add("piim");
		toiduainetevalikud.add("jahu");
		toiduainetevalikud.add("munad");
		toiduainetevalikud.add("suhkruvatt");
		
		

			
		ArrayList <CheckBox> kastidej‰rjend = new ArrayList <CheckBox>();
	    for(String toiduaine : toiduainetevalikud){
	    	CheckBox aine = new CheckBox(toiduaine);
	    	kastidej‰rjend.add(aine);
	    }
	    
	    Button valmis = new Button("Valmis");
		valmis.setOnAction((event) -> {
			
			try {
				VBox layout1 = new VBox(30); // nr on kastide vahemaa ¸ksteisest
				Scene taust1 = new Scene(layout1, 200,200);
				valmis.setOnAction(e -> aken.setScene(taust1));
				ArrayList <String> retseptivalikud = pakuRetsepte(kastidej‰rjend);
				ArrayList <Button> nupud = teeNupud(retseptivalikud);
				layout1.getChildren().addAll(nupud);
				layout1.setPadding(new Insets(20,20,20,20));
				aken.setScene(taust1);
				aken.show(); // nuppudega ekraan 
				
			
				for (Button nupp: nupud){
					nupp.setOnAction((event1) -> { 
						
						try {
							
							VBox layout2 = new VBox(30); // nr on kastide vahemaa ¸ksteisest
							Scene taust2 = new Scene(layout2, 200,200);
							nupp.setOnAction(e -> aken.setScene(taust2));
							HashMap <String,StringBuilder> sınastik = sınastik(kastidej‰rjend);
							
							
							for(String vıti : sınastik.keySet()){
								if (event1.getSource() == nupp){
									String asi = nupp.getText();
									//System.out.println(asi);
									if (vıti.equals(asi)){
										StringBuilder v‰‰rtus = sınastik.get(vıti);
										String v‰‰rtus1 = v‰‰rtus.toString();
										primaryStage.setScene(taust2);
										layout2.setPadding(new Insets(20,20,20,20));
										layout2.getChildren().add(new Text(v‰‰rtus1));
										primaryStage.show();
													
									}
								}									
									//}		
								
							}			
						} catch (Exception e) {
							e.printStackTrace();
						}		
				});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
			

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		layout.getChildren().addAll(kastidej‰rjend);
		layout.getChildren().addAll(valmis);
		
		Scene scene1 = new Scene(layout, 200,200);
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	
	public ArrayList<String> pakuRetsepte(ArrayList <CheckBox> kastidej‰rjend) throws FileNotFoundException {
		
		HashMap<String,Set<String>> retseptJaKoostisosad = new HashMap <String, Set<String>>();
		File dir = new File("C:\\Users\\HelenHen\\Documents\\OOP\\v‰rk");
		ArrayList <String> retseptidekastid = new ArrayList <String> ();
		ArrayList<String> sobivadRetseptid = new ArrayList<>();

		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".txt"))) {
				StringBuilder failinimitxt = new StringBuilder(file.getName());
				String nimi = failinimitxt.delete(failinimitxt.length()-4, failinimitxt.length()).toString(); //vıtab failinime
				retseptidekastid.add(nimi);
				
				
				Set<String> koostisosad = new HashSet<String>();
				
				Scanner sc = new Scanner(file, "UTF-8");
				StringBuilder retsept = new StringBuilder();
				while (sc.hasNextLine()) {
					String rida = sc.nextLine();
					retsept.append(rida + "\n");
					if (rida.split(" ").length==3){
						String komponent = rida.split(" ")[0];
						koostisosad.add(komponent);
					}
				}
				sc.close();
				retseptJaKoostisosad.put(nimi, koostisosad);
				
			}
				
		}
		Set<String> olemas = new HashSet<String>();
		for (CheckBox toiduaine: kastidej‰rjend){
			if (toiduaine.isSelected()){
				olemas.add((String)toiduaine.getText()); // kıik olemasolev pannakse teise hulka		
			}
		}
		System.out.println(olemas);
		
		for(String vıti : retseptJaKoostisosad.keySet() ){
			Set<String> v‰‰rtus = retseptJaKoostisosad.get(vıti);
			if (olemas.containsAll(v‰‰rtus)){ 
				sobivadRetseptid.add(vıti); // siia peab panema Stringi
			}
			
		}
		System.out.println(sobivadRetseptid);
		
		return sobivadRetseptid;
		
	}
	
	public ArrayList <Button> teeNupud(ArrayList <String> j‰rjend) {
		ArrayList <Button> nupud = new ArrayList <>();
		for(String nimi : j‰rjend){
			Button nupp = new Button(nimi);
			nupud.add(nupp);		
		}
		return nupud;
	}
	
	
public HashMap<String,StringBuilder> sınastik(ArrayList <CheckBox> kastidej‰rjend) throws FileNotFoundException {
		
		HashMap<String, StringBuilder> retseptidesınastik = new HashMap <String, StringBuilder>();
		File dir = new File("C:\\Users\\HelenHen\\Documents\\OOP\\v‰rk");

		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".txt"))) {
				StringBuilder failinimitxt = new StringBuilder(file.getName());
				String nimi = failinimitxt.delete(failinimitxt.length()-4, failinimitxt.length()).toString(); //vıtab failinime
				
				
				
				Scanner sc = new Scanner(file, "UTF-8");
				StringBuilder retsept = new StringBuilder();
				while (sc.hasNextLine()) {
					String rida = sc.nextLine();
					retsept.append(rida + "\n");
					}
				sc.close();

				retseptidesınastik.put(nimi, retsept);
			}
		}
		return retseptidesınastik;				
}
}