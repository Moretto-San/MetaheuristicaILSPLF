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

	public void removeFacilidadesUsada(Integer facilidade) {
		System.err.println("re");
		this.facilidadesUsadas.remove(facilidade);
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
			AddFacilidadesUsada(facilidade);
			iter++;
		}
		for (int i = 0; i < this.clientesXFacilidade.length; i++) {
			int linhaMenorCusto = 0;
			int menorCusto = Integer.MAX_VALUE;
			for (int facilidadesUsada : facilidadesUsadas) {
				if (custoConexao[facilidadesUsada][i] < menorCusto) {
					menorCusto = custoConexao[facilidadesUsada][i];
					linhaMenorCusto = facilidadesUsada;
				}
			}
			this.clientesXFacilidade[i] = linhaMenorCusto;
			this.custoClienteXFacilidade[i] = menorCusto;
			this.custoTotal += menorCusto;
		}
		removeFacilidadesNaoUsadas();
		for (Integer facilidadesUsada : facilidadesUsadas) {
			this.custoTotal += custoAberturaFacilidade[facilidadesUsada];
		}
	}

	public Solucao buscaLocalRemoveFacilidade() {
		Solucao solucao = new Solucao();
		for (int facilidadeUsada : facilidadesUsadas) {

		}
		return solucao;
	}

	public void removeFacilidadesNaoUsadas() {
		ArrayList<Integer> facilidadesRemover = new ArrayList<Integer>();
		for (int facilidadesUsada : facilidadesUsadas) {
			boolean achou = false;
			for (int facilidade : clientesXFacilidade) {
				if (facilidade == facilidadesUsada) {
					achou = true;
				}
			}
			if (!achou) {
				facilidadesRemover.add(facilidadesUsada);				
			}
		}
		for (Integer facilidade : facilidadesRemover) {
			removeFacilidadesUsada(facilidade);
		}
	}

}
