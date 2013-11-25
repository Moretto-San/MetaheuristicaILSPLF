import java.io.File;
import java.io.RandomAccessFile;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			RandomAccessFile instanciaArq = new RandomAccessFile(new File(
					"InstanciaB1"), "r");
			String leitura = instanciaArq.readLine();
			String nomeIntancia = leitura.split(" ")[1];
			leitura = instanciaArq.readLine();
			int numFacilidades = Integer.parseInt(leitura.split(" ")[0]);
			int numCidades = Integer.parseInt(leitura.split(" ")[1]);
			leitura = instanciaArq.readLine();
			int[][] custoConexao = new int[numFacilidades][numCidades];
			int[] custoAberturaFacilidade = new int[numFacilidades];
			int linha = 0;
			while (leitura != null) {
				String[] split = leitura.split(" ");
				custoAberturaFacilidade[linha] = Integer.parseInt(split[1]);
				for (int j = 0; j < numCidades; j++) {
					custoConexao[linha][j] = Integer.parseInt(split[j + 2]);
				}
				linha++;
				leitura = instanciaArq.readLine();
			}
			Solucao corrente = new Solucao();
			corrente.ConstruirSolucao(custoConexao, custoAberturaFacilidade);
			System.err.println("Facilidades Usadas:"
					+ corrente.getFacilidadesUsadas().size());
			System.err.println("Custo total:" + corrente.getCustoTotal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
