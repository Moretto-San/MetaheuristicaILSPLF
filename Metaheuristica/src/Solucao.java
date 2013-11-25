import java.util.ArrayList;
import java.util.Random;

public class Solucao {

	private int[] clientesXFacilidade;
	private int custoTotal = Integer.MAX_VALUE;
	private int[] custoClienteXFacilidade;
	ArrayList<Integer> facilidadesUsadas = new ArrayList<Integer>();

	public ArrayList<Integer> getFacilidadesUsadas() {
		return facilidadesUsadas;
	}

	public void AddFacilidadesUsada(int facilidadeNova) {
		boolean jaPossui = false;
		for (Integer facilidade : this.facilidadesUsadas) {
			if (facilidade == facilidadeNova) {
				jaPossui = false;
			}
		}
		if (!jaPossui) {
			this.facilidadesUsadas.add(facilidadeNova);
		}
	}

	public void removeFacilidadesUsada(int facilidade) {
		this.facilidadesUsadas.remove(facilidadesUsadas);
	}

	public void setFacilidadesUsadas(ArrayList<Integer> facilidadesUsadas) {
		this.facilidadesUsadas = facilidadesUsadas;
	}

	public int[] getClientesXFacilidade() {
		return clientesXFacilidade;
	}

	public void setClientesXFacilidade(int[] clientesXFacilidade) {
		this.clientesXFacilidade = clientesXFacilidade;
	}

	public int getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(int custoTotal) {
		this.custoTotal = custoTotal;
	}

	public int[] getCustoClienteXFacilidade() {
		return custoClienteXFacilidade;
	}

	public void setCustoClienteXFacilidade(int[] custoClienteXFacilidade) {
		this.custoClienteXFacilidade = custoClienteXFacilidade;
	}

	public void ConstruirSolucao(int[][] custoConexao,
			int[] custoAberturaFacilidade) {
		this.custoTotal = 0;
		this.clientesXFacilidade = new int[custoConexao[0].length];
		this.custoClienteXFacilidade = new int[custoConexao[0].length];
		int iter = 0;
		Random random = new Random();
		while (iter < custoAberturaFacilidade.length / 2) {
			int facilidade = random.nextInt(custoAberturaFacilidade.length);
			System.err.println(facilidade);
			AddFacilidadesUsada(facilidade);
			iter++;
		}
		for (int i = 0; i < this.clientesXFacilidade.length; i++) {
			int linhaMenorCusto = 0;
			int menorCusto = Integer.MAX_VALUE;
			for (int linha = 0; linha < custoConexao.length; linha++) {
				if (custoConexao[linha][i] < menorCusto) {
					menorCusto = custoConexao[linha][i];
					linhaMenorCusto = linha;
				}
			}
			this.clientesXFacilidade[i] = linhaMenorCusto;
			this.custoClienteXFacilidade[i] = menorCusto;
			this.custoTotal += menorCusto;
		}
		for (int facilidade : clientesXFacilidade) {
			if (!this.facilidadesUsadas.contains(facilidadesUsadas)) {
				removeFacilidadesUsada(facilidade);
			}
		}
	}

}
