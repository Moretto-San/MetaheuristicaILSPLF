import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static String nomeIntancia;
	public static Integer numFacilidades;
	public static Integer numCidades;
	public static Integer[][] custoConexao;
	public static Integer[] custoAberturaFacilidade;

	public static void main(String[] args) {
		leArqInstancia();
		// TODO Auto-generated method stub
		Random random = new Random();
		//random.setSeed(2);
		Solucao corrente = new Solucao();
		corrente.ConstruirSolucao(custoConexao, custoAberturaFacilidade, random);
		corrente = buscaLocalRemoveFacilidade(corrente, random, custoConexao);
		int iter = 0;		
		while(iter < 100){
			Solucao temp = corrente.copiarSolucao();
			perturbacao(temp, random);
			temp = buscaLocalRemoveFacilidade(temp, random, custoConexao);
			if(temp.getCustoTotal() < corrente.getCustoTotal()){
				corrente = temp.copiarSolucao();
			}
			iter++;
		}
		imprimiSolucao(corrente);
		corrente.verificaNullclientesXFacilidade();
	}

	public static void leArqInstancia() {
		try {
			RandomAccessFile instanciaArq = new RandomAccessFile(new File(
					"InstanciaB1"), "r");
			String leitura = instanciaArq.readLine();
			nomeIntancia = leitura.split(" ")[1];
			leitura = instanciaArq.readLine();
			numFacilidades = Integer.parseInt(leitura.split(" ")[0]);
			numCidades = Integer.parseInt(leitura.split(" ")[1]);
			leitura = instanciaArq.readLine();
			custoConexao = new Integer[numFacilidades][numCidades];
			custoAberturaFacilidade = new Integer[numFacilidades];
			Integer linha = 0;
			while (leitura != null) {
				String[] split = leitura.split(" ");
				custoAberturaFacilidade[linha] = Integer.parseInt(split[1]);
				for (Integer j = 0; j < numCidades; j++) {
					custoConexao[linha][j] = Integer.parseInt(split[j + 2]);
				}
				linha++;
				leitura = instanciaArq.readLine();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void imprimiSolucao(Solucao solucao) {
		solucao.imprimiSolucao();
		System.err.println("Instancia: " + nomeIntancia);
		System.err.println("Numero de Facilidades: " + numFacilidades
				+ " Numero de Cidades: " + numCidades);
		System.err.println("Facilidades Usadas: "
				+ solucao.getFacilidadesUsadas().size());
		System.err.println("Custo total: " + solucao.getCustoTotal());		
	}

	public static Solucao buscaLocalRemoveFacilidade(Solucao corrente,
			Random random, Integer[][] custoConexao) {
		Solucao temp = corrente.copiarSolucao();
		boolean melhorou = true;
		while (melhorou) {
			ArrayList<String> facilidadesUsadas = corrente
					.getFacilidadesUsadas();
			int aleatorio = random.nextInt(facilidadesUsadas.size()-1);
			temp = corrente.copiarSolucao();
			temp.removeFacilidadesUsada(facilidadesUsadas.get(aleatorio));
			temp.setCidadesSemFacilidade(custoConexao, custoAberturaFacilidade);
			if (temp.getCustoTotal() < corrente.getCustoTotal()) {
				corrente = temp.copiarSolucao();
			}else{
				melhorou = false;
			}
		}
		return corrente;
	}
	
	public static Solucao perturbacao(Solucao corrente, Random random){
		Solucao temp = corrente.copiarSolucao();
		ArrayList<String> facilidadesUsadas = corrente
				.getFacilidadesUsadas();
		int quantidade = (int) (facilidadesUsadas.size()*(0.3));
		for(int i=0; i<quantidade; i++){
			temp.removeFacilidadesUsada(facilidadesUsadas.get(i));
		}
		temp.reconstroiSolucao(quantidade, numFacilidades, random);
		return temp;
	}
	
}
