package br.com.cicom.comunicacicom.criptografia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CriptografiaDeUrl {
	
	public static String criptografarUrl(String urlSemIds) {

		String[] arrayDePaginas = urlSemIds.split(Pattern.quote("/"));
	
		String sufixo = arrayDePaginas[arrayDePaginas.length - 1];

		List<Integer> listaDeLetras = new ArrayList<>();

		for (int i = 0; i < sufixo.length(); i++) {
			listaDeLetras.add(Integer.valueOf(""+sufixo.charAt(i)));
		}
		String sufixoCriptografado = criptografa(listaDeLetras);
			String url = "";
		for (String pagina : arrayDePaginas) {
			url = url + pagina + "/";
		}
		return urlSemIds.replace(sufixo, sufixoCriptografado);

	}
	
	public static String criptografarNumerosString(String urlSemIds) {
		List<Integer> listaDeLetras = new ArrayList<>();

		for (int i = 0; i < urlSemIds.length(); i++) {
			listaDeLetras.add(Integer.valueOf(""+urlSemIds.charAt(i)));
		}

		String sufixoCriptografado = criptografa(listaDeLetras);
			return sufixoCriptografado;

	}


	private static char[] getArrayMontado() {
		char[] arrayMontado = { 'f', 'g', 'i', 's', 'w', 'q', 'c', 'h', 'u', 'x', 'p','e','o','k','a','z','A','P','J'};
		return arrayMontado;
	}

	private static List<int[]> getlistaDeOrdens() {
		List<int[]> listaDeOrdens = new ArrayList<>();
		int[] novaOrdem0 = { 0 , 12 , 28 , 25 , 20 , 13 , 22 , 27 , 19 , 6 , 4 , 17 , 2 , 24 , 7 , 1 , 11 , 14 , 30 , 9 , 23 , 18 , 21 , 15 , 5 , 8 , 16 , 26 , 29 , 3};
		listaDeOrdens.add(novaOrdem0);
		int[] novaOrdem1 = { 3 , 25 , 8 , 27 , 11 , 7 , 26 , 18 , 22 , 30 , 4 , 13 , 0 , 28 , 17 , 2 , 21 , 5 , 24 , 20 , 23 , 6 , 19 , 15 , 29 , 1 , 12 , 9 , 14 , 16 };
		listaDeOrdens.add(novaOrdem1);
		int[] novaOrdem2 = { 4 , 12 , 6 , 30 , 18 , 0 , 19 , 2 , 5 , 24 , 21 , 20 , 16 , 15 , 17 , 9 , 22 , 7 , 26 , 27 , 29 , 25 , 23 , 14 , 3 , 11 , 1 , 28 , 8 , 13 };
		listaDeOrdens.add(novaOrdem2);
		int[] novaOrdem3 = { 12 , 0 , 15 , 21 , 1 , 29 , 6 , 14 , 27 , 3 , 4 , 20 , 7 , 24 , 26 , 9 , 13 , 30 , 17 , 8 , 28 , 18 , 23 , 19 , 25 , 16 , 5 , 11 , 22 , 2 };
		listaDeOrdens.add(novaOrdem3);
		int[] novaOrdem4 = { 11 , 12 , 19 , 17 , 23 , 9 , 24 , 15 , 7 , 4 , 30 , 16 , 26 , 13 , 6 , 5 , 2 , 8 , 3 , 14 , 0 , 21 , 20 , 18 , 27 , 29 , 22 , 25 , 28 , 1 };
		listaDeOrdens.add(novaOrdem4);
		int[] novaOrdem5 = { 14 , 1 , 22 , 27 , 29 , 23 , 28 , 3 , 8 , 9 , 2 , 6 , 16 , 11 , 17 , 5 , 24 , 18 , 0 , 15 , 30 , 25 , 19 , 7 , 21 , 26 , 20 , 12 , 13 , 4 };
		listaDeOrdens.add(novaOrdem5);
		int[] novaOrdem6 = { 17 , 16 , 11 , 20 , 0 , 21 , 29 , 15 , 1 , 3 , 22 , 5 , 8 , 25 , 6 , 24 , 4 , 18 , 30 , 23 , 14 , 26 , 13 , 27 , 2 , 9 , 12 , 28 , 7 , 19 };
		listaDeOrdens.add(novaOrdem6);
		int[] novaOrdem7 = { 29 , 7 , 22 , 5 , 17 , 30 , 26 , 20 , 19 , 6 , 27 , 2 , 24 , 21 , 11 , 14 , 15 , 4 , 3 , 25 , 23 , 0 , 1 , 13 , 18 , 16 , 12 , 8 , 9 , 28 };
		listaDeOrdens.add(novaOrdem7);
		int[] novaOrdem8 = { 7 , 3 , 24 , 1 , 5 , 13 , 11 , 26 , 20 , 30 , 19 , 21 , 14 , 16 , 9 , 8 , 28 , 4 , 6 , 17 , 18 , 27 , 12 , 0 , 15 , 25 , 2 , 23 , 22 , 29 };
		listaDeOrdens.add(novaOrdem8);
		int[] novaOrdem9 = { 11 , 1 , 16 , 7 , 18 , 5 , 27 , 6 , 22 , 24 , 14 , 20 , 21 , 3 , 19 , 4 , 8 , 12 , 0 , 17 , 26 , 25 , 28 , 29 , 23 , 30 , 9 , 15 , 2 , 13 };
		listaDeOrdens.add(novaOrdem9);
		return listaDeOrdens;
	}

	
	private static char[] getlistaDeLetrasParaSubstiuir() {
		 char[] listaDeLetras = { '$' , 'h' , 'm' , 'z' , 'x' ,'k', 'w', 'y', 'r', 'T','$' , 'h' , 'm' , 'z' , 'x' , 'k', 'w', 'y', 'r', 'T','$' , 'h' , 'm' , 'z' ,  'x' ,  'k', 'w', 'y', 'r', 'T','$' , 'h' , 'm' , 'z' ,   'x' , 'k', 'w', 'y', 'r', 'T', 'z' , 'x' , 'k', 'w', 'y', 'r', 'T','$' , 'h' , 'm' , 'z' ,  'x' ,  'k', 'w', 'y', 'r', 'T','$' , 'h' , 'm' , 'z' ,   'x' , 'k', 'w', 'y', 'r', 'T'};
		 return listaDeLetras;
	}
	
	private static List<Integer> getlistaDeListaDeOrdens() {

		List<Integer> listaDeListaDeOrdens = new ArrayList<>();
		listaDeListaDeOrdens.add(0);
		listaDeListaDeOrdens.add(1);
		listaDeListaDeOrdens.add(2);
		listaDeListaDeOrdens.add(3);
		listaDeListaDeOrdens.add(4);
		listaDeListaDeOrdens.add(5);
		listaDeListaDeOrdens.add(6);
		listaDeListaDeOrdens.add(7);
		listaDeListaDeOrdens.add(8);
		listaDeListaDeOrdens.add(9);
		Collections.shuffle(listaDeListaDeOrdens);
		Collections.shuffle(listaDeListaDeOrdens);
		return listaDeListaDeOrdens;
	}

	
	  private static String criptografa(List<Integer> listaDeLetras) {
	  
	  char[] arrayMontado = getArrayMontado();
	  
	  int indiceDeNovaOrdem = getlistaDeListaDeOrdens().get(3);

	  int[] novaOrdem = getlistaDeOrdens().get(indiceDeNovaOrdem);

	  List<Integer> listaComNovaOrdem =  new ArrayList<>(); 
	  
	  for(Integer numero: listaDeLetras){
			  listaComNovaOrdem.add(numero);
		  }
	  int ponteiro = 0;
	  
	  for(int i = 0 ; i < novaOrdem.length || listaDeLetras.size()> ponteiro ; i++) {
		  if(novaOrdem[i] < listaDeLetras.size()) {
			  listaComNovaOrdem.set(novaOrdem[i], listaDeLetras.get(ponteiro));
			  ponteiro++;
		  }		  
	  }
	 
	  listaDeLetras = listaComNovaOrdem;
	  
	  
	  if(listaDeLetras.size() % 2 == 0){ 
		  Integer letra = listaDeLetras.get(listaDeLetras.size()/2);
		  listaDeLetras.set(listaDeLetras.size()/2, indiceDeNovaOrdem);
		  listaDeLetras.add(letra); 
	  }else{ 
		  Integer letra = listaDeLetras.get(((listaDeLetras.size()+1)/2)-1);		 
		  listaDeLetras.set((((listaDeLetras.size()+1)/2)-1), indiceDeNovaOrdem);
		  listaDeLetras.add(letra);
	  }
	  String numeroCriptografado = "";

	  for(Integer i : listaDeLetras) {
		  numeroCriptografado =  numeroCriptografado+arrayMontado[i]; 
	  }
	  listaDeLetras.clear();
	  for (int i = 0; i < numeroCriptografado.length(); i++) {
			listaDeLetras.add((int)numeroCriptografado.charAt(i));
	  }
	  String Hexadecimais = "";
	  
		  for(int i : numeroCriptografado.toCharArray()) { 
			  Hexadecimais = Hexadecimais + Integer.toHexString(i); 
		  }
	  
		  String numeroFinal= "";
		  
		  char[] listaDeCaracteres = getlistaDeLetrasParaSubstiuir();
		  int apontador = 0;
		  for(int i = 0;i < Hexadecimais.length()-1; i++ ) {
			  if(Hexadecimais.charAt(i) == Hexadecimais.charAt(i+1)) {
				  numeroFinal = numeroFinal + Hexadecimais.charAt(i) + listaDeCaracteres[apontador];
				  apontador++;
			  }else if(Hexadecimais.split(""+Hexadecimais.charAt(i)).length > 2){
				  numeroFinal = numeroFinal + Hexadecimais.charAt(i)+listaDeCaracteres[apontador];
				  apontador++;
			  } else {
				  numeroFinal = numeroFinal + Hexadecimais.charAt(i);
			  }
		  }
		  numeroFinal = numeroFinal + Hexadecimais.charAt(Hexadecimais.length()-1);  
//		  System.out.println("criptografado ->> " +numeroFinal);
//		  String resultado = embaralhaOsNumeros(numeroFinal);
//		  System.out.println("melhorando ->> " +resultado);
//		  System.out.println("retornando ->> " +desembaralhaOsNumeros(resultado));
	  return numeroFinal; 
	}
	
	  private static String embaralhaOsNumeros(String criptografia) {
		 String numeros = "";
		 String numerosAtivos = "";
		 char ultimoNumero = ' ';
		 List<String> listaI = new ArrayList<>();
		 listaI.add("1");
		 listaI.add("5");
		 listaI.add("7");
		
		 
		 List<String> listaP = new ArrayList<>();
		 listaP.add("0");
		 listaP.add("2");
		 listaP.add("4");
		 listaP.add("8");
		 
		 List<String> listaD3 = new ArrayList<>();
		 
		 listaD3.add("6");
		 listaD3.add("3");
		 listaD3.add("9");
		 
			for (int i = 0; i < criptografia.length(); i++) {
				if(criptografia.charAt(i) == '0' || criptografia.charAt(i) == '1' || criptografia.charAt(i) == '2' || criptografia.charAt(i) == '3' || criptografia.charAt(i) == '4' || criptografia.charAt(i) == '5' || criptografia.charAt(i) == '6' || criptografia.charAt(i) == '7' || criptografia.charAt(i) == '8' || criptografia.charAt(i) == '9') {	
				
					if(!numerosAtivos.contains(""+criptografia.charAt(i))) {
						if(ultimoNumero == criptografia.charAt(i)) {
							Collections.shuffle(listaP);
							numeros = numeros+listaP.get(1);
						}else{
							Collections.shuffle(listaI);
							numeros = numeros+listaI.get(1)+criptografia.charAt(i);
							ultimoNumero = criptografia.charAt(i);
						}
						
						numerosAtivos=numerosAtivos+criptografia.charAt(i);
						
					}else{
						Collections.shuffle(listaD3);
						//System.out.println("teste antes "+numerosAtivos.indexOf(criptografia.charAt(i)));
						numeros = numeros+listaD3.get(1)+numerosAtivos.indexOf(criptografia.charAt(i));
					}
					
					
				}else{
					numeros = numeros+criptografia.charAt(i);
				}
			}
			
		  return numeros;
	  }
	  
	  private static String desembaralhaOsNumeros(String criptografia) {
		 String numeros = "";
		 String numerosAtivos = "";
		 char ultimoNumero = ' ';
		 List<Integer> listaDeIndices = new ArrayList<>();
		 int apontador = 1;
		 
		 for (int i = 0; i < criptografia.length(); i++) {
			 if(criptografia.charAt(i) == '0' || criptografia.charAt(i) == '1' || criptografia.charAt(i) == '2' || criptografia.charAt(i) == '3' || criptografia.charAt(i) == '4' || criptografia.charAt(i) == '5' || criptografia.charAt(i) == '6' || criptografia.charAt(i) == '7' || criptografia.charAt(i) == '8' || criptografia.charAt(i) == '9') {	
				if(!numerosAtivos.contains(""+criptografia.charAt(i))) {
					 numerosAtivos = numerosAtivos+ criptografia.charAt(i);
					 listaDeIndices.add(i);
				}
				
			 }	
		 }
			for (int i = 0; i < criptografia.length(); i++) {
				if(criptografia.charAt(i) == '0' || criptografia.charAt(i) == '1' || criptografia.charAt(i) == '2' || criptografia.charAt(i) == '3' || criptografia.charAt(i) == '4' || criptografia.charAt(i) == '5' || criptografia.charAt(i) == '6' || criptografia.charAt(i) == '7' || criptografia.charAt(i) == '8' || criptografia.charAt(i) == '9') {	
				
					if(Integer.valueOf(criptografia.charAt(i)) % 2 != 0 || Integer.valueOf(criptografia.charAt(i)) == 6){
						if(Integer.valueOf(""+criptografia.charAt(i)) == 3 || Integer.valueOf(""+criptografia.charAt(i)) == 6 || Integer.valueOf(""+criptografia.charAt(i)) == 9) {
							System.out.println("ativos - > "+numerosAtivos+"   criptografiaAt --> "+criptografia.charAt(listaDeIndices.get(apontador))+" valor ->"+Integer.valueOf(""+criptografia.charAt(listaDeIndices.get(apontador))));
							numeros = numeros + numerosAtivos.charAt(Integer.valueOf(""+criptografia.charAt(listaDeIndices.get(apontador))));
							apontador++;
							i++;
						//numerosAtivos = numerosAtivos+numerosAtivos.charAt(Integer.valueOf(""+criptografia.charAt(i+1)));
						
						}else if(ultimoNumero == ' ') {	
							ultimoNumero = criptografia.charAt(listaDeIndices.get(apontador));
							apontador ++;
							numeros = numeros + ultimoNumero;
						//	numerosAtivos = ""+ultimoNumero;
							i++;
						}else {
							numeros = numeros + ultimoNumero;
							//numerosAtivos = numerosAtivos+ultimoNumero;
						}
					
					} else {
						ultimoNumero = criptografia.charAt(listaDeIndices.get(apontador));
						apontador ++;
						//numerosAtivos = numerosAtivos+ultimoNumero;
						numeros = numeros+ultimoNumero;
						i++;
					}
					
					
					
				}else {
					numeros = numeros + criptografia.charAt(i);
				}
				
				
			}			
		  return numeros;
	  }
	  
	public static String descriptografarUrl(String identificador) {
		
		List<Integer> listaDeLetras = new ArrayList<>();

		for (int i = 0; i < identificador.length(); i++) { 
			if(identificador.charAt(i) == '0' || identificador.charAt(i) == '1' || identificador.charAt(i) == '2' || identificador.charAt(i) == '3' || identificador.charAt(i) == '4' || identificador.charAt(i) == '5' || identificador.charAt(i) == '6' || identificador.charAt(i) == '7' || identificador.charAt(i) == '8' || identificador.charAt(i) == '9') {
				listaDeLetras.add(Integer.valueOf(""+identificador.charAt(i)));
			}	
		}

		List<String> numerosHexadecimais = new ArrayList<>();

		for (int i = 0; i < listaDeLetras.size(); i = i + 2) {
			if(i+1 < listaDeLetras.size()) {
				numerosHexadecimais.add("" + listaDeLetras.get(i) + "" +  listaDeLetras.get(i + 1));
			}else{
				numerosHexadecimais.add("0" + listaDeLetras.get(i));
			}
		}
		
		List<Integer> numerosDecimais = new ArrayList<>();
		
		for (String numeroHex : numerosHexadecimais) {
			numerosDecimais.add(Integer.parseInt(numeroHex, 16));
		}
		
		String listaDeCaracteres = "";

		for (int numeroDec : numerosDecimais) {
			char caractere = (char) numeroDec;
			listaDeCaracteres = listaDeCaracteres + caractere;
		}

		char[] arrayMontado = getArrayMontado();
		char[] arrayCaractere = listaDeCaracteres.toCharArray();
		
		List<Integer> listaDeNumerosOriginais = new ArrayList<>();
		
		for (int i = 0; i < arrayCaractere.length; i++) {
			for (int j = 0; j < arrayMontado.length; j++) {

				if (arrayMontado[j] == arrayCaractere[i]) {
					listaDeNumerosOriginais.add(j);
				}
			
			}
		}
		Integer indiceDeNovaOrdem = 0;
		
		  if(listaDeNumerosOriginais.size() % 2 != 0 && listaDeNumerosOriginais.size() > 2){ 
			  indiceDeNovaOrdem = listaDeNumerosOriginais.get(((listaDeNumerosOriginais.size()-1)/2));
			  listaDeNumerosOriginais.set(((listaDeNumerosOriginais.size()-1)/2), listaDeNumerosOriginais.get(listaDeNumerosOriginais.size()-1));
			  listaDeNumerosOriginais.remove(listaDeNumerosOriginais.size()-1);
		  }else if(listaDeNumerosOriginais.size() % 2 == 0 && listaDeNumerosOriginais.size() > 2){ 
			  indiceDeNovaOrdem = listaDeNumerosOriginais.get(((listaDeNumerosOriginais.size())/2));
			  listaDeNumerosOriginais.set((((listaDeNumerosOriginais.size())/2)),listaDeNumerosOriginais.get(listaDeNumerosOriginais.size()-1));
			  listaDeNumerosOriginais.remove(listaDeNumerosOriginais.size()-1);
		  }else if(listaDeNumerosOriginais.size() == 2) {
			  indiceDeNovaOrdem = listaDeNumerosOriginais.get(0);
			  listaDeNumerosOriginais.set(0,listaDeNumerosOriginais.get(1));
			  listaDeNumerosOriginais.remove(listaDeNumerosOriginais.get(1));	
		  }

		List<int[]> listaDeOrdem = getlistaDeOrdens();
		List<Integer> listaDeNumerosOriginaisNaOrdem = new ArrayList<>();
		List<Integer> lista = new ArrayList<>();
		int[] ordem = listaDeOrdem.get(indiceDeNovaOrdem);
	
			for(int n = 0; n < ordem.length; n++) {
				if (ordem[n] < listaDeNumerosOriginais.size()) {
					lista.add(ordem[n]);

				}
			}	
			
			for(int i: lista) {
				listaDeNumerosOriginaisNaOrdem.add(listaDeNumerosOriginais.get(i));
				
             
	}
				String numeroLivre = "";
			for(Integer numero: listaDeNumerosOriginaisNaOrdem) {
				numeroLivre = numeroLivre + Integer.toString(numero);
			}
			
		return numeroLivre;

	}


}
